package com.hhwy.pm.qqch.wzch.priorpurchase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优先进场物资设备采购策划对象 wzch_prior_purchase
 *
 * @author mls
 * @date 2022-11-17
 */

@Data
@ToString
public class WzchPriorPurchase extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 1L;

    public WzchPriorPurchase() {
    }

    public WzchPriorPurchase(BigDecimal version) {
        super.setVersion(version);
    }

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String priorPurchaseCode;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    private String region;// 搜索用

    /** 所属区域id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 所属区域 */
    private String regionName;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 版本号 */
    @JsonIgnore // 不用返回给前端
    private BigDecimal versionCode;
    /**
     * 版本号 v1.0
     */
    @Excel(name = "版本号")
    private String versionCodeStr;

    /** 限价方案说明 */
    private String limitPriceDesc;

    /** 附件组id */
    private String fileGroupId;

    /** 项目id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;



    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 是否有效 1-有效 0-失效 */
    @Excel(name = "是否有效")
    private String valid;

    /** 数据创建者id */
    private String createUser;

    /** 数据创建者名称 */
    @Excel(name = "编制人")
    private String createUserName;

    @Excel(name = "编制时间",dateFormat = "yyyyMMdd HH:mm:dd")
    private Date createTime;

    @Excel(name = "流程状态")
    private String taskStatus;

    @Excel(name = "当前处理人")
    private String processTaskMan;


    /** 数据修改者id */
    private String updateUser;

    /** 数据修改者名称 */
    private String updateUserName;

    /** 数据删除者 */
    private String delUser;

    /** 数据删除系统时间 */
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag;



    /** 预留字段1 */
    private String ptVar1;

    /** 预留字段2 */
    private String ptVar2;

    /** 预留字段3 */
    private String ptVar3;

    /** 预留字段4 */
    private String ptVar4;

}
