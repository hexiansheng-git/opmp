package com.hhwy.sp.experiment.mixRatioManage.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author han
 * @date 2024-04-07 13:27:44
 * @remark sgjs_mix_ratio_manage_material
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SgjsMixRatioManageMaterial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long foreignId;
    /**
     * 字段描述：材料编号
     */
    @JsonProperty
    @FtExcel(name = "材料编号")
    private String materialCode;
    /**
     * 字段描述：材料名称
     */
    @JsonProperty
    @FtExcel(name = "材料名称")
    private String materialName;
    /**
     * 字段描述：规格型号
     */
    @JsonProperty
    @FtExcel(name = "规格型号")
    private String specifications;
    /**
     * 字段描述：厂牌/产地
     */
    @JsonProperty
    @FtExcel(name = "厂牌/产地")
    private String brand;
    /**
     * 字段描述：材料检测报告编号
     */
    @JsonProperty
    @FtExcel(name = "材料检测报告编号")
    private String materialDetectionReportCode;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @FtExcel(name = "单位")
    private String unit;
    /**
     * 字段描述：数量
     */
    @JsonProperty
    @FtExcel(name = "数量")
    private Double amount;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @FtExcel(name = "备注")
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
     * 字段描述：项目名称
     */
    @JsonProperty
    private String projectName;
    /**
     * 字段描述：项目编码
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
