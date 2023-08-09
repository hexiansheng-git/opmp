package com.hhwy.domain.base.system.material;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.validation.ValidationGroups;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 中交同步物资设备库对象 t_material_info
 * 
 * @author lcf
 * @date 2022-10-21
 */
public class MaterialInfoVo {
    private static final long serialVersionUID = 1L;
    /** 主键 */
    @NotNull(message = "id不能为空", groups = {ValidationGroups.Update.class})
    private Long id;
    /** 类别编码 */
    @Excel(name = "类别编码")
    @NotBlank(message = "类别编码不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String categoryCode;
    /** 单位 */
    @Excel(name = "单位")
    @NotBlank(message = "单位不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String unit;
    /** 物资设备编码 */
    @Excel(name = "材料编码")
    @NotBlank(message = "材料编码不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialCode;
    /** 物资设备名称 */
    @Excel(name = "材料名称")
    @NotBlank(message = "材料名称不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialName;
    /** 规格型号 */
    @Excel(name = "规格型号")
    @NotBlank(message = "规格型号不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialSpec;
    /** 状态，0-停用 1-启用 */
    @Excel(name = "状态，0-停用 1-启用")
    private String status;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialSpec() {
        return materialSpec;
    }

    public void setMaterialSpec(String materialSpec) {
        this.materialSpec = materialSpec;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
