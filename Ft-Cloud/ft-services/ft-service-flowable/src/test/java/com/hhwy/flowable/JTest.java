package com.hhwy.flowable;

import com.hhwy.common.core.utils.bean.BeanUtils;
import com.hhwy.flowable.core.cmd.ExpressionCmd;
import com.hhwy.flowable.core.config.CustomInjectUserTaskInProcessInstanceCmd;
import com.hhwy.flowable.core.config.CustomProcessDiagramGenerator;
import com.hhwy.flowable.core.domain.*;
import com.hhwy.flowable.core.service.impl.BpmnServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.ProcessEngineImpl;
import org.flowable.engine.impl.dynamic.DynamicUserTaskBuilder;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class JTest {

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

    @Autowired
    BpmnServiceImpl bpmnService;

    /**
     * 部署流程
     */
    @Test
    public void deployFlow() {
        String resource = "bpmn/temp.bpmn";
        String resource2 = "bpmn/测试流程.bpmn20.xml";
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource(resource)
                .name("测试流程部署")
                .deploy();
        System.err.println("--------部署的相关信息-----------");
        System.err.println("deploy.getId() = " + deploy.getId());
        // 获取流程定义的信息
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId())
                .list();
        for (ProcessDefinition processDefinition : list) {
            System.err.println("-----------流程定义相关的信息--------");
            System.err.println("processDefinition.getId() = " + processDefinition.getId());
        }
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startFlow() {
        Map<String,Object> map = new HashMap<>();
//        List<String> assigneeList = new ArrayList<>();
//        assigneeList.add("ww");
//        assigneeList.add("zs");
//        map.put("assigneeList",assigneeList);
        map.put("count",2);
        map.put("day",2);
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById("p_001:1:b418c3fc-cece-11ed-b052-70b5e820c95b",map);
        System.err.println("processInstance.getProcessInstanceId() = " + processInstance.getProcessInstanceId());
    }

    @Test
    public void complete() {
        Map<String,Object> map = new HashMap<>();
        List<String> assigneeList = new ArrayList<>();
        assigneeList.add("c");
        assigneeList.add("d");
        //map.put("assigneeList",assigneeList);
        map.put("count",7);
        map.put("day",7);
        String taskId = "d70dc290-cee1-11ed-b887-70b5e820c95b";
        CompleteTaskResource completeTaskResource = new CompleteTaskResource();
        completeTaskResource.setTaskId(taskId);
        completeTaskResource.setVariables(map);
        bpmnService.completeTask(completeTaskResource);
    }

    @Test
    public void backTaskToPre( ) {
        String taskId = "f13a37f8-cecf-11ed-95b9-70b5e820c95b";
        BackTaskToPreResource backTaskToPreResource = new BackTaskToPreResource();
        backTaskToPreResource.setCurrentTaskId(taskId);
        bpmnService.backTaskToPre(backTaskToPreResource);
    }

    @Test
    public void backTaskToAnyNode( ) {
        BackTaskToAnyNodeResource backTaskToAnyNodeResource = new BackTaskToAnyNodeResource();
        backTaskToAnyNodeResource.setCurrentTaskId("f77aff7d-cee1-11ed-921d-70b5e820c95b");
        backTaskToAnyNodeResource.setTargetNodeId("sid-7129DC25-D64A-42AD-B5DE-804C83A6F549");
        bpmnService.backTaskToAnyNode(backTaskToAnyNodeResource);
    }

    @Test
    public void recallTask( ) {
        String taskId = "f13a37f8-cecf-11ed-95b9-70b5e820c95b";
        RecallTaskResource recallTaskResource = new RecallTaskResource();
        List<String> taskIds = new ArrayList<>();
        taskIds.add(taskId);
        recallTaskResource.setPreTaskId(taskId);
        bpmnService.recallTask(recallTaskResource);
    }

    @Test
    public void claim() {
        taskService.claim("06a41853-bc01-11ed-80f2-70b5e820c95b","jzq");
    }

    /**
     * 完成任务
     */
    @Test
    public void complete2() {
        Map<String,Object> map = new HashMap<>();
        List<String> assigneeList = new ArrayList<>();
        assigneeList.add("ww");
        assigneeList.add("dd");
        map.put("assigneeList",assigneeList);
        map.put("count",2);
        map.put("day",2);
        taskService.complete("1f495c56-cc43-11ed-8def-70b5e820c95b",map);
        try {
            TimeUnit.HOURS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void completeAssigneeList() {
        Map<String,Object> map = new HashMap<>();
        List<String> assigneeList = new ArrayList<>();
        assigneeList.add("ww");
        map.put("assigneeList",assigneeList);
        taskService.complete("836c4151-bcd1-11ed-98ee-70b5e820c95b",map);
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void completeNoParam() {
        taskService.complete("659ebc9a-bd92-11ed-b87b-70b5e820c95b");
    }

    /**
     * 动态新增节点任务
     */
    @Test
    public void injectUserTaskFlowable() {
        String processInstanceId = "a403b4c1-b731-11ed-9414-70b5e820c95b";
        String dynamicUserTaskId = "ut_" + UUID.randomUUID().toString().replaceAll("-", "");
        DynamicUserTaskBuilder dynamicUserTaskBuilder = new DynamicUserTaskBuilder();
        dynamicUserTaskBuilder.setId(dynamicUserTaskId);
        dynamicUserTaskBuilder.setName("动态新增节点");
        dynamicUserTaskBuilder.setAssignee("jzq");
        dynamicBpmnService.injectUserTaskInProcessInstance(processInstanceId, dynamicUserTaskBuilder);
    }

    @Test
    public void injectUserTaskCustom() {
        String processInstanceId = "885e9e79-b7fb-11ed-b395-70b5e820c95b";
        String processDefinitionId = "j_test_process:1:6e70e4e4-b7fb-11ed-a426-70b5e820c95b";
        String dynamicUserId = "ut_" + UUID.randomUUID().toString().replaceAll("-", "");
        DynamicUserTaskBuilder dynamicUserTaskBuilder = new DynamicUserTaskBuilder();
        dynamicUserTaskBuilder.setId(dynamicUserId);
        dynamicUserTaskBuilder.setName("动态新增节点");
        dynamicUserTaskBuilder.setAssignee("jzq");
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Process process = bpmnModel.getProcesses().get(0);
        try {
            processEngine.getManagementService().executeCommand(
                    new CustomInjectUserTaskInProcessInstanceCmd(
                            processInstanceId,
                            dynamicUserTaskBuilder,
                            process.getFlowElement("sid-7ECBEF36-8115-4A40-A97F-B2DAD7BA4E5A", true)
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void injectUserTaskCustomByTask() {
        String taskId = "9c5d24c4-bcd1-11ed-bccb-70b5e820c95b";
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String dynamicUserId = "ut_" + UUID.randomUUID().toString().replaceAll("-", "");
        DynamicUserTaskBuilder dynamicUserTaskBuilder = new DynamicUserTaskBuilder();
        dynamicUserTaskBuilder.setId(dynamicUserId);
        dynamicUserTaskBuilder.setName("动态新增节点");
        dynamicUserTaskBuilder.setAssignee("jzq");
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        Process process = bpmnModel.getProcesses().get(0);
        try {
            processEngine.getManagementService().executeCommand(
                    new CustomInjectUserTaskInProcessInstanceCmd(
                            task.getProcessInstanceId(),
                            dynamicUserTaskBuilder,
                            process.getFlowElement(task.getTaskDefinitionKey(), true)
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void myDiagram() {
        String processInstanceId = "8218b558-ce0c-11ed-90f3-70b5e820c95b";
        String processDefinitionId;
        // 获取当前的流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        // 如果流程已经结束，则得到结束节点
        if (Objects.isNull(processInstance)) {
            HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            processDefinitionId = pi.getProcessDefinitionId();
        } else {
            // 如果流程没有结束，则取当前活动节点
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            processDefinitionId = pi.getProcessDefinitionId();
        }

        // 获得活动的节点
        List<HistoricActivityInstance> highLightedFlowList = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                //.finished()
                .orderByHistoricActivityInstanceStartTime().asc().list();
        //List<HistoricTaskInstance> list1 = historyService.createHistoricTaskInstanceQuery().processInstanceId("").finished().list();
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
                        configuration.getLabelFontName(), configuration.getAnnotationFontName(), configuration.getClassLoader(), 1.0, false);
        try {
            FileUtils.copyInputStreamToFile(inStream, new File("C:\\Users\\hhwy-jzq\\Desktop\\test\\temp.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void diagramInputStream() {
        String processDefinitionId = "3c1acf8d-be29-11ed-8ab4-70b5e820c95b";
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        new BpmnAutoLayout(bpmnModel).execute();
        ProcessEngineConfiguration configuration = processEngine.getProcessEngineConfiguration();
        //获取自定义图片生成器
        ProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        InputStream inStream = diagramGenerator
                .generateDiagram(bpmnModel, "png", new ArrayList<>(), new ArrayList<>(), configuration.getActivityFontName(),
                        configuration.getLabelFontName(), configuration.getAnnotationFontName(), configuration.getClassLoader(), 1, true);
        try {
            FileUtils.copyInputStreamToFile(inStream, new File("C:\\Users\\hhwy-jzq\\Desktop\\test\\98.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void forwardTask() {
        String taskId = "2ecd203e-be1c-11ed-9ae5-70b5e820c95b";
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        System.err.println();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        // 获取当前节点
        FlowNode currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement("inclusiveGateway_c54a66ad6fa24257aead467a5f77a49a_start");
        Optional<SequenceFlow> sequenceFlowOpt = currentFlowNode.getOutgoingFlows().stream().filter(item -> {
            try {
                return processEngine.getManagementService().executeCommand(new ExpressionCmd(runtimeService, ((ProcessEngineImpl) processEngine).getProcessEngineConfiguration(), task.getProcessInstanceId(), item.getConditionExpression(), taskService.getVariables(taskId)));
            } catch (Exception e) {
                return false;
            }
        }).findFirst();

        System.err.println();
    }

    @Test
    public void calculateFlowPath() {
        String taskId = "5c039ae8-be29-11ed-9670-70b5e820c95b";
        List<FlowElement> flowElements = bpmnService.calculateFlowPath(taskId);
        System.err.println();
    }

    @Test
    public void calculateNextTaskNode() {
        String taskId = "5c039ae8-be29-11ed-9670-70b5e820c95b";
        List<FlowElement> flowElements = bpmnService.calculateFlowPath(taskId);
        System.err.println();
    }

    @Test
    public void test() {
        List<Task> taskList = taskService.createTaskQuery().list();
        List<TaskResource> taskResourceList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(taskList)) {
            taskList.stream().forEach(task -> {
                TaskResource taskResource = new TaskResource();
                BeanUtils.copyProperties(task,taskResource);
                taskResourceList.add(taskResource);
            });
        }
    }


}
