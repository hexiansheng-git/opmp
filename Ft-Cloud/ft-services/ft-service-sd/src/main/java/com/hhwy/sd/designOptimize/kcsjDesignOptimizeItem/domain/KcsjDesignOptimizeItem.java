package com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.domain;

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
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;

/**
 * @author cjh
 * @date 2024-02-04 13:31:49
 * @remark kcsj_design_optimize_item
 */
@Data
public class KcsjDesignOptimizeItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@FtExcel(name = "主键")
    private Long id;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@FtExcel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    //@FtExcel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@FtExcel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    //@FtExcel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@FtExcel(name = "部门id")
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    //@FtExcel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    //@FtExcel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    //@FtExcel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    //@FtExcel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    //@FtExcel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    //@FtExcel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    //@FtExcel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    //@FtExcel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    //@FtExcel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    //@FtExcel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    //@FtExcel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    //@FtExcel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    //@FtExcel(name = "预留字段5")
    private String ptVar5;
    /**
     * 字段描述：设计优化主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@FtExcel(name = "设计优化主表id")
    private Long optimizeId;
    /**
     * 字段描述：主材/清单编码
     */
    @JsonProperty
    @FtExcel(name = "主材/清单编码")
    private String itemCode;
    /**
     * 字段描述：主材/清单名称
     */
    @JsonProperty
    @FtExcel(name = "主材/清单名称")
    private String itemName;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @FtExcel(name = "单位")
    private String unit;
    /**
     * 字段描述：优化前工程量
     */
    @JsonProperty
    @FtExcel(name = "优化前工程量")
    private BigDecimal beforeOptimizeQty;
    /**
     * 字段描述：优化后工程量
     */
    @JsonProperty
    @FtExcel(name = "优化后工程量")
    private BigDecimal afterOptimizeQty;
    /**
     * 字段描述：预估单价
     */
    @JsonProperty
    @FtExcel(name = "预估单价")
    private BigDecimal estimatePrice;
    /**
     * 字段描述：预估优化金额
     */
    @JsonProperty
    @FtExcel(name = "预估优化金额")
    private BigDecimal estimateAmt;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @FtExcel(name = "备注")
    private String remark;

}
