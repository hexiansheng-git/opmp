package com.hhwy.pm.xmsl.implement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import java.util.Date;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-03 11:59:44
 * @remark 水、电、交通、通讯条件
 */
@Data
public class XmslBasicFacilitiesConditions extends BaseEntity {

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
     * 字段描述：施工用水
     */
    @JsonProperty
    @Excel(name = "施工用水")
    private String constructionWater;
    /**
     * 字段描述：施工用电
     */
    @JsonProperty
    @Excel(name = "施工用电")
    private String constructionElectricity;
    /**
     * 字段描述：通讯网络
     */
    @JsonProperty
    @Excel(name = "通讯网络")
    private String communicationNetwork;
    /**
     * 字段描述：交通
     */
    @JsonProperty
    @Excel(name = "交通")
    private String traffic;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    private String fileGroupId;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    private String remark;
    /**
     * 字段描述：所属区域id
     */
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
