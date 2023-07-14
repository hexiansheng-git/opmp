package com.hhwy.pm.qqch.preparation.survey.risk.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchSurveyDesignRiskPlan;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.vo.QqchSurveyDesignRiskPlanVo;
import com.hhwy.pm.qqch.preparation.survey.risk.mapper.QqchSurveyDesignRiskPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.risk.service.IQqchSurveyDesignRiskPlanService;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

/**
 * @author han
 * @date 2023-07-13 11:39:34
 * @remark 勘察设计风险策划
 */
@Service
public class QqchSurveyDesignRiskPlanServiceImpl implements IQqchSurveyDesignRiskPlanService {

    @Autowired
    private QqchSurveyDesignRiskPlanMapper qqchSurveyDesignRiskPlanMapper;


    /**
     * 勘察设计风险策划Vo
     * @param qqchSurveyDesignRiskPlan
     * @return
     */
    public QqchSurveyDesignRiskPlanVo getQqchSurveyDesignRiskPlanVo(QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan) {
        QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo = new QqchSurveyDesignRiskPlanVo();
        List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList = qqchSurveyDesignRiskPlanMapper.getQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlan);
        List<QqchSurveyDesignRiskPlan> treeList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(qqchSurveyDesignRiskPlanList)){
            treeList = ListTreeUtil.formatTree(
                    qqchSurveyDesignRiskPlanList,
                    o -> o.getPid() == null,
                    (r,n) -> r.getId().equals(n.getPid()),
                    QqchSurveyDesignRiskPlan::getChildren,
                    QqchSurveyDesignRiskPlan::setChildren);
        }
        qqchSurveyDesignRiskPlanVo.setQqchSurveyDesignRiskPlanList(treeList);

        //TODO 获取确认情况
        qqchSurveyDesignRiskPlanVo.setQqchModuleConfirmCase(new QqchModuleConfirmCase());

        return qqchSurveyDesignRiskPlanVo;
    }

    /**
     * 保存
     * @param qqchSurveyDesignRiskPlanVo
     * @return
     */
    @Override
    public void save(QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo) {
        this.editQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanVo.getQqchSurveyDesignRiskPlanList());
    }

    /**
     * 确认
     * @param qqchSurveyDesignRiskPlanVo
     * @return
     */
    @Override
    public void confirm(QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo) {
        this.editQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanVo.getQqchSurveyDesignRiskPlanList());
        //TODO 修改确认状态
    }

    /**
     * 编辑树列表
     * @param qqchSurveyDesignRiskPlanList
     */
    @Transactional
    public void editQqchSurveyDesignRiskPlanList(List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList){
        List<QqchSurveyDesignRiskPlan> insertList = TreeUtils.splitTreeList(qqchSurveyDesignRiskPlanList);
        if(!CollectionUtils.isEmpty(insertList)){
            this.insertQqchSurveyDesignRiskPlanList(insertList);
        }
    }

    /**
     * 批量插入
     * @param qqchSurveyDesignRiskPlanList
     * @return
     */
    @Transactional
    public int insertQqchSurveyDesignRiskPlanList(List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList) {
        for (QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan : qqchSurveyDesignRiskPlanList) {
            qqchSurveyDesignRiskPlan.setId(IdWorker.createId());
            qqchSurveyDesignRiskPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSurveyDesignRiskPlan.setCreateUserName(SecurityUtils.getUserName());
            qqchSurveyDesignRiskPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSurveyDesignRiskPlanMapper.insertQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanList);
    }

    /**
     * 批量修改
     * @param qqchSurveyDesignRiskPlanList
     * @return
     */
    @Transactional
    public int updateQqchSurveyDesignRiskPlanList(List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList) {
        for (QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan : qqchSurveyDesignRiskPlanList) {
            qqchSurveyDesignRiskPlan.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSurveyDesignRiskPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSurveyDesignRiskPlanMapper.updateQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanList);
    }

    /**
     * 批量删除
     * @param qqchSurveyDesignRiskPlanPkList
     * @return
     */
    @Transactional
    public int deleteQqchSurveyDesignRiskPlanByPks(List<Long> qqchSurveyDesignRiskPlanPkList) {
        return qqchSurveyDesignRiskPlanMapper.deleteQqchSurveyDesignRiskPlanByPks(qqchSurveyDesignRiskPlanPkList);
    }
}
