package com.hhwy.pm.qqch.preparation.doc.techmae.service;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTechVo;
import com.hhwy.pm.qqch.preparation.doc.techmae.domain.QqchDocTechMae;
import com.hhwy.pm.qqch.preparation.doc.techmae.domain.QqchDocTechMaeV0;

/**
 * @author mls
 * @date 2023-07-25 18:25:47
 * @remark
 */
public interface IQqchDocTechMaeService {

    QqchDocTechMae getQqchDocTechMae(QqchDocTechMae qqchDocTechMae);

    List<QqchDocTechMae> getQqchDocTechMaeList(QqchDocTechMae qqchDocTechMae);

    int insertQqchDocTechMae(QqchDocTechMae qqchDocTechMae);

    int insertQqchDocTechMaeList(List<QqchDocTechMae> qqchDocTechMaeList);

    int updateQqchDocTechMae(QqchDocTechMae qqchDocTechMae);

    int updateQqchDocTechMaeList(List<QqchDocTechMae> qqchDocTechMaeList);

    int deleteQqchDocTechMae(QqchDocTechMae qqchDocTechMae);

    int deleteQqchDocTechMaeByPks(List<Long> qqchDocTechMaePkList);

    QqchDocTechMaeV0 geteQqchDocTechMaeVo(BigDecimal version);

    int inserteQqchDocTechMaeVo(QqchDocTechMaeV0 qqchDocTechVo);
}
