package com.hhwy.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.form.api.FormDeployment;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.model.FormField;
import org.flowable.form.model.SimpleFormModel;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class FormFlowTest {

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    FormRepositoryService formRepositoryService;

    /**
     * 1.部署流程
     * 2.部署表单
     * 3.启动带有表单的流程-->创建了对应的流程实例
     */
    @Test
    public void deployFormFlow(){
        // 1.获取需要部署的form文件
        String json = "{\"name\":\"报销流程表单\",\"key\":\"expenseAccountForm\",\"version\":0,\"fields\":[{\"fieldType\":\"FormField\",\"id\":\"amount\",\"name\":\"报销金额\",\"type\":\"integer\",\"value\":null,\"required\":true,\"readOnly\":false,\"overrideId\":true,\"placeholder\":\"0\",\"layout\":null},{\"fieldType\":\"FormField\",\"id\":\"reason\",\"name\":\"报销原因\",\"type\":\"text\",\"value\":null,\"required\":false,\"readOnly\":false,\"overrideId\":true,\"placeholder\":null,\"layout\":null},{\"fieldType\":\"FormField\",\"id\":\"expenseDate\",\"name\":\"报销日期\",\"type\":\"date\",\"value\":null,\"required\":false,\"readOnly\":false,\"overrideId\":true,\"placeholder\":null,\"layout\":null}],\"outcomes\":[]}";
        FormDeployment deploy = formRepositoryService.createDeployment()
                .addString("报销流程表单.form", json)
                .name("报销表单")
                .deploy();
        System.out.println("deploy.getId() = " + deploy.getId());
    }

    /**
     * 启动一个流程实例
     */
    @Test
    public void startFormFlow(){
        String processDefinitionId = "holiday:2:836fb728-1471-11ed-b7bb-c03c59ad2248";
//        Map<String,Object> map = new HashMap<>();
//        map.put("days",4);
//        map.put("reason","测试数据");
//        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceWithForm(processDefinitionId
//                , "Reject", map, "测试表单的outcom");
        ProcessInstance processInstance = processEngine
                .getRuntimeService()
                .startProcessInstanceById(processDefinitionId);
        System.out.println("processInstance.getId() = " + processInstance.getId());
    }


    @Test
    public void updateFormData(){
        Map<String,Object> map = new HashMap<>();
        map.put("days",6);
        map.put("reason","测试数据");
        // 设置Task对应的表单的数据
        processEngine.getTaskService().setVariables("a6188d21-1471-11ed-ba0f-c03c59ad2248",map);
    }


    /**
     * 查看流程对应的表单数据
     */
    @Test
    public void getTaskFormData(){
        String processDefinitionId = "holiday:1:f358d79e-146d-11ed-b7bb-c03c59ad2248";
        Task task = processEngine.getTaskService().createTaskQuery()
                .processDefinitionId(processDefinitionId)
                .taskAssignee("zhang")
                .singleResult();
        // 查看Task对应的表单数据
        String processInstanceId = "557e6c50-146e-11ed-89fd-c03c59ad2248";
        FormInfo formInfo = processEngine.getRuntimeService().getStartFormModel(processDefinitionId, processInstanceId);
        System.out.println("formInfo.getKey() = " + formInfo.getKey());
        System.out.println("formInfo.getName() = " + formInfo.getName());
        System.out.println("formInfo.getDescription() = " + formInfo.getDescription());
        SimpleFormModel formModel = (SimpleFormModel) formInfo.getFormModel();
        List<FormField> fields = formModel.getFields();
        for (FormField field : fields) {
            System.out.println("field.getId() = " + field.getId());
            System.out.println("field.getName() = " + field.getName());
            System.out.println("field.getValue() = " + field.getValue());
        }
    }

    @Test
    public void completeTask(){
        String processDefinitionId = "holiday:2:836fb728-1471-11ed-b7bb-c03c59ad2248";
        Task task = processEngine.getTaskService().createTaskQuery()
                .processDefinitionId(processDefinitionId)
                .taskAssignee("zhang")
                .singleResult();
        Map<String,Object> map = new HashMap<>();
        map.put("days",6);
        map.put("reason","测试数据");
        processEngine.getTaskService().completeTaskWithForm(task.getId()
                ,"0dac0a01-146e-11ed-b7bb-c03c59ad2248"
                ,"Reject"
                ,map);
        //processEngine.getTaskService().complete(task.getId());
    }

    /**
     * 查看流程对应的表单数据
     */
    @Test
    public void getTaskFormData1(){
        String processDefinitionId = "holiday:1:f358d79e-146d-11ed-b7bb-c03c59ad2248";
        Task task = processEngine.getTaskService().createTaskQuery()
                .processDefinitionId(processDefinitionId)
                .taskAssignee("zhang")
                .singleResult();
        // 查看Task对应的表单数据
        FormInfo formInfo = processEngine.getTaskService().getTaskFormModel(task.getId());
        System.out.println("formInfo.getKey() = " + formInfo.getKey());
        System.out.println("formInfo.getName() = " + formInfo.getName());
        System.out.println("formInfo.getDescription() = " + formInfo.getDescription());
        SimpleFormModel formModel = (SimpleFormModel) formInfo.getFormModel();
        List<FormField> fields = formModel.getFields();
        for (FormField field : fields) {
            System.out.println("field.getId() = " + field.getId());
            System.out.println("field.getName() = " + field.getName());
            System.out.println("field.getValue() = " + field.getValue());
        }
    }
}
