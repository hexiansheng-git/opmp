package com.hhwy.pm.qqch.tax.qqchTaxGlobalFormula.domain;

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
 * @date 2023-08-17 16:19:10
 * @remark   qqch_tax_global_formula
 */
public class QqchTaxGlobalFormula extends BaseEntity {
    private static final long serialVersionUID = 1L;

            /**
         * 字段描述：主键id
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "主键id"    )
                private Long id;
            /**
         * 字段描述：年份
         */
                                        @JsonProperty
                @Excel(name = "年份"    )
                private Integer year;
            /**
         * 字段描述：工程量计算
         */
                                        @JsonProperty
                @Excel(name = "工程量计算"    )
                private BigDecimal quantities;
            /**
         * 字段描述：调价收入
         */
                                        @JsonProperty
                @Excel(name = "调价收入"    )
                private BigDecimal adjustInAmt;
            /**
         * 字段描述：利息收入
         */
                                        @JsonProperty
                @Excel(name = "利息收入"    )
                private BigDecimal interestInAmt;
            /**
         * 字段描述：预付款比例
         */
                                        @JsonProperty
                @Excel(name = "预付款比例"    )
                private BigDecimal prePayRate;
            /**
         * 字段描述：预付款
         */
                                        @JsonProperty
                @Excel(name = "预付款"    )
                private BigDecimal prePayAmt;
            /**
         * 字段描述：质保金扣除比例
         */
                                        @JsonProperty
                @Excel(name = "质保金扣除比例"    )
                private BigDecimal guaDeductRate;
            /**
         * 字段描述：质保金
         */
                                        @JsonProperty
                @Excel(name = "质保金"    )
                private BigDecimal guaAmt;
            /**
         * 字段描述：单独计量的利息收入
         */
                                        @JsonProperty
                @Excel(name = "单独计量的利息收入"    )
                private BigDecimal aloneInterestInAmt;
            /**
         * 字段描述：索赔收入
         */
                                        @JsonProperty
                @Excel(name = "索赔收入"    )
                private BigDecimal claimInAmt;
            /**
         * 字段描述：节点回收比例
         */
                                        @JsonProperty
                @Excel(name = "节点回收比例"    )
                private BigDecimal nodeRecoveryRate;
            /**
         * 字段描述：不含税合同金额
         */
                                        @JsonProperty
                @Excel(name = "不含税合同金额"    )
                private BigDecimal excContAmt;
            /**
         * 字段描述：附件
         */
                                        @JsonProperty
                @Excel(name = "附件"    )
                private String fileGroupId;
            /**
         * 字段描述：备注
         */
                                        @JsonProperty
                @Excel(name = "备注"    )
                private String remark;
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
         * 字段描述：用户id
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "用户id"    )
                private Long userId;
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
         * 字段描述：版本号
         */
                                        @JsonProperty
                @Excel(name = "版本号"    )
                private BigDecimal version;
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
            /**
         * 字段描述：序号
         */
                                        @JsonProperty
                @Excel(name = "序号"    )
                private Integer sort;
    
            @JsonIgnore
        public Long getId() {
            return id;
        }
        @JsonIgnore
        public void setId(Long id) {
            this.id = id;
        }
            @JsonIgnore
        public Integer getYear() {
            return year;
        }
        @JsonIgnore
        public void setYear(Integer year) {
            this.year = year;
        }
            @JsonIgnore
        public BigDecimal getQuantities() {
            return quantities;
        }
        @JsonIgnore
        public void setQuantities(BigDecimal quantities) {
            this.quantities = quantities;
        }
            @JsonIgnore
        public BigDecimal getAdjustInAmt() {
            return adjustInAmt;
        }
        @JsonIgnore
        public void setAdjustInAmt(BigDecimal adjustInAmt) {
            this.adjustInAmt = adjustInAmt;
        }
            @JsonIgnore
        public BigDecimal getInterestInAmt() {
            return interestInAmt;
        }
        @JsonIgnore
        public void setInterestInAmt(BigDecimal interestInAmt) {
            this.interestInAmt = interestInAmt;
        }
            @JsonIgnore
        public BigDecimal getPrePayRate() {
            return prePayRate;
        }
        @JsonIgnore
        public void setPrePayRate(BigDecimal prePayRate) {
            this.prePayRate = prePayRate;
        }
            @JsonIgnore
        public BigDecimal getPrePayAmt() {
            return prePayAmt;
        }
        @JsonIgnore
        public void setPrePayAmt(BigDecimal prePayAmt) {
            this.prePayAmt = prePayAmt;
        }
            @JsonIgnore
        public BigDecimal getGuaDeductRate() {
            return guaDeductRate;
        }
        @JsonIgnore
        public void setGuaDeductRate(BigDecimal guaDeductRate) {
            this.guaDeductRate = guaDeductRate;
        }
            @JsonIgnore
        public BigDecimal getGuaAmt() {
            return guaAmt;
        }
        @JsonIgnore
        public void setGuaAmt(BigDecimal guaAmt) {
            this.guaAmt = guaAmt;
        }
            @JsonIgnore
        public BigDecimal getAloneInterestInAmt() {
            return aloneInterestInAmt;
        }
        @JsonIgnore
        public void setAloneInterestInAmt(BigDecimal aloneInterestInAmt) {
            this.aloneInterestInAmt = aloneInterestInAmt;
        }
            @JsonIgnore
        public BigDecimal getClaimInAmt() {
            return claimInAmt;
        }
        @JsonIgnore
        public void setClaimInAmt(BigDecimal claimInAmt) {
            this.claimInAmt = claimInAmt;
        }
            @JsonIgnore
        public BigDecimal getNodeRecoveryRate() {
            return nodeRecoveryRate;
        }
        @JsonIgnore
        public void setNodeRecoveryRate(BigDecimal nodeRecoveryRate) {
            this.nodeRecoveryRate = nodeRecoveryRate;
        }
            @JsonIgnore
        public BigDecimal getExcContAmt() {
            return excContAmt;
        }
        @JsonIgnore
        public void setExcContAmt(BigDecimal excContAmt) {
            this.excContAmt = excContAmt;
        }
            @JsonIgnore
        public String getFileGroupId() {
            return fileGroupId;
        }
        @JsonIgnore
        public void setFileGroupId(String fileGroupId) {
            this.fileGroupId = fileGroupId;
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
        public Long getUserId() {
            return userId;
        }
        @JsonIgnore
        public void setUserId(Long userId) {
            this.userId = userId;
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
        public BigDecimal getVersion() {
            return version;
        }
        @JsonIgnore
        public void setVersion(BigDecimal version) {
            this.version = version;
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
            @JsonIgnore
        public Integer getSort() {
            return sort;
        }
        @JsonIgnore
        public void setSort(Integer sort) {
            this.sort = sort;
        }
    }
