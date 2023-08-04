package com.hhwy.pm.qqch.preparation.quality.qc.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.QqchQcImplementPlan;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.vo.QqchQcImplementPlanVo;
import com.hhwy.pm.qqch.preparation.quality.qc.mapper.QqchQcImplementPlanMapper;
import com.hhwy.pm.qqch.preparation.quality.qc.service.IQqchQcImplementPlanService;
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
 * @date 2023-08-04 10:30:39
 * @remark 9.6.2 QC实施计划
 */
@Service
public class QqchQcImplementPlanServiceImpl implements IQqchQcImplementPlanService {

    @Autowired
    private QqchQcImplementPlanMapper qqchQcImplementPlanMapper;
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
    public QqchQcImplementPlanVo getQqchQcImplementPlanList(BigDecimal version) {
        QqchQcImplementPlanVo vo = new QqchQcImplementPlanVo();
        version = VersionUtil.getVersion("qqch_qc_implement_plan", version);

        QqchQcImplementPlan qryParam = new QqchQcImplementPlan();
        qryParam.setVersion(version);
        List<QqchQcImplementPlan> list = qqchQcImplementPlanMapper.getQqchQcImplementPlanList(qryParam);

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
    public void updateQqchQcImplementPlan(QqchQcImplementPlanVo voParam) {
        for (QqchQcImplementPlan qqchQcImplementPlan : voParam.getList()) {
            qqchQcImplementPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchQcImplementPlan.setUpdateTime(DateUtils.getNowDate());
        }
        qqchQcImplementPlanMapper.updateQqchQcImplementPlanList(voParam.getList());

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    /**
     * 课题清单清单数据保存时，将数据同步实施计划
     *
     * @param voParam
     */
    @Transactional
    public void syncData(QqchQcImplementPlanVo voParam) {
        // 查询数据库表中数据
        QqchQcImplementPlan plan = new QqchQcImplementPlan();
        plan.setVersion(voParam.getVersion());
        List<QqchQcImplementPlan> dbList = qqchQcImplementPlanMapper.getQqchQcImplementPlanList(plan);

        // 清空数据库表中数据
        QqchQcImplementPlan deleteParam = new QqchQcImplementPlan();
        deleteParam.setVersion(voParam.getVersion());
        qqchQcImplementPlanMapper.deleteQqchQcImplementPlan(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchQcImplementPlan qqchQcImplementPlan : voParam.getList()) {
                qqchQcImplementPlan.setId(IdWorker.createId());
                qqchQcImplementPlan.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchQcImplementPlan.setValid(Valid.YES);
                }
                qqchQcImplementPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchQcImplementPlan.setCreateUserName(SecurityUtils.getUserName());
                qqchQcImplementPlan.setCreateTime(DateUtils.getNowDate());

                if (!CollectionUtils.isEmpty(dbList)) {
                    for (QqchQcImplementPlan db : dbList) {
                        if (db.getTopicCode().equals(qqchQcImplementPlan.getTopicCode())) {
                            qqchQcImplementPlan.setTopicDirectorId(db.getTopicDirectorId());
                            qqchQcImplementPlan.setTopicDirector(db.getTopicDirector());
                            qqchQcImplementPlan.setTeamName(db.getTeamName());
                            qqchQcImplementPlan.setWbsCode(db.getWbsCode());
                            qqchQcImplementPlan.setWbsName(db.getWbsName());
                            qqchQcImplementPlan.setPlanStartTime(db.getPlanStartTime());
                            qqchQcImplementPlan.setPlanEndTime(db.getPlanEndTime());
                            qqchQcImplementPlan.setExcellenceGoals(db.getExcellenceGoals());
                            qqchQcImplementPlan.setRemark(db.getRemark());
                        }
                    }
                }
            }
            qqchQcImplementPlanMapper.insertQqchQcImplementPlanList(voParam.getList());
        }
    }

}
