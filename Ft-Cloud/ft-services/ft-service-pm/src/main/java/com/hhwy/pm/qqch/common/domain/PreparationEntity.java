package com.hhwy.pm.qqch.common.domain;

import com.hhwy.utils.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 编制通用实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreparationEntity {

    /**
     * 字段描述：阶段标识
     */
    @NotBlank(message = "阶段标识不能为空！",groups = ValidationGroups.Save.class)
    private String stageIdentity;
    /**
     * 字段描述：版本
     */
    @NotNull(message = "版本不能为空！",groups = ValidationGroups.Save.class)
    private BigDecimal version;
    /**
     * 字段描述：菜单id
     */
    @NotBlank(message = "菜单id不能为空！",groups = ValidationGroups.Save.class)
    private String menuId;
    /**
     * 字段描述：按钮标识（0：保存，1：确认，2：提交）
     */
    @NotBlank(message = "按钮标识不能为空！",groups = ValidationGroups.Save.class)
    private String buttonMark;
}
