package com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.utils.excel.FtExcel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author han
 * @date 2023-07-25 10:39:47
 * @remark 四新应用及创新计划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchAppInnovatePlanExportVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：四新”技术名称
     */
    @JsonProperty
    @FtExcel(name = "“四新”技术名称")
    private String technologyName;
    /**
     * 字段描述：应用/创新（字典项：app_or_innovate）
     */
    @JsonProperty
    @FtExcel(name = "应用/创新",dictType = "app_or_innovate")
    private String appOrInnovate;
    /**
     * 字段描述：四新”类型（字典项：four_news_type）
     */
    @JsonProperty
    @FtExcel(name = "“四新”类型",dictType = "four_news_type")
    private String type;
    /**
     * 字段描述：负责人（带联系方式）
     */
    private String director;
    /**
     * 字段描述：相关人员
     */
    @JsonProperty
    @FtExcel(name = "相关人员")
    private String relatedStaff;
    /**
     * 字段描述：开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "开始时间", dateFormat = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 字段描述：结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "结束时间", dateFormat = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @FtExcel(name = "备注")
    private String remark;
}
