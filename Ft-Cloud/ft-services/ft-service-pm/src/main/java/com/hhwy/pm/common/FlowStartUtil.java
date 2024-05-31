package com.hhwy.pm.common;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.api.RemoteBpmnService;
import com.hhwy.flowable.domain.NextNodesParam;
import com.hhwy.flowable.domain.NodeInfo;
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
    public static void start(String processDefinitionKey, String businessKey, String tableName, List<String> userNameList, String routeId, String processInstanceName){
        log.info("流程KEY：{}，流程名称：{}，业务id: {}， 表名：{}， 用户列表：{}， 菜单id：{}", processDefinitionKey, processInstanceName, businessKey, tableName, userNameList, routeId);
        NextNodesParam nextNodesParam = new NextNodesParam();
        nextNodesParam.setProcessDefinitionKey(processDefinitionKey);
        //BpmnController  nextNodesForFeign
        R<List<NodeInfo>> r = remoteBpmnService.nextNodesForFeign(nextNodesParam);
        log.info("发起流程响应结果：{}", JSON.toJSONString(r));
        int code = r.getCode();
        if (code != 200) {
            log.error("发起流程失败，获取下一节点实例失败，状态code：{}---响应mas：{}---响应data：{}", r.getCode(), r.getMsg(), r.getData());
            return;
        }
        if (r.getData() == null){
            log.error("发起流程失败，r.getData() == null");
            return;
        }
        String s = JSON.toJSONString(r.getData());
        List<Map> maps = JSON.parseArray(s, Map.class);
        if (CollUtil.isEmpty(maps)){
            log.error("发起流程失败，CollUtil.isEmpty(maps)");
            return;
        }
        Map map = maps.get(0);
        if (null == map) {
            log.error("发起流程失败，null == map");
            return;
        }
        Object nodeId = map.get("nodeId");
        if (nodeId == null)
        {
            log.error("发起流程失败，nodeId == null");
            return;
        }
        StartFlowResource startFlowResource = new StartFlowResource();
        startFlowResource.setProcessDefinitionKey(processDefinitionKey);
        startFlowResource.setBusinessKey(businessKey);
        startFlowResource.setProcessInstanceName(processInstanceName);
//        startFlowResource.setComment();
//        startFlowResource.setFileGroupId();

        Map<String, Object> variableParam = new HashMap<>();
        startFlowResource.setVariables(variableParam);
        variableParam.put("routerId", routeId);
        variableParam.put("tableName", tableName);
        String assginList = "assigneeList_" + nodeId;
        variableParam.put(assginList, userNameList);
        //BpmnController  startAndCompleteFlowForFeign
        R r1 = remoteBpmnService.startAndCompleteFlowForFeign(startFlowResource);
        if (r1.getCode() == 200){
            log.info("流程发起成功，状态code：{}---响应mas：{}---响应data：{}", r1.getCode(), r1.getMsg(), r1.getData());
        }else {
            log.error("流程发起失败，状态code：{}---响应mas：{}---响应data：{}", r1.getCode(), r1.getMsg(), r1.getData());
        }
    }
}