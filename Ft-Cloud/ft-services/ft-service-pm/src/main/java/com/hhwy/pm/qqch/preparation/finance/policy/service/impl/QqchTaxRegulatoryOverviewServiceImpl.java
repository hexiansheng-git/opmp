package com.hhwy.pm.qqch.preparation.finance.policy.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchTaxLaw;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchTaxRegulatoryOverview;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchTaxRegulatoryOverviewVo;
import com.hhwy.pm.qqch.preparation.finance.policy.mapper.QqchTaxRegulatoryOverviewMapper;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchTaxLawService;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchTaxRegulatoryOverviewService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    public QqchTaxRegulatoryOverviewVo getQqchTaxRegulatoryOverview(BigDecimal version) {
        QqchTaxRegulatoryOverviewVo vo = new QqchTaxRegulatoryOverviewVo();
        version = VersionUtil.getVersion("qqch_tax_regulatory_overview", version);

        QqchTaxRegulatoryOverview qryOverviewParam = new QqchTaxRegulatoryOverview();
        qryOverviewParam.setVersion(version);
        QqchTaxRegulatoryOverview overview = qqchTaxRegulatoryOverviewMapper
            .getQqchTaxRegulatoryOverview(qryOverviewParam);

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
        // 查询数据库是否存在
        QqchTaxRegulatoryOverview qryParam = new QqchTaxRegulatoryOverview();
        qryParam.setVersion(voParam.getVersion());
        QqchTaxRegulatoryOverview dbOverview = qqchTaxRegulatoryOverviewMapper.getQqchTaxRegulatoryOverview(qryParam);

        QqchTaxRegulatoryOverview overview = voParam.getOverview();

        if (dbOverview == null) {
            overview.setId(IdWorker.createId());
            overview.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            overview.setCreateUserName(SecurityUtils.getUserName());
            overview.setCreateTime(DateUtils.getNowDate());
            qqchTaxRegulatoryOverviewMapper.insertQqchTaxRegulatoryOverview(overview);
        } else {
            overview.setId(dbOverview.getId());
            overview.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            overview.setUpdateTime(DateUtils.getNowDate());
            qqchTaxRegulatoryOverviewMapper.updateQqchTaxRegulatoryOverview(overview);
        }

        // 清空数据库表中税法数据
        QqchTaxLaw deleteParam = new QqchTaxLaw();
        deleteParam.setVersion(voParam.getVersion());
        qqchTaxLawService.deleteQqchTaxLaw(deleteParam);

        // 税法
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            qqchTaxLawService.insertQqchTaxLawList(voParam.getList());
        }

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }
}
