package com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.service;

import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.domain.QqchSurveyResultAsk;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.domain.QqchSurveyResultAskVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:45:52
 * @remark 2.3.2 勘察成果验收内容形式审查要求
 */
public interface IQqchSurveyResultAskService {

    QqchSurveyResultAskVo getQqchSurveyResultAskList(QqchSurveyResultAsk qqchSurveyResultAsk);


    void save(QqchSurveyResultAskVo qqchSurveyResultAskVo);

    void confirm(QqchSurveyResultAskVo qqchSurveyResultAskVo);
}
