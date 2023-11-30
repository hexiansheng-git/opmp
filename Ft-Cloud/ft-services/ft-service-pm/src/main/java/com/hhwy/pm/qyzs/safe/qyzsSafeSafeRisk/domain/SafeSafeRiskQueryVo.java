package com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.domain;

import lombok.Data;

/**
 * @author cjh
 * @date 2023-11-17 16:26:05
 * @remark qyzs_safe_safe_risk
 */
@Data
public class SafeSafeRiskQueryVo {
    /**
     * 字段描述：工程类别
     */
    private String projectType;
    /**
     * 字段描述：标准wbs编码
     */
    private String wbsCode;
    /**
     * 字段描述：作业单元
     */
    private String workUnit;
    /**
     * 字段描述：风险等级
     */
    private String riskLevel;
}
