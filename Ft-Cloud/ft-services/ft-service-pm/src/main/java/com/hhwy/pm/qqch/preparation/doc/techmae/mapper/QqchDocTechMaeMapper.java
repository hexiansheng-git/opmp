package com.hhwy.pm.qqch.preparation.doc.techmae.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.doc.techmae.domain.QqchDocTechMae;

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
