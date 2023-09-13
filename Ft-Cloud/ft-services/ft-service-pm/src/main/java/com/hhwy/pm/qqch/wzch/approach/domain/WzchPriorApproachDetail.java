package com.hhwy.pm.qqch.wzch.approach.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 优先进场物资详情对象 wzch_prior_approach_detail
 * 
 * @author mls
 * @date 2022-11-30
 */
@Data
public class WzchPriorApproachDetail extends MyPrepareBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 优先进场id */
    @Excel(name = "优先进场id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long priorApproachId;

    /** 物资编码 */
    @Excel(name = "物资编码")
    private String materialCode;

    /** 技术参数 */
    @Excel(name = "技术参数")
    private String materialTechParam;

    /** 执行标准 */
    @Excel(name = "执行标准")
    private String materialStandard;

    /** 总需用量 */
    
    @Excel(name = "总需用量")
    private BigDecimal totalDemandAmount;

    /** 自采需用量 */
    
    @Excel(name = "自采需用量")
    private BigDecimal selfDemandAmount;

    /** 优先到场数量 */
    
    @Excel(name = "优先到场数量")
    private BigDecimal priorApproachNum;

    /** 最早需用日期 */
    @Excel(name = "最早需用日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date earliestReqTime;

    /** 要求到场日期 */
    @Excel(name = "要求到场日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date presentTime;

    /** 附件组id */
    @Excel(name = "附件组id")
    private String fileGroupId;

    /** 项目id */
    @Excel(name = "项目id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 部门id */
    @Excel(name = "部门id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 数据创建者id */
    @Excel(name = "数据创建者id")
    private String createUser;

    /** 数据创建者名称 */
    @Excel(name = "数据创建者名称")
    private String createUserName;

    /** 数据修改者id */
    @Excel(name = "数据修改者id")
    private String updateUser;

    /** 数据修改者名称 */
    @Excel(name = "数据修改者名称")
    private String updateUserName;

    /** 数据删除者 */
    @Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 删除标记: 0-未删除 1-已删除 */
    private String delFlag;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String ptVar1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String ptVar2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private String ptVar3;

    /** 物资名称 */
    @Excel(name = " 物资名称")
    private String materialName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String materialSpec;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 执行标准 */
    @Excel(name = "类型")
    private String categoryName;

    private List<String> yearList;

    private String orderNo;

    private List<WzchPriorApproachYearCount> wzchPriorApproachYearCountList;

    public WzchPriorApproachDetail(Long priorApproachId) {
        this.priorApproachId = priorApproachId;
    }

    public WzchPriorApproachDetail() {
    }
}
