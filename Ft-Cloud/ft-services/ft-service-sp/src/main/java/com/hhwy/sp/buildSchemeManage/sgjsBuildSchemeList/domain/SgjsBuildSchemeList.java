package com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author fushudong
 * @date 2024-03-19 15:57:37
 * @remark sgjs_build_scheme_list
 */
@Data
public class SgjsBuildSchemeList extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer serialNum;
    private String startTime;
    private String endTime;
    private Long[] ids;
    private String[] schemeTypeArr;
    private String[] dangerLevelArr;

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
    private Long foreignId;
    /**
     * 字段描述：方案编号
     */
    @JsonProperty
    @FtExcel(name = "方案编号")
    private String schemeNum;
    /**
     * 字段描述：方案名称
     */
    @JsonProperty
    @FtExcel(name = "方案名称")
    @NotBlank(message = "方案名称不能为空",groups = {ValidationGroups.Save.class})
    private String schemeName;
    /**
     * 字段描述：变更类型 1推迟、2提前、3新增、4废止
     */
    @JsonProperty
    @FtExcel(name = "变更类型", dictType = "change_type")
    private String changeType;
    /**
     * 字段描述：关联WBS
     */
    @JsonProperty
//    @FtExcel(name = "关联WBS编号")
    private String relationWbsId;
    /**
     * 字段描述：关联WBS
     */
    @JsonProperty
    @FtExcel(name = "关联WBS名称")
    private String relationWbsName;
    /**
     * 字段描述：方案类型
     */
    @JsonProperty

    private String schemeType;

    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    @FtExcel(name = "方案类型")
    private String ptVar4;

    /**
     * 字段描述：方案分级 1Ⅰ、2Ⅱ、3Ⅲ、4Ⅳ
     */
    @JsonProperty
    @FtExcel(name = "方案分级", dictType = "scheme_level")
    @NotBlank(message = "方案分级不能为空",groups = {ValidationGroups.Save.class})
    private String schemeLevel;
    /**
     * 字段描述：是否危大工程 1危大、2超危大、3一般
     */
    @JsonProperty
    @FtExcel(name = "是否危大工程", dictType = "danger_level")
    @NotBlank(message = "是否危大工程不能为空",groups = {ValidationGroups.Save.class})
    private String dangerLevel;
    /**
     * 字段描述：施工重难点
     */
    @JsonProperty
    @FtExcel(name = "施工重难点")
    @NotBlank(message = "施工重难点不能为空",groups = {ValidationGroups.Save.class})
    private String buildDifficult;
    /**
     * 字段描述：计划实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty
    @FtExcel(name = "计划实施时间", dateFormat = "yyyy年MM月dd日")
//    @NotNull(message = "计划实施时间不能为空",groups = {ValidationGroups.Save.class})
    private Date planImplementTime;
    /**
     * 字段描述：计划编制完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty
    @FtExcel(name = "计划编制完成时间", dateFormat = "yyyy年MM月dd日")
    @NotNull(message = "计划编制完成时间不能为空",groups = {ValidationGroups.Save.class})
    private Date planComplationTime;
    /**
     * 字段描述：原计划实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty
//    @FtExcel(name = "原计划实施时间", dateFormat = "yyyy-MM-dd")
    private Date planComplationTimeOrigin;
    /**
     * 字段描述：变更计划实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty
    @FtExcel(name = "变更计划实施时间", dateFormat = "yyyy年MM月dd日")
    private Date planImplementTimeChange;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    private String remark;
    /**
     * 字段描述：关联id,业务唯一标识
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long relevancyId;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
     * 字段描述：标识 0原版本带过来，1本次调整的
     */
    @JsonProperty
    private String ptVar3;
    /**
     * 字段描述：项目编号
     */
    @JsonProperty
    private String ptVar5;
}
