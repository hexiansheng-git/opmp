package com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author han
 * @date 2023-07-21 16:47:23
 * @remark
 * <p>
 * 2.4 设计交底策划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignDisclosurePlanDto {

    /**
     * 字段描述：交底名称
     */
    @JsonProperty
    private String name;
    /**
     * 字段描述：交底单位
     */
    @JsonProperty
    private String disclosureUnit;
    /**
     * 字段描述：被交底单位
     */
    @JsonProperty
    private String passiveDisclosureUnit;
    /**
     * 字段描述：交底内容
     */
    @JsonProperty
    private String disclosureContent;
    /**
     * 字段描述：计划交底日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date planDisclosureDate;
}
