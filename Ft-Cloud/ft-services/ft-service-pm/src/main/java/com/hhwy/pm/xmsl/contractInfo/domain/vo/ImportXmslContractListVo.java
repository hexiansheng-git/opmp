package com.hhwy.pm.xmsl.contractInfo.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportXmslContractListVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Excel(name = "层级码")
    private String innerCode;
    @Excel(name = "父层级码")
    private String parentInnerCode;

    /**
     *
     */
    private String dataFrom;

    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：主表id（xmsl_contract_payinfo）
     */
    @NotNull(message = "masterId不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long masterId;
    /**
     * 字段描述：父id
     */
    @NotNull(message = "父id不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long pid;
    /**
     * 字段描述：祖籍id集合
     */
    @JsonProperty
    private String ancestors;
    /**
     * 字段描述：清单编号
     */
    @NotBlank(message = "清单编号不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "清单编号")
    private String code;
    /**
     * 字段描述：清单中文名称
     */
    @NotBlank(message = "清单中文名称不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
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
    @NotBlank(message = "清单类型不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "清单类型")
    private String listType;
    /**
     * 字段描述：单位编码
     */
    @JsonProperty
    private String unitCode;
    /**
     * 字段描述：单位
     */
    @NotBlank(message = "单位不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "单位")
    private String unit;
    /**
     * 字段描述：中标数量
     */
    @NotNull(message = "中标数量不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "中标数量")
    private BigDecimal winNum;
    /**
     * 字段描述：中标单价（不含税）
     */
    @NotNull(message = "中标单价（不含税）不能为空",groups = {ValidationGroups.Update.class,ValidationGroups.Save.class})
    @JsonProperty
    @Excel(name = "中标单价（不含税）")
    private BigDecimal winUnitPrice;
    /**
     * 字段描述：中标金额（不含税）
     */
    @JsonProperty
    @Excel(name = "中标金额（不含税）")
    private BigDecimal winAmount;
    /**
     * 字段描述：变更数量
     */
    @JsonProperty
    @Excel(name = "变更数量")
    private BigDecimal changeNum;
    /**
     * 字段描述：变更单价（不含税）
     */
    @JsonProperty
    @Excel(name = "变更单价（不含税）")
    private BigDecimal changeUnitPrice;
    /**
     * 字段描述：变更金额（不含税）
     */
    @JsonProperty
    @Excel(name = "变更金额（不含税）")
    private BigDecimal changeAmount;
    /**
     * 字段描述：变更后数量
     */
    @JsonProperty
    @Excel(name = "变更后数量")
    private BigDecimal afterNum;
    /**
     * 字段描述：变更后单价（不含税）
     */
    @JsonProperty
    @Excel(name = "变更后单价（不含税）")
    private BigDecimal afterUnitPrice;
    /**
     * 字段描述：变更后金额（不含税）
     */
    @JsonProperty
    @Excel(name = "变更后金额（不含税）")
    private BigDecimal afterAmount;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "部门id")
    private Long deptId;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd")
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

    /**
     * 字段描述：排序
     */
    @JsonProperty
    @Excel(name = "排序")
    private Integer sort;

    private Long[] ids;

    private List<ImportXmslContractListVo> children;
}
