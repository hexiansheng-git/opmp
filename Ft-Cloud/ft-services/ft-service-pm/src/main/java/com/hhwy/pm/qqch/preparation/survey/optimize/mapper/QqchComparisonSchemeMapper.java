package com.hhwy.pm.qqch.preparation.survey.optimize.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonScheme;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-07 18:35:38
 * @remark 重大设计方案比选-方案
 */
@Repository
public interface QqchComparisonSchemeMapper {

    QqchComparisonScheme getQqchComparisonScheme(QqchComparisonScheme qqchComparisonScheme);

    List<QqchComparisonScheme> getQqchComparisonSchemeList(@Param("version") BigDecimal version);

    int insertQqchComparisonScheme(QqchComparisonScheme qqchComparisonScheme);

    int insertQqchComparisonSchemeList(@Param("qqchComparisonSchemeList") List<QqchComparisonScheme> qqchComparisonSchemeList);

    int updateQqchComparisonScheme(QqchComparisonScheme qqchComparisonScheme);

    int updateQqchComparisonSchemeList(@Param("list") List<QqchComparisonScheme> qqchComparisonSchemeList);

    int deleteQqchComparisonScheme(QqchComparisonScheme qqchComparisonScheme);

    int deleteQqchComparisonSchemeByPks(@Param("qqchComparisonSchemePkList") List<Long> qqchComparisonSchemePkList);
}
