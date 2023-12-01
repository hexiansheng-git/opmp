package com.hhwy.pm.xmsl.wbs.push;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.factory.FlowServiceFallbackFactory;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVo;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVoBean;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.HttpClientUtil;
import com.hhwy.utils.ObjectUtils;
import lombok.extern.java.Log;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WbsPushP6 {
    private static final Logger log = LoggerFactory.getLogger(FlowServiceFallbackFactory.class);
    @Autowired
    private IXmslWbsMainService wbsMainService;
    @Value("${p6.wbsPushUrl}")
    private String wbsPushUrl;
    @Value("${p6.wbsPushUpdateUrl}")
    private String wbsPushUpdateUrl;

    /**
     * 推送到p6
     * @param mainId      wbsMainId
     * @param projectCode  项目编号
     * @param list [{ptVar5:标记是否为修改的wbs}]
     */
    public void push2P6(Long mainId,String projectCode,List<XmslWbs> list){
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
        //推送到
//        System.out.println(JSONObject.toJSONString(treeList, SerializerFeature.DisableCircularReferenceDetect));
        push(mainId,projectCode,treeList,updateList);
    }

    private void push(Long mainId,String projectCode,List<WbsInfoVoBean> treeList,List<WbsInfoVoBean> updateList){
        long begin = System.currentTimeMillis();
        try{
//            projectCode = "test-01";
            //1、新增接口
            if(CollectionUtils.isNotEmpty(treeList)){
                log.debug("wbs推送新增p6,mainId:{},新增树形第一级条目数:{}",mainId,treeList.size());
                StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(ObjectUtils.toMap(
                        "projectId",projectCode,
                        "wbsList",treeList)), ContentType.APPLICATION_JSON);
                String resultStr =  HttpClientUtil.send(wbsPushUrl, HttpClientUtil.METHOD_POST,
                        null,null,stringEntity,null);
                AjaxResult result = JSONObject.parseObject(resultStr, AjaxResult.class);
                Assert.isTrue(AjaxResult.isSuccess(result), "新增p6返回失败:"+result.get(AjaxResult.MSG_TAG));
                WbsInfoVo vo = JSONObject.parseObject(JSONObject.toJSONString(result.get(AjaxResult.DATA_TAG)),WbsInfoVo.class);
                wbsMainService.updateP6Code(vo);
            }
            //2、更新接口
            if(CollectionUtils.isNotEmpty(updateList)){
                log.debug("wbs推送修改p6,mainId:{},修改条目数:{}",mainId,updateList.size());
                StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(ObjectUtils.toMap(
                        "projectId",projectCode,
                        "wbsList",updateList)), ContentType.APPLICATION_JSON);
                String resultStr =  HttpClientUtil.send(wbsPushUpdateUrl, HttpClientUtil.METHOD_POST,
                        null,null,stringEntity,null);
                if(StringUtils.isNotBlank(resultStr)){
                    AjaxResult result = JSONObject.parseObject(resultStr, AjaxResult.class);
                    Assert.isTrue(AjaxResult.isSuccess(result), "修改p6返回失败:"+result.get(AjaxResult.MSG_TAG));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("wbs推送p6失败，mainID:{},消息:{}",mainId,e.getMessage());
            throw e;
        }finally {
            long usemills = System.currentTimeMillis()-begin;
            log.debug("wbs推送p6，mainID:{},耗时:{}毫秒",mainId,usemills);
        }
    }

}
