package com.hhwy.pm.qqch.preparation.survey.optimize.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeChangeOrganization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-07 18:35:50
 * @remark 优化变更组织策划
 */
@Repository
public interface QqchOptimizeChangeOrganizationMapper {

    QqchOptimizeChangeOrganization getQqchOptimizeChangeOrganization(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization);

    List<QqchOptimizeChangeOrganization> getQqchOptimizeChangeOrganizationList(@Param("version") BigDecimal version);

    int insertQqchOptimizeChangeOrganization(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization);

    int insertQqchOptimizeChangeOrganizationList(@Param("qqchOptimizeChangeOrganizationList") List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList);

    int updateQqchOptimizeChangeOrganization(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization);

    int updateQqchOptimizeChangeOrganizationList(@Param("list") List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList);

    int deleteQqchOptimizeChangeOrganization(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization);

    int deleteQqchOptimizeChangeOrganizationByPks(@Param("qqchOptimizeChangeOrganizationPkList") List<Long> qqchOptimizeChangeOrganizationPkList);
}
