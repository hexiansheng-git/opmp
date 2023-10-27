package com.hhwy.pm.qqch.preparation.safe.risk.domain.vo;

import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author zq
 * @date 2023-08-11 13:41:25
 * @remark qqch_safe_risk_list
 */
@Data
public class SafeRiskListQueryVo {

    @NotBlank(message = "类型不能为空！",groups = ValidationGroups.Select.class)
    private String type;

    @NotBlank(message = "wbsId不能为空！",groups = ValidationGroups.Select.class)
    private String wbsId;

    @NotBlank(message = "wbsI编码不能为空！",groups = ValidationGroups.Select.class)
    private String wbsCode;

    private BigDecimal version;
}
