package com.hhwy.sp.buildSchemeManage.review.domain.vo;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReview;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewOpinionRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author han
 * @date 2024-03-20 09:39:48
 * @remark sgjs_build_scheme_review_opinion_record
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildSchemeReviewOpinionRecordVo {
    private static final long serialVersionUID = 1L;

    private SgjsBuildSchemeReview review;

    private SgjsBuildSchemeReviewOpinionRecord record;
}
