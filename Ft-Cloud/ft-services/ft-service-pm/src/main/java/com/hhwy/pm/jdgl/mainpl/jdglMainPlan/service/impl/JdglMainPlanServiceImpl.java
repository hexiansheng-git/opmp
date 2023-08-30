package com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.mapper.JdglMainPlanMapper;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:20
 * @remark
 */
@Service
public class JdglMainPlanServiceImpl implements IJdglMainPlanService {

    @Autowired
    private JdglMainPlanMapper jdglMainPlanMapper;

    @Autowired
    private IJdglMainPlanItemService iJdglMainPlanItemService;


    public JdglMainPlan getJdglMainPlan(JdglMainPlan jdglMainPlan) {
        JdglMainPlan jdglMainPlan1 = jdglMainPlanMapper.getJdglMainPlan(jdglMainPlan);
        if(jdglMainPlan1 == null) {
            return jdglMainPlan1;
        }
        List<JdglMainPlanItem> jdglMainPlanItemList = iJdglMainPlanItemService.getJdglMainPlanItemList(new JdglMainPlanItem());
        jdglMainPlan1.setJdglMainPlanItemList(jdglMainPlanItemList);
        return jdglMainPlan1;
    }

    public List<JdglMainPlan> getJdglMainPlanList(JdglMainPlan jdglMainPlan) {
        List<JdglMainPlan> jdglMainPlanList = jdglMainPlanMapper.getJdglMainPlanList(jdglMainPlan);

        if(CollectionUtils.isEmpty(jdglMainPlanList)) return jdglMainPlanList;

        for (JdglMainPlan jdglMainPlan1: jdglMainPlanList) {
            List<JdglMainPlanItem> jdglMainPlanItemList = iJdglMainPlanItemService.getJdglMainPlanItemList(new JdglMainPlanItem());
            jdglMainPlan1.setJdglMainPlanItemList(jdglMainPlanItemList);
        }

        return jdglMainPlanList;
    }

    @Transactional
    public int insertJdglMainPlan(JdglMainPlan jdglMainPlan) {
        jdglMainPlan.setId(IdWorker.createId());
        jdglMainPlan.setCreateUser(SecurityUtils.getUserName());
        jdglMainPlan.setCreateTime(DateUtils.getNowDate());
        return jdglMainPlanMapper.insertJdglMainPlan(jdglMainPlan);
    }

    @Transactional
    public int insertJdglMainPlanList(List<JdglMainPlan> jdglMainPlanList) {
        for (JdglMainPlan jdglMainPlan : jdglMainPlanList) {
            jdglMainPlan.setId(IdWorker.createId());
            jdglMainPlan.setCreateUser(SecurityUtils.getUserName());
            jdglMainPlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglMainPlanMapper.insertJdglMainPlanList(jdglMainPlanList);
    }

    @Transactional
    public int updateJdglMainPlan(JdglMainPlan jdglMainPlan) {
        jdglMainPlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMainPlan.setUpdateTime(DateUtils.getNowDate());
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlan.getJdglMainPlanItemList();
        iJdglMainPlanItemService.updateJdglMainPlanItemList(jdglMainPlanItemList);
        return jdglMainPlanMapper.updateJdglMainPlan(jdglMainPlan);
    }

    @Transactional
    public int updateJdglMainPlanList(List<JdglMainPlan> jdglMainPlanList) {
        for (JdglMainPlan jdglMainPlan : jdglMainPlanList) {
            jdglMainPlan.setUpdateUser(SecurityUtils.getUserName());
            jdglMainPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglMainPlanMapper.updateJdglMainPlanList(jdglMainPlanList);
    }

    @Transactional
    public int deleteJdglMainPlan(JdglMainPlan jdglMainPlan) {
        jdglMainPlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMainPlan.setUpdateTime(DateUtils.getNowDate());
        return jdglMainPlanMapper.deleteJdglMainPlan(jdglMainPlan);
    }

    @Transactional
    public int deleteJdglMainPlanByPks(List<Long> jdglMainPlanPkList) {
        return jdglMainPlanMapper.deleteJdglMainPlanByPks(jdglMainPlanPkList);
    }
}
