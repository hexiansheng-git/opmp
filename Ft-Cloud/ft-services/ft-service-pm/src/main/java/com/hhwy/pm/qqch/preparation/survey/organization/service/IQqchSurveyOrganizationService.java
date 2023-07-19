package com.hhwy.pm.qqch.preparation.survey.organization.service;

import com.hhwy.pm.qqch.preparation.survey.organization.domain.QqchSurveyOrganization;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-19 15:37:23
 * @remark
 */
public interface IQqchSurveyOrganizationService {

    QqchSurveyOrganization getQqchSurveyOrganization(QqchSurveyOrganization qqchSurveyOrganization);

    List<QqchSurveyOrganization> getQqchSurveyOrganizationList(QqchSurveyOrganization qqchSurveyOrganization);

    int insertQqchSurveyOrganization(QqchSurveyOrganization qqchSurveyOrganization);

    int insertQqchSurveyOrganizationList(List<QqchSurveyOrganization> qqchSurveyOrganizationList);

    int updateQqchSurveyOrganization(QqchSurveyOrganization qqchSurveyOrganization);

    int updateQqchSurveyOrganizationList(List<QqchSurveyOrganization> qqchSurveyOrganizationList);

    int deleteQqchSurveyOrganization(QqchSurveyOrganization qqchSurveyOrganization);

    int deleteQqchSurveyOrganizationByPks(List<Long> qqchSurveyOrganizationPkList);
}
