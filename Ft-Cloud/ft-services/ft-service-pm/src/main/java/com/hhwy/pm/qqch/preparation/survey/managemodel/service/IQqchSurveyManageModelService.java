package com.hhwy.pm.qqch.preparation.survey.managemodel.service;

import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.MasterEntity;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.MasterEntityVo;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;

/**
 * @author ldd
 * @date 2023-07-18 14:57:01
 * @remark  总体勘察设计经营模式确定
 */
public interface IQqchSurveyManageModelService {


    MasterEntity getQqchSurveyManageModelList(QqchSurveyManageModel qqchSurveyManageModel);


    void confirm(MasterEntityVo masterEntityVo);


    int save(MasterEntityVo masterEntityVo);
}
