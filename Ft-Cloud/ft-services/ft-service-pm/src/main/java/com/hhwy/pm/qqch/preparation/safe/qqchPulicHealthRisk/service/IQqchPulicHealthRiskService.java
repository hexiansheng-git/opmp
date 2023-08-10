package com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.service;

import com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.domain.QqchPulicHealthRisk;
import com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.domain.vo.QqchPulicHealthRiskVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 15:01:10
 * @remark
 */
public interface IQqchPulicHealthRiskService {

    QqchPulicHealthRisk getQqchPulicHealthRisk(QqchPulicHealthRisk qqchPulicHealthRisk);

    QqchPulicHealthRiskVo getQqchPulicHealthRiskList(QqchPulicHealthRisk qqchPulicHealthRisk);

    int insertQqchPulicHealthRisk(QqchPulicHealthRisk qqchPulicHealthRisk);


    int updateQqchPulicHealthRisk(QqchPulicHealthRisk qqchPulicHealthRisk);

    int updateQqchPulicHealthRiskList(List<QqchPulicHealthRisk> qqchPulicHealthRiskList);

    int deleteQqchPulicHealthRisk(QqchPulicHealthRisk qqchPulicHealthRisk);

    int deleteQqchPulicHealthRiskByPks(List<Long> qqchPulicHealthRiskPkList);

    void save(QqchPulicHealthRiskVo vo);
}
