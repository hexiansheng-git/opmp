package com.hhwy.pm.qqch.preparation.survey.risk.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchSurveyDesignRiskPlan;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.vo.QqchSurveyDesignRiskPlanVo;
import com.hhwy.pm.qqch.preparation.survey.risk.mapper.QqchSurveyDesignRiskPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.risk.service.IQqchSurveyDesignRiskPlanService;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private CommonMapper commonMapper;


    /**
     * 勘察设计风险策划Vo
     * @return
     */
    public QqchSurveyDesignRiskPlanVo getQqchSurveyDesignRiskPlanVo() {
        QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo = new QqchSurveyDesignRiskPlanVo();

        BigDecimal version = commonMapper.selectMaxVersion("qqch_survey_design_risk_plan");
        qqchSurveyDesignRiskPlanVo.setVersion(version);

        QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan = new QqchSurveyDesignRiskPlan();
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

        return qqchSurveyDesignRiskPlanVo;
    }

    /**
     * 保存
     * @param qqchSurveyDesignRiskPlanVo
     * @return
     */
    @Override
    public void save(QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo) {
        //删除旧数据
        QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan = new QqchSurveyDesignRiskPlan();
        qqchSurveyDesignRiskPlan.setVersion(qqchSurveyDesignRiskPlan.getVersion());
        qqchSurveyDesignRiskPlanMapper.deleteQqchSurveyDesignRiskPlan(qqchSurveyDesignRiskPlan);

        //插入新数据
        this.insertQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanVo.getQqchSurveyDesignRiskPlanList(), qqchSurveyDesignRiskPlanVo.getVersion());
    }

    /**
     * 确认
     * @param qqchSurveyDesignRiskPlanVo
     * @return
     */
    @Override
    public void confirm(QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo) {
        this.save(qqchSurveyDesignRiskPlanVo);
        //TODO 修改确认状态
    }

    /**
     * 批量插入
     * @param qqchSurveyDesignRiskPlanList
     * @param version
     */
    @Transactional
    public void insertQqchSurveyDesignRiskPlanList(List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList, BigDecimal version) {
        List<QqchSurveyDesignRiskPlan> insertList = TreeUtils.splitTreeList(qqchSurveyDesignRiskPlanList);
        for (QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan : insertList) {
            qqchSurveyDesignRiskPlan.setVersion(version);
            qqchSurveyDesignRiskPlan.setValid(Valid.YES);
            qqchSurveyDesignRiskPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSurveyDesignRiskPlan.setCreateUserName(SecurityUtils.getUserName());
            qqchSurveyDesignRiskPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchSurveyDesignRiskPlanMapper.insertQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanList);
    }
}
