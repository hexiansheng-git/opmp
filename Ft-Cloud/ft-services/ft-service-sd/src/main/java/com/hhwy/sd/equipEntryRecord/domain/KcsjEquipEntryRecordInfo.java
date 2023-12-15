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
 * @date 2023-12-14 11:08:15
 * @remark kcsj_equip_entry_record_info
 */
@Data
public class KcsjEquipEntryRecordInfo extends TreeNode<KcsjEquipEntryRecordInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    //@Excel(name = "主表id")
    private Long recordId;
    /**
     * 字段描述：班组名称
     */
    @JsonProperty
    @Excel(name = "班组名称")
    private String teamName;
    /**
     * 字段描述：设备编码
     */
    @JsonProperty
    @Excel(name = "设备编码")
    private String equipCode;
    /**
     * 字段描述：管理编码
     */
    @JsonProperty
    @Excel(name = "管理编码")
    private String teamNumber;
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
     * 字段描述：来源
     */
    @JsonProperty
    @Excel(name = "来源")
    private String equipSource;
    /**
     * 字段描述：实际进场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际进场日期", dateFormat = "yyyy-MM-dd")
    private Date entryDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String entryDateStr;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String entryEndDateStr;
    /**
     * 字段描述：实际退场时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际退场时间", dateFormat = "yyyy-MM-dd")
    private Date exitDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String exitDateStr;
    /**
     * 字段描述：当前状态
     */
    @JsonProperty
    @Excel(name = "当前状态")
    private String currentState;
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


    //导入查询
    private List<Long> ids;
}
