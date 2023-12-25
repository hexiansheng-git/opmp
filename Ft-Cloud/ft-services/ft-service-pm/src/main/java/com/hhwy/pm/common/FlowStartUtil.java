package com.hhwy.pm.common;

import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.api.RemoteBpmnService;
import com.hhwy.flowable.domain.NextNodesParam;
import com.hhwy.flowable.domain.StartFlowResource;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FlowStartUtil {

    static RemoteBpmnService remoteBpmnService = SpringUtils.getBean(RemoteBpmnService.class);

    /***
     * 功能描述: 发起流程
     * @param processDefinitionKey 流程key
     * @param businessKey 业务id
     * @param tableName 业务表名
     * @param userNameList 下一节点审批人
     * @param routeId 菜单id
     * 作者: fushudong
     * 时间: 2023/12/25
     */
    public static void start(String processDefinitionKey, String businessKey, String tableName, List<String> userNameList, String routeId){
        NextNodesParam nextNodesParam = new NextNodesParam();
        nextNodesParam.setProcessDefinitionKey(processDefinitionKey);
        R r = remoteBpmnService.nextNodesForFeign(nextNodesParam);
        int code = r.getCode();
        if (code != 200) {
            log.error("纠偏措施制定，发起流程失败，状态code：{}---响应mas：{}---响应data：{}", r.getCode(), r.getMsg(), r.getData());
            return;
        }
        StartFlowResource startFlowResource = new StartFlowResource();
        startFlowResource.setProcessDefinitionKey(processDefinitionKey);
        startFlowResource.setBusinessKey(businessKey);
        startFlowResource.setProcessInstanceName("纠偏措施指定审批流程批");
//        startFlowResource.setComment();
//        startFlowResource.setFileGroupId();

        Map<String, Object> variableParam = new HashMap<>();
        startFlowResource.setVariables(variableParam);
        variableParam.put("routeId", routeId);
        variableParam.put("tableName", tableName);
        String assginList = "assigneeList_" + r.getData();
        variableParam.put(assginList, userNameList);
        R r1 = remoteBpmnService.startAndCompleteFlowForFeign(startFlowResource);
        if (r1.getCode() == 200){
            log.info("流程发起成功");
        }else {
            log.error("流程发起失败");
        }
    }
}