package com.hhwy.pm.qqch.preparation.safe.cost.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.cost.domain.QqchSafeMeasureCostPlan;
import com.hhwy.pm.qqch.preparation.safe.cost.domain.vo.QqchSafeMeasureCostPlanVo;
import com.hhwy.pm.qqch.preparation.safe.cost.mapper.QqchSafeMeasureCostPlanMapper;
import com.hhwy.pm.qqch.preparation.safe.cost.service.IQqchSafeMeasureCostPlanService;
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
 * @date 2023-08-10 16:38:50
 * @remark 8.11 安全文明措施费策划
 */
@Service
public class QqchSafeMeasureCostPlanServiceImpl implements IQqchSafeMeasureCostPlanService {

    @Autowired
    private QqchSafeMeasureCostPlanMapper qqchSafeMeasureCostPlanMapper;
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
    public QqchSafeMeasureCostPlanVo getQqchSafeMeasureCostPlanList(BigDecimal version) {
        QqchSafeMeasureCostPlanVo vo = new QqchSafeMeasureCostPlanVo();
        version = VersionUtil.getVersion("qqch_safe_measure_cost_plan", version);

        QqchSafeMeasureCostPlan qryParam = new QqchSafeMeasureCostPlan();
        qryParam.setVersion(version);
        List<QqchSafeMeasureCostPlan> list = qqchSafeMeasureCostPlanMapper.getQqchSafeMeasureCostPlanList(qryParam);

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
    public void batchSave(QqchSafeMeasureCostPlanVo voParam) {
        // 清空数据库表中数据
        QqchSafeMeasureCostPlan deleteParam = new QqchSafeMeasureCostPlan();
        deleteParam.setVersion(voParam.getVersion());
        qqchSafeMeasureCostPlanMapper.deleteQqchSafeMeasureCostPlan(deleteParam);

        String buttonMark = voParam.getButtonMark();
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            for (QqchSafeMeasureCostPlan qqchSafeMeasureCostPlan : voParam.getList()) {
                qqchSafeMeasureCostPlan.setId(IdWorker.createId());
                qqchSafeMeasureCostPlan.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchSafeMeasureCostPlan.setValid(Valid.YES);
                }
                qqchSafeMeasureCostPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchSafeMeasureCostPlan.setCreateUserName(SecurityUtils.getUserName());
                qqchSafeMeasureCostPlan.setCreateTime(DateUtils.getNowDate());
            }
            qqchSafeMeasureCostPlanMapper.insertQqchSafeMeasureCostPlanList(voParam.getList());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }
}
