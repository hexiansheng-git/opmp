package com.hhwy.pm.qqch.preparation.finance.policy.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchLocalBankSituation;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchLocalBankSituationVo;
import com.hhwy.pm.qqch.preparation.finance.policy.mapper.QqchLocalBankSituationMapper;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchLocalBankSituationService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-08-03 13:45:08
 * @remark 10.2.5当地银行情况描述
 */
@Service
public class QqchLocalBankSituationServiceImpl implements IQqchLocalBankSituationService {

    @Autowired
    private QqchLocalBankSituationMapper qqchLocalBankSituationMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchLocalBankSituationVo getQqchLocalBankSituationList(BigDecimal version) {
        QqchLocalBankSituationVo vo = new QqchLocalBankSituationVo();
        version = VersionUtil.getVersion("qqch_local_bank_situation", version);

        QqchLocalBankSituation qryParam = new QqchLocalBankSituation();
        qryParam.setVersion(version);
        List<QqchLocalBankSituation> list = qqchLocalBankSituationMapper.getQqchLocalBankSituationList(qryParam);

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
    public void batchSave(QqchLocalBankSituationVo voParam) {
        // 清空数据库表中数据
        QqchLocalBankSituation deleteParam = new QqchLocalBankSituation();
        deleteParam.setVersion(voParam.getVersion());
        qqchLocalBankSituationMapper.deleteQqchLocalBankSituation(deleteParam);

        String buttonMark = voParam.getButtonMark();
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            for (QqchLocalBankSituation qqchLocalBankSituation : voParam.getList()) {
                qqchLocalBankSituation.setId(IdWorker.createId());
                qqchLocalBankSituation.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchLocalBankSituation.setValid(Valid.YES);
                }
                qqchLocalBankSituation.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchLocalBankSituation.setCreateUserName(SecurityUtils.getUserName());
                qqchLocalBankSituation.setCreateTime(DateUtils.getNowDate());
            }
            qqchLocalBankSituationMapper.insertQqchLocalBankSituationList(voParam.getList());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }
}
