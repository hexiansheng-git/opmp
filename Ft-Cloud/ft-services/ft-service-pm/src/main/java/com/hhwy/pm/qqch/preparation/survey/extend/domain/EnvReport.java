package com.hhwy.pm.qqch.preparation.survey.extend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author han
 * @date 2023-07-07 18:36:03
 * @remark 前期策划-前期策划编制-勘察设计策划-扩展
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvReport {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    private String fileGroupId;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    private BigDecimal version;
    /**
     * 字段描述：上传人
     */
    @JsonProperty
    private String uploadUser;
    /**
     * 字段描述：上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date uploadTime;

    /**
     * 功能标识
     */
    private String moduleIdentity;
}
