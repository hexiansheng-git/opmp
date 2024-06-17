package com.hhwy.pm.qqch.preparation.safe.risk.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.func.VoidFunc;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.TreeUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.domain.QyzsSafeSpecialEquipment;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerList;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerListVo;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerListService;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchSpecialBigEquList;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.vo.QqchSpecialBigEquListVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service.IQqchSpecialBigEquListService;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskList;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.QqchSafeRiskListVo;
import com.hhwy.pm.qqch.preparation.safe.risk.mapper.QqchSafeRiskListDetailMapper;
import com.hhwy.pm.qqch.preparation.safe.risk.mapper.QqchSafeRiskListMapper;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskListDetailService;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskListService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.QyzsSafeRiskBigProj;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.QyzsSafeRiskBigProjItem;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zq
 * @date 2023-08-11 13:41:38
 * @remark
 */
@Service
@Slf4j
public class QqchSafeRiskListDetailServiceImpl implements IQqchSafeRiskListDetailService {

    @Autowired
    private QqchSafeRiskListDetailMapper qqchSafeRiskListDetailMapper;

    @Autowired
    private IQqchSafeRiskListDetailService qqchSafeRiskListDetailService;

    @Autowired
    private IQqchDangerListService qqchDangerListService;
    @Autowired
    private IQqchSpecialBigEquListService qqchSpecialBigEquListService;

    @Autowired
    private QqchSafeRiskListMapper qqchSafeRiskListMapper;

    @Value("${gm.back-url}")
    private String gmUrl;

