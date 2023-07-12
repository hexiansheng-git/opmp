package com.hhwy.pm.qqch.preparation.survey.extend.service;

import com.hhwy.pm.qqch.preparation.survey.extend.domain.QqchPreparationSurveyExtend;

import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:36:03
 * @remark 前期策划-前期策划编制-勘察设计策划-扩展
 */
public interface IQqchPreparationSurveyExtendService {

    /**
     * 获取扩展数据
     * @return
     */
    QqchPreparationSurveyExtend getQqchPreparationSurveyExtend();

    List<QqchPreparationSurveyExtend> getQqchPreparationSurveyExtendList(QqchPreparationSurveyExtend qqchPreparationSurveyExtend);

    int insertQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend);

    int insertQqchPreparationSurveyExtendList(List<QqchPreparationSurveyExtend> qqchPreparationSurveyExtendList);

    int updateQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend);

    int updateQqchPreparationSurveyExtendList(List<QqchPreparationSurveyExtend> qqchPreparationSurveyExtendList);

    int deleteQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend);

    int deleteQqchPreparationSurveyExtendByPks(List<Long> qqchPreparationSurveyExtendPkList);
}
