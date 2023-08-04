package com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.service;

import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.domain.QqchQualityRiskControlMeasures;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.domain.vo.QqchQualityRiskControlMeasuresVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 11:20:54
 * @remark 
 */
public interface IQqchQualityRiskControlMeasuresService {
                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchQualityRiskControlMeasures getQqchQualityRiskControlMeasures(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures);

    QqchQualityRiskControlMeasuresVo getQqchQualityRiskControlMeasuresList(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures);

    int insertQqchQualityRiskControlMeasures(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures);

    int updateQqchQualityRiskControlMeasures(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures);

    int updateQqchQualityRiskControlMeasuresList(List<QqchQualityRiskControlMeasures> qqchQualityRiskControlMeasuresList);
    
    int deleteQqchQualityRiskControlMeasures(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures);

    int deleteQqchQualityRiskControlMeasuresByPks(List<Long> qqchQualityRiskControlMeasuresPkList);

    void save(QqchQualityRiskControlMeasuresVo vo);
}
