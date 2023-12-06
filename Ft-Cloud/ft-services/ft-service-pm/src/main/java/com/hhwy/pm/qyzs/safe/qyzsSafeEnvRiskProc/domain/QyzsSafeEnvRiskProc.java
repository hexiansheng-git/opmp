package com.hhwy.pm.qyzs.safe.qyzsSafeEnvRiskProc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author cjh
 * @date 2023-11-17 16:25:55
 * @remark qyzs_safe_env_risk_proc
 */
@Data
public class QyzsSafeEnvRiskProc extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "部门id")
    private Long deptId;
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
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    @Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    @Excel(name = "预留字段5")
    private String ptVar5;
    /**
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
    @Excel(name = "流程状态（5已完成）")
    private String taskStatus;
    /**
     * 字段描述：父级ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父级ID")
    private Long pid;
    /**
     * 字段描述：是否叶子
     */
    @JsonProperty
    @Excel(name = "是否叶子")
    private String leaf;
    /**
     * 字段描述：顺序号
     */
    @JsonProperty
    @Excel(name = "顺序号")
    private Integer sort;
    /**
     * 字段描述：工序
     */
    @JsonProperty
    @Excel(name = "工序")
    private String procName;
    /**
     * 字段描述：序号
     */
    @JsonProperty
    @Excel(name = "序号")
    private String num;
    /**
     * 字段描述：作业
     */
    @JsonProperty
    @Excel(name = "作业")
    private String workName;
    /**
     * 字段描述：环境因素
     */
    @JsonProperty
    @Excel(name = "环境因素")
    private String envFactor;
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
    private String envEffect;
    /**
     * 字段描述：法规符合
     */
    @JsonProperty
    @Excel(name = "法规符合")
    private String regulatoryCompliance;
    /**
     * 字段描述：发生频率
     */
    @JsonProperty
    @Excel(name = "发生频率")
    private String occurrence;
    /**
     * 字段描述：影响范围
     */
    @JsonProperty
    @Excel(name = "影响范围")
    private String reach;
    /**
     * 字段描述：社会关注
     */
    @JsonProperty
    @Excel(name = "社会关注")
    private String communityAttention;
    /**
     * 字段描述：产值年消耗量
     */
    @JsonProperty
    @Excel(name = "产值年消耗量")
    private String valueConsumption;
    /**
     * 字段描述：可节约程度
     */
    @JsonProperty
    @Excel(name = "可节约程度")
    private String saveDegree;
    /**
     * 字段描述：综合分
     */
    @JsonProperty
    @Excel(name = "综合分")
    private BigDecimal totalScore;
    /**
     * 字段描述：是否重大环境因素
     */
    @JsonProperty
    @Excel(name = "是否重大环境因素")
    private String isMajor;
    /**
     * 字段描述：工程类别
     */
    @JsonProperty
    @Excel(name = "工程类别")
    private String projectType;
    /**
     * 字段描述：标准wbs编码
     */
    @JsonProperty
    @Excel(name = "标准wbs编码")
    private String wbsCode;

    @JsonProperty
    private String isAdd;

    /**
     * 字段描述：措施项
     */
    @JsonProperty
    @Excel(name = "措施项")
    private String measureName;

    private List<QyzsSafeEnvRiskProc> children;
}
