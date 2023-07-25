package com.hhwy.pm.qqch.preparation.measureexp.tech.service;

import java.util.List;
import com.hhwy.pm.qqch.preparation.measureexp.tech.domain.QqchMeasureExpTech;

/**
 * @author mls
 * @date 2023-07-25 18:01:36
 * @remark 
 */
public interface IQqchMeasureExpTechService {
                                                                                                                                                                                                                                                                                                                
    QqchMeasureExpTech getQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech);

    List<QqchMeasureExpTech> getQqchMeasureExpTechList(QqchMeasureExpTech qqchMeasureExpTech);

    int insertQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech);

    int insertQqchMeasureExpTechList(List<QqchMeasureExpTech> qqchMeasureExpTechList);

    int updateQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech);

            int updateQqchMeasureExpTechList(List<QqchMeasureExpTech> qqchMeasureExpTechList);
    
    int deleteQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech);

            int deleteQqchMeasureExpTechByPks(List<Long> qqchMeasureExpTechPkList);
    }
