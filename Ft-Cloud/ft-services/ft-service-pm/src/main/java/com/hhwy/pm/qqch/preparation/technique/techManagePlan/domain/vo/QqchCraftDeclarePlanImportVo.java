package com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.excel.FtExcel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author han
 * @date 2023-07-25 10:40:02
 * @remark 工艺工法申报计划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchCraftDeclarePlanImportVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：区域中心
     */
    @JsonProperty
    @FtExcel(name = "区域中心")
    private String regionalCenter;
    /**
     * 字段描述：工艺工法名称
     */
    @JsonProperty
    @FtExcel(name = "工艺工法名称")
    private String craftName;
    /**
     * 字段描述：工法等级（字典项：craft_grade）
     */
    @JsonProperty
    @FtExcel(name = "工法等级",dictType = "craft_grade")
    private String craftGrade;
    /**
     * 字段描述：工法简介
     */
    @JsonProperty
    @FtExcel(name = "工法简介")
    private String craftIntro;
    /**
     * 字段描述：拟报送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "拟报送时间", dateFormat = "yyyy-MM-dd")
    private Date simulateSubmissionTime;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @FtExcel(name = "备注")
    private String remark;
}
