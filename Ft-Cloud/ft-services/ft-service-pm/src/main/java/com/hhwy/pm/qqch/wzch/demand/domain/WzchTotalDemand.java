package com.hhwy.pm.qqch.wzch.demand.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 物资总需对象 wzch_total_demand
 * 
 * @author mls
 * @date 2022-11-15
 */
@Data
@NoArgsConstructor
@ToString
public class WzchTotalDemand extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private List<Long> ids;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String demandCode;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 所属区域id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long regionId;

    /** 所属区域 */
    @Excel(name = "所属区域")
    private String regionName;

    /** 版本号 */
    @Excel(name = "版本号")
    private String versionCode;
    private String versionCodeStr;

    /** 计划开始时间 */
    @Excel(name = "计划开始时间", width = 30, dateFormat = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date planStartTime;

    /** 计划结束时间 */
    @Excel(name = "计划结束时间", width = 30, dateFormat = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date planEndTime;

    /** 附件组id */
    @Excel(name = "附件组id")
    private String fileGroupId;

    /** 项目id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 部门id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;

    @Excel(name = "编制时间",  dateFormat = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    /** 数据创建者id */
    @Excel(name = "数据创建者id")
    private String createUser;

    /** 数据创建者名称 */
    @Excel(name = "数据创建者名称")
    private String createUserName;

    /** 数据修改者id */
    @Excel(name = "数据修改者id")
    private String updateUser;

    /** 数据修改者名称 */
    @Excel(name = "数据修改者名称")
    private String updateUserName;

    /** 数据删除者 */
    @Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag = "0";

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String ptVar1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String ptVar2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private String ptVar3;

    /** 预留字段4 */
    @Excel(name = "预留字段4")
    private String ptVar4;

    /** 预留字段5 */
    @Excel(name = "预留字段5")
    private String ptVar5;

    /**
     * 0=否 1-是
     */
    @Excel(name = "是否有效")
    private String valid;

    //视角类型
    private String viewType;

    /**
     * 字段描述：阶段标识
     */
    @NotBlank(message = "阶段标识不能为空！",groups = ValidationGroups.Save.class)
    private String stageIdentity;
    /**
     * 字段描述：版本
     */
    @NotNull(message = "版本不能为空！",groups = ValidationGroups.Save.class)
    private BigDecimal version;
    /**
     * 字段描述：菜单id
     */
    @NotBlank(message = "菜单id不能为空！",groups = ValidationGroups.Save.class)
    private String menuId;
    /**
     * 字段描述：按钮标识（0：保存，1：确认，2：提交）
     */
    @NotBlank(message = "按钮标识不能为空！",groups = ValidationGroups.Save.class)
    private String buttonMark;

    @JsonProperty
    @Excel(name = "模块标识（页面唯一标识）1： 2： ...")
    private String moduleIdentity;


    private List<WzchTotalDemandDetail> wzchTotalDemandDetailList;

    public WzchTotalDemand(Long id) {
        this.id = id;
    }

    public WzchTotalDemand(String valid) {
        this.valid = valid;
    }

    public WzchTotalDemand(Long id, Long projectId, String valid) {
        this.id = id;
        this.projectId = projectId;
        this.valid = valid;
    }

    public WzchTotalDemand(Long id, String valid) {
        this.id = id;
        this.valid = valid;
    }

    public WzchTotalDemand( String versionCode, Long projectId) {
        this.versionCode = versionCode;
        this.projectId = projectId;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
        this.versionCode = version==null?"":version.toString();
    }

}
