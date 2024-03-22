package com.hhwy.sp.buildSchemeManage.review.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark 施工方案评审查询参数实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildSchemeReviewQueryVo {
    /**
     * 字段描述：方案编号
     */
    private String schemeNum;
    /**
     * 字段描述：方案名称
     */
    private String schemeName;
    /**
     * 字段描述：方案分级 1Ⅰ、2Ⅱ、3Ⅲ、4Ⅳ
     */
    private String schemeLevel;
    /**
     * 字段描述：流程状态
     */
    private String taskStatus;

    private List<Long> ids;
}
