package com.hhwy.pm.qqch.preparation.survey.managemodel.service;

import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.MasterEntity;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-18 14:57:01
 * @remark  总体勘察设计经营模式确定
 */
public interface IQqchSurveyManageModelService {
                                                                                                                                                                                                                                                                                
    QqchSurveyManageModel getQqchSurveyManageModel(QqchSurveyManageModel qqchSurveyManageModel);

    MasterEntity getQqchSurveyManageModelList(QqchSurveyManageModel qqchSurveyManageModel);

    int insertQqchSurveyManageModel(MasterEntity masterEntity);

    int insertQqchSurveyManageModelList(List<QqchSurveyManageModel> qqchSurveyManageModelList);

    int updateQqchSurveyManageModel(QqchSurveyManageModel qqchSurveyManageModel);

    
    int deleteQqchSurveyManageModel(QqchSurveyManageModel qqchSurveyManageModel);

    void confirm(MasterEntity masterEntity);
}
