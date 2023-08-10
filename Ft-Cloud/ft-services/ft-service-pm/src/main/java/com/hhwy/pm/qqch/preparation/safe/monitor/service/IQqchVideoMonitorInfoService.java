package com.hhwy.pm.qqch.preparation.safe.monitor.service;

import com.hhwy.pm.qqch.preparation.safe.monitor.domain.vo.QqchVideoMonitorInfoVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-10 16:50:39
 * @remark 8.12.1 视频监控信息
 */
public interface IQqchVideoMonitorInfoService {

    QqchVideoMonitorInfoVo getQqchVideoMonitorInfoList(BigDecimal version);

    void batchSave(QqchVideoMonitorInfoVo qqchVideoMonitorInfoVo);
}
