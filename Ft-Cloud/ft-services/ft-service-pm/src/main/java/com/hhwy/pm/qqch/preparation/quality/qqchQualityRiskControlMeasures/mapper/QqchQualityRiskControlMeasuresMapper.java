package com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.mapper;

import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.domain.QqchQualityRiskControlMeasures;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 11:20:54
 * @remark 
 */
public interface QqchQualityRiskControlMeasuresMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchQualityRiskControlMeasures getQqchQualityRiskControlMeasures(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures);

    List<QqchQualityRiskControlMeasures> getQqchQualityRiskControlMeasuresList(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures);

    int insertQqchQualityRiskControlMeasures(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures);

    int insertQqchQualityRiskControlMeasuresList(@Param("qqchQualityRiskControlMeasuresList") List<QqchQualityRiskControlMeasures> qqchQualityRiskControlMeasuresList);

    int updateQqchQualityRiskControlMeasures(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures);

            int updateQqchQualityRiskControlMeasuresList(@Param("qqchQualityRiskControlMeasuresList") List<QqchQualityRiskControlMeasures> qqchQualityRiskControlMeasuresList);
    
    int deleteQqchQualityRiskControlMeasures(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures);

            int deleteQqchQualityRiskControlMeasuresByPks(@Param("qqchQualityRiskControlMeasuresPkList") List<Long> qqchQualityRiskControlMeasuresPkList);
    }