    /**
     * 功能描述: 更新接口，从831和841同步数据
     * 作者:
     * 时间: 2024/6/14
     */
    @Override
    @Transactional
    public AjaxResult syncData() {
        BigDecimal version = VersionUtil.getVersion("qqch_safe_risk_list",null);
        List<QqchSafeRiskList> saveSafeRiskList = new ArrayList<>();
        List<QqchSafeRiskListDetail> saveDetailList = new ArrayList<>();
        //获取831数据
        QqchDangerListVo qqchDangerListList = qqchDangerListService.getQqchDangerListList(version);
        List<QqchDangerList> resultList831 = qqchDangerListList.getList();
        if (CollUtil.isNotEmpty(resultList831)) {
            //根据831中的危大工程类型和判定条件 去总部知识库 - 清单查询
            for (QqchDangerList qqchDangerList : resultList831) {
                String wbsId = qqchDangerList.getPtVar3();
                if (StrUtil.isBlank(wbsId)) {
                    log.info("wbsId为空: {}", qqchDangerList.getSchemeName());
                    continue;
                }
                QyzsSafeRiskBigProj param = new QyzsSafeRiskBigProj();
                param.setRiskProjType(qqchDangerList.getRiskProjType());
                param.setJudgmentCondition(qqchDangerList.getDecisionCondition());
                AjaxResult ajaxResult = qqchDangerListService.getGmRiskBigProjList(param);
                if (!AjaxResult.isSuccess(ajaxResult) || ajaxResult.get(AjaxResult.DATA_TAG) == null) {
                    log.info("请求总部危大工程清单接口失败:" + ajaxResult.get(AjaxResult.MSG_TAG));
                    continue;
                }
                String jsonString = JSON.toJSONString(ajaxResult.get(AjaxResult.DATA_TAG));
                List<QyzsSafeRiskBigProjItem> qyzsSafeRiskBigProjList = JSON.parseArray(jsonString, QyzsSafeRiskBigProjItem.class);
                if (CollUtil.isEmpty(qyzsSafeRiskBigProjList)){
                    log.info("总部危大工程清单查询为空:" + ajaxResult.get(AjaxResult.DATA_TAG));
                    continue;
                }
                QyzsSafeRiskBigProjItem qyzsSafeRiskBigProj = qyzsSafeRiskBigProjList.get(0);
                String riskEvent = qyzsSafeRiskBigProj.getRiskEvent();
                String riskLevel = qyzsSafeRiskBigProj.getRiskLevel();
                String possibleConsequence = qyzsSafeRiskBigProj.getPossibleConsequence();
                String safeTechnicalMeasure = qyzsSafeRiskBigProj.getSafeTechnicalMeasure();
                String riskProjType = qyzsSafeRiskBigProj.getRiskProjType();

                QqchSafeRiskList info = new QqchSafeRiskList();
                Long infoId = IdWorker.createId();
                info.setId(infoId);
                info.setWbsId(wbsId);
                info.setVersion(version);
                if (version.compareTo(BigDecimal.ONE) == 0) {
                    info.setValid(Valid.YES);
                }
                info.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                info.setCreateUserName(SecurityUtils.getUserName());
                info.setCreateTime(DateUtils.getNowDate());
                info.setType("1");
                saveSafeRiskList.add(info);

                QqchSafeRiskListDetail qqchSafeRiskListDetail = new QqchSafeRiskListDetail();
                qqchSafeRiskListDetail.setId(IdWorker.createId());
                qqchSafeRiskListDetail.setInfoId(infoId);
                qqchSafeRiskListDetail.setRiskLevel(riskLevel);
                qqchSafeRiskListDetail.setDangerThing(riskEvent);
                qqchSafeRiskListDetail.setPossibleResult(possibleConsequence);
                qqchSafeRiskListDetail.setRiskControWay(safeTechnicalMeasure);
                saveDetailList.add(qqchSafeRiskListDetail);
            }
        }
        //获取841数据
        QqchSpecialBigEquList qqchSpecialBigEquList = new QqchSpecialBigEquList();
        qqchSpecialBigEquList.setVersion(version);
        QqchSpecialBigEquListVo qqchSpecialBigEquListList = qqchSpecialBigEquListService.getQqchSpecialBigEquListList(qqchSpecialBigEquList);
        List<QqchSpecialBigEquList> resultList841 = qqchSpecialBigEquListList.getQqchSpecialBigEquListList();
        for (QqchSpecialBigEquList specialBigEquList : resultList841) {
            String wbsId = specialBigEquList.getPtVar3();
            if (StrUtil.isBlank(wbsId)) {
                log.info("获取841数据,wbsId为空: {}", specialBigEquList.getEquName());
                continue;
            }
            String equType = specialBigEquList.getEquType();
            if (StrUtil.isBlank(equType)) {
                log.info("特种设备类型为空：{}", specialBigEquList.getEquName());
                continue;
            }
            String url = gmUrl + "/gm/qyzsSafeSpecialEquipment/getChilderByKind3?kind3Arr={kind3Arr}";
            HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(headers);
            HashMap<String, Object> mapParam = new HashMap<>();
            //841设备类型 等于 总部特种设备品种字段
            mapParam.put("kind3Arr", equType);
            AjaxResult ajaxResult = RestTemplateUtils.get(url, httpEntity, AjaxResult.class, mapParam);
//            AjaxResult ajaxResult = qqchSpecialBigEquListService.getGmRiskBigProjList("", "", equType);
            if (!AjaxResult.isSuccess(ajaxResult) || ajaxResult.get(AjaxResult.DATA_TAG) == null) {
                log.info("请求总部特种设备清单接口失败:" + ajaxResult.get(AjaxResult.MSG_TAG));
                continue;
            }
            String jsonString = JSON.toJSONString(ajaxResult.get(AjaxResult.DATA_TAG));
            List<QyzsSafeSpecialEquipment> specialEquipmentList = JSON.parseArray(jsonString, QyzsSafeSpecialEquipment.class);
            if (CollUtil.isEmpty(specialEquipmentList)){
                log.info("总部特种设备清单查询为空:" + ajaxResult.get(AjaxResult.DATA_TAG));
                continue;
            }
//            List<QyzsSafeSpecialEquipment> specialEquipmentAllList = TreeUtil.treeToList(specialEquipmentTreeList);
//            List<QyzsSafeSpecialEquipment> specialEquipmentList = specialEquipmentAllList.stream().filter(p -> StrUtil.isNotBlank(p.getPerhapsTrouble()) || StrUtil.isNotBlank(p.getRiskEvent())
//                    || StrUtil.isNotBlank(p.getControlMeasure())).collect(Collectors.toList());
//            if (CollUtil.isEmpty(specialEquipmentList)) {
//                log.info("总部特种设备清单查询为空:" + ajaxResult.get(AjaxResult.DATA_TAG));
//                continue;
//            }
            QqchSafeRiskList info = new QqchSafeRiskList();
            Long infoId = IdWorker.createId();
            info.setId(infoId);
            info.setWbsId(wbsId);
            info.setVersion(version);
            if (version.compareTo(BigDecimal.ONE) == 0) {
                info.setValid(Valid.YES);
            }
            info.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            info.setCreateUserName(SecurityUtils.getUserName());
            info.setCreateTime(DateUtils.getNowDate());
            info.setType("1");
            saveSafeRiskList.add(info);
            for (QyzsSafeSpecialEquipment specialEquipment : specialEquipmentList) {
                String riskEvent = specialEquipment.getRiskEvent();
                String perhapsTrouble = specialEquipment.getPerhapsTrouble();
                String controlMeasure = specialEquipment.getControlMeasure();
                QqchSafeRiskListDetail qqchSafeRiskListDetail = new QqchSafeRiskListDetail();
                qqchSafeRiskListDetail.setId(IdWorker.createId());
                qqchSafeRiskListDetail.setInfoId(infoId);
                qqchSafeRiskListDetail.setRiskLevel("3");
                qqchSafeRiskListDetail.setDangerThing(riskEvent);
                qqchSafeRiskListDetail.setPossibleResult(perhapsTrouble);
                qqchSafeRiskListDetail.setRiskControWay(controlMeasure);
                saveDetailList.add(qqchSafeRiskListDetail);
            }
        }
        if (CollUtil.isNotEmpty(saveSafeRiskList)) {
            qqchSafeRiskListMapper.deleteQqchSafeRiskList(new QqchSafeRiskList());
            qqchSafeRiskListMapper.insertQqchSafeRiskListList(saveSafeRiskList);
        }
        if (CollUtil.isNotEmpty(saveDetailList)) {
            qqchSafeRiskListDetailMapper.deleteAll();
            qqchSafeRiskListDetailService.insertQqchSafeRiskListDetailList(saveDetailList);
        }
        return AjaxResult.success();
    }

