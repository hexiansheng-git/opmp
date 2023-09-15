package com.hhwy.pm.qqch.wzch.revolverent.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.common.CommonBaseEntity;
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
public class WzchRevolveRentDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 周转材租赁策划id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long revolveRentId;

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

    /** 租赁需用量 */
    private BigDecimal rentNum;

    /** 币种 */
    private String currency;

    /** 单价 */
    private BigDecimal unitPrice;

    /** 计划进场日期 */
    private Date planEnterDate;

    /** 租赁天数 */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long rentDays;

    /** 计划退场日期 */
    private Date planExitDate;

    /** 其他费用 */
    private BigDecimal otherCost;

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

    /* 是否生效 0-未生效 1-已生效*/
    private String valid;

    /** 预留字段1 */
    private String ptVar1;

    /** 预留字段2 */
    private String ptVar2;

    /** 预留字段3 */
    private String ptVar3;

}
