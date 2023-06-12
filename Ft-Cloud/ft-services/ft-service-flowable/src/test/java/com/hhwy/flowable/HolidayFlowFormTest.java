package com.hhwy.flowable;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.form.api.FormDeployment;
import org.flowable.form.api.FormRepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class HolidayFlowFormTest {

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

    @Autowired
    FormRepositoryService formRepositoryService;


    /**
     * 部署流程：
     */
    @Test
    public void deploy(){
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("flowable-xml/表单.bpmn20.xml")
                .name("表单")
                .deploy();
        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println("deploy.getName() = " + deploy.getName());
        System.out.println("部署开始的时间：" + new Date());
    }

    @Test
    public void deployForm() throws Exception{

        FormDeployment formDeployment = formRepositoryService.createDeployment()
                .addClasspathResource("form/holiday.form")
                .name("test")
                .parentDeploymentId("4cf236a4-b3e8-11ed-ace5-70b5e820c95b")
                .deploy();
        System.out.println("formDeployment.getId() = " + formDeployment.getId());
    }

    @Test
    public void startFlow(){
        Map<String,Object> map = new HashMap<>();
        map.put("days","6");
        identityService.setAuthenticatedUserId("admin");
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById("myProcess:1:c79c1422-b3e9-11ed-adfe-70b5e820c95b");
        System.out.println("processInstance.getProcessInstanceId() = " + processInstance.getProcessInstanceId());
    }

    @Test
    public void completeUser2(){
        Map<String,Object> map = new HashMap<>();
        map.put("days","1");
        taskService.complete("f1a11c51-b3ea-11ed-a6eb-70b5e820c95b",map,true);
    }




    @Test
    void startTask(){
        Map<String,Object> map = new HashMap<>();
        map.put("days","4");
        map.put("startTime","20220404");
        map.put("reason","出去玩玩");
        ProcessInstance processInstance = runtimeService.startProcessInstanceWithForm(
                "myProcess:1:c79c1422-b3e9-11ed-adfe-70b5e820c95b"
                , null
                , map
                , "请假流程");
        String id = processInstance.getId();
        System.out.println("id = " + id);

    }

}
