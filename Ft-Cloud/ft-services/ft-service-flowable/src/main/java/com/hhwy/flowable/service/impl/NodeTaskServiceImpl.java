package com.hhwy.flowable.service.impl;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.flowable.service.INodeTaskService;
import org.apache.commons.collections4.CollectionUtils;
import org.flowable.bpmn.model.Activity;
import org.flowable.cmmn.engine.impl.process.ProcessInstanceService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ActivityInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NodeTaskServiceImpl implements INodeTaskService {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;


    @Override
    public String isNowfirstNode(String insId) {
        if(StringUtils.isBlank(insId))
            return null;
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(insId).list();
        if(CollectionUtils.isEmpty(taskList))
            return null;
        Task task = taskList.get(0);
        String firstId = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(insId)
                .activityType("userTask")
                .orderByHistoricActivityInstanceStartTime()
                .list().get(0).getActivityId();
        ActivityInstance activityInstance = runtimeService.createActivityInstanceQuery().processInstanceId(insId).executionId(task.getExecutionId()).list().get(0);
        if(firstId.equalsIgnoreCase(activityInstance.getActivityId())){
            return activityInstance.getAssignee();
        }
        return null;
    }
}
