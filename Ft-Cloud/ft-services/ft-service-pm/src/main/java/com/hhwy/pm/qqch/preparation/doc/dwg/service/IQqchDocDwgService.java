package com.hhwy.pm.qqch.preparation.doc.dwg.service;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.pm.qqch.preparation.doc.dwg.domain.QqchDocDwg;
import com.hhwy.pm.qqch.preparation.doc.dwg.domain.QqchDocDwgVo;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTechVo;

/**
 * @author mls
 * @date 2023-07-25 18:25:42
 * @remark
 */
public interface IQqchDocDwgService {

    QqchDocDwg getQqchDocDwg(QqchDocDwg qqchDocDwg);

    List<QqchDocDwg> getQqchDocDwgList(QqchDocDwg qqchDocDwg);

    int insertQqchDocDwg(QqchDocDwg qqchDocDwg);

    int insertQqchDocDwgList(List<QqchDocDwg> qqchDocDwgList);

    int updateQqchDocDwg(QqchDocDwg qqchDocDwg);

    int updateQqchDocDwgList(List<QqchDocDwg> qqchDocDwgList);

    int deleteQqchDocDwg(QqchDocDwg qqchDocDwg);

    int deleteQqchDocDwgByPks(List<Long> qqchDocDwgPkList);

    QqchDocDwgVo getQqchDocDwgVo(BigDecimal version);

    int insertQqchDocDwgVo(QqchDocDwgVo qqchDocDwgVo);
}
