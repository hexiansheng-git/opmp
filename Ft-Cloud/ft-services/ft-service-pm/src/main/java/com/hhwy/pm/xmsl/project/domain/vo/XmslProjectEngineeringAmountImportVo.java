package com.hhwy.pm.xmsl.project.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @author han
 * @date 2023-07-03 09:48:34
 * @remark 主要工程数量导入类
 */
@Data
public class XmslProjectEngineeringAmountImportVo {
    private static final long serialVersionUID = 1L;

    @Excel(name = "层级码")
    private String innerCode;
    @Excel(name = "父层级码")
    private String parentInnerCode;

    /**
     * 字段描述：树id
     */
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long treeId;
    /**
     * 字段描述：父id
     */
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentTreeId;
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
