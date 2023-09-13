package com.hhwy.pm.qqch.wzch.demand.domain;

import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;

/**
 * 物资总需用详情对象 wzch_total_demand_detail
 * 
 * @author mls
 * @date 2022-11-15
 */
@Data
@ToString
@NoArgsConstructor
public class WzchTotalDemandDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /** 物资总需id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long totalDemandId;

    /** 物资编码 */
    @NotBlank(message = "物资编码不能为空",groups = {ValidationGroups.Save.class})
    @FtExcel(name = "物资编码")
    private String materialCode;

    /** 技术参数 */
    @FtExcel(name = "技术参数")
    private String materialTechParam;

    /** 执行标准 */
    @NotBlank(message = "执行标准不能为空",groups = {ValidationGroups.Save.class})
    @FtExcel(name = "执行标准", dictType = "material_standard")
    private String materialStandard;

    /** 总需用量 */
    
    @FtExcel(name = "总需用量")
    private BigDecimal totalDemandAmount;
    /** 原总需用量 */
    
    private BigDecimal backTotalDemandAmount;
    /** 自采需用量 */
    
    @FtExcel(name = "自采需用量")
    private BigDecimal selfDemandAmount;
    /** 原自采需用量 */
    
    private BigDecimal backSelfDemandAmount;

    /** 非自采量 */
    
    @FtExcel(name = "非自采量")
    private BigDecimal nonSelfAmount;
    /** 原非自采量 */
    
    private BigDecimal backNonSelfAmount;

    /** 类型(t_material_category的category_name) */
    @NotBlank(message = "类型不能为空",groups = {ValidationGroups.Save.class})
    @FtExcel(name = "类型", dictType = "total_demand_category_name")
    private String categoryName;

    /** 是否优先进场:0-否;1-是 */
    @FtExcel(name = "是否优先进场",dictType = "warn_flag")
    private String firstEnterFlag;

    /** 业主合同相关技术标准要求 */
    @FtExcel(name = "业主合同相关技术标准要求")
    private String contStandard;

    /** 资源调查 */
    @FtExcel(name = "资源调查")
    private String resourceSurvey;

    /** 附件组id */
    @FtExcel(name = "附件")
    private String fileGroupId;

    /** 项目id */

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long projectId;

    /** 项目名称 */
    private String projectName;

    /** 部门id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;

    /** 数据创建者id */
    private String createUser;

    /** 数据创建者名称 */
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
    private String delFlag = "0";

    /** 预留字段1 */
    private String ptVar1;

    /** 预留字段2 */
    private String ptVar2;

    /** 预留字段3 */
    private String ptVar3;

    private Integer pageNum;

    private Integer pageSize;
    
    private BigDecimal version;

    List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCountList;

    /** 单位 */
    @FtExcel(name = "单位")
    private String unit;

    /** 物资设备名称 */
    @FtExcel(name = "物资名称")
    private String materialName;

    /** 规格型号 */
    @FtExcel(name = "规格型号")
    private String materialSpec;


    public WzchTotalDemandDetail(Long totalDemandId) {
        this.totalDemandId = totalDemandId;
    }

    /** 年度视角-Y，季度视角-Q 月份视角-M */
    private String type = "Y";

    private List<String> viewYearList;

    /**
     *  是否有效  1-是 0-否
     */
    private String valid;

    private Date validDate;

    /** 计划开始时间 */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date planStartTime;

    /** 计划结束时间 */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date planEndTime;

    /**
     * 行号
     */
    private Integer orderNo;

    public WzchTotalDemandDetail(String valid,Long projectId) {
        this.valid = valid;
        this.projectId = projectId;
    }

    public WzchTotalDemandDetail( String valid,String firstEnterFlag, Long projectId) {
        this.valid = valid;
        this.firstEnterFlag = firstEnterFlag;
        this.projectId = projectId;
    }




}
