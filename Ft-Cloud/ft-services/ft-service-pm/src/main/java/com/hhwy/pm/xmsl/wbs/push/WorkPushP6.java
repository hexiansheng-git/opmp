package com.hhwy.pm.xmsl.wbs.push;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.core.sync.enums.SyncBusinessEnum;
import com.hhwy.pm.core.sync.service.ISyncLogMasterService;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.qqch.preparation.sbch.imported.customs.service.ISbchImportCustomsClearDetailService;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.push.bean.P6WorkBean;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVo;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVoBean;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.HttpClientUtil;
import com.hhwy.utils.MySecurityUtils;
import com.hhwy.utils.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;

/**
 *  作业推送到p6 
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2024/5/10 13:56   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2024/5/10 13:56    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
@Component
public class WorkPushP6 {
    private static final Logger log = LoggerFactory.getLogger(WorkPushP6.class);

    @Value("${p6.workPushUrl}")
    private String workPushUrl;
    @Value("${p6.workPushUpdateUrl}")
    private String workPushUpdateUrl;
    @Value("${p6.workPushDeleteUrl}")
    private String workPushDeleteUrl;
    @Autowired
    private IXmslWbsMainService wbsMainService;
    @Autowired
    private ISyncLogMasterService syncLogMasterService;

    /**
     * 推送作业到p6
     * @param mainId 项目wbs主表id
     * @param tenantKey 租户key
     * @param list 全部wbs，自动过滤掉非作业数据
     * @param invalidIdSet 失效wbsId            
     */
    public void push(Long mainId,String tenantKey,List<XmslWbs> list, Set<String> invalidIdSet){
//        tenantKey = "PJ2019010034";
        long begin = System.currentTimeMillis();
        try{
            List<XmslWbs> addList = new ArrayList<>();
            List<XmslWbs> updateList = new ArrayList<>();
            //解析新增、修改数据
            for (int i = 0; i < list.size(); i++) {
                XmslWbs temp = list.get(i);
                if(!StringUtils.equals(temp.getNodeType(),"5") || temp.getStatus() == 0)
                    continue;
                if(StringUtils.isBlank(temp.getPtVar4()))
                    addList.add(temp);
                else
                    updateList.add(temp);
            }
            insert(tenantKey, addList);
            update(tenantKey, updateList);
            delete(tenantKey, invalidIdSet);
        }catch(Exception e){
            e.printStackTrace();
            log.error("作业推送新增p6失败，mainID:{},消息:{}",mainId,e.getMessage());
        }finally {
            long usemills = System.currentTimeMillis()-begin;
            log.debug("作业推送到p6完成，mainID:{},耗时:{}毫秒",mainId,usemills);
        }
    }
    
    

    //新增作业
    //{
    //  "projectId": "PJ2019010034", 
    //  "createList": [
    //      {
    //          "wbsObjectId": "380457", 
    //          "activityInfoList": [
    //              {
    //                  "id": "作业代码1修改", 
    //                  "name": "作业名称1修改" 
    //              },
    //              {
    //                  "id": "作业代码2修改", 
    //                  "name": "作业名称2修改" 
    //              }
    //          ] 
    //      }    ]
    private int insert(String tenantKey,List<XmslWbs> addList){
        if(CollectionUtils.isEmpty(addList))
            return 0;
        String paramStr = "",resultStr ="",failMsg ="";
        boolean isSuccess = false;
        long beginMills = System.currentTimeMillis();
        int successCount = 0;
        try{
            Map<String,Map> createMap = new HashMap<>();
            //构建请求参数
            List<Map> pushList = new ArrayList<>();
            for (int i = 0; i < addList.size(); i++) {
                XmslWbs temp = addList.get(i);
                if(StringUtils.isBlank(temp.getParentId()) || Long.valueOf(temp.getParentId()) < 1L){
                    log.error("作业的父级为空或者不准确，跳过推送,wbsid:{},wbs编号:{},parentId:{},tenantKey:{}", temp.getId(),temp.getCode(),temp.getParentId(), MySecurityUtils.getTenantKey());
                    continue;
                }
                XmslWbs parent = WbsRedisUtils.getWbs(Long.valueOf(temp.getParentId()));
                if(parent == null){
                    log.error("作业的父级获取失败，跳过推送,wbsid:{},wbs编号:{},parentId:{},tenantKey:{}", temp.getId(),temp.getCode(),temp.getParentId(), MySecurityUtils.getTenantKey());
                    continue;
                }
                if(StringUtils.isBlank(parent.getPtVar4())){
                    log.error("作业的父级ptVar4为空，跳过推送,parentId:{},编号:{},tenantKey:{}", parent.getId(),parent.getCode(),MySecurityUtils.getTenantKey());
                    continue;
                }
                if(temp.getStatus() == 0)
                    continue;
                Map tempMap = ObjectUtils.toMap(
                        "id",temp.getSelfCode()
                        ,"name",temp.getName()
                );
                Map pushMap = createMap.get(temp.getParentId());
                if(pushMap == null){
                    pushMap = ObjectUtils.toMap(
                            "wbsObjectId",parent.getPtVar4(),
                            "activityInfoList",new ArrayList<>(Arrays.asList(tempMap))
                    );
                    createMap.put(temp.getParentId(),pushMap);
                    pushList.add(pushMap);
                }else{
                    List list = (List)pushMap.get("activityInfoList");
                    list.add(tempMap);
                }
            }
            if(CollectionUtils.isEmpty(pushList)){
                log.error("wbs作业新增接口推送没有任何需要推送的数据，tenantKey:{}",tenantKey);
                return 0;
            }
            JSONObject pushParam = new JSONObject();
            pushParam.put("projectId", tenantKey);
            pushParam.put("createList", pushList);
            paramStr = JSONObject.toJSONString(pushParam);
            StringEntity stringEntity = new StringEntity(paramStr, ContentType.APPLICATION_JSON);
            resultStr = HttpClientUtil.send(workPushUrl, HttpClientUtil.METHOD_POST,
                    null,null,stringEntity,null);
            AjaxResult result = JSONObject.parseObject(resultStr, AjaxResult.class);
            isSuccess = AjaxResult.isSuccess(result);
            failMsg = ObjectUtils.nvlString(result.get(AjaxResult.MSG_TAG));
            if(!isSuccess){
                log.error("wbs作业新增接口推送返回错误，code:{},fullResult:{}",result.get(AjaxResult.CODE_TAG),result);
                return 0;
            }
            //成功，修改作业的返回值
            StringBuilder errMsgBuild = new StringBuilder("");
            List<P6WorkBean> resuList = JSONObject.parseArray(JSONObject.toJSONString(result.get(AjaxResult.DATA_TAG)), P6WorkBean.class);
            List<WbsInfoVoBean> successList = new ArrayList<>();
            for (int i = 0; i < resuList.size(); i++) {
                P6WorkBean temp = resuList.get(i);
                if(StringUtils.isBlank(temp.getObjectId())){
                    errMsgBuild.append(temp.getId()+":"+temp.getReason()+";");
                    continue;
                }
                successList.add(new WbsInfoVoBean(temp.getId(),temp.getName(),temp.getObjectId()));    
            }
            successCount = successList.size();
            wbsMainService.updateP6Code(new WbsInfoVo(tenantKey,successList));
            log.debug("wbs作业推送，预推送:{}条，符合推送条件:{}条，推送成功:{}条", addList.size(), pushList.size(),successCount);
        }finally {
            Integer status = isSuccess? Constant.YES_INT:Constant.NO_INT;
            Long useMills = System.currentTimeMillis()-beginMills;
            log.debug("[wbs作业新增]推送完成，共{}条，成功:{}条,耗时:{},状态:{}",addList.size(),successCount,useMills,status);
            syncLogMasterService.save(SyncBusinessEnum.WORKPUSHP6_ADD_ENUM.name(), paramStr,resultStr
                    ,status,failMsg,System.currentTimeMillis()-beginMills);
        }
        return successCount;
    }
    
    //更新作业
    private void update(String tenantKey,List<XmslWbs> updateList){
        if(CollectionUtils.isEmpty(updateList))
            return ;
        String paramStr = "",resultStr ="",failMsg ="";
        boolean isSuccess = false;
        long beginMills = System.currentTimeMillis();
        try{
            List<P6WorkBean> pushList = new ArrayList<>();
            for (int i = 0; i < updateList.size(); i++) {
                XmslWbs temp = updateList.get(i);
                if(temp.getStatus() == 0)
                    continue;
                pushList.add(new P6WorkBean(temp.getSelfCode(),temp.getName(),temp.getPtVar4()));
            }
            JSONObject pushParam = new JSONObject();
            pushParam.put("projectId", tenantKey);                
            pushParam.put("activityInfoList", pushList);
            paramStr = JSONObject.toJSONString(pushParam);
            StringEntity stringEntity = new StringEntity(paramStr, ContentType.APPLICATION_JSON);
            resultStr = HttpClientUtil.send(workPushUpdateUrl, HttpClientUtil.METHOD_POST,
                    null,null,stringEntity,null);
            AjaxResult result = JSONObject.parseObject(resultStr,AjaxResult.class);
            failMsg = ObjectUtils.nvlString(result.get(AjaxResult.MSG_TAG));
            isSuccess = AjaxResult.isSuccess(result);
        }finally {
            Integer status = isSuccess? Constant.YES_INT:Constant.NO_INT;
            Long useMills = System.currentTimeMillis()-beginMills;
            log.debug("[wbs作业更新]推送完成，共{}条，耗时:{},状态:{}",updateList.size(),useMills,status);
            syncLogMasterService.save(SyncBusinessEnum.WORKPUSHP6_UPDATE_ENUM.name(), paramStr,resultStr
                    ,status,failMsg,useMills);
        }
    }
    
    //删除作业
    private void delete(String tenantKey,Set<String> idSet){
        if(CollectionUtils.isEmpty(idSet))
            return ;
        String paramStr = "",resultStr ="",failMsg ="";
        boolean isSuccess = false;
        long beginMills = System.currentTimeMillis();
        try{
            Map pushParamMap = ObjectUtils.toMap("projectId",tenantKey);
            List<Map> list = new ArrayList<>();
            List<XmslWbs> wbsList = WbsRedisUtils.getWbs(idSet);
            for (int i = 0; i < wbsList.size(); i++) {
                XmslWbs wbs = wbsList.get(i);
                if(wbs==null || StringUtils.isBlank(wbs.getPtVar4())){
                    log.debug("wbs数据异常（wbs为空或ptVar4为空），wbsID:{},tenantKey:{}", wbs==null?"":wbs.getId(), tenantKey);
                    continue;
                }
                list.add(ObjectUtils.toMap("objectId",wbs.getPtVar4()));        
            }
            pushParamMap.put("activityInfoList", list);
            paramStr = JSONObject.toJSONString(pushParamMap);
            StringEntity stringEntity = new StringEntity(paramStr, ContentType.APPLICATION_JSON);
            resultStr = HttpClientUtil.send(workPushDeleteUrl, HttpClientUtil.METHOD_POST,
                    null,null,stringEntity,null);
            AjaxResult result = JSONObject.parseObject(resultStr,AjaxResult.class);
            failMsg = ObjectUtils.nvlString(result.get(AjaxResult.MSG_TAG));
            isSuccess = AjaxResult.isSuccess(result);
        }finally {
            Integer status = isSuccess? Constant.YES_INT:Constant.NO_INT;
            Long useMills = System.currentTimeMillis()-beginMills;
            log.debug("[wbs作业删除]推送完成，共{}条，耗时:{},状态:{}",idSet.size(),useMills,status);
            syncLogMasterService.save(SyncBusinessEnum.WORKPUSHP6_DELETE_ENUM.name(), paramStr,resultStr
                    ,status,failMsg,useMills);
        }
    }
}
