package com.hhwy.utils.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author zqq
 * @create 2023-08-24 10:52
 */
@Data
public class MyPrepareBaseEntity extends BaseEntity {
    @NotBlank(message = "项目编码不能为空！",groups = ValidationGroups.Save.class)
    private String prjCode;
    private String unicode;
    private String valid;
    private String isValid;
    private BigDecimal versionNo;
    private String formNo;
    private String titleName;
    @NotBlank(message = "项目名称不能为空！",groups = ValidationGroups.Save.class)
    private String projectName;
    private String createUserName;
    private Long deptId;
    private String updateUserName;
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

    @JsonProperty
    @Excel(name = "模块标识（页面唯一标识）1： 2： ...")
    private String moduleIdentity;
}
