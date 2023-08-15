package com.hhwy.pm.xmsl.xmslMaterialReport.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 主材报表
 * @author wk
 * @date 2023-08-15 17:39:46
 * @remark xmsl_material_report
 */
@Data
public class XmslMaterialReport extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "Id")
    private Long id;
    /**
     * 字段描述：材料编码,t_material_info
     */
    @JsonProperty
    @Excel(name = "材料编码,t_material_info")
    private String code;
    /**
     * 字段描述：材料名称
     */
    @JsonProperty
    @Excel(name = "材料名称")
    private String name;
    /**
     * 字段描述：规格型号
     */
    @JsonProperty
    @Excel(name = "规格型号")
    private String spec;
    /**
     * 字段描述：材料类型，字典:xmsl_mater_type
     */
    @JsonProperty
    @Excel(name = "材料类型，字典:xmsl_mater_type")
    private String type;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：设计量
     */
    @JsonProperty
    @Excel(name = "设计量")
    private BigDecimal quanlity;
    /**
     * 字段描述：理论用量，设计量*（1+损耗率）
     */
    @JsonProperty
    @Excel(name = "理论用量，设计量*（1+损耗率）")
    private BigDecimal theoreticalDosage;
    /**
     * 字段描述：理论需用量，设计量*（1+损耗率）
     */
    @JsonProperty
    @Excel(name = "理论需用量，设计量*（1+损耗率）")
    private BigDecimal theoreticalDosage1;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
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
     * 字段描述：数据修改系统时
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据修改系统时", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
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
}
