package com.hhwy.pm.qqch.preparation.safe.risk.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskControlPlan;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.QqchSafeRiskControlPlanVo;
import com.hhwy.pm.qqch.preparation.safe.risk.mapper.QqchSafeRiskControlPlanMapper;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskControlPlanService;
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
 * @date 2023-08-07 13:53:33
 * @remark 8.2.3 安全风险过程管控策划
 */
@Service
public class QqchSafeRiskControlPlanServiceImpl implements IQqchSafeRiskControlPlanService {

    @Autowired
    private QqchSafeRiskControlPlanMapper qqchSafeRiskControlPlanMapper;
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
    public QqchSafeRiskControlPlanVo getQqchSafeRiskControlPlanList(BigDecimal version) {
        QqchSafeRiskControlPlanVo vo = new QqchSafeRiskControlPlanVo();
        version = VersionUtil.getVersion("qqch_safe_risk_control_plan", version);

        QqchSafeRiskControlPlan qryParam = new QqchSafeRiskControlPlan();
        qryParam.setVersion(version);
        List<QqchSafeRiskControlPlan> list = qqchSafeRiskControlPlanMapper.getQqchSafeRiskControlPlanList(qryParam);

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
    public void batchSave(QqchSafeRiskControlPlanVo voParam) {
        // 清空数据库表中数据
        QqchSafeRiskControlPlan deleteParam = new QqchSafeRiskControlPlan();
        deleteParam.setVersion(voParam.getVersion());
        qqchSafeRiskControlPlanMapper.deleteQqchSafeRiskControlPlan(deleteParam);

        String buttonMark = voParam.getButtonMark();
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            for (QqchSafeRiskControlPlan qqchSafeRiskControlPlan : voParam.getList()) {
                qqchSafeRiskControlPlan.setId(IdWorker.createId());
                qqchSafeRiskControlPlan.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchSafeRiskControlPlan.setValid(Valid.YES);
                }
                qqchSafeRiskControlPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchSafeRiskControlPlan.setCreateUserName(SecurityUtils.getUserName());
                qqchSafeRiskControlPlan.setCreateTime(DateUtils.getNowDate());
            }
            qqchSafeRiskControlPlanMapper.insertQqchSafeRiskControlPlanList(voParam.getList());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }
}
