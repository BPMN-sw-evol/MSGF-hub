package com.MSGFCentralSys.MSGFCentralSys.services;

import com.MSGFCentralSys.MSGFCentralSys.dto.CreditRequestDTO;
import com.MSGFCentralSys.MSGFCentralSys.dto.TaskInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msgfoundation.annotations.BPMNGetterVariables;
import com.msgfoundation.annotations.BPMNSetterVariables;
import com.msgfoundation.annotations.BPMNTask;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@Service
@BPMNTask(type = "UserTask",name = "Aprobar proceso de pago")
@RequiredArgsConstructor
public class TreasuryServices {
    private final RestTemplate restTemplate;
    private List<TaskInfo> tasksList = new ArrayList<>();

    public List<String> getAllProcessByActivityId(String activityId) {
        String url = "http://localhost:9000/engine-rest/history/activity-instance?sortBy=startTime&sortOrder=desc&activityId=" + activityId + "&finished=false&unfinished=true&withoutTenantId=false";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        List<String> processIds = new ArrayList<>();

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                for (JsonNode node : jsonNode) {
                    String processInstanceId = node.get("processInstanceId").asText();
                    processIds.add(processInstanceId);
                }
            } catch (IOException e) {
                System.err.println("Error al analizar la respuesta JSON: " + e.getMessage());
            }
        } else {
            System.err.println("Error al obtener las instancias de proceso: " + responseEntity.getStatusCode());
        }

        return processIds;
    }

    @BPMNGetterVariables(container = "CreditRequestDTO", variables = {"coupleName1", "coupleName2", "coupleEmail1", "coupleEmail2", "marriageYears", "bothEmployees", "housePrices", "quotaValue", "coupleSavings", "creationDate", "countReviewsBpm"})
    public CreditRequestDTO getProcessVariablesById(String processId) {
        String CAMUNDA_API_URL = "http://localhost:9000/engine-rest/";
        String camundaURL = CAMUNDA_API_URL + "process-instance/" + processId + "/variables?deserializeValues=true";

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                camundaURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        Map<String, Object> variablesMap = responseEntity.getBody();

        if (variablesMap != null) {
            CreditRequestDTO creditRequest = new CreditRequestDTO();
            Map<String, Object> coupleName1Map = (Map<String, Object>) variablesMap.get("coupleName1");
            String coupleName1Value = (String) coupleName1Map.get("value");
            creditRequest.setCoupleName1(coupleName1Value);

            Map<String, Object> coupleName2Map = (Map<String, Object>) variablesMap.get("coupleName2");
            String coupleName2Value = (String) coupleName2Map.get("value");
            creditRequest.setCoupleName2(coupleName2Value);

            Map<String, Object> marriageYearsMap = (Map<String, Object>) variablesMap.get("marriageYears");
            Integer marriageYearsValue = (Integer) marriageYearsMap.get("value");
            creditRequest.setMarriageYears(marriageYearsValue.longValue());

            Map<String, Object> bothEmployeesMap = (Map<String, Object>) variablesMap.get("bothEmployees");
            Boolean bothEmployeesValue = (Boolean) bothEmployeesMap.get("value");
            creditRequest.setBothEmployees(bothEmployeesValue);

            Map<String, Object> housePricesMap = (Map<String, Object>) variablesMap.getOrDefault("housePrices", Collections.singletonMap("value", 0));
            Integer housePricesValue = (Integer) housePricesMap.get("value");
            creditRequest.setHousePrices(housePricesValue != null ? housePricesValue.longValue() : 0);

            Map<String, Object> quotaValueMap = (Map<String, Object>) variablesMap.getOrDefault("quotaValue", Collections.singletonMap("value", 0));
            Integer quotaValueValue = (Integer) quotaValueMap.get("value");
            creditRequest.setQuotaValue(quotaValueValue != null ? quotaValueValue.longValue() : 0);

            Map<String, Object> coupleSavingsMap = (Map<String, Object>) variablesMap.getOrDefault("coupleSavings", Collections.singletonMap("value", 0));
            Integer coupleSavingsValue = (Integer) coupleSavingsMap.get("value");
            creditRequest.setCoupleSavings(coupleSavingsValue != null ? coupleSavingsValue.longValue() : 0);

            Map<String, Object> requestDateMap = (Map<String, Object>) variablesMap.get("creationDate");
            String requestDateValue = (String) requestDateMap.get("value");
            creditRequest.setRequestDate(requestDateValue);

            Map<String, Object> coupleEmail1Map = (Map<String, Object>) variablesMap.get("coupleEmail1");
            String coupleEmail1Value = (String) coupleEmail1Map.get("value");
            creditRequest.setCoupleEmail1(coupleEmail1Value);

            Map<String, Object> coupleEmail2Map = (Map<String, Object>) variablesMap.get("coupleEmail2");
            String coupleEmail2Value = (String) coupleEmail2Map.get("value");
            creditRequest.setCoupleEmail2(coupleEmail2Value);

            Map<String, Object> countReviewsCSMap = (Map<String, Object>) variablesMap.getOrDefault("countReviewsBpm", Collections.singletonMap("value", 0));
            Integer countReviewsCSValue = (Integer) countReviewsCSMap.get("value");
            creditRequest.setCountReviewsCS(countReviewsCSValue != null ? countReviewsCSValue.longValue() : 0);

            creditRequest.setProcessId(processId);
            return creditRequest;
        } else {
            return null; // Devolver null si no se encontraron variables
        }
    }

    public TaskInfo getTaskInfoByProcessId(String processId) {
        // Construir la URL para consultar las tareas relacionadas con el proceso
        String camundaUrl = "http://localhost:9000/engine-rest/task?processInstanceId=" + processId;

        try {
            // Realizar una solicitud GET a Camunda para obtener la lista de tareas
            ResponseEntity<List<Map>> response = restTemplate.exchange(camundaUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Map>>() {
            });

            // Verificar si la respuesta contiene tareas
            List<Map> tasks = response.getBody();
            if (tasks != null && !tasks.isEmpty()) {
                // Supongamos que tomamos la primera tarea encontrada
                Map<String, String> taskInfoMap = new HashMap<>();
                taskInfoMap.put("taskId", String.valueOf(tasks.get(0).get("id")));
                taskInfoMap.put("taskName", String.valueOf(tasks.get(0).get("name")));
                taskInfoMap.put("assignee", String.valueOf(tasks.get(0).get("assignee")));

                System.out.println("Task Info for Process ID " + processId + ":");
                System.out.println("Task ID: " + taskInfoMap.get("taskId"));
                System.out.println("Task Name: " + taskInfoMap.get("taskName"));
                System.out.println("Assignee: " + taskInfoMap.get("assignee"));

                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setProcessId(processId);
                taskInfo.setTaskId(taskInfoMap.get("taskId"));
                taskInfo.setTaskName(taskInfoMap.get("taskName"));
                taskInfo.setTaskAssignee(taskInfoMap.get("assignee"));

                tasksList.add(taskInfo);
                return taskInfo;
            } else {
                System.err.println("No tasks found for Process ID " + processId);
                return null;
            }
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            System.err.println("Error en la solicitud a Camunda: " + errorMessage);
            return null;
        }
    }

    public String getTaskIdByProcessIdWithApi(String processId) {
        String camundaUrl = "http://localhost:9000/engine-rest/task?processInstanceId=" + processId;

        try {
            ResponseEntity<List<Map>> response = restTemplate.exchange(camundaUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Map>>() {
            });
            List<Map> tasks = response.getBody();

            if (tasks != null && !tasks.isEmpty()) {
                return String.valueOf(tasks.get(0).get("id"));
            } else {
                System.err.println("No tasks found for Process ID " + processId);
                return null;
            }
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            System.err.println("Error en la solicitud a Camunda: " + errorMessage);
            return null;
        }
    }

    public String getTaskNameByProcessId(String processId) {
        for (TaskInfo taskInfo : tasksList) {
            if (taskInfo.getProcessId().equals(processId)) {
                return taskInfo.getTaskName();
            }
        }
        return null;
    }

    public void updateTaskByProcessId(String processId, String taskId) {
        for (TaskInfo taskInfo : tasksList) {
            if (taskInfo.getProcessId().equals(processId)) {
                taskInfo.setTaskId(taskId);
            }
        }
    }

    public String approveTask(String processId) {
        TaskInfo taskInfo = getTaskInfoByProcessId(processId);

        if (taskInfo != null) {
            String taskId = taskInfo.getTaskId();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            HttpEntity<Map> requestEntity = new HttpEntity<>(requestBody, headers);

            try {
                String camundaUrl = "http://localhost:9000/engine-rest/task/" + taskId + "/complete";
                restTemplate.postForEntity(camundaUrl, requestEntity, Map.class);
                String newTaskId = getTaskIdByProcessIdWithApi(processId);

                if (newTaskId != null) {
                    updateTaskByProcessId(processId, newTaskId);
                    updateReviewAndStatus(processId,"Credito aprobado y desembolsado");
                    updateCountReviewsBpm(processId);
                }
                return "";
            } catch (HttpClientErrorException e) {
                String errorMessage = e.getResponseBodyAsString();
                System.err.println("Error during task completion: " + errorMessage);
                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("No task information found for Process ID " + processId);
            return null;
        }
    }

    @BPMNSetterVariables(variables = "countReviewsBpm")
    public void updateReviewAndStatus(String processId, String status) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/credit_request", "postgres", "admin");

        String updateQuery = "UPDATE credit_request SET status = ?, count_reviewcr = count_reviewcr + 1 WHERE process_id = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, status);
            updateStatement.setString(2, processId);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Status updated, and count_reviewcr incremented.");
            } else {
                System.out.println("No records found for the given processId: " + processId);
            }
        }
    }

    public void updateCountReviewsBpm(String processId) {
        // Obtener el nuevo valor de countReviewsBpm desde la base de datos
        long countReviewsBpm = getCountReviewsBpmFromDatabase(processId);

        String camundaUrl = "http://localhost:9000/engine-rest/process-instance/" + processId + "/variables/countReviewsBpm";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("value", countReviewsBpm);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    camundaUrl,
                    HttpMethod.PUT,
                    requestEntity,
                    Void.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("countReviewsBpm updated successfully for processId: " + processId);
            } else {
                System.err.println("Error updating countReviewsBpm for processId " + processId + ". Status code: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            System.err.println("Error updating countReviewsBpm: " + errorMessage);
        }
    }


    private long getCountReviewsBpmFromDatabase(String processId) {
        long countReviewsBpm = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Conectar a la base de datos
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/credit_request", "postgres", "admin");

            // Consulta SQL para obtener countReviewsBpm
            String query = "SELECT count_reviewcr FROM credit_request WHERE process_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, processId);

            // Ejecutar la consulta
            resultSet = preparedStatement.executeQuery();

            // Obtener el resultado
            if (resultSet.next()) {
                countReviewsBpm = resultSet.getLong("count_reviewcr");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener countReviewsBpm desde la base de datos: " + e.getMessage());
        } finally {
            // Cerrar recursos
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return countReviewsBpm;
    }
}
