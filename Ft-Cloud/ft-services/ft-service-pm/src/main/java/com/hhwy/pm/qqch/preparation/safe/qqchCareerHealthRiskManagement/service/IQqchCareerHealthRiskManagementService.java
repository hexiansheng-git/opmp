package com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.service;

import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.domain.QqchCareerHealthRiskManagement;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.domain.vo.QqchCareerHealthRiskManagementVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 15:01:41
 * @remark
 */
public interface IQqchCareerHealthRiskManagementService {

    QqchCareerHealthRiskManagement getQqchCareerHealthRiskManagement(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement);

    QqchCareerHealthRiskManagementVo getQqchCareerHealthRiskManagementList(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement);

    int insertQqchCareerHealthRiskManagement(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement);


    int updateQqchCareerHealthRiskManagement(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement);

    int updateQqchCareerHealthRiskManagementList(List<QqchCareerHealthRiskManagement> qqchCareerHealthRiskManagementList);

    int deleteQqchCareerHealthRiskManagement(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement);

    int deleteQqchCareerHealthRiskManagementByPks(List<Long> qqchCareerHealthRiskManagementPkList);

    void save(QqchCareerHealthRiskManagementVo vo);
}
