package com.hhwy.pm.qqch.preparation.safe.danger.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerList;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerSafeMeasures;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerSafeMeasuresDetail;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerListVo;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerSafeMeasuresVo;
import com.hhwy.pm.qqch.preparation.safe.danger.mapper.QqchDangerSafeMeasuresDetailMapper;
import com.hhwy.pm.qqch.preparation.safe.danger.mapper.QqchDangerSafeMeasuresMapper;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerListService;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerSafeMeasuresService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.DistinctUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.QyzsSafeRiskBigProj;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.QyzsSafeRiskBigProjItem;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.SafeRiskBigProjQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.service.IQyzsSafeRiskBigProjService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.Constant;
import com.hhwy.utils.HttpClientUtil;
import com.hhwy.utils.idworker.IdWorker;
import javafx.scene.shape.Mesh;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.omg.CORBA.ServiceDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhenglili
 * @date 2023-08-07 14:23:10
 * @remark 8.3.2 危大工程安全技术措施
 */
@Service
public class QqchDangerSafeMeasuresServiceImpl implements IQqchDangerSafeMeasuresService {

    @Autowired
    private QqchDangerSafeMeasuresMapper qqchDangerSafeMeasuresMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private QqchDangerSafeMeasuresDetailMapper qqchDangerSafeMeasuresDetailMapper;
    @Autowired
    private IQqchDangerListService qqchDangerListService;
    @Autowired
    private IQyzsSafeRiskBigProjService qyzsSafeRiskBigProjService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Value("${gm.back-url}")
    private String gmUrl;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchDangerSafeMeasuresVo getQqchDangerSafeMeasuresList(BigDecimal version) {
        QqchDangerSafeMeasuresVo vo = new QqchDangerSafeMeasuresVo();
        version = VersionUtil.getVersion("qqch_danger_safe_measures", version);

        QqchDangerSafeMeasures qryParam = new QqchDangerSafeMeasures();
        qryParam.setVersion(version);
        List<QqchDangerSafeMeasures> list = qqchDangerSafeMeasuresMapper.getQqchDangerSafeMeasuresList(qryParam);

        // 组装新列表
        List<QqchDangerSafeMeasures> newList = new ArrayList<>();

        // 危大工程清单
        Set<String> typeStrSet = new HashSet<>();
        List<QqchDangerList> dangerList = qqchDangerListService.getQqchDangerListList(version).getList();
        for (QqchDangerList qqchDangerList : dangerList) {
            QqchDangerSafeMeasures qqchDangerSafeMeasures = new QqchDangerSafeMeasures();
            for (QqchDangerSafeMeasures measures : list) {
                if (measures.getSchemeCode().equals(qqchDangerList.getSchemeCode())) {
                    BeanUtils.copyProperties(measures, qqchDangerSafeMeasures);
                }
            }
            if(qqchDangerSafeMeasures.getId() == null){
                qqchDangerSafeMeasures.setId(IdWorker.createId());
            }
            qqchDangerSafeMeasures.setSchemeCode(qqchDangerList.getSchemeCode());
            qqchDangerSafeMeasures.setSchemeName(qqchDangerList.getSchemeName());
            qqchDangerSafeMeasures.setDangerLevel(qqchDangerList.getDangerLevel());
            qqchDangerSafeMeasures.setDangerLevelLabel(qqchDangerList.getDangerLevelLabel());
            qqchDangerSafeMeasures.setWbsCode(qqchDangerList.getWbsCode());
            qqchDangerSafeMeasures.setWbsName(qqchDangerList.getWbsName());
            qqchDangerSafeMeasures.setPtVar1(qqchDangerList.getRiskProjType());
            qqchDangerSafeMeasures.setPtVar3(qqchDangerList.getDecisionCondition());
            newList.add(qqchDangerSafeMeasures);
        }
        
        newList.stream().forEach(r->{typeStrSet.add(r.getPtVar1()+"__"+r.getPtVar3());});
        //获取企业知识库危大工程清单数据
        JSONObject gmObj = gmProjItemList(typeStrSet);
        
        // 全部详情
        QqchDangerSafeMeasuresDetail qryParamDetail = new QqchDangerSafeMeasuresDetail();
        qryParamDetail.setVersion(version);
        List<QqchDangerSafeMeasuresDetail> deTailList = qqchDangerSafeMeasuresDetailMapper.getQqchDangerSafeMeasuresDetailList(qryParamDetail);
        Map<Long, List<QqchDangerSafeMeasuresDetail>> detailMap = deTailList.stream().collect(Collectors.groupingBy(QqchDangerSafeMeasuresDetail::getMasterId));

        for (QqchDangerSafeMeasures measures : newList) {
            Map<String,QqchDangerSafeMeasuresDetail> sourceDetailMap = new HashMap<>();
            List<QqchDangerSafeMeasuresDetail> oldDetailList = detailMap.get(measures.getId());
            if(!CollectionUtils.isEmpty(oldDetailList)){
                for (int i = 0; i < oldDetailList.size(); i++) {
                    QqchDangerSafeMeasuresDetail temp = oldDetailList.get(i);
                    if("1".equals(temp.getIsSelect()))
                        continue;
                    sourceDetailMap.put(measures.getPtVar1()+"__"+measures.getPtVar3()+"__"+temp.getMeasures(),temp);
                }
            }
            //拿总部版数据
            Object gmChildObj = gmObj.get(measures.getPtVar1()+"__"+measures.getPtVar3());
            if(gmChildObj == null)
                continue;
            measures.setDetailList(new ArrayList<>());
            List<QyzsSafeRiskBigProjItem> gmChildList = JSONObject.parseArray(JSONObject.toJSONString(gmChildObj),QyzsSafeRiskBigProjItem.class);
            for (int i = 0; i < gmChildList.size(); i++) {
                QqchDangerSafeMeasuresDetail temp = trans2Item(gmChildList.get(i));
                temp.setMasterId(measures.getId());
                temp.setVersion(version);
                measures.getDetailList().add(temp);
                //进行去重
                sourceDetailMap.remove(measures.getPtVar1()+"__"+measures.getPtVar3()+"__"+temp.getMeasures()); 
            }
            measures.getDetailList().addAll(sourceDetailMap.values());
        }

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(newList);
        return vo;
    }

