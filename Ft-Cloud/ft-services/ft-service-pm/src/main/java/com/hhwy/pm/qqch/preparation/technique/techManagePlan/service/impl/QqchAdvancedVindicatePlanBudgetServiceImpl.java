package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlanBudget;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchAdvancedVindicatePlanBudgetMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchAdvancedVindicatePlanBudgetService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-27 15:51:20
 * @remark
 */
@Service
public class QqchAdvancedVindicatePlanBudgetServiceImpl implements IQqchAdvancedVindicatePlanBudgetService {

    @Autowired
    private QqchAdvancedVindicatePlanBudgetMapper qqchAdvancedVindicatePlanBudgetMapper;


    public QqchAdvancedVindicatePlanBudget getQqchAdvancedVindicatePlanBudget(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget) {
        return qqchAdvancedVindicatePlanBudgetMapper.getQqchAdvancedVindicatePlanBudget(qqchAdvancedVindicatePlanBudget);
    }

    public List<QqchAdvancedVindicatePlanBudget> getQqchAdvancedVindicatePlanBudgetList(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget) {
        return qqchAdvancedVindicatePlanBudgetMapper.getQqchAdvancedVindicatePlanBudgetList(qqchAdvancedVindicatePlanBudget);
    }

    @Transactional
    public int insertQqchAdvancedVindicatePlanBudget(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget) {
        qqchAdvancedVindicatePlanBudget.setId(IdWorker.createId());
        qqchAdvancedVindicatePlanBudget.setCreateUser(SecurityUtils.getUserName());
        qqchAdvancedVindicatePlanBudget.setCreateTime(DateUtils.getNowDate());
        return qqchAdvancedVindicatePlanBudgetMapper.insertQqchAdvancedVindicatePlanBudget(qqchAdvancedVindicatePlanBudget);
    }

    @Transactional
    public int insertQqchAdvancedVindicatePlanBudgetList(List<QqchAdvancedVindicatePlanBudget> qqchAdvancedVindicatePlanBudgetList) {
        for (QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget : qqchAdvancedVindicatePlanBudgetList) {
            qqchAdvancedVindicatePlanBudget.setId(IdWorker.createId());
            qqchAdvancedVindicatePlanBudget.setCreateUser(SecurityUtils.getUserName());
            qqchAdvancedVindicatePlanBudget.setCreateTime(DateUtils.getNowDate());
        }
        return qqchAdvancedVindicatePlanBudgetMapper.insertQqchAdvancedVindicatePlanBudgetList(qqchAdvancedVindicatePlanBudgetList);
    }

    @Transactional
    public int updateQqchAdvancedVindicatePlanBudget(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget) {
        qqchAdvancedVindicatePlanBudget.setUpdateUser(SecurityUtils.getUserName());
        qqchAdvancedVindicatePlanBudget.setUpdateTime(DateUtils.getNowDate());
        return qqchAdvancedVindicatePlanBudgetMapper.updateQqchAdvancedVindicatePlanBudget(qqchAdvancedVindicatePlanBudget);
    }

    @Transactional
    public int updateQqchAdvancedVindicatePlanBudgetList(List<QqchAdvancedVindicatePlanBudget> qqchAdvancedVindicatePlanBudgetList) {
        for (QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget : qqchAdvancedVindicatePlanBudgetList) {
            qqchAdvancedVindicatePlanBudget.setUpdateUser(SecurityUtils.getUserName());
            qqchAdvancedVindicatePlanBudget.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchAdvancedVindicatePlanBudgetMapper.updateQqchAdvancedVindicatePlanBudgetList(qqchAdvancedVindicatePlanBudgetList);
    }

    @Transactional
    public int deleteQqchAdvancedVindicatePlanBudget(QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget) {
        qqchAdvancedVindicatePlanBudget.setUpdateUser(SecurityUtils.getUserName());
        qqchAdvancedVindicatePlanBudget.setUpdateTime(DateUtils.getNowDate());
        return qqchAdvancedVindicatePlanBudgetMapper.deleteQqchAdvancedVindicatePlanBudget(qqchAdvancedVindicatePlanBudget);
    }

    @Transactional
    public int deleteQqchAdvancedVindicatePlanBudgetByPks(List<Long> qqchAdvancedVindicatePlanBudgetPkList) {
        return qqchAdvancedVindicatePlanBudgetMapper.deleteQqchAdvancedVindicatePlanBudgetByPks(qqchAdvancedVindicatePlanBudgetPkList);
    }
}
