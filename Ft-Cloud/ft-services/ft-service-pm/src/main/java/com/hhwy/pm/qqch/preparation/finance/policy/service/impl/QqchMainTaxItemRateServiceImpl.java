package com.hhwy.pm.qqch.preparation.finance.policy.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchMainTaxItemRate;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchMainTaxItemRateVo;
import com.hhwy.pm.qqch.preparation.finance.policy.mapper.QqchMainTaxItemRateMapper;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchMainTaxItemRateService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTaxItemRate.domain.QyzsFinanceTaxItemRate;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
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
 * @date 2023-08-01 16:03:00
 * @remark 10.2.2主要税目税率
 */
@Service
public class QqchMainTaxItemRateServiceImpl implements IQqchMainTaxItemRateService {

    @Autowired
    private QqchMainTaxItemRateMapper qqchMainTaxItemRateMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public QqchMainTaxItemRateVo getQqchMainTaxItemRateList(BigDecimal version) {
        QqchMainTaxItemRateVo vo = new QqchMainTaxItemRateVo();
        version = VersionUtil.getVersion("qqch_main_tax_item_rate", version);

        QqchMainTaxItemRate qqchMainTaxItemRate = new QqchMainTaxItemRate();
        qqchMainTaxItemRate.setVersion(version);
        List<QqchMainTaxItemRate> list = qqchMainTaxItemRateMapper.getQqchMainTaxItemRateList(qqchMainTaxItemRate);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    @Transactional
    public void batchSave(QqchMainTaxItemRateVo voParam) {
        // 清空数据库表中数据
        QqchMainTaxItemRate deleteParam = new QqchMainTaxItemRate();
        deleteParam.setVersion(voParam.getVersion());
        qqchMainTaxItemRateMapper.deleteQqchMainTaxItemRate(deleteParam);

        String buttonMark = voParam.getButtonMark();
        List<QqchMainTaxItemRate> list = voParam.getList();
        if (!CollectionUtils.isEmpty(list)) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            for (QqchMainTaxItemRate qqchMainTaxItemRate : voParam.getList()) {
                qqchMainTaxItemRate.setId(IdWorker.createId());
                qqchMainTaxItemRate.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchMainTaxItemRate.setValid(Valid.YES);
                }
                qqchMainTaxItemRate.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchMainTaxItemRate.setCreateUserName(SecurityUtils.getUserName());
                qqchMainTaxItemRate.setCreateTime(DateUtils.getNowDate());
            }
            qqchMainTaxItemRateMapper.insertQqchMainTaxItemRateList(voParam.getList());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
            this.pushQyzsFinanceTaxItemRate(list);
        }
    }

    /**
     * 推送税种到总部版
     * @param list
     */
    public void pushQyzsFinanceTaxItemRate(List<QqchMainTaxItemRate> list){
        Map<String,Object> map = new HashMap<>();

        String projectLocation = xmslProjectBasicInfoService.projectInfo().getProjectLocation();
        if(StringUtils.isBlank(projectLocation) || CollectionUtils.isEmpty(list)){
            return;
        }

        List<QyzsFinanceTaxItemRate> taxItemRateList = new ArrayList<>();
        for (QqchMainTaxItemRate qqchMainTaxItemRate : list) {
            String isSelect = qqchMainTaxItemRate.getIsSelect();
            if(!"1".equals(isSelect)){
                QyzsFinanceTaxItemRate rate = new QyzsFinanceTaxItemRate();
                rate.setId(IdWorker.createId());
                rate.setCountryCode(projectLocation);
                rate.setTaxesCategories(qqchMainTaxItemRate.getTaxType());
                rate.setTaxRate(String.valueOf(qqchMainTaxItemRate.getTaxRate()));
                rate.setTaxBase(qqchMainTaxItemRate.getTaxBase());
                rate.setTaxCalculationMethod(qqchMainTaxItemRate.getTaxCalculationMethod());
                rate.setTaxPaymentDeadline(qqchMainTaxItemRate.getTaxPayDeadline());
                rate.setDeclarationProcedure(qqchMainTaxItemRate.getDeclarationProcedure());
                rate.setPreferentialTaxPolicy(qqchMainTaxItemRate.getTaxPreferentialPolicy());
                rate.setOperationProcessDescription(qqchMainTaxItemRate.getOperationProcessDescription());
                rate.setCreateTime(DateUtils.getNowDate());
                rate.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                rate.setCreateUserName(SecurityUtils.getUserName());
                taxItemRateList.add(rate);
            }
        }

        if(CollectionUtils.isEmpty(taxItemRateList)){
            return;
        }

        map.put("countryCode",projectLocation);
        map.put("taxItemRateList",taxItemRateList);
        rocketMQTemplate.convertAndSend("qyzs_finance_tax_item_rate:tenantSuccess", map);
    }
}
