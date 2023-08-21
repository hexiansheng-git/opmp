package com.hhwy.pm.qqch.sgch.important.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.utils.JsonUtils;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mls
 * @date 2023-08-03 11:17:04
 * @remark qqch_important
 */
@Data
@ToString
public class QqchImportant extends CompileEntity<QqchImportant> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：重要工作事项
     */
    @JsonProperty
    @FtExcel(name = "重要工作事项")
    private String importantJob;
    /**
     * 字段描述：可能出现的风险
     */
    @JsonProperty
    @FtExcel(name = "可能出现的风险")
    private String risk;
    /**
     * 字段描述：保障措施
     */
    @JsonProperty
    @FtExcel(name = "保障措施")
    private String safeguard;
    /**
     * 字段描述：责任部门名称
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long dutyDeptId;
    /**
     * 字段描述：责任部门名称
     */
    @JsonProperty
    @FtExcel(name = "责任部门")
    private String dutyDeptName;
    /**
     * 字段描述：责任人名称
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long dutyUserId;
    /**
     * 字段描述：责任人名称
     */
    @JsonProperty
    @FtExcel(name = "责任人")
    private String dutyUserName;
    /**
     * 字段描述：开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "开始时间", dateFormat = "yyyy/MM/dd")
    private Date beginDate;
    /**
     * 字段描述：结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "结束时间", dateFormat = "yyyyMM/dd")
    private Date endDate;
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
     * 字段描述：备注
     */
    @JsonProperty
    @FtExcel(name = "备注")
    private String remark;
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


    public static void main(String[] args) {
        JsonUtils.soutJsonStr(QqchImportant.class);
    }

}
