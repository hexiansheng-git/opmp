package com.hhwy.pm.xmsl.wbs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * wbs主表
 * @author wk
 * @date 2023-07-13 18:02:49
 * @remark
 */
public class XmslWbsMain extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主键id")
    @NotNull(message = "id不能为空！",groups = {ValidationGroups.Delete.class,ValidationGroups.Update.class})
    private Long id;
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
     * 字段描述：版本号
     */
    @JsonProperty
    @Excel(name = "版本号")
    private Integer version;
    /**
     * 字段描述：是否生效,0:否，1:是
     */
    @JsonProperty
    @Excel(name = "是否生效,0:否，1:是")
    private Integer valid;
    /**
     * 字段描述：发布人
     */
    @JsonProperty
    @Excel(name = "发布人")
    private String publishUserName;
    /**
     * 字段描述：发布人ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "发布人ID")
    private Long publishUserId;
    /**
     * 字段描述：发布日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "发布日期", dateFormat = "yyyy-MM-dd")
    private Date publishDate;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd")
    private Date createTime;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
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
    public Integer getVersion() {
        return version;
    }

    @JsonIgnore
    public void setVersion(Integer version) {
        this.version = version;
    }

    @JsonIgnore
    public Integer getValid() {
        return valid;
    }

    @JsonIgnore
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @JsonIgnore
    public String getPublishUserName() {
        return publishUserName;
    }

    @JsonIgnore
    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
    }

    @JsonIgnore
    public Long getPublishUserId() {
        return publishUserId;
    }

    @JsonIgnore
    public void setPublishUserId(Long publishUserId) {
        this.publishUserId = publishUserId;
    }

    @JsonIgnore
    public Date getPublishDate() {
        return publishDate;
    }

    @JsonIgnore
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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
}
