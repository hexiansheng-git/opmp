package com.hhwy.domain.base.system.material;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.data.annotation.Transient;

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
public class MaterialCategory extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @NotNull(message = "id不能为空",groups = {ValidationGroups.Delete.class, ValidationGroups.Update.class})
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    //父节点id
    private Long pid;
    //父节点编码
    @NotBlank(message = "父节点编码不能为空",groups = {ValidationGroups.Save.class})
    private String pcode;

    /** 类别编码 */
    @Excel(name = "类别编码")
    @NotBlank(message = "类别编码不能为空",groups = {ValidationGroups.Save.class})
    private String categoryCode;

    /** 类别名称 */
    @Excel(name = "类别名称")
    @NotBlank(message = "类别名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String categoryName;

    private String parentCategoryName;

    /** 排序规则 */
    @Excel(name = "排序规则")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long sort;

    @Excel(name="别名")
    private String alias;

    /** 级次 */
    @Excel(name = "级次")
    private String level;

    /** 状态,0-停用 1-启用 */
    @Excel(name = "状态,0-停用 1-启用")
    private String status;

    /** 数据删除者 */
    @Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 0有效1无效 */
    private String delFlag;
    @Transient
    private List<String> ids;

    private Integer type;

    @NotNull(message = "nodeId1不能为空",groups = {ValidationGroups.Other.class})
    private Long nodeId1;
    @NotNull(message = "nodeId2不能为空",groups = {ValidationGroups.Other.class})
    private Long nodeId2;
    @NotBlank(message = "nodeStatus不能为空",groups = {ValidationGroups.Other.class})
    private String nodeStatus;
    private String sameLevel;

    private List<String> categoryCodes;
    
    private String path;

    public List<String> getCategoryCodes() {
        return categoryCodes;
    }

    public void setCategoryCodes(List<String> categoryCodes) {
        this.categoryCodes = categoryCodes;
    }

    public String getSameLevel() {
        return sameLevel;
    }

    public void setSameLevel(String sameLevel) {
        this.sameLevel = sameLevel;
    }

    public Long getNodeId1() {
        return nodeId1;
    }

    public void setNodeId1(Long nodeId1) {
        this.nodeId1 = nodeId1;
    }

    public Long getNodeId2() {
        return nodeId2;
    }

    public void setNodeId2(Long nodeId2) {
        this.nodeId2 = nodeId2;
    }

    public String getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(String nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }
}
