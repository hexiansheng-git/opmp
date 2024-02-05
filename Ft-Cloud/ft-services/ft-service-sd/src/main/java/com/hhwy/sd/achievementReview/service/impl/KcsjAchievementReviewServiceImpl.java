package com.hhwy.sd.achievementReview.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.achievementReview.domain.KcsjAchievementReview;
import com.hhwy.sd.achievementReview.mapper.KcsjAchievementReviewMapper;
import com.hhwy.sd.achievementReview.service.IKcsjAchievementReviewService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-02-05 09:04:12
 * @remark
 */
@Service
public class KcsjAchievementReviewServiceImpl implements IKcsjAchievementReviewService {

    @Autowired
    private KcsjAchievementReviewMapper kcsjAchievementReviewMapper;


    public KcsjAchievementReview getKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview) {
        return kcsjAchievementReviewMapper.getKcsjAchievementReview(kcsjAchievementReview);
    }

    public List<KcsjAchievementReview> getKcsjAchievementReviewList(KcsjAchievementReview kcsjAchievementReview) {
        return kcsjAchievementReviewMapper.getKcsjAchievementReviewList(kcsjAchievementReview);
    }

    @Transactional
    public int insertKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview) {
        kcsjAchievementReview.setId(IdWorker.createId());
        kcsjAchievementReview.setCreateUser(SecurityUtils.getUserName());
        kcsjAchievementReview.setCreateTime(DateUtils.getNowDate());
        return kcsjAchievementReviewMapper.insertKcsjAchievementReview(kcsjAchievementReview);
    }

    @Transactional
    public int insertKcsjAchievementReviewList(List<KcsjAchievementReview> kcsjAchievementReviewList) {
        for (KcsjAchievementReview kcsjAchievementReview : kcsjAchievementReviewList) {
            kcsjAchievementReview.setId(IdWorker.createId());
            kcsjAchievementReview.setCreateUser(SecurityUtils.getUserName());
            kcsjAchievementReview.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjAchievementReviewMapper.insertKcsjAchievementReviewList(kcsjAchievementReviewList);
    }

    @Transactional
    public int updateKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview) {
        kcsjAchievementReview.setUpdateUser(SecurityUtils.getUserName());
        kcsjAchievementReview.setUpdateTime(DateUtils.getNowDate());
        return kcsjAchievementReviewMapper.updateKcsjAchievementReview(kcsjAchievementReview);
    }

    @Transactional
    public int updateKcsjAchievementReviewList(List<KcsjAchievementReview> kcsjAchievementReviewList) {
        for (KcsjAchievementReview kcsjAchievementReview : kcsjAchievementReviewList) {
            kcsjAchievementReview.setUpdateUser(SecurityUtils.getUserName());
            kcsjAchievementReview.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjAchievementReviewMapper.updateKcsjAchievementReviewList(kcsjAchievementReviewList);
    }

    @Transactional
    public int deleteKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview) {
        kcsjAchievementReview.setUpdateUser(SecurityUtils.getUserName());
        kcsjAchievementReview.setUpdateTime(DateUtils.getNowDate());
        return kcsjAchievementReviewMapper.deleteKcsjAchievementReview(kcsjAchievementReview);
    }

    @Transactional
    public int deleteKcsjAchievementReviewByPks(List<Long> kcsjAchievementReviewPkList) {
        return kcsjAchievementReviewMapper.deleteKcsjAchievementReviewByPks(kcsjAchievementReviewPkList);
    }
}
