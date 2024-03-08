package com.hhwy.sd.disclosureRecord.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author han
 * @date 2023-12-18 11:15:48
 * @remark kcsj_disclosure_record
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KcsjDisclosureRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：交底名称
     */
    @JsonProperty
    @FtExcel(name = "交底名称")
    @NotBlank(message = "交底名称不能为空",groups = ValidationGroups.Save.class)
    private String disclosureName;
    /**
     * 字段描述：交底单位
     */
    @JsonProperty
    @FtExcel(name = "交底单位")
    @NotBlank(message = "交底单位不能为空",groups = ValidationGroups.Save.class)
    private String disclosureUnit;
    /**
     * 字段描述：被交底单位
     */
    @JsonProperty
    @FtExcel(name = "被交底单位")
    @NotBlank(message = "被交底单位不能为空",groups = ValidationGroups.Save.class)
    private String beDisclosureUnit;
    /**
     * 字段描述：交底内容
     */
    @JsonProperty
    @FtExcel(name = "交底内容")
    private String disclosureContent;
    /**
     * 字段描述：实际交底日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @JSONField(format = "yyyy-MM-dd")
    @FtExcel(name = "实际交底日期", dateFormat = "yyyy年MM月dd日")
    @NotBlank(message = "实际交底日期不能为空",groups = ValidationGroups.Save.class)
    private Date actualDisclosureDate;
    /**
     * 字段描述：数据来源 （0：新增，1：同步）
     */
    @JsonProperty
    private String dataSource;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    @NotBlank(message = "附件不能为空",groups = ValidationGroups.Save.class)
    private String fileGroupId;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @FtExcel(name = "备注")
    private String remark;
    /**
     * 字段描述：流程状态（5已完成）
     */
    @JsonProperty
    private String taskStatus;
    /**
     * 字段描述：排序
     */
    @JsonProperty
    private Integer sort;
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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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

    /**
     * 是否是新增数据
     */
    private String isAdd;
}
