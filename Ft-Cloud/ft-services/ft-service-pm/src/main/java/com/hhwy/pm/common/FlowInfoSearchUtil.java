package com.hhwy.pm.common;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.enums.FlowStatusEnum;
import com.hhwy.feign.service.FlowServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.flowable.api.RemoteBpmnService;
import com.hhwy.pm.common.mapper.FlowInfoMapper;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import jodd.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.SetUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能：流程信息获取
 * 作者: fushudong
 * 时间: 2023/09/04
 */
@Log4j2
public class FlowInfoSearchUtil {

    static FlowInfoMapper flowInfoMapper = SpringUtils.getBean(FlowInfoMapper.class);
    static SystemServiceApi systemServiceApi = SpringUtils.getBean(SystemServiceApi.class);
    static SystemApiService systemApiService= SpringUtils.getBean(SystemApiService.class);
    static FlowServiceApi flowServiceApi= SpringUtils.getBean(FlowServiceApi.class);
    static RemoteBpmnService remoteBpmnService = SpringUtils.getBean(RemoteBpmnService.class);
    
    /***
     * 功能描述: 查询流程信息
     * 台账页使用
     * @param list
     * @param flowEnum
     * 作者: fushudong
     * 时间: 2023/9/4
     */
    public static <T extends CommonBaseEntity> List<T> getFlowInfo(List<T> list, FlowEnum flowEnum){
        if(CollectionUtils.isEmpty(list) || flowEnum == null || StringUtil.isBlank(flowEnum.getTableName()))
            return list;
        String[] businessIds =list.stream().filter(o -> o.getId() != null).map(r->r.getId()+"").toArray(String[]::new);
        if(businessIds.length == 0){
            return list;
        }
        //查询流程数据：1、ft_act_business查不到数据（根据 业务主键business_id、表名business_table_name、租户标识tenant_key），表明流程未发起
        //           2、ft_act_business有数据，根据process_instance_id联查act_ru_task（PROC_INST_ID_），查到的记录即为当前流程待审核节点，
        //          如若没有数据，表明流程已结束，NAME_：当前审批节点名称，ASSIGNEE_：审批人
        List<CommonBaseEntity> flowList = flowInfoMapper.flowByTBNameAndId(flowEnum.getTableName(), businessIds, SecurityUtils.getTenantKey());
        setProcessInfo(list,flowEnum,flowList);
        return list;
    }

    /**
     * 只根据业务id查询流程信息
     * 为总部提供
     * @param busMap  
     * @param flowEnum
     * @param <T>
     * @return
     */
    public static <T extends CommonBaseEntity> List<T> getFlowInfo(Map<String,String> busMap, FlowEnum flowEnum){
        if(MapUtils.isEmpty(busMap) || flowEnum == null || StringUtil.isBlank(flowEnum.getTableName()))
            return new ArrayList<>();
        //租户标识: 业务ID集合
        Map<String,List<String>> groupBusMap = new HashMap<>();
        for(String k: busMap.keySet()){
            if(busMap.get(k) ==null)                
                continue;
            ObjectUtils.add2MapList(groupBusMap, busMap.get(k), k);
        }
        if(MapUtils.isEmpty(groupBusMap) )
            return new ArrayList<>();
        List<CommonBaseEntity> flowList = new ArrayList<>();
        //生成list
        List list = new ArrayList<>();
        for(String k: groupBusMap.keySet()){
            if(CollectionUtils.isEmpty(groupBusMap.get(k)))
                continue;
            List<String> busIdList = groupBusMap.get(k);
            for (int i = 0; i < busIdList.size(); i++) {
                CommonBaseEntity entity = new CommonBaseEntity();
                entity.setId(Long.valueOf(busIdList.get(i)));
                list.add(entity);
            }
            List<CommonBaseEntity> tempList = flowInfoMapper.flowByTBNameAndId(flowEnum.getTableName(),groupBusMap.get(k).toArray(new String[]{}) , k);
            if(tempList != null)
                flowList.addAll(tempList);
        }
        setProcessInfo(list,flowEnum,flowList);
        return list;
    }

