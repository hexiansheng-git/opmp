package com.hhwy.pm.xmsl.wbs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wk
 * @date 2023-07-13 18:13:13
 * @remark
 */
@Data
public class XmslWbs extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public XmslWbs() {
    }

    public XmslWbs(String id) {
        this.id = id;
    }

    /**
     * 字段描述：主键id
     */
    @JsonProperty
    @Excel(name = "主键id")
    @NotBlank(message = "Id不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String id;
    /**
     * 字段描述：主表id,xmsl_wbs_main.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主表id,xmsl_wbs_main.id")
    private Long mainId;
    /**
     * 字段描述：编号，单位工程按整百递增，分部分项子分项按三位流水号递增
     */
    @JsonProperty
    @Excel(name = "编号，单位工程按整百递增，分部分项子分项按三位流水号递增")
    @NotBlank(message = "编号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String code;
    /**
     * 字段描述：父级ID,最顶级为0
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父级ID,最顶级为-1")
    private String parentId;
    /**
     * 字段描述：是否包含子级，0:否,1:是
     */
    @JsonProperty
    @Excel(name = "是否包含子级，0:否,1:是")
    private Integer haveChildren;
    /**
     * 字段描述：祖级ID
     */
    @JsonProperty
    @Excel(name = "祖级ID")
    @NotBlank(message = "祖级ID不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String ancestors;
    /**
     * 字段描述：祖级名称
     */
    @JsonProperty
    @Excel(name = "祖级名称")
    @NotBlank(message = "祖级名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String ancestorsName;
    /**
     * 字段描述：项目部位
     */
    @JsonProperty
    @Excel(name = "项目部位")
    @NotBlank(message = "项目部位不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String partCode;
    /**
     * 字段描述：名称
     */
    @JsonProperty
    @Excel(name = "名称")
    private String name;
    /**
     * 字段描述：清单编码
     */
    @JsonProperty
    @Excel(name = "清单编码")
    private String listCode;
    /**
     * 字段描述：标准wbsId，预留
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "标准wbsId，预留")
    private Long standardId;
    /**
     * 字段描述：标准wbs编码
     */
    @JsonProperty
    @Excel(name = "标准wbs编码")
    private String standardCode;
    /**
     * 字段描述：标准wbs名称
     */
    @JsonProperty
    @Excel(name = "标准wbs名称")
    private String standardName;
    /**
     * 字段描述：节点类型,字典:xmsl_wbs_type
     */
    @JsonProperty
    @Excel(name = "节点类型,字典:xmsl_wbs_type")
    @NotBlank(message = "节点类型不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String nodeType;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位,字典:xmsl_wbs_unit 暂时废弃")
    private String unit;
    /**
     * 字段描述：层级
     */
    @JsonProperty
    @Excel(name = "层级")
    private Integer level;
    /**
     * 字段描述：状态,0:停用,1:启用
     */
    @JsonProperty
    @Excel(name = "状态,0:停用,1:启用")
    private Integer status;
    /**
     * 字段描述：设计量
     */
    @JsonProperty
    @Excel(name = "设计量 暂时废弃")
    private BigDecimal designQuanlity;
    /**
     * 字段描述：复核量
     */
    @JsonProperty
    @Excel(name = "复核量 暂时废弃")
    private BigDecimal checkQuanlity;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "用户id")
    private Long userId;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "部门id")
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    @Excel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    @Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    @Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    @Excel(name = "预留字段1 生效状态，0：未生效,1：已生效")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    @Excel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    @Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    @Excel(name = "预留字段5")
    private String ptVar5;

    //序号
    private Integer sort;

    //不存在于数据库
    @JsonProperty
    private String listIds;   //清单ID
    private String wbsId;


    @JsonIgnore
    public Long getMainId() {
        return mainId;
    }

    @JsonIgnore
    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    @JsonIgnore
    public String getCode() {
        return code;
    }

    @JsonIgnore
    public void setCode(String code) {
        this.code = code;
    }

    @JsonIgnore
    public Integer getHaveChildren() {
        return haveChildren;
    }

    @JsonIgnore
    public void setHaveChildren(Integer haveChildren) {
        this.haveChildren = haveChildren;
    }

    @JsonIgnore
    public String getAncestors() {
        return ancestors;
    }

    @JsonIgnore
    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    @JsonIgnore
    public String getAncestorsName() {
        return ancestorsName;
    }

    @JsonIgnore
    public void setAncestorsName(String ancestorsName) {
        this.ancestorsName = ancestorsName;
    }

    @JsonIgnore
    public String getPartCode() {
        return partCode;
    }

    @JsonIgnore
    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getListCode() {
        return listCode;
    }

    @JsonIgnore
    public void setListCode(String listCode) {
        this.listCode = listCode;
    }

    @JsonIgnore
    public Long getStandardId() {
        return standardId;
    }

    @JsonIgnore
    public void setStandardId(Long standardId) {
        this.standardId = standardId;
    }

    @JsonIgnore
    public String getStandardCode() {
        return standardCode;
    }

    @JsonIgnore
    public void setStandardCode(String standardCode) {
        this.standardCode = standardCode;
    }

    @JsonIgnore
    public String getStandardName() {
        return standardName;
    }

    @JsonIgnore
    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    @JsonIgnore
    public String getNodeType() {
        return nodeType;
    }

    @JsonIgnore
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    @JsonIgnore
    public String getUnit() {
        return unit;
    }

    @JsonIgnore
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonIgnore
    public Integer getLevel() {
        return level;
    }

    @JsonIgnore
    public void setLevel(Integer level) {
        this.level = level;
    }

    @JsonIgnore
    public Integer getStatus() {
        return status;
    }

    @JsonIgnore
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonIgnore
    public BigDecimal getDesignQuanlity() {
        return designQuanlity;
    }

    @JsonIgnore
    public void setDesignQuanlity(BigDecimal designQuanlity) {
        this.designQuanlity = designQuanlity;
    }

    @JsonIgnore
    public BigDecimal getCheckQuanlity() {
        return checkQuanlity;
    }

    @JsonIgnore
    public void setCheckQuanlity(BigDecimal checkQuanlity) {
        this.checkQuanlity = checkQuanlity;
    }

    @JsonIgnore
    public String getRemark() {
        return remark;
    }

    @JsonIgnore
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonIgnore
    public Long getRegionId() {
        return regionId;
    }

    @JsonIgnore
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @JsonIgnore
    public String getRegionName() {
        return regionName;
    }

    @JsonIgnore
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @JsonIgnore
    public Long getProjectId() {
        return projectId;
    }

    @JsonIgnore
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @JsonIgnore
    public String getProjectName() {
        return projectName;
    }

    @JsonIgnore
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @JsonIgnore
    public Long getUserId() {
        return userId;
    }

    @JsonIgnore
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JsonIgnore
    public Long getDeptId() {
        return deptId;
    }

    @JsonIgnore
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @JsonIgnore
    public String getCreateUser() {
        return createUser;
    }

    @JsonIgnore
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @JsonIgnore
    public String getCreateUserName() {
        return createUserName;
    }

    @JsonIgnore
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @JsonIgnore
    public Date getCreateTime() {
        return createTime;
    }

    @JsonIgnore
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonIgnore
    public String getUpdateUser() {
        return updateUser;
    }

    @JsonIgnore
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @JsonIgnore
    public Date getUpdateTime() {
        return updateTime;
    }

    @JsonIgnore
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @JsonIgnore
    public String getDelUser() {
        return delUser;
    }

    @JsonIgnore
    public void setDelUser(String delUser) {
        this.delUser = delUser;
    }

    @JsonIgnore
    public Date getDelTime() {
        return delTime;
    }

    @JsonIgnore
    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    @JsonIgnore
    public String getDelFlag() {
        return delFlag;
    }

    @JsonIgnore
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @JsonIgnore
    public String getPtVar1() {
        return ptVar1;
    }

    @JsonIgnore
    public void setPtVar1(String ptVar1) {
        this.ptVar1 = ptVar1;
    }

    @JsonIgnore
    public String getPtVar2() {
        return ptVar2;
    }

    @JsonIgnore
    public void setPtVar2(String ptVar2) {
        this.ptVar2 = ptVar2;
    }

    @JsonIgnore
    public String getPtVar3() {
        return ptVar3;
    }

    @JsonIgnore
    public void setPtVar3(String ptVar3) {
        this.ptVar3 = ptVar3;
    }

    @JsonIgnore
    public String getPtVar4() {
        return ptVar4;
    }

    @JsonIgnore
    public void setPtVar4(String ptVar4) {
        this.ptVar4 = ptVar4;
    }

    @JsonIgnore
    public String getPtVar5() {
        return ptVar5;
    }

    @JsonIgnore
    public void setPtVar5(String ptVar5) {
        this.ptVar5 = ptVar5;
    }
}
