package com.hhwy.pm.qqch.preparation.technique.scheme.mapper;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchSimilarProjectScheme;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-01-05 11:49:06
 * @remark
 */
@Repository
public interface QqchSimilarProjectSchemeMapper {

    QqchSimilarProjectScheme getQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme);

    List<QqchSimilarProjectScheme> getQqchSimilarProjectSchemeList(QqchSimilarProjectScheme qqchSimilarProjectScheme);

    int insertQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme);

    int insertQqchSimilarProjectSchemeList(@Param("qqchSimilarProjectSchemeList") List<QqchSimilarProjectScheme> qqchSimilarProjectSchemeList);

    int updateQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme);

    int updateQqchSimilarProjectSchemeList(@Param("list") List<QqchSimilarProjectScheme> qqchSimilarProjectSchemeList);

    int deleteQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme);

    int deleteQqchSimilarProjectSchemeByPks(@Param("qqchSimilarProjectSchemePkList") List<Long> qqchSimilarProjectSchemePkList);

    void updateBAPByProjectCode(@Param("projectCode") String projectCode,@Param("businessAreasAndProducts") String businessAreasAndProducts);
}
