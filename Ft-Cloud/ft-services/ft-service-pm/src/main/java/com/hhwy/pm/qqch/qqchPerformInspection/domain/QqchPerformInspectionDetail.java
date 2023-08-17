package com.hhwy.pm.qqch.qqchPerformInspection.domain;

import com.hhwy.common.core.web.domain.BaseEntity;

import java.util.Date;
import java.math.BigDecimal;

import com.hhwy.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zqq
 * @date 2023-08-17 10:58:17
 * @remark qqch_perform_inspection_detail
 */
@Data
public class QqchPerformInspectionDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long pid;
    /**
     * 字段描述：qqch_perform_inspection
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "qqch_perform_inspection")
    private Long infoId;
    /**
     * 字段描述：策划项id
     */
    @JsonProperty
    @Excel(name = "策划项id")
    private String itemId;
    /**
     * 字段描述：策划项名称
     */
    @JsonProperty
    @Excel(name = "策划项名称")
    private String itemName;
    /**
     * 字段描述：显示顺序
     */
    @JsonProperty
    @Excel(name = "显示顺序")
    private Integer sort;
    /**
     * 字段描述：工作说明
     */
    @JsonProperty
    @Excel(name = "工作说明")
    private String workExplain;
    /**
     * 字段描述：编制责任人
     */
    @JsonProperty
    @Excel(name = "编制责任人")
    private String editor;
    /**
     * 字段描述：执行检查情况
     */
    @JsonProperty
    @Excel(name = "执行检查情况")
    private String performInspection;
    /**
     * 字段描述：检查人id
     */
    @JsonProperty
    @Excel(name = "检查人id")
    private String inspectionPerson;
    /**
     * 字段描述：检查人姓名
     */
    @JsonProperty
    @Excel(name = "检查人姓名")
    private String inspectionPersonName;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    @Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    @Excel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    @Excel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @Excel(name = "预留字段3")
    private String ptVar3;

    private List<QqchPerformInspectionDetail> childrenList;
}
