package com.MSGFCentralSys.MSGFCentralSys.dto;

import lombok.Data;

@Data
public class TaskInfo {
    private String processId;
    private String taskId;
    private String taskName;
    private String taskAssignee;
}
