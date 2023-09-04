package com.hhwy.pm.common;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.enums.FlowEnum;
import com.hhwy.enums.FlowStatusEnum;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.domain.FtActBusiness;
import com.hhwy.pm.common.mapper.FlowInfoMapper;
import com.hhwy.system.api.domain.SysUser;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 功能：流程信息获取
 * 作者: fushudong
 * 时间: 2023/09/04
 */
@Log4j2
public class FlowInfoSearchUtil {

    static FlowInfoMapper flowInfoMapper = SpringUtils.getBean(FlowInfoMapper.class);
    static SystemServiceApi systemServiceApi = SpringUtils.getBean(SystemServiceApi.class);

    /***
     * 功能描述: 查询流程信息
     * @param tableName 表名
     * @param businessId 业务主键
     * 作者: fushudong
     * 时间: 2023/9/4
     */
    public static FtActBusiness getFlowInfo(String tableName, String businessId){
        FtActBusiness result = new FtActBusiness();
        //查询流程数据：1、ft_act_business查不到数据（根据 业务主键business_id、表名business_table_name），表明流程未发起
        //           2、ft_act_business有数据，根据process_instance_id联查act_ru_task（PROC_INST_ID_），查到的记录即为当前流程待审核节点，
        //          如若没有数据，表明流程已结束，NAME_：当前审批节点名称，ASSIGNEE_：审批人
        FtActBusiness ftActBusiness = flowInfoMapper.flowByTBNameAndId(tableName, businessId);
        if (null == ftActBusiness) {
            log.info("流程未发起：tableName:{},businessId{}", tableName, businessId);
            //ft_act_business无数据，表明流程未开始,返回空
            result.setName(FlowStatusEnum.FLOW_STATUS_1.getName());
            return result;
        }
        String isuseUserName = getUserNameByLoginAccount(ftActBusiness.getCreateUser());
        String name = ftActBusiness.getName();
        String assignee = ftActBusiness.getAssignee();
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(assignee)){
            log.info("流程已结束：tableName:{},businessId{}", tableName, businessId);
            //act_ru_task 无数据，表明流程已结束
            result.setCreateUser(isuseUserName);
            result.setCreateTime(ftActBusiness.getCreateTime());
            result.setName(FlowStatusEnum.FLOW_STATUS_3.getName());
            result.setAssignee("-");
            return result;
        }
        String currentNodeUserName = getUserNameByLoginAccount(ftActBusiness.getAssignee());
        ftActBusiness.setCreateUser(isuseUserName);
        ftActBusiness.setAssignee(currentNodeUserName);
        result.setName(FlowStatusEnum.FLOW_STATUS_2.getName());
        return ftActBusiness;
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