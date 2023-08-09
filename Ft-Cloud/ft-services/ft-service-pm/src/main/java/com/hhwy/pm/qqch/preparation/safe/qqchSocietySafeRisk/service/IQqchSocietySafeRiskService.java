package com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.service;

import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.QqchSocietySafeRisk;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.vo.QqchSocietySafeRiskVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 09:30:14
 * @remark
 */
public interface IQqchSocietySafeRiskService {

    QqchSocietySafeRisk getQqchSocietySafeRisk(QqchSocietySafeRisk qqchSocietySafeRisk);

    QqchSocietySafeRiskVo getQqchSocietySafeRiskList(QqchSocietySafeRisk qqchSocietySafeRisk);

    int insertQqchSocietySafeRisk(QqchSocietySafeRisk qqchSocietySafeRisk);

    int updateQqchSocietySafeRisk(QqchSocietySafeRisk qqchSocietySafeRisk);

    int updateQqchSocietySafeRiskList(List<QqchSocietySafeRisk> qqchSocietySafeRiskList);

    int deleteQqchSocietySafeRisk(QqchSocietySafeRisk qqchSocietySafeRisk);

    int deleteQqchSocietySafeRiskByPks(List<Long> qqchSocietySafeRiskPkList);

    void save(QqchSocietySafeRiskVo vo);
}
