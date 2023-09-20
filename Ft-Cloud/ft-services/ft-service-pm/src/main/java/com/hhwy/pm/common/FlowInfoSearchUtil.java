package com.hhwy.pm.common;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.enums.FlowStatusEnum;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.domain.FtActBusiness;
import com.hhwy.pm.common.mapper.FlowInfoMapper;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import jodd.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.poi.ss.formula.functions.T;

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
        String[] businessIds =list.stream().map(r->r.getId()+"").toArray(String[]::new);
        //查询流程数据：1、ft_act_business查不到数据（根据 业务主键business_id、表名business_table_name、租户标识tenant_key），表明流程未发起
        //           2、ft_act_business有数据，根据process_instance_id联查act_ru_task（PROC_INST_ID_），查到的记录即为当前流程待审核节点，
        //          如若没有数据，表明流程已结束，NAME_：当前审批节点名称，ASSIGNEE_：审批人
        List<CommonBaseEntity> flowList = flowInfoMapper.flowByTBNameAndId(flowEnum.getTableName(), businessIds, SecurityUtils.getTenantKey());
        //业务ID : 流程信息
        Map<String,CommonBaseEntity> flowMap = new HashMap<>();
        for (int i = 0; i < flowList.size(); i++) {
            CommonBaseEntity temp = flowList.get(i);
            flowMap.put(temp.getBusinessId(), temp);
        }
        //填充流程信息到list
        Set<String> userNameSet = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            t.setProcessKey(flowEnum.getProcessKey());
            t.setBusinessTableName(flowEnum.getTableName());
            CommonBaseEntity flowInfo = flowMap.get(t.getId()+"");
            if(flowInfo == null){
                t.setTaskStatus(FlowStatusEnum.FLOW_STATUS_INIT.getKey());
                continue;
            }
            //ProcessTaskManId为空，表示流程已结束
            t.setTaskStatus(StringUtils.isBlank(flowInfo.getProcessTaskManId())
                    ?FlowStatusEnum.FLOW_STATUS_END.getKey():FlowStatusEnum.FLOW_STATUS_AUDITING.getKey());
            t.setTaskId(flowInfo.getTaskId());
            t.setProcessTaskName(flowInfo.getProcessTaskName());
            t.setInstanceId(flowInfo.getInstanceId());
            t.setProcessTaskManId(flowInfo.getProcessTaskManId());
            t.setNextNodeId(flowInfo.getNextNodeId());
            t.setCurrentTaskId(flowInfo.getCurrentTaskId());
            if(!t.getTaskStatus().equals(FlowStatusEnum.FLOW_STATUS_END.getKey()))
                userNameSet.addAll(SetUtils.hashSet(flowInfo.getProcessTaskManId().split(",")));
        }
        //查询流程审批人名称
        if(CollectionUtils.isNotEmpty(userNameSet)){
            List<SysUser> userList = systemApiService.selectUserListByUsernames(StringUtils.join(userNameSet,","));
            //用户登陆名 ： 用户nickName
            Map<String,String> nickNameMap = userList.stream().collect(Collectors.toMap(r->r.getUserName(),r->r.getNickName()));
            for (int i = 0; i < list.size(); i++) {
                T t = list.get(i);
                String processTaskManName = ObjectUtils.replaceWithMap(t.getProcessTaskManId(),nickNameMap);
                t.setProcessTaskName(processTaskManName);
            }
        }
        return list;
    }

    /**
     * 明细接口查询使用(新增走这个)
     * @param t
     * @param flowEnum
     * @param <T>     
     * @return
     */
    public static <T extends CommonBaseEntity> T getFlowInfo(T t,FlowEnum flowEnum){
        getFlowInfo(Arrays.asList(t), flowEnum);
        t.setProcessKey(flowEnum.getProcessKey());
        t.setBusinessTableName(flowEnum.getTableName());
        return t;
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



}