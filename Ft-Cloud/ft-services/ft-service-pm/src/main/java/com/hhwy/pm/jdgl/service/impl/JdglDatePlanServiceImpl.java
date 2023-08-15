package com.hhwy.pm.jdgl.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.domain.JdglDatePlan;
import com.hhwy.pm.jdgl.mapper.JdglDatePlanMapper;
import com.hhwy.pm.jdgl.service.IJdglDatePlanService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author fushudong
 * @date 2023-08-14 17:55:25
 * @remark
 */
@Service
public class JdglDatePlanServiceImpl implements IJdglDatePlanService {

    @Autowired
    private JdglDatePlanMapper jdglDatePlanMapper;


    public JdglDatePlan getJdglDatePlan(JdglDatePlan jdglDatePlan) {
        return jdglDatePlanMapper.getJdglDatePlan(jdglDatePlan);
    }

    public List<JdglDatePlan> getJdglDatePlanList(JdglDatePlan jdglDatePlan) {
        return jdglDatePlanMapper.getJdglDatePlanList(jdglDatePlan);
    }

    @Transactional
    public int insertJdglDatePlan(JdglDatePlan jdglDatePlan) {
        jdglDatePlan.setId(IdWorker.createId());
        jdglDatePlan.setCreateUser(SecurityUtils.getUserName());
        jdglDatePlan.setCreateTime(DateUtils.getNowDate());
        return jdglDatePlanMapper.insertJdglDatePlan(jdglDatePlan);
    }

    @Transactional
    public int insertJdglDatePlanList(List<JdglDatePlan> jdglDatePlanList) {
        for (JdglDatePlan jdglDatePlan : jdglDatePlanList) {
            jdglDatePlan.setId(IdWorker.createId());
            jdglDatePlan.setCreateUser(SecurityUtils.getUserName());
            jdglDatePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglDatePlanMapper.insertJdglDatePlanList(jdglDatePlanList);
    }

    @Transactional
    public int updateJdglDatePlan(JdglDatePlan jdglDatePlan) {
        jdglDatePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglDatePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglDatePlanMapper.updateJdglDatePlan(jdglDatePlan);
    }

    @Transactional
    public int updateJdglDatePlanList(List<JdglDatePlan> jdglDatePlanList) {
        for (JdglDatePlan jdglDatePlan : jdglDatePlanList) {
            jdglDatePlan.setUpdateUser(SecurityUtils.getUserName());
            jdglDatePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglDatePlanMapper.updateJdglDatePlanList(jdglDatePlanList);
    }

    @Transactional
    public int deleteJdglDatePlan(JdglDatePlan jdglDatePlan) {
        jdglDatePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglDatePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglDatePlanMapper.deleteJdglDatePlan(jdglDatePlan);
    }

    @Transactional
    public int deleteJdglDatePlanByPks(List<Long> jdglDatePlanPkList) {
        return jdglDatePlanMapper.deleteJdglDatePlanByPks(jdglDatePlanPkList);
    }
}
