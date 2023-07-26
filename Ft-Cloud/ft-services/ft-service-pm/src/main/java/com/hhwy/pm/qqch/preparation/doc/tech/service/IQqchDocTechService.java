package com.hhwy.pm.qqch.preparation.doc.tech.service;

import java.util.List;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTech;

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
    }
