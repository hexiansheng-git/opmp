package com.hhwy.pm.xmsl.wbs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.common.core.web.domain.TreeEntity;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.tree.TreeNode;
import com.hhwy.utils.tree.TreeNodeBase;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class XmslWbs extends TreeNodeBase<XmslWbs,String> {
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

}
