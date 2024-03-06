package com.MSGFoundation.service;

import com.MSGFoundation.dto.CreditInfoDTO;
import com.MSGFoundation.dto.TaskInfo;
import com.MSGFoundation.model.CreditRequest;

import java.sql.SQLException;

public interface MarriedCoupleService {
    String startProcessInstance(CreditInfoDTO creditInfoDTO);
    void setAssignee(String taskId, String userId);
    TaskInfo getTaskInfoByProcessId(String processId);
    TaskInfo getTaskInfoByProcessIdWithApi(String processId);
    String updateProcessVariables(String processId, CreditRequest creditRequest);
    String completeTask(String processId);
    void messageEvent(String processId);
    Long getCountReview(String processId);
    void updateReviewAndStatus(String processId, String status) throws SQLException;

}
