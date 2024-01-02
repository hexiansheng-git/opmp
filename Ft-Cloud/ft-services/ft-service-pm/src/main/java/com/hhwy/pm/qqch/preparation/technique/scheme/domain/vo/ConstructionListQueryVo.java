package com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 施工方案清单
 * @author zhenglili
 * @date 2023-07-13 14:40:32
 * @remark 3.4.2施工方案清单
 */
@Data
public class ConstructionListQueryVo {
    /**
     * 字段描述：方案名称
     */
    private String schemeName;
    /**
     * 字段描述：关联WBS编码
     */
//    @NotBlank(message = "wbs编码不能为空！",groups = ValidationGroups.Select.class)
    private String wbsCode;
    /**
     * 字段描述：方案类型（字典类型scheme_type）
     */
    private String schemeType;
    /**
     * 字段描述：版本
     */
    private BigDecimal version;
}
