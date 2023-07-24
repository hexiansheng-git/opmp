package com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.pm.qqch.constant.ConfirmStatus;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchDangerConstructionList;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-17 14:26:41
 * @remark qqch_danger_construction_list
 */
@Data
public class QqchDangerConstructionListVo {

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
     * 字段描述：危大工程方案清单集合
     */
    private List<QqchDangerConstructionList> list;
}
