package com.hhwy.sp.buildSchemeManage.review.service;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReview;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewOpinionRecord;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeReviewDetailQueryVo;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeReviewQueryVo;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark
 */
public interface ISgjsBuildSchemeReviewService {

    SgjsBuildSchemeReview getSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    List<SgjsBuildSchemeReview> getSgjsBuildSchemeReviewList(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int insertSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int insertSgjsBuildSchemeReviewList(List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList);

    int updateSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int updateSgjsBuildSchemeReviewList(List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList);

    int deleteSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int deleteSgjsBuildSchemeReviewByPks(List<Long> sgjsBuildSchemeReviewPkList);

    /**
     * 台账
     * @param queryVo
     * @return
     */
    List<SgjsBuildSchemeReview> getListByQueryVo(BuildSchemeReviewQueryVo queryVo);

    /**
     * 详情
     * @param detailQueryVo
     * @return
     */
    SgjsBuildSchemeReview getDetail(BuildSchemeReviewDetailQueryVo detailQueryVo);

    Long save(SgjsBuildSchemeReview review);

    List<SgjsBuildSchemeReview> getListByIds(List<Long> ids);

    String sync();

    void turnDown(Long reviewId);

    List<SgjsBuildSchemeList> getSchemeList(SgjsBuildSchemeList schemeList);

    void submitBuildSchemeReviewProcess(Long id);

    void updateBuildSchemeReviewProcess(Long id);

    SgjsBuildSchemeReviewOpinionRecord getSchemeReviewRecord(Long reviewId);

    void warnMessage();

    void deleteById(Long id);
}