    /**
     * 保存/确认/提交
     *
     * @param voParam
     * @return
     */
    @Transactional
    public void batchSave(QqchDangerSafeMeasuresVo voParam) {
        // 批量删除主表数据
        QqchDangerSafeMeasures deleteParam = new QqchDangerSafeMeasures();
        deleteParam.setVersion(voParam.getVersion());
        qqchDangerSafeMeasuresMapper.deleteQqchDangerSafeMeasures(deleteParam);

        List<QqchDangerSafeMeasures> list = voParam.getList();
        if (!CollectionUtils.isEmpty(list)) {
            List<Long> ids = list.stream().map(QqchDangerSafeMeasures::getId).collect(Collectors.toList());
            // 批量删除子表数据
            qqchDangerSafeMeasuresDetailMapper.deleteAll();

            List<QqchDangerSafeMeasures> newMainList = list;
            // 新子列表集合
            List<QqchDangerSafeMeasuresDetail> newDetailList = new ArrayList<>();
            for (QqchDangerSafeMeasures newMain : newMainList) {
                newMain.setId(IdWorker.createId());
                newMain.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    newMain.setValid(Valid.YES);
                }
                newMain.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                newMain.setCreateUserName(SecurityUtils.getUserName());
                newMain.setCreateTime(DateUtils.getNowDate());

                List<QqchDangerSafeMeasuresDetail> detailList = newMain.getDetailList();
                if (!CollectionUtils.isEmpty(detailList)) {
                    for (QqchDangerSafeMeasuresDetail detail : detailList) {
                        detail.setId(IdWorker.createId());
                        detail.setMasterId(newMain.getId());
                        detail.setVersion(newMain.getVersion());
                        if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                            detail.setValid(Valid.YES);
                        }
                        detail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                        detail.setCreateUserName(SecurityUtils.getUserName());
                        detail.setCreateTime(DateUtils.getNowDate());
                        newDetailList.add(detail);
                    }
                }
            }

