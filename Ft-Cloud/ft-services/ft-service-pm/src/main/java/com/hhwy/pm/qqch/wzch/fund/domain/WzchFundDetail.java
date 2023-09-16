package com.hhwy.pm.qqch.wzch.fund.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 资金策划详情对象 wzch_fund_detail
 *
 * @author mls
 * @date 2022-12-08
 */
@Data
@ToString
public class WzchFundDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 资金策划id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long fundId;

    /**
     * 资金策划事项
     */
    @FtExcel(name = "资金策划事项")
    private String fundPlanItems;

    /**
     * 要求内容
     */
    @FtExcel(name = "要求内容")
    private String reqContents;

    /**
     * 实施措施
     */
    @FtExcel(name = "实施措施")
    private String implMeasure;

    /**
     * 是否预警(1-是,0-否)
     */
    @FtExcel(name = "是否预警",dictType = "warn_flag")
    private String warnFlag;
    private String warnFlagName;

    @FtExcel(name = "备注")
    private String remark;


    /**
     * 文件id
     */
    private String fileGroupId;

    /**
     * 项目id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 部门id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    /**
     * 数据创建者id
     */
    private String createUser;

    /**
     * 数据创建者名称
     */
    private String createUserName;

    /**
     * 数据修改者id
     */
    private String updateUser;

    /**
     * 数据修改者名称
     */
    private String updateUserName;

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

    /**
     * 是否有效 1-有效 0-失效
     */
    private String valid;

    /**
     * 预留字段1
     */
    private String ptVar1;

    /**
     * 预留字段2
     */
    private String ptVar2;

    /**
     * 预留字段3
     */
    private String ptVar3;

}
