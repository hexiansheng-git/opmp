package com.hhwy.pm.qqch.preparation.measureexp.range.domain;


import java.util.Date;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;
import lombok.ToString;

/**
 * @author mls
 * @date 2023-07-25 18:01:30
 * @remark qqch_measure_exp_person
 */

@Data
@ToString
public class QqchMeasureExpPerson extends CompileEntity<QqchMeasureExpPerson> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @FtExcel(name = "主键id")
    private Long id;
    /**
     * 字段描述：数据类型:1-测量管理计划 2-实验管理计划
     */
    @JsonProperty
    @FtExcel(name = "数据类型:1-测量管理计划 2-实验管理计划")
    private String dataType;
    /**
     * 字段描述：岗位编码
     */
    @JsonProperty
    @FtExcel(name = "岗位编码")
    private String positionCode;
    /**
     * 字段描述：岗位名称
     */
    @JsonProperty
    @FtExcel(name = "岗位名称")
    private String positionName;
    /**
     * 字段描述：工作分工
     */
    @JsonProperty
    @FtExcel(name = "工作分工")
    private String workDesc;
    /**
     * 字段描述：配置测量工（中方）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @FtExcel(name = "配置测量工（中方）")
    private BigDecimal cnNum;
    /**
     * 字段描述：配置测量工（属地化）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @FtExcel(name = "配置测量工（属地化）")
    private BigDecimal localNum;
    /**
     * 字段描述：来源
     */
    @JsonProperty
    @FtExcel(name = "来源")
    private String source;
    /**
     * 字段描述：计划进场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划进场日期", dateFormat = "yyyy-MM-dd")
    private Date planInDate;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @FtExcel(name = "版本")
    private BigDecimal version;
    /**
     * 字段描述：是否有效 1-有效 0-失效
     */
    @JsonProperty
    @FtExcel(name = "是否有效 1-有效 0-失效")
    private String valid;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @FtExcel(name = "部门id")
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @FtExcel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    @FtExcel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @FtExcel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    @FtExcel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @FtExcel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    @FtExcel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @FtExcel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @FtExcel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    @FtExcel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    @FtExcel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @FtExcel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    @FtExcel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    @FtExcel(name = "预留字段5")
    private String ptVar5;
}
