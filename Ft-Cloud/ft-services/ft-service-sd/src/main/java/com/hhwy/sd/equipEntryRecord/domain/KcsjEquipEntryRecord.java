package com.hhwy.sd.equipEntryRecord.domain;

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
 * @date 2023-12-14 11:08:08
 * @remark kcsj_equip_entry_record
 */
@Data
public class KcsjEquipEntryRecord extends TreeNode<KcsjEquipEntryRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long pid;
    /**
     * 字段描述：所属班组ID
     */
    @JsonProperty
    @Excel(name = "所属班组ID")
    private String teamId;
    /**
     * 字段描述：所属班组名称
     */
    @JsonProperty
    @Excel(name = "所属班组名称")
    private String teamName;
    /**
     * 字段描述：班组编码
     */
    @JsonProperty
    @Excel(name = "班组编码")
    private String teamNumber;
    /**
     * 字段描述：类别名称
     */
    @JsonProperty
    @Excel(name = "类别名称")
    private String categoryName;
    /**
     * 字段描述：类别id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "类别id")
    private Long categoryId;
    /**
     * 字段描述：设备编码
     */
    @JsonProperty
    @Excel(name = "设备编码")
    private String equipCode;
    /**
     * 字段描述：设备名称
     */
    @JsonProperty
    @Excel(name = "设备名称")
    private String equipName;
    /**
     * 字段描述：规格型号
     */
    @JsonProperty
    @Excel(name = "规格型号")
    private String equipSpec;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String equipUnit;
    /**
     * 字段描述：计划进场数量
     */
    @JsonProperty
    @Excel(name = "计划进场数量")
    private BigDecimal planNum;
    /**
     * 字段描述：实际进场数量
     */
    @JsonProperty
    @Excel(name = "实际进场数量")
    private BigDecimal practicalNum;
    /**
     * 字段描述：数据来源 0新增1同步
     */
    @JsonProperty
    @Excel(name = "数据来源 0新增1同步")
    private String dataSource;
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
     * 字段描述：预留字段1   项目编码
     */
    @JsonProperty
    @Excel(name = "预留字段1   项目编码")
    private String ptVar1;
    /**
     * 字段描述：预留字段2  leaf 是否是叶子节点 0否1是
     */
    @JsonProperty
    @Excel(name = "预留字段2  leaf 是否是叶子节点 0否1是")
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

    private List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList;

    //新增数据标识
    private String isAdd;

    private List<String> delIdList;

}
