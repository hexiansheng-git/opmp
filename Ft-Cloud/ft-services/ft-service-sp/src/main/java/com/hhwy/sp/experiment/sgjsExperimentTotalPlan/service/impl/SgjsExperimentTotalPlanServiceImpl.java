package com.hhwy.sp.experiment.sgjsExperimentTotalPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.domain.SgjsExperimentTotalPlan;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.mapper.SgjsExperimentTotalPlanMapper;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.service.ISgjsExperimentTotalPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lcf--试验总体计划
 * @date 2023-12-11 10:00:11
 * @remark
 */
@Service
public class SgjsExperimentTotalPlanServiceImpl implements ISgjsExperimentTotalPlanService{

    @Autowired
    private SgjsExperimentTotalPlanMapper sgjsExperimentTotalPlanMapper;


    public SgjsExperimentTotalPlan getSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan) {
        return sgjsExperimentTotalPlanMapper.getSgjsExperimentTotalPlan(sgjsExperimentTotalPlan);
    }

    public List<SgjsExperimentTotalPlan> getSgjsExperimentTotalPlanList(SgjsExperimentTotalPlan sgjsExperimentTotalPlan) {
        return sgjsExperimentTotalPlanMapper.getSgjsExperimentTotalPlanList(sgjsExperimentTotalPlan);
    }

    @Transactional
    public int insertSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan) {
        sgjsExperimentTotalPlan.setId(IdWorker.createId());
        sgjsExperimentTotalPlan.setCreateUser(SecurityUtils.getUserName());
        sgjsExperimentTotalPlan.setCreateTime(DateUtils.getNowDate());
        return sgjsExperimentTotalPlanMapper.insertSgjsExperimentTotalPlan(sgjsExperimentTotalPlan);
    }

    @Transactional
    public int insertSgjsExperimentTotalPlanList(List<SgjsExperimentTotalPlan> sgjsExperimentTotalPlanList) {
        for (SgjsExperimentTotalPlan sgjsExperimentTotalPlan : sgjsExperimentTotalPlanList) {
            sgjsExperimentTotalPlan.setId(IdWorker.createId());
            sgjsExperimentTotalPlan.setCreateUser(SecurityUtils.getUserName());
            sgjsExperimentTotalPlan.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentTotalPlanMapper.insertSgjsExperimentTotalPlanList(sgjsExperimentTotalPlanList);
    }

    @Transactional
    public int updateSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan) {
        sgjsExperimentTotalPlan.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentTotalPlan.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentTotalPlanMapper.updateSgjsExperimentTotalPlan(sgjsExperimentTotalPlan);
    }

    @Transactional
    public int updateSgjsExperimentTotalPlanList(List<SgjsExperimentTotalPlan> sgjsExperimentTotalPlanList) {
        for (SgjsExperimentTotalPlan sgjsExperimentTotalPlan : sgjsExperimentTotalPlanList) {
            sgjsExperimentTotalPlan.setUpdateUser(SecurityUtils.getUserName());
            sgjsExperimentTotalPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentTotalPlanMapper.updateSgjsExperimentTotalPlanList(sgjsExperimentTotalPlanList);
    }

    @Transactional
    public int deleteSgjsExperimentTotalPlan(SgjsExperimentTotalPlan sgjsExperimentTotalPlan) {
        sgjsExperimentTotalPlan.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentTotalPlan.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentTotalPlanMapper.deleteSgjsExperimentTotalPlan(sgjsExperimentTotalPlan);
    }

    @Transactional
    public int deleteSgjsExperimentTotalPlanByPks(List<Long> sgjsExperimentTotalPlanPkList) {
        return sgjsExperimentTotalPlanMapper.deleteSgjsExperimentTotalPlanByPks(sgjsExperimentTotalPlanPkList);
    }
}
