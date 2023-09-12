package com.hhwy.pm.xmsl.drawReview.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.core.base.WarpBaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 图纸复核-清单
 * @author wk
 * @date 2023-08-07 11:35:12
 * @remark xmsl_draw_review_list
 */
@Data
public class XmslDrawReviewList extends WarpBaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 字段描述：清单编码,xmsl_contract_list.code
     */
    @JsonProperty
    @Excel(name = "清单编码,xmsl_contract_list.code")
    private String listCode;
    /**
     * 字段描述：主表ID,xmsl_draw_review.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主表ID,xmsl_draw_review.id")
    private Long mainId;
    /**
     * 字段描述：清单ID,xmsl_contract_list.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "清单ID,xmsl_contract_list.id")
    private Long listId;
    /**
     * 字段描述：引用主合同清单ID,xmsl_contract_list.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long wbsId;
    
    //wbs编号
    private String wbsCode;
    
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父id")
    private Long pid;
    //版本号
    private Integer version;
    //是否为最新版本
    private Integer versionFlag;
    /**
     * 字段描述：祖籍id集合
     */
    @JsonProperty
    @Excel(name = "祖籍id集合")
    private String ancestors;
    /**
     * 字段描述：清单中文名称
     */
    @JsonProperty
    @Excel(name = "清单中文名称")
    private String chineseName;
    /**
     * 字段描述：清单外文名称
     */
    @JsonProperty
    @Excel(name = "清单外文名称")
    private String foreignName;
    /**
     * 字段描述：清单类型(字典项（list_type）)
     */
    @JsonProperty
    @Excel(name = "清单类型(字典项（list_type）)")
    private String listType;
    /**
     * 字段描述：单位编码
     */
    @JsonProperty
    @Excel(name = "单位编码")
    private String unitCode;
    /**
     * 字段描述：单位
     */
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：合同总数量
     */
    @JsonProperty
    @Excel(name = "合同总数量")
    private BigDecimal winNum;
    /**
     * 字段描述：复核量
     */
    @JsonProperty
    @Excel(name = "复核量")
    private BigDecimal checkNum;
    /**
     * 字段描述：形象进度单元,0:否,1:是
     */
    @JsonProperty
    @Excel(name = "形象进度单元,0:否,1:是")
    private String imageProgress;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    @Excel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    @Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    @Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
    @Excel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    @Excel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    @Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    @Excel(name = "预留字段5")
    private String ptVar5;

    List<XmslDrawReviewMaterial> materialList;
    List<XmslDrawReviewWbs> wbsList;

}
