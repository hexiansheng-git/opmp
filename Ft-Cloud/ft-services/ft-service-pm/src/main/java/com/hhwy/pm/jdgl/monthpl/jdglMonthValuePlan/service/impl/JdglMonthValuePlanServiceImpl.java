package com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.domain.JdglMonthValuePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.mapper.JdglMonthValuePlanMapper;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service.IJdglMonthValuePlanService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
@Service
public class JdglMonthValuePlanServiceImpl implements IJdglMonthValuePlanService {

    @Autowired
    private JdglMonthValuePlanMapper jdglMonthValuePlanMapper;


    public JdglMonthValuePlan getJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan) {
        return jdglMonthValuePlanMapper.getJdglMonthValuePlan(jdglMonthValuePlan);
    }

    public List<JdglMonthValuePlan> getJdglMonthValuePlanList(JdglMonthValuePlan jdglMonthValuePlan) {
        Long pid = jdglMonthValuePlan.getPid();
        List<JdglMonthValuePlan> jdglMonthValuePlanList = jdglMonthValuePlanMapper.getJdglMonthValuePlanList(jdglMonthValuePlan);
        List<JdglMonthValuePlan> build = TreeUtil.build(jdglMonthValuePlanList, pid);
        return build;
    }

    /**
     * 查询产值进度&未完&
     * @param yearPlanId
     * @return
     */
    public List<JdglMonthValuePlan> getJdglMonthValuePlanListByPlanId(Long planId) {
        JdglMonthValuePlan jdglMonthValuePlan = new JdglMonthValuePlan();
        jdglMonthValuePlan.setPlanId(planId);

        // 获取清单及wbs关联数据

        // 获取形象进度、总形象进度及wbs关联数据

        // 根据上面2个结果集wbs关联进行清单设计量等计算

        // 获取进度填报中已填报的清单产值数据

        // 树形结构维护

        return getJdglMonthValuePlanList(jdglMonthValuePlan);
    }

    @Transactional
    public int insertJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan) {
        jdglMonthValuePlan.setId(IdWorker.createId());
        jdglMonthValuePlan.setCreateUser(SecurityUtils.getUserName());
        jdglMonthValuePlan.setCreateTime(DateUtils.getNowDate());
        return jdglMonthValuePlanMapper.insertJdglMonthValuePlan(jdglMonthValuePlan);
    }

    @Transactional
    public int insertJdglMonthValuePlanList(List<JdglMonthValuePlan> jdglMonthValuePlanList) {
        for (JdglMonthValuePlan jdglMonthValuePlan : jdglMonthValuePlanList) {
            jdglMonthValuePlan.setId(IdWorker.createId());
            jdglMonthValuePlan.setCreateUser(SecurityUtils.getUserName());
            jdglMonthValuePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglMonthValuePlanMapper.insertJdglMonthValuePlanList(jdglMonthValuePlanList);
    }

    @Transactional
    public int updateJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan) {
        jdglMonthValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMonthValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglMonthValuePlanMapper.updateJdglMonthValuePlan(jdglMonthValuePlan);
    }

    @Transactional
    public int updateJdglMonthValuePlanList(List<JdglMonthValuePlan> jdglMonthValuePlanList) {
        if (!CollectionUtils.isEmpty(jdglMonthValuePlanList)) {
            for (JdglMonthValuePlan jdglMonthValuePlan : jdglMonthValuePlanList) {
                jdglMonthValuePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglMonthValuePlan.setUpdateTime(DateUtils.getNowDate());
            }
            return jdglMonthValuePlanMapper.updateJdglMonthValuePlanList(jdglMonthValuePlanList);
        }
        return 0;
    }

    @Transactional
    public int deleteJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan) {
        jdglMonthValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMonthValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglMonthValuePlanMapper.deleteJdglMonthValuePlan(jdglMonthValuePlan);
    }

    @Override
    public int deleteJdglMonthValuePlanByPlanId(Long planId) {
        JdglMonthValuePlan jdglMonthValuePlan = new JdglMonthValuePlan();
        jdglMonthValuePlan.setPlanId(planId);
        return deleteJdglMonthValuePlan(jdglMonthValuePlan);
    }

    @Transactional
    public int deleteJdglMonthValuePlanByPks(List<Long> jdglMonthValuePlanPkList) {
        return jdglMonthValuePlanMapper.deleteJdglMonthValuePlanByPks(jdglMonthValuePlanPkList);
    }

    @Override
    public List<JdglMonthValuePlan> getBillListByYearAndMonth(String year, String month) {
        return jdglMonthValuePlanMapper.getBillListByYearAndMonth(year, month);
    }
}
