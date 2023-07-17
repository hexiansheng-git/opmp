package com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-13 14:40:32
 * @remark 3.4.2施工方案清单
 */
@Data
public class QqchConstructionListVo {

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
     * 字段描述：施工方案清单集合
     */
    private List<QqchConstructionList> list;
}
