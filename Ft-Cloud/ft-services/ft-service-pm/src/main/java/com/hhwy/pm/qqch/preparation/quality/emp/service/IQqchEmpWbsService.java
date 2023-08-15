package com.hhwy.pm.qqch.preparation.quality.emp.service;

import com.hhwy.pm.qqch.preparation.quality.emp.domain.QqchEmpWbs;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-15 09:36:48
 * @remark
 */
public interface IQqchEmpWbsService {

    QqchEmpWbs getQqchEmpWbs(QqchEmpWbs qqchEmpWbs);

    List<QqchEmpWbs> getQqchEmpWbsList(QqchEmpWbs qqchEmpWbs);

    int insertQqchEmpWbs(QqchEmpWbs qqchEmpWbs);

    int insertQqchEmpWbsList(List<QqchEmpWbs> qqchEmpWbsList);

    int updateQqchEmpWbs(QqchEmpWbs qqchEmpWbs);

    int updateQqchEmpWbsList(List<QqchEmpWbs> qqchEmpWbsList);

    int deleteQqchEmpWbs(QqchEmpWbs qqchEmpWbs);

    int deleteQqchEmpWbsByPks(List<Long> qqchEmpWbsPkList);
}
