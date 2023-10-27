package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.vo;

import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author zq8.8.2
 * @date 2023-08-14 14:04:02
 * @remark qqch_safe_most_envir_risk_list
 */
@Data
public class SafeMostEnvirRiskListQueryVo {

    @NotNull(message = "wbsId不能为空！",groups = ValidationGroups.Select.class)
    private Long wbsId;

    @NotBlank(message = "wbsI编码不能为空！",groups = ValidationGroups.Select.class)
    private String wbsCode;

    private BigDecimal version;
}
