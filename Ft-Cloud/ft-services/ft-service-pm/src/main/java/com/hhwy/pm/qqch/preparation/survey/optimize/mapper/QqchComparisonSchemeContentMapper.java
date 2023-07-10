package com.hhwy.pm.qqch.preparation.survey.optimize.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-07 19:02:54
 * @remark 比选方案比选内容
 */
@Repository
public interface QqchComparisonSchemeContentMapper {

    QqchComparisonSchemeContent getQqchComparisonSchemeContent(QqchComparisonSchemeContent qqchComparisonSchemeContent);

    List<QqchComparisonSchemeContent> getQqchComparisonSchemeContentList(QqchComparisonSchemeContent qqchComparisonSchemeContent);

    int insertQqchComparisonSchemeContent(QqchComparisonSchemeContent qqchComparisonSchemeContent);

    int insertQqchComparisonSchemeContentList(@Param("qqchComparisonSchemeContentList") List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList);

    int updateQqchComparisonSchemeContent(QqchComparisonSchemeContent qqchComparisonSchemeContent);

    int updateQqchComparisonSchemeContentList(@Param("list") List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList);

    int deleteQqchComparisonSchemeContent(QqchComparisonSchemeContent qqchComparisonSchemeContent);

    int deleteQqchComparisonSchemeContentByPks(@Param("qqchComparisonSchemeContentPkList") List<Long> qqchComparisonSchemeContentPkList);
}
