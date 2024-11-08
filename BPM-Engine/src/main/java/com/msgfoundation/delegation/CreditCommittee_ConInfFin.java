package com.msgfoundation.delegation;


import com.msgfoundation.annotations.BPMNGetterVariables;
import com.msgfoundation.annotations.BPMNSetterVariables;
import com.msgfoundation.annotations.BPMNTask;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
@BPMNTask(type = "serviceTask", name = "Consultar información financiera")
public class CreditCommittee_ConInfFin implements JavaDelegate {

    @Value("${CONNECTION_CREDIT_REQUEST}")
    private String connectionDB;
    @Value("${USER_DB}")
    private String userDB;
    @Value("${PASSWORD_DB}")
    private String passwordDB;

    public ResultSet getterVariables(Long codRequest) throws SQLException{

        Connection connection = DriverManager.getConnection(connectionDB, userDB, passwordDB);

        String query = "SELECT couple_savings, house_prices, quota_value FROM credit_request WHERE cod_request = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, codRequest);

        return preparedStatement.executeQuery();
    }

    @BPMNSetterVariables( variables = { "housePrices", "coupleSavings", "quotaValue" })
    public void setterVariables(DelegateExecution execution, ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            long coupleSavings = resultSet.getLong("couple_savings");
            long housePrices = resultSet.getLong("house_prices");
            long quotaValue = resultSet.getLong("quota_value");

            // Aquí asignamos los valores a las variables de salida definidas en el BPMN
            execution.setVariable("coupleSavingsOutput", coupleSavings);
            execution.setVariable("housePricesOutput", housePrices);
            execution.setVariable("quotaValueOutput", quotaValue);
        }
    }
    @Override
    @BPMNGetterVariables( variables = { "codRequest" })
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
