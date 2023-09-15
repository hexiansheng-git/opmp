package com.hhwy.pm.qqch.preparation.safe.organ.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.QqchSafeOrganDutyPlan;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchSafeOrganDutyPlanVo;
import com.hhwy.pm.qqch.preparation.safe.organ.mapper.QqchSafeOrganDutyPlanMapper;
import com.hhwy.pm.qqch.preparation.safe.organ.service.IQqchSafeOrganDutyPlanService;
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
 * @date 2023-08-07 13:49:43
 * @remark 8.1.1 安全组织职责策划
 */
@Service
public class QqchSafeOrganDutyPlanServiceImpl implements IQqchSafeOrganDutyPlanService {

    @Autowired
    private QqchSafeOrganDutyPlanMapper qqchSafeOrganDutyPlanMapper;
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
    public QqchSafeOrganDutyPlanVo getQqchSafeOrganDutyPlanList(BigDecimal version) {
        QqchSafeOrganDutyPlanVo vo = new QqchSafeOrganDutyPlanVo();
        version = VersionUtil.getVersion("qqch_safe_organ_duty_plan", version);

        List<QqchSafeOrganDutyPlan> list = this.getNewVersionList(version);
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
    public void batchSave(QqchSafeOrganDutyPlanVo voParam) {
        // 清空数据库表中数据
        QqchSafeOrganDutyPlan deleteParam = new QqchSafeOrganDutyPlan();

        deleteParam.setVersion(voParam.getVersion());
        qqchSafeOrganDutyPlanMapper.deleteQqchSafeOrganDutyPlan(deleteParam);

        String buttonMark = voParam.getButtonMark();

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            for (QqchSafeOrganDutyPlan qqchSafeOrganDutyPlan : voParam.getList()) {
                qqchSafeOrganDutyPlan.setId(IdWorker.createId());
                qqchSafeOrganDutyPlan.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchSafeOrganDutyPlan.setValid(Valid.YES);
                }
                qqchSafeOrganDutyPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchSafeOrganDutyPlan.setCreateUserName(SecurityUtils.getUserName());
                qqchSafeOrganDutyPlan.setCreateTime(DateUtils.getNowDate());
            }
            qqchSafeOrganDutyPlanMapper.insertQqchSafeOrganDutyPlanList(voParam.getList());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    public List<QqchSafeOrganDutyPlan> getNewVersionList(BigDecimal version) {
        QqchSafeOrganDutyPlan qryParam = new QqchSafeOrganDutyPlan();
        qryParam.setVersion(version);
        List<QqchSafeOrganDutyPlan> list = qqchSafeOrganDutyPlanMapper.getQqchSafeOrganDutyPlanList(qryParam);
        return list;
    }

}
