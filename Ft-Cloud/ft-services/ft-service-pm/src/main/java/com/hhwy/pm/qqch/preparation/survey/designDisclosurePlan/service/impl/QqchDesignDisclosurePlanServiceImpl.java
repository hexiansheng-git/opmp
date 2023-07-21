package com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.domain.QqchDesignDisclosurePlan;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.mapper.QqchDesignDisclosurePlanMapper;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.service.IQqchDesignDisclosurePlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:47:23
 * @remark 2.4 设计交底策划
 */
@Service
public class QqchDesignDisclosurePlanServiceImpl implements IQqchDesignDisclosurePlanService {

    @Autowired
    private QqchDesignDisclosurePlanMapper qqchDesignDisclosurePlanMapper;


    public QqchDesignDisclosurePlan getQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan) {
        return qqchDesignDisclosurePlanMapper.getQqchDesignDisclosurePlan(qqchDesignDisclosurePlan);
    }

    public List<QqchDesignDisclosurePlan> getQqchDesignDisclosurePlanList(QqchDesignDisclosurePlan qqchDesignDisclosurePlan) {
        return qqchDesignDisclosurePlanMapper.getQqchDesignDisclosurePlanList(qqchDesignDisclosurePlan);
    }

    @Transactional
    public int insertQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan) {
        qqchDesignDisclosurePlan.setId(IdWorker.createId());
        qqchDesignDisclosurePlan.setCreateUser(SecurityUtils.getUserName());
        qqchDesignDisclosurePlan.setCreateTime(DateUtils.getNowDate());
        return qqchDesignDisclosurePlanMapper.insertQqchDesignDisclosurePlan(qqchDesignDisclosurePlan);
    }

    @Transactional
    public int insertQqchDesignDisclosurePlanList(List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanList) {
        for (QqchDesignDisclosurePlan qqchDesignDisclosurePlan : qqchDesignDisclosurePlanList) {
            qqchDesignDisclosurePlan.setId(IdWorker.createId());
            qqchDesignDisclosurePlan.setCreateUser(SecurityUtils.getUserName());
            qqchDesignDisclosurePlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDesignDisclosurePlanMapper.insertQqchDesignDisclosurePlanList(qqchDesignDisclosurePlanList);
    }

    @Transactional
    public int updateQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan) {
        qqchDesignDisclosurePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchDesignDisclosurePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchDesignDisclosurePlanMapper.updateQqchDesignDisclosurePlan(qqchDesignDisclosurePlan);
    }

    @Transactional
    public int updateQqchDesignDisclosurePlanList(List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanList) {
        for (QqchDesignDisclosurePlan qqchDesignDisclosurePlan : qqchDesignDisclosurePlanList) {
            qqchDesignDisclosurePlan.setUpdateUser(SecurityUtils.getUserName());
            qqchDesignDisclosurePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchDesignDisclosurePlanMapper.updateQqchDesignDisclosurePlanList(qqchDesignDisclosurePlanList);
    }

    @Transactional
    public int deleteQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan) {
        qqchDesignDisclosurePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchDesignDisclosurePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchDesignDisclosurePlanMapper.deleteQqchDesignDisclosurePlan(qqchDesignDisclosurePlan);
    }

    @Transactional
    public int deleteQqchDesignDisclosurePlanByPks(List<Long> qqchDesignDisclosurePlanPkList) {
        return qqchDesignDisclosurePlanMapper.deleteQqchDesignDisclosurePlanByPks(qqchDesignDisclosurePlanPkList);
    }
}
