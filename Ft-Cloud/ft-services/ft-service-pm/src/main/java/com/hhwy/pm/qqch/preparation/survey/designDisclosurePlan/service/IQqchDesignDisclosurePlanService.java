package com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.service;

import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.domain.QqchDesignDisclosurePlan;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.domain.vo.QqchDesignDisclosurePlanVo;

/**
 * @author ldd
 * @date 2023-07-21 16:47:23
 * @remark 2.4 设计交底策划
 */
public interface IQqchDesignDisclosurePlanService {


    QqchDesignDisclosurePlanVo getQqchDesignDisclosurePlanList(QqchDesignDisclosurePlan qqchDesignDisclosurePlan);

    void confirm(QqchDesignDisclosurePlanVo qqchDesignDisclosurePlanVo);

    void save(QqchDesignDisclosurePlanVo qqchDesignDisclosurePlanVo);
}
