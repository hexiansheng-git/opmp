package com.hhwy.pm.xmsl.implement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import java.util.Date;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:13
 * @remark 地形地貌
 */
@Data
public class XmslTerrainLandforms extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 字段描述：段落
     */
    @JsonProperty
    @Excel(name = "段落")
    private String section;
    /**
     * 字段描述：地形
     */
    @JsonProperty
    @Excel(name = "地形")
    private String terrain;
    /**
     * 字段描述：最高海拔
     */
    @JsonProperty
    @Excel(name = "最高海拔")
    private String maxAltitude;
    /**
     * 字段描述：最低海拔
     */
    @JsonProperty
    @Excel(name = "最低海拔")
    private String minAltitude;
    /**
     * 字段描述：高差
     */
    @JsonProperty
    @Excel(name = "高差")
    private String heightDiff;
    /**
     * 字段描述：位置
     */
    @JsonProperty
    @Excel(name = "位置")
    private String positionOne;
    /**
     * 字段描述：坡率
     */
    @JsonProperty
    @Excel(name = "坡率")
    private String slopeRate;
    /**
     * 字段描述：位置
     */
    @JsonProperty
    @Excel(name = "位置")
    private String positionTwo;
    /**
     * 字段描述：类型
     */
    @JsonProperty
    @Excel(name = "类型")
    private String type;
    /**
     * 字段描述：最大填高/挖深
     */
    @JsonProperty
    @Excel(name = "最大填高/挖深")
    private String maxFillingHeight;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    private String fileGroupId;
    /**
     * 字段描述：所属区域id
     */
    @JsonProperty
    private Integer regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonProperty
    private Integer projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonProperty
    private Integer deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    private String createUser;
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
