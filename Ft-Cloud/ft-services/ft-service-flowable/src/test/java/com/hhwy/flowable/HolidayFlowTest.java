package com.hhwy.flowable;

import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.engine.*;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Attachment;
import org.flowable.engine.task.Comment;
import org.flowable.form.api.FormDeployment;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class HolidayFlowTest {

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    IdentityService identityService;

    @Autowired
    FormService formService;

    @Autowired
    ManagementService managementService;

    @Autowired
    DynamicBpmnService dynamicBpmnService;

    /**
     * 部署流程
     */
    @Test
    public void deployFlow(){
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("flowable-xml/测试流程.bpmn20.xml")
                //.addClasspathResource("flowable-xml/错误结束事件.bpmn20.xml")
                .name("9号流程部署")
                .category("test")
                .tenantId("长沙分公司")
                .deploy();
        System.out.println("--------部署的相关信息-----------");
        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println("deploy.getCategory() = " + deploy.getCategory());
        System.out.println("deploy.getDeploymentTime() = " + deploy.getDeploymentTime());
        System.out.println("deploy.getKey() = " + deploy.getKey());
        System.out.println("deploy.getTenantId() = " + deploy.getTenantId());
        // 获取流程定义的信息
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId())
                .list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println("-----------获取流程定义相关的信息--------");
            System.out.println("processDefinition.getId() = " + processDefinition.getId());
            System.out.println("processDefinition.getName() = " + processDefinition.getName());
            System.out.println("processDefinition.getResourceName() = " + processDefinition.getResourceName());
            System.out.println("processDefinition.getDescription() = " + processDefinition.getDescription());
            System.out.println("processDefinition.getDiagramResourceName() = " + processDefinition.getDiagramResourceName());
            System.out.println("processDefinition.getDeploymentId() = " + processDefinition.getDeploymentId());
        }

    }

    /**
     * 启动流程实例
     */
    @Test
    public void startFlow(){
        Map<String,Object> map = new HashMap<>();
        map.put("employee","启动");
        map.put("days","1天");
        identityService.setAuthenticatedUserId("admin");
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById("j_test_process:1:6e70e4e4-b7fb-11ed-a426-70b5e820c95b", map);
        System.out.println("processInstance.getProcessInstanceId() = " + processInstance.getProcessInstanceId());
    }

    /*
    * 只能会签节点（加签）
    * */
    @Test
    public void addMultiInstanceExecution(){
        Map<String,Object> map = new HashMap<>();
        map.put("employee","启动");
        map.put("days","1天");
        ExecutionEntity execution = (ExecutionEntity) runtimeService.addMultiInstanceExecution("usertask1", "cb1027ca-b509-11ed-8227-70b5e820c95b", map);
    }


    @Test
    public void delegateTask(){
       taskService.delegateTask("78c1b223-b504-11ed-84e6-70b5e820c95b","jzq");
    }


    /**
     * 启动一个表单流程
     */
    @Test
    public void startFormFlow(){
        Map<String,Object> map = new HashMap<>();
        map.put("days","3");
        map.put("reason","世界太大，想出去逛逛");
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById("holiday-form:1:38bf37b1-0c3c-11ed-a7cb-c03c59ad2248", map);
    }

    @Autowired
    private FormRepositoryService formRepositoryService;

    /**
     * 部署表单
     */
    @Test
    public void deployForm(){
        FormDeployment deploy = formRepositoryService.createDeployment()
                .addClasspathResource("test1.json")
                .name("表单测试流程-请假")
                .deploy();
        System.out.println("deploy.getId() = " + deploy.getId());
    }
    @Test
    public void startFormFlow1(){
        Map<String,Object> map = new HashMap<>();
        map.put("days","4");
        map.put("reason","世界太大，想出去逛逛 666");
        ProcessInstance processInstance = runtimeService.startProcessInstanceWithForm("holiday-form:1:38bf37b1-0c3c-11ed-a7cb-c03c59ad2248"
                , null
                , map
                , "请假流程-Form");
        System.out.println("processInstance.getId() = " + processInstance.getId());
    }

    /**
     * 完成任务
     */
    @Test
    public void complete(){
        List<Task> list = taskService.createTaskQuery()
                .processInstanceId("3e8b2cb9-0c3d-11ed-9633-c03c59ad2248")
                //.processDefinitionId("holidayRequest:1:eab99edd-fa9f-11ec-9d85-c03c59ad2248")
                .taskAssignee("zhang")
                .list();

        /*Map<String,Object> map = new HashMap<>();
        map.put("approved",false);*/
        for (Task task : list) {
            taskService.complete(task.getId(),null);
        }
    }

    @Test
    public void completeUser1(){
        Map<String,Object> map = new HashMap<>();
        map.put("employee","user1");
        map.put("days","2天");
        taskService.complete("78c1b223-b504-11ed-84e6-70b5e820c95b",map);
    }

    @Test
    public void resolveTask(){
        Map<String,Object> map = new HashMap<>();
        map.put("employee","user1");
        map.put("days","2天");
        taskService.resolveTask("78c1b223-b504-11ed-84e6-70b5e820c95b",map);
    }

    @Test
    public void completeUser2(){
        Map<String,Object> map = new HashMap<>();
        map.put("employee","user2");
        map.put("days","3天");
        taskService.complete("1bb67172-b3e4-11ed-b84c-70b5e820c95b",map,true);
    }

    @Test
    public void completeUser3(){
        Map<String,Object> map = new HashMap<>();
        map.put("employee","user3");
        map.put("days","4天");
        taskService.complete("b19618d7-b3e5-11ed-9dc2-70b5e820c95b",map,true);
    }

    @Test
    public void attachment(){
        taskService.createAttachment("png", "26cbe4f2-b3e6-11ed-9dda-70b5e820c95b", "8e5f4650-b3e3-11ed-95b8-70b5e820c95b", "燕子.png", "附件123", "https://img2.baidu.com/it/u=3202947311,1179654885&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500");
    }

    @Test
    public void addComment(){
        taskService.addComment( "26cbe4f2-b3e6-11ed-9dda-70b5e820c95b", "8e5f4650-b3e3-11ed-95b8-70b5e820c95b", "bbbbzzz" );
    }

    @Test
    public void getTaskAttachments(){
        List<Attachment> taskAttachments = taskService.getTaskAttachments("26cbe4f2-b3e6-11ed-9dda-70b5e820c95b");
        for (Attachment taskAttachment : taskAttachments) {
            System.err.println();
        }
    }

    @Test
    public void getTaskComments(){
        List<Comment> taskComments = taskService.getTaskComments("26cbe4f2-b3e6-11ed-9dda-70b5e820c95b");
        for (Comment taskComment : taskComments) {
            System.err.println();
        }
    }

    @Test
    public void tasks(){
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().includeProcessVariables().includeTaskLocalVariables().list();
        for (HistoricTaskInstance historicTaskInstance : list) {
            Map<String, Object> processVariables = historicTaskInstance.getProcessVariables();
            Map<String, Object> taskLocalVariables = historicTaskInstance.getTaskLocalVariables();

            System.err.println();
        }

        List<Task> list1 = taskService.createTaskQuery().list();
        for (Task task : list1) {
            Map<String, Object> processVariables = task.getProcessVariables();
            System.err.println();
        }
    }
    /**
     * 根据Task编号来查看表单数据
     */
    @Test
    void getTaskFormData(){
        String taskId = "a42b6943-0c3d-11ed-a648-c03c59ad2248";
        TaskFormData taskFormData = processEngine.getFormService().getTaskFormData(taskId);
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        System.out.println("formProperties.size() = " + formProperties.size());
        for (FormProperty formProperty : formProperties) {
            System.out.println("formProperty.getId() = " + formProperty.getId());
            System.out.println("formProperty.getName() = " + formProperty.getName());
            System.out.println("formProperty.getValue() = " + formProperty.getValue());
        }
        /*TaskFormData taskFormData = processEngine.getFormService().getTaskFormData(taskId);
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        for (FormProperty formProperty : formProperties) {
            System.out.println("formProperty.getId() = " + formProperty.getId());
            System.out.println("formProperty.getName() = " + formProperty.getName());
            System.out.println("formProperty.getValue() = " + formProperty.getValue());
        }*/
    }

    @Test
    public void deleteProcess(){
        repositoryService.deleteDeployment("a3627941-ffa1-11ec-9ac8-c03c59ad2248",true);
    }

    @Test
    public void isFirstTask() {
        Task task = taskService.createTaskQuery().taskId("f1a11c51-b3ea-11ed-a6eb-70b5e820c95b").singleResult();
        String processDefinitionId = task.getProcessDefinitionId();
        String taskDefinitionKey = task.getTaskDefinitionKey();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        List<SequenceFlow> userTaskSequenceFlows = ((UserTask) bpmnModel.getMainProcess().getFlowElement(taskDefinitionKey)).getIncomingFlows();
        for (SequenceFlow userTaskSequenceFlow : userTaskSequenceFlows) {
            if(userTaskSequenceFlow.getSourceFlowElement() instanceof StartEvent){
                System.err.println(true);
            }
        }
    }

    /**
     * BnpmModle:讲解
     */
    @Test
    public void bpmnModel1() {


        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId("myProcess:1:c79c1422-b3e9-11ed-adfe-70b5e820c95b").singleResult();
        String resourceName = processDefinition.getResourceName();
        // 获取对应流程的BpmnModel文件
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        // 获取相关的配置信息
        Process mainProcess = bpmnModel.getMainProcess();
        System.out.println("mainProcess.getId() = " + mainProcess.getId());
        System.out.println("mainProcess.getName() = " + mainProcess.getName());
    }

    /**
     * BnpmModle:讲解
     */
    @Test
    public void bpmnModel(){
        /*Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentId("0f992136-fcd5-11ec-9bae-c03c59ad2248")
                .singleResult();
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();

        DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();*/
        // 实例化一个 BpmnModel 对象
        BpmnModel model = new BpmnModel();
        // 开始节点
        StartEvent startEvent = new StartEvent();
        startEvent.setId("holiday-bpmnModel");
        startEvent.setName("自定义流程文件");

        // 普通的 UserTask
        UserTask userTask = new UserTask();
        userTask.setId("userTask1");
        userTask.setName("userTaskName1");


        // 结束节点
        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent1");
        endEvent.setName("end");

        // 连接线
        // 第一条连接下  开始节点  -- > 用户任务
        SequenceFlow sequenceFlow1 = new SequenceFlow();
        sequenceFlow1.setId("sequence1");
        sequenceFlow1.setName("sequence1Name");
        // 起点
        sequenceFlow1.setSourceRef("holiday-bpmnModel");
        // 连接点
        sequenceFlow1.setTargetRef("userTask1");

        // 第二条连接线
        SequenceFlow sequenceFlow2 = new SequenceFlow();
        sequenceFlow2.setId("sequence2");
        sequenceFlow2.setName("sequnce2Name");
        sequenceFlow2.setSourceRef("userTask1");
        sequenceFlow2.setTargetRef("endEvent1");

        List<SequenceFlow> startOutgoingFlows = new ArrayList<>();
        startOutgoingFlows.add(sequenceFlow1);
        startEvent.setOutgoingFlows(startOutgoingFlows);

        List<SequenceFlow> endOutgoingFlows = new ArrayList<>();
        endOutgoingFlows.add(sequenceFlow2);
        // 用户任务的出线
        userTask.setOutgoingFlows(endOutgoingFlows);
        // 用户任务的进线
        userTask.setIncomingFlows(startOutgoingFlows);
        endEvent.setIncomingFlows(endOutgoingFlows);

        Process process = new Process();
        process.setId("process1");
        process.addFlowElement(startEvent);
        process.addFlowElement(sequenceFlow1);
        process.addFlowElement(userTask);
        process.addFlowElement(sequenceFlow2);
        process.addFlowElement(endEvent);
        process.setExecutable(true);

        model.addProcess(process);

        // 讲创建的对象转换为xml文件
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] bytes = bpmnXMLConverter.convertToXML(model);
        String xmlString = new String(bytes);
        System.out.println(xmlString);

        // 验证BpmnModel是否正确
        ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
        ProcessValidator defaultProcessValidator = processValidatorFactory.createDefaultProcessValidator();
        List<ValidationError> validate = defaultProcessValidator.validate(model);
        for (ValidationError validationError : validate) {
            // 没有打印信息说明 验证是成功的
            System.out.println("validate = " + validate);
        }

        // 部署流程
        Deployment deployment = repositoryService.createDeployment()
                .addBpmnModel("自定义BpmnModel", model)
                .name("请假流程-BpmnModel")
                .deploy();
        System.out.println("deployment.getId() = " + deployment.getId());


    }



}
