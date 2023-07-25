package com.hhwy.pm.qqch.preparation.technique.bimTechPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.QqchBimTechPlan;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.mapper.QqchBimTechPlanMapper;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.service.IQqchBimTechPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:45:57
 * @remark
 */
@Service
public class QqchBimTechPlanServiceImpl implements IQqchBimTechPlanService {

    @Autowired
    private QqchBimTechPlanMapper qqchBimTechPlanMapper;


    public QqchBimTechPlan getQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan) {
        return qqchBimTechPlanMapper.getQqchBimTechPlan(qqchBimTechPlan);
    }

    public List<QqchBimTechPlan> getQqchBimTechPlanList(QqchBimTechPlan qqchBimTechPlan) {
        return qqchBimTechPlanMapper.getQqchBimTechPlanList(qqchBimTechPlan);
    }

    @Transactional
    public int insertQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan) {
        qqchBimTechPlan.setId(IdWorker.createId());
        qqchBimTechPlan.setCreateUser(SecurityUtils.getUserName());
        qqchBimTechPlan.setCreateTime(DateUtils.getNowDate());
        return qqchBimTechPlanMapper.insertQqchBimTechPlan(qqchBimTechPlan);
    }

    @Transactional
    public int insertQqchBimTechPlanList(List<QqchBimTechPlan> qqchBimTechPlanList) {
        for (QqchBimTechPlan qqchBimTechPlan : qqchBimTechPlanList) {
            qqchBimTechPlan.setId(IdWorker.createId());
            qqchBimTechPlan.setCreateUser(SecurityUtils.getUserName());
            qqchBimTechPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchBimTechPlanMapper.insertQqchBimTechPlanList(qqchBimTechPlanList);
    }

    @Transactional
    public int updateQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan) {
        qqchBimTechPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchBimTechPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchBimTechPlanMapper.updateQqchBimTechPlan(qqchBimTechPlan);
    }

    @Transactional
    public int updateQqchBimTechPlanList(List<QqchBimTechPlan> qqchBimTechPlanList) {
        for (QqchBimTechPlan qqchBimTechPlan : qqchBimTechPlanList) {
            qqchBimTechPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchBimTechPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchBimTechPlanMapper.updateQqchBimTechPlanList(qqchBimTechPlanList);
    }

    @Transactional
    public int deleteQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan) {
        qqchBimTechPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchBimTechPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchBimTechPlanMapper.deleteQqchBimTechPlan(qqchBimTechPlan);
    }

    @Transactional
    public int deleteQqchBimTechPlanByPks(List<Long> qqchBimTechPlanPkList) {
        return qqchBimTechPlanMapper.deleteQqchBimTechPlanByPks(qqchBimTechPlanPkList);
    }
}
