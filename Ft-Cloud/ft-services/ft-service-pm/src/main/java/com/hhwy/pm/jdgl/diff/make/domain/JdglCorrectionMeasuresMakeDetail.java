package com.hhwy.pm.jdgl.diff.make.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.tree.TreeNode;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-25 15:26:48
 * @remark jdgl_correction_measures_make_detail
 */
@Data
public class JdglCorrectionMeasuresMakeDetail extends TreeNode<JdglCorrectionMeasuresMakeDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父id")
    private Long pid;
    /**
     * 字段描述：制定id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "制定id")
    private Long makeId;
    /**
     * 字段描述：作业代码
     */
    @JsonProperty
    @Excel(name = "作业代码")
    private String workCode;
    /**
     * 字段描述：作业名称
     */
    @JsonProperty
    @Excel(name = "作业名称")
    private String workName;
    /**
     * 字段描述：是否关键线路 1-有效 0-失效
     */
    @JsonProperty
    @Excel(name = "是否关键线路 1-有效 0-失效")
    private String isKeyLine;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：工程量
     */
    @JsonProperty
    @Excel(name = "工程量")
    private BigDecimal quantity;
    /**
     * 字段描述：偏差量
     */
    @JsonProperty
    @Excel(name = "偏差量")
    private BigDecimal deviationQuantity;
    /**
     * 字段描述：总时差
     */
    @JsonProperty
    @Excel(name = "总时差")
    private BigDecimal totalFloat;
    /**
     * 字段描述：预计滞后天数
     */
    @JsonProperty
    @Excel(name = "预计滞后天数")
    private BigDecimal expectLagDay;
    /**
     * 字段描述：SV值
     */
    @JsonProperty
    @Excel(name = "SV值")
    private BigDecimal svValue;
    /**
     * 字段描述：完成进度百分比
     */
    @JsonProperty
    @Excel(name = "完成进度百分比")
    private BigDecimal completeProgressPercentage;
    /**
     * 字段描述：完成工期百分比
     */
    @JsonProperty
    @Excel(name = "完成工期百分比")
    private BigDecimal completeDatePercentage;
    /**
     * 字段描述：偏差原因分析
     */
    @JsonProperty
    @Excel(name = "偏差原因分析")
    private String deviationCausesAnalysis;
    /**
     * 字段描述：纠偏目标
     */
    @JsonProperty
    @Excel(name = "纠偏目标")
    private String correctionTarget;
    /**
     * 字段描述：具体措施
     */
    @JsonProperty
    @Excel(name = "具体措施")
    private String concreteMeasure;
    /**
     * 字段描述：纠偏完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "纠偏完成日期")
    private Date correctionCompleteDate;
    /**
     * 字段描述：作业队伍
     */
    @JsonProperty
    @Excel(name = "作业队伍")
    private BigDecimal workTeam;
    /**
     * 字段描述：责任人id
     */
    @JsonProperty
    @Excel(name = "责任人id")
    private String directorId;
    /**
     * 字段描述：责任人
     */
    @JsonProperty
    @Excel(name = "责任人")
    private String director;
    /**
     * 字段描述：纠偏执行情况
     */
    @JsonProperty
    @Excel(name = "纠偏执行情况")
    private String correctionExecuteSituation;
    /**
     * 字段描述：排序
     */
    @JsonProperty
    @Excel(name = "排序")
    private Integer sort;
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
}
