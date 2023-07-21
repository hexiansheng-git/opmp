package com.hhwy.pm.qqch.preparation.technique.disclose.service;

import com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo.QqchDiscloseThirdVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-07-21 14:26:42
 * @remark 3.5.2三级交底
 */
public interface IQqchDiscloseThirdService {

    QqchDiscloseThirdVo getQqchDiscloseThirdList(BigDecimal version);

    void batchSave(QqchDiscloseThirdVo qqchDiscloseThirdVo);
}
