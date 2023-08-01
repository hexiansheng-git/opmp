package com.hhwy.pm.qqch.preparation.finance.policy.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchMainTaxItemRate;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchMainTaxItemRateVo;
import com.hhwy.pm.qqch.preparation.finance.policy.mapper.QqchMainTaxItemRateMapper;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchMainTaxItemRateService;
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
        this.deleteQqchMainTaxItemRate(deleteParam);

        // 税法
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchMainTaxItemRate qqchMainTaxItemRate : voParam.getList()) {
                qqchMainTaxItemRate.setId(IdWorker.createId());
                qqchMainTaxItemRate.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchMainTaxItemRate.setCreateUserName(SecurityUtils.getUserName());
                qqchMainTaxItemRate.setCreateTime(DateUtils.getNowDate());
            }
            qqchMainTaxItemRateMapper.insertQqchMainTaxItemRateList(voParam.getList());
        }

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Transactional
    public int deleteQqchMainTaxItemRate(QqchMainTaxItemRate qqchMainTaxItemRate) {
        return qqchMainTaxItemRateMapper.deleteQqchMainTaxItemRate(qqchMainTaxItemRate);
    }
}
