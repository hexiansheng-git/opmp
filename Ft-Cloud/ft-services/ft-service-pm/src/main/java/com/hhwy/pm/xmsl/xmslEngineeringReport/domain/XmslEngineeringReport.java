package com.hhwy.pm.xmsl.xmslEngineeringReport.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程量清单
 * @author wk
 * @date 2023-08-14 13:48:27
 * @remark xmsl_engineering_report
 */
@Data
public class XmslEngineeringReport extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：报表类型,1:wbs,2:清单
     */
    @JsonProperty
    @Excel(name = "报表类型,1:wbs,2:清单")
    @NotNull(message = "报表类型不能为空",groups = {ValidationGroups.Select.class})
    private Integer reportType;
    /**
     * 字段描述：主表id,xmsl_wbs_main.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主表id,xmsl_wbs_main.id")
    private Long mainId;
    /**
     * 字段描述：父级ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父级ID")
    private Long parentId;
    /**
     * 字段描述：WBS编号
     */
    @JsonProperty
    @Excel(name = "WBS编号")
    private Long wbsId;
    /**
     * 字段描述：WBS编号
     */
    @JsonProperty
    @Excel(name = "WBS编号")
    private String wbsCode;
    /**
     * 字段描述：WBS名称
     */
    @JsonProperty
    @Excel(name = "WBS名称")
    private String wbsName;
    /**
     * 字段描述：节点类型,字典:xmsl_wbs_type
     */
    @JsonProperty
    @Excel(name = "节点类型,字典:xmsl_wbs_type")
    private String nodeType;
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
    private String ancestors;
    /**
     * 字段描述：祖级名称
     */
    @JsonProperty
    @Excel(name = "祖级名称")
    private String ancestorsName;
    /**
     * 字段描述：项目部位
     */
    @JsonProperty
    @Excel(name = "项目部位")
    private String partCode;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String wbsUnit;
    /**
     * 字段描述：层级
     */
    @JsonProperty
    @Excel(name = "层级")
    private Integer level;
    private Long listId;
    /**
     * 字段描述：清单编号
     */
    @JsonProperty
    @Excel(name = "清单编号")
    private String listCode;
    /**
     * 字段描述：清单中文名称
     */
    @JsonProperty
    @Excel(name = "清单中文名称")
    private String listName;
    /**
     * 字段描述：单位编码
     */
    @JsonProperty
    @Excel(name = "单位编码")
    private String unitCode;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：设计量
     */
    @JsonProperty
    @Excel(name = "设计量")
    private BigDecimal designQuanlity;
    /**
     * 字段描述：复核量
     */
    @JsonProperty
    @Excel(name = "复核量")
    private BigDecimal checkQuanlity;
    /**
     * 字段描述：形象进度单元,0:否,1:是
     */
    @JsonProperty
    @Excel(name = "形象进度单元,0:否,1:是")
    private String imageProgress;
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
    @Excel(name = "预留字段1")
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
    /**
     * 字段描述：序号
     */
    @JsonProperty
    @Excel(name = "序号")
    private Integer sort;

}
