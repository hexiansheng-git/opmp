package com.hhwy.pm.qqch.preparation.measureexp.tech.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.measureexp.tech.domain.QqchMeasureExpTech;
import org.apache.ibatis.annotations.Param;

/**
 * @author mls
 * @date 2023-07-25 18:01:36
 * @remark 
 */
public interface QqchMeasureExpTechMapper {
                                                                                                                                                                                                                                                                                                                
    QqchMeasureExpTech getQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech);

    List<QqchMeasureExpTech> getQqchMeasureExpTechList(QqchMeasureExpTech qqchMeasureExpTech);

    int insertQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech);

    int insertQqchMeasureExpTechList(@Param("qqchMeasureExpTechList") List<QqchMeasureExpTech> qqchMeasureExpTechList);

    int updateQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech);

            int updateQqchMeasureExpTechList(@Param("qqchMeasureExpTechList") List<QqchMeasureExpTech> qqchMeasureExpTechList);
    
    int deleteQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech);

            int deleteQqchMeasureExpTechByPks(@Param("qqchMeasureExpTechPkList") List<Long> qqchMeasureExpTechPkList);
    }