    public QqchSafeRiskListDetail getQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail) {
        return qqchSafeRiskListDetailMapper.getQqchSafeRiskListDetail(qqchSafeRiskListDetail);
    }

    public List<QqchSafeRiskListDetail> getQqchSafeRiskListDetailList(QqchSafeRiskListDetail qqchSafeRiskListDetail) {
        return qqchSafeRiskListDetailMapper.getQqchSafeRiskListDetailList(qqchSafeRiskListDetail);
    }

    @Transactional
    public int insertQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail) {
        qqchSafeRiskListDetail.setId(IdWorker.createId());
        qqchSafeRiskListDetail.setCreateUser(SecurityUtils.getUserName());
        qqchSafeRiskListDetail.setCreateTime(DateUtils.getNowDate());
        return qqchSafeRiskListDetailMapper.insertQqchSafeRiskListDetail(qqchSafeRiskListDetail);
    }

    @Transactional
    public int insertQqchSafeRiskListDetailList(List<QqchSafeRiskListDetail> qqchSafeRiskListDetailList) {
        for (QqchSafeRiskListDetail qqchSafeRiskListDetail : qqchSafeRiskListDetailList) {
            qqchSafeRiskListDetail.setCreateUser(SecurityUtils.getUserName());
            qqchSafeRiskListDetail.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSafeRiskListDetailMapper.insertQqchSafeRiskListDetailList(qqchSafeRiskListDetailList);
    }

    @Transactional
    public int updateQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail) {
        qqchSafeRiskListDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeRiskListDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeRiskListDetailMapper.updateQqchSafeRiskListDetail(qqchSafeRiskListDetail);
    }

    @Transactional
    public int updateQqchSafeRiskListDetailList(List<QqchSafeRiskListDetail> qqchSafeRiskListDetailList) {
        for (QqchSafeRiskListDetail qqchSafeRiskListDetail : qqchSafeRiskListDetailList) {
            qqchSafeRiskListDetail.setUpdateUser(SecurityUtils.getUserName());
            qqchSafeRiskListDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSafeRiskListDetailMapper.updateQqchSafeRiskListDetailList(qqchSafeRiskListDetailList);
    }

    @Transactional
    public int deleteQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail) {
        qqchSafeRiskListDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeRiskListDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeRiskListDetailMapper.deleteQqchSafeRiskListDetail(qqchSafeRiskListDetail);
    }

    @Transactional
    public int deleteQqchSafeRiskListDetailByPks(List<Long> qqchSafeRiskListDetailPkList) {
        return qqchSafeRiskListDetailMapper.deleteQqchSafeRiskListDetailByPks(qqchSafeRiskListDetailPkList);
    }

    @Override
    @Transactional
    public void deleteByInfoId(Long infoId, String userId, String userName, Date nowDate) {
        qqchSafeRiskListDetailMapper.deleteByInfoId(infoId, userName, nowDate);
    }
}
