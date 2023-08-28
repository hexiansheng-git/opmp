package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * 跨国别设备调拨详情-费用估算对象 sbch_equipment_allot_transnational_cost
 * 
 * @author hwj
 * @date 2022-12-22
 */
@Data
public class ImportSbchEquipmentAllotTransnationalCost extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "序号")
    private String xh;

    /** 调出/掉入方式  0调出 1掉入 */
    @Excel(name = "调出/调入", readConverterExp = "0=调出,1=调入")
    private String allotType;

    /** 国家id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long countryId;

    /** 国家 */
    @Excel(name = "国家")
    private String country;

    /** 币种 必须是美元 */
    @Excel(name = "币种")
    private String currency;

    /** 海运费用 */
    @Excel(name = "海运费")
//    @Pattern(regexp = "^[0-9]+\\.{0,1}[0-9]{0,2}$",message = "'海运费用'只能输入数字")
    private String shippingCost;
//    private BigDecimal shippingCost;

    /** 海运保险费用 */
    @Excel(name = "海运保险费")
//    @Pattern(regexp = "^[0-9]+\\.{0,1}[0-9]{0,2}$",message = "'海运保险费用'只能输入数字")
    private String shippingInsuranceCost;
//    private BigDecimal shippingInsuranceCost;

    /** 港费用 */
    @Excel(name = "港杂费")
//    @Pattern(regexp = "^[0-9]+\\.{0,1}[0-9]{0,2}$",message = "'港费用'只能输入数字")
    private String sundryCost;
//    private BigDecimal sundryCost;

    /** 陆运费用 */
    @Excel(name = "陆运费")
//    @Pattern(regexp = "^[0-9]+\\.{0,1}[0-9]{0,2}$",message = "'陆运费用'只能输入数字")
    private String landCost;
//    private BigDecimal landCost;

    /** 陆运保险费用 */
    @Excel(name = "陆运保险费")
//    @Pattern(regexp = "^[0-9]+\\.{0,1}[0-9]{0,2}$",message = "'陆运保险费用'只能输入数字")
    private String landInsuranceCost;
//    private BigDecimal landInsuranceCost;

    /** 关税 */
    @Excel(name = "关税")
    @Pattern(regexp = "^[0-9]+\\.{0,1}[0-9]{0,2}$",message = "'关税'只能输入数字")
    private String tariffsCost;
//    private BigDecimal tariffsCost;

    /** 报关费 */
    @Excel(name = "报关费")
//    @Pattern(regexp = "^[0-9]+\\.{0,1}[0-9]{0,2}$",message = "'报关费'只能输入数字")
    private String reportCost;
//    private BigDecimal reportCost;

    /** 其他税费 */
    @Excel(name = "其他税费")
//    @Pattern(regexp = "^[0-9]+\\.{0,1}[0-9]{0,2}$",message = "'其他税费'只能输入数字")
    private String otherCost;
//    private BigDecimal otherCost;

    /** 清关费 */
    @Excel(name = "清关费")
//    @Pattern(regexp = "^[0-9]+\\.{0,1}[0-9]{0,2}$",message = "'清关费'只能输入数字")
    private String clearanceCost;
//    private BigDecimal clearanceCost;

    /** 费用合计 */
    private BigDecimal totalCost;
}
