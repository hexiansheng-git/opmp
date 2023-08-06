package com.hhwy.pm.qqch.preparation.measureexp.tech.service;

import com.hhwy.pm.qqch.preparation.measureexp.tech.domain.vo.QqchMeasureExpTechVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-04 16:09:57
 * @remark 3.6.3测量技术方案计划、3.7.3试验方案计划
 */
public interface IQqchMeasureExpTechService {

    QqchMeasureExpTechVo getQqchMeasureExpTechList(BigDecimal version, String type);

    void insertQqchMeasureExpTechList(QqchMeasureExpTechVo qqchMeasureExpTechVo);
}
