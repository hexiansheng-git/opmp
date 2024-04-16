package com.hhwy.sp.designChangeList.domain;

import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
public class SgjsDesignChangeList extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 主表id */
    @FtExcel(name = "主表id")
    private Long mainId;

    /** 类型,1:元数据/2:调整后 */
    @FtExcel(name = "类型,1:元数据/2:调整后")
    private Integer type;

    /** 清单编码,xmsl_contract_list.code */
    @FtExcel(name = "清单编码,xmsl_contract_list.code")
    private String listCode;

    /** 父id */
    @FtExcel(name = "父id")
    private Long pid;

    /** 主合同清单ID,xmsl_contract_list.id */
    @FtExcel(name = "主合同清单ID,xmsl_contract_list.id")
    private Long listId;

    /** wbsId */
    @FtExcel(name = "wbsId")
    private Long wbsId;

    /** wbs编码 */
    @FtExcel(name = "wbs编码")
    private String wbsCode;

    /** 祖籍id集合 */
    @FtExcel(name = "祖籍id集合")
    private String ancestors;

    /** 清单中文名称 */
    @FtExcel(name = "清单中文名称")
    private String chineseName;

    /** 清单外文名称 */
    @FtExcel(name = "清单外文名称")
    private String foreignName;

    /** 清单类型(字典项（sp_list_type）) */
    @FtExcel(name = "清单类型(字典项", readConverterExp = "sp_list_type")
    private String listType;

    /** 单位编码 */
    @FtExcel(name = "单位编码")
    private String unitCode;

    /** 单位 */
    @FtExcel(name = "单位")
    private String unit;

    /** 合同总数量 */
    @FtExcel(name = "合同总数量")
    private BigDecimal conNum;

    /** 合同单价不含税 */
    @FtExcel(name = "合同单价不含税")
    private BigDecimal conExcludePrice;

    /** 合同单价含税 */
    @FtExcel(name = "合同单价含税")
    private BigDecimal conIncludePrice;

    /** 0#工程量清单 */
    @FtExcel(name = "0#工程量清单")
    private BigDecimal zeroNum;

    /** 0#单价不含税 */
    @FtExcel(name = "0#单价不含税")
    private BigDecimal zeroExcludePrice;

    /** 0#单价含税 */
    @FtExcel(name = "0#单价含税")
    private BigDecimal zeroIncludePrice;

    /** 变更前-工程量清单 */
    @FtExcel(name = "变更前-工程量清单")
    private BigDecimal beforeNum;

    /** 变更前-单价不含税 */
    @FtExcel(name = "变更前-单价不含税")
    private BigDecimal beforeExcludePrice;

    /** 变更前-单价含税 */
    @FtExcel(name = "变更前-单价含税")
    private BigDecimal beforeIncludePrice;

    /** 当前工程量清单 */
    @FtExcel(name = "当前工程量清单")
    private BigDecimal changeNum;

    /** 当前单价不含税 */
    @FtExcel(name = "当前单价不含税")
    private BigDecimal changeExcludePrice;

    /** 当前单价含税 */
    @FtExcel(name = "当前单价含税")
    private BigDecimal changeIncludePrice;

    /** 变更后-工程量清单 */
    @FtExcel(name = "变更后-工程量清单")
    private BigDecimal afterNum;

    /** 变更后-单价不含税 */
    @FtExcel(name = "变更后-单价不含税")
    private BigDecimal afterExcludePrice;

    /** 变更后-单价含税 */
    @FtExcel(name = "变更后-单价含税")
    private BigDecimal afterIncludePrice;

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
    private Date delTime;

    /** 删除标识：0未删除；1已删除 */
    private String delFlag;

    /** 预留字段1 */
    @FtExcel(name = "预留字段1")
    private String ptVar1;

    /** 预留字段2 */
    @FtExcel(name = "预留字段2")
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
            .append("conIncludePrice", getConIncludePrice())
            .append("zeroNum", getZeroNum())
            .append("zeroExcludePrice", getZeroExcludePrice())
            .append("zeroIncludePrice", getZeroIncludePrice())
            .append("beforeNum", getBeforeNum())
            .append("beforeExcludePrice", getBeforeExcludePrice())
            .append("beforeIncludePrice", getBeforeIncludePrice())
            .append("changeNum", getChangeNum())
            .append("changeExcludePrice", getChangeExcludePrice())
            .append("changeIncludePrice", getChangeIncludePrice())
            .append("afterNum", getAfterNum())
            .append("afterExcludePrice", getAfterExcludePrice())
            .append("afterIncludePrice", getAfterIncludePrice())
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
