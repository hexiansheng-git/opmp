package com.hhwy.pm.qqch.preparation.measureexp.beton.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.tree.TreeNode;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

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
     * 字段描述：所属WBS编码
     */
    @JsonProperty
    private String wbsCode;
    /**
     * 字段描述：所属WBS名称
     */
    @JsonProperty
    private String wbsName;
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
    @FtExcel(name = "所需数量")
    private BigDecimal usedNum;
    /**
     * 字段描述：拟完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @FtExcel(name = "拟完成时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date planFinishTime;
    /**
     * 字段描述：排序
     */
    @JsonProperty
    private Integer sort;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @FtExcel(name = "备注")
    private String remark;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    private BigDecimal version;
    /**
     * 字段描述：是否有效 1-有效 0-失效
     */
    @JsonProperty
    private String valid;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    private String ptVar5;
}
