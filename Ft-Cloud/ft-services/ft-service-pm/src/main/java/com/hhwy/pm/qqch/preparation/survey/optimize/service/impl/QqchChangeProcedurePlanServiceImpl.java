package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchChangeProcedurePlan;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchChangeProcedurePlanMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchChangeProcedurePlanService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-07 18:35:34
 * @remark 变更程序策划
 */
@Service
public class QqchChangeProcedurePlanServiceImpl implements IQqchChangeProcedurePlanService {

    @Autowired
    private QqchChangeProcedurePlanMapper qqchChangeProcedurePlanMapper;


    public QqchChangeProcedurePlan getQqchChangeProcedurePlan(QqchChangeProcedurePlan qqchChangeProcedurePlan) {
        return qqchChangeProcedurePlanMapper.getQqchChangeProcedurePlan(qqchChangeProcedurePlan);
    }

    public List<QqchChangeProcedurePlan> getQqchChangeProcedurePlanList(QqchChangeProcedurePlan qqchChangeProcedurePlan) {
        return qqchChangeProcedurePlanMapper.getQqchChangeProcedurePlanList(qqchChangeProcedurePlan);
    }

    @Transactional
    public int insertQqchChangeProcedurePlan(QqchChangeProcedurePlan qqchChangeProcedurePlan) {
        qqchChangeProcedurePlan.setId(IdWorker.createId());
        qqchChangeProcedurePlan.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
        qqchChangeProcedurePlan.setCreateUserName(SecurityUtils.getUserName());
        qqchChangeProcedurePlan.setCreateTime(DateUtils.getNowDate());
        return qqchChangeProcedurePlanMapper.insertQqchChangeProcedurePlan(qqchChangeProcedurePlan);
    }

    @Transactional
    public int insertQqchChangeProcedurePlanList(List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList) {
        for (QqchChangeProcedurePlan qqchChangeProcedurePlan : qqchChangeProcedurePlanList) {
            qqchChangeProcedurePlan.setId(IdWorker.createId());
            qqchChangeProcedurePlan.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchChangeProcedurePlan.setCreateUserName(SecurityUtils.getUserName());
            qqchChangeProcedurePlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchChangeProcedurePlanMapper.insertQqchChangeProcedurePlanList(qqchChangeProcedurePlanList);
    }

    @Transactional
    public int updateQqchChangeProcedurePlan(QqchChangeProcedurePlan qqchChangeProcedurePlan) {
        qqchChangeProcedurePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchChangeProcedurePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchChangeProcedurePlanMapper.updateQqchChangeProcedurePlan(qqchChangeProcedurePlan);
    }

    @Transactional
    public int updateQqchChangeProcedurePlanList(List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList) {
        for (QqchChangeProcedurePlan qqchChangeProcedurePlan : qqchChangeProcedurePlanList) {
            qqchChangeProcedurePlan.setUpdateUser(SecurityUtils.getUserName());
            qqchChangeProcedurePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchChangeProcedurePlanMapper.updateQqchChangeProcedurePlanList(qqchChangeProcedurePlanList);
    }

    @Transactional
    public int deleteQqchChangeProcedurePlan(QqchChangeProcedurePlan qqchChangeProcedurePlan) {
        qqchChangeProcedurePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchChangeProcedurePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchChangeProcedurePlanMapper.deleteQqchChangeProcedurePlan(qqchChangeProcedurePlan);
    }

    @Transactional
    public int deleteQqchChangeProcedurePlanByPks(List<Long> qqchChangeProcedurePlanPkList) {
        return qqchChangeProcedurePlanMapper.deleteQqchChangeProcedurePlanByPks(qqchChangeProcedurePlanPkList);
    }
}
