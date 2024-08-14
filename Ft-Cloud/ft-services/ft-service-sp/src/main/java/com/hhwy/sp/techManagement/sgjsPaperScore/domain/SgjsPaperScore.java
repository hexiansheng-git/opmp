package com.hhwy.sp.techManagement.sgjsPaperScore.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author fsd
 * @date 2024-07-10 17:28:01
 * @remark sgjs_paper_score 论文评分主表
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SgjsPaperScore extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private List<Long> ids;
    private String startTime;
    private String endTime;
    private Integer limit;
    private String paperFile;

    /**
     * 字段描述：最终得分
     */
    private Integer finalScore;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：论文编号
     */
    @JsonProperty
    @FtExcel(name = "论文编号")
    private String paperCode;
    /**
     * 字段描述：论文名称
     */
    @JsonProperty
    @FtExcel(name = "论文名称")
    private String paperName;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @FtExcel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：申报等级
     */
    @JsonProperty
    private String declareGrade;
    /**
     * 字段描述：专业类别
     */
    @JsonProperty
    @FtExcel(name = "专业类别",dictType = "profession_areas")
    private String professionType;
    /**
     * 字段描述：专业板块
     */
    @JsonProperty
    @FtExcel(name = "专业板块",dictType = "profession_plate")
    private String professionPlate;
    /**
     * 字段描述：主要完成人
     */
    @JsonProperty
    @FtExcel(name = "主要完成人")
    private String principalConsumator;
    /**
     * 字段描述：主要完成人联系方式
     */
    @JsonProperty
    private String principalConsumatorContactWay;
    /**
     * 字段描述：评分专家
     */
    @JsonProperty
    @FtExcel(name = "评分专家")
    private String specialist;
    /**
     * 字段描述：平均分
     */
    @JsonProperty
    @FtExcel(name = "平均分")
    private Integer averageScore;
    /**
     * 字段描述：当前状态（未发起，审批中，已结束）
     */
    @JsonProperty
    @FtExcel(name = "评审状态")
    private String taskStatus;
    /**
     * 字段描述：提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date submitDate;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    private String remark;
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
     * 字段描述：项目编号
     */
    @JsonProperty
    private String projectCode;
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
