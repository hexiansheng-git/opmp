package com.hhwy.pm.qqch.sgch.qqchconst.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.utils.JsonUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 16:08:05
 * @remark qqch_const
 */
@Data
@NoArgsConstructor
@ToString
public class QqchConst extends CompileEntity<QqchConst> {
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
     * 字段描述：施工名称
     */
    @JsonProperty
    @Excel(name = "施工名称")
    private String constName;
    /**
     * 字段描述：明细
     */
    @JsonProperty
    @Excel(name = "明细")
    private String constDesc;
    /**
     * 字段描述：类型 const_type
     */
    @JsonProperty
    @Excel(name = "类型 const_type")
    private String constType;
    /**
     * 字段描述：施工内容
     */
    @JsonProperty
    @Excel(name = "施工内容")
    private String constContent;
    /**
     * 字段描述：组织模式 org_pattern
     */
    @JsonProperty
    @Excel(name = "组织模式 org_pattern")
    private String orgPattern;
    /**
     * 字段描述：拟选协作队伍id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "拟选协作队伍id")
    private Long teamId;
    /**
     * 字段描述：拟选协作队伍名称
     */
    @JsonProperty
    @Excel(name = "拟选协作队伍名称")
    private String teamName;
    /**
     * 字段描述：选用方式 selected_type
     */
    @JsonProperty
    @Excel(name = "选用方式 selected_type")
    private String selectedType;
    /**
     * 字段描述：支付币种和比例
     */
    @JsonProperty
    @Excel(name = "支付币种和比例")
    private String payInfo;
    /**
     * 字段描述：外账比例
     */
    @JsonProperty
    @Excel(name = "外账比例")
    private String extRatio;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：数据来源（1：选择，2：手动新增）
     */
    @JsonProperty
    @Excel(name = "数据来源（1：选择，2：手动新增）")
    private String source;
    /**
     * 字段描述：叶子节点（1：是，0：否）
     */
    @JsonProperty
    @Excel(name = "叶子节点（1：是，0：否）")
    private String leaf;
    /**
     * 字段描述：排序
     */
    @JsonProperty
    @Excel(name = "排序")
    private Integer sort;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private BigDecimal version;
    /**
     * 字段描述：是否有效 1-有效 0-失效
     */
    @JsonProperty
    @Excel(name = "是否有效 1-有效 0-失效")
    private String valid;
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
     * 工作内容
     */
    private List<QqchConstJob> jobList;
    /**
     * 人员策划
     */
    private List<QqchConstStaffPlan> staffList;
    /**
     * 设备策划
     */
    private List<QqchConstFacilityPlan> facilityPlanList;


    public static void main(String[] args) {
        
//        JsonUtils.soutJsonStr(QqchConstJob.class);
//        JsonUtils.soutJsonStr(QqchConstStaffPlan.class);
        JsonUtils.soutJsonStr(QqchConstFacilityPlan.class);
    }

    /**
     * 字段描述：工种编号
     */
    @JsonProperty
    @Excel(name = "工种编号")
    private String occupationCode;
    /**
     * 字段描述：工种名称
     */
    @JsonProperty
    @Excel(name = "工种名称")
    private String occupationName;

    /**
     * 字段描述：关联id
     */
    private Long relevancy;

    /*分包收入*/
    private BigDecimal subpackageIncome;
    /*总产值占比*/
    private BigDecimal totalOutputValueProportion;
}
