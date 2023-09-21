package com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mls
 * @date 2023-08-17 16:19:06
 * @remark qqch_tax_global
 */
@Data
@ToString
public class QqchTaxGlobal extends CompileEntity<QqchTaxGlobal> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
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
     * 字段描述：年份
     */
    @JsonProperty
    private Integer year;

    @FtExcel(name = "序号",serialNumFlag = true)
    private String serNum;
    /**
     * 字段描述：资金项
     */
    @JsonProperty
    @FtExcel(name = "资金项")
    private String itemName;
    /**
     * 字段描述：预算金额-当地币
     */
    @JsonProperty
    @FtExcel(name = "预算金额-当地币")
    private BigDecimal budgetLocalAmt;
    /**
     * 字段描述：预算金额-美元
     */
    @JsonProperty
    @FtExcel(name = "预算金额-美元")
    private BigDecimal budgetUsdAmt;
    /**
     * 字段描述：项目直接收支-当地币
     */
    @JsonProperty
    @FtExcel(name = "项目直接收支-当地币")
    private BigDecimal prjLocalAmt;
    /**
     * 字段描述：项目直接收支-当地币折美元汇率
     */
    @JsonProperty
    @FtExcel(name = "项目直接收支-当地币折美元汇率")
    private BigDecimal prjLocalRate;
    /**
     * 字段描述：区域总部/国家办事处/总项目部代收支-当地币
     */
    @JsonProperty
    @FtExcel(name = "区域总部/国家办事处/总项目部代收支-当地币")
    private BigDecimal regionLocalAmt;
    /**
     * 字段描述：区域总部/国家办事处/总项目部代收支-当地币折美元汇率
     */
    @JsonProperty
    @FtExcel(name = "区域总部/国家办事处/总项目部代收支-当地币折美元汇率")
    private BigDecimal regionLocalRate;
    /**
     * 字段描述：海外事业部代收支-美元
     */
    @JsonProperty
    @FtExcel(name = "海外事业部代收支-美元")
    private BigDecimal overseasUsdAmt;
    /**
     * 字段描述：海外事业部代收支-人民币
     */
    @JsonProperty
    @FtExcel(name = "海外事业部代收支-人民币")
    private BigDecimal overseasCnyAmt;
    /**
     * 字段描述：海外事业部代收支-人民币折美元汇率
     */
    @JsonProperty
    @FtExcel(name = "海外事业部代收支-人民币折美元汇率")
    private BigDecimal overseasCnyRate;
    /**
     * 字段描述：合计-当地币种
     */
    @JsonProperty
    @FtExcel(name = "合计-当地币种")
    private BigDecimal sumLocalAmt;
    /**
     * 字段描述：合计-当地币折美元汇率
     */
    @JsonProperty
    @FtExcel(name = "合计-当地币折美元汇率")
    private BigDecimal sumLocalRate;
    /**
     * 字段描述：合计-美元
     */
    @JsonProperty
    @FtExcel(name = "合计-美元")
    private BigDecimal sumUsdAmt;
    /**
     * 字段描述：合计-人民币折美元汇率
     */
    @JsonProperty
    @FtExcel(name = "合计-人民币折美元汇率")
    private BigDecimal sumCnyRate;
    /**
     * 字段描述：附件
     */
    @JsonProperty
    private String fileGroupId;
    /**
     * 字段描述：备注
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
     * 字段描述：版本号
     */
    @JsonProperty
    private BigDecimal version;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    private String valid;
    /**
     * 字段描述：树id
     */
    @JsonProperty
    private String treeId;
    /**
     * 字段描述：树父级id
     */
    @JsonProperty
    private String treePid;
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
