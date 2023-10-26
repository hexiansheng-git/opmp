package com.hhwy.pm.qqch.preparation.measureexp.beton.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhenglili
 * @date 2023-08-04 16:12:49
 * @remark 混凝土配合比导入实体
 */
@Data
public class QqchExpBetonImportVo extends TreeNode<QqchExpBetonImportVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 序号  导入用
     */
    @FtExcel(name = "序号", serialNumFlag = true)
    private String serialNum;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long pid;
    /**
     * 字段描述：配合比类型
     */
    @JsonProperty
    @FtExcel(name = "配合比类型")
    private String mixRatioType;
    /**
     * 字段描述：配合比名称
     */
    @JsonProperty
    @FtExcel(name = "配合比名称")
    private String mixRatioName;
    /**
     * 字段描述：坍落度
     */
    @JsonProperty
    @FtExcel(name = "坍落度")
    private String slumps;
    /**
     * 字段描述：不同工艺环境下配合比要求
     */
    @JsonProperty
    @FtExcel(name = "不同工艺环境下配合比要求")
    private String reqContent;
    /**
     * 字段描述：计划最早开工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划最早开工日期", dateFormat = "yyyy-MM-dd")
    private Date planDate;
    /**
     * 字段描述：所需数量
     */
    @JsonProperty
    @FtExcel(name = "使用数量")
    private BigDecimal usedNum;
    /**
     * 字段描述：拟完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "拟完成时间", dateFormat = "yyyy-MM-dd")
    private Date planFinishTime;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @FtExcel(name = "备注")
    private String remark;
}
