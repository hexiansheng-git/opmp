package com.hhwy.pm.qqch.preparation.safe.danger.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerList;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerSafeMeasures;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerSafeMeasuresDetail;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerSafeMeasuresVo;
import com.hhwy.pm.qqch.preparation.safe.danger.mapper.QqchDangerSafeMeasuresDetailMapper;
import com.hhwy.pm.qqch.preparation.safe.danger.mapper.QqchDangerSafeMeasuresMapper;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerListService;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerSafeMeasuresService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.QyzsSafeRiskBigProj;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.QyzsSafeRiskBigProjItem;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.SafeRiskBigProjQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.service.IQyzsSafeRiskBigProjService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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


        //获取企业知识库危大工程清单数据
        AjaxResult ajaxResult = qyzsSafeRiskBigProjService.getQyzsSafeRiskBigProjList(new SafeRiskBigProjQueryVo());
        Map<String,Object> dataMap = (Map<String, Object>) ajaxResult.get("data");
        List<LinkedHashMap<String,Object>> riskBigProjList = (List<LinkedHashMap<String,Object>>) dataMap.get("items");
//        Map<String, QyzsSafeRiskBigProj> riskBigProjMap = riskBigProjList.stream().filter(DistinctUtil.distinctByKey(QyzsSafeRiskBigProj::getRiskProjType)).collect(Collectors.toMap(QyzsSafeRiskBigProj::getRiskProjType, o -> o));

        // 组装新列表
        List<QqchDangerSafeMeasures> newList = new ArrayList<>();

        // 危大工程清单
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
            qqchDangerSafeMeasures.setPtVar1(qqchDangerList.getPtVar1());
            newList.add(qqchDangerSafeMeasures);
        }

        // 全部详情
        QqchDangerSafeMeasuresDetail qryParamDetail = new QqchDangerSafeMeasuresDetail();
        qryParamDetail.setVersion(version);
        List<QqchDangerSafeMeasuresDetail> deTailList = qqchDangerSafeMeasuresDetailMapper.getQqchDangerSafeMeasuresDetailList(qryParamDetail);
        Map<Long, List<QqchDangerSafeMeasuresDetail>> detailMap = deTailList.stream().collect(Collectors.groupingBy(QqchDangerSafeMeasuresDetail::getMasterId));

        for (QqchDangerSafeMeasures measures : newList) {
            List<QqchDangerSafeMeasuresDetail> detailList = new ArrayList<>();
            List<QqchDangerSafeMeasuresDetail> oldDetailList = detailMap.get(measures.getId());
            if(!CollectionUtils.isEmpty(oldDetailList)){
                detailList.addAll(oldDetailList);
            }
            /*危大工程类型*/
            String dangerType = measures.getPtVar1();
            /*是否已同步过主数据*/
            String whetherSync = measures.getPtVar2();
            if(StringUtils.isNotBlank(dangerType) && !"1".equals(whetherSync)){
                LinkedHashMap<String, Object> proj = null;
                for (LinkedHashMap<String, Object> linkedHashMap : riskBigProjList) {
                    if(dangerType.equals(linkedHashMap.get("riskProjType"))){
                        proj = linkedHashMap;
                        break;
                    }
                }
                if(proj == null){
                    continue;
                }
                List<LinkedHashMap<String,Object>> riskBigProjItemList = (List<LinkedHashMap<String, Object>>) proj.get("qyzsSafeRiskBigProjItemList");
                if(!CollectionUtils.isEmpty(riskBigProjItemList)){
                    measures.setPtVar2("1");
                    for (LinkedHashMap<String,Object> item : riskBigProjItemList) {
                        QqchDangerSafeMeasuresDetail detail = new QqchDangerSafeMeasuresDetail();
                        detail.setId(IdWorker.createId());
                        detail.setMasterId(measures.getId());
                        detail.setMeasures(item.get("safeTechnicalMeasure") == null?null:item.get("safeTechnicalMeasure").toString());
                        detail.setIsWarehouse("0");
                        detail.setIsSelect("1");
                        detailList.add(detail);
                    }
                }
            }
            measures.setDetailList(detailList);
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
            qqchDangerSafeMeasuresDetailMapper.deleteQqchDangerSafeMeasuresDetailByPks(ids);

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
            List<QyzsSafeRiskBigProjItem> itemList = new ArrayList<>();
            for (QqchDangerSafeMeasuresDetail detail : warehouseList) {
                QyzsSafeRiskBigProjItem item = new QyzsSafeRiskBigProjItem();
                item.setSafeTechnicalMeasure(detail.getMeasures());
                itemList.add(item);
            }
            riskBigProj.setQyzsSafeRiskBigProjItemList(itemList);
            riskBigProjList.add(riskBigProj);
        }
        rocketMQTemplate.convertAndSend("qyzs_safe_risk_big_proj:tenantSuccess", riskBigProjList);
    }
}
