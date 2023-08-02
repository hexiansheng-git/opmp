package com.hhwy.pm.qqch.preparation.doc.tech.service;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTech;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTechVo;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationListVo;

/**
 * @author mls
 * @date 2023-07-25 18:25:45
 * @remark
 */
public interface IQqchDocTechService {

    QqchDocTech getQqchDocTech(QqchDocTech qqchDocTech);

    List<QqchDocTech> getQqchDocTechList(QqchDocTech qqchDocTech);

    int insertQqchDocTech(QqchDocTech qqchDocTech);

    int insertQqchDocTechList(List<QqchDocTech> qqchDocTechList);

    int updateQqchDocTech(QqchDocTech qqchDocTech);

    int updateQqchDocTechList(List<QqchDocTech> qqchDocTechList);

    int deleteQqchDocTech(QqchDocTech qqchDocTech);

    int deleteQqchDocTechByPks(List<Long> qqchDocTechPkList);

    QqchDocTechVo getQqchDocTechListVo(BigDecimal version);

    int insertQqchDocTechListVo(QqchDocTechVo qqchDocTechVo);

}
