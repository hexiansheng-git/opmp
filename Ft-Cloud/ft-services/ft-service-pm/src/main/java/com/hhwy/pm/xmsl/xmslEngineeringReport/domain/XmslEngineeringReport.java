package com.hhwy.pm.xmsl.xmslEngineeringReport.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.tree.TreeNode;
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
public class XmslEngineeringReport extends TreeNode<XmslEngineeringReport> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：报表类型,1:wbs,2:清单
     */
    @JsonProperty
    @NotNull(message = "报表类型不能为空",groups = {ValidationGroups.Select.class})
    private Integer reportType;
    /**
     * 字段描述：主表id,xmsl_wbs_main.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long mainId;
    /**
     * 字段描述：父级ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long parentId;
    /**
     * 字段描述：WBS编号
     */
    @JsonProperty
    private Long wbsId;
    /**
     * 字段描述：WBS编号
     */
    @JsonProperty
    @FtExcel(name = "WBS编码")
    private String wbsCode;
    /**
     * 字段描述：WBS名称
     */
    @JsonProperty
    @FtExcel(name = "WBS名称")
    private String wbsName;
    /**
     * 字段描述：节点类型,字典:xmsl_wbs_type
     */
    @JsonProperty
    @FtExcel(name = "节点类型",dictType = "xmsl_wbs_type")
    private String nodeType;
    /**
     * 字段描述：是否包含子级，0:否,1:是
     */
    @JsonProperty
    private Integer haveChildren;
    /**
     * 字段描述：祖级ID
     */
    @JsonProperty
    private String ancestors;
    /**
     * 字段描述：祖级名称
     */
    @JsonProperty
    private String ancestorsName;
    /**
     * 字段描述：项目部位
     */
    @JsonProperty
    private String partCode;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    private String wbsUnit;
    /**
     * 字段描述：层级
     */
    @JsonProperty
    private Integer level;
    private Long listId;
    /**
     * 字段描述：清单编号
     */
    @JsonProperty
    @FtExcel(name = "清单编码")     
    private String listCode;
    /**
     * 字段描述：清单中文名称
     */
    @JsonProperty
    @FtExcel(name = "清单名称")
    private String listName;
    /**
     * 字段描述：单位编码
     */
    @JsonProperty
    private String unitCode;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @FtExcel(name = "清单单位")
    private String unit;
    /**
     * 字段描述：设计量
     */
    @JsonProperty
    @FtExcel(name = "合同总数量")
    private BigDecimal designQuanlity;
    /**
     * 字段描述：复核量
     */
    @JsonProperty
    @FtExcel(name = "本部位复核数量")
    private BigDecimal checkQuanlity;
    /**
     * 字段描述：形象进度单元,0:否,1:是
     */
    @JsonProperty
    @FtExcel(name = "主核算单元",readConverterExp="0=否,1:是")
    private String imageProgress;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
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
     * 字段描述：用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long userId;
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
     * 字段描述：预留字段2 wbs或清单编号
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
    /**
     * 字段描述：序号
     */
    @JsonProperty
    private Integer sort;

}
