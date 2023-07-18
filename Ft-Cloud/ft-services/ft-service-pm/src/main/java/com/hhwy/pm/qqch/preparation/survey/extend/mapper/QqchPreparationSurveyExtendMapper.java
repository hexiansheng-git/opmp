package com.hhwy.pm.qqch.preparation.survey.extend.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.survey.extend.domain.QqchPreparationSurveyExtend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-07 18:36:03
 * @remark 前期策划-前期策划编制-勘察设计策划-扩展
 */
@Repository
public interface QqchPreparationSurveyExtendMapper {

    /**
     * 获取扩展数据
     * @return
     * @param qqchPreparationSurveyExtend
     */
    QqchPreparationSurveyExtend getQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend);

    List<QqchPreparationSurveyExtend> getQqchPreparationSurveyExtendList(QqchPreparationSurveyExtend qqchPreparationSurveyExtend);

    int insertQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend);

    int insertQqchPreparationSurveyExtendList(@Param("qqchPreparationSurveyExtendList") List<QqchPreparationSurveyExtend> qqchPreparationSurveyExtendList);

    int updateQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend);

    int updateQqchPreparationSurveyExtendList(@Param("qqchPreparationSurveyExtendList") List<QqchPreparationSurveyExtend> qqchPreparationSurveyExtendList);

    int deleteQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend);

    int deleteQqchPreparationSurveyExtendByPks(@Param("qqchPreparationSurveyExtendPkList") List<Long> qqchPreparationSurveyExtendPkList);
}
