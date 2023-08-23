package com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.mapper.JdglYearImagePlanMapper;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.IJdglYearImagePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
@Service
public class JdglYearImagePlanServiceImpl implements IJdglYearImagePlanService {

    @Autowired
    private JdglYearImagePlanMapper jdglYearImagePlanMapper;


    public JdglYearImagePlan getJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
        return jdglYearImagePlanMapper.getJdglYearImagePlan(jdglYearImagePlan);
    }

    public List<JdglYearImagePlan> getJdglYearImagePlanList(JdglYearImagePlan jdglYearImagePlan) {
        Long pid = jdglYearImagePlan.getPid();
        List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearImagePlanMapper.getJdglYearImagePlanList(jdglYearImagePlan);
        if(CollectionUtils.isEmpty(jdglYearImagePlanList)) {
            return jdglYearImagePlanList;
        }
        List<JdglYearImagePlan> build = TreeUtil.build(jdglYearImagePlanList, pid);
        return build;
    }

    public List<JdglYearImagePlan> getJdglYearImagePlanListByYearPlanId(Long yearPlanId) {
        JdglYearImagePlan jdglYearImagePlan = new JdglYearImagePlan();
        jdglYearImagePlan.setYearPlanId(yearPlanId);
        return getJdglYearImagePlanList(jdglYearImagePlan);
    }

    @Transactional
    public int insertJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
        jdglYearImagePlan.setId(IdWorker.createId());
        jdglYearImagePlan.setCreateUser(SecurityUtils.getUserName());
        jdglYearImagePlan.setCreateTime(DateUtils.getNowDate());
        return jdglYearImagePlanMapper.insertJdglYearImagePlan(jdglYearImagePlan);
    }

    @Transactional
    public int insertJdglYearImagePlanList(List<JdglYearImagePlan> jdglYearImagePlanList) {
        for (JdglYearImagePlan jdglYearImagePlan : jdglYearImagePlanList) {
            jdglYearImagePlan.setId(IdWorker.createId());
            jdglYearImagePlan.setCreateUser(SecurityUtils.getUserName());
            jdglYearImagePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglYearImagePlanMapper.insertJdglYearImagePlanList(jdglYearImagePlanList);
    }

    @Transactional
    public int updateJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
        jdglYearImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglYearImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglYearImagePlanMapper.updateJdglYearImagePlan(jdglYearImagePlan);
    }

    @Transactional
    public int updateJdglYearImagePlanList(List<JdglYearImagePlan> jdglYearImagePlanList) {
        if(!CollectionUtils.isEmpty(jdglYearImagePlanList)) {
            for (JdglYearImagePlan jdglYearImagePlan : jdglYearImagePlanList) {
                jdglYearImagePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglYearImagePlan.setUpdateTime(DateUtils.getNowDate());
            }
            return jdglYearImagePlanMapper.updateJdglYearImagePlanList(jdglYearImagePlanList);
        }
        return 0;
    }

    @Transactional
    public int deleteJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
        jdglYearImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglYearImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglYearImagePlanMapper.deleteJdglYearImagePlan(jdglYearImagePlan);
    }

    @Transactional
    public int deleteJdglYearImagePlanByPks(List<Long> jdglYearImagePlanPkList) {
        return jdglYearImagePlanMapper.deleteJdglYearImagePlanByPks(jdglYearImagePlanPkList);
    }

    @Override
    public int deleteJdglYearImagePlanByYearPlanId(Long yearPlanId) {
        JdglYearImagePlan jdglYearImagePlan = new JdglYearImagePlan();
        jdglYearImagePlan.setYearPlanId(yearPlanId);
        return deleteJdglYearImagePlan(jdglYearImagePlan);
    }

    /**
     * 从总进度计划获取数据&未完&
     * @param jdglYearPlanParam
     * @return
     */
    @Override
    public List<JdglYearImagePlan> syncFromTotalPlan(JdglYearPlan jdglYearPlanParam) {

        List<JdglYearImagePlan> returnList = new ArrayList<JdglYearImagePlan>();

        // 最新获取总进度计划数据（根据年份日期区间获取总计划、形象计划及关联wbs数据）

        // 获取当前版本形象计划数据

        // 增修年进度计划数据

        // 维护returnList树结构

        // 修改年进度计划主表引用总体计划的版本号

        return returnList;
    }
}
