package com.hhwy.pm.qqch.preparation.survey.organization.service;

import com.hhwy.pm.qqch.preparation.survey.organization.domain.QqchSurveyOrganization;
import com.hhwy.pm.qqch.preparation.survey.organization.domain.QqchSurveyOrganizationVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-19 15:37:23
 * @remark 2.1.2 项目部勘察设计组织机构
 */
public interface IQqchSurveyOrganizationService {

    

    List<QqchSurveyOrganization> getQqchSurveyOrganizationList(QqchSurveyOrganization qqchSurveyOrganization);


    int save(QqchSurveyOrganizationVo qqchSurveyOrganizationVo);

    void confirm(QqchSurveyOrganizationVo qqchSurveyOrganizationVo);
}
