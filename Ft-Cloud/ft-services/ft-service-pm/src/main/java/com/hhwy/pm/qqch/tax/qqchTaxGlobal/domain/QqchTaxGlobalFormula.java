package com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.common.service.CommonServiceUtil;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mls
 * @date 2023-08-17 16:19:10
 * @remark qqch_tax_global_formula
 */
@Data
@ToString
public class QqchTaxGlobalFormula extends CompileEntity<QqchTaxGlobalFormula> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：年份
     */
    @JsonProperty
    @Excel(name = "年份")
    private Integer year;
    /**
     * 字段描述：工程量计算
     */
    @JsonProperty
    @Excel(name = "工程量计算")
    private BigDecimal quantities;
    /**
     * 字段描述：调价收入
     */
    @JsonProperty
    @Excel(name = "调价收入")
    private BigDecimal adjustInAmt;
    /**
     * 字段描述：利息收入
     */
    @JsonProperty
    @Excel(name = "利息收入")
    private BigDecimal interestInAmt;
    /**
     * 字段描述：预付款比例
     */
    @JsonProperty
    @Excel(name = "预付款比例")
    private BigDecimal prePayRate;
    /**
     * 字段描述：预付款
     */
    @JsonProperty
    @Excel(name = "预付款")
    private BigDecimal prePayAmt;
    /**
     * 字段描述：质保金扣除比例
     */
    @JsonProperty
    @Excel(name = "质保金扣除比例")
    private BigDecimal guaDeductRate;
    /**
     * 字段描述：质保金
     */
    @JsonProperty
    @Excel(name = "质保金")
    private BigDecimal guaAmt;
    /**
     * 字段描述：单独计量的利息收入
     */
    @JsonProperty
    @Excel(name = "单独计量的利息收入")
    private BigDecimal aloneInterestInAmt;
    /**
     * 字段描述：索赔收入
     */
    @JsonProperty
    @Excel(name = "索赔收入")
    private BigDecimal claimInAmt;
    /**
     * 字段描述：节点回收比例
     */
    @JsonProperty
    @Excel(name = "节点回收比例")
    private BigDecimal nodeRecoveryRate;
    /**
     * 字段描述：不含税合同金额
     */
    @JsonProperty
    @Excel(name = "不含税合同金额")
    private BigDecimal excContAmt;
    /**
     * 字段描述：附件
     */
    @JsonProperty
    @Excel(name = "附件")
    private String fileGroupId;
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
    private BigDecimal cycle;
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
     * 字段描述：版本号
     */
    @JsonProperty
    @Excel(name = "版本号")
    private BigDecimal version;
    /**
     * 字段描述：预留字段2
     */ 
    @JsonProperty
    @Excel(name = "币种")
    private String currency;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @Excel(name = "汇率")
    private BigDecimal rate;
    /**
     * 字段描述：美元对人民币汇率
     */
    @JsonProperty
    @Excel(name = "美元对人民币汇率")
    private BigDecimal cnyRate;
    /**
     * 字段描述：项目当地币汇率
     */
    @JsonProperty
    @Excel(name = "项目当地币汇率")
    private BigDecimal localRate;
    /**
     * 字段描述：序号
     */
    @JsonProperty
    @Excel(name = "是否有效")
    private String valid;

    private BigDecimal recAmt;
    private BigDecimal usdRecAmt;
    private BigDecimal cnyRecAmt;
    private BigDecimal localRecAmt;

    private BigDecimal backAmt;
    private BigDecimal usdBackAmt;
    private BigDecimal cnyBackAmt;
    private BigDecimal localBackAmt;

    private BigDecimal payAmt;
    private BigDecimal usdPayAmt;
    private BigDecimal cnyPayAmt;
    private BigDecimal localPayAmt;


    public BigDecimal getRecAmt() {
        BigDecimal a = BigDecimalUtils.sum(quantities, adjustInAmt, interestInAmt);
        BigDecimal b = BigDecimalUtils.subtract(BigDecimalUtils.subtract(a, prePayAmt), guaAmt);
        return recAmt = BigDecimalUtils.sum(b, aloneInterestInAmt, claimInAmt);
    }

    public BigDecimal getBackAmt() {
        return backAmt = BigDecimalUtils.multiply(guaAmt, nodeRecoveryRate);
    }

    public BigDecimal getPayAmt() {
        return payAmt = BigDecimalUtils.multiply(this.excContAmt, this.prePayRate);
    }


    public BigDecimal getUsdRecAmt() {
        return usdRecAmt = CommonServiceUtil.getUsdAmt(this.getRecAmt(), this.rate);
    }

    public BigDecimal getUsdBackAmt() {
        return usdBackAmt = CommonServiceUtil.getUsdAmt(this.getBackAmt(), this.rate);
    }


    public BigDecimal getUsdPayAmt() {
        return usdPayAmt = CommonServiceUtil.getUsdAmt(this.getPayAmt(), this.rate);
    }

//    public BigDecimal getCnyRecAmt() {
//        return cnyRecAmt = BigDecimalUtils.multiply(this.getUsdRecAmt(), this.getCnyRate());
//    }
//
//
//    public BigDecimal getLocalRecAmt() {
//        return localRecAmt = BigDecimalUtils.multiply(this.getUsdRecAmt(), this.getLocalRate());
//    }
//
//
//

//
//    public BigDecimal getCnyBackAmt() {
//        return cnyBackAmt = BigDecimalUtils.multiply(this.getUsdBackAmt(), this.getCnyRate());
//    }
//
//    public BigDecimal getLocalBackAmt() {
//        return localBackAmt = BigDecimalUtils.multiply(this.getUsdBackAmt(), this.getLocalRate());
//    }
//
//    
//

//
//    public BigDecimal getCnyPayAmt() {
//        return cnyPayAmt = BigDecimalUtils.multiply(this.getUsdPayAmt(), this.getCnyRate());
//    }
//
//    public BigDecimal getLocalPayAmt() {
//        return localPayAmt = BigDecimalUtils.multiply(this.getUsdPayAmt(), this.getLocalRate());
//    }

}
