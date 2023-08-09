package com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.domain.QqchCareerHealthRiskManagement;

/**
 * @author ldd
 * @date 2023-08-09 15:01:41
 * @remark 
 */
public interface QqchCareerHealthRiskManagementMapper {
                                                                                                                                                                                                                                                                                                                                                                                        
    QqchCareerHealthRiskManagement getQqchCareerHealthRiskManagement(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement);

    List<QqchCareerHealthRiskManagement> getQqchCareerHealthRiskManagementList(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement);

    int insertQqchCareerHealthRiskManagement(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement);

    int insertQqchCareerHealthRiskManagementList(@Param("qqchCareerHealthRiskManagementList") List<QqchCareerHealthRiskManagement> qqchCareerHealthRiskManagementList);

    int updateQqchCareerHealthRiskManagement(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement);

            int updateQqchCareerHealthRiskManagementList(@Param("qqchCareerHealthRiskManagementList") List<QqchCareerHealthRiskManagement> qqchCareerHealthRiskManagementList);
    
    int deleteQqchCareerHealthRiskManagement(QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement);

            int deleteQqchCareerHealthRiskManagementByPks(@Param("qqchCareerHealthRiskManagementPkList") List<Long> qqchCareerHealthRiskManagementPkList);
    }
