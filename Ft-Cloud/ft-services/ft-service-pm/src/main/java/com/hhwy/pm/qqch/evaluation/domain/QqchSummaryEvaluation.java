package com.hhwy.pm.qqch.evaluation.domain;

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
 * @author zhenglili
 * @date 2023-07-24 16:48:10
 * @remark   qqch_summary_evaluation
 */
public class QqchSummaryEvaluation extends BaseEntity {
    private static final long serialVersionUID = 1L;

            /**
         * 字段描述：主键id
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "主键id"    )
                private Long id;
            /**
         * 字段描述：策划总结概述
         */
                                        @JsonProperty
                @Excel(name = "策划总结概述"    )
                private String summaryOverview;
            /**
         * 字段描述：策划总结概述附件组id
         */
                                        @JsonProperty
                @Excel(name = "策划总结概述附件组id"    )
                private String overviewFileGroupId;
            /**
         * 字段描述：初评单位id
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "初评单位id"    )
                private Long initialUnitId;
            /**
         * 字段描述：初评单位
         */
                                        @JsonProperty
                @Excel(name = "初评单位"    )
                private String initialUnit;
            /**
         * 字段描述：初评价人id
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "初评价人id"    )
                private Long initialPersonId;
            /**
         * 字段描述：初评价人
         */
                                        @JsonProperty
                @Excel(name = "初评价人"    )
                private String initialPerson;
            /**
         * 字段描述：初评价时间
         */
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @JsonProperty
                @Excel(name = "初评价时间"  ,dateFormat = "yyyy-MM-dd HH:mm:ss"    )
                private Date initialDate;
            /**
         * 字段描述：初评概述
         */
                                        @JsonProperty
                @Excel(name = "初评概述"    )
                private String initialOverview;
            /**
         * 字段描述：初评附件组id
         */
                                        @JsonProperty
                @Excel(name = "初评附件组id"    )
                private String initialFileGroupId;
            /**
         * 字段描述：终评单位id
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "终评单位id"    )
                private Long finalUnitId;
            /**
         * 字段描述：终评单位
         */
                                        @JsonProperty
                @Excel(name = "终评单位"    )
                private String finalUnit;
            /**
         * 字段描述：终评价人id
         */
                                        @JsonSerialize(using = ToStringSerializer.class)
                @JsonProperty
                @Excel(name = "终评价人id"    )
                private Long finalPersonId;
            /**
         * 字段描述：终评价人
         */
                                        @JsonProperty
                @Excel(name = "终评价人"    )
                private String finalPerson;
            /**
         * 字段描述：终评价时间
         */
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @JsonProperty
                @Excel(name = "终评价时间"  ,dateFormat = "yyyy-MM-dd HH:mm:ss"    )
                private Date finalDate;
            /**
         * 字段描述：终评概述
         */
                                        @JsonProperty
                @Excel(name = "终评概述"    )
                private String finalOverview;
            /**
         * 字段描述：终评附件组id
         */
                                        @JsonProperty
                @Excel(name = "终评附件组id"    )
                private String finalFileGroupId;
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
        public String getSummaryOverview() {
            return summaryOverview;
        }
        @JsonIgnore
        public void setSummaryOverview(String summaryOverview) {
            this.summaryOverview = summaryOverview;
        }
            @JsonIgnore
        public String getOverviewFileGroupId() {
            return overviewFileGroupId;
        }
        @JsonIgnore
        public void setOverviewFileGroupId(String overviewFileGroupId) {
            this.overviewFileGroupId = overviewFileGroupId;
        }
            @JsonIgnore
        public Long getInitialUnitId() {
            return initialUnitId;
        }
        @JsonIgnore
        public void setInitialUnitId(Long initialUnitId) {
            this.initialUnitId = initialUnitId;
        }
            @JsonIgnore
        public String getInitialUnit() {
            return initialUnit;
        }
        @JsonIgnore
        public void setInitialUnit(String initialUnit) {
            this.initialUnit = initialUnit;
        }
            @JsonIgnore
        public Long getInitialPersonId() {
            return initialPersonId;
        }
        @JsonIgnore
        public void setInitialPersonId(Long initialPersonId) {
            this.initialPersonId = initialPersonId;
        }
            @JsonIgnore
        public String getInitialPerson() {
            return initialPerson;
        }
        @JsonIgnore
        public void setInitialPerson(String initialPerson) {
            this.initialPerson = initialPerson;
        }
            @JsonIgnore
        public Date getInitialDate() {
            return initialDate;
        }
        @JsonIgnore
        public void setInitialDate(Date initialDate) {
            this.initialDate = initialDate;
        }
            @JsonIgnore
        public String getInitialOverview() {
            return initialOverview;
        }
        @JsonIgnore
        public void setInitialOverview(String initialOverview) {
            this.initialOverview = initialOverview;
        }
            @JsonIgnore
        public String getInitialFileGroupId() {
            return initialFileGroupId;
        }
        @JsonIgnore
        public void setInitialFileGroupId(String initialFileGroupId) {
            this.initialFileGroupId = initialFileGroupId;
        }
            @JsonIgnore
        public Long getFinalUnitId() {
            return finalUnitId;
        }
        @JsonIgnore
        public void setFinalUnitId(Long finalUnitId) {
            this.finalUnitId = finalUnitId;
        }
            @JsonIgnore
        public String getFinalUnit() {
            return finalUnit;
        }
        @JsonIgnore
        public void setFinalUnit(String finalUnit) {
            this.finalUnit = finalUnit;
        }
            @JsonIgnore
        public Long getFinalPersonId() {
            return finalPersonId;
        }
        @JsonIgnore
        public void setFinalPersonId(Long finalPersonId) {
            this.finalPersonId = finalPersonId;
        }
            @JsonIgnore
        public String getFinalPerson() {
            return finalPerson;
        }
        @JsonIgnore
        public void setFinalPerson(String finalPerson) {
            this.finalPerson = finalPerson;
        }
            @JsonIgnore
        public Date getFinalDate() {
            return finalDate;
        }
        @JsonIgnore
        public void setFinalDate(Date finalDate) {
            this.finalDate = finalDate;
        }
            @JsonIgnore
        public String getFinalOverview() {
            return finalOverview;
        }
        @JsonIgnore
        public void setFinalOverview(String finalOverview) {
            this.finalOverview = finalOverview;
        }
            @JsonIgnore
        public String getFinalFileGroupId() {
            return finalFileGroupId;
        }
        @JsonIgnore
        public void setFinalFileGroupId(String finalFileGroupId) {
            this.finalFileGroupId = finalFileGroupId;
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
