package com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.mapper.JdglQuarterImagePlanMapper;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service.IJdglQuarterImagePlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
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
public class JdglQuarterImagePlanServiceImpl implements IJdglQuarterImagePlanService {

    @Autowired
    private JdglQuarterImagePlanMapper jdglQuarterImagePlanMapper;


    public JdglQuarterImagePlan getJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan) {
        return jdglQuarterImagePlanMapper.getJdglQuarterImagePlan(jdglQuarterImagePlan);
    }

    public List<JdglQuarterImagePlan> getJdglQuarterImagePlanList(JdglQuarterImagePlan jdglQuarterImagePlan) {
        Long pid = jdglQuarterImagePlan.getPid();
        List<JdglQuarterImagePlan> jdglQuarterImagePlanList = jdglQuarterImagePlanMapper.getJdglQuarterImagePlanList(jdglQuarterImagePlan);
        if(CollectionUtils.isEmpty(jdglQuarterImagePlanList)) {
            return jdglQuarterImagePlanList;
        }
        List<JdglQuarterImagePlan> build = TreeUtil.build(jdglQuarterImagePlanList, pid);
        return build;
    }

    public List<JdglQuarterImagePlan> getJdglQuarterImagePlanListByPlanId(Long planId) {
        JdglQuarterImagePlan jdglQuarterImagePlan = new JdglQuarterImagePlan();
        jdglQuarterImagePlan.setPlanId(planId);
        return getJdglQuarterImagePlanList(jdglQuarterImagePlan);
    }

    @Transactional
    public int insertJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan) {
        jdglQuarterImagePlan.setId(IdWorker.createId());
        jdglQuarterImagePlan.setCreateUser(SecurityUtils.getUserName());
        jdglQuarterImagePlan.setCreateTime(DateUtils.getNowDate());
        return jdglQuarterImagePlanMapper.insertJdglQuarterImagePlan(jdglQuarterImagePlan);
    }

    @Transactional
    public int insertJdglQuarterImagePlanList(List<JdglQuarterImagePlan> jdglQuarterImagePlanList) {
        for (JdglQuarterImagePlan jdglQuarterImagePlan : jdglQuarterImagePlanList) {
            jdglQuarterImagePlan.setId(IdWorker.createId());
            jdglQuarterImagePlan.setCreateUser(SecurityUtils.getUserName());
            jdglQuarterImagePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglQuarterImagePlanMapper.insertJdglQuarterImagePlanList(jdglQuarterImagePlanList);
    }

    @Transactional
    public int updateJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan) {
        jdglQuarterImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglQuarterImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglQuarterImagePlanMapper.updateJdglQuarterImagePlan(jdglQuarterImagePlan);
    }

    @Transactional
    public int updateJdglQuarterImagePlanList(List<JdglQuarterImagePlan> jdglQuarterImagePlanList) {
        if(!CollectionUtils.isEmpty(jdglQuarterImagePlanList)) {
            for (JdglQuarterImagePlan jdglQuarterImagePlan : jdglQuarterImagePlanList) {
                jdglQuarterImagePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglQuarterImagePlan.setUpdateTime(DateUtils.getNowDate());
            }
            return jdglQuarterImagePlanMapper.updateJdglQuarterImagePlanList(jdglQuarterImagePlanList);
        }
        return 0;
    }

    @Transactional
    public int deleteJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan) {
        jdglQuarterImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglQuarterImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglQuarterImagePlanMapper.deleteJdglQuarterImagePlan(jdglQuarterImagePlan);
    }

    @Transactional
    public int deleteJdglQuarterImagePlanByPks(List<Long> jdglQuarterImagePlanPkList) {
        return jdglQuarterImagePlanMapper.deleteJdglQuarterImagePlanByPks(jdglQuarterImagePlanPkList);
    }

    @Override
    public int deleteJdglQuarterImagePlanByPlanId(Long planId) {
        JdglQuarterImagePlan jdglQuarterImagePlan = new JdglQuarterImagePlan();
        jdglQuarterImagePlan.setPlanId(planId);
        return deleteJdglQuarterImagePlan(jdglQuarterImagePlan);
    }

    /**
     * 从总进度计划获取数据&未完&
     * @param jdglQuarterPlanParam
     * @return
     */
    @Override
    public List<JdglQuarterImagePlan> syncFromTotalPlan(JdglQuarterPlan jdglQuarterPlanParam) {

        List<JdglQuarterImagePlan> returnList = new ArrayList<JdglQuarterImagePlan>();

        // 最新获取总进度计划数据（根据年份日期区间获取总计划、形象计划及关联wbs数据）

        // 获取当前版本形象计划数据

        // 增修年进度计划数据

        // 维护returnList树结构

        // 修改年进度计划主表引用总体计划的版本号

        return returnList;
    }
}
