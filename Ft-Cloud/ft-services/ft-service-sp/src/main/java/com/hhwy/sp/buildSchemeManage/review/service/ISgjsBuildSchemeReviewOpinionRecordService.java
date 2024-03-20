package com.hhwy.sp.buildSchemeManage.review.service;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewOpinionRecord;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:48
 * @remark
 */
public interface ISgjsBuildSchemeReviewOpinionRecordService {

    SgjsBuildSchemeReviewOpinionRecord getSgjsBuildSchemeReviewOpinionRecord(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord);

    List<SgjsBuildSchemeReviewOpinionRecord> getSgjsBuildSchemeReviewOpinionRecordList(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord);

    int insertSgjsBuildSchemeReviewOpinionRecord(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord);

    int insertSgjsBuildSchemeReviewOpinionRecordList(List<SgjsBuildSchemeReviewOpinionRecord> sgjsBuildSchemeReviewOpinionRecordList);

    int updateSgjsBuildSchemeReviewOpinionRecord(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord);

    int updateSgjsBuildSchemeReviewOpinionRecordList(List<SgjsBuildSchemeReviewOpinionRecord> sgjsBuildSchemeReviewOpinionRecordList);

    int deleteSgjsBuildSchemeReviewOpinionRecord(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord);

    int deleteSgjsBuildSchemeReviewOpinionRecordByPks(List<Long> sgjsBuildSchemeReviewOpinionRecordPkList);
}
