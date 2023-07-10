package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeProcedurePlan;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchOptimizeProcedurePlanMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeProcedurePlanService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-07 18:35:53
 * @remark 优化程序策划
 */
@Service
public class QqchOptimizeProcedurePlanServiceImpl implements IQqchOptimizeProcedurePlanService {

    @Autowired
    private QqchOptimizeProcedurePlanMapper qqchOptimizeProcedurePlanMapper;


    public QqchOptimizeProcedurePlan getQqchOptimizeProcedurePlan(QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan) {
        return qqchOptimizeProcedurePlanMapper.getQqchOptimizeProcedurePlan(qqchOptimizeProcedurePlan);
    }

    /**
     * 获取优化程序策划集合
     * @return
     */
    public List<QqchOptimizeProcedurePlan> getQqchOptimizeProcedurePlanList() {
        return qqchOptimizeProcedurePlanMapper.getQqchOptimizeProcedurePlanList();
    }

    /**
     * 批量编辑（新增和修改）
     * @param qqchOptimizeProcedurePlanListParam
     * @return
     */
    @Override
    public int editQqchOptimizeProcedurePlanList(List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanListParam) {
        return 0;
    }

    @Transactional
    public int insertQqchOptimizeProcedurePlan(QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan) {
        qqchOptimizeProcedurePlan.setId(IdWorker.createId());
        qqchOptimizeProcedurePlan.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
        qqchOptimizeProcedurePlan.setCreateUserName(SecurityUtils.getUserName());
        qqchOptimizeProcedurePlan.setCreateTime(DateUtils.getNowDate());
        return qqchOptimizeProcedurePlanMapper.insertQqchOptimizeProcedurePlan(qqchOptimizeProcedurePlan);
    }

    @Transactional
    public int insertQqchOptimizeProcedurePlanList(List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList) {
        for (QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan : qqchOptimizeProcedurePlanList) {
            qqchOptimizeProcedurePlan.setId(IdWorker.createId());
            qqchOptimizeProcedurePlan.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchOptimizeProcedurePlan.setCreateUserName(SecurityUtils.getUserName());
            qqchOptimizeProcedurePlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchOptimizeProcedurePlanMapper.insertQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanList);
    }

    @Transactional
    public int updateQqchOptimizeProcedurePlan(QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan) {
        qqchOptimizeProcedurePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchOptimizeProcedurePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchOptimizeProcedurePlanMapper.updateQqchOptimizeProcedurePlan(qqchOptimizeProcedurePlan);
    }

    @Transactional
    public int updateQqchOptimizeProcedurePlanList(List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList) {
        for (QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan : qqchOptimizeProcedurePlanList) {
            qqchOptimizeProcedurePlan.setUpdateUser(SecurityUtils.getUserName());
            qqchOptimizeProcedurePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchOptimizeProcedurePlanMapper.updateQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanList);
    }

    @Transactional
    public int deleteQqchOptimizeProcedurePlan(QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan) {
        qqchOptimizeProcedurePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchOptimizeProcedurePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchOptimizeProcedurePlanMapper.deleteQqchOptimizeProcedurePlan(qqchOptimizeProcedurePlan);
    }

    @Transactional
    public int deleteQqchOptimizeProcedurePlanByPks(List<Long> qqchOptimizeProcedurePlanPkList) {
        return qqchOptimizeProcedurePlanMapper.deleteQqchOptimizeProcedurePlanByPks(qqchOptimizeProcedurePlanPkList);
    }
}
