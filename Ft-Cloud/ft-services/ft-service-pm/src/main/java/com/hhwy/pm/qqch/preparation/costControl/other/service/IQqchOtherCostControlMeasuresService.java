package com.hhwy.pm.qqch.preparation.costControl.other.service;

import com.hhwy.pm.qqch.preparation.costControl.other.domain.vo.QqchOtherCostControlMeasuresVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-11 09:38:56
 * @remark 5.5 其他成本管控工作安排及措施
 */
public interface IQqchOtherCostControlMeasuresService {

    QqchOtherCostControlMeasuresVo getQqchOtherCostControlMeasuresList(BigDecimal version);

    void batchSave(QqchOtherCostControlMeasuresVo qqchOtherCostControlMeasuresVo);
}
