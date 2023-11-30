package com.hhwy.pm.qyzs.quality.qyzsQualityCommonProblem.domain;

import lombok.Data;

/**
 * @author cjh
 * @date 2023-11-22 11:52:14
 * @remark qyzs_quality_common_problem
 */
@Data
public class QualityCommonProblemQueryVo {
    /**
     * 字段描述：工程类型
     */
    private String projectType;
    /**
     * 字段描述：标准wbs编码
     */
    private String wbsCode;
    /**
     * 字段描述：通病名称
     */
    private String problemName;
    /**
     * 字段描述：措施项
     */
    private String measure;
    /**
     * 字段描述：数据来源
     */
//    private String dataFrom;
    /**
     * 字段描述：编制/推送人
     */
//    private String editer;
    /**
     * 字段描述：编制/推送时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date editDate;
}
