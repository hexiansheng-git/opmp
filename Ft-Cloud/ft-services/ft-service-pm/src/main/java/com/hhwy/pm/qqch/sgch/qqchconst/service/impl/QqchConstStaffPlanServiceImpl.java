package com.hhwy.pm.qqch.sgch.qqchconst.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstJob;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstStaffPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.mapper.QqchConstStaffPlanMapper;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstStaffPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 16:08:24
 * @remark
 */
@Service
public class QqchConstStaffPlanServiceImpl implements IQqchConstStaffPlanService {


    private static final String TN = "qqch_const_staff_plan";

    @Autowired
    private QqchConstStaffPlanMapper qqchConstStaffPlanMapper;


    public QqchConstStaffPlan getQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan) {
        return qqchConstStaffPlanMapper.getQqchConstStaffPlan(qqchConstStaffPlan);
    }

    public List<QqchConstStaffPlan> getQqchConstStaffPlanList(QqchConstStaffPlan qqchConstStaffPlan) {
        return qqchConstStaffPlanMapper.getQqchConstStaffPlanList(qqchConstStaffPlan);
    }

    @Transactional
    public int insertQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan) {
        qqchConstStaffPlan.setId(IdWorker.createId());
        qqchConstStaffPlan.setCreateUser(SecurityUtils.getUserName());
        qqchConstStaffPlan.setCreateTime(DateUtils.getNowDate());
        return qqchConstStaffPlanMapper.insertQqchConstStaffPlan(qqchConstStaffPlan);
    }

    @Transactional
    public int insertQqchConstStaffPlanList(List<QqchConstStaffPlan> qqchConstStaffPlanList) {
        for (QqchConstStaffPlan qqchConstStaffPlan : qqchConstStaffPlanList) {
            qqchConstStaffPlan.setId(IdWorker.createId());
            qqchConstStaffPlan.setCreateUser(SecurityUtils.getUserName());
            qqchConstStaffPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchConstStaffPlanMapper.insertQqchConstStaffPlanList(qqchConstStaffPlanList);
    }

    @Transactional
    public int updateQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan) {
        qqchConstStaffPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchConstStaffPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchConstStaffPlanMapper.updateQqchConstStaffPlan(qqchConstStaffPlan);
    }

    @Transactional
    public int updateQqchConstStaffPlanList(List<QqchConstStaffPlan> qqchConstStaffPlanList) {
        for (QqchConstStaffPlan qqchConstStaffPlan : qqchConstStaffPlanList) {
            qqchConstStaffPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchConstStaffPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchConstStaffPlanMapper.updateQqchConstStaffPlanList(qqchConstStaffPlanList);
    }

    @Transactional
    public int deleteQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan) {
        qqchConstStaffPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchConstStaffPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchConstStaffPlanMapper.deleteQqchConstStaffPlan(qqchConstStaffPlan);
    }

    @Transactional
    public int deleteQqchConstStaffPlanByPks(List<Long> qqchConstStaffPlanPkList) {
        return qqchConstStaffPlanMapper.deleteQqchConstStaffPlanByPks(qqchConstStaffPlanPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void saveList(List<QqchConstStaffPlan> iStaffList) {
        this.qqchConstStaffPlanMapper.insertQqchConstStaffPlanList(iStaffList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchConstStaffPlan> list(QqchConstStaffPlan dto) {
        return this.getQqchConstStaffPlanList(dto);
    }
}
