package com.hhwy.pm.qqch.preparation.measureexp.plan.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.measureexp.plan.service.IQqchMeasureExpPlanService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.measureexp.plan.mapper.QqchMeasureExpPlanMapper;
import com.hhwy.pm.qqch.preparation.measureexp.plan.domain.QqchMeasureExpPlan;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:01:32
 * @remark 
 */
@Service
public class QqchMeasureExpPlanServiceImpl implements IQqchMeasureExpPlanService {

    @Autowired
    private QqchMeasureExpPlanMapper qqchMeasureExpPlanMapper;

                                                                                                                                                                                                                                                                                                                            
    public QqchMeasureExpPlan getQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan) {
        return qqchMeasureExpPlanMapper.getQqchMeasureExpPlan(qqchMeasureExpPlan);
    }

    public List<QqchMeasureExpPlan> getQqchMeasureExpPlanList(QqchMeasureExpPlan qqchMeasureExpPlan) {
        return qqchMeasureExpPlanMapper.getQqchMeasureExpPlanList(qqchMeasureExpPlan);
    }

    @Transactional
    public int insertQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan) {
        qqchMeasureExpPlan.setId(IdWorker.createId());
        qqchMeasureExpPlan.setCreateUser(SecurityUtils.getUserName());
        qqchMeasureExpPlan.setCreateTime(DateUtils.getNowDate());
        return qqchMeasureExpPlanMapper.insertQqchMeasureExpPlan(qqchMeasureExpPlan);
    }

    @Transactional
    public int insertQqchMeasureExpPlanList(List<QqchMeasureExpPlan> qqchMeasureExpPlanList) {
        for (QqchMeasureExpPlan qqchMeasureExpPlan : qqchMeasureExpPlanList) {
            qqchMeasureExpPlan.setId(IdWorker.createId());
            qqchMeasureExpPlan.setCreateUser(SecurityUtils.getUserName());
            qqchMeasureExpPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpPlanMapper.insertQqchMeasureExpPlanList(qqchMeasureExpPlanList);
    }

    @Transactional
    public int updateQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan) {
        qqchMeasureExpPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpPlanMapper.updateQqchMeasureExpPlan(qqchMeasureExpPlan);
    }

            @Transactional
        public int updateQqchMeasureExpPlanList(List<QqchMeasureExpPlan> qqchMeasureExpPlanList) {
            for (QqchMeasureExpPlan qqchMeasureExpPlan : qqchMeasureExpPlanList) {
                qqchMeasureExpPlan.setUpdateUser(SecurityUtils.getUserName());
                qqchMeasureExpPlan.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchMeasureExpPlanMapper.updateQqchMeasureExpPlanList(qqchMeasureExpPlanList);
        }
    
    @Transactional
    public int deleteQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan) {
        qqchMeasureExpPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpPlanMapper.deleteQqchMeasureExpPlan(qqchMeasureExpPlan);
    }

            @Transactional
        public int deleteQqchMeasureExpPlanByPks(List<Long> qqchMeasureExpPlanPkList) {
            return qqchMeasureExpPlanMapper.deleteQqchMeasureExpPlanByPks(qqchMeasureExpPlanPkList);
        }
    }
