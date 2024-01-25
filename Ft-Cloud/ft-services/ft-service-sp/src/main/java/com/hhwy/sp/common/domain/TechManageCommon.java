package com.hhwy.sp.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class TechManageCommon extends BaseEntity {

    /**
     * 字段描述：外键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long foreignId;
    /**
     * 字段描述：所属业务
     */
    @JsonProperty
    private String belongBusiness;
    /**
     * 字段描述：申报奖项
     */
    @JsonProperty
    private String applyAward;
    /**
     * 字段描述：奖项等级
     */
    @JsonProperty
    private String awardGrade;
    /**
     * 字段描述：奖项类别
     */
    @JsonProperty
    private String awardType;
    /**
     * 字段描述：授予单位
     */
    @JsonProperty
    private String grantUnit;
    /**
     * 字段描述：奖项时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date awardTime;

    private String allAward;
}
