package com.hhwy.pm.qqch.sgch.sche.mapper;

import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheFactors;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:52
 * @remark
 */
public interface QqchScheFactorsMapper {

    QqchScheFactors getQqchScheFactors(QqchScheFactors qqchScheFactors);

    List<QqchScheFactors> getQqchScheFactorsList(QqchScheFactors qqchScheFactors);

    int insertQqchScheFactors(QqchScheFactors qqchScheFactors);

    int insertQqchScheFactorsList(@Param("qqchScheFactorsList") List<QqchScheFactors> qqchScheFactorsList);

    int updateQqchScheFactors(QqchScheFactors qqchScheFactors);

    int updateQqchScheFactorsList(@Param("qqchScheFactorsList") List<QqchScheFactors> qqchScheFactorsList);

    int deleteQqchScheFactors(QqchScheFactors qqchScheFactors);

    int deleteQqchScheFactorsByPks(@Param("qqchScheFactorsPkList") List<Long> qqchScheFactorsPkList);
}
