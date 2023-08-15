package com.hhwy.pm.qqch.preparation.quality.emp.mapper;

import com.hhwy.pm.qqch.preparation.quality.emp.domain.QqchEmpWbs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-15 09:36:48
 * @remark
 */
public interface QqchEmpWbsMapper {

    QqchEmpWbs getQqchEmpWbs(QqchEmpWbs qqchEmpWbs);

    List<QqchEmpWbs> getQqchEmpWbsList(QqchEmpWbs qqchEmpWbs);

    int insertQqchEmpWbs(QqchEmpWbs qqchEmpWbs);

    int insertQqchEmpWbsList(@Param("qqchEmpWbsList") List<QqchEmpWbs> qqchEmpWbsList);

    int updateQqchEmpWbs(QqchEmpWbs qqchEmpWbs);

    int updateQqchEmpWbsList(@Param("qqchEmpWbsList") List<QqchEmpWbs> qqchEmpWbsList);

    int deleteQqchEmpWbs(QqchEmpWbs qqchEmpWbs);

    int deleteQqchEmpWbsByPks(@Param("qqchEmpWbsPkList") List<Long> qqchEmpWbsPkList);
}
