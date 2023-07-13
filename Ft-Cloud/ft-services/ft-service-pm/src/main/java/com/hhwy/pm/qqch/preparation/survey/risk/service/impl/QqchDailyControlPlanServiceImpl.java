package com.hhwy.pm.qqch.preparation.survey.risk.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchDailyControlPlan;
import com.hhwy.pm.qqch.preparation.survey.risk.mapper.QqchDailyControlPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.risk.service.IQqchDailyControlPlanService;
import org.springframework.stereotype.Service;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-13 11:39:57
 * @remark 日常管控策划
 */
@Service
public class QqchDailyControlPlanServiceImpl implements IQqchDailyControlPlanService {

    @Autowired
    private QqchDailyControlPlanMapper qqchDailyControlPlanMapper;


    public QqchDailyControlPlan getQqchDailyControlPlan(QqchDailyControlPlan qqchDailyControlPlan) {
        return qqchDailyControlPlanMapper.getQqchDailyControlPlan(qqchDailyControlPlan);
    }

    public List<QqchDailyControlPlan> getQqchDailyControlPlanList(QqchDailyControlPlan qqchDailyControlPlan) {
        return qqchDailyControlPlanMapper.getQqchDailyControlPlanList(qqchDailyControlPlan);
    }

    @Transactional
    public int insertQqchDailyControlPlan(QqchDailyControlPlan qqchDailyControlPlan) {
        qqchDailyControlPlan.setId(IdWorker.createId());
        qqchDailyControlPlan.setCreateUser(SecurityUtils.getUserName());
        qqchDailyControlPlan.setCreateTime(DateUtils.getNowDate());
        return qqchDailyControlPlanMapper.insertQqchDailyControlPlan(qqchDailyControlPlan);
    }

    @Transactional
    public int insertQqchDailyControlPlanList(List<QqchDailyControlPlan> qqchDailyControlPlanList) {
        for (QqchDailyControlPlan qqchDailyControlPlan : qqchDailyControlPlanList) {
            qqchDailyControlPlan.setId(IdWorker.createId());
            qqchDailyControlPlan.setCreateUser(SecurityUtils.getUserName());
            qqchDailyControlPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDailyControlPlanMapper.insertQqchDailyControlPlanList(qqchDailyControlPlanList);
    }

    @Transactional
    public int updateQqchDailyControlPlan(QqchDailyControlPlan qqchDailyControlPlan) {
        qqchDailyControlPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchDailyControlPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchDailyControlPlanMapper.updateQqchDailyControlPlan(qqchDailyControlPlan);
    }

    @Transactional
    public int updateQqchDailyControlPlanList(List<QqchDailyControlPlan> qqchDailyControlPlanList) {
        for (QqchDailyControlPlan qqchDailyControlPlan : qqchDailyControlPlanList) {
            qqchDailyControlPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchDailyControlPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchDailyControlPlanMapper.updateQqchDailyControlPlanList(qqchDailyControlPlanList);
    }

    @Transactional
    public int deleteQqchDailyControlPlan(QqchDailyControlPlan qqchDailyControlPlan) {
        qqchDailyControlPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchDailyControlPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchDailyControlPlanMapper.deleteQqchDailyControlPlan(qqchDailyControlPlan);
    }

    @Transactional
    public int deleteQqchDailyControlPlanByPks(List<Long> qqchDailyControlPlanPkList) {
        return qqchDailyControlPlanMapper.deleteQqchDailyControlPlanByPks(qqchDailyControlPlanPkList);
    }
}
