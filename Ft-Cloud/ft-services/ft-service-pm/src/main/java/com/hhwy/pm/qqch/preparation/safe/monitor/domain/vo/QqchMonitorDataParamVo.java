package com.hhwy.pm.qqch.preparation.safe.monitor.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.monitor.domain.QqchMonitorDataParam;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-10 16:50:46
 * @remark 监控数据参数
 */
@Data
public class QqchMonitorDataParamVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：监控数据参数集合
     */
    private List<QqchMonitorDataParam> treeList;
}
