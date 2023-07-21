package com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.mapper.QqchSurveyWorkPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.service.IQqchSurveyWorkPlanService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-20 11:49:55
 * @remark  2.2 勘察设计工作计划
 */
@Service
public class QqchSurveyWorkPlanServiceImpl implements IQqchSurveyWorkPlanService{

    @Autowired
    private QqchSurveyWorkPlanMapper qqchSurveyWorkPlanMapper;


    public QqchSurveyWorkPlan getQqchSurveyWorkPlan(QqchSurveyWorkPlan qqchSurveyWorkPlan) {
        return qqchSurveyWorkPlanMapper.getQqchSurveyWorkPlan(qqchSurveyWorkPlan);
    }

    public List<QqchSurveyWorkPlan> getQqchSurveyWorkPlanList(QqchSurveyWorkPlan qqchSurveyWorkPlan) {
        List<QqchSurveyWorkPlan> qqchSurveyWorkPlanList = qqchSurveyWorkPlanMapper.getQqchSurveyWorkPlanList(qqchSurveyWorkPlan);
        List<QqchSurveyWorkPlan> qqchSurveyWorkPlans = TreeUtil.build(qqchSurveyWorkPlanList, 0l);
        return  qqchSurveyWorkPlans;
    }

    @Transactional
    public int insertQqchSurveyWorkPlan(QqchSurveyWorkPlan qqchSurveyWorkPlan) {
        qqchSurveyWorkPlan.setId(IdWorker.createId());
        qqchSurveyWorkPlan.setCreateUser(SecurityUtils.getUserName());
        qqchSurveyWorkPlan.setCreateTime(DateUtils.getNowDate());
        return qqchSurveyWorkPlanMapper.insertQqchSurveyWorkPlan(qqchSurveyWorkPlan);
    }

    @Transactional
    public int insertQqchSurveyWorkPlanList(List<QqchSurveyWorkPlan> qqchSurveyWorkPlanList) {
        if(CollectionUtils.isEmpty(qqchSurveyWorkPlanList)){
            return 0;
        }

        List<QqchSurveyWorkPlan> insertList = new ArrayList<>();
        List<QqchSurveyWorkPlan> updateList = new ArrayList<>();
        for (QqchSurveyWorkPlan qqchSurveyWorkPlan : qqchSurveyWorkPlanList) {
            this.recursionSubset(qqchSurveyWorkPlan, insertList, updateList);
        }
        if (insertList.size() > 0) {
            insertList.forEach(q->{
                if (q.getPid() != null) {
                    q.setPid(q.getPid());
                } else {
                    q.setPid(0l);
                }
            });
            qqchSurveyWorkPlanMapper.insertQqchSurveyWorkPlanList(insertList);
        }
        if (updateList.size() > 0) {
            qqchSurveyWorkPlanMapper.updateQqchSurveyWorkPlanList(updateList);
        }
        return 1;
    }

    /**
     *  递归处理
     *
     * @param qqchSurveyWorkPlan
     * @param insertList
     * @param updateList
     */
    private void recursionSubset(QqchSurveyWorkPlan qqchSurveyWorkPlan, List<QqchSurveyWorkPlan> insertList, List<QqchSurveyWorkPlan> updateList) {
        Long id = qqchSurveyWorkPlan.getId();
        if (id == null) {
            id = IdWorker.createId();
            qqchSurveyWorkPlan.setId(id);
            EntityUtils.setCreateUpdateInfo(qqchSurveyWorkPlan);
            insertList.add(qqchSurveyWorkPlan);
        } else {
            EntityUtils.setUpdateInfo(qqchSurveyWorkPlan);
            updateList.add(qqchSurveyWorkPlan);
        }
        List<QqchSurveyWorkPlan> children = qqchSurveyWorkPlan.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (QqchSurveyWorkPlan child : children) {
                child.setPid(id);
                this.recursionSubset(child, insertList, updateList);
            }
        }
    }

    @Transactional
    public int updateQqchSurveyWorkPlan(QqchSurveyWorkPlan qqchSurveyWorkPlan) {
        qqchSurveyWorkPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyWorkPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyWorkPlanMapper.updateQqchSurveyWorkPlan(qqchSurveyWorkPlan);
    }

    @Transactional
    public int updateQqchSurveyWorkPlanList(List<QqchSurveyWorkPlan> qqchSurveyWorkPlanList) {
        for (QqchSurveyWorkPlan qqchSurveyWorkPlan : qqchSurveyWorkPlanList) {
            qqchSurveyWorkPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchSurveyWorkPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSurveyWorkPlanMapper.updateQqchSurveyWorkPlanList(qqchSurveyWorkPlanList);
    }

    @Transactional
    public int deleteQqchSurveyWorkPlan(QqchSurveyWorkPlan qqchSurveyWorkPlan) {
        qqchSurveyWorkPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyWorkPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyWorkPlanMapper.deleteQqchSurveyWorkPlan(qqchSurveyWorkPlan);
    }

    @Transactional
    public int deleteQqchSurveyWorkPlanByPks(List<Long> qqchSurveyWorkPlanPkList) {
        return qqchSurveyWorkPlanMapper.deleteQqchSurveyWorkPlanByPks(qqchSurveyWorkPlanPkList);
    }
}
