package com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain;

import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.web.domain.BaseEntity;

import java.util.Date;
import java.math.BigDecimal;

import com.hhwy.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.utils.tree.TreeNode;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述: 技术文件管理 - 施工环节图纸管理
 * @author fsd
 * @date 2024-01-22 09:56:49
 * @remark sgjs_technical_file_blueprint
 *
 */
@Data
public class SgjsTechnicalFileBlueprint extends TreeNode<SgjsTechnicalFileBlueprint> {
    private static final long serialVersionUID = 1L;

    @JsonProperty
    private String isAdd;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：父级id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long pid;
    /**
     * 字段描述：排序
     */
    @JsonProperty
    private Integer sort;
    /**
     * 字段描述：祖籍
     */
    @JsonProperty
    private String ancestors;
    /**
     * 字段描述：图纸编号
     */
    @JsonProperty
    @Excel(name = "图纸编号")
    private String blueprintNum;
    /**
     * 字段描述：图纸名称
     */
    @JsonProperty
    @Excel(name = "图纸名称")
    @NotBlank(message = "图纸名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String blueprintName;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private String version;
    /**
     * 字段描述：图纸数量
     */
    @JsonProperty
    @Excel(name = "发放图纸数量")
    private Integer blueprintCount;
    /**
     * 字段描述：wbs
     */
    @JsonProperty
    private String wbsCode;
    /**
     * 字段描述：wbs名称
     */
    @JsonProperty
    @Excel(name = "wbs范围")
    private String wbsName;
    /**
     * 字段描述：计划开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty
    @Excel(name = "计划开工日期", dateFormat = "yyyy年MM月dd日")
    private Date startDatePlan;
    /**
     * 字段描述：发放人
     */
    @JsonProperty
    private String senderId;
    /**
     * 字段描述：发放人名称
     */
    @JsonProperty
    @Excel(name = "图纸发放人")
    @NotBlank(message = "图纸发放人不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String senderName;
    /**
     * 字段描述：图纸发放日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty
    @Excel(name = "图纸发放日期", dateFormat = "yyyy年MM月dd日")
    private Date sendDate;
    /**
     * 字段描述：图纸接收人
     */
    @JsonProperty
    private Integer receiverId;
    /**
     * 字段描述：图纸接收人名称
     */
    @JsonProperty
    @Excel(name = "图纸接收人")
    @NotBlank(message = "图纸接收人不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String receiverName;
    /**
     * 字段描述：是否变更
     */
    @JsonProperty
    @Excel(name = "是否变更后图纸")
    private String changeOr;
    /**
     * 字段描述：图纸有效性
     */
    @JsonProperty
    @Excel(name = "图纸有效性")
    @NotBlank(message = "图纸有效性不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String blueprintValid;
    /**
     * 字段描述：图纸是否回收
     */
    @JsonProperty
    @Excel(name = "作废图纸是否回收")
    private String recycleOr;
    /**
     * 字段描述：章有效性
     */
    @JsonProperty
    @Excel(name = "有效章/作废章")
    private String signetValid;
    /**
     * 字段描述：作废日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty
    @Excel(name = "图纸作废日期", dateFormat = "yyyy年MM月dd日")
    private Date cancelDate;
    /**
     * 字段描述：附件id
     */
    @JsonProperty
    private String fileGroupId;
    /**
     * 字段描述：
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    private String ptVar5;
}
