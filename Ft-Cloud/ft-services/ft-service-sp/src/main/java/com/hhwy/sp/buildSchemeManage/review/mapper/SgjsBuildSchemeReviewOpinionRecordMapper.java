package com.hhwy.sp.buildSchemeManage.review.mapper;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewOpinionRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:48
 * @remark
 */
@Repository
public interface SgjsBuildSchemeReviewOpinionRecordMapper {

    SgjsBuildSchemeReviewOpinionRecord getSgjsBuildSchemeReviewOpinionRecord(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord);

    List<SgjsBuildSchemeReviewOpinionRecord> getSgjsBuildSchemeReviewOpinionRecordList(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord);

    int insertSgjsBuildSchemeReviewOpinionRecord(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord);

    int insertSgjsBuildSchemeReviewOpinionRecordList(@Param("sgjsBuildSchemeReviewOpinionRecordList") List<SgjsBuildSchemeReviewOpinionRecord> sgjsBuildSchemeReviewOpinionRecordList);

    int updateSgjsBuildSchemeReviewOpinionRecord(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord);

    int updateSgjsBuildSchemeReviewOpinionRecordList(@Param("list") List<SgjsBuildSchemeReviewOpinionRecord> sgjsBuildSchemeReviewOpinionRecordList);

    int deleteSgjsBuildSchemeReviewOpinionRecord(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord);

    int deleteSgjsBuildSchemeReviewOpinionRecordByPks(@Param("sgjsBuildSchemeReviewOpinionRecordPkList") List<Long> sgjsBuildSchemeReviewOpinionRecordPkList);

    int getMaxSerialNumber(@Param("reviewId") Long reviewId);
}
