package com.hhwy.pm.xmsl.contractInfo.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @author cjh
 * @date 2023-11-15 11:20:12
 * @remark qyzs_manage_cont_condition
 */
@Data
public class QyzsManageContConditionVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
//   @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
//   @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//   @Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
//    @Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//   @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
//   @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//   @Excel(name = "部门id")
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
//    @Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
//    @Excel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
//    @Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
//    @Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
//    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
//    @Excel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
//    @Excel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
//    @Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
//    @Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
//    @Excel(name = "预留字段5")
    private String ptVar5;
    /**
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
//    @Excel(name = "流程状态（5已完成）")
    private String taskStatus;
    /**
     * 字段描述：合同版本
     */
    @JsonProperty
    @Excel(name = "合同版本")
    private String contVersion;
    /**
     * 字段描述：通用合同条件编号
     */
    @JsonProperty
    @Excel(name = "通用合同条件编号")
    private String contConditionNo;
    /**
     * 字段描述：通用合同条件父编号
     */
    @JsonProperty
    @Excel(name = "通用合同条件父编号")
    private String contConditionPno;
    /**
     * 字段描述：中文条件名称
     */
    @JsonProperty
    @Excel(name = "中文条件名称")
    private String chineseConditonName;
    /**
     * 字段描述：外文条件名称
     */
    @JsonProperty
    @Excel(name = "外文条件名称")
    private String foreignConditonName;
    /**
     * 字段描述：外文条件内容
     */
    @JsonProperty
    @Excel(name = "外文条件内容")
    private String foreignConditionContent;
    /**
     * 字段描述：中文条件内容
     */
    @JsonProperty
    @Excel(name = "中文条件内容")
    private String chineseConditionContent;
    /**
     * 字段描述：父级Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long pid;
    /**
     * 字段描述：是否叶子节点
     */
    @JsonProperty
    private String leaf;

    @JsonProperty
    private String isAdd;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        QyzsManageContConditionVo that = (QyzsManageContConditionVo) o;
        return Objects.equals(id, that.id) && Objects.equals(fileGroupId, that.fileGroupId) && Objects.equals(remark, that.remark) && Objects.equals(regionId, that.regionId) && Objects.equals(regionName, that.regionName) && Objects.equals(projectId, that.projectId) && Objects.equals(projectName, that.projectName) && Objects.equals(deptId, that.deptId) && Objects.equals(createUser, that.createUser) && Objects.equals(createUserName, that.createUserName) && Objects.equals(createTime, that.createTime) && Objects.equals(updateUser, that.updateUser) && Objects.equals(updateTime, that.updateTime) && Objects.equals(delUser, that.delUser) && Objects.equals(delTime, that.delTime) && Objects.equals(delFlag, that.delFlag) && Objects.equals(ptVar1, that.ptVar1) && Objects.equals(ptVar2, that.ptVar2) && Objects.equals(ptVar3, that.ptVar3) && Objects.equals(ptVar4, that.ptVar4) && Objects.equals(ptVar5, that.ptVar5) && Objects.equals(taskStatus, that.taskStatus) && Objects.equals(contVersion, that.contVersion) && Objects.equals(contConditionNo, that.contConditionNo) && Objects.equals(contConditionPno, that.contConditionPno) && Objects.equals(chineseConditonName, that.chineseConditonName) && Objects.equals(foreignConditonName, that.foreignConditonName) && Objects.equals(foreignConditionContent, that.foreignConditionContent) && Objects.equals(chineseConditionContent, that.chineseConditionContent) && Objects.equals(pid, that.pid) && Objects.equals(leaf, that.leaf) && Objects.equals(isAdd, that.isAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, fileGroupId, remark, regionId, regionName, projectId, projectName, deptId, createUser, createUserName, createTime, updateUser, updateTime, delUser, delTime, delFlag, ptVar1, ptVar2, ptVar3, ptVar4, ptVar5, taskStatus, contVersion, contConditionNo, contConditionPno, chineseConditonName, foreignConditonName, foreignConditionContent, chineseConditionContent, pid, leaf, isAdd);
    }
}
