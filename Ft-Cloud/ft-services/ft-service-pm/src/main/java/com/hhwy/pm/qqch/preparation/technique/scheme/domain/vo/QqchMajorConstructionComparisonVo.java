package com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.pm.qqch.constant.ConfirmStatus;
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
public class QqchMajorConstructionComparisonVo {

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
     * 字段描述：确认状态（0：未确认，1：已确认）
     */
    private String confirmStatus = ConfirmStatus.UNCONFIRMED;

    /**
     * 字段描述：菜单id
     */
    private String menuId;

    /**
     * 字段描述：按钮标识（0：保存，1：确认，2：提交）
     */
    private String buttonMark;

    /**
     * 字段描述：重大施工方案比选集合
     */
    private List<QqchMajorConstructionComparison> treeList;
}
