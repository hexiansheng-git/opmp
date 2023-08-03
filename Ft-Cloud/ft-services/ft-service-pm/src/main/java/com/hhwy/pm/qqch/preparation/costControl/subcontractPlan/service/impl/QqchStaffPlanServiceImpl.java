package com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.domain.QqchStaffPlan;
import com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.mapper.QqchStaffPlanMapper;
import com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.service.IQqchStaffPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:36:01
 * @remark
 */
@Service
public class QqchStaffPlanServiceImpl implements IQqchStaffPlanService {

    @Autowired
    private QqchStaffPlanMapper qqchStaffPlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;


    public QqchStaffPlan getQqchStaffPlan(QqchStaffPlan qqchStaffPlan) {
        return qqchStaffPlanMapper.getQqchStaffPlan(qqchStaffPlan);
    }

    public List<QqchStaffPlan> getQqchStaffPlanList(QqchStaffPlan qqchStaffPlan) {
        return qqchStaffPlanMapper.getQqchStaffPlanList(qqchStaffPlan);
    }

    @Transactional
    public int insertQqchStaffPlan(QqchStaffPlan qqchStaffPlan) {
        qqchStaffPlan.setId(IdWorker.createId());
        qqchStaffPlan.setCreateUser(SecurityUtils.getUserName());
        qqchStaffPlan.setCreateTime(DateUtils.getNowDate());
        return qqchStaffPlanMapper.insertQqchStaffPlan(qqchStaffPlan);
    }

    @Transactional
    public int insertQqchStaffPlanList(List<QqchStaffPlan> qqchStaffPlanList) {
        for (QqchStaffPlan qqchStaffPlan : qqchStaffPlanList) {
            qqchStaffPlan.setId(IdWorker.createId());
            qqchStaffPlan.setCreateUser(SecurityUtils.getUserName());
            qqchStaffPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchStaffPlanMapper.insertQqchStaffPlanList(qqchStaffPlanList);
    }

    @Transactional
    public int updateQqchStaffPlan(QqchStaffPlan qqchStaffPlan) {
        qqchStaffPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchStaffPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchStaffPlanMapper.updateQqchStaffPlan(qqchStaffPlan);
    }

    @Transactional
    public int updateQqchStaffPlanList(List<QqchStaffPlan> qqchStaffPlanList) {
        for (QqchStaffPlan qqchStaffPlan : qqchStaffPlanList) {
            qqchStaffPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchStaffPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchStaffPlanMapper.updateQqchStaffPlanList(qqchStaffPlanList);
    }

    @Transactional
    public int deleteQqchStaffPlan(QqchStaffPlan qqchStaffPlan) {
        qqchStaffPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchStaffPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchStaffPlanMapper.deleteQqchStaffPlan(qqchStaffPlan);
    }

    @Transactional
    public int deleteQqchStaffPlanByPks(List<Long> qqchStaffPlanPkList) {
        return qqchStaffPlanMapper.deleteQqchStaffPlanByPks(qqchStaffPlanPkList);
    }
}
