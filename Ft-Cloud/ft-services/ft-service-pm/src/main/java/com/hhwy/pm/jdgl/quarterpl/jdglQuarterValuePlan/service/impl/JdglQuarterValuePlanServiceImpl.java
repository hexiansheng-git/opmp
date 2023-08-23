package com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.domain.JdglQuarterValuePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.mapper.JdglQuarterValuePlanMapper;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.service.IJdglQuarterValuePlanService;
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
public class JdglQuarterValuePlanServiceImpl implements IJdglQuarterValuePlanService {

    @Autowired
    private JdglQuarterValuePlanMapper jdglQuarterValuePlanMapper;


    public JdglQuarterValuePlan getJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan) {
        return jdglQuarterValuePlanMapper.getJdglQuarterValuePlan(jdglQuarterValuePlan);
    }

    public List<JdglQuarterValuePlan> getJdglQuarterValuePlanList(JdglQuarterValuePlan jdglQuarterValuePlan) {
        Long pid = jdglQuarterValuePlan.getPid();
        List<JdglQuarterValuePlan> jdglQuarterValuePlanList = jdglQuarterValuePlanMapper.getJdglQuarterValuePlanList(jdglQuarterValuePlan);
        List<JdglQuarterValuePlan> build = TreeUtil.build(jdglQuarterValuePlanList, pid);
        return build;
    }

    /**
     * 查询产值进度&未完&
     * @param planId
     * @return
     */
    public List<JdglQuarterValuePlan> getJdglQuarterValuePlanListByPlanId(Long planId) {
        JdglQuarterValuePlan jdglQuarterValuePlan = new JdglQuarterValuePlan();
        jdglQuarterValuePlan.setPlanId(planId);

        // 获取清单及wbs关联数据

        // 获取形象进度、总形象进度及wbs关联数据

        // 根据上面2个结果集wbs关联进行清单设计量等计算

        // 获取进度填报中已填报的清单产值数据

        // 树形结构维护

        return getJdglQuarterValuePlanList(jdglQuarterValuePlan);
    }

    @Transactional
    public int insertJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan) {
        jdglQuarterValuePlan.setId(IdWorker.createId());
        jdglQuarterValuePlan.setCreateUser(SecurityUtils.getUserName());
        jdglQuarterValuePlan.setCreateTime(DateUtils.getNowDate());
        return jdglQuarterValuePlanMapper.insertJdglQuarterValuePlan(jdglQuarterValuePlan);
    }

    @Transactional
    public int insertJdglQuarterValuePlanList(List<JdglQuarterValuePlan> jdglQuarterValuePlanList) {
        for (JdglQuarterValuePlan jdglQuarterValuePlan : jdglQuarterValuePlanList) {
            jdglQuarterValuePlan.setId(IdWorker.createId());
            jdglQuarterValuePlan.setCreateUser(SecurityUtils.getUserName());
            jdglQuarterValuePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglQuarterValuePlanMapper.insertJdglQuarterValuePlanList(jdglQuarterValuePlanList);
    }

    @Transactional
    public int updateJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan) {
        jdglQuarterValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglQuarterValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglQuarterValuePlanMapper.updateJdglQuarterValuePlan(jdglQuarterValuePlan);
    }

    @Transactional
    public int updateJdglQuarterValuePlanList(List<JdglQuarterValuePlan> jdglQuarterValuePlanList) {
        if(!CollectionUtils.isEmpty(jdglQuarterValuePlanList)) {
            for (JdglQuarterValuePlan jdglQuarterValuePlan : jdglQuarterValuePlanList) {
                jdglQuarterValuePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglQuarterValuePlan.setUpdateTime(DateUtils.getNowDate());
            }
            return jdglQuarterValuePlanMapper.updateJdglQuarterValuePlanList(jdglQuarterValuePlanList);
        }
        return 0;
    }

    @Transactional
    public int deleteJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan) {
        jdglQuarterValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglQuarterValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglQuarterValuePlanMapper.deleteJdglQuarterValuePlan(jdglQuarterValuePlan);
    }

    @Override
    public int deleteJdglQuarterValuePlanByPlanId(Long planId) {
        JdglQuarterValuePlan jdglQuarterValuePlan = new JdglQuarterValuePlan();
        jdglQuarterValuePlan.setPlanId(planId);
        return deleteJdglQuarterValuePlan(jdglQuarterValuePlan);
    }

    @Transactional
    public int deleteJdglQuarterValuePlanByPks(List<Long> jdglQuarterValuePlanPkList) {
        return jdglQuarterValuePlanMapper.deleteJdglQuarterValuePlanByPks(jdglQuarterValuePlanPkList);
    }
}
