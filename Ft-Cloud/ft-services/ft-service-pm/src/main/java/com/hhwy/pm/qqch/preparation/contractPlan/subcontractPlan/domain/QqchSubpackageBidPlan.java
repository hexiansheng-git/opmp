package com.hhwy.pm.qqch.preparation.contractPlan.subcontractPlan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:35:44
 * @remark qqch_subpackage_bid_plan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchSubpackageBidPlan extends BaseEntity {
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
     * 字段描述：名称
     */
    @JsonProperty
    @Excel(name = "名称")
    private String name;
    /**
     * 字段描述：施工内容
     */
    @JsonProperty
    @Excel(name = "施工内容")
    private String content;
    /**
     * 字段描述：拟分包模式
     */
    @JsonProperty
    @Excel(name = "拟分包模式")
    private String subpackageModel;
    /**
     * 字段描述：预计分包合同额
     */
    @JsonProperty
    @Excel(name = "预计分包合同额")
    private BigDecimal subpackageContractAmount;
    /**
     * 字段描述：拟支付货币和支付比例
     */
    @JsonProperty
    @Excel(name = "拟支付货币和支付比例")
    private String currencyRatio;
    /**
     * 字段描述：拟招标/议标方案
     */
    @JsonProperty
    @Excel(name = "拟招标/议标方案")
    private String bidScheme;
    /**
     * 字段描述：拟分包方案依据
     */
    @JsonProperty
    @Excel(name = "拟分包方案依据")
    private String subpackageSchemeGist;
    /**
     * 字段描述：拟启动招标程序时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "拟启动招标程序时间", dateFormat = "yyyy-MM-dd")
    private Date startupTime;
    /**
     * 字段描述：拟定协作单位进场时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "拟定协作单位进场时间", dateFormat = "yyyy-MM-dd")
    private Date approachTime;
    /**
     * 字段描述：拟邀请参与投标协作单位来源
     */
    @JsonProperty
    @Excel(name = "拟邀请参与投标协作单位来源")
    private String cooperativeUnitSource;
    /**
     * 字段描述：合同签订和执行情况
     */
    @JsonProperty
    @Excel(name = "合同签订和执行情况")
    private String contractSignExecution;
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
     * 字段描述：子集合
     */
    private List<QqchSubpackageBidPlan> children;
    /**
     * 字段描述：分包清单集合
     */
//    private List<QqchSubpackageInventory> qqchSubpackageInventoryList;

    /**
     * 字段描述：人员策划集合
     */
//    private List<QqchStaffPlan> qqchStaffPlanList;
    /**
     * 字段描述：设备策划集合
     */
//    private List<QqchFacilityPlan> qqchFacilityPlanList;
}
