package com.hhwy.system.push;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.domain.log.SysSyncLog;
import com.hhwy.feign.service.ILogServiceApi;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.core.mapper.SysUserMapper;
import com.hhwy.system.mapper.UserMapper;
import com.hhwy.system.warn.push.Warn2Push;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import java.util.*;

/**
 *  预警推送到门户
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2024/4/8 9:47   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2024/4/8 9:47    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
@Component
public class WarnPushMenHu {

    private static final Logger log = LoggerFactory.getLogger(WarnPushMenHu.class);
    @Value("${menhu.todoUrl}")
    private String todoUrl;
    @Value("${menhu.verifykey}")
    private String verifykey;
    @Value("${menhu.sysType}")
    private String sysType;
    @Value("${pushTask.pmUrl}")
    private String pmUrl;
    @Value("${gm.url}")
    private String gmUrl;
    @Autowired
    private ILogServiceApi logServiceApi;
    @Autowired
    private Warn2Push warn2Push;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private UserMapper myUserMapper;
    private final String logTitle = "预警推送中交门户";
    
    
    public void push(TWarn warn){
        Map param = new HashMap();
        String result = "",errMsg = "";
        String sysUrl = StringUtils.equals(warn.getPtVar1(),"1")?gmUrl:pmUrl;
        long createTime = new Date().getTime();
        if(StringUtils.isBlank(warn.getWarnUrl())){
            log.error("{},url地址为空！id:{},租户:{}",logTitle,warn.getWarnId(), warn.getTenantKey());
        }
        //获取预警接收人
        List<String> unameList = warn2Push.getUsernames(warn);
        for (int i = 0; i < unameList.size(); i++) {
            try{
                String receive = unameList.get(i);
                warn.setPtVar3(receive);
                String path = (sysUrl.endsWith("/")?sysUrl.substring(0,sysUrl.length()-1):sysUrl) +
                        (todoUrl.startsWith("/")?todoUrl:todoUrl.substring(1));
                String pcurl = String.format("%s?id=%s&tenantKey=%s&receiver=%s&pageType=fw",
                        path, ObjectUtils.nvlString(warn.getBusinessId()),warn.getTenantKey(),receive) ;
//                String pcurl = String.format("?id=%s&tenantKey=%s&receiver=%s&pageType=fw",
//                        ObjectUtils.nvlString(warn.getBusinessId()),warn.getTenantKey(),receive) ;
                //拉哥要求做base64
                pcurl = new String(Base64Utils.encode(pcurl.getBytes()));
                SysUser createUser = userMapper.selectUserByUserName(warn.getCreateUser(), Arrays.asList(warn.getTenantKey()));
                String createUserName = createUser==null?warn.getCreateUser():createUser.getNickName();
                Map<String,Object> paramMap = new HashMap();
                paramMap.put("dataId","0");
                paramMap.put("dataNo",warn.getWarnId()+receive);
                paramMap.put("doUserId",receive);
                paramMap.put("sysType",sysType);     //系统标识
                paramMap.put("contentType","5");         //消息类型
                paramMap.put("sysModel",warn.getWarnItem());     //业务系统特定模块
//                paramMap.put("title",String.format("项管信息提醒,【%s】%s",warn.getWarnItem(),warn.getWarnContent()));
                paramMap.put("title",String.format("【海外项管预警提醒】,%s", warn.getWarnContent()));
                paramMap.put("doType","0");              //3:已阅
                paramMap.put("createdTime",createTime);
                paramMap.put("writeTime",createTime);
                paramMap.put("pcActUrl",pcurl);
                paramMap.put("otherActUrl",pcurl);
                paramMap.put("urgentLevel","0");        //紧急程度
                paramMap.put("updateTime",createTime);        //
                paramMap.put("flowId","0");        //
                paramMap.put("flowName","0");        //
                paramMap.put("flowInstid","0");        //
                paramMap.put("nodeID","0");        //
                paramMap.put("nodeName","0");        //
                paramMap.put("corpName","中交一公局");        //单位名称
                paramMap.put("verifykey",verifykey);        //授权码
                paramMap.put("sendUserId",warn.getCreateUser()); //提交人4A       
                paramMap.put("sendUserName",createUserName);     //提交人名称
                param =  ObjectUtils.toMap("todos",Arrays.asList(paramMap));
                result= HttpRequest.post(todoUrl)
                        .header("Content-type","application/json; charset=utf-8")
                        .header("Accept", "application/json")
                        .body(JSON.toJSONString(param)).execute().body();
                JSONObject resultObj = JSONObject.parseObject(result);
                JSONArray results = (JSONArray)resultObj.get("TodoResponse");
                String resultCode = results.getJSONObject(0).getString("code");
                Assert.isTrue("0000".equals(resultCode),"门户推送消息返回失败："+results.getJSONObject(0).getString("msg"));
            }catch(Exception e){
                e.printStackTrace();
                errMsg = e.getMessage();
            }finally {
                logServiceApi.insertSysSyncLog(buildLog(warn,"预警推送中交门户", JSONObject.toJSONString(param),result,errMsg));
            }
        }
        if(CollectionUtils.isEmpty(unameList))
            log.error("{},用户名获取为空！id:{},租户:{}",logTitle,warn.getWarnId(), warn.getTenantKey());
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
}

