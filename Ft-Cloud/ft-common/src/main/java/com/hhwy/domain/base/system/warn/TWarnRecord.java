package com.hhwy.domain.base.system.warn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author han
 * @date 2023-09-26 17:52:28
 * @remark t_warn_record
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TWarnRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：预警id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "预警id")
    private Long warnId;
    /**
     * 字段描述：预警人id
     */
    @JsonProperty
    @Excel(name = "预警人id")
    private String warnUserId;
    /**
     * 字段描述：预警人姓名
     */
    @JsonProperty
    @Excel(name = "预警人姓名")
    private String warnUserName;
    /**
     * 字段描述：读取状态（0：未读，1：已读）
     */
    @JsonProperty
    @Excel(name = "读取状态（0：未读，1：已读）")
    private String status;
    /**
     * 字段描述：创建者
     */
    @JsonProperty
    @Excel(name = "创建者")
    private String createUser;
    /**
     * 字段描述：创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：更新者
     */
    @JsonProperty
    @Excel(name = "更新者")
    private String updateUser;
    /**
     * 字段描述：更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Long getWarnId() {
        return warnId;
    }

    @JsonIgnore
    public void setWarnId(Long warnId) {
        this.warnId = warnId;
    }

    @JsonIgnore
    public String getWarnUserId() {
        return warnUserId;
    }

    @JsonIgnore
    public void setWarnUserId(String warnUserId) {
        this.warnUserId = warnUserId;
    }

    @JsonIgnore
    public String getWarnUserName() {
        return warnUserName;
    }

    @JsonIgnore
    public void setWarnUserName(String warnUserName) {
        this.warnUserName = warnUserName;
    }

    @JsonIgnore
    public String getStatus() {
        return status;
    }

    @JsonIgnore
    public void setStatus(String status) {
        this.status = status;
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
    public String getRemark() {
        return remark;
    }

    @JsonIgnore
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
