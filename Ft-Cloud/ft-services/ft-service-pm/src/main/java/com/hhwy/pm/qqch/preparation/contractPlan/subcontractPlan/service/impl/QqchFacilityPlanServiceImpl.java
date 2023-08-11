package com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.domain.QqchFacilityPlan;
import com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.mapper.QqchFacilityPlanMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.service.IQqchFacilityPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:36:11
 * @remark
 */
@Service
public class QqchFacilityPlanServiceImpl implements IQqchFacilityPlanService {

    @Autowired
    private QqchFacilityPlanMapper qqchFacilityPlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;


    public QqchFacilityPlan getQqchFacilityPlan(QqchFacilityPlan qqchFacilityPlan) {
        return qqchFacilityPlanMapper.getQqchFacilityPlan(qqchFacilityPlan);
    }

    public List<QqchFacilityPlan> getQqchFacilityPlanList(QqchFacilityPlan qqchFacilityPlan) {
        return qqchFacilityPlanMapper.getQqchFacilityPlanList(qqchFacilityPlan);
    }

    @Transactional
    public int insertQqchFacilityPlan(QqchFacilityPlan qqchFacilityPlan) {
        qqchFacilityPlan.setId(IdWorker.createId());
        qqchFacilityPlan.setCreateUser(SecurityUtils.getUserName());
        qqchFacilityPlan.setCreateTime(DateUtils.getNowDate());
        return qqchFacilityPlanMapper.insertQqchFacilityPlan(qqchFacilityPlan);
    }

    @Transactional
    public int insertQqchFacilityPlanList(List<QqchFacilityPlan> qqchFacilityPlanList) {
        for (QqchFacilityPlan qqchFacilityPlan : qqchFacilityPlanList) {
            qqchFacilityPlan.setId(IdWorker.createId());
            qqchFacilityPlan.setCreateUser(SecurityUtils.getUserName());
            qqchFacilityPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchFacilityPlanMapper.insertQqchFacilityPlanList(qqchFacilityPlanList);
    }

    @Transactional
    public int updateQqchFacilityPlan(QqchFacilityPlan qqchFacilityPlan) {
        qqchFacilityPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchFacilityPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchFacilityPlanMapper.updateQqchFacilityPlan(qqchFacilityPlan);
    }

    @Transactional
    public int updateQqchFacilityPlanList(List<QqchFacilityPlan> qqchFacilityPlanList) {
        for (QqchFacilityPlan qqchFacilityPlan : qqchFacilityPlanList) {
            qqchFacilityPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchFacilityPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchFacilityPlanMapper.updateQqchFacilityPlanList(qqchFacilityPlanList);
    }

    @Transactional
    public int deleteQqchFacilityPlan(QqchFacilityPlan qqchFacilityPlan) {
        qqchFacilityPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchFacilityPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchFacilityPlanMapper.deleteQqchFacilityPlan(qqchFacilityPlan);
    }

    @Transactional
    public int deleteQqchFacilityPlanByPks(List<Long> qqchFacilityPlanPkList) {
        return qqchFacilityPlanMapper.deleteQqchFacilityPlanByPks(qqchFacilityPlanPkList);
    }
}
