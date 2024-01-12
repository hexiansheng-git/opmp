package com.hhwy.pm.qqch.preparation.quality.emp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.utils.JsonUtils;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mls
 * @date 2023-08-15 10:03:46
 * @remark qqch_emp_item
 */
@Data
@ToString
public class QqchEmpItem extends CompileEntity<QqchEmpItem> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：xmsl_wbs的id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "xmsl_wbs的id")
    private Long wbsId;
    /**
     * 字段描述：xmsl_wbs的编码
     */
    @JsonProperty
    @Excel(name = "xmsl_wbs的编码")
    private String wbsCode;
    /**
     * 字段描述：检查表编号
     */
    @JsonProperty
    @Excel(name = "检查表编号")
    private String checkCode;
    /**
     * 字段描述：检查表名称
     */
    @JsonProperty
    @Excel(name = "检查表名称")
    private String checkName;
    /**
     * 字段描述：检查项目
     */
    @JsonProperty
    @Excel(name = "检查项目")
    private String checkItem;
    /**
     * 字段描述：规定值或允许偏差
     */
    @JsonProperty
    @Excel(name = "规定值或允许偏差")
    private String stipulate;
    /**
     * 字段描述：检查方法及频率
     */
    @JsonProperty
    @Excel(name = "检查方法及频率")
    private String checkMethod;
    /**
     * 字段描述：附件
     */
    @JsonProperty
    @Excel(name = "附件")
    private String fileGroupId;
    /**
     * 字段描述：是否入库
     */
    @JsonProperty
    @Excel(name = "是否入库")
    private String storeFlag;
    private Boolean bstoreFlag;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
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
     * 字段描述：预留字段1，生效状态，0：未生效,1：已生效
     */
    @JsonProperty
    @Excel(name = "版本号")
    private BigDecimal version;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    @Excel(name = "预留字段2")
    private String valid;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @Excel(name = "预留字段3")
    private Long pid;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    @Excel(name = "祖级wbsId")
    private String ancestorsWbsId;
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

    //检查表Id
    private Long inspectionId;


    public static void main(String[] args) {
        JsonUtils.soutJsonStr(QqchEmpItem.class);
    }
}
