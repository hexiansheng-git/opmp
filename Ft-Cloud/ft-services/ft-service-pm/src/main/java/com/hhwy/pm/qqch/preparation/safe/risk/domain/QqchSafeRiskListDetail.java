package com.hhwy.pm.qqch.preparation.safe.risk.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-11 13:41:38
 * @remark qqch_safe_risk_list_detail
 */
@Data
public class QqchSafeRiskListDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：父级id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父级id")
    private Long pid;
    /**
     * 字段描述：qqch_safe_risk_list主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "qqch_safe_risk_list主表id")
    private Long infoId;
    /**
     * 字段描述：排序
     */
    private Integer sort;
    /**
     * 字段描述：作业类型
     */
    @JsonProperty
    @Excel(name = "作业类型")
    private String workType;
    /**
     * 字段描述：作业单元
     */
    @JsonProperty
    @Excel(name = "作业单元")
    private String workUnit;
    /**
     * 字段描述：风险事件
     */
    @JsonProperty
    @Excel(name = "风险事件")
    private String dangerThing;
    /**
     * 字段描述：可能后果
     */
    @JsonProperty
    @Excel(name = "可能后果")
    private String possibleResult;
    /**
     * 字段描述：可能性等级(the_level)
     */
    @JsonProperty
    @Excel(name = "可能性等级(the_level)")
    private String possibleLevel;
    /**
     * 字段描述：严重程度等级(the_level)
     */
    @JsonProperty
    @Excel(name = "严重程度等级(the_level)")
    private String severityLevel;
    /**
     * 字段描述：风险等级（risk_level）
     */
    @JsonProperty
    @Excel(name = "风险等级（risk_level）")
    private String riskLevel;
    /**
     * 字段描述：风险控制措施
     */
    @JsonProperty
    @Excel(name = "风险控制措施")
    private String riskControWay;
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
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
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

    private List<QqchSafeRiskListDetail> children;
}
