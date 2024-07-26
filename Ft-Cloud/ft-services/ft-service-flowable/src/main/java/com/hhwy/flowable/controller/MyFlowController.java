package com.hhwy.flowable.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.flowable.core.domain.TaskResource;
import com.hhwy.flowable.core.service.IBpmnService;
import com.hhwy.flowable.service.INodeTaskService;
import org.apache.commons.lang3.ObjectUtils;
import org.flowable.engine.HistoryService;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/myFlow")
public class MyFlowController {

    @Autowired
    private INodeTaskService nodeTaskService;
    @Autowired
    private IBpmnService bpmnService;
    @Autowired
    private HistoryService historyService;

    @RequestMapping("/isNowfirstNode")
    public AjaxResult isNowfirstNode(@RequestParam("insId") String insId){
        String firstName = nodeTaskService.isNowfirstNode(insId);
        return AjaxResult.success("",firstName);
    }

    //获取任务信息
    @RequestMapping("/taskInfoDetail")
    public AjaxResult taskInfoDetail(@RequestParam("taskId") String taskId){
        TaskResource taskResource = bpmnService.taskInfoDetail(taskId);
        return AjaxResult.success(taskResource);
    }


    //获取任务信息
    @RequestMapping("/ttt111111")
    public AjaxResult taskInfoDetail1(@RequestParam("taskId") String taskId){
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).includeProcessVariables().singleResult();
        return AjaxResult.success(task);
    }



}
