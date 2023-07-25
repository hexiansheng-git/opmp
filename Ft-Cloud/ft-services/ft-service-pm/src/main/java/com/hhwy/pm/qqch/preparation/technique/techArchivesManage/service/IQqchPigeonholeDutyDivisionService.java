package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service;

import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchPigeonholeDutyDivision;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:48:27
 * @remark
 */
public interface IQqchPigeonholeDutyDivisionService {

    QqchPigeonholeDutyDivision getQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision);

    List<QqchPigeonholeDutyDivision> getQqchPigeonholeDutyDivisionList(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision);

    int insertQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision);

    int insertQqchPigeonholeDutyDivisionList(List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList);

    int updateQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision);

    int updateQqchPigeonholeDutyDivisionList(List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList);

    int deleteQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision);

    int deleteQqchPigeonholeDutyDivisionByPks(List<Long> qqchPigeonholeDutyDivisionPkList);
}
