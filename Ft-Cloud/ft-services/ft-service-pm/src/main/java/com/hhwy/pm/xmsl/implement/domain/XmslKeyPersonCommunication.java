package com.hhwy.pm.xmsl.implement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import java.util.Date;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-03 13:13:27
 * @remark 重要干系人识别及沟通
 */
@Data
public class XmslKeyPersonCommunication extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonProperty
    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：重要干系人
     */
    @JsonProperty
    @Excel(name = "重要干系人")
    private String keyPerson;
    /**
     * 字段描述：项目中的角色
     */
    @JsonProperty
    @Excel(name = "项目中的角色")
    private String projectRole;
    /**
     * 字段描述：对项目影响程度
     */
    @JsonProperty
    @Excel(name = "对项目影响程度")
    private String projectImpactDegree;
    /**
     * 字段描述：合同界面责任
     */
    @JsonProperty
    @Excel(name = "合同界面责任")
    private String contractDuty;
    /**
     * 字段描述：沟通重点
     */
    @JsonProperty
    @Excel(name = "沟通重点")
    private String communicationFocus;
    /**
     * 字段描述：沟通频率（日、周、月）
     */
    @JsonProperty
    @Excel(name = "沟通频率（日、周、月）")
    private String communicationFrequency;
    /**
     * 字段描述：沟通途径（口头、会议、书面）
     */
    @JsonProperty
    @Excel(name = "沟通途径（口头、会议、书面）")
    private String communicationChannels;
    /**
     * 字段描述：责任人
     */
    @JsonProperty
    @Excel(name = "责任人")
    private String responsiblePerson;
    /**
     * 字段描述：所属区域id
     */
    @JsonProperty
    @Excel(name = "所属区域id")
    private Integer regionId;
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
    private Integer projectId;
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
    private Integer deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @Excel(name = "数据创建者id")
    private String createUser;
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
