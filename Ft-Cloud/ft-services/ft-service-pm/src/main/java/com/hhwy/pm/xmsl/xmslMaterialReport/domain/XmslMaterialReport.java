package com.hhwy.pm.xmsl.xmslMaterialReport.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
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
    private Long id;
    /**
     * 字段描述：材料编码,t_material_info
     */
    @JsonProperty
    @FtExcel(name = "物资编码")
    private String code;
    /**
     * 字段描述：材料名称
     */
    @JsonProperty
    @FtExcel(name = "物资名称")
    private String name;
    /**
     * 字段描述：规格型号
     */
    @JsonProperty
    @FtExcel(name = "规格型号")
    private String spec;
    /**
     * 字段描述：材料类型，字典:xmsl_mater_type
     */
    @JsonProperty
    private String type;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @FtExcel(name = "单位")
    private String unit;
    /**
     * 字段描述：设计量
     */
    @JsonProperty
    private BigDecimal quanlity;
    /**
     * 字段描述：理论用量，设计量*（1+损耗率）
     */
    @JsonProperty
    @FtExcel(name = "总需用量")
    private BigDecimal theoreticalDosage;
    /**
     * 字段描述：理论需用量，设计量*（1+损耗率）
     */
    @JsonProperty
    private BigDecimal theoreticalDosage1;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    private String remark;
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
     * 字段描述：数据修改系统时
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date updateTime;
    /**
     * 字段描述：用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long userId;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long deptId;
}
