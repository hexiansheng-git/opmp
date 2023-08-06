package com.hhwy.pm.qqch.preparation.measureexp.equ.service;

import com.hhwy.pm.qqch.preparation.measureexp.equ.domain.vo.QqchMeasureExpEquVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-04 16:12:29
 * @remark 3.6.4测量仪器设备配置计划、3.7.4试验仪器设备配置计划
 */
public interface IQqchMeasureExpEquService {

    QqchMeasureExpEquVo getQqchMeasureExpEquList(BigDecimal version, String type);

    void insertQqchMeasureExpEquList(QqchMeasureExpEquVo qqchMeasureExpEquVo);
}
