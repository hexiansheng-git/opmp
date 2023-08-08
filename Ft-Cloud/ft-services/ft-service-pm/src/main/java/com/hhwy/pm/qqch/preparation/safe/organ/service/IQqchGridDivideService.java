package com.hhwy.pm.qqch.preparation.safe.organ.service;

import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchGridDivideVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-07 13:52:31
 * @remark 8.1.4 格子划分
 */
public interface IQqchGridDivideService {

    QqchGridDivideVo getQqchGridDivideList(BigDecimal version);

    void batchSave(QqchGridDivideVo qqchGridDivideVo);
}
