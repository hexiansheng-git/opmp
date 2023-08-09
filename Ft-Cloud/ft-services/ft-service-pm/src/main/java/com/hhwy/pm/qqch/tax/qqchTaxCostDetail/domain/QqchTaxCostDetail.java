package com.hhwy.pm.qqch.tax.qqchTaxCostDetail.domain;

import com.hhwy.common.core.web.domain.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
import com.hhwy.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mls
 * @date 2023-08-09 18:17:26
 * @remark   qqch_tax_cost_detail
 */
public class QqchTaxCostDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

            /**
         * 字段描述：主键
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "主键"    )
                private Long id;
            /**
         * 字段描述：qqch_tax_cost.id
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "qqch_tax_cost.id"    )
                private Long masterId;
            /**
         * 字段描述：1-属地账成本明细_成本明细, 2-属地账成本明细_间接费用, 3-所得税明细
         */
                                        @JsonProperty
                @Excel(name = "1-属地账成本明细_成本明细, 2-属地账成本明细_间接费用, 3-所得税明细"    )
                private String dataType;
            /**
         * 字段描述：年份
         */
                                        @JsonProperty
                @Excel(name = "年份"    )
                private String year;
            /**
         * 字段描述：币种
         */
                                        @JsonProperty
                @Excel(name = "币种"    )
                private String currency;
            /**
         * 字段描述：汇率
         */
                                        @JsonProperty
                @Excel(name = "汇率"    )
                private BigDecimal rate;
            /**
         * 字段描述：内账成本/内账
         */
                                        @JsonProperty
                @Excel(name = "内账成本/内账"    )
                private BigDecimal innerAmt;
            /**
         * 字段描述：符合属地账要求成本
         */
                                        @JsonProperty
                @Excel(name = "符合属地账要求成本"    )
                private BigDecimal reqAmt;
            /**
         * 字段描述：属地账策划成本
         */
                                        @JsonProperty
                @Excel(name = "属地账策划成本"    )
                private BigDecimal localAmt;
            /**
         * 字段描述：(美元)内账成本/内账
         */
                                        @JsonProperty
                @Excel(name = "(美元)内账成本/内账"    )
                private BigDecimal usdInnerAmt;
            /**
         * 字段描述：(美元)符合属地账要求成本
         */
                                        @JsonProperty
                @Excel(name = "(美元)符合属地账要求成本"    )
                private BigDecimal usdReqAmt;
            /**
         * 字段描述：(美元)属地账策划成本
         */
                                        @JsonProperty
                @Excel(name = "(美元)属地账策划成本"    )
                private BigDecimal usdLocalAmt;
            /**
         * 字段描述：备注/描述
         */
                                        @JsonProperty
                @Excel(name = "备注/描述"    )
                private String remark;
            /**
         * 字段描述：叶子节点（1：是，0：否）
         */
                                        @JsonProperty
                @Excel(name = "叶子节点（1：是，0：否）"    )
                private String leaf;
            /**
         * 字段描述：排序
         */
                                        @JsonProperty
                @Excel(name = "排序"    )
                private Integer sort;
            /**
         * 字段描述：版本
         */
                                        @JsonProperty
                @Excel(name = "版本"    )
                private BigDecimal version;
            /**
         * 字段描述：是否有效 1-有效 0-失效
         */
                                        @JsonProperty
                @Excel(name = "是否有效 1-有效 0-失效"    )
                private String valid;
            /**
         * 字段描述：所属区域id
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "所属区域id"    )
                private Long regionId;
            /**
         * 字段描述：所属区域名称
         */
                                        @JsonProperty
                @Excel(name = "所属区域名称"    )
                private String regionName;
            /**
         * 字段描述：项目id
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "项目id"    )
                private Long projectId;
            /**
         * 字段描述：项目名称
         */
                                        @JsonProperty
                @Excel(name = "项目名称"    )
                private String projectName;
            /**
         * 字段描述：部门id
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "部门id"    )
                private Long deptId;
            /**
         * 字段描述：数据创建者id
         */
                                        @JsonProperty
                @Excel(name = "数据创建者id"    )
                private String createUser;
            /**
         * 字段描述：数据创建者名称
         */
                                        @JsonProperty
                @Excel(name = "数据创建者名称"    )
                private String createUserName;
            /**
         * 字段描述：数据创建系统时间
         */
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @JsonProperty
                @Excel(name = "数据创建系统时间"  ,dateFormat = "yyyy-MM-dd HH:mm:ss"    )
                private Date createTime;
            /**
         * 字段描述：数据修改者id
         */
                                        @JsonProperty
                @Excel(name = "数据修改者id"    )
                private String updateUser;
            /**
         * 字段描述：数据修改系统时间
         */
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @JsonProperty
                @Excel(name = "数据修改系统时间"  ,dateFormat = "yyyy-MM-dd HH:mm:ss"    )
                private Date updateTime;
            /**
         * 字段描述：数据删除者
         */
                                        @JsonProperty
                @Excel(name = "数据删除者"    )
                private String delUser;
            /**
         * 字段描述：数据删除系统时间
         */
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @JsonProperty
                @Excel(name = "数据删除系统时间"  ,dateFormat = "yyyy-MM-dd HH:mm:ss"    )
                private Date delTime;
            /**
         * 字段描述：删除标识：0未删除；1已删除
         */
                                        @JsonProperty
                @Excel(name = "删除标识：0未删除；1已删除"    )
                private String delFlag;
            /**
         * 字段描述：预留字段1
         */
                                        @JsonProperty
                @Excel(name = "预留字段1"    )
                private String ptVar1;
            /**
         * 字段描述：预留字段2
         */
                                        @JsonProperty
                @Excel(name = "预留字段2"    )
                private String ptVar2;
            /**
         * 字段描述：预留字段3
         */
                                        @JsonProperty
                @Excel(name = "预留字段3"    )
                private String ptVar3;
            /**
         * 字段描述：预留字段4
         */
                                        @JsonProperty
                @Excel(name = "预留字段4"    )
                private String ptVar4;
            /**
         * 字段描述：预留字段5
         */
                                        @JsonProperty
                @Excel(name = "预留字段5"    )
                private String ptVar5;
    
            @JsonIgnore
        public Long getId() {
            return id;
        }
        @JsonIgnore
        public void setId(Long id) {
            this.id = id;
        }
            @JsonIgnore
        public Long getMasterId() {
            return masterId;
        }
        @JsonIgnore
        public void setMasterId(Long masterId) {
            this.masterId = masterId;
        }
            @JsonIgnore
        public String getDataType() {
            return dataType;
        }
        @JsonIgnore
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
            @JsonIgnore
        public String getYear() {
            return year;
        }
        @JsonIgnore
        public void setYear(String year) {
            this.year = year;
        }
            @JsonIgnore
        public String getCurrency() {
            return currency;
        }
        @JsonIgnore
        public void setCurrency(String currency) {
            this.currency = currency;
        }
            @JsonIgnore
        public BigDecimal getRate() {
            return rate;
        }
        @JsonIgnore
        public void setRate(BigDecimal rate) {
            this.rate = rate;
        }
            @JsonIgnore
        public BigDecimal getInnerAmt() {
            return innerAmt;
        }
        @JsonIgnore
        public void setInnerAmt(BigDecimal innerAmt) {
            this.innerAmt = innerAmt;
        }
            @JsonIgnore
        public BigDecimal getReqAmt() {
            return reqAmt;
        }
        @JsonIgnore
        public void setReqAmt(BigDecimal reqAmt) {
            this.reqAmt = reqAmt;
        }
            @JsonIgnore
        public BigDecimal getLocalAmt() {
            return localAmt;
        }
        @JsonIgnore
        public void setLocalAmt(BigDecimal localAmt) {
            this.localAmt = localAmt;
        }
            @JsonIgnore
        public BigDecimal getUsdInnerAmt() {
            return usdInnerAmt;
        }
        @JsonIgnore
        public void setUsdInnerAmt(BigDecimal usdInnerAmt) {
            this.usdInnerAmt = usdInnerAmt;
        }
            @JsonIgnore
        public BigDecimal getUsdReqAmt() {
            return usdReqAmt;
        }
        @JsonIgnore
        public void setUsdReqAmt(BigDecimal usdReqAmt) {
            this.usdReqAmt = usdReqAmt;
        }
            @JsonIgnore
        public BigDecimal getUsdLocalAmt() {
            return usdLocalAmt;
        }
        @JsonIgnore
        public void setUsdLocalAmt(BigDecimal usdLocalAmt) {
            this.usdLocalAmt = usdLocalAmt;
        }
            @JsonIgnore
        public String getRemark() {
            return remark;
        }
        @JsonIgnore
        public void setRemark(String remark) {
            this.remark = remark;
        }
            @JsonIgnore
        public String getLeaf() {
            return leaf;
        }
        @JsonIgnore
        public void setLeaf(String leaf) {
            this.leaf = leaf;
        }
            @JsonIgnore
        public Integer getSort() {
            return sort;
        }
        @JsonIgnore
        public void setSort(Integer sort) {
            this.sort = sort;
        }
            @JsonIgnore
        public BigDecimal getVersion() {
            return version;
        }
        @JsonIgnore
        public void setVersion(BigDecimal version) {
            this.version = version;
        }
            @JsonIgnore
        public String getValid() {
            return valid;
        }
        @JsonIgnore
        public void setValid(String valid) {
            this.valid = valid;
        }
            @JsonIgnore
        public Long getRegionId() {
            return regionId;
        }
        @JsonIgnore
        public void setRegionId(Long regionId) {
            this.regionId = regionId;
        }
            @JsonIgnore
        public String getRegionName() {
            return regionName;
        }
        @JsonIgnore
        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }
            @JsonIgnore
        public Long getProjectId() {
            return projectId;
        }
        @JsonIgnore
        public void setProjectId(Long projectId) {
            this.projectId = projectId;
        }
            @JsonIgnore
        public String getProjectName() {
            return projectName;
        }
        @JsonIgnore
        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }
            @JsonIgnore
        public Long getDeptId() {
            return deptId;
        }
        @JsonIgnore
        public void setDeptId(Long deptId) {
            this.deptId = deptId;
        }
            @JsonIgnore
        public String getCreateUser() {
            return createUser;
        }
        @JsonIgnore
        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }
            @JsonIgnore
        public String getCreateUserName() {
            return createUserName;
        }
        @JsonIgnore
        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }
            @JsonIgnore
        public Date getCreateTime() {
            return createTime;
        }
        @JsonIgnore
        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }
            @JsonIgnore
        public String getUpdateUser() {
            return updateUser;
        }
        @JsonIgnore
        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }
            @JsonIgnore
        public Date getUpdateTime() {
            return updateTime;
        }
        @JsonIgnore
        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }
            @JsonIgnore
        public String getDelUser() {
            return delUser;
        }
        @JsonIgnore
        public void setDelUser(String delUser) {
            this.delUser = delUser;
        }
            @JsonIgnore
        public Date getDelTime() {
            return delTime;
        }
        @JsonIgnore
        public void setDelTime(Date delTime) {
            this.delTime = delTime;
        }
            @JsonIgnore
        public String getDelFlag() {
            return delFlag;
        }
        @JsonIgnore
        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }
            @JsonIgnore
        public String getPtVar1() {
            return ptVar1;
        }
        @JsonIgnore
        public void setPtVar1(String ptVar1) {
            this.ptVar1 = ptVar1;
        }
            @JsonIgnore
        public String getPtVar2() {
            return ptVar2;
        }
        @JsonIgnore
        public void setPtVar2(String ptVar2) {
            this.ptVar2 = ptVar2;
        }
            @JsonIgnore
        public String getPtVar3() {
            return ptVar3;
        }
        @JsonIgnore
        public void setPtVar3(String ptVar3) {
            this.ptVar3 = ptVar3;
        }
            @JsonIgnore
        public String getPtVar4() {
            return ptVar4;
        }
        @JsonIgnore
        public void setPtVar4(String ptVar4) {
            this.ptVar4 = ptVar4;
        }
            @JsonIgnore
        public String getPtVar5() {
            return ptVar5;
        }
        @JsonIgnore
        public void setPtVar5(String ptVar5) {
            this.ptVar5 = ptVar5;
        }
    }
