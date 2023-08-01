package com.hhwy.pm.qqch.preparation.measureexp.range.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureOrg;
import org.apache.ibatis.annotations.Param;

/**
 * @author mls
 * @date 2023-07-25 18:01:39
 * @remark
 */
public interface QqchMeasureOrgMapper {

    QqchMeasureOrg getQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg);

    List<QqchMeasureOrg> getQqchMeasureOrgList(QqchMeasureOrg qqchMeasureOrg);

    int insertQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg);

    int insertQqchMeasureOrgList(@Param("qqchMeasureOrgList") List<QqchMeasureOrg> qqchMeasureOrgList);

    int updateQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg);

    int updateQqchMeasureOrgList(@Param("qqchMeasureOrgList") List<QqchMeasureOrg> qqchMeasureOrgList);

    int deleteQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg);

    int deleteQqchMeasureOrgByPks(@Param("qqchMeasureOrgPkList") List<Long> qqchMeasureOrgPkList);
}
