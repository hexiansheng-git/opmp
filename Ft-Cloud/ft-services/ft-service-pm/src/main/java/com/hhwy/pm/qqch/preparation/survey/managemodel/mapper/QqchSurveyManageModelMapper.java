package com.hhwy.pm.qqch.preparation.survey.managemodel.mapper;

import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-18 14:57:01
 * @remark  总体勘察设计经营模式确定
 */
public interface QqchSurveyManageModelMapper {
                                                                                                                                                                                                                                                                                
    QqchSurveyManageModel getQqchSurveyManageModel(QqchSurveyManageModel qqchSurveyManageModel);

    List<QqchSurveyManageModel> getQqchSurveyManageModelList(QqchSurveyManageModel qqchSurveyManageModel);

    int insertQqchSurveyManageModel(QqchSurveyManageModel qqchSurveyManageModel);

    int insertQqchSurveyManageModelList(@Param("qqchSurveyManageModelList") List<QqchSurveyManageModel> qqchSurveyManageModelList);

    int updateQqchSurveyManageModel(QqchSurveyManageModel qqchSurveyManageModel);

    
    int deleteQqchSurveyManageModel(QqchSurveyManageModel qqchSurveyManageModel);

    }
