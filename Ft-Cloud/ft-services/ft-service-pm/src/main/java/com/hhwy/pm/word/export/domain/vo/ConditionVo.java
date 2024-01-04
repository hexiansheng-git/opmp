package com.hhwy.pm.word.export.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:48
 * @remark word条款实体
 */
@Data
public class ConditionVo {
    /**
     * 字段描述：主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 字段描述：父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;
    /**
     * 字段描述：序号
     */
    private String serialNumber;
    /**
     * 字段描述：事项
     */
    private String name;
    /**
     * 字段描述：条件内容
     */
    private String content;
    /**
     * 字段描述：风险等级（字典项：condition_risk_grade）
     */
    private String riskGrade;
    /**
     * 字段描述：分析及应对措施
     */
    private String analyseSolutions;
    private List<ConditionVo> children;
}
