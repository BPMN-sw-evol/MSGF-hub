package com.msgfoundation.delegation;


import com.msgfoundation.annotations.BPMNGetterVariables;
import com.msgfoundation.annotations.BPMNSetterVariables;
import com.msgfoundation.annotations.BPMNTask;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;

@BPMNTask(type="ServiceTask", name="Consultar información financiera")
public class DatabaseServiceTaskDelegate implements JavaDelegate {
    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUser;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    @BPMNGetterVariables(variables = {"quotaValue", "housePrices", "coupleSavings"})
    public ResultSet getterVariables(Long codRequest) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://credit_request_db:5432/credit_request", "postgres", "admin");

        String query = "SELECT couple_savings, house_prices, quota_value FROM credit_request WHERE cod_request = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, codRequest);

        return preparedStatement.executeQuery();
    }

    @BPMNSetterVariables(variables = {"quotaValue", "housePrices", "coupleSavings"})
    public void setterVariables(DelegateExecution execution, ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            long coupleSavings = resultSet.getLong("couple_savings");
            long housesPrices = resultSet.getLong("house_prices");
            long quotaValue = resultSet.getLong("quota_value");

            System.out.println("variables obtenidas: ");
            System.out.println("Couple Savings: " + coupleSavings);
            System.out.println("Houses Prices: " + housesPrices);
            System.out.println("Quota Value: " + quotaValue);

            // Aquí asignamos los valores a las variables de salida definidas en el BPMN
            execution.setVariable("coupleSavingsOutput", coupleSavings);
            execution.setVariable("housePricesOutput", housesPrices);
            execution.setVariable("quotaValueOutput", quotaValue);
        }
    }
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long codRequest = (Long) execution.getProcessInstance().getVariables().get("codRequest");
        if (codRequest != null) {
            ResultSet resultSet = getterVariables(codRequest);
            setterVariables(execution,resultSet);
        } else {
            System.out.println("La variable codRequest no está definida o es nula.");
        }
    }
}
