package com.hhwy.sp.designChangeList.domain;

import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.tree.TreeNode;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 设计变更清单对象 sgjs_design_change_list
 * 
 * @author wk
 * @date 2024-04-16
 */
@Data
public class SgjsDesignChangeList extends TreeNode<SgjsDesignChangeList> {
    private static final long serialVersionUID = 1L;

    /** id */
    protected Long id;

    /** 主表id */
    private Long mainId;

    /** 类型,1:元数据/2:调整后 */
    private Integer type;
    
    /** wbs编码 */
    @FtExcel(name = "wbs编码",type = FtExcel.Type.EXPORT)
    private String wbsCode;

    @FtExcel(name = "wbs名称",type = FtExcel.Type.EXPORT)
    private String wbsName;

    /** 清单编码,xmsl_contract_list.code */
    @FtExcel(name = "清单编号")
    @NotBlank(message = "清单编号不能为空",groups = {ValidationGroups.Other.class})
    private String listCode;

    /** 父id */
    protected Long pid;

    /** 主合同清单ID,xmsl_contract_list.id */
    private Long listId;

    /** wbsId */
    private Long wbsId;

    /** 祖籍id集合 */
    private String ancestors;

    /** 清单中文名称 */
    @FtExcel(name = "清单中文名称")
    @NotBlank(message = "清单中文名称不能为空",groups = {ValidationGroups.Other.class})
    private String chineseName;

    /** 清单外文名称 */
    @FtExcel(name = "清单外文名称")
    private String foreignName;

    /** 清单类型(字典项（sp_list_type）) */
    @FtExcel(name = "清单类型", dictType = "sp_list_type")
    @NotBlank(message = "清单类型不能为空",groups = {ValidationGroups.Other.class})
    private String listType;

    /** 单位编码 */
    private String unitCode;

    /** 单位 */
    @FtExcel(name = "单位")
    @NotBlank(message = "单位不能为空",groups = {ValidationGroups.Other.class})
    private String unit;

    /** 合同总数量 */
    @FtExcel(name = "合同总数量",type = FtExcel.Type.EXPORT)
    private BigDecimal conNum;

    /** 合同单价不含税 */
    @FtExcel(name = "合同单价不含税",type = FtExcel.Type.EXPORT)
    private BigDecimal conExcludePrice;

    /** 合同单价含税 */
    @FtExcel(name = "合同单价含税",type = FtExcel.Type.EXPORT)
    private BigDecimal conSumPrice;

    /** 0#工程量清单 */
    @FtExcel(name = "0#工程量清单-数量",type = FtExcel.Type.EXPORT)
    private BigDecimal zeroNum;

    /** 0#单价不含税 */
    @FtExcel(name = "0#工程量清单-单价（不含税）",type = FtExcel.Type.EXPORT)
    private BigDecimal zeroExcludePrice;

    /** 0#单价含税 */
    @FtExcel(name = "0#工程量清单-金额（不含税）",type = FtExcel.Type.EXPORT)
    private BigDecimal zeroSumPrice;

    /** 本次变更前累计-工程量清单 */
    @FtExcel(name = "本次变更前累计-数量",type = FtExcel.Type.EXPORT)
    private BigDecimal beforeNum;

    /** 本次变更前累计-单价不含税 */
    @FtExcel(name = "本次变更前累计-单价不含税",type = FtExcel.Type.EXPORT)
    private BigDecimal beforeExcludePrice;

    /** 本次变更前累计-单价含税 */
    @FtExcel(name = "本次变更前累计-单价含税",type = FtExcel.Type.EXPORT)
    private BigDecimal beforeSumPrice;

    /** 当前工程量清单 */
    @FtExcel(name = "本次变更数量")
    @NotNull(message = "本次变更数量不能为空",groups = {ValidationGroups.Other.class})
    private BigDecimal changeNum;

    /** 当前单价不含税 */
    @FtExcel(name = "本次变更单价（不含税）",width = 21L)
    @NotNull(message = "本次变更单价（不含税）不能为空",groups = {ValidationGroups.Other.class})
    private BigDecimal changeExcludePrice;

    /** 当前单价含税 */
    @FtExcel(name = "本次变更金额（不含税）",width = 21L)
    @NotNull(message = "本次变更金额（不含税）不能为空",groups = {ValidationGroups.Other.class})
    private BigDecimal changeSumPrice;

    /** 变更后-工程量清单 */
    @FtExcel(name = "变更后-工程量清单",type = FtExcel.Type.EXPORT)
    private BigDecimal afterNum;

    /** 变更后-单价不含税 */
    @FtExcel(name = "变更后-单价不含税",type = FtExcel.Type.EXPORT)
    private BigDecimal afterExcludePrice;

    /** 变更后-单价含税 */
    @FtExcel(name = "变更后-单价含税",type = FtExcel.Type.EXPORT)
    private BigDecimal afterSumPrice;

    /** 数据创建者id */
    private String createUser;

    /** 数据创建者名称 */
    private String createUserName;

    /** 数据修改者id */
    private String updateUser;

    /** 数据删除者 */
    private String delUser;

    /** 数据删除系统时间 */
    private Date delTime;

    /** 删除标识：0未删除；1已删除 */
    private String delFlag;

    /** 预留字段1 */
    private String ptVar1;

    /** 预留字段2 */
    private String ptVar2;

    /** 预留字段3 */
    private String ptVar3;

    /** 预留字段4 */
    private String ptVar4;

    /** 预留字段5 */
    private String ptVar5;

    private List<SgjsDesignChangeList> children;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mainId", getMainId())
            .append("type", getType())
            .append("listCode", getListCode())
            .append("pid", getPid())
            .append("listId", getListId())
            .append("wbsId", getWbsId())
            .append("wbsCode", getWbsCode())
            .append("ancestors", getAncestors())
            .append("chineseName", getChineseName())
            .append("foreignName", getForeignName())
            .append("listType", getListType())
            .append("unitCode", getUnitCode())
            .append("unit", getUnit())
            .append("conNum", getConNum())
            .append("conExcludePrice", getConExcludePrice())
            .append("conSumPrice", getConSumPrice())
            .append("zeroNum", getZeroNum())
            .append("zeroExcludePrice", getZeroExcludePrice())
            .append("zeroSumPrice", getZeroSumPrice())
            .append("beforeNum", getBeforeNum())
            .append("beforeExcludePrice", getBeforeExcludePrice())
            .append("beforeSumPrice", getBeforeSumPrice())
            .append("changeNum", getChangeNum())
            .append("changeExcludePrice", getChangeExcludePrice())
            .append("changeSumPrice", getChangeSumPrice())
            .append("afterNum", getAfterNum())
            .append("afterExcludePrice", getAfterExcludePrice())
            .append("afterSumPrice", getAfterSumPrice())
            .append("remark", getRemark())
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
            .toString();
    }
}
