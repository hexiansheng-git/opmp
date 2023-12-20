package com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain;

import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.tree.TreeNode;
import java.util.Date;
import java.math.BigDecimal;
import com.hhwy.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zmh
 * @date 2023-12-07 18:13:51
 * @remark sgjs_plan_measure_manage
 */
@Data
public class SgjsPlanMeasureManage extends TreeNode<SgjsPlanMeasureManage> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;

    /**
     * 字段描述：父级id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@Excel(name = "父级id")
    private Long pid;

    @Excel(name = "序号")
    private String serialNumber;

    /**
     * 字段描述：测量工作项
     */
    @JsonProperty
    @Excel(name = "测量工作项")
    private String measureName;
    
    /**
     * 字段描述：计量单位
     */
    @JsonProperty
    @Excel(name = "计量单位")
    private String measureUnit;

    /**
     * 字段描述：工作量
     */
    @JsonProperty
    @Excel(name = "工作量")
    private String workload;

    /**
     * 字段描述：计划开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date planStartDate;
    @Excel(name = "计划开始日期")
    private String planStartDateStr;

    /**
     * 字段描述：计划结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date planEndDate;
    @Excel(name = "计划结束日期")
    private String planEndDateStr;

    /**
     * 字段描述：实际开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date realStartDate;
    @Excel(name = "实际开始日期")
    private String realStartDateStr;

    /**
     * 字段描述：实际结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date realEndDate;
    @Excel(name = "实际结束日期")
    private String realEndDateStr;

    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;

    /**
     * 字段描述：数据来源 0新增1同步
     */
    @JsonProperty
    //@Excel(name = "数据来源 0新增1同步")
    private String dataSource;

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
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    //@Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

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
     * 字段描述：预留字段1   项目编码
     */
    @JsonProperty
    //@Excel(name = "预留字段1   项目编码")
    private Long syncId;
    /**
     * 字段描述：预留字段2  leaf 是否是叶子节点 0否1是
     */
    @JsonProperty
    //@Excel(name = "预留字段2  leaf 是否是叶子节点 0否1是")
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
     * 字段描述：预留字段5
     */
    @JsonProperty
    //@Excel(name = "预留字段5")
    private String ptVar5;

    //节点判断标识
    private String path;

    //导入查询
    private List<String> ids;

    //新增标识
    private String isAdd;


}
