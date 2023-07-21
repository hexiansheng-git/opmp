package com.hhwy.pm.qqch.preparation.survey.risk.service;

import com.hhwy.pm.qqch.preparation.survey.risk.domain.vo.QqchSurveyDesignRiskPlanVo;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-13 11:39:34
 * @remark 勘察设计风险策划
 */
public interface IQqchSurveyDesignRiskPlanService {

    /**
     * 勘察设计风险策划Vo
     * @return
     * @param version
     */
    QqchSurveyDesignRiskPlanVo getQqchSurveyDesignRiskPlanVo(BigDecimal version);

    /**
     * 保存
     * @param qqchSurveyDesignRiskPlanVo
     * @return
     */
    void save(QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo);

    /**
     * 确认
     * @param qqchSurveyDesignRiskPlanVo
     * @return
     */
    void confirm(QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo);
}
