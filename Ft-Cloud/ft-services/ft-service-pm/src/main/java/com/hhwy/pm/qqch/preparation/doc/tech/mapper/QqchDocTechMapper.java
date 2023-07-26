package com.hhwy.pm.qqch.preparation.doc.tech.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTech;

/**
 * @author mls
 * @date 2023-07-25 18:25:45
 * @remark 
 */
public interface QqchDocTechMapper {
                                                                                                                                                                                                                                                                            
    QqchDocTech getQqchDocTech(QqchDocTech qqchDocTech);

    List<QqchDocTech> getQqchDocTechList(QqchDocTech qqchDocTech);

    int insertQqchDocTech(QqchDocTech qqchDocTech);

    int insertQqchDocTechList(@Param("qqchDocTechList") List<QqchDocTech> qqchDocTechList);

    int updateQqchDocTech(QqchDocTech qqchDocTech);

            int updateQqchDocTechList(@Param("qqchDocTechList") List<QqchDocTech> qqchDocTechList);
    
    int deleteQqchDocTech(QqchDocTech qqchDocTech);

            int deleteQqchDocTechByPks(@Param("qqchDocTechPkList") List<Long> qqchDocTechPkList);
    }
