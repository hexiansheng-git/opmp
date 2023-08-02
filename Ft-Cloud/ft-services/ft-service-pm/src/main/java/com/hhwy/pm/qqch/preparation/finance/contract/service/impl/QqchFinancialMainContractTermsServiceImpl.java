package com.hhwy.pm.qqch.preparation.finance.contract.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.finance.contract.domain.QqchFinancialMainContractTerms;
import com.hhwy.pm.qqch.preparation.finance.contract.domain.vo.QqchFinancialMainContractTermsVo;
import com.hhwy.pm.qqch.preparation.finance.contract.mapper.QqchFinancialMainContractTermsMapper;
import com.hhwy.pm.qqch.preparation.finance.contract.service.IQqchFinancialMainContractTermsService;
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
 * @date 2023-08-02 14:24:03
 * @remark 10.1财务相关主合同条款
 */
@Service
public class QqchFinancialMainContractTermsServiceImpl implements IQqchFinancialMainContractTermsService {

    @Autowired
    private QqchFinancialMainContractTermsMapper qqchFinancialMainContractTermsMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    public QqchFinancialMainContractTermsVo getQqchFinancialMainContractTermsList(BigDecimal version) {
        QqchFinancialMainContractTermsVo vo = new QqchFinancialMainContractTermsVo();
        version = VersionUtil.getVersion("qqch_financial_main_contract_terms", version);

        QqchFinancialMainContractTerms qqchFinancialMainContractTerms = new QqchFinancialMainContractTerms();
        qqchFinancialMainContractTerms.setVersion(version);
        List<QqchFinancialMainContractTerms> list = qqchFinancialMainContractTermsMapper
            .getQqchFinancialMainContractTermsList(qqchFinancialMainContractTerms);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    @Transactional
    public void batchSave(QqchFinancialMainContractTermsVo voParam) {
        // 清空数据库表中数据
        QqchFinancialMainContractTerms deleteParam = new QqchFinancialMainContractTerms();
        deleteParam.setVersion(voParam.getVersion());
        qqchFinancialMainContractTermsMapper.deleteQqchFinancialMainContractTerms(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchFinancialMainContractTerms qqchFinancialMainContractTerms : voParam.getList()) {
                qqchFinancialMainContractTerms.setId(IdWorker.createId());
                qqchFinancialMainContractTerms.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchFinancialMainContractTerms.setValid(Valid.YES);
                }
                qqchFinancialMainContractTerms.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchFinancialMainContractTerms.setCreateUserName(SecurityUtils.getUserName());
                qqchFinancialMainContractTerms.setCreateTime(DateUtils.getNowDate());
            }
            qqchFinancialMainContractTermsMapper.insertQqchFinancialMainContractTermsList(voParam.getList());
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
