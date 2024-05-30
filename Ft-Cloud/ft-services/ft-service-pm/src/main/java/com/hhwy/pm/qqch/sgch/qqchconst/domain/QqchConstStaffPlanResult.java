package com.hhwy.pm.qqch.sgch.qqchconst.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import lombok.Data;

/**
 * @author mls
 * @date 2023-08-03 16:08:24
 * @remark qqch_const_staff_plan
 */
@Data
public class QqchConstStaffPlanResult extends CompileEntity<QqchConstStaffPlanResult> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主键 对应1.3施工部署中人员策划中的pt_var1
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;

    /**
     * 字段描述：主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long masterId;

    /**
     * 字段描述：工种编号
     */
    @JsonProperty
    private String occupationCode;
    /**
     * 字段描述：工种名称
     */
    @JsonProperty
    private String occupationName;
    /**
     * 字段描述：班组
     */
    @JsonProperty
    private String constDesc;
    /**
     * 字段描述：施工内容
     */
    @JsonProperty
    private String constContent;
    /**
     * 字段描述：中方数量
     */
    @JsonProperty
    private Integer chineseSideCount;
    /**
     * 字段描述：属地化数量
     */
    @JsonProperty
    private Integer localCount;
    /**
     * 字段描述：总数量
     */
    @JsonProperty
    private Integer totalCount;
    /**
     * 字段描述：在场天数
     */
    @JsonProperty
    private Integer siteDays;


}
