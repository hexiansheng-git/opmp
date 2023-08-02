package com.hhwy.pm.qqch.preparation.finance.policy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:49
 * @remark qqch_local_tariff_policy
 */
@Data
public class QqchLocalTariffPolicy extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：名称
     */
    @JsonProperty
    @FtExcel(name = "名称")
    private String name;
    /**
     * 字段描述：出口退税率%
     */
    @JsonProperty
    @FtExcel(name = "出口退税率%")
    private BigDecimal exportRebateRate;
    /**
     * 字段描述：进口税率ACFTA%(东盟-中国自由贸易区)
     */
    @JsonProperty
    @FtExcel(name = "进口税率ACFTA%(东盟-中国自由贸易区)")
    private BigDecimal importTaxRate;
    /**
     * 字段描述：增值税税率%
     */
    @JsonProperty
    @FtExcel(name = "增值税税率%")
    private BigDecimal vatRate;
    /**
     * 字段描述：消费税税率%
     */
    @JsonProperty
    @FtExcel(name = "消费税税率%")
    private BigDecimal consumptionTaxRate;
    /**
     * 字段描述：是否选择来的数据 1-是 0-否
     */
    @JsonProperty
    private String isSelect;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    private BigDecimal version;
    /**
     * 字段描述：是否有效 1-有效 0-失效
     */
    @JsonProperty
    private String valid;
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
     * 字段描述：预留字段1
     */
    @JsonProperty
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    private String ptVar5;
}