            // 主表全量入库
            qqchDangerSafeMeasuresMapper.insertQqchDangerSafeMeasuresList(newMainList);

            if (!CollectionUtils.isEmpty(newDetailList)) {
                // 子全量入库
                qqchDangerSafeMeasuresDetailMapper.insertQqchDangerSafeMeasuresDetailList(newDetailList);
            }
        }

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
            this.pushQyzsSafeRiskBigProj(list);
        }
    }

    /**
     * 推送危大工程安全技术措施到总部版
     * @param list
     */
    public void pushQyzsSafeRiskBigProj(List<QqchDangerSafeMeasures> list){
        List<QyzsSafeRiskBigProj> riskBigProjList = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        for (QqchDangerSafeMeasures measures : list) {
            List<QqchDangerSafeMeasuresDetail> detailList = measures.getDetailList();
            if(CollectionUtils.isEmpty(detailList)){
                continue;
            }
            List<QqchDangerSafeMeasuresDetail> warehouseList = detailList.stream().filter(o -> !"1".equals(o.getIsSelect()) && "1".equals(o.getIsWarehouse())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(warehouseList)){
                continue;
            }
            QyzsSafeRiskBigProj riskBigProj = new QyzsSafeRiskBigProj();
            riskBigProj.setRiskProjType(measures.getPtVar1());
            riskBigProj.setJudgmentCondition(measures.getPtVar3());   //判定条件
            List<QyzsSafeRiskBigProjItem> itemList = new ArrayList<>();
            for (QqchDangerSafeMeasuresDetail detail : warehouseList) {
                QyzsSafeRiskBigProjItem item = new QyzsSafeRiskBigProjItem();
                item.setSafeTechnicalMeasure(detail.getMeasures());
                item.setRiskEvent(detail.getPtVar1());
                item.setPossibleConsequence(detail.getPtVar2());
                itemList.add(item);  
            }
            riskBigProj.setChildren(itemList);
            riskBigProjList.add(riskBigProj);
        }
        System.out.println(JSONObject.toJSONString(riskBigProjList));
        rocketMQTemplate.convertAndSend("qyzs_safe_risk_big_proj:tenantSuccess", riskBigProjList);
    }

    /**
     * 获取危大工程类型-判定条件 下的应对措施
     * @param typeStrSet [危大工程类型-判定条件]
     * @return {危大工程类型-判定条件: [QyzsSafeRiskBigProjItem] }
     */
    private JSONObject gmProjItemList(Set<String> typeStrSet){
        gmUrl = "http://10.0.1.118:10010/ftCenter/basic-api/gm/qyzsSafeRiskBigProj/getChildList";
//        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        Map map = new HashMap();
        map.put(Constant.AUTHORIZATION,"8780c2ea-d64e-4f94-94a4-c0e31897e62d");
        map.put(Constant.TENANT_KEY,"master");
        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(typeStrSet), ContentType.APPLICATION_JSON);
        Object string = HttpClientUtil.send(gmUrl, HttpClientUtil.METHOD_POST, null, map, stringEntity, null);
        JSONObject resultObj = JSONObject.parseObject(string.toString());
        if(!StringUtils.equals(resultObj.get("code")+"","200")){
            throw new RuntimeException(resultObj.get("msg")+"");
        }
        JSONObject relationObj = JSONObject.parseObject(JSONObject.toJSONString(resultObj.get("data")) ) ;
        return relationObj;
    }
    
    @Override
    @Transactional
    public void sync(BigDecimal version) {
        Assert.notNull(version, "version不能为空");
        qqchDangerSafeMeasuresMapper.deleteMeasures();
        qqchDangerSafeMeasuresMapper.deleteMeasuresDetail();
        QqchDangerListVo dangerListVo = qqchDangerListService.getQqchDangerListList(version);
        List<QqchDangerList> list = dangerListVo.getList();
        if(CollectionUtils.isEmpty(list))
            return ;
        Set<String> typeStrSet = list.stream().map(r->r.getRiskProjType()).collect(Collectors.toSet());

        gmUrl = "http://10.0.1.118:10010/ftCenter/basic-api/gm/qyzsSafeRiskBigProj/getChildList";
//        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        Map map = new HashMap();
        map.put(Constant.AUTHORIZATION,"8780c2ea-d64e-4f94-94a4-c0e31897e62d");
        map.put(Constant.TENANT_KEY,"master");
        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(typeStrSet), ContentType.APPLICATION_JSON);
        Object string = HttpClientUtil.send(gmUrl, HttpClientUtil.METHOD_POST, null, map, stringEntity, null);
        JSONObject resultObj = JSONObject.parseObject(string.toString());
        if(!StringUtils.equals(resultObj.get("code")+"","200")){
            throw new RuntimeException(resultObj.get("msg")+"");
        }
        JSONObject mapObj = JSONObject.parseObject(JSONObject.toJSONString(resultObj.get("data")) ) ;
        //构建主从表
        List<QqchDangerSafeMeasures> measuresList = new ArrayList<>();
        List<QqchDangerSafeMeasuresDetail> detailList = new ArrayList<>();
        Map<String,Long> measuresIdMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            QqchDangerList temp = list.get(i);
            QqchDangerSafeMeasures measures = trans2Measures(temp);
            measures.setVersion(version);
            measuresIdMap.put(measures.getPtVar1(),measures.getId());
            measuresList.add(measures);
        }
        for(String projType : mapObj.keySet()){
            List<QyzsSafeRiskBigProjItem> itemList = JSONObject.parseArray(JSONObject.toJSONString(mapObj.get(projType)),QyzsSafeRiskBigProjItem.class);
            Long pid = measuresIdMap.get(projType);
            for (int i = 0; i < itemList.size(); i++) {
                QyzsSafeRiskBigProjItem temp = itemList.get(i);
                QqchDangerSafeMeasuresDetail measuresDetail = trans2Item(temp);
                measuresDetail.setMasterId(pid);
                measuresDetail.setVersion(version);
                detailList.add(measuresDetail);
            }
        }
        qqchDangerSafeMeasuresMapper.insertQqchDangerSafeMeasuresList(measuresList);
        qqchDangerSafeMeasuresDetailMapper.insertQqchDangerSafeMeasuresDetailList(detailList);
    }
    
    private QqchDangerSafeMeasures trans2Measures(QqchDangerList temp){
        QqchDangerSafeMeasures measures = new QqchDangerSafeMeasures();
        measures.setId(IdWorker.createId());
        new AddBaseInfoUtil<>().addBaseEntity(measures);
        measures.setSchemeCode(temp.getSchemeCode());
        measures.setSchemeName(temp.getSchemeName());
        measures.setDangerLevel(temp.getDangerLevel());
        measures.setWbsCode(temp.getWbsCode());
        measures.setWbsName(temp.getWbsName());
        measures.setPtVar1(temp.getRiskProjType());
        measures.setPtVar2(temp.getPtVar2());
        measures.setPtVar3(temp.getPtVar3());
        measures.setPtVar4(temp.getPtVar4());
        measures.setPtVar5(temp.getPtVar5());
        return measures;
    }

    private QqchDangerSafeMeasuresDetail trans2Item(QyzsSafeRiskBigProjItem item){
        QqchDangerSafeMeasuresDetail detail = new QqchDangerSafeMeasuresDetail();
        detail.setId(IdWorker.createId());
        detail.setMeasures(item.getSafeTechnicalMeasure());
        detail.setIsWarehouse("1");
        detail.setIsSelect("1");
        detail.setRegionId(item.getRegionId());
        detail.setRegionName(item.getRegionName());
        detail.setProjectId(item.getProjectId());
        detail.setProjectName(item.getProjectName());
        detail.setDeptId(item.getDeptId());
        new AddBaseInfoUtil<>().addBaseEntity(detail);
        detail.setPtVar1(item.getRiskEvent());
        detail.setPtVar2(item.getPossibleConsequence());
        return detail;
    }
}
        