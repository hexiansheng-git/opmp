package com.hhwy.pm.qqch.preparation.survey.managemodel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  用于重新封装数据  子数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SonEntity {
    private Long id;

    /**
     * 字段描述：管理模式
     */
    @JsonProperty
    @Excel(name = "管理模式")
    private String manageModel;
    /**
     * 字段描述：优点
     */
    @JsonProperty
    @Excel(name = "优点")
    private String advantage;
    /**
     * 字段描述：缺点
     */
    @JsonProperty
    @Excel(name = "缺点")
    private String disadvantage;

    private String remark;
}
