package com.hhwy.pm.qqch.wzch.revolverent.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.qqch.wzch.revolverent.domain.WzchRevolveRentDetail;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 周转材租赁策划物资详情对象 wzch_revolve_rent_detail
 *
 * @author mls
 * @date 2022-11-17
 */
@Data
@ToString
public class WzchRevolveRentDetailDTO extends WzchRevolveRentDetail {
    private static final long serialVersionUID = 1L;

    public WzchRevolveRentDetailDTO() {
    }
    public WzchRevolveRentDetailDTO(BigDecimal version) {
        this.version = version;
    }

    /**
     * 物资编码
     */
    @Excel(name = "物资编码")
    private String materialCode;

    /**
     * 物资名称
     */
    @Excel(name = "物资名称")
    private String materialName;
    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String materialSpec;

    /**
     * 技术参数
     */
    @Excel(name = "技术参数")
    private String materialTechParam;

    /**
     * 品牌
     */
    @Excel(name = "品牌")
    private String brand;

    /**
     * 执行标准
     */
    @Excel(name = "执行标准")
    private String materialStandard;
    private String materialStandardName;


    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;


    /**
     * 总需用量
     */
    @Excel(name = "总需用量")
    private BigDecimal totalDemandAmount;

    /**
     * 租赁需用量
     */
    @Excel(name = "租赁需用量")
    private BigDecimal rentNum;


    /**
     * 物资分类
     */
    @Excel(name = "物资分类")
    private String categoryName;
    private String categoryNameName;

    /**
     * 币种
     */
    @Excel(name = "币种")
    private String currency;
    private String currencyName;

    /**
     * 单价
     */
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /**
     * 计划进场日期
     */
    @Excel(name = "计划进场日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planEnterDate;

    /**
     * 租赁天数
     */
    @Excel(name = "租赁天数")

    @JsonSerialize(using = ToStringSerializer.class)
    private Long rentDays;

    /**
     * 计划退场日期
     */
    @Excel(name = "计划退场日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planExitDate;

    /**
     * 其他费用
     */
    @Excel(name = "其他费用")
    private BigDecimal otherCost;

    /**
     * 总价
     */
    @Excel(name = "总价")
    private BigDecimal totalPrice;

    private BigDecimal version;


}
