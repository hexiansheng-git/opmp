package com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能：
 * 作者: fushudong
 * 时间: 2024/01/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SgjsTechnicalFileBlueprintParam extends SgjsTechnicalFileBlueprint{

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String endDate;

}