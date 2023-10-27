package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain;

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
 * @date 2023-08-14 14:00:39
 * @remark qqch_safe_envir_risk_list_detail
 */
@Data
public class QqchSafeEnvirRiskListDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;

    private Long pid;
    /**
     * 字段描述：qqch_safe_envir_risk_list主表
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "qqch_safe_envir_risk_list主表")
    private Long infoId;
    /**
     * 字段描述：工序
     */
    @JsonProperty
    @Excel(name = "工序")
    private String proProcess;
    /**
     * 字段描述：作业
     */
    @JsonProperty
    @Excel(name = "作业")
    private String workContent;
    /**
     * 字段描述：环境因素
     */
    @JsonProperty
    @Excel(name = "环境因素")
    private String envriReason;
    /**
     * 字段描述：频率
     */
    @JsonProperty
    @Excel(name = "频率")
    private String frequency;
    /**
     * 字段描述：环境影响
     */
    @JsonProperty
    @Excel(name = "环境影响")
    private String envriImpact;
    /**
     * 字段描述：是否重大环境因素
     */
    @JsonProperty
    @Excel(name = "是否重大环境因素")
    private String isMostReason;
    /**
     * 字段描述：措施项
     */
    @JsonProperty
    @Excel(name = "措施项")
    private String measure;
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

    private List<QqchSafeEnvirRiskListDetail> children;
}
