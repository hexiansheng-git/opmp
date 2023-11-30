package com.hhwy.pm.qyzs.safe.qyzsSafeSpecialJobCatalog.domain;

import lombok.Data;

/**
 * @author cjh
 * @date 2023-11-20 15:21:34
 * @remark qyzs_safe_special_job_catalog
 */
@Data
public class SafeSpecialJobCatalogQueryVo {
    /**
     * 字段描述：特种作业名称
     */
    private String specialJobName;
    /**
     * 字段描述：特种作业内容
     */
    private String specialJobContent;
}
