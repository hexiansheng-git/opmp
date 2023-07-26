package com.hhwy.pm.qqch.preparation.measureexp.beton.domain;

import com.hhwy.common.core.web.domain.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;
import com.hhwy.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mls
 * @date 2023-07-25 18:31:38
 * @remark   qqch_exp_beton
 */
public class QqchExpBeton extends BaseEntity {
    private static final long serialVersionUID = 1L;

            /**
         * 字段描述：主键id
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "主键id"    )
                private Long id;
            /**
         * 字段描述：配合比类型
         */
                                        @JsonProperty
                @Excel(name = "配合比类型"    )
                private String mixRatioType;
            /**
         * 字段描述：配合比名称
         */
                                        @JsonProperty
                @Excel(name = "配合比名称"    )
                private String mixRatioName;
            /**
         * 字段描述：坍落度
         */
                                        @JsonProperty
                @Excel(name = "坍落度"    )
                private String slumps;
            /**
         * 字段描述：所属WBS编码
         */
                                        @JsonProperty
                @Excel(name = "所属WBS编码"    )
                private String wbsCode;
            /**
         * 字段描述：所属WBS名称
         */
                                        @JsonProperty
                @Excel(name = "所属WBS名称"    )
                private String wbsName;
            /**
         * 字段描述：不同工艺环境下配合比要求
         */
                                        @JsonProperty
                @Excel(name = "不同工艺环境下配合比要求"    )
                private String reqContent;
            /**
         * 字段描述：计划最早开工日期
         */
                                @JsonFormat(pattern = "yyyy-MM-dd")
                        @JsonProperty
                @Excel(name = "计划最早开工日期"    ,dateFormat = "yyyy-MM-dd"  )
                private Date planDate;
            /**
         * 字段描述：所需数量
         */
                                        @JsonProperty
                @Excel(name = "所需数量"    )
                private BigDecimal usedNum;
            /**
         * 字段描述：拟完成时间
         */
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @JsonProperty
                @Excel(name = "拟完成时间"  ,dateFormat = "yyyy-MM-dd HH:mm:ss"    )
                private Date planFinishTime;
            /**
         * 字段描述：备注
         */
                                        @JsonProperty
                @Excel(name = "备注"    )
                private String remark;
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
        public String getMixRatioType() {
            return mixRatioType;
        }
        @JsonIgnore
        public void setMixRatioType(String mixRatioType) {
            this.mixRatioType = mixRatioType;
        }
            @JsonIgnore
        public String getMixRatioName() {
            return mixRatioName;
        }
        @JsonIgnore
        public void setMixRatioName(String mixRatioName) {
            this.mixRatioName = mixRatioName;
        }
            @JsonIgnore
        public String getSlumps() {
            return slumps;
        }
        @JsonIgnore
        public void setSlumps(String slumps) {
            this.slumps = slumps;
        }
            @JsonIgnore
        public String getWbsCode() {
            return wbsCode;
        }
        @JsonIgnore
        public void setWbsCode(String wbsCode) {
            this.wbsCode = wbsCode;
        }
            @JsonIgnore
        public String getWbsName() {
            return wbsName;
        }
        @JsonIgnore
        public void setWbsName(String wbsName) {
            this.wbsName = wbsName;
        }
            @JsonIgnore
        public String getReqContent() {
            return reqContent;
        }
        @JsonIgnore
        public void setReqContent(String reqContent) {
            this.reqContent = reqContent;
        }
            @JsonIgnore
        public Date getPlanDate() {
            return planDate;
        }
        @JsonIgnore
        public void setPlanDate(Date planDate) {
            this.planDate = planDate;
        }
            @JsonIgnore
        public BigDecimal getUsedNum() {
            return usedNum;
        }
        @JsonIgnore
        public void setUsedNum(BigDecimal usedNum) {
            this.usedNum = usedNum;
        }
            @JsonIgnore
        public Date getPlanFinishTime() {
            return planFinishTime;
        }
        @JsonIgnore
        public void setPlanFinishTime(Date planFinishTime) {
            this.planFinishTime = planFinishTime;
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
