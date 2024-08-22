package com.hhwy.sp.buildSchemeManage.review.service;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReview;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeReviewDetailQueryVo;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeReviewOpinionRecordVo;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeReviewQueryVo;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    /**
     * 将流程改为初始化
     * @param id
     */
    void updateBuildSchemeReviewProcess2Init(Long id);

    BuildSchemeReviewOpinionRecordVo getSchemeReviewRecordVo(Long reviewId);

    void warnMessage();

    void deleteById(Long id);
    
    <T> void formatSchemeType(List<T> list, Function<T,String> function, BiFunction<T,String,String> setValFunc);

    /**
     * 流程转办给其他人
     * 如果是
     * @param taskId
     * @param username
     * @param nickName
     */
    void transferTask(String taskId,String username,String nickName);

    SgjsBuildSchemeReview exportSuggestion(HttpServletResponse response,BuildSchemeReviewDetailQueryVo detailQueryVo) throws IOException;
}
