package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.ArrayList;
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

    /**
     * 获取变更程序策划集合
     * @return
     */
    public List<QqchChangeProcedurePlan> getQqchChangeProcedurePlanList() {
        return qqchChangeProcedurePlanMapper.getQqchChangeProcedurePlanList();
    }

    /**
     * 批量编辑（新增和修改）
     * @param qqchChangeProcedurePlanListParam
     * @return
     */
    @Override
    @Transactional
    public int editQqchChangeProcedurePlanList(List<QqchChangeProcedurePlan> qqchChangeProcedurePlanListParam) {
        List<QqchChangeProcedurePlan> insertList = new ArrayList<>();
        List<QqchChangeProcedurePlan> updateList = new ArrayList<>();
        for (QqchChangeProcedurePlan qqchChangeProcedurePlan : qqchChangeProcedurePlanListParam) {
            Long id = qqchChangeProcedurePlan.getId();
            if(id == null){
                qqchChangeProcedurePlan.setId(IdWorker.createId());
                qqchChangeProcedurePlan.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
                qqchChangeProcedurePlan.setCreateUserName(SecurityUtils.getUserName());
                qqchChangeProcedurePlan.setCreateTime(DateUtils.getNowDate());
                insertList.add(qqchChangeProcedurePlan);
            }else {
                qqchChangeProcedurePlan.setUpdateUser(SecurityUtils.getUserName());
                qqchChangeProcedurePlan.setUpdateTime(DateUtils.getNowDate());
                updateList.add(qqchChangeProcedurePlan);
            }
        }
        if(insertList.size() > 0){
            qqchChangeProcedurePlanMapper.insertQqchChangeProcedurePlanList(insertList);
        }
        if(updateList.size() > 0){
            qqchChangeProcedurePlanMapper.updateQqchChangeProcedurePlanList(updateList);
        }
        return 1;
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
