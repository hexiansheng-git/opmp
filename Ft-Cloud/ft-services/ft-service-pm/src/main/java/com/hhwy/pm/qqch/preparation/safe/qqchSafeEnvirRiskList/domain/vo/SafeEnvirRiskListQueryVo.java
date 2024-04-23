package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.vo;

import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author zq 8.8.1
 * @date 2023-08-14 13:56:17
 * @remark qqch_safe_envir_risk_list
 */
@Data
public class SafeEnvirRiskListQueryVo {

    @NotBlank(message = "wbsId不能为空！",groups = ValidationGroups.Select.class)
    private String wbsId;

    @NotBlank(message = "wbs编码不能为空！",groups = ValidationGroups.Select.class)
    private String wbsCode;

    private BigDecimal version;
}
