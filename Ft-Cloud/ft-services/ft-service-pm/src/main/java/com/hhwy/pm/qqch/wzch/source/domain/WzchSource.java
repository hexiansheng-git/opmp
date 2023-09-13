package com.hhwy.pm.qqch.wzch.source.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 来源策划对象 wzch_source
 * 
 * @author mls
 * @date 2022-11-21
 */
@Data
@NoArgsConstructor
public class WzchSource extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String sourceCode;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 所属区域 */
    @Excel(name = "所属区域")
    private String regionName;

    /** 所属区域id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 附件组id */
    private String fileGroupId;

    /** 项目id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 数据创建者名称 */
    @Excel(name = "编制人")
    private String createUserName;

    /** 数据修改者名称 */
    private String updateUserName;

    /** 数据删除者 */
    private String delUser;

    /** 数据删除系统时间 */
    private Date delTime;

    /** 物资总需版本 */
    private String demandVersion;
    /** 物资总需最新版本 */
    private String demandNewVersion;
    /** 物资总需生效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date demandValidDate;

    private List<WzchSourceDetail> wzchSourceDetailList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Excel(name = "编制时间",dateFormat = "yyyy-MM-dd HH:mm")
    private Date createTime;

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

    private String valid;
    
    private List<Long> idList;

    public WzchSource(Long id, Long projectId) {
        this.id = id;
        this.projectId = projectId;
    }


}
