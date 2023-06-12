package com.hhwy.flowable;

import com.hhwy.flowable.core.config.CustomInjectUserTaskInProcessInstanceCmd;
import com.hhwy.flowable.core.config.CustomProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.dynamic.DynamicUserTaskBuilder;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest
public class DynamicBpmnTest {

    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    DynamicBpmnService dynamicBpmnService;


    /**
     * 部署流程
     */
    @Test
    public void deployFlow(){
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("flowable-xml/测试流程.bpmn20.xml")
                .name("测试流程部署")
                .deploy();
        System.out.println("--------部署的相关信息-----------");
        System.out.println("deploy.getId() = " + deploy.getId());
        // 获取流程定义的信息
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId())
                .list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println("-----------流程定义相关的信息--------");
            System.out.println("processDefinition.getId() = " + processDefinition.getId());
        }

    }

    /**
     * 启动流程实例
     */
    @Test
    public void startFlow(){
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById("myProcess:1:3596b318-b718-11ed-b857-70b5e820c95b");
        System.out.println("processInstance.getProcessInstanceId() = " + processInstance.getProcessInstanceId());
    }

    /**
     * 完成任务
     */
    @Test
    public void complete(){
        taskService.complete("455b1b56-b718-11ed-83db-70b5e820c95b");
    }

    /**
     * 动态新增节点任务
     */
    @Test
    public void injectUserTaskFlowable(){
        String processInstanceId = "557e68d0-b714-11ed-b8a8-70b5e820c95b";
        String dynamicUserTaskId = "UserTaskAdd_"+UUID.randomUUID().toString().replaceAll("-","");
        DynamicUserTaskBuilder dynamicUserTaskBuilder = new DynamicUserTaskBuilder();
        dynamicUserTaskBuilder.setId(dynamicUserTaskId);
        dynamicUserTaskBuilder.setName("动态新增节点1");
        dynamicUserTaskBuilder.setAssignee("jzq");
        dynamicBpmnService.injectUserTaskInProcessInstance(processInstanceId,dynamicUserTaskBuilder);
    }

    @Test
    public void injectUserTaskCustom(){
        String processInstaceId = "455771d1-b718-11ed-83db-70b5e820c95b";
        String processDefinitionId = "myProcess:1:3596b318-b718-11ed-b857-70b5e820c95b";
        String dynamicUserId = "ut_"+UUID.randomUUID().toString().replaceAll("-","");
        DynamicUserTaskBuilder dynamicUserTaskBuilder = new DynamicUserTaskBuilder();
        dynamicUserTaskBuilder.setId(dynamicUserId);
        dynamicUserTaskBuilder.setName("新增节点3");
        dynamicUserTaskBuilder.setAssignee("jzq");
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Process process = bpmnModel.getProcesses().get(0);
        try {
            processEngine.getManagementService().executeCommand(
                    new CustomInjectUserTaskInProcessInstanceCmd(processInstaceId, dynamicUserTaskBuilder,process.getFlowElement("usertask2"))
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void myDiagram(){
        String processId = "455771d1-b718-11ed-83db-70b5e820c95b";
        String processDefinitionId;
        // 获取当前的流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        // 如果流程已经结束，则得到结束节点
        if (Objects.isNull(processInstance)) {
            HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processId).singleResult();
            processDefinitionId = pi.getProcessDefinitionId();
        } else {
            // 如果流程没有结束，则取当前活动节点
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processId).singleResult();
            processDefinitionId = pi.getProcessDefinitionId();
        }

        // 获得活动的节点
        List<HistoricActivityInstance> highLightedFlowList = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processId)
                .orderByHistoricActivityInstanceStartTime().asc().list();

        List<String> highLightedFlows = new ArrayList<>();
        List<String> highLightedNodes = new ArrayList<>();
        //高亮线
        for (HistoricActivityInstance tempActivity : highLightedFlowList) {
            if ("sequenceFlow".equals(tempActivity.getActivityType())) {
                //高亮线
                highLightedFlows.add(tempActivity.getActivityId());
            } else {
                //高亮节点
                highLightedNodes.add(tempActivity.getActivityId());
            }
        }
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration configuration = processEngine.getProcessEngineConfiguration();
        //获取自定义图片生成器
        ProcessDiagramGenerator diagramGenerator = new CustomProcessDiagramGenerator();
        InputStream inStream = diagramGenerator
                .generateDiagram(bpmnModel, "png", highLightedNodes, highLightedFlows, configuration.getActivityFontName(),
                        configuration.getLabelFontName(), configuration.getAnnotationFontName(), configuration.getClassLoader(), 1.0, true);

        try {
            FileUtils.copyInputStreamToFile(inStream, new File("C:\\Users\\hhwy-jzq\\Desktop\\test\\6.jpg"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
