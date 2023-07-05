package com.hhwy.pm.xmsl.project.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import lombok.Data;

/**
 * @author han
 * @date 2023-07-03 09:48:34
 * @remark 主要工程数量导出类
 */
@Data
public class XmslProjectEngineeringAmountExportVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：工程项目
     */
    @JsonProperty
    @Excel(name = "工程项目")
    private String engineeringProject;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String units;
    /**
     * 字段描述：数量
     */
    @JsonProperty
    @Excel(name = "数量")
    private String amount;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
}
