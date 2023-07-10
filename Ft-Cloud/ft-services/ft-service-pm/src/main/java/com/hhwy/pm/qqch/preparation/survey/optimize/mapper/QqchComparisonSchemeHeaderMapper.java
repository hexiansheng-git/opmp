package com.hhwy.pm.qqch.preparation.survey.optimize.mapper;

import java.util.List;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeHeader;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-07 18:35:43
 * @remark 比选方案表头
 */
@Repository
public interface QqchComparisonSchemeHeaderMapper {

    QqchComparisonSchemeHeader getQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader);

    List<QqchComparisonSchemeHeader> getQqchComparisonSchemeHeaderList(QqchComparisonSchemeHeader qqchComparisonSchemeHeader);

    int insertQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader);

    int insertQqchComparisonSchemeHeaderList(@Param("qqchComparisonSchemeHeaderList") List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList);

    int updateQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader);

    int updateQqchComparisonSchemeHeaderList(@Param("list") List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList);

    int deleteQqchComparisonSchemeHeader(QqchComparisonSchemeHeader qqchComparisonSchemeHeader);

    int deleteQqchComparisonSchemeHeaderByPks(@Param("qqchComparisonSchemeHeaderPkList") List<Long> qqchComparisonSchemeHeaderPkList);
}
