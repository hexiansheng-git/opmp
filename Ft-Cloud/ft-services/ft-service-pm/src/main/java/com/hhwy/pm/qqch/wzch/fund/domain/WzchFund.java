package com.hhwy.pm.qqch.wzch.fund.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金策划对象 wzch_fund
 *
 * @author mls
 * @date 2022-12-08
 */

@Data
@ToString
public class WzchFund extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 1L;

    public WzchFund(BigDecimal version) {
        super.setVersion(version);
    }
    public WzchFund() {
    }

        /**
         * id
         */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 单据编号
     */
    @Excel(name = "单据编号")
    private String fundCode;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 所属区域id
     */

    private String region;// 搜索用

    @JsonSerialize(using = ToStringSerializer.class)
    private Long regionId;

    /**
     * 地域名
     */
    @Excel(name = "地域名")
    private String regionName;

    /**
     * 版本号
     */
    @Excel(name = "版本号")
    private BigDecimal versionCode;

    /**
     * 限价方案说明
     */
    @Excel(name = "限价方案说明")
    private String limitPriceDesc;

    /**
     * 文件id
     */
    @Excel(name = "文件id")
    private String fileGroupId;

    /**
     * 项目id
     */
    @Excel(name = "项目id")

    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    private String projectName;

    /**
     * 部门id
     */
    @Excel(name = "部门id")

    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    /**
     * 数据创建者id
     */
    @Excel(name = "数据创建者id")
    private String createUser;

    /**
     * 数据创建者名称
     */
    @Excel(name = "数据创建者名称")
    private String createUserName;

    /**
     * 数据修改者id
     */
    @Excel(name = "数据修改者id")
    private String updateUser;

    /**
     * 数据修改者名称
     */
    @Excel(name = "数据修改者名称")
    private String updateUserName;

    /**
     * 数据删除者
     */
    @Excel(name = "数据删除者")
    private String delUser;

    /**
     * 数据删除系统时间
     */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /**
     * 删除标记: 0-未删除 1-已删除
     */
    private String delFlag;

    /**
     * 是否有效 1-有效 0-失效
     */
    @Excel(name = "是否有效 1-有效 0-失效")
    private String valid;

    /**
     * 预留字段1
     */
    @Excel(name = "预留字段1")
    private String ptVar1;

    /**
     * 预留字段2
     */
    @Excel(name = "预留字段2")
    private String ptVar2;

    /**
     * 预留字段3
     */
    @Excel(name = "预留字段3")
    private String ptVar3;

    /**
     * 预留字段4
     */
    @Excel(name = "预留字段4")
    private String ptVar4;

}
