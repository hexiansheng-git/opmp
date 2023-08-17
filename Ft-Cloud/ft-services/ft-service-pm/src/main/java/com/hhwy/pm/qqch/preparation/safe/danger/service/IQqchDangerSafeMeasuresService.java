package com.hhwy.pm.qqch.preparation.safe.danger.service;

import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerSafeMeasuresVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-07 14:23:10
 * @remark 8.3.2 危大工程安全技术措施
 */
public interface IQqchDangerSafeMeasuresService {

    /**
     * 列表
     *
     * @param version
     * @return
     */
    QqchDangerSafeMeasuresVo getQqchDangerSafeMeasuresList(BigDecimal version);

    /**
     * 保存/确认/提交
     *
     * @param qqchDangerSafeMeasuresVo
     * @return
     */
    void batchSave(QqchDangerSafeMeasuresVo qqchDangerSafeMeasuresVo);
}
