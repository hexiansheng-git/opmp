package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchTopicResearchPlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchTopicResearchPlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchTopicResearchPlanVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:25
 * @remark 课题研究计划
 */
public interface IQqchTopicResearchPlanService {

    QqchTopicResearchPlan getQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan);

    List<QqchTopicResearchPlan> getQqchTopicResearchPlanList(QqchTopicResearchPlan qqchTopicResearchPlan);

    int insertQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan);

    int updateQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan);

    int updateQqchTopicResearchPlanList(List<QqchTopicResearchPlan> qqchTopicResearchPlanList);

    int deleteQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan);

    int deleteQqchTopicResearchPlanByPks(List<Long> qqchTopicResearchPlanPkList);

    /**
     * 获取课题研究计划Vo
     * @param qqchTopicResearchPlan
     * @return
     */
    QqchTopicResearchPlanVo getQqchTopicResearchPlanVo(QqchTopicResearchPlan qqchTopicResearchPlan);

    /**
     * 保存/确认/提交
     * @param qqchTopicResearchPlanVo
     * @return
     */
    void save(QqchTopicResearchPlanVo qqchTopicResearchPlanVo);

    /**
     * 获取导出数据
     * @param qqchTopicResearchPlan
     * @return
     */
    List<QqchTopicResearchPlanExportVo> getQqchTopicResearchPlanExportVoList(QqchTopicResearchPlan qqchTopicResearchPlan);
}
