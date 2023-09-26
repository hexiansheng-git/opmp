package com.hhwy.system.warn.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date 2023-09-26 10:33:21
 * @remark t_warn_record
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TWarnRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：记录id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "记录id")
    private Long recordId;
    /**
     * 字段描述：预警项
     */
    @JsonProperty
    @Excel(name = "预警项")
    private String warnItem;
    /**
     * 字段描述：预警项id
     */
    @JsonProperty
    @Excel(name = "预警项id")
    private String warnItemId;
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
     * 字段描述：预警内容
     */
    @JsonProperty
    @Excel(name = "预警内容")
    private String warnContent;
    /**
     * 字段描述：预警状态
     */
    @JsonProperty
    @Excel(name = "预警状态")
    private String warnStatus;
    /**
     * 字段描述：预警类型
     */
    @JsonProperty
    @Excel(name = "预警类型")
    private String warnType;
    /**
     * 字段描述：预警url
     */
    @JsonProperty
    @Excel(name = "预警url")
    private String warnUrl;
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
    /**
     * 字段描述：租户标识
     */
    @JsonProperty
    @Excel(name = "租户标识")
    private String tenantKey;
}
