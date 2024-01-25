package com.hhwy.pm.xmsl.wbs.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.tree.TreeNodeBase;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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
@ExcelIgnoreUnannotated
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
    @NotBlank(message = "Id不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String id;
    /**
     * 字段描述：主表id,xmsl_wbs_main.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long mainId;
    /**
     * 字段描述：编号，单位工程按整百递增，分部分项子分项按三位流水号递增
     */
    @JsonProperty
    @FtExcel(name = "编号")
    @NotBlank(message = "编号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @ExcelProperty("编号")
    protected String code;
    /**
     * 字段描述：父级ID,最顶级为0
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private String parentId;
    /**
     * 字段描述：是否包含子级，0:否,1:是
     */
    @JsonProperty
    private Integer haveChildren;
    /**
     * 字段描述：祖级ID
     */
    @JsonProperty
//    @NotBlank(message = "祖级ID不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String ancestors;
    /**
     * 字段描述：祖级名称
     */
    @JsonProperty
//    @NotBlank(message = "祖级名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String ancestorsName;
    /**
     * 字段描述：项目部位
     */
    @JsonProperty
    @FtExcel(name = "项目部位（桩号）")
    @NotBlank(message = "项目部位不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @ExcelProperty("项目部位（桩号）")
    protected String partCode;

    /**
     * 字段描述：标准wbs编码
     */
    @JsonProperty
    @FtExcel(name = "关联标准WBS")
    protected String standardCode;

    /**
     * 字段描述：名称 实际是 桩号-wbs名称
     */
    @JsonProperty
    @FtExcel(name = "标准WBS名称")
    @NotBlank(message = "标准WBS名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @ExcelProperty("标准WBS名称")
    protected String name;
    /**
     * 字段描述：节点类型,字典:xmsl_wbs_type
     */
    @JsonProperty
    @FtExcel(name = "节点类型",dictType = "xmsl_wbs_type")
    @NotBlank(message = "节点类型不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    protected String nodeType;

    /**
     * 字段描述：状态,0:停用,1:启用
     */
    @JsonProperty
    @FtExcel(name = "启用/禁用",combo = {"启用","停用"},readConverterExp = "0=停用,1=启用")
    protected Integer status;
    
    /**
     * 字段描述：清单编码
     */
    @JsonProperty
    @FtExcel(name = "关联清单编号")
    protected String listCode;
    /**
     * 字段描述：标准wbsId，预留
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long standardId;

    /**
     * 字段描述：标准wbs名称
     */
    @JsonProperty
    private String standardName;
   
    /**
     * 字段描述：单位
     */
    @JsonProperty
    private String unit;
    /**
     * 字段描述：层级
     */
    @JsonProperty
    private Integer level;
    
    /**
     * 字段描述：设计量
     */
    @JsonProperty
    private BigDecimal designQuanlity;
    /**
     * 字段描述：复核量
     */
    @JsonProperty
    private BigDecimal checkQuanlity;
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
     * 字段描述：预留字段1  生效状态，0：未生效,1：已生效
     */
    @JsonProperty
    private String ptVar1;
    /**
     * 字段描述：版本修改状态，1:原数据修改,2:新增数据，3：禁用（仅生效数据）
     */
    @JsonProperty
    private String ptVar2;
    /**
     * 字段描述：wbs名称
     */
    @JsonProperty
    private String ptVar3;
    /**
     * 字段描述：p6对应的wbsID,推送到p6时需要用到
     */
    @JsonProperty
    private String ptVar4;
    /**
     * 字段描述：预留字段5  父级编号
     */
    @JsonProperty
    private String ptVar5;

    //序号
    private Integer sort;

    //不存在于数据库
    @JsonProperty
    private String listIds;   //清单ID
    private String wbsId;

    //当前层级编号
    @NotBlank(message = "编号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    private String selfCode;
    //旧当前层级编码  不存在于数据库
    private String oldSelfCode;
    //父级编号
    private String parentCode;

    @Override
    public String toString() {
        return partCode + StringUtils.trim(name) + nodeType + listCode;
    }
}
