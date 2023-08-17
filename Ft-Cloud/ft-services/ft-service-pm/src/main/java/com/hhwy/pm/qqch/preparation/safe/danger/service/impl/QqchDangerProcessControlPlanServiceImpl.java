package com.hhwy.pm.qqch.preparation.safe.danger.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerList;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerProcessControlPlan;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerProcessControlPlanVo;
import com.hhwy.pm.qqch.preparation.safe.danger.mapper.QqchDangerProcessControlPlanMapper;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerListService;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerProcessControlPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-08-07 14:24:24
 * @remark 8.3.3 危大工程过程管控策划
 */
@Service
public class QqchDangerProcessControlPlanServiceImpl implements IQqchDangerProcessControlPlanService {

    @Autowired
    private QqchDangerProcessControlPlanMapper qqchDangerProcessControlPlanMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchDangerListService qqchDangerListService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchDangerProcessControlPlanVo getQqchDangerProcessControlPlanList(BigDecimal version) {
        QqchDangerProcessControlPlanVo vo = new QqchDangerProcessControlPlanVo();
        version = VersionUtil.getVersion("qqch_danger_process_control_plan", version);

        QqchDangerProcessControlPlan qryParam = new QqchDangerProcessControlPlan();
        qryParam.setVersion(version);
        List<QqchDangerProcessControlPlan> planList = qqchDangerProcessControlPlanMapper
            .getQqchDangerProcessControlPlanList(qryParam);

        // 组装列表
        List<QqchDangerProcessControlPlan> newList = new ArrayList<>();

        // 危大工程清单
        List<QqchDangerList> dangerList = qqchDangerListService.getQqchDangerListList(version).getList();
        for (QqchDangerList qqchDangerList : dangerList) {
            QqchDangerProcessControlPlan qqchDangerProcessControlPlan = new QqchDangerProcessControlPlan();
            for (QqchDangerProcessControlPlan plan : planList) {
                if (plan.getSchemeCode().equals(qqchDangerList.getSchemeCode())) {
                    BeanUtils.copyProperties(plan, qqchDangerProcessControlPlan);
                }
            }
            qqchDangerProcessControlPlan.setSchemeCode(qqchDangerList.getSchemeCode());
            qqchDangerProcessControlPlan.setSchemeName(qqchDangerList.getSchemeName());
            newList.add(qqchDangerProcessControlPlan);
        }

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(newList);
        return vo;
    }


    /**
     * 保存/确认/提交
     *
     * @param voParam
     * @return
     */
    @Transactional
    public void batchSave(QqchDangerProcessControlPlanVo voParam) {
        // 清空数据库表中数据
        QqchDangerProcessControlPlan deleteParam = new QqchDangerProcessControlPlan();
        deleteParam.setVersion(voParam.getVersion());
        qqchDangerProcessControlPlanMapper.deleteQqchDangerProcessControlPlan(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchDangerProcessControlPlan qqchDangerProcessControlPlan : voParam.getList()) {
                qqchDangerProcessControlPlan.setId(IdWorker.createId());
                qqchDangerProcessControlPlan.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchDangerProcessControlPlan.setValid(Valid.YES);
                }
                qqchDangerProcessControlPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchDangerProcessControlPlan.setCreateUserName(SecurityUtils.getUserName());
                qqchDangerProcessControlPlan.setCreateTime(DateUtils.getNowDate());
            }
            qqchDangerProcessControlPlanMapper.insertQqchDangerProcessControlPlanList(voParam.getList());
        }

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    /**
     * 同步数据(废弃)
     *
     * @param voParam
     */
    @Transactional
    public void syncData(QqchDangerProcessControlPlanVo voParam) {
        // 从数据库查出数据
        List<QqchDangerProcessControlPlan> dbList = this.getQqchDangerProcessControlPlanList(voParam.getVersion())
            .getList();

        // 清空数据库表中数据
        QqchDangerProcessControlPlan deleteParam = new QqchDangerProcessControlPlan();
        deleteParam.setVersion(voParam.getVersion());
        qqchDangerProcessControlPlanMapper.deleteQqchDangerProcessControlPlan(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchDangerProcessControlPlan qqchDangerProcessControlPlan : voParam.getList()) {
                qqchDangerProcessControlPlan.setId(IdWorker.createId());
                qqchDangerProcessControlPlan.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchDangerProcessControlPlan.setValid(Valid.YES);
                }
                qqchDangerProcessControlPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchDangerProcessControlPlan.setCreateUserName(SecurityUtils.getUserName());
                qqchDangerProcessControlPlan.setCreateTime(DateUtils.getNowDate());

                if (!CollectionUtils.isEmpty(dbList)) {
                    for (QqchDangerProcessControlPlan db : dbList) {
                        if (db.getSchemeCode().equals(qqchDangerProcessControlPlan.getSchemeCode())) {
                            qqchDangerProcessControlPlan.setAssistUnit(db.getAssistUnit());
                            qqchDangerProcessControlPlan.setPlanStartDate(db.getPlanStartDate());
                            qqchDangerProcessControlPlan.setSchemeFinalizeDate(db.getSchemeFinalizeDate());
                            qqchDangerProcessControlPlan.setThirdDisclosureDate(db.getThirdDisclosureDate());
                            qqchDangerProcessControlPlan.setSafeTrainDate(db.getSafeTrainDate());
                            qqchDangerProcessControlPlan.setPreClassSpeechDate(db.getPreClassSpeechDate());
                            qqchDangerProcessControlPlan.setLeaderExamineFrequency(db.getLeaderExamineFrequency());
                            qqchDangerProcessControlPlan.setLeaderFileGroupId(db.getLeaderFileGroupId());
                            qqchDangerProcessControlPlan.setSpecialExamineFrequency(db.getSpecialExamineFrequency());
                            qqchDangerProcessControlPlan.setSpecialFileGroupId(db.getSpecialFileGroupId());
                            qqchDangerProcessControlPlan.setWhetherFirst(db.getWhetherFirst());
                        }
                    }
                }
            }
            qqchDangerProcessControlPlanMapper.insertQqchDangerProcessControlPlanList(voParam.getList());
        }
    }
}
