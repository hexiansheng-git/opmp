package com.hhwy.sd.achievementReview.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.achievementReview.constant.AchievementReviewStatus;
import com.hhwy.sd.achievementReview.domain.KcsjAchievement;
import com.hhwy.sd.achievementReview.domain.KcsjAchievementReview;
import com.hhwy.sd.achievementReview.mapper.KcsjAchievementMapper;
import com.hhwy.sd.achievementReview.mapper.KcsjAchievementReviewMapper;
import com.hhwy.sd.achievementReview.service.IKcsjAchievementReviewService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2024-02-05 09:04:12
 * @remark
 */
@Service
public class KcsjAchievementReviewServiceImpl implements IKcsjAchievementReviewService {

    @Autowired
    private KcsjAchievementReviewMapper kcsjAchievementReviewMapper;

    @Autowired
    private KcsjAchievementMapper kcsjAchievementMapper;


    public KcsjAchievementReview getKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview) {
        return kcsjAchievementReviewMapper.getKcsjAchievementReview(kcsjAchievementReview);
    }

    public List<KcsjAchievementReview> getKcsjAchievementReviewList(KcsjAchievementReview kcsjAchievementReview) {
        return kcsjAchievementReviewMapper.getKcsjAchievementReviewList(kcsjAchievementReview);
    }

    @Transactional
    public int insertKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview) {
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

    @Override
    public void deleteById(Long id) {
        kcsjAchievementMapper.cleanForeignIdByForeignId(id);
        kcsjAchievementReviewMapper.deleteById(id);
    }

    @Transactional
    public int deleteKcsjAchievementReviewByPks(List<Long> kcsjAchievementReviewPkList) {
        return kcsjAchievementReviewMapper.deleteKcsjAchievementReviewByPks(kcsjAchievementReviewPkList);
    }

    @Override
    public KcsjAchievementReview getKcsjAchievementReviewById(Long id) {
        KcsjAchievementReview review = kcsjAchievementReviewMapper.getKcsjAchievementReviewById(id);
        if(review != null){
            List<KcsjAchievement> achievementList = kcsjAchievementMapper.getListByForeignId(id);
            review.setAchievementList(achievementList);
        }
        return review;
    }

    @Override
    @Transactional
    public Long save(KcsjAchievementReview review) {
        //保存类型  1：新增；2：修改
        String saveType = review.getSaveType();

        List<KcsjAchievement> achievementList = review.getAchievementList();
        this.setData(achievementList,review);

        review.setTaskStatus(null);
        Long id;
        if("1".equals(saveType) && review.getId() == null){
            //新增
            id = IdWorker.createId();
            review.setId(id);
            this.insertKcsjAchievementReview(review);
        }else if("2".equals(saveType)){
            //修改
            id = review.getId();
            review.setTaskStatus(null);
            this.updateKcsjAchievementReview(review);
        }else {
            throw new RuntimeException("保存类型错误");
        }

        this.relevancy(achievementList, id, saveType);
        return id;
    }

    private void setData(List<KcsjAchievement> achievementList,KcsjAchievementReview review){
        if(CollectionUtils.isEmpty(achievementList)){
            return;
        }
        String achievementName = achievementList.stream().map(KcsjAchievement::getAchievementName).filter(StringUtils::isNotBlank).collect(Collectors.joining(" , "));
        String reviewOpinion = achievementList.stream().map(KcsjAchievement::getReviewOpinion).filter(StringUtils::isNotBlank).collect(Collectors.joining(","));
        review.setAchievementName(achievementName);
        review.setReviewOpinion(reviewOpinion);
    }

    /**
     * 关联主子表
     * @param achievementList
     * @param id
     * @param saveType
     */
    private void relevancy(List<KcsjAchievement> achievementList, Long id, String saveType){
        if("1".equals(saveType) && CollectionUtils.isEmpty(achievementList)){
            return;
        }

        kcsjAchievementMapper.cleanForeignIdByForeignId(id);
        if(CollectionUtils.isEmpty(achievementList)){
            return;
        }

        for (KcsjAchievement achievement : achievementList) {
            achievement.setForeignId(id);
            achievement.setAchievementStatus(null);
        }
        kcsjAchievementMapper.updateKcsjAchievementList(achievementList);
    }

    @Override
    public void updateKcsjAchievementReviewProcess(Long id) {
        KcsjAchievementReview review = kcsjAchievementReviewMapper.getKcsjAchievementReviewById(id);
        review.setTaskStatus("4");
        kcsjAchievementReviewMapper.updateKcsjAchievementReview(review);

        kcsjAchievementMapper.updateReviewExpertByForeignId(id,review.getReviewExpert());
        kcsjAchievementMapper.updateAchievementStatusByForeignId(id,AchievementReviewStatus.REVIEWED);
    }

    @Override
    public void submit(Long id) {
        KcsjAchievementReview review = kcsjAchievementReviewMapper.getKcsjAchievementReviewById(id);
        review.setTaskStatus("1");
        kcsjAchievementReviewMapper.updateKcsjAchievementReview(review);

        kcsjAchievementMapper.updateAchievementStatusByForeignId(id,AchievementReviewStatus.UNDER_REVIEW);
    }
}
