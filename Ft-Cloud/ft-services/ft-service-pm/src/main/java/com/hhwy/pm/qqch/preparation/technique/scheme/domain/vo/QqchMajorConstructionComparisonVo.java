package com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchMajorConstructionComparison;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-13 14:27:03
 * @remark 3.4.1重大施工方案比选
 */
@Data
public class QqchMajorConstructionComparisonVo extends BaseEntity {

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
     * 字段描述：重大施工方案比选集合
     */
    private List<QqchMajorConstructionComparison> treeList;
}
