package com.hhwy.pm.qqch.preparation.technique.disclose.service;

import com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo.QqchDiscloseFirstSecondVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-07-20 15:07:49
 * @remark 3.5.1一、二级交底
 */
public interface IQqchDiscloseFirstSecondService {

    QqchDiscloseFirstSecondVo getQqchDiscloseFirstSecondList(BigDecimal version);

    void batchSave(QqchDiscloseFirstSecondVo qqchDiscloseFirstSecondVo);
}
