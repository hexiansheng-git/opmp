package com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.domain.JdglWeekImagePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.mapper.JdglWeekImagePlanMapper;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.service.IJdglWeekImagePlanService;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
@Service
public class JdglWeekImagePlanServiceImpl implements IJdglWeekImagePlanService {

    @Autowired
    private JdglWeekImagePlanMapper jdglWeekImagePlanMapper;


    public JdglWeekImagePlan getJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan) {
        return jdglWeekImagePlanMapper.getJdglWeekImagePlan(jdglWeekImagePlan);
    }

    public List<JdglWeekImagePlan> getJdglWeekImagePlanList(JdglWeekImagePlan jdglWeekImagePlan) {
        Long pid = jdglWeekImagePlan.getPid();
        List<JdglWeekImagePlan> jdglWeekImagePlanList = jdglWeekImagePlanMapper.getJdglWeekImagePlanList(jdglWeekImagePlan);
        if(CollectionUtils.isEmpty(jdglWeekImagePlanList)) {
            return jdglWeekImagePlanList;
        }
        List<JdglWeekImagePlan> build = TreeUtil.build(jdglWeekImagePlanList, pid);
        return build;
    }

    public List<JdglWeekImagePlan> getJdglWeekImagePlanListByPlanId(Long planId) {
        JdglWeekImagePlan jdglWeekImagePlan = new JdglWeekImagePlan();
        jdglWeekImagePlan.setPlanId(planId);
        return getJdglWeekImagePlanList(jdglWeekImagePlan);
    }

    @Transactional
    public int insertJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan) {
        jdglWeekImagePlan.setId(IdWorker.createId());
        jdglWeekImagePlan.setCreateUser(SecurityUtils.getUserName());
        jdglWeekImagePlan.setCreateTime(DateUtils.getNowDate());
        return jdglWeekImagePlanMapper.insertJdglWeekImagePlan(jdglWeekImagePlan);
    }

    @Transactional
    public int insertJdglWeekImagePlanList(List<JdglWeekImagePlan> jdglWeekImagePlanList) {
        for (JdglWeekImagePlan jdglWeekImagePlan : jdglWeekImagePlanList) {
            jdglWeekImagePlan.setId(IdWorker.createId());
            jdglWeekImagePlan.setCreateUser(SecurityUtils.getUserName());
            jdglWeekImagePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglWeekImagePlanMapper.insertJdglWeekImagePlanList(jdglWeekImagePlanList);
    }

    @Transactional
    public int updateJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan) {
        jdglWeekImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglWeekImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglWeekImagePlanMapper.updateJdglWeekImagePlan(jdglWeekImagePlan);
    }

    @Transactional
    public int updateJdglWeekImagePlanList(List<JdglWeekImagePlan> jdglWeekImagePlanList) {
        if(!CollectionUtils.isEmpty(jdglWeekImagePlanList)) {
            List<JdglWeekImagePlan> jdglWeekImagePlans = TreeUtil.treeToList(jdglWeekImagePlanList);
            for (JdglWeekImagePlan jdglWeekImagePlan : jdglWeekImagePlans) {
                jdglWeekImagePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglWeekImagePlan.setUpdateTime(DateUtils.getNowDate());
            }
            return jdglWeekImagePlanMapper.updateJdglWeekImagePlanList(jdglWeekImagePlans);
        }
        return 0;
    }

    @Transactional
    public int deleteJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan) {
        jdglWeekImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglWeekImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglWeekImagePlanMapper.deleteJdglWeekImagePlan(jdglWeekImagePlan);
    }

    @Transactional
    public int deleteJdglWeekImagePlanByPks(List<Long> jdglWeekImagePlanPkList) {
        return jdglWeekImagePlanMapper.deleteJdglWeekImagePlanByPks(jdglWeekImagePlanPkList);
    }

    @Override
    public int deleteJdglWeekImagePlanByPlanId(Long planId) {
        JdglWeekImagePlan jdglWeekImagePlan = new JdglWeekImagePlan();
        jdglWeekImagePlan.setPlanId(planId);
        return deleteJdglWeekImagePlan(jdglWeekImagePlan);
    }

    /**
     * 从总进度计划获取数据&未完&
     * @param jdglWeekPlanParam
     * @return
     */
    @Override
    public JdglWeekPlan syncFromTotalPlan(JdglWeekPlan jdglWeekPlanParam) {

        List<JdglWeekImagePlan> returnList = new ArrayList<JdglWeekImagePlan>();

        // 最新获取总进度计划数据（根据年份日期区间获取总计划、形象计划及关联wbs数据）

        // 获取当前版本形象计划数据

        // 增修年进度计划数据

        // 维护returnList树结构

        // 修改年进度计划主表引用总体计划的版本号

        return null;
    }

    @Override
    public List<JdglWeekImagePlan> getWbsListByYearAndWeek(String year, String week) {
        return jdglWeekImagePlanMapper.getWbsListByYearAndWeek(year, week);
    }
}
