package com.msgfoundation.delegation;

import com.msgfoundation.annotations.BPMNGetterVariables;
import com.msgfoundation.annotations.BPMNTask;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service("activateForm")
@BPMNTask(type = "serviceTask", name = "Habilitar edición formulario")
public class CreditAnalyst_HabEdiFor implements JavaDelegate {

    @Value("${CONNECTION_CREDIT_REQUEST}")
    private String connectionDB;
    @Value("${USER_DB}")
    private String userDB;
    @Value("${PASSWORD_DB}")
    private String passwordDB;

    @Override
    @BPMNGetterVariables( variables = { "codRequest" })
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long codRequest = (Long) delegateExecution.getProcessInstance().getVariables().get("codRequest");
        if (codRequest != null) {
            updateStatusToDraft(codRequest); 
        }else{
            System.out.println("CodRequest is not defined or is invalid");
        }

        delegateExecution.setProcessBusinessKey(delegateExecution.getProcessInstanceId());
    }

    private void updateStatusToDraft(Long codRequest) throws SQLException {
        System.out.println("Your credit status has been updated");
        Connection connection = DriverManager.getConnection(connectionDB, userDB, passwordDB);

        String updateQuery = "UPDATE credit_request SET status = 'DRAFT' WHERE cod_request = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setLong(1, codRequest);
            updateStatement.executeUpdate();
        }
    }
}
