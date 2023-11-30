package com.hhwy.pm.qqch.preparation.finance.policy.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchLocalTariffPolicy;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchLocalTariffPolicyVo;
import com.hhwy.pm.qqch.preparation.finance.policy.mapper.QqchLocalTariffPolicyMapper;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchLocalTariffPolicyService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTariffPolicy.domain.QyzsFinanceTariffPolicy;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
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
 * @date 2023-08-02 14:24:49
 * @remark 10.2.4当地关税政策描述
 */
@Service
public class QqchLocalTariffPolicyServiceImpl implements IQqchLocalTariffPolicyService {

    @Autowired
    private QqchLocalTariffPolicyMapper qqchLocalTariffPolicyMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchLocalTariffPolicyVo getQqchLocalTariffPolicyList(BigDecimal version) {
        QqchLocalTariffPolicyVo vo = new QqchLocalTariffPolicyVo();
        version = VersionUtil.getVersion("qqch_local_tariff_policy", version);

        QqchLocalTariffPolicy qryParam = new QqchLocalTariffPolicy();
        qryParam.setVersion(version);
        List<QqchLocalTariffPolicy> list = qqchLocalTariffPolicyMapper.getQqchLocalTariffPolicyList(qryParam);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    /**
     * 保存/确认/提交
     *
     * @param voParam
     */
    @Transactional
    public void batchSave(QqchLocalTariffPolicyVo voParam) {
        // 清空数据库表中数据
        QqchLocalTariffPolicy deleteParam = new QqchLocalTariffPolicy();
        deleteParam.setVersion(voParam.getVersion());
        qqchLocalTariffPolicyMapper.deleteQqchLocalTariffPolicy(deleteParam);

        List<QqchLocalTariffPolicy> list = voParam.getList();
        if (!CollectionUtils.isEmpty(list)) {
            for (QqchLocalTariffPolicy qqchLocalTariffPolicy : voParam.getList()) {
                qqchLocalTariffPolicy.setId(IdWorker.createId());
                qqchLocalTariffPolicy.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchLocalTariffPolicy.setValid(Valid.YES);
                }
                qqchLocalTariffPolicy.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchLocalTariffPolicy.setCreateUserName(SecurityUtils.getUserName());
                qqchLocalTariffPolicy.setCreateTime(DateUtils.getNowDate());
            }
            qqchLocalTariffPolicyMapper.insertQqchLocalTariffPolicyList(voParam.getList());
        }

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
            this.pushQyzsFinanceTariffPolicy(list);
        }
    }

    /**
     * 推送当地关税政策到总部版
     * @param list
     */
    public void pushQyzsFinanceTariffPolicy(List<QqchLocalTariffPolicy> list){
        Map<String,Object> map = new HashMap<>();

        String projectLocation = xmslProjectBasicInfoService.projectInfo().getProjectLocation();
        if(StringUtils.isBlank(projectLocation) || CollectionUtils.isEmpty(list)){
            return;
        }

        List<QyzsFinanceTariffPolicy> tariffPolicyList = new ArrayList<>();
        for (QqchLocalTariffPolicy qqchLocalTariffPolicy : list) {
            String isSelect = qqchLocalTariffPolicy.getIsSelect();
            if(!"1".equals(isSelect)){
                QyzsFinanceTariffPolicy tariffPolicy = new QyzsFinanceTariffPolicy();
                tariffPolicy.setId(IdWorker.createId());
                tariffPolicy.setCountryCode(projectLocation);
                tariffPolicy.setName(qqchLocalTariffPolicy.getName());
                tariffPolicy.setExportRebateRate(qqchLocalTariffPolicy.getExportRebateRate());
                tariffPolicy.setImportTaxRateAcfta(qqchLocalTariffPolicy.getImportTaxRate());
                tariffPolicy.setVatRate(qqchLocalTariffPolicy.getVatRate());
                tariffPolicy.setConsumptionTaxRate(qqchLocalTariffPolicy.getConsumptionTaxRate());
                tariffPolicy.setCreateTime(DateUtils.getNowDate());
                tariffPolicy.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                tariffPolicy.setCreateUserName(SecurityUtils.getUserName());
                tariffPolicyList.add(tariffPolicy);
            }
        }

        if(CollectionUtils.isEmpty(tariffPolicyList)){
            return;
        }

        map.put("countryCode",projectLocation);
        map.put("tariffPolicyList",tariffPolicyList);
        rocketMQTemplate.convertAndSend("qyzs_finance_tariff_policy:tenantSuccess", map);
    }
}
