package com.hhwy.system.warn.push;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.domain.log.SysSyncLog;
import com.hhwy.feign.service.ILogServiceApi;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.core.mapper.SysUserMapper;
import com.hhwy.system.mapper.UserMapper;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  预警推送门户
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2024/4/2 10:25   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2024/4/2 10:25    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
@Component
public class Warn2Push {
    private static final Logger log = LoggerFactory.getLogger(Warn2Push.class);
    @Value("${pushTask.warnUrl}")
    private String warnUrl;
    @Value("${pushTask.pmUrl}")
    private String pmUrl;
    @Value("${gm.url}")
    private String gmUrl;
    @Autowired
    private ILogServiceApi logServiceApi;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private UserMapper myUserMapper;
    
    private final String logTitle = "预警推送门户";
    

    /**
     * 推送至一公局门户
     * @param warn { ptVar1: 空或者0:项目版 / 1:总部版 }
     */
    public void push(TWarn warn) {
        Map param = new HashMap();
        String result = "",errMsg = "";
        if(StringUtils.isBlank(warn.getWarnUrl())){
            log.error("{},url地址为空！id:{},租户:{}",logTitle,warn.getWarnId(), warn.getTenantKey());
        }
        //为了能正常单点，每个用户单独发消息
        List<String> usernameList = getUsernames(warn);
        for (int i = 0; i < usernameList.size(); i++) {
            String uname = usernameList.get(i);
            warn.setWarnScope(uname);
            warn.setPtVar3(uname);
            try{
                param = buildParam(warn);
                result= HttpRequest.post(warnUrl)
                        .body(JSONObject.toJSONString(param)).execute().body();
                JSONObject jsonObject = JSONObject.parseObject(result);
                Object resuCode = jsonObject.get("operResult");
                ObjectUtils.toString(jsonObject.get("message"));
                errMsg = "1".equals(resuCode)?"":ObjectUtils.toString(jsonObject.get("message"));
            }catch(Exception e){
                e.printStackTrace();
                errMsg = e.getMessage();
            }finally {
                logServiceApi.insertSysSyncLog(buildLog(warn,"预警推送门户",JSONObject.toJSONString(param),result,errMsg));
            }
        }
        if(CollectionUtils.isEmpty(usernameList))
            log.error("{},用户名获取为空！id:{},租户:{}",logTitle,warn.getWarnId(), warn.getTenantKey());
    }

    public List<String> getUsernames(TWarn warn){
        List<SysUser> userList = new ArrayList<>();
        List<String> usernameList = new ArrayList<>();
        if(WarnScopeType.ALL.getWarnScopeType().equals(warn.getWarnScopeType())){
            userList = userMapper.selectUserListAll(new SysUser(), Arrays.asList(warn.getTenantKey()));
        }else if(WarnScopeType.DEPT.getWarnScopeType().equals(warn.getWarnScopeType())){
            userList = this.userMapper.selectUserListByDeptIds(warn.getWarnScope());
        }else if(WarnScopeType.ROLE.getWarnScopeType().equals(warn.getWarnScopeType())){
            String[] roleKeyList = warn.getWarnScope().split(",");
            userList = myUserMapper.selectByRoleKeyList(roleKeyList, warn.getTenantKey());
        }else if(WarnScopeType.USER.getWarnScopeType().equals(warn.getWarnScopeType())){
            usernameList = Arrays.asList(warn.getWarnScope().split(","));
        }
        if(CollectionUtils.isNotEmpty(userList))
            usernameList = userList.stream().map(r->r.getUserName()).collect(Collectors.toList());
        return usernameList;
    }
    
    private Map buildParam(TWarn warn){
        String receive = Stream.of(warn.getWarnScope().split(",")).filter(r->StringUtils.isNotBlank(r)).collect(Collectors.joining(";"));
        if(StringUtils.isBlank(receive)){
            String errMsg = String.format("%s,接收人为空,租户:%s,id:%s,接收人:%s",logTitle,warn.getTenantKey(),warn.getWarnId(),warn.getWarnScope());
            log.error(errMsg);
            throw new IllegalArgumentException(errMsg);
        }
        String sysUrl = StringUtils.equals(warn.getPtVar1(),"1")?gmUrl:pmUrl; 
        Map map = new HashMap();
        map.put("syscode","PM");
        map.put("flowid",warn.getWarnId()+warn.getWarnScope());
        map.put("requestname",String.format("项管信息提醒,【%s】%s",warn.getWarnItem(),warn.getWarnContent()));
        map.put("workflowname",warn.getWarnItem());
        map.put("nodename",warn.getWarnItem());
        map.put("nodeId",warn.getWarnId()+"");
        String warnUrl = warn.getWarnUrl();
        String path = (sysUrl.endsWith("/")?sysUrl.substring(0,sysUrl.length()-1):sysUrl) +
                (warnUrl.startsWith("/")?warnUrl:warnUrl.substring(1));
        map.put("pcurl",String.format("%s?id=%s&tenantKey=%s&receiver=%s&pageType=fw",
                path,ObjectUtils.nvlString(warn.getBusinessId()),warn.getTenantKey(),receive) );
        map.put("appurl","");
        map.put("isremark","8");
        map.put("viewtype","0");
        map.put("creator", warn.getCreateUser());
        map.put("createdatetime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,warn.getCreateTime()));
        map.put("receiver",receive);
        map.put("receivedatetime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,new Date()));
        map.put("requestlevel","0");
        return map;
    }

    private SysSyncLog buildLog(TWarn warn, String typeStr, String params, String result, String failMsg){
        SysSyncLog syncLog = new SysSyncLog();
        syncLog.setId(IdWorker.createId());
        syncLog.setCreateUser(warn.getCreateUser());
        syncLog.setCreateTime(warn.getCreateTime());
        syncLog.setDelFlag("0");
        syncLog.setInterfaceName(typeStr);
        syncLog.setFunName(typeStr);
        syncLog.setReq(params);
        syncLog.setRes(result);
        syncLog.setStatus(StringUtils.isBlank(failMsg)?"0":"1");
        syncLog.setRemark(ObjectUtils.nvlString(failMsg));
        syncLog.setPtVar1(warn.getWarnId()+"");
        syncLog.setPtVar2(warn.getTenantKey());
        syncLog.setPtVar3(warn.getPtVar3());
        return syncLog;
    }
    
    public static void main(String[] args) {
    }
    
}