    private static <T extends CommonBaseEntity> void setProcessInfo(List<T> list, FlowEnum flowEnum, List<CommonBaseEntity> flowList){
        //业务ID : 流程信息
        Map<String,CommonBaseEntity> flowMap = new HashMap<>();
        for (CommonBaseEntity temp : flowList) {
            flowMap.put(temp.getBusinessId(), temp);
        }
        //填充流程信息到list
        Set<String> userNameSet = new HashSet<>();
        for (T t : list) {
            t.setProcessKey(flowEnum.getProcessKey());
            t.setBusinessTableName(flowEnum.getTableName());
            CommonBaseEntity flowInfo = flowMap.get(t.getId() + "");
            if (flowInfo == null) {
                t.setTaskStatus(FlowStatusEnum.FLOW_STATUS_INIT.getKey());
                t.setIsFirstNode("0");
                continue;
            }
            //ProcessTaskManId为空，表示流程已结束
            t.setTaskStatus(StringUtils.isBlank(flowInfo.getProcessTaskManId())
                    ? FlowStatusEnum.FLOW_STATUS_END.getKey() : FlowStatusEnum.FLOW_STATUS_AUDITING.getKey());
            t.setTaskId(flowInfo.getTaskId());
            t.setProcessTaskName(flowInfo.getProcessTaskName());
            t.setInstanceId(flowInfo.getInstanceId());
            t.setProcessTaskManId(flowInfo.getProcessTaskManId());
            t.setNextNodeId(flowInfo.getNextNodeId());
            t.setCurrentTaskId(flowInfo.getCurrentTaskId());
            if (!t.getTaskStatus().equals(FlowStatusEnum.FLOW_STATUS_END.getKey()))
                userNameSet.addAll(SetUtils.hashSet(flowInfo.getProcessTaskManId().split(",")));
            //是否为第一节点发起&当前登录用户等于发起人
            t.setIsFirstNode(isFirstNodeEdit(flowInfo.getInstanceId()));
            //当前用户若在处理人中，将currentTaskId设置为对应得，否则前端点处理时有问题
            if(StringUtils.isNotBlank(t.getProcessTaskManId()) && t.getProcessTaskManId().indexOf(SecurityUtils.getUserName()) > -1){
                int index = Arrays.asList(t.getProcessTaskManId().split(",")).indexOf(SecurityUtils.getUserName());
                String myTaskId = Arrays.asList(t.getCurrentTaskId().split(",")).get(index);
                t.setCurrentTaskId(myTaskId);
            }
        }
        //查询流程审批人名称
        if(CollectionUtils.isNotEmpty(userNameSet)){
            List<SysUser> userList = systemApiService.selectUserListByUsernames(StringUtils.join(userNameSet,","));
            //用户登陆名 ： 用户nickName
            Map<String,String> nickNameMap = userList.stream().collect(Collectors.toMap(SysUser::getUserName, SysUser::getNickName));
            for (T t : list) {
                String processTaskManName = ObjectUtils.replaceWithMap(t.getProcessTaskManId(), nickNameMap);
                t.setProcessTaskMan(processTaskManName);
            }
        }
    }

    /**
     * 明细接口查询使用(新增走这个)
     * @param t
     * @param flowEnum
     * @param <T>     
     * @return
     */
    public static <T extends CommonBaseEntity> T getFlowInfo(T t,FlowEnum flowEnum){
        if(t != null){
            getFlowInfo(Arrays.asList(t), flowEnum);
            t.setProcessKey(flowEnum.getProcessKey());
            t.setBusinessTableName(flowEnum.getTableName());
        }
        return t;
    }

    public static <T extends CommonBaseEntity> void setInstanceId(T t, FlowEnum flowEnum, String tenantKey){
        setInstanceId(Arrays.asList(t),flowEnum,tenantKey);
    }

    public static <T extends CommonBaseEntity> void setInstanceId(List<T> list, FlowEnum flowEnum, String tenantKey){
        if(CollectionUtils.isEmpty(list) || flowEnum == null || StringUtil.isBlank(flowEnum.getTableName()))
            return;
        String[] businessIds =list.stream().map(r-> String.valueOf(r.getId())).toArray(String[]::new);
        List<CommonBaseEntity> flowList = flowInfoMapper.flowByTBNameAndId(flowEnum.getTableName(), businessIds, tenantKey);

        //业务ID : 流程信息
        Map<String,CommonBaseEntity> flowMap = new HashMap<>();
        for (CommonBaseEntity temp : flowList) {
            flowMap.put(temp.getBusinessId(), temp);
        }
        for (T t : list) {
            CommonBaseEntity flowInfo = flowMap.get(String.valueOf(t.getId()));
            if (flowInfo == null) {
                t.setTaskStatus(FlowStatusEnum.FLOW_STATUS_INIT.getKey());
                t.setIsFirstNode("0");
                continue;
            }
            t.setInstanceId(flowInfo.getInstanceId());
        }
    }

    /**
     * 根据流程id，获取当前节点审批人员用户名
     * @param instanceId
     * @return
     */
    public static String getApprove(String instanceId){
        StringBuilder approve = new StringBuilder();
        if(StringUtils.isBlank(instanceId)){
            return approve.toString();
        }
        Map<String, Object> map = remoteBpmnService.handleList(instanceId, null,null).getData();
        List<LinkedHashMap<String,Object>> items = (List<LinkedHashMap<String, Object>>) map.get("items");
        for (LinkedHashMap<String, Object> item : items) {
            String endTime = (String) item.get("endTime");
            String assignee = (String) item.get("assignee");
            if(StringUtils.isBlank(endTime)){
                approve.append(assignee).append(",");
            }
        }

        return approve.toString();
    }
    
    /***
     * 功能描述: 根据登录账号获取用户姓名
     * 作者: fushudong
     * 时间: 2023/9/4
     */
    private static String getUserNameByLoginAccount(String account){
        SysUser sysUser = new SysUser();
        sysUser.setUserName(account);
        AjaxResult ajaxResult = systemServiceApi.selectSysUserInfo(sysUser);
        Integer code = (Integer) ajaxResult.get("code");
        if (code!=200) return account;
        List<SysUser> userList = JSONArray.parseArray(JSONObject.toJSONString(ajaxResult.get("data")), SysUser.class);
        if (CollectionUtil.isEmpty(userList)) {
            log.warn("未查询到用户信息，account：{}", account);
            return account;
        }
        return userList.get(0).getNickName();
    }

    private static String isFirstNodeEdit(String insId){
        if(StringUtils.isBlank(insId))
            return "0";
        AjaxResult result = flowServiceApi.isNowfirstNode(insId);
        if(!AjaxResult.isSuccess(result)){
            return "0";
        }
        boolean isFirst= ObjectUtils.nvlString(result.get(AjaxResult.DATA_TAG)).equalsIgnoreCase(SecurityUtils.getSysUser().getUserName());
        return isFirst?"1":"0";
    }


}