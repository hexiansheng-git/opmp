package com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fushudong
 * @date 2024-03-19 15:57:37
 * @remark 方案清单-危大清单
 * sgjs_build_scheme_list
 */
@Data
public class SgjsBuildSchemeRiskList implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer ptVar6;
    private String startTime;
    private String endTime;
    private Long[] ids;

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
    private String schemeName;
    /**
     * 字段描述：变更类型 1推迟、2提前、3新增、4废止
     */
    @JsonProperty
    private String changeType;
    /**
     * 字段描述：关联WBS
     */
    @JsonProperty
    private String relationWbsId;
    /**
     * 字段描述：关联WBS
     */
    @JsonProperty
    @FtExcel(name = "WBS")
    private String relationWbsName;
    /**
     * 字段描述：方案类型
     */
    @JsonProperty
    private String schemeType;
    /**
     * 字段描述：方案分级 1Ⅰ、2Ⅱ、3Ⅲ、4Ⅳ
     */
    @JsonProperty
    @FtExcel(name = "方案分级", dictType = "scheme_level")
    private String schemeLevel;
    /**
     * 字段描述：是否危大工程 1危大、2超危大、3一般
     */
    @JsonProperty
    @FtExcel(name = "危大等级", dictType = "danger_level")
    private String dangerLevel;
    /**
     * 字段描述：施工重难点
     */
    @JsonProperty
    @FtExcel(name = "施工重难点")
    private String buildDifficult;
    /**
     * 字段描述：计划编制完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划编制完成时间", dateFormat = "yyyy年MM月dd日")
    private Date planComplationTime;
    /**
     * 字段描述：原计划实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date planComplationTimeOrigin;
    /**
     * 字段描述：计划实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划实施时间", dateFormat = "yyyy年MM月dd日")
    private Date planImplementTime;
    /**
     * 字段描述：变更计划实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
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
     * 字段描述：标识 0原版本带过来，1本次调整的
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
