package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchCraftDeclarePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchCraftDeclarePlanMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchCraftDeclarePlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:40:02
 * @remark
 */
@Service
public class QqchCraftDeclarePlanServiceImpl implements IQqchCraftDeclarePlanService {

    @Autowired
    private QqchCraftDeclarePlanMapper qqchCraftDeclarePlanMapper;


    public QqchCraftDeclarePlan getQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        return qqchCraftDeclarePlanMapper.getQqchCraftDeclarePlan(qqchCraftDeclarePlan);
    }

    public List<QqchCraftDeclarePlan> getQqchCraftDeclarePlanList(QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        return qqchCraftDeclarePlanMapper.getQqchCraftDeclarePlanList(qqchCraftDeclarePlan);
    }

    @Transactional
    public int insertQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        qqchCraftDeclarePlan.setId(IdWorker.createId());
        qqchCraftDeclarePlan.setCreateUser(SecurityUtils.getUserName());
        qqchCraftDeclarePlan.setCreateTime(DateUtils.getNowDate());
        return qqchCraftDeclarePlanMapper.insertQqchCraftDeclarePlan(qqchCraftDeclarePlan);
    }

    @Transactional
    public int insertQqchCraftDeclarePlanList(List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList) {
        for (QqchCraftDeclarePlan qqchCraftDeclarePlan : qqchCraftDeclarePlanList) {
            qqchCraftDeclarePlan.setId(IdWorker.createId());
            qqchCraftDeclarePlan.setCreateUser(SecurityUtils.getUserName());
            qqchCraftDeclarePlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchCraftDeclarePlanMapper.insertQqchCraftDeclarePlanList(qqchCraftDeclarePlanList);
    }

    @Transactional
    public int updateQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        qqchCraftDeclarePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchCraftDeclarePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchCraftDeclarePlanMapper.updateQqchCraftDeclarePlan(qqchCraftDeclarePlan);
    }

    @Transactional
    public int updateQqchCraftDeclarePlanList(List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList) {
        for (QqchCraftDeclarePlan qqchCraftDeclarePlan : qqchCraftDeclarePlanList) {
            qqchCraftDeclarePlan.setUpdateUser(SecurityUtils.getUserName());
            qqchCraftDeclarePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchCraftDeclarePlanMapper.updateQqchCraftDeclarePlanList(qqchCraftDeclarePlanList);
    }

    @Transactional
    public int deleteQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        qqchCraftDeclarePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchCraftDeclarePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchCraftDeclarePlanMapper.deleteQqchCraftDeclarePlan(qqchCraftDeclarePlan);
    }

    @Transactional
    public int deleteQqchCraftDeclarePlanByPks(List<Long> qqchCraftDeclarePlanPkList) {
        return qqchCraftDeclarePlanMapper.deleteQqchCraftDeclarePlanByPks(qqchCraftDeclarePlanPkList);
    }
}
