package com.hhwy.pm.qqch.preparation.workPlanning.service.impl;

import java.math.BigDecimal;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlan;
import com.hhwy.pm.qqch.preparation.workPlanning.mapper.QqchWorkPlanningBuildPlanMapper;
import com.hhwy.pm.qqch.preparation.workPlanning.service.IQqchWorkPlanningBuildPlanService;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hhwy.utils.idworker.IdWorker;

/**
 * @author zq
 * @date 2023-07-19 11:30:27
 * @remark 
 */
@Service
public class QqchWorkPlanningBuildPlanServiceImpl implements IQqchWorkPlanningBuildPlanService {

    @Autowired
    private QqchWorkPlanningBuildPlanMapper qqchWorkPlanningBuildPlanMapper;

                                                                                                                                                                                                                                                                                                                
    public QqchWorkPlanningBuildPlan getQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan) {
        return qqchWorkPlanningBuildPlanMapper.getQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlan);
    }

    public List<QqchWorkPlanningBuildPlan> getQqchWorkPlanningBuildPlanList(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan) {
        return qqchWorkPlanningBuildPlanMapper.getQqchWorkPlanningBuildPlanList(qqchWorkPlanningBuildPlan);
    }

    @Transactional
    public int insertQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan) {
        qqchWorkPlanningBuildPlan.setId(IdWorker.createId());
        qqchWorkPlanningBuildPlan.setCreateUser(SecurityUtils.getUserName());
        qqchWorkPlanningBuildPlan.setCreateTime(DateUtils.getNowDate());
        return qqchWorkPlanningBuildPlanMapper.insertQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlan);
    }

    @Transactional
    public int insertQqchWorkPlanningBuildPlanList(List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanList) {
        //查询是否有历史数据
        List<QqchWorkPlanningBuildPlan> oldList = getQqchWorkPlanningBuildPlanList(new QqchWorkPlanningBuildPlan());
        if(!ObjectNullUtil.isEmpty(oldList)){
            for (QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan : qqchWorkPlanningBuildPlanList) {
                qqchWorkPlanningBuildPlan.setId(IdWorker.createId());
                qqchWorkPlanningBuildPlan.setUpdateUser(SecurityUtils.getUserName());
                qqchWorkPlanningBuildPlan.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchWorkPlanningBuildPlanMapper.updateQqchWorkPlanningBuildPlanList(qqchWorkPlanningBuildPlanList);
        }else{
            for (QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan : qqchWorkPlanningBuildPlanList) {
                qqchWorkPlanningBuildPlan.setId(IdWorker.createId());
                qqchWorkPlanningBuildPlan.setCreateUser(SecurityUtils.getUserName());
                qqchWorkPlanningBuildPlan.setCreateTime(DateUtils.getNowDate());
                qqchWorkPlanningBuildPlan.setVersion(new BigDecimal(0));
                qqchWorkPlanningBuildPlan.setValid("1");
            }
            return qqchWorkPlanningBuildPlanMapper.insertQqchWorkPlanningBuildPlanList(qqchWorkPlanningBuildPlanList);
        }
    }

    @Transactional
    public int updateQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan) {
        qqchWorkPlanningBuildPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlanningBuildPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlanningBuildPlanMapper.updateQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlan);
    }

            @Transactional
        public int updateQqchWorkPlanningBuildPlanList(List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanList) {
            for (QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan : qqchWorkPlanningBuildPlanList) {
                qqchWorkPlanningBuildPlan.setUpdateUser(SecurityUtils.getUserName());
                qqchWorkPlanningBuildPlan.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchWorkPlanningBuildPlanMapper.updateQqchWorkPlanningBuildPlanList(qqchWorkPlanningBuildPlanList);
        }
    
    @Transactional
    public int deleteQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan) {
        qqchWorkPlanningBuildPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlanningBuildPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlanningBuildPlanMapper.deleteQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlan);
    }

            @Transactional
        public int deleteQqchWorkPlanningBuildPlanByPks(List<Long> qqchWorkPlanningBuildPlanPkList) {
            return qqchWorkPlanningBuildPlanMapper.deleteQqchWorkPlanningBuildPlanByPks(qqchWorkPlanningBuildPlanPkList);
        }
    }
