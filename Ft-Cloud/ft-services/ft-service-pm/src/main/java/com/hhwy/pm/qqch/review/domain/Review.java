package com.hhwy.pm.qqch.review.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import com.hhwy.utils.JsonUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author mls
 * @date 2023-07-18 09:58:24
 * @remark qqch_review
 */
@Data
@NoArgsConstructor
public class Review extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    public Review(Long id) {
        this.id = id;
    }

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：评审名称
     */
    @JsonProperty
    @FtExcel(name = "评审名称")
    private String reviewName;
    /**
     * 字段描述：前期策划阶段
     */
    @JsonProperty
    @FtExcel(name = "前期策划阶段")
    private String stage;
    /**
     * 字段描述：评审组织部门id
     */
    @JsonProperty
    @FtExcel(name = "评审组织部门id")
    private String reviewDeptId;
    /**
     * 字段描述：评审组织部门名称
     */
    @JsonProperty
    @FtExcel(name = "评审组织部门名称")
    private String reviewDeptName;
    /**
     * 字段描述：要求完成评审日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "要求完成评审日期", dateFormat = "yyyy-MM-dd")
    private Date finishDate;
    /**
     * 字段描述：评审要求
     */
    @JsonProperty
    @FtExcel(name = "评审要求")
    private String reviewReq;
    /**
     * 字段描述：策划阶段(1-第一阶段,2-第二阶段,3-第三阶段);字典项:plan_stage
     */
    @JsonProperty
    @FtExcel(name = "策划阶段(1-第一阶段,2-第二阶段,3-第三阶段);字典项:plan_stage",dictType = "plan_stage")
    private String planStage;
    /**
     * 字段描述：策划编制负责人
     */
    @JsonProperty
    @FtExcel(name = "策划编制负责人")
    private String planEstablishDirector;
    /**
     * 字段描述：策划编制负责人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @FtExcel(name = "策划编制负责人id")
    private Long planEstablishDirectorId;
    /**
     * 字段描述：工作内容说明
     */
    @JsonProperty
    @FtExcel(name = "工作内容说明")
    private String planContent;
    /**
     * 字段描述：要求提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "要求提交日期", dateFormat = "yyyy-MM-dd")
    private Date reqSubmitDate;
    /**
     * 字段描述：策划项数量
     */
    @JsonProperty
    @FtExcel(name = "策划项数量")
    private Integer planNum;
    /**
     * 字段描述：编制完成数量
     */
    @JsonProperty
    @FtExcel(name = "编制完成数量")
    private Integer finishNum;
    /**
     * 字段描述：紧急程度 0特急 1紧急 2 正常
     */
    @JsonProperty
    @FtExcel(name = "紧急程度 0特急 1紧急 2 正常")
    private String exigencyStatus;
    /**
     * 字段描述：流程状态
     */
    @JsonProperty
    @FtExcel(name = "流程状态")
    private String taskStatus;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    @FtExcel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @FtExcel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @FtExcel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    @FtExcel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @FtExcel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    @FtExcel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @FtExcel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    @FtExcel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @FtExcel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @FtExcel(name = "删除标识：0未删除；1已删除")
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
    @FtExcel(name = "发起人id")
    private Long initUserId;
    @FtExcel(name = "发起人姓名")
    private String initUserName;
    @FtExcel(name = "发起日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date initDate;

    
}
