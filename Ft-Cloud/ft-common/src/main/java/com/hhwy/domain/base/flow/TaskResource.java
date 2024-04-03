package com.hhwy.domain.base.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class TaskResource {
    private String businessKey;
    private String taskId;
    private String taskName;
    private String taskDefinitionKey;
    private String assignee;
    private String assigneeNickName;
    private String processStartUser;
    private String processStartUserNickName;
    private String taskExecutionId;
    private String processNumber;
    private String processInstanceId;
    private String processInstanceName;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private int processDefinitionVersion;
    private String processDeployId;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date processStartTime;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createTime;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date endTime;
    private String duration;
    //    private List<Comment> commentList;
//    private ActFormModel actFormModel;
//    private List<ActFormPerm> actFormPermList;
    private Map<String, Object> variables;
    private String fileGroupId;
    private String taskStatus;
    private String handleComponentPath;
    private String todoTaskHandleComponentPath;
    private String doneTaskHandleComponentPath;
    private String myStartedProcessHandleComponentPath;
    private String detailComponentPath;
    private String pageRoute;
    private Map<String, List<String>> customProperties;
}