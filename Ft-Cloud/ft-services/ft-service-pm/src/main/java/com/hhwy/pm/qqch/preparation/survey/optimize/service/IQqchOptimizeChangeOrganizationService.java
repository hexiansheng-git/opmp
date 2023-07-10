package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeChangeOrganization;

import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:50
 * @remark 优化变更组织策划
 */
public interface IQqchOptimizeChangeOrganizationService {

    QqchOptimizeChangeOrganization getQqchOptimizeChangeOrganization(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization);

    /**
     * 优化变更组织策划台账
     * @return
     */
    List<QqchOptimizeChangeOrganization> getQqchOptimizeChangeOrganizationTreeList();

    List<QqchOptimizeChangeOrganization> getQqchOptimizeChangeOrganizationList(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization);

    int insertQqchOptimizeChangeOrganization(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization);

    int insertQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList);

    int updateQqchOptimizeChangeOrganization(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization);

    int updateQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList);

    int deleteQqchOptimizeChangeOrganization(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization);

    int deleteQqchOptimizeChangeOrganizationByPks(List<Long> qqchOptimizeChangeOrganizationPkList);

    /**
     * 批量编辑（新增和修改）
     * @param qqchOptimizeChangeOrganizationListParam
     * @return
     */
    int editQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationListParam);

}
