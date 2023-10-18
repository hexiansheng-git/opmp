package com.hhwy.pm.qqch.preparation.survey.managemodel.service;

import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModelVo;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-18 14:57:01
 * @remark  总体勘察设计经营模式确定
 */
public interface IQqchSurveyManageModelService {


    QqchSurveyManageModelVo  getQqchSurveyManageModelList(QqchSurveyManageModel qqchSurveyManageModel);


    QqchSurveyManageModelVo confirm(QqchSurveyManageModelVo qqchSurveyManageModelVo);


    int save(QqchSurveyManageModelVo qqchSurveyManageModelVo);

    List<QqchSurveyManageModel> getSameTypeProject(QqchSurveyManageModel qqchSurveyManageModelParam);
}
