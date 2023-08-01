package com.hhwy.pm.qqch.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.tree.TreeNode;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 编制通用实体类
 *
 * @author m
 */
@Data
@ToString
public class CompileEntity<T> extends TreeNode<T> {
    /**
     * 版本号
     */
    private BigDecimal version;
    /**
     * 有效状态
     */
    private String valid;


    @NotBlank(message = "保存/确认标识不能为空！",groups = ValidationGroups.Save.class)
    private String submitFlag;

    /**
     * 阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    @JsonProperty
    private String stageIdentity;

    @JsonProperty
    @Excel(name = "模块标识（页面唯一标识）1： 2： ...")
    private String moduleIdentity;


}
