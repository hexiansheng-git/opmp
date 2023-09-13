package com.hhwy.pm.qqch.wzch.source.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 来源策划物资详情对象 wzch_source_detail
 * 
 * @author mls
 * @date 2022-11-21
 */
@Data
public class WzchSourceDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 来源策划id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long sourceId;

    /** 物资编码 */
    @ExcelProperty( "物资编码")
    private String materialCode;


    /** 物资设备名称 */
    @ExcelProperty( "物资名称")
    private String materialName;

    /** 规格型号 */
    @ExcelProperty( "规格型号")
    private String materialSpec;

    /** 技术参数 */
    @ExcelProperty( "技术参数")
    private String materialTechParam;

    /** 执行标准 */
    @ExcelProperty( "执行标准")
    private String materialStandard;

    /** 单位 */
    @ExcelProperty( "单位")
    private String unit;

    /** 总需用量 */
    @ExcelProperty( "总需用量")
    private BigDecimal totalDemandAmount;

    /** 原总需用量 */
    private BigDecimal backTotalDemandAmount;

    /** 自采需用量 */
    
    @ExcelProperty( "自采需用量")
    private BigDecimal selfDemandAmount;

    /** 类型(t_material_category的category_name) */
    @Excel(name = "类型(t_material_category的category_name)")
    private String categoryName;

    /** 原自采需用量 */
    
    private BigDecimal backSelfDemandAmount;

    /** 非自采量 */
   // @ExcelProperty( "非自采量")
    
    private BigDecimal nonSelfAmount;
    /** 原非自采量 */
    
    private BigDecimal backNonSelfAmount;

    /** 附件组id */
    private String fileGroupId;

    /** 项目id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    /** 项目名称 */
    private String projectName;

    /** 部门id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 数据创建者id */
    private String createUser;

    /** 数据创建者名称 */
    @ExcelProperty( "编制人")
    private String createUserName;

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

    private List<WzchSourceApproachYearCount> wzchSourceApproachYearCountList;

    private List<String> yearList;

    private String valid;

    private String orderNo;
    
    private BigDecimal version;

    public WzchSourceDetail() {
    }

    public WzchSourceDetail(String materialCode, String materialName, String materialSpec, String materialTechParam, String materialStandard, String unit, BigDecimal totalDemandAmount, BigDecimal selfDemandAmount, String categoryName) {
        this.materialCode = materialCode;
        this.materialName = materialName;
        this.materialSpec = materialSpec;
        this.materialTechParam = materialTechParam;
        this.materialStandard = materialStandard;
        this.unit = unit;
        this.totalDemandAmount = totalDemandAmount;
        this.selfDemandAmount = selfDemandAmount;
        this.categoryName = categoryName;
    }

    public WzchSourceDetail(List<WzchSourceApproachYearCount> wzchSourceApproachYearCountList) {
        this.wzchSourceApproachYearCountList = wzchSourceApproachYearCountList;
    }

    public WzchSourceDetail(Long projectId) {
        this.projectId = projectId;
    }
}
