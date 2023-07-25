package com.hhwy.pm.qqch.preparation.technique.expert.service;

import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetAdvisoryOrgan;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:56:43
 * @remark
 */
public interface IQqchTargetAdvisoryOrganService {

    QqchTargetAdvisoryOrgan getQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    List<QqchTargetAdvisoryOrgan> getQqchTargetAdvisoryOrganList(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    int insertQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    int insertQqchTargetAdvisoryOrganList(List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganList);

    int updateQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    int updateQqchTargetAdvisoryOrganList(List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganList);

    int deleteQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    int deleteQqchTargetAdvisoryOrganByPks(List<Long> qqchTargetAdvisoryOrganPkList);
}
