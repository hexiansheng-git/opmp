package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchPatentDeclarePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchPatentDeclarePlanMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchPatentDeclarePlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:40:39
 * @remark
 */
@Service
public class QqchPatentDeclarePlanServiceImpl implements IQqchPatentDeclarePlanService {

    @Autowired
    private QqchPatentDeclarePlanMapper qqchPatentDeclarePlanMapper;


    public QqchPatentDeclarePlan getQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        return qqchPatentDeclarePlanMapper.getQqchPatentDeclarePlan(qqchPatentDeclarePlan);
    }

    public List<QqchPatentDeclarePlan> getQqchPatentDeclarePlanList(QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        return qqchPatentDeclarePlanMapper.getQqchPatentDeclarePlanList(qqchPatentDeclarePlan);
    }

    @Transactional
    public int insertQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        qqchPatentDeclarePlan.setId(IdWorker.createId());
        qqchPatentDeclarePlan.setCreateUser(SecurityUtils.getUserName());
        qqchPatentDeclarePlan.setCreateTime(DateUtils.getNowDate());
        return qqchPatentDeclarePlanMapper.insertQqchPatentDeclarePlan(qqchPatentDeclarePlan);
    }

    @Transactional
    public int insertQqchPatentDeclarePlanList(List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList) {
        for (QqchPatentDeclarePlan qqchPatentDeclarePlan : qqchPatentDeclarePlanList) {
            qqchPatentDeclarePlan.setId(IdWorker.createId());
            qqchPatentDeclarePlan.setCreateUser(SecurityUtils.getUserName());
            qqchPatentDeclarePlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchPatentDeclarePlanMapper.insertQqchPatentDeclarePlanList(qqchPatentDeclarePlanList);
    }

    @Transactional
    public int updateQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        qqchPatentDeclarePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchPatentDeclarePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchPatentDeclarePlanMapper.updateQqchPatentDeclarePlan(qqchPatentDeclarePlan);
    }

    @Transactional
    public int updateQqchPatentDeclarePlanList(List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList) {
        for (QqchPatentDeclarePlan qqchPatentDeclarePlan : qqchPatentDeclarePlanList) {
            qqchPatentDeclarePlan.setUpdateUser(SecurityUtils.getUserName());
            qqchPatentDeclarePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchPatentDeclarePlanMapper.updateQqchPatentDeclarePlanList(qqchPatentDeclarePlanList);
    }

    @Transactional
    public int deleteQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        qqchPatentDeclarePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchPatentDeclarePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchPatentDeclarePlanMapper.deleteQqchPatentDeclarePlan(qqchPatentDeclarePlan);
    }

    @Transactional
    public int deleteQqchPatentDeclarePlanByPks(List<Long> qqchPatentDeclarePlanPkList) {
        return qqchPatentDeclarePlanMapper.deleteQqchPatentDeclarePlanByPks(qqchPatentDeclarePlanPkList);
    }
}
