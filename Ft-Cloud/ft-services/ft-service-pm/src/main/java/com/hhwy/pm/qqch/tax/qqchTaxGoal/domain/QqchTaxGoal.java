package com.hhwy.pm.qqch.tax.qqchTaxGoal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:29
 * @remark qqch_tax_goal
 */
@Data
@ToString
public class QqchTaxGoal extends CompileEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：总目标
     */
    @JsonProperty
    @FtExcel(name = "总目标")
    private String allGoal;
    /**
     * 字段描述：分目标
     */
    @JsonProperty
    @FtExcel(name = "分目标")
    private String partGoal;
    /**
     * 字段描述：关键影响因素
     */
    @JsonProperty
    @FtExcel(name = "关键影响因素")
    private String factor;
    /**
     * 字段描述：应对措施
     */
    @JsonProperty
    @FtExcel(name = "应对措施")
    private String measure;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    private String remark;
    /**
     * 字段描述：叶子节点（1：是，0：否）
     */
    @JsonProperty
    private String leaf;
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
    
    
    @JsonIgnore
    private List children;
 

    public static void main(String[] args) {
        JsonUtils.soutJsonStr(QqchTaxGoal.class);
    }

}
