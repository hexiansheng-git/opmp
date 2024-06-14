package com.hhwy.sp.designChangeList.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * wbs对象 sgjs_design_change_wbs
 * 
 * @author wk
 * @date 2024-04-16
 */
@Data
public class SgjsDesignChangeWbs extends TreeNode<SgjsDesignChangeWbs> {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 主表id */
    @FtExcel(name = "主表id")
    private Long mainId;

    /** 编号，单位工程按整百递增，分部分项子分项按三位流水号递增 */
    @FtExcel(name = "编号，单位工程按整百递增，分部分项子分项按三位流水号递增")
    private String code;

    /** 父级ID,最顶级为0 */
    @FtExcel(name = "父级ID,最顶级为0")
    private Long parentId;

    /** 是否包含子级，0:否,1:是 */
    @FtExcel(name = "是否包含子级，0:否,1:是")
    private Integer haveChildren;

    /** 祖级ID */
    @FtExcel(name = "祖级ID")
    private String ancestors;

    /** 祖级名称 */
    @FtExcel(name = "祖级名称")
    private String ancestorsName;

    /** 项目部位 */
    @FtExcel(name = "项目部位")
    private String partCode;

    /** 名称 */
    @FtExcel(name = "名称")
    private String name;

    /** 清单编码 */
    @FtExcel(name = "清单编码")
    private String listCode;

    /** 标准wbsId，预留 */
    @FtExcel(name = "标准wbsId，预留")
    private Long standardId;

    /** 标准wbs编码 */
    @FtExcel(name = "标准wbs编码")
    private String standardCode;

    /** 标准wbs名称 */
    @FtExcel(name = "标准wbs名称")
    private String standardName;

    /** 节点类型,字典:xmsl_wbs_type */
    @FtExcel(name = "节点类型,字典:xmsl_wbs_type")
    private String nodeType;

    /** 单位 */
    @FtExcel(name = "单位")
    private String unit;

    /** 层级 */
    @FtExcel(name = "层级")
    private Integer level;

    /** 状态,0:停用,1:启用 */
    @FtExcel(name = "状态,0:停用,1:启用")
    private Integer status;

    /** 设计量 */
    @FtExcel(name = "设计量")
    private BigDecimal designQuanlity;

    /** 复核量 */
    @FtExcel(name = "复核量")
    private BigDecimal checkQuanlity;

    /** 所属区域id */
    @FtExcel(name = "所属区域id")
    private Long regionId;

    /** 所属区域名称 */
    @FtExcel(name = "所属区域名称")
    private String regionName;

    /** 项目id */
    @FtExcel(name = "项目id")
    private Long projectId;

    /** 项目名称 */
    @FtExcel(name = "项目名称")
    private String projectName;

    /** 用户id */
    @FtExcel(name = "用户id")
    private Long userId;

    /** 部门id */
    @FtExcel(name = "部门id")
    private Long deptId;

    /** 数据创建者id */
    @FtExcel(name = "数据创建者id")
    private String createUser;

    /** 数据创建者名称 */
    @FtExcel(name = "数据创建者名称")
    private String createUserName;

    /** 数据修改者id */
    @FtExcel(name = "数据修改者id")
    private String updateUser;

    /** 数据删除者 */
    @FtExcel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @FtExcel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;

    /** 删除标识：0未删除；1已删除 */
    private String delFlag;

    /** 预留字段1 */
    @FtExcel(name = "预留字段1 加载状态，1:已加载,其他:未加载")
    private String ptVar1;

    /** null */
    @FtExcel(name = "预留字段2 类型,1:元数据/2:调整后")
    private String ptVar2;

    /** 预留字段3 */
    @FtExcel(name = "预留字段3")
    private String ptVar3;

    /** 预留字段4 */
    @FtExcel(name = "预留字段4")
    private String ptVar4;

    /** 预留字段5 */
    @FtExcel(name = "预留字段5")
    private String ptVar5;

    /** 序号 */
    @FtExcel(name = "序号")
    private Integer sort;

    /** 清单Id */
    @FtExcel(name = "清单Id")
    private String listIds;

    /** 当前层级编号 */
    @FtExcel(name = "当前层级编号")
    private String selfCode;

    /** 父级编号 */
    @FtExcel(name = "父级编号")
    private String parentCode;

    private List<SgjsDesignChangeWbs> children;
    //清单
    private List<SgjsDesignChangeList> list;
    
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mainId", getMainId())
            .append("code", getCode())
            .append("parentId", getParentId())
            .append("haveChildren", getHaveChildren())
            .append("ancestors", getAncestors())
            .append("ancestorsName", getAncestorsName())
            .append("partCode", getPartCode())
            .append("name", getName())
            .append("listCode", getListCode())
            .append("standardId", getStandardId())
            .append("standardCode", getStandardCode())
            .append("standardName", getStandardName())
            .append("nodeType", getNodeType())
            .append("unit", getUnit())
            .append("level", getLevel())
            .append("status", getStatus())
            .append("designQuanlity", getDesignQuanlity())
            .append("checkQuanlity", getCheckQuanlity())
            .append("remark", getRemark())
            .append("regionId", getRegionId())
            .append("regionName", getRegionName())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("createUser", getCreateUser())
            .append("createUserName", getCreateUserName())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateTime", getUpdateTime())
            .append("delUser", getDelUser())
            .append("delTime", getDelTime())
            .append("delFlag", getDelFlag())
            .append("ptVar1", getPtVar1())
            .append("ptVar2", getPtVar2())
            .append("ptVar3", getPtVar3())
            .append("ptVar4", getPtVar4())
            .append("ptVar5", getPtVar5())
            .append("sort", getSort())
            .append("listIds", getListIds())
            .append("selfCode", getSelfCode())
            .append("parentCode", getParentCode())
            .toString();
    }
}
