package com.hhwy.pm.qqch.preparation.quality.duty.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.QqchPersonControlPlan;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.vo.QqchPersonControlPlanVo;
import com.hhwy.pm.qqch.preparation.quality.duty.mapper.QqchPersonControlPlanMapper;
import com.hhwy.pm.qqch.preparation.quality.duty.service.IQqchPersonControlPlanService;
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
 * @date 2023-08-03 14:29:43
 * @remark 9.1.2 人员管控策划
 */
@Service
public class QqchPersonControlPlanServiceImpl implements IQqchPersonControlPlanService {

    @Autowired
    private QqchPersonControlPlanMapper qqchPersonControlPlanMapper;
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
    public QqchPersonControlPlanVo getQqchPersonControlPlanList(BigDecimal version) {
        QqchPersonControlPlanVo vo = new QqchPersonControlPlanVo();
        version = VersionUtil.getVersion("qqch_person_control_plan", version);

        QqchPersonControlPlan qryParam = new QqchPersonControlPlan();
        qryParam.setVersion(version);
        List<QqchPersonControlPlan> list = qqchPersonControlPlanMapper.getQqchPersonControlPlanList(qryParam);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    /**
     * 保存/确认/提交
     *
     * @param voParam
     * @return
     */
    @Transactional
    public void insertQqchPersonControlPlanList(QqchPersonControlPlanVo voParam) {
        // 清空数据库表中数据
        QqchPersonControlPlan deleteParam = new QqchPersonControlPlan();
        deleteParam.setVersion(voParam.getVersion());
        qqchPersonControlPlanMapper.deleteQqchPersonControlPlan(deleteParam);

        String buttonMark = voParam.getButtonMark();
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            for (QqchPersonControlPlan qqchPersonControlPlan : voParam.getList()) {
                qqchPersonControlPlan.setId(IdWorker.createId());
                qqchPersonControlPlan.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchPersonControlPlan.setValid(Valid.YES);
                }
                qqchPersonControlPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchPersonControlPlan.setCreateUserName(SecurityUtils.getUserName());
                qqchPersonControlPlan.setCreateTime(DateUtils.getNowDate());
            }
            qqchPersonControlPlanMapper.insertQqchPersonControlPlanList(voParam.getList());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }
}
