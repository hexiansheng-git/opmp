package com.hhwy.pm.qqch.preparation.measureexp.equ.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.utils.excel.FtExcel;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-04 16:12:29
 * @remark 3.6.4测量仪器设备配置计划、3.7.4试验仪器设备配置计划
 */
@Data
public class QqchMeasureExpEquExperimentExportVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：仪器设备配置计划 1-测量仪器设备配置计划，2-试验仪器设备配置计划
     */
    @JsonProperty
    private String type;
    /**
     * 字段描述：设备id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long equId;
    /**
     * 字段描述：设备编码
     */
    @JsonProperty
    @FtExcel(name = "设备编码")
    private String equCode;
    /**
     * 字段描述：设备名称
     */
    @JsonProperty
    @FtExcel(name = "设备名称")
    private String equName;
    /**
     * 字段描述：类别
     */
    @JsonProperty
    @FtExcel(name = "类别")
    private String equType;
    /**
     * 字段描述：类别名称
     */
    @JsonProperty
    @FtExcel(name = "类别名称")
    private String equTypeName;
    /**
     * 字段描述：型号
     */
    @JsonProperty
    @FtExcel(name = "型号")
    private String spec;
    /**
     * 字段描述：主机功率（KW）
     */
    @JsonProperty
    @FtExcel(name = "主机功率（KW）")
    private BigDecimal hostPower;
    /**
     * 字段描述：所需数量
     */
    @JsonProperty
    @FtExcel(name = "所需数量")
    private BigDecimal reqNum;
    /**
     * 字段描述：要求进场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "要求进场日期", dateFormat = "yyyy-MM-dd")
    private Date reqInDate;
    /**
     * 字段描述：来源
     */
    @JsonProperty
    @FtExcel(name = "来源")
    private String source;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @FtExcel(name = "备注")
    private String remark;
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
