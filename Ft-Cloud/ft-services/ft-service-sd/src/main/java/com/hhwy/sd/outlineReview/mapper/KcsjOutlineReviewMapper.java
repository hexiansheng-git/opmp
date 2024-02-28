package com.hhwy.sd.outlineReview.mapper;

import java.util.List;

import com.hhwy.sd.outlineReview.domain.KcsjOutlineReview;
import org.apache.ibatis.annotations.Param;

/**
 * 功能描述: 勘察设计 - 勘察设计大纲评审
 * @author fushudong
 * @date 2024-02-04 15:29:15
 */
public interface KcsjOutlineReviewMapper {

    KcsjOutlineReview getKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview);

    List<KcsjOutlineReview> getKcsjOutlineReviewList(KcsjOutlineReview kcsjOutlineReview);

    int insertKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview);

    int insertKcsjOutlineReviewList(@Param("kcsjOutlineReviewList") List<KcsjOutlineReview> kcsjOutlineReviewList);

    int updateKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview);

    int updateKcsjOutlineReviewList(@Param("kcsjOutlineReviewList") List<KcsjOutlineReview> kcsjOutlineReviewList);

    int deleteKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview);

    int deleteKcsjOutlineReviewByPks(@Param("kcsjOutlineReviewPkList") List<Integer> kcsjOutlineReviewPkList);

    KcsjOutlineReview getMaxVersionData();
}
