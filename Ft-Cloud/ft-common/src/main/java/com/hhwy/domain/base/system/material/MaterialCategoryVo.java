package com.hhwy.domain.base.system.material;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.validation.ValidationGroups;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 物料分类名称对象 t_material_category
 * 
 * @author lcf
 * @date 2022-10-21
 */
public class MaterialCategoryVo {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @NotNull(message = "id不能为空",groups = {ValidationGroups.Update.class})
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    //父节点id
    @JsonSerialize(using= ToStringSerializer.class)
    private Long pid;
    //父节点编码
    private String pcode;

    @NotBlank(message = "类型不能为空",groups ={ValidationGroups.Select.class} )
    private String type;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date updateTime;

    /** 类别编码 */
    @Excel(name = "类别编码")
    @NotBlank(message = "类别编码不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String categoryCode;

    /** 类别名称 */
    @Excel(name = "类别名称")
    @NotBlank(message = "类别名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String categoryName;

    /** 排序规则 */
    @Excel(name = "排序规则")
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "排序规则不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private Long sort;

    /** 级次 */
    @Excel(name = "级次")
    @NotBlank(message = "级别不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String level;

    /** 状态,0-停用 1-启用 */
    @Excel(name = "状态,0-启用 1-停用")
    private String status;

    private String path;

    private Integer pageNum;

    private Integer pageSize;

    private Boolean showFlag = false;

    private List<MaterialCategoryVo> children;

    public List<MaterialCategoryVo> getChildren() {
        return children;
    }

    public void setChildren(List<MaterialCategoryVo> children) {
        this.children = children;
    }

    public Boolean getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(Boolean showFlag) {
        this.showFlag = showFlag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPcode() {
        return pcode;
    }

    public String getPath() {
        return path;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
