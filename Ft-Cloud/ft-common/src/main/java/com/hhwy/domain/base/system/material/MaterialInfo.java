package com.hhwy.domain.base.system.material;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
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
public class MaterialInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @NotNull(message = "id不能为空", groups = {ValidationGroups.Update.class})
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 类别编码 */
    @Excel(name = "类别编码")
    @NotBlank(message = "类别编码不能为空",groups = {ValidationGroups.Save.class})
    private String categoryCode;

    /** 类别名称 */
    private String categoryName;

    //编码分类id
    @JsonSerialize(using= ToStringSerializer.class)
    private Long categoryId;

    /** 单位 */
    @Excel(name = "单位")
    @NotBlank(message = "单位不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String unit;

//    @NotEmpty(message = "单位编码不能为空",groups = {ValidationGroups.Save.class,ValidationGroups.Update.class})
    private String unitCode;

    @Excel(name="别名")
    private String alias;

    /** 物资设备编码 */
    @Excel(name = "材料编码")
    //@NotBlank(message = "材料编码不能为空",groups = {ValidationGroups.Save.class,ValidationGroups.Update.class})
    private String materialCode;

    /** 物资设备名称 */
    @Excel(name = "材料名称")
    @NotBlank(message = "材料名称不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialName;

    //排序
    private Long sort;

    //英文名称
    private String materialEnName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    @NotBlank(message = "规格型号不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String materialSpec;

    /** 状态，0-停用 1-启用 */
    @Excel(name = "状态，0-停用 1-启用")
    private String status;

    /** 0-物资 1-设备 */
    @Excel(name = "0-物资 1-设备")
    @NotNull(message = "材料类型不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Integer type;

    /*物资类型,字典：material_type*/
    private String materialType;

    /** 数据删除者 */
    @Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    private String delFlag;

    @NotNull(message = "页码不能为空",groups = {ValidationGroups.Select.class})
    private Integer pageNum;

    @NotNull(message = "页数不能为空",groups = {ValidationGroups.Select.class})
    private Integer pageSize;
    //是否手动分页   空否 不空是
    private String isFalg;

    //所属机型
    private String ownType;
    //所属部位
    private String ownSite;
    //配件号
    private String partNo;

    public String getOwnType() {
        return ownType;
    }

    public void setOwnType(String ownType) {
        this.ownType = ownType;
    }

    public String getOwnSite() {
        return ownSite;
    }

    public void setOwnSite(String ownSite) {
        this.ownSite = ownSite;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getIsFalg() {
        return isFalg;
    }

    public void setIsFalg(String isFalg) {
        this.isFalg = isFalg;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getMaterialEnName() {
        return materialEnName;
    }

    public void setMaterialEnName(String materialEnName) {
        this.materialEnName = materialEnName;
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

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getdelUser() {
        return delUser;
    }

    public void setdelUser(String delUser) {
        this.delUser = delUser;
    }

    public Date getdelTime() {
        return delTime;
    }

    public void setdelTime(Date delTime) {
        this.delTime = delTime;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
}
