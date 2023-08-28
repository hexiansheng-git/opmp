package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跨国别设备调拨详情-费用估算对象 sbch_equipment_allot_transnational_cost
 * 
 * @author hwj
 * @date 2022-12-22
 */
@Data
public class SbchEquipmentAllotTransnationalCost extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 跨国别方案主表 sbch_equipment_allot_transnational_details */
    @Excel(name = "跨国别方案主表 sbch_equipment_allot_transnational_details")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long mainId;

    /** 调出/掉入方式  0调出 1掉入 */
    @Excel(name = "调出/掉入方式  0调出 1掉入")
    private String allotType;

    /** 国家id */
    @Excel(name = "国家id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long countryId;

    /** 国家 */
    @Excel(name = "国家")
    private String country;

    /** 币种 必须是美元 */
    @Excel(name = "币种 必须是美元")
    private String currency;

    /** 海运费用 */
    @Excel(name = "海运费用")
    private BigDecimal shippingCost;

    /** 海运保险费用 */
    @Excel(name = "海运保险费用")
    private BigDecimal shippingInsuranceCost;

    /** 港费用 */
    @Excel(name = "港费用")
    private BigDecimal sundryCost;

    /** 陆运费用 */
    @Excel(name = "陆运费用")
    private BigDecimal landCost;

    /** 陆运保险费用 */
    @Excel(name = "陆运保险费用")
    private BigDecimal landInsuranceCost;

    /** 关税 */
    @Excel(name = "关税")
    private BigDecimal tariffsCost;

    /** 报关费 */
    @Excel(name = "报关费")
    private BigDecimal reportCost;

    /** 其他税费 */
    @Excel(name = "其他税费")
    private BigDecimal otherCost;

    /** 清关费 */
    @Excel(name = "清关费")
    private BigDecimal clearanceCost;

    /** 费用合计 */
    @Excel(name = "费用合计")
    private BigDecimal totalCost;

    /** 数据创建者id */
    @Excel(name = "数据创建者id")
    private String createUser;

    /** 数据创建者名称 */
    @Excel(name = "数据创建者名称")
    private String createUserName;

    /** 数据修改者id */
    @Excel(name = "数据修改者id")
    private String updateUser;

    /** 数据修改者名称 */
    @Excel(name = "数据修改者名称")
    private String updateUserName;

    /** 数据删除者 */
    @Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String ptVar1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String ptVar2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private String ptVar3;

    /** 预留字段4 */
    @Excel(name = "预留字段4")
    private String ptVar4;

    /** 部门id */
    @Excel(name = "部门id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 项目id */
    @Excel(name = "项目id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    /** 所属区域id */
    @Excel(name = "所属区域id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 所属区域 */
    @Excel(name = "所属区域")
    private String region;
}
