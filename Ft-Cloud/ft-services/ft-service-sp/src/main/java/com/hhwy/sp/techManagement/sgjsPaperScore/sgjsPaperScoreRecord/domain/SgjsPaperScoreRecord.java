package com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author fsd
 * @date 2024-07-10 17:28:48
 * @remark sgjs_paper_score_record
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SgjsPaperScoreRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键")
    private Long id;
    /**
     * 字段描述：论文主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "论文主表id")
    @NotNull(message = "foreignId不能为空",groups = {ValidationGroups.Save.class})
    private Long foreignId;
    /**
     * 字段描述：评分专家
     */
    @JsonProperty
    @Excel(name = "评分专家")
    @NotBlank(message = "评分专家姓名不能为空",groups = {ValidationGroups.Save.class})
    private String specialist;
    /**
     * 字段描述：评分专家用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "评分专家用户id")
    @NotNull(message = "评分专家userid不能为空",groups = {ValidationGroups.Save.class})
    private Long specialistId;
    /**
     * 字段描述：学术水平分数
     */
    @JsonProperty
    @Excel(name = "学术水平分数")
    @NotNull(message = "学术水平分数不能为空",groups = {ValidationGroups.Update.class})
    private Integer scienceLevel;
    /**
     * 字段描述：推广应用价值分数
     */
    @JsonProperty
    @Excel(name = "推广应用价值分数")
    @NotNull(message = "推广应用价值分数不能为空",groups = {ValidationGroups.Update.class})
    private Integer applyValue;
    /**
     * 字段描述：逻辑性分数
     */
    @JsonProperty
    @Excel(name = "逻辑性分数")
    @NotNull(message = "逻辑性分数不能为空",groups = {ValidationGroups.Update.class})
    private Integer logicality;
    /**
     * 字段描述：写作水平分数
     */
    @JsonProperty
    @Excel(name = "写作水平分数")
    @NotNull(message = "写作水平分数不能为空",groups = {ValidationGroups.Update.class})
    private Integer writingLevel;
    /**
     * 字段描述：综合得分
     */
    @JsonProperty
    @Excel(name = "综合得分")
    @NotNull(message = "综合得分不能为空",groups = {ValidationGroups.Update.class})
    private Integer weightingScore;
    /**
     * 字段描述：提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "提交时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
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
     * 字段描述：项目编号
     */
    @JsonProperty
    @Excel(name = "项目编号")
    private String projectCode;
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
    @NotBlank(message = "评分专家username不能为空",groups = {ValidationGroups.Save.class})
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
