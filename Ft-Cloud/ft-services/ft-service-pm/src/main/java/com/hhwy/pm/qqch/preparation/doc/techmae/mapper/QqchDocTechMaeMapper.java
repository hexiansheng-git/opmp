package com.hhwy.pm.qqch.preparation.doc.techmae.mapper;

import com.hhwy.pm.qqch.preparation.doc.techmae.domain.QqchDocTechMae;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-25 18:25:47
 * @remark
 */
public interface QqchDocTechMaeMapper {

    QqchDocTechMae getQqchDocTechMae(QqchDocTechMae qqchDocTechMae);

    List<QqchDocTechMae> getQqchDocTechMaeList(QqchDocTechMae qqchDocTechMae);

    int insertQqchDocTechMae(QqchDocTechMae qqchDocTechMae);

    int insertQqchDocTechMaeList(@Param("qqchDocTechMaeList") List<QqchDocTechMae> qqchDocTechMaeList);

    int updateQqchDocTechMae(QqchDocTechMae qqchDocTechMae);

    int updateQqchDocTechMaeList(@Param("qqchDocTechMaeList") List<QqchDocTechMae> qqchDocTechMaeList);

    int deleteQqchDocTechMae(QqchDocTechMae qqchDocTechMae);

    int deleteQqchDocTechMaeByPks(@Param("qqchDocTechMaePkList") List<Long> qqchDocTechMaePkList);
}
