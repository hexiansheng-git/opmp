package com.hhwy.pm.xmsl.implement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import java.util.Date;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:53
 * @remark 当地资源供应-属地物资供应情况
 */
@Data
public class XmslLocalMaterialsSupply extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 字段描述：名称
     */
    @JsonProperty
    @Excel(name = "名称")
    private String name;
    /**
     * 字段描述：规格
     */
    @JsonProperty
    @Excel(name = "规格")
    private String standards;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：价格
     */
    @JsonProperty
    @Excel(name = "价格")
    private String price;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
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
