package com.hhwy.pm.xmsl.wbs.push;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.file.FileUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.feign.factory.FlowServiceFallbackFactory;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.core.sync.enums.SyncBusinessEnum;
import com.hhwy.pm.core.sync.service.ISyncLogMasterService;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.warn.WarnService;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVo;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVoBean;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.Constant;
import com.hhwy.utils.HttpClientUtil;
import com.hhwy.utils.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WbsPushP6 {
    private static final Logger log = LoggerFactory.getLogger(FlowServiceFallbackFactory.class);
    @Autowired
    private IXmslWbsMainService wbsMainService;
    @Autowired
    private IXmslWbsService wbsService;
    @Autowired
    private ISyncLogMasterService syncLogMasterService;
    @Autowired
    private WarnService warnService;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private SystemApiService systemApiService;
    
    @Value("${p6.wbsPushUrl}")
    private String wbsPushUrl;
    @Value("${p6.wbsPushUpdateUrl}")
    private String wbsPushUpdateUrl;
    @Value("${p6.wbsPushDeleteUrl}")
    private String wbsPushDeleteUrl;
    @Value("${p6.ip_port}")
    private String p6Url;

    /**
     * 推送wbs到p6
     * @param mainId
     * @param projectCode
     */
    public void push2P6(Long mainId, String projectCode)  {
        //List<XmslWbs> wbsList = WbsRedisUtils.allWbs(projectCode);



    }


    /**
     * 推送到p6 (wbs审批结束调用)
     * @param mainId      wbsMainId
     * @param projectCode  项目编号
     * @param list [{ptVar5:标记是否为修改的wbs}]
     * @param invalidIdSet 禁用的id
     */
    public void push2P6(Long mainId, String projectCode, List<XmslWbs> list, Set<String> invalidIdSet)  {
        long beginMills = System.currentTimeMillis();
        try{
            List<WbsInfoVoBean> voList = new ArrayList<>();
            //转换 > WbsInfoVoBean
            Map<String,WbsInfoVoBean> map = new HashMap<>();
            List<WbsInfoVoBean> treeList = new ArrayList<>();
            List<WbsInfoVoBean> updateList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                XmslWbs temp = list.get(i);
                if(StringUtils.isBlank(temp.getCode())){
                    log.error("WBS编号为空,ID:"+temp.getId()+",mainId:"+temp.getMainId());
                    continue;
                }
                if(StringUtils.isBlank(temp.getName())){
                    log.error("WBS名称为空,ID:"+temp.getId()+",mainId:"+temp.getMainId());
                    continue;
                }
                if(temp.getStatus() == Constant.NO_INT)
                    continue;
                WbsInfoVoBean bean = WbsInfoVoBean.parseWbs(temp);
                if(temp.getLevel() == 1 )
                    treeList.add(bean);
                voList.add(bean);
                map.put(temp.getId(),bean);
                if(StringUtils.isNotBlank(temp.getPtVar5())) //不为空：需要推送到修改接口
                    updateList.add(bean);
            }
            //递归成树形
            for (int i = 0; i < voList.size(); i++) {
                WbsInfoVoBean temp = voList.get(i);
                WbsInfoVoBean parent = map.get(temp.getPid());
                if(parent == null)
                    continue;
                temp.setParentObjectId(parent.getObjectId());
                parent.getChildren().add(temp);
            }
            //推送新增修改数据到p6
            push(mainId,projectCode,treeList,updateList);
            //禁用wbs推送到p6,需要判断这些wbs是否已经推送给p6
            pushDelete(mainId,projectCode,invalidIdSet);
            //发送消息给张双勤  P6数据已推送，请及时上传作业
            List<SysDictData> dictDataList = systemApiService.selectDictDataByType("wbs_push_p6_warning");
            if(CollectionUtils.isNotEmpty(dictDataList) && StringUtils.isNotBlank(dictDataList.get(0).getDictValue())){
                String username = dictDataList.get(0).getDictValue();
                List<SysUser> userList = systemServiceApi.selectUserInfoByUserNameAndTenant(ObjectUtils.toMap(
                        "userNames",username,
                        "tenantKey",projectCode));
                if(CollectionUtils.isEmpty(userList)){
                    log.warn("未在租户{}下找到p6预警的用户{}信息",projectCode,username);
                }else{
                    warnService.addWarn(WarnItem.WBS_P6_WARN,WarnItem.WBS_P6_WARN.getWarnRule(), WarnScopeType.USER, "",userList.get(0).getUserId()+"",projectCode);
                }
            }
        }finally {
            long usemills = System.currentTimeMillis()-beginMills;
            log.debug("wbs推送p6，mainID:{},耗时:{}毫秒",mainId,usemills);
        }

    }

    //推送新增&修改数据
    private void push(Long mainId,String projectCode,List<WbsInfoVoBean> treeList,List<WbsInfoVoBean> updateList){
        long begin = System.currentTimeMillis();
        try{
            //1、新增接口
            if(CollectionUtils.isNotEmpty(treeList)){
                log.debug("wbs推送新增p6,mainId:{},新增树形第一级条目数:{}",mainId,treeList.size());
                StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(ObjectUtils.toMap(
                        "projectId",projectCode,
                        "wbsList",treeList)), ContentType.APPLICATION_JSON);
                String resultStr = "";
                boolean isSuccess = false;
                try{
                    resultStr =  HttpClientUtil.send(wbsPushUrl, HttpClientUtil.METHOD_POST,
                            null,null,stringEntity,null);
                    AjaxResult result = JSONObject.parseObject(resultStr, AjaxResult.class);
                    isSuccess = AjaxResult.isSuccess(result);
                        Assert.isTrue(AjaxResult.isSuccess(result), "新增p6返回失败:"+result.get(AjaxResult.MSG_TAG));
                    WbsInfoVo vo = JSONObject.parseObject(JSONObject.toJSONString(result.get(AjaxResult.DATA_TAG)),WbsInfoVo.class);
                    wbsMainService.updateP6Code(vo);
                }finally {
                    Integer status = isSuccess?Constant.YES_INT:Constant.NO_INT;
                    syncLogMasterService.save(SyncBusinessEnum.WBSPUSHP6_ADD_ENUM.name(), getContent(stringEntity),resultStr,status,"",System.currentTimeMillis()-begin);
                }
            }
            //2、更新接口
            if(CollectionUtils.isNotEmpty(updateList)){
                long beginMills= System.currentTimeMillis();
                log.debug("wbs推送修改p6,mainId:{},修改条目数:{}",mainId,updateList.size());
                StringEntity stringEntity = null;
                String resultStr = "";
                boolean isSuccess = false;
                try{
                    //修改时删除children
                    for (int i = 0; i < updateList.size(); i++) {
                        WbsInfoVoBean temp = updateList.get(i);
                        temp.setChildren(null);
                    }
                    stringEntity = new StringEntity(JSONObject.toJSONString(ObjectUtils.toMap(
                            "projectId",projectCode,
                            "wbsList",updateList)), ContentType.APPLICATION_JSON);
                    resultStr =  HttpClientUtil.send(wbsPushUpdateUrl, HttpClientUtil.METHOD_POST,
                            null,null,stringEntity,null);
                    if(StringUtils.isNotBlank(resultStr)){
                        AjaxResult result = JSONObject.parseObject(resultStr, AjaxResult.class);
                        isSuccess = AjaxResult.isSuccess(result);
                        Assert.isTrue(AjaxResult.isSuccess(result), "修改p6返回失败:"+result.get(AjaxResult.MSG_TAG));
                    }
                }finally {
                    Integer status = isSuccess?Constant.YES_INT:Constant.NO_INT;
                    syncLogMasterService.save(SyncBusinessEnum.WBSPUSHP6_UPDATE_ENUM.name(),getContent(stringEntity),resultStr,status,"",System.currentTimeMillis()-beginMills);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("wbs推送新增p6失败，mainID:{},消息:{}",mainId,e.getMessage());
        }finally {
            long usemills = System.currentTimeMillis()-begin;
            log.debug("wbs推送新增p6，mainID:{},耗时:{}毫秒",mainId,usemills);
        }
    }

    //推送删除数据，需要保证要删除的数据推送给p6过
    private void pushDelete(Long mainId,String projectCode,Set<String> invalidIdSourceSet){
        if(CollectionUtils.isEmpty(invalidIdSourceSet))
            return;
        long begin = System.currentTimeMillis();
        StringEntity stringEntity =null;
        String resultStr = null;
        boolean isSuccess = false;
        try{

            Set<Long> invalidIdSet = new HashSet<>();
            for(String r : invalidIdSourceSet){
                if(StringUtils.isBlank(r))
                    continue;
                invalidIdSet.add(Long.valueOf(r));
            }
            Map<String,String> p6IdMap = new HashMap<>();
            List<Map> wbsList = new ArrayList<>();
            Iterator<Long> iterator = invalidIdSet.iterator();
            while(iterator.hasNext()){
                Long id = iterator.next();
                XmslWbs tempWbs = WbsRedisUtils.getWbs(id);
                if(StringUtils.isNotBlank(tempWbs.getPtVar4())){
                    //delIdSet.add(tempWbs.getPtVar4());
                    p6IdMap.put(tempWbs.getPtVar4(),tempWbs.getId());
                    wbsList.add(ObjectUtils.toMap("objectId",tempWbs.getPtVar4().trim()));
                }
            }
            if(CollectionUtils.isEmpty(wbsList)){
                log.debug("wbs删除推送p6,没有任何要处理的数据,prjCode:{},禁用ID数量:{}",projectCode,invalidIdSet.size());
                return ;
            }
            //构建请求参数
            stringEntity = new StringEntity(JSONObject.toJSONString(ObjectUtils.toMap(
                    "projectId",projectCode,
                    "wbsList",wbsList)), ContentType.APPLICATION_JSON);
            resultStr =  HttpClientUtil.send(wbsPushDeleteUrl, HttpClientUtil.METHOD_POST,
                    null,null,stringEntity,null);
            //处理结果
            AjaxResult result = JSONObject.parseObject(resultStr, AjaxResult.class);
            isSuccess = AjaxResult.isSuccess(result);
            //将处理失败的id从p6Map中剔除掉
            if(result.get(AjaxResult.DATA_TAG) != null){
                JSONArray failArray = JSONObject.parseArray(JSONObject.toJSONString(result.get(AjaxResult.DATA_TAG)));
                for (int i = 0; i < failArray.size(); i++) {
                    JSONObject jsonObject = failArray.getJSONObject(i);
                    String objId = jsonObject.getString("objectId");
                    p6IdMap.remove(objId);
                }
            }
            //若成功，修改推送成功数据的ptVar4为空
            Set<Long> updateIdSet = new HashSet<>();
            for(Map.Entry<String,String> entry : p6IdMap.entrySet()){
                if(StringUtils.isBlank(entry.getValue()))
                    continue;
                updateIdSet.add(Long.valueOf(entry.getValue()));
            }
            wbsService.clearPtVar4(updateIdSet);
        }catch(Exception e){
            e.printStackTrace();
            log.error("wbs推送删除p6失败，mainID:{},消息:{}",mainId,e.getMessage());
        }finally {
            long usemills = System.currentTimeMillis()-begin;
            log.debug("wbs推送删除p6，mainID:{},耗时:{}毫秒",mainId,usemills);
            Integer status = isSuccess?Constant.YES_INT:Constant.NO_INT;
            if(stringEntity != null)
                syncLogMasterService.save(SyncBusinessEnum.WBSPUSHP6_DELETE_ENUM.name(),getContent(stringEntity),resultStr,status,"",System.currentTimeMillis()-begin);
        }
    }

    private String getContent(StringEntity stringEntity){
        try{
            if(stringEntity ==null)
                return "";
            return FileUtils.readFile(stringEntity.getContent());
        }catch(IOException e){
            e.printStackTrace();
        }
        return stringEntity.toString();
    }

    /**
     * 判断项目是否存在
     * @param projectCode
     * @return
     */
    public boolean isPrjExist(){
        return this.isPrjExist(SecurityUtils.getTenantKey());
    }

    /**
     * 判断项目是否存在
     * @param projectCode
     * @return
     */
    public boolean isPrjExist(String projectCode){
        long beginMills = System.currentTimeMillis();
        try{
            String url = ObjectUtils.concatUrl(p6Url,"projectInfoByProjectId");
            String result = HttpClientUtil.get(url,ObjectUtils.toMap("projectId",projectCode));
            if(StringUtils.isBlank(result)){
                log.info("p6判断项目是否存在接口,prjCode:{},未获取到返回值",projectCode);
                return false;
            }
            System.out.println(result);
            JSONArray jsonArray = JSONObject.parseArray(result);
            System.out.println(jsonArray);
            return CollectionUtils.isNotEmpty(jsonArray);
        }catch(Exception e){
            e.printStackTrace();
            log.error("p6判断项目是否存在接口错误",e);
            return false;
        }finally {
            long usemills = System.currentTimeMillis()-beginMills;
            log.debug("p6判断项目是否存在接口，prjCode:{},耗时:{}毫秒",projectCode,usemills);
        }
    }

}
