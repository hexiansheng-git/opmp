package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchOptimizeChangeOrganizationVo;

/**
 * @author han
 * @date 2023-07-07 18:35:50
 * @remark 优化变更组织策划
 */
public interface IQqchOptimizeChangeOrganizationService {

    /**
     * 优化变更组织策划台账
     * @return
     */
    QqchOptimizeChangeOrganizationVo getQqchOptimizeChangeOrganizationVo();

    /**
     * 保存
     * @param qqchOptimizeChangeOrganizationVo
     */
    void save(QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo);

    /**
     * 确认
     * @param qqchOptimizeChangeOrganizationVo
     * @return
     */
    void confirm(QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo);
}
