package com.hhwy.pm.qqch.preparation.technique.techTrainPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.domain.QqchTechTrainPlan;
import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.mapper.QqchTechTrainPlanMapper;
import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.service.IQqchTechTrainPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:57:39
 * @remark
 */
@Service
public class QqchTechTrainPlanServiceImpl implements IQqchTechTrainPlanService {

    @Autowired
    private QqchTechTrainPlanMapper qqchTechTrainPlanMapper;


    public QqchTechTrainPlan getQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan) {
        return qqchTechTrainPlanMapper.getQqchTechTrainPlan(qqchTechTrainPlan);
    }

    public List<QqchTechTrainPlan> getQqchTechTrainPlanList(QqchTechTrainPlan qqchTechTrainPlan) {
        return qqchTechTrainPlanMapper.getQqchTechTrainPlanList(qqchTechTrainPlan);
    }

    @Transactional
    public int insertQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan) {
        qqchTechTrainPlan.setId(IdWorker.createId());
        qqchTechTrainPlan.setCreateUser(SecurityUtils.getUserName());
        qqchTechTrainPlan.setCreateTime(DateUtils.getNowDate());
        return qqchTechTrainPlanMapper.insertQqchTechTrainPlan(qqchTechTrainPlan);
    }

    @Transactional
    public int insertQqchTechTrainPlanList(List<QqchTechTrainPlan> qqchTechTrainPlanList) {
        for (QqchTechTrainPlan qqchTechTrainPlan : qqchTechTrainPlanList) {
            qqchTechTrainPlan.setId(IdWorker.createId());
            qqchTechTrainPlan.setCreateUser(SecurityUtils.getUserName());
            qqchTechTrainPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTechTrainPlanMapper.insertQqchTechTrainPlanList(qqchTechTrainPlanList);
    }

    @Transactional
    public int updateQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan) {
        qqchTechTrainPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchTechTrainPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchTechTrainPlanMapper.updateQqchTechTrainPlan(qqchTechTrainPlan);
    }

    @Transactional
    public int updateQqchTechTrainPlanList(List<QqchTechTrainPlan> qqchTechTrainPlanList) {
        for (QqchTechTrainPlan qqchTechTrainPlan : qqchTechTrainPlanList) {
            qqchTechTrainPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchTechTrainPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTechTrainPlanMapper.updateQqchTechTrainPlanList(qqchTechTrainPlanList);
    }

    @Transactional
    public int deleteQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan) {
        qqchTechTrainPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchTechTrainPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchTechTrainPlanMapper.deleteQqchTechTrainPlan(qqchTechTrainPlan);
    }

    @Transactional
    public int deleteQqchTechTrainPlanByPks(List<Long> qqchTechTrainPlanPkList) {
        return qqchTechTrainPlanMapper.deleteQqchTechTrainPlanByPks(qqchTechTrainPlanPkList);
    }
}
