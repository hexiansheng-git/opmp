package com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.mapper.JdglMonthImagePlanMapper;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.IJdglMonthImagePlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
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
public class JdglMonthImagePlanServiceImpl implements IJdglMonthImagePlanService {

    @Autowired
    private JdglMonthImagePlanMapper jdglMonthImagePlanMapper;


    public JdglMonthImagePlan getJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan) {
        return jdglMonthImagePlanMapper.getJdglMonthImagePlan(jdglMonthImagePlan);
    }

    public List<JdglMonthImagePlan> getJdglMonthImagePlanList(JdglMonthImagePlan jdglMonthImagePlan) {
        Long pid = jdglMonthImagePlan.getPid();
        List<JdglMonthImagePlan> jdglMonthImagePlanList = jdglMonthImagePlanMapper.getJdglMonthImagePlanList(jdglMonthImagePlan);
        if(CollectionUtils.isEmpty(jdglMonthImagePlanList)) {
            return jdglMonthImagePlanList;
        }
        List<JdglMonthImagePlan> build = TreeUtil.build(jdglMonthImagePlanList, pid);
        return build;
    }

    public List<JdglMonthImagePlan> getJdglMonthImagePlanListByPlanId(Long planId) {
        JdglMonthImagePlan jdglMonthImagePlan = new JdglMonthImagePlan();
        jdglMonthImagePlan.setPlanId(planId);
        return getJdglMonthImagePlanList(jdglMonthImagePlan);
    }

    @Transactional
    public int insertJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan) {
        jdglMonthImagePlan.setId(IdWorker.createId());
        jdglMonthImagePlan.setCreateUser(SecurityUtils.getUserName());
        jdglMonthImagePlan.setCreateTime(DateUtils.getNowDate());
        return jdglMonthImagePlanMapper.insertJdglMonthImagePlan(jdglMonthImagePlan);
    }

    @Transactional
    public int insertJdglMonthImagePlanList(List<JdglMonthImagePlan> jdglMonthImagePlanList) {
        for (JdglMonthImagePlan jdglMonthImagePlan : jdglMonthImagePlanList) {
            jdglMonthImagePlan.setId(IdWorker.createId());
            jdglMonthImagePlan.setCreateUser(SecurityUtils.getUserName());
            jdglMonthImagePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglMonthImagePlanMapper.insertJdglMonthImagePlanList(jdglMonthImagePlanList);
    }

    @Transactional
    public int updateJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan) {
        jdglMonthImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMonthImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglMonthImagePlanMapper.updateJdglMonthImagePlan(jdglMonthImagePlan);
    }

    @Transactional
    public int updateJdglMonthImagePlanList(List<JdglMonthImagePlan> jdglMonthImagePlanList) {
        if(!CollectionUtils.isEmpty(jdglMonthImagePlanList)) {
            List<JdglMonthImagePlan> jdglMonthImagePlans = TreeUtil.treeToList(jdglMonthImagePlanList);
            for (JdglMonthImagePlan jdglMonthImagePlan : jdglMonthImagePlans) {
                jdglMonthImagePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglMonthImagePlan.setUpdateTime(DateUtils.getNowDate());
            }
            return jdglMonthImagePlanMapper.updateJdglMonthImagePlanList(jdglMonthImagePlans);
        }

        return 0;
    }

    @Transactional
    public int deleteJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan) {
        jdglMonthImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMonthImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglMonthImagePlanMapper.deleteJdglMonthImagePlan(jdglMonthImagePlan);
    }

    @Transactional
    public int deleteJdglMonthImagePlanByPks(List<Long> jdglMonthImagePlanPkList) {
        return jdglMonthImagePlanMapper.deleteJdglMonthImagePlanByPks(jdglMonthImagePlanPkList);
    }

    @Override
    public int deleteJdglMonthImagePlanByPlanId(Long planId) {
        JdglMonthImagePlan jdglMonthImagePlan = new JdglMonthImagePlan();
        jdglMonthImagePlan.setPlanId(planId);
        return deleteJdglMonthImagePlan(jdglMonthImagePlan);
    }

    /**
     * 从总进度计划获取数据&未完&
     * @param jdglMonthPlanParam
     * @return
     */
    @Override
    public List<JdglMonthImagePlan> syncFromTotalPlan(JdglMonthPlan jdglMonthPlanParam) {

        List<JdglMonthImagePlan> returnList = new ArrayList<JdglMonthImagePlan>();

        // 最新获取总进度计划数据（根据年份日期区间获取总计划、形象计划及关联wbs数据）

        // 获取当前版本形象计划数据

        // 增修年进度计划数据

        // 维护returnList树结构

        // 修改年进度计划主表引用总体计划的版本号

        return returnList;
    }
}
