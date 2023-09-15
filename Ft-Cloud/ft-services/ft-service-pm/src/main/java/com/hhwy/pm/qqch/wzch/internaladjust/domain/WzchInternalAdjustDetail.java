package com.hhwy.pm.qqch.wzch.internaladjust.domain;

import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;
import lombok.ToString;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 内部调剂材料策划物资详情对象 wzch_internal_adjust_detail
 *
 * @author MLS
 * @date 2022-11-17
 */
@Data
@ToString
public class WzchInternalAdjustDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;
    private String materialCodeAndStandard;
    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 内部调剂材料策划id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long internalAdjustId;

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
     * 物资类型
     */
    @Excel(name = "类型")
    private String categoryName;
    private String categoryNameName;

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
     * 内部调剂需用量
     */
    @Excel(name = "内部调剂需用量")
    private BigDecimal innerAdjustNum;
    private BigDecimal inventory;

    /**
     * 计划需用日期
     */
    @Excel(name = "计划需用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planReqDate;

    /**
     * 可调出数量
     */
    @Excel(name = "可调出数量")
    private BigDecimal adjustableNum;

    /**
     * 调出项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long adjustProjectId;

    /**
     * 调出项目名称
     */
    @Excel(name = "调出项目")
    private String adjustProjectName;

    /**
     * 可调出日期
     */
    @Excel(name = "可调出日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date adjustableDate;

    /**
     * 调拨单价
     */
    @Excel(name = "调拨单价")
    private BigDecimal adjustUnitPrice;

    /**
     * 币种
     */
    @Excel(name = "币种")
    private String currency;
    private String currencyName;

    /**
     * 总价
     */
    @Excel(name = "总价")
    private BigDecimal totalPrice;

    /**
     * 附件组id
     */
    private String fileGroupId;

    /**
     * 项目id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 部门id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    /**
     * 数据创建者id
     */
    private String createUser;

    /**
     * 数据创建者名称
     */
    private String createUserName;

    /**
     * 数据修改者id
     */
    private String updateUser;

    /**
     * 数据修改者名称
     */
    private String updateUserName;

    /**
     * 数据删除者
     */
    private String delUser;

    /**
     * 数据删除系统时间
     */
    private Date delTime;

    /**
     * 删除标识：0有效1无效
     */
    private String delFlag;
    private String valid;

    /**
     * 预留字段1
     */
    private String ptVar1;

    /**
     * 预留字段2
     */
    private String ptVar2;

    /**
     * 预留字段3
     */
    private String ptVar3;
    
    private BigDecimal version;


}
