package com.hhwy.pm.qqch.preparation.safe.organ.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.QqchSpecialPersonControlPlan;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchSpecialPersonControlPlanVo;
import com.hhwy.pm.qqch.preparation.safe.organ.mapper.QqchSpecialPersonControlPlanMapper;
import com.hhwy.pm.qqch.preparation.safe.organ.service.IQqchSpecialPersonControlPlanService;
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
 * @date 2023-08-07 13:50:54
 * @remark 8.1.3 特种作业人员管控策划
 */
@Service
public class QqchSpecialPersonControlPlanServiceImpl implements IQqchSpecialPersonControlPlanService {

    @Autowired
    private QqchSpecialPersonControlPlanMapper qqchSpecialPersonControlPlanMapper;
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
    public QqchSpecialPersonControlPlanVo getQqchSpecialPersonControlPlanList(BigDecimal version) {
        QqchSpecialPersonControlPlanVo vo = new QqchSpecialPersonControlPlanVo();
        version = VersionUtil.getVersion("qqch_special_person_control_plan", version);

        QqchSpecialPersonControlPlan qryParam = new QqchSpecialPersonControlPlan();
        qryParam.setVersion(version);
        List<QqchSpecialPersonControlPlan> list =
            qqchSpecialPersonControlPlanMapper.getQqchSpecialPersonControlPlanList(qryParam);

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
    public void batchSave(QqchSpecialPersonControlPlanVo voParam) {
        // 清空数据库表中数据
        QqchSpecialPersonControlPlan deleteParam = new QqchSpecialPersonControlPlan();
        deleteParam.setVersion(voParam.getVersion());
        qqchSpecialPersonControlPlanMapper.deleteQqchSpecialPersonControlPlan(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchSpecialPersonControlPlan qqchSpecialPersonControlPlan : voParam.getList()) {
                qqchSpecialPersonControlPlan.setId(IdWorker.createId());
                qqchSpecialPersonControlPlan.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchSpecialPersonControlPlan.setValid(Valid.YES);
                }
                qqchSpecialPersonControlPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchSpecialPersonControlPlan.setCreateUserName(SecurityUtils.getUserName());
                qqchSpecialPersonControlPlan.setCreateTime(DateUtils.getNowDate());
            }
            qqchSpecialPersonControlPlanMapper.insertQqchSpecialPersonControlPlanList(voParam.getList());
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
