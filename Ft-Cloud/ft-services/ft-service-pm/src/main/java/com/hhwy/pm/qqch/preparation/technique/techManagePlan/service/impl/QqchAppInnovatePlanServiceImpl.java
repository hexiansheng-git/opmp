package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAppInnovatePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchAppInnovatePlanMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchAppInnovatePlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:47
 * @remark 四新应用及创新计划
 */
@Service
public class QqchAppInnovatePlanServiceImpl implements IQqchAppInnovatePlanService {

    @Autowired
    private QqchAppInnovatePlanMapper qqchAppInnovatePlanMapper;


    public QqchAppInnovatePlan getQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan) {
        return qqchAppInnovatePlanMapper.getQqchAppInnovatePlan(qqchAppInnovatePlan);
    }

    public List<QqchAppInnovatePlan> getQqchAppInnovatePlanList(QqchAppInnovatePlan qqchAppInnovatePlan) {
        return qqchAppInnovatePlanMapper.getQqchAppInnovatePlanList(qqchAppInnovatePlan);
    }

    @Transactional
    public int insertQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan) {
        qqchAppInnovatePlan.setId(IdWorker.createId());
        qqchAppInnovatePlan.setCreateUser(SecurityUtils.getUserName());
        qqchAppInnovatePlan.setCreateTime(DateUtils.getNowDate());
        return qqchAppInnovatePlanMapper.insertQqchAppInnovatePlan(qqchAppInnovatePlan);
    }

    @Transactional
    public int insertQqchAppInnovatePlanList(List<QqchAppInnovatePlan> qqchAppInnovatePlanList) {
        for (QqchAppInnovatePlan qqchAppInnovatePlan : qqchAppInnovatePlanList) {
            qqchAppInnovatePlan.setId(IdWorker.createId());
            qqchAppInnovatePlan.setCreateUser(SecurityUtils.getUserName());
            qqchAppInnovatePlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchAppInnovatePlanMapper.insertQqchAppInnovatePlanList(qqchAppInnovatePlanList);
    }

    @Transactional
    public int updateQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan) {
        qqchAppInnovatePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchAppInnovatePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchAppInnovatePlanMapper.updateQqchAppInnovatePlan(qqchAppInnovatePlan);
    }

    @Transactional
    public int updateQqchAppInnovatePlanList(List<QqchAppInnovatePlan> qqchAppInnovatePlanList) {
        for (QqchAppInnovatePlan qqchAppInnovatePlan : qqchAppInnovatePlanList) {
            qqchAppInnovatePlan.setUpdateUser(SecurityUtils.getUserName());
            qqchAppInnovatePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchAppInnovatePlanMapper.updateQqchAppInnovatePlanList(qqchAppInnovatePlanList);
    }

    @Transactional
    public int deleteQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan) {
        qqchAppInnovatePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchAppInnovatePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchAppInnovatePlanMapper.deleteQqchAppInnovatePlan(qqchAppInnovatePlan);
    }

    @Transactional
    public int deleteQqchAppInnovatePlanByPks(List<Long> qqchAppInnovatePlanPkList) {
        return qqchAppInnovatePlanMapper.deleteQqchAppInnovatePlanByPks(qqchAppInnovatePlanPkList);
    }
}
