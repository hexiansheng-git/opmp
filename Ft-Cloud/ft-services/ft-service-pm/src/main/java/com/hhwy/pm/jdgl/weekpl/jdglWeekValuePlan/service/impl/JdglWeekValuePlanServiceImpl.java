package com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.domain.JdglWeekValuePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.mapper.JdglWeekValuePlanMapper;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.service.IJdglWeekValuePlanService;
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
public class JdglWeekValuePlanServiceImpl implements IJdglWeekValuePlanService {

    @Autowired
    private JdglWeekValuePlanMapper jdglWeekValuePlanMapper;


    public JdglWeekValuePlan getJdglWeekValuePlan(JdglWeekValuePlan jdglWeekValuePlan) {
        return jdglWeekValuePlanMapper.getJdglWeekValuePlan(jdglWeekValuePlan);
    }

    public List<JdglWeekValuePlan> getJdglWeekValuePlanList(JdglWeekValuePlan jdglWeekValuePlan) {
        Long pid = jdglWeekValuePlan.getPid();
        List<JdglWeekValuePlan> jdglWeekValuePlanList = jdglWeekValuePlanMapper.getJdglWeekValuePlanList(jdglWeekValuePlan);
        List<JdglWeekValuePlan> build = TreeUtil.build(jdglWeekValuePlanList, pid);
        return build;
    }

    /**
     * 查询产值进度&未完&
     * @param yearPlanId
     * @return
     */
    public List<JdglWeekValuePlan> getJdglWeekValuePlanListByPlanId(Long planId) {
        JdglWeekValuePlan jdglWeekValuePlan = new JdglWeekValuePlan();
        jdglWeekValuePlan.setPlanId(planId);

        // 获取清单及wbs关联数据

        // 获取形象进度、总形象进度及wbs关联数据

        // 根据上面2个结果集wbs关联进行清单设计量等计算

        // 获取进度填报中已填报的清单产值数据

        // 树形结构维护

        return getJdglWeekValuePlanList(jdglWeekValuePlan);
    }

    @Transactional
    public int insertJdglWeekValuePlan(JdglWeekValuePlan jdglWeekValuePlan) {
        jdglWeekValuePlan.setId(IdWorker.createId());
        jdglWeekValuePlan.setCreateUser(SecurityUtils.getUserName());
        jdglWeekValuePlan.setCreateTime(DateUtils.getNowDate());
        return jdglWeekValuePlanMapper.insertJdglWeekValuePlan(jdglWeekValuePlan);
    }

    @Transactional
    public int insertJdglWeekValuePlanList(List<JdglWeekValuePlan> jdglWeekValuePlanList) {
        for (JdglWeekValuePlan jdglWeekValuePlan : jdglWeekValuePlanList) {
            jdglWeekValuePlan.setId(IdWorker.createId());
            jdglWeekValuePlan.setCreateUser(SecurityUtils.getUserName());
            jdglWeekValuePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglWeekValuePlanMapper.insertJdglWeekValuePlanList(jdglWeekValuePlanList);
    }

    @Transactional
    public int updateJdglWeekValuePlan(JdglWeekValuePlan jdglWeekValuePlan) {
        jdglWeekValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglWeekValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglWeekValuePlanMapper.updateJdglWeekValuePlan(jdglWeekValuePlan);
    }

    @Transactional
    public int updateJdglWeekValuePlanList(List<JdglWeekValuePlan> jdglWeekValuePlanList) {
        if(!CollectionUtils.isEmpty(jdglWeekValuePlanList)) {
            for (JdglWeekValuePlan jdglWeekValuePlan : jdglWeekValuePlanList) {
                jdglWeekValuePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglWeekValuePlan.setUpdateTime(DateUtils.getNowDate());
            }
            return jdglWeekValuePlanMapper.updateJdglWeekValuePlanList(jdglWeekValuePlanList);
        }
        return 0;
    }

    @Transactional
    public int deleteJdglWeekValuePlan(JdglWeekValuePlan jdglWeekValuePlan) {
        jdglWeekValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglWeekValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglWeekValuePlanMapper.deleteJdglWeekValuePlan(jdglWeekValuePlan);
    }

    @Override
    public int deleteJdglWeekValuePlanByPlanId(Long planId) {
        JdglWeekValuePlan jdglWeekValuePlan = new JdglWeekValuePlan();
        jdglWeekValuePlan.setPlanId(planId);
        return deleteJdglWeekValuePlan(jdglWeekValuePlan);
    }

    @Transactional
    public int deleteJdglWeekValuePlanByPks(List<Long> jdglWeekValuePlanPkList) {
        return jdglWeekValuePlanMapper.deleteJdglWeekValuePlanByPks(jdglWeekValuePlanPkList);
    }
}
