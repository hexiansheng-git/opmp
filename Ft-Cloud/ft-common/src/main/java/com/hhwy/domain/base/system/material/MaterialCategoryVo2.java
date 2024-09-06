package com.hhwy.domain.base.system.material;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.tree.TreeNode;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 物料分类名称对象 t_material_category
 * 
 * @author lcf
 * @date 2022-10-21
 */
@Data
public class MaterialCategoryVo2 extends TreeNode<MaterialCategoryVo2> {
    private static final long serialVersionUID = 1L;

    private Long id;
    //父节点id
    @JsonSerialize(using= ToStringSerializer.class)
    private Long pid;
    //父节点编码
    private String pcode;

    /** 类别编码 */
    @Excel(name = "类别编码")
    @NotBlank(message = "类别编码不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String categoryCode;

    /** 类别名称 */
    @Excel(name = "类别名称")
    @NotBlank(message = "类别名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String categoryName;

    /** 状态,0-停用 1-启用 */
    @Excel(name = "状态,0-停用 1-启用")
    private String status;

    private String ptVar5;

}
