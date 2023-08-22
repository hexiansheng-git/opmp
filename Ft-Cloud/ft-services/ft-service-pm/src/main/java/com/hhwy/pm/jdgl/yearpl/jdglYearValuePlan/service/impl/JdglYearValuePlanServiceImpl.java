package com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.mapper.JdglYearValuePlanMapper;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.service.IJdglYearValuePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.domain.JdglYearValuePlan;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
@Service
public class JdglYearValuePlanServiceImpl implements IJdglYearValuePlanService {

    @Autowired
    private JdglYearValuePlanMapper jdglYearValuePlanMapper;


    public JdglYearValuePlan getJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan) {
        return jdglYearValuePlanMapper.getJdglYearValuePlan(jdglYearValuePlan);
    }

    public List<JdglYearValuePlan> getJdglYearValuePlanList(JdglYearValuePlan jdglYearValuePlan) {
        Long pid = jdglYearValuePlan.getPid();
        List<JdglYearValuePlan> jdglYearValuePlanList = jdglYearValuePlanMapper.getJdglYearValuePlanList(jdglYearValuePlan);
        List<JdglYearValuePlan> build = TreeUtil.build(jdglYearValuePlanList, pid);
        return build;
    }

    /**
     * 查询产值进度&未完&
     * @param yearPlanId
     * @return
     */
    public List<JdglYearValuePlan> getJdglYearValuePlanListByYearPlanId(Long yearPlanId) {
        JdglYearValuePlan jdglYearValuePlan = new JdglYearValuePlan();
        jdglYearValuePlan.setYearPlanId(yearPlanId);

        // 获取清单及wbs关联数据

        // 获取形象进度、总形象进度及wbs关联数据

        // 根据上面2个结果集wbs关联进行清单设计量等计算

        // 获取进度填报中已填报的清单产值数据

        // 树形结构维护

        return getJdglYearValuePlanList(jdglYearValuePlan);
    }

    @Transactional
    public int insertJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan) {
        jdglYearValuePlan.setId(IdWorker.createId());
        jdglYearValuePlan.setCreateUser(SecurityUtils.getUserName());
        jdglYearValuePlan.setCreateTime(DateUtils.getNowDate());
        return jdglYearValuePlanMapper.insertJdglYearValuePlan(jdglYearValuePlan);
    }

    @Transactional
    public int insertJdglYearValuePlanList(List<JdglYearValuePlan> jdglYearValuePlanList) {
        for (JdglYearValuePlan jdglYearValuePlan : jdglYearValuePlanList) {
            jdglYearValuePlan.setId(IdWorker.createId());
            jdglYearValuePlan.setCreateUser(SecurityUtils.getUserName());
            jdglYearValuePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglYearValuePlanMapper.insertJdglYearValuePlanList(jdglYearValuePlanList);
    }

    @Transactional
    public int updateJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan) {
        jdglYearValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglYearValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglYearValuePlanMapper.updateJdglYearValuePlan(jdglYearValuePlan);
    }

    @Transactional
    public int updateJdglYearValuePlanList(List<JdglYearValuePlan> jdglYearValuePlanList) {
        for (JdglYearValuePlan jdglYearValuePlan : jdglYearValuePlanList) {
            jdglYearValuePlan.setUpdateUser(SecurityUtils.getUserName());
            jdglYearValuePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglYearValuePlanMapper.updateJdglYearValuePlanList(jdglYearValuePlanList);
    }

    @Transactional
    public int deleteJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan) {
        jdglYearValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglYearValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglYearValuePlanMapper.deleteJdglYearValuePlan(jdglYearValuePlan);
    }

    @Override
    public int deleteJdglYearValuePlanByYearPlanId(Long yearPlanId) {
        JdglYearValuePlan jdglYearValuePlan = new JdglYearValuePlan();
        jdglYearValuePlan.setYearPlanId(yearPlanId);
        return deleteJdglYearValuePlan(jdglYearValuePlan);
    }

    @Transactional
    public int deleteJdglYearValuePlanByPks(List<Long> jdglYearValuePlanPkList) {
        return jdglYearValuePlanMapper.deleteJdglYearValuePlanByPks(jdglYearValuePlanPkList);
    }
}
