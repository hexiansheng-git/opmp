package com.hhwy.pm.qqch.preparation.finance.policy.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchTaxLaw;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchTaxRegulatoryOverview;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchTaxRegulatoryOverviewVo;
import com.hhwy.pm.qqch.preparation.finance.policy.mapper.QqchTaxRegulatoryOverviewMapper;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchTaxLawService;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchTaxRegulatoryOverviewService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTaxLaw.domain.QyzsFinanceTaxLaw;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhenglili
 * @date 2023-08-01 16:02:35
 * @remark 10.2.1税务监管环境概述
 */
@Service
public class QqchTaxRegulatoryOverviewServiceImpl implements IQqchTaxRegulatoryOverviewService {

    @Autowired
    private QqchTaxRegulatoryOverviewMapper qqchTaxRegulatoryOverviewMapper;
    @Autowired
    private IQqchTaxLawService qqchTaxLawService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IXmslContractInfoService xmslContractInfoService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public QqchTaxRegulatoryOverviewVo getQqchTaxRegulatoryOverview(BigDecimal version) {
        QqchTaxRegulatoryOverviewVo vo = new QqchTaxRegulatoryOverviewVo();
        version = VersionUtil.getVersion("qqch_tax_regulatory_overview", version);

        QqchTaxRegulatoryOverview qryOverviewParam = new QqchTaxRegulatoryOverview();
        qryOverviewParam.setVersion(version);
        QqchTaxRegulatoryOverview overview = qqchTaxRegulatoryOverviewMapper
            .getQqchTaxRegulatoryOverview(qryOverviewParam);
        if (overview == null) {
            overview = new QqchTaxRegulatoryOverview();
        }

        // 查询合同信息，获取合同所在国家
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getValidMaxVersionContractInfo();
        if (xmslContractInfo != null) {
            String countryCode = xmslContractInfo.getProjectLocation();
            if (StringUtils.isNotBlank(countryCode)) {
                List<CountryInfo> countryNameList = CommonServiceUtil.getCountryInfoByCodes(countryCode);
                if (!CollectionUtils.isEmpty(countryNameList)) {
                    String countryName = countryNameList.get(0).getCountryName();
                    overview.setCountryCode(countryCode);
                    overview.setCountryName(countryName);
                }
            }
        }

        // 税法列表
        QqchTaxLaw taxLawParam = new QqchTaxLaw();
        taxLawParam.setVersion(version);
        List<QqchTaxLaw> taxLawList = qqchTaxLawService.getQqchTaxLawList(taxLawParam);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setOverview(overview);
        vo.setList(taxLawList);
        return vo;
    }

    @Transactional
    public void batchSave(QqchTaxRegulatoryOverviewVo voParam) {

        // 清空数据库表中概述数据
        QqchTaxRegulatoryOverview deleteOverviewParam = new QqchTaxRegulatoryOverview();
        deleteOverviewParam.setVersion(voParam.getVersion());
        qqchTaxRegulatoryOverviewMapper.deleteQqchTaxRegulatoryOverview(deleteOverviewParam);

        QqchTaxRegulatoryOverview overview = voParam.getOverview();
        if (overview != null) {
            overview.setId(IdWorker.createId());
            overview.setVersion(voParam.getVersion());
            if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                overview.setValid(Valid.YES);
            }
            overview.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            overview.setCreateUserName(SecurityUtils.getUserName());
            overview.setCreateTime(DateUtils.getNowDate());
            qqchTaxRegulatoryOverviewMapper.insertQqchTaxRegulatoryOverview(overview);
        }

        // 清空数据库表中税法数据
        QqchTaxLaw deleteParam = new QqchTaxLaw();
        deleteParam.setVersion(voParam.getVersion());
        qqchTaxLawService.deleteQqchTaxLaw(deleteParam);

        String buttonMark = voParam.getButtonMark();
        // 税法
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            qqchTaxLawService.insertQqchTaxLawList(voParam.getList(), voParam.getVersion());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);

            //推送数据到总部版
            this.pushQyzsFinanceTaxLaw(voParam);
        }
    }

    /**
     * 推送税法数据到总部版
     * @param voParam
     */
    public void pushQyzsFinanceTaxLaw(QqchTaxRegulatoryOverviewVo voParam) {
        Map<String,Object> map = new HashMap<>();

        QqchTaxRegulatoryOverview overview = voParam.getOverview();
        String countryCode = overview.getCountryCode();
        String countryName = overview.getCountryName();
        List<QqchTaxLaw> qqchTaxLawList = voParam.getList();
        if(StringUtils.isBlank(countryCode) || CollectionUtils.isEmpty(qqchTaxLawList)){
            return;
        }

        List<QyzsFinanceTaxLaw> lawList = new ArrayList<>();
        for (QqchTaxLaw qqchTaxLaw : qqchTaxLawList) {
            String isSelect = qqchTaxLaw.getIsSelect();
            if(!"1".equals(isSelect)){
                QyzsFinanceTaxLaw law = new QyzsFinanceTaxLaw();
                law.setId(IdWorker.createId());
                law.setCountryCode(countryCode);
                law.setCountryName(countryName);
                law.setTaxLawEngName(qqchTaxLaw.getTaxLawEnglish());
                law.setTaxLawChnName(qqchTaxLaw.getTaxLawChinese());
                law.setReleaseYear(qqchTaxLaw.getPublishYear());
                law.setPublishingAgency(qqchTaxLaw.getPublishOrgan());
                law.setRemark(qqchTaxLaw.getRemark());
                law.setCreateTime(DateUtils.getNowDate());
                law.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                law.setCreateUserName(SecurityUtils.getUserName());
                lawList.add(law);
            }
        }
        if(CollectionUtils.isEmpty(lawList)){
            return;
        }
        map.put("countryCode",countryCode);
        map.put("lawList",lawList);
        rocketMQTemplate.convertAndSend("qyzs_finance_tax_law:tenantSuccess", map);
    }
}
