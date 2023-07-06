package com.hhwy.pm.xmsl.implement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import java.util.Date;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:35
 * @remark 当地政策要点说明, 社会和人文条件说明, 气候条件附件
 */
@Data
public class XmslExtend extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonProperty
    @Excel(name = "主键id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 字段描述：当地政策要点说明
     */
    @JsonProperty
    @Excel(name = "当地政策要点说明")
    private String policyKeyPointsExplanation;
    /**
     * 字段描述：社会和人文条件说明
     */
    @JsonProperty
    @Excel(name = "社会和人文条件说明")
    private String socialHumanisticExplanation;
    /**
     * 字段描述：气候条件附件组id
     */
    @JsonProperty
    @Excel(name = "气候条件附件组id")
    private String fileGroupId;
    /**
     * 字段描述：所属区域id
     */
    @JsonProperty
    @Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonProperty
    @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonProperty
    @Excel(name = "部门id")
    private Long deptId;
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
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据创建系统时间")
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
    @Excel(name = "数据修改系统时间")
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
    @Excel(name = "数据删除系统时间")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    @Excel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    @Excel(name = "预留字段2")
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
}
