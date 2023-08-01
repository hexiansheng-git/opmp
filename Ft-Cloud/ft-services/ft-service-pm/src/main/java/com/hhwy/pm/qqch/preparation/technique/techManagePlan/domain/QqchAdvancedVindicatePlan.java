package com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author han
 * @date 2023-07-27 15:51:15
 * @remark 高新维护计划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchAdvancedVindicatePlan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：单位名称
     */
    @JsonProperty
    private String unitName;
    /**
     * 字段描述：研发课题名称
     */
    @JsonProperty
    private String researchTopicName;
    /**
     * 字段描述：课题编号
     */
    @JsonProperty
    private String topicCode;
    /**
     * 字段描述：课题类别
     */
    @JsonProperty
    private String topicType;
    /**
     * 字段描述：起始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date startDate;
    /**
     * 字段描述：完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date endDate;
    /**
     * 字段描述：合计
     */
    @JsonProperty
    private BigDecimal total;
    /**
     * 字段描述：拟定研发人员
     */
    @JsonProperty
    private String simulateResearchStaff;
    /**
     * 字段描述：计划取得研发成果（外部证据）（专利、工法、查新、论文等各几项）
     */
    @JsonProperty
    private String extrinsicEvidence;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    private String remark;
    /**
     * 字段描述：排序
     */
    @JsonProperty
    private Integer sort;
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

    /* 年份预算键值对集合 */
    private Map<String,BigDecimal> vintageBudgetMap;
}
