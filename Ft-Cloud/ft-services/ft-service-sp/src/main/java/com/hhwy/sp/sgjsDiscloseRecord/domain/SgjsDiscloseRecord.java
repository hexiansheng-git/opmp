package com.hhwy.sp.sgjsDiscloseRecord.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author cjh
 * @date 2023-11-23 14:47:22
 * @remark sgjs_disclose_record
 */
@Data
public class SgjsDiscloseRecord extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：交底文件
     */
    @JsonProperty
    //@Excel(name = "交底文件")
    private String fileGroupId;

    /**
     * 字段描述：交底记录上传
     */
    @JsonProperty
    //@Excel(name = "交底记录上传")
    private String discloseFileId;

    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    //@Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    //@Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@Excel(name = "部门id")
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    //@Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    //@Excel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    //@Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    //@Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    //@Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    //@Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    //@Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    //@Excel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    //@Excel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    //@Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    //@Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：交底编号
     */
    @JsonProperty
    @Excel(name = "交底编号")
    private String ptVar5;
    /**
     * 字段描述：交底等级
     */
    @JsonProperty
    @Excel(name = "交底等级",dictType = "dis_levle")
    private String discloseLevel;
    /**
     * 字段描述：交底名称
     */
    @JsonProperty
    @Excel(name = "交底名称")
    @FtExcel(name = "交底名称")
    private String discloseName;
    /**
     * 字段描述：交底人
     */
    @JsonProperty
    @Excel(name = "交底人")
    @FtExcel(name = "交底人")
    private String discloseUser;
    /**
     * 字段描述：交底人职务
     */
    @JsonProperty
    @Excel(name = "交底人职务")
    @FtExcel(name = "交底人职务")
    private String discloseUserPost;
    /**
     * 字段描述：被交底人
     */
    @JsonProperty
    @Excel(name = "被交底人")
    @FtExcel(name = "被交底人")
    private String wasDiscloseUser;
    /**
     * 字段描述：实际交底日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际交底日期", dateFormat = "yyyy年MM月dd日")
    @FtExcel(name = "实际交底日期", dateFormat = "yyyy年MM月dd日")
    private Date actDiscloseDate;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "创建日期", dateFormat = "yyyy年MM月dd日")
    @FtExcel(name = "创建日期", dateFormat = "yyyy年MM月dd日")
    private Date createTime;
    /**
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
    @Excel(name = "流程状态",dictType = "task_status")
    private String taskStatus;

    private String taskStatusStr;
    /**
     *字段描述：当前处理人
     */
    @FtExcel(name = "当前处理人")
    @Excel(name = "当前处理人")
    private String processTaskMan;//当前处理人
    /**
     * 字段描述：wbs名称
     */
    @JsonProperty
    //@Excel(name = "项目WBS")
    //@FtExcel(name = "项目WBS")
    private String wbsName;
    /**
     * 字段描述：审核人
     */
    @JsonProperty
    //@Excel(name = "审核人")
    //@FtExcel(name = "审核人")
    private String reviewUser;
    /**
     * 字段描述：作业班组
     */
    @JsonProperty
    //@Excel(name = "作业班组")
    //@FtExcel(name = "作业班组")
    private String workTeam;

    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    @FtExcel(name = "备注")
    private String remark;


    /**
     * 字段描述：wbs编码
     */
    @JsonProperty
    private String wbsCode;

    /**
     * 数据分类:oneOrTwo、three
     */
    private String dataType;

    /**
     * 是否是新增数据
     */
    private String isAdd;
    /**
     * 字段描述：实际交底日期-查询-开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date queryStartDate;
    /**
     * 字段描述：实际交底日期-查询-结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date queryEndDate;

    @JsonProperty
    private List<SgjsDiscloseRecord> exportList;

    private String type;

    //施工方案编号
    private String buildNo;
}
