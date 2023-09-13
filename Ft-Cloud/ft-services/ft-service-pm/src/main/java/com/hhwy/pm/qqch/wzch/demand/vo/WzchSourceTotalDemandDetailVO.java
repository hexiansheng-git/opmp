package com.hhwy.pm.qqch.wzch.demand.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.qqch.wzch.source.vo.ReminderOfChangeDetailResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 来源策划物资下总需详情对象
 * @author HCT
 */
@Data
public class WzchSourceTotalDemandDetailVO implements Serializable {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private String materialCode;
    /** 技术参数 */
    private String materialTechParam;

    /** 执行标准 */
    private String materialStandard;

    /** 总需用量 */
    private BigDecimal totalDemandAmount;

    /** 自采需用量 */
    private BigDecimal selfDemandAmount;

    /** 非自采量 */
    private BigDecimal nonSelfAmount;

    /** 原总需用量 */
    private BigDecimal backTotalDemandAmount;

    /** 原自采需用量 */
    private BigDecimal backSelfDemandAmount;

    /** 原非自采量 */
    private BigDecimal backNonSelfAmount;

    /** 类型(t_material_category的category_name) */
    private String categoryName;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 物资设备名称 */
    @Excel(name = "物资名称")
    private String materialName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String materialSpec;

    //删除标识 1-已删除 0-新增 2-修改
    private String handleType;

    /**
     * 新增年
     */
    private List<String> yesrList;

    private List<ReminderOfChangeDetailResponse> detailResponses;

}
