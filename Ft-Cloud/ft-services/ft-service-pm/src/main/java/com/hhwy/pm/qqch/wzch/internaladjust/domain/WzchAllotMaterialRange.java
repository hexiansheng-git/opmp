package com.hhwy.pm.qqch.wzch.internaladjust.domain;

import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 可调拨材料可调拨范围(WzchAllotMaterialRange)实体类
 *
 * @author makejava
 * @since 2023-02-03 16:17:01
 */
@Data
@ToString
public class WzchAllotMaterialRange extends CommonBaseEntity {
    private static final long serialVersionUID = -13674936296804272L;
    /**
     * id
     */
    private Long id;
    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 可调拨范围(1-全区域，2-指定国家)
     */
    private String adjustRange;

    // 国家编码
    private String countryCodes;
    private List<String> countryCodeList;
    /**
     * 项目id集合(该物资可调拨的项目)
     */
    private String projectIds;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 数据创建者id
     */
    private String createUser;
    /**
     * 数据创建系统时间
     */
    private Date createTime;
    /**
     * 数据修改者id
     */
    private String updateUser;
    /**
     * 数据修改系统时间
     */
    private Date updateTime;
    /**
     * 数据删除者
     */
    private String delUser;
    /**
     * 数据删除系统时间
     */
    private Date delTime;
    /**
     * 删除标记: 0-未删除 1-已删除
     */
    private String delFlag;

}

