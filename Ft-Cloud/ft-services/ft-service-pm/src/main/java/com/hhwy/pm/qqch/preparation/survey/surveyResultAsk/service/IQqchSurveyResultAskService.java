package com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.service;

import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.domain.QqchSurveyResultAsk;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:45:52
 * @remark 2.3.2 勘察成果验收内容形式审查要求
 */
public interface IQqchSurveyResultAskService {

    QqchSurveyResultAsk getQqchSurveyResultAsk(QqchSurveyResultAsk qqchSurveyResultAsk);

    List<QqchSurveyResultAsk> getQqchSurveyResultAskList(QqchSurveyResultAsk qqchSurveyResultAsk);

    int insertQqchSurveyResultAsk(QqchSurveyResultAsk qqchSurveyResultAsk);

    int insertQqchSurveyResultAskList(List<QqchSurveyResultAsk> qqchSurveyResultAskList);

    int updateQqchSurveyResultAsk(QqchSurveyResultAsk qqchSurveyResultAsk);

    int updateQqchSurveyResultAskList(List<QqchSurveyResultAsk> qqchSurveyResultAskList);

    int deleteQqchSurveyResultAsk(QqchSurveyResultAsk qqchSurveyResultAsk);

    int deleteQqchSurveyResultAskByPks(List<Long> qqchSurveyResultAskPkList);
}
