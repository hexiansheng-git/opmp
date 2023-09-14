package com.hhwy.pm.qqch.wzch.priorpurchase.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优先进场物资设备采购策划物资详情对象 wzch_prior_purchase_detail
 * 
 * @author mls
 * @date 2022-11-17
 */
@Data
@ToString
public class WzchPriorPurchaseDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 优先进场id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long priorPurchaseId;

    /** 物资编码 */
    private String materialCode;

    /** 技术参数 */
    private String materialTechParam;

    /** 品牌 */
    private String brand;

    /** 执行标准 */
    private String materialStandard;

    /** 总需用量 */
    private BigDecimal totalDemandAmount;

    /** 自采需用量 */
    private BigDecimal selfDemandAmount;

    /** 优先到场数量 */
    private BigDecimal priorApproachAmount;

    /** 最早需用日期 */
    private Date earliestReqTime;

    /** 要求到场日期 */
    private Date presentTime;

    /** 来源 */
    private String source;

    /** 币种 */
    private String currency;

    /** 单价 */
    private BigDecimal unitPrice;

    /** 平均采购价 */
    private BigDecimal avgPurchasePrice;

    /** 平均发运价 */
    private BigDecimal avgDespatchPrice;

    /** 平均清关价 */
    private BigDecimal avgCustClearPrice;

    /** 平均当地运输价 */
    private BigDecimal avgLocalTransportPrice;

    /** 平均落地价 */
    private BigDecimal avgLandingPrice;

    /** 总价 */
    private BigDecimal totalPrice;

    /** 附件组id */
    private String fileGroupId;

    /** 项目id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    /** 项目名称 */
    private String projectName;

    /** 部门id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 数据创建者id */
    private String createUser;

    /** 数据创建者名称 */
    private String createUserName;

    /** 数据修改者id */
    private String updateUser;

    /** 数据修改者名称 */
    private String updateUserName;

    /** 数据删除者 */
    private String delUser;

    /** 数据删除系统时间 */
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag;
    private String valid;

    /** 预留字段1 */
    private String ptVar1;

    /** 预留字段2 */
    private String ptVar2;

    /** 预留字段3 */
    private String ptVar3;

    private Date planPurchaseDateStart;
    private Date planPurchaseDateEnd;


}
