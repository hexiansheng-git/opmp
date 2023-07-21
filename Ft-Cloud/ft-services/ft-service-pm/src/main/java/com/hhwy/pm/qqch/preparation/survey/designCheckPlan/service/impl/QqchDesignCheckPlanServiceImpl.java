package com.hhwy.pm.qqch.preparation.survey.designCheckPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.QqchDesignCheckPlan;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.mapper.QqchDesignCheckPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.service.IQqchDesignCheckPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:48:41
 * @remark  2.3.3 设计成果验收计划
 */
@Service
public class QqchDesignCheckPlanServiceImpl implements IQqchDesignCheckPlanService{

    @Autowired
    private QqchDesignCheckPlanMapper qqchDesignCheckPlanMapper;

                                                                                                                                                                                                                                                                                                                                                                                                                                        
    public QqchDesignCheckPlan getQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan) {
        return qqchDesignCheckPlanMapper.getQqchDesignCheckPlan(qqchDesignCheckPlan);
    }

    public List<QqchDesignCheckPlan> getQqchDesignCheckPlanList(QqchDesignCheckPlan qqchDesignCheckPlan) {
        return qqchDesignCheckPlanMapper.getQqchDesignCheckPlanList(qqchDesignCheckPlan);
    }

    @Transactional
    public int insertQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan) {
        qqchDesignCheckPlan.setId(IdWorker.createId());
        qqchDesignCheckPlan.setCreateUser(SecurityUtils.getUserName());
        qqchDesignCheckPlan.setCreateTime(DateUtils.getNowDate());
        return qqchDesignCheckPlanMapper.insertQqchDesignCheckPlan(qqchDesignCheckPlan);
    }

    @Transactional
    public int insertQqchDesignCheckPlanList(List<QqchDesignCheckPlan> qqchDesignCheckPlanList) {
        for (QqchDesignCheckPlan qqchDesignCheckPlan : qqchDesignCheckPlanList) {
            qqchDesignCheckPlan.setId(IdWorker.createId());
            qqchDesignCheckPlan.setCreateUser(SecurityUtils.getUserName());
            qqchDesignCheckPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDesignCheckPlanMapper.insertQqchDesignCheckPlanList(qqchDesignCheckPlanList);
    }

    @Transactional
    public int updateQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan) {
        qqchDesignCheckPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchDesignCheckPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchDesignCheckPlanMapper.updateQqchDesignCheckPlan(qqchDesignCheckPlan);
    }

            @Transactional
        public int updateQqchDesignCheckPlanList(List<QqchDesignCheckPlan> qqchDesignCheckPlanList) {
            for (QqchDesignCheckPlan qqchDesignCheckPlan : qqchDesignCheckPlanList) {
                qqchDesignCheckPlan.setUpdateUser(SecurityUtils.getUserName());
                qqchDesignCheckPlan.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchDesignCheckPlanMapper.updateQqchDesignCheckPlanList(qqchDesignCheckPlanList);
        }
    
    @Transactional
    public int deleteQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan) {
        qqchDesignCheckPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchDesignCheckPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchDesignCheckPlanMapper.deleteQqchDesignCheckPlan(qqchDesignCheckPlan);
    }

            @Transactional
        public int deleteQqchDesignCheckPlanByPks(List<Long> qqchDesignCheckPlanPkList) {
            return qqchDesignCheckPlanMapper.deleteQqchDesignCheckPlanByPks(qqchDesignCheckPlanPkList);
        }
    }
