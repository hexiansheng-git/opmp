package com.hhwy.pm.qqch.wzch.internaladjust.domain;

import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;
import lombok.ToString;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 可调拨材料对象 wzch_allot_material
 *
 * @author mls
 * @date 2022-11-17
 */
@Data
@ToString
public class WzchAllotMaterial extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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
     * 执行标准
     */
    @Excel(name = "执行标准")
    private String materialStandard;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;


    /**
     * 库存数量
     */
    @Excel(name = "库存数量")
    private BigDecimal inventory;

    /**
     * 可调出日期
     */
    @Excel(name = "可调出日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planEnterDate;

    /**
     * 可调拨范围
     */
    private String range;

    @JsonSerialize(using = ToStringSerializer.class)
    private String countryCode;
    private String countryCodes;
    private List<String> countryCodeList;
    /**
     * 项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String projectIds;

    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    private String ancestors;

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
     * 数据修改者id
     */
    private String updateUser;

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

}
