package com.hhwy.pm.qqch.preparation.technique.manage.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchTechManageModeComparison;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-11 15:17:31
 * @remark 3.3.1技术管理模式比选
 */
@Data
public class QqchTechManageModeComparisonVo {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    @JsonProperty
    private String stageIdentity;

    /**
     * 版本状态
     */
    @JsonProperty
    private BigDecimal version;

    /**
     * 技术管理模式比选集合
     */
    private List<QqchTechManageModeComparison> list;
}
