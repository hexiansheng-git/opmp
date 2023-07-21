package com.hhwy.pm.qqch.preparation.survey.organization.mapper;

import com.hhwy.pm.qqch.preparation.survey.organization.domain.QqchSurveyOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-19 15:37:23
 * @remark  2.1.2 项目部勘察设计组织机构
 */
public interface QqchSurveyOrganizationMapper {
                                                                                                                                                                                                                                                                                                                                                                                        
    QqchSurveyOrganization getQqchSurveyOrganization(QqchSurveyOrganization qqchSurveyOrganization);

    List<QqchSurveyOrganization> getQqchSurveyOrganizationList(QqchSurveyOrganization qqchSurveyOrganization);

    int insertQqchSurveyOrganization(QqchSurveyOrganization qqchSurveyOrganization);

    int insertQqchSurveyOrganizationList(@Param("qqchSurveyOrganizationList") List<QqchSurveyOrganization> qqchSurveyOrganizationList);

    int updateQqchSurveyOrganization(QqchSurveyOrganization qqchSurveyOrganization);

    int updateQqchSurveyOrganizationList(@Param("list") List<QqchSurveyOrganization> qqchSurveyOrganizationList);
    
    int deleteQqchSurveyOrganization(QqchSurveyOrganization qqchSurveyOrganization);

    int deleteQqchSurveyOrganizationByPks(@Param("qqchSurveyOrganizationPkList") List<Long> qqchSurveyOrganizationPkList);
    }
