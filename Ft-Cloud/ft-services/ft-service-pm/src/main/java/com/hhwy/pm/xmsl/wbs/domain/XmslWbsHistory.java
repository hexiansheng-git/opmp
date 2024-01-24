package com.hhwy.pm.xmsl.wbs.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wk
 * @date 2023-07-14 11:09:09
 * @remark
 */
@Data
public class XmslWbsHistory extends XmslWbs {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    private String id;
    /**
     * 字段描述：编号，单位工程按整百递增，分部分项子分项按三位流水号递增
     */
    @JsonProperty
    @FtExcel(name = "编号")
    @NotBlank(message = "编号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @ExcelProperty("编号")
    protected String code;

    @JsonProperty
    @FtExcel(name = "项目部位（桩号）")
    @NotBlank(message = "项目部位不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @ExcelProperty("项目部位（桩号）")
    protected String partCode;

    /**
     * 字段描述：标准wbs编码
     */
    @JsonProperty
    @FtExcel(name = "关联标准WBS")
    protected String standardCode;

    /**
     * 字段描述：名称 实际是 桩号-wbs名称
     */
    @JsonProperty
    @FtExcel(name = "标准WBS名称")
    @NotBlank(message = "标准WBS名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @ExcelProperty("标准WBS名称")
    protected String name;
    /**
     * 字段描述：节点类型,字典:xmsl_wbs_type
     */
    @JsonProperty
    @FtExcel(name = "节点类型",dictType = "xmsl_wbs_type")
    @NotBlank(message = "节点类型不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    protected String nodeType;

    /**
     * 字段描述：状态,0:停用,1:启用
     */
    @JsonProperty
    @FtExcel(name = "启用/禁用",combo = {"启用","停用"},readConverterExp = "0=停用,1=启用")
    protected Integer status;

    /**
     * 字段描述：清单编码
     */
    @JsonProperty
    @FtExcel(name = "关联清单编号")
    protected String listCode;


}
