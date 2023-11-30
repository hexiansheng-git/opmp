package com.hhwy.pm.qyzs.safe.qyzsSafeEnvRiskProc.domain;

import lombok.Data;

/**
 * @author cjh
 * @date 2023-11-17 16:25:55
 * @remark qyzs_safe_env_risk_proc
 */
@Data
public class SafeEnvRiskProcQueryVo {
    /**
     * 字段描述：工程类别
     */
    private String projectType;
    /**
     * 字段描述：标准wbs编码
     */
    private String wbsCode;
    /**
     * 字段描述：工序
     */
    private String procName;
    /**
     * 字段描述：作业
     */
    private String workName;
    /**
     * 字段描述：频率
     */
    private String frequency;
}
