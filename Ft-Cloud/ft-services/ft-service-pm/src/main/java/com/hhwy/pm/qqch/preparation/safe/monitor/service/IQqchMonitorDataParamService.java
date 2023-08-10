package com.hhwy.pm.qqch.preparation.safe.monitor.service;

import com.hhwy.pm.qqch.preparation.safe.monitor.domain.vo.QqchMonitorDataParamVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-10 16:50:46
 * @remark 8.12.2 监控数据参数
 */
public interface IQqchMonitorDataParamService {

    QqchMonitorDataParamVo getQqchMonitorDataParamList(BigDecimal version);

    void batchSave(QqchMonitorDataParamVo qqchMonitorDataParamVo);
}
