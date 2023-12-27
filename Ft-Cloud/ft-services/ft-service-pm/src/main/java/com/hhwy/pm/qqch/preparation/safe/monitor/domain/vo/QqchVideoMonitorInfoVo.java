package com.hhwy.pm.qqch.preparation.safe.monitor.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.monitor.domain.QqchVideoMonitorInfo;
import lombok.Data;

import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-10 16:50:39
 * @remark 视频监控信息
 */
@Data
public class QqchVideoMonitorInfoVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：视频监控信息集合
     */
    private List<QqchVideoMonitorInfo> list;
}
