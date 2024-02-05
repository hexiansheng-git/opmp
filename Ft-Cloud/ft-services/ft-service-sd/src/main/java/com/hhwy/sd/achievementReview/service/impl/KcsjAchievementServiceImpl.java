package com.hhwy.sd.achievementReview.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.achievementReview.domain.KcsjAchievement;
import com.hhwy.sd.achievementReview.mapper.KcsjAchievementMapper;
import com.hhwy.sd.achievementReview.service.IKcsjAchievementService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-02-05 09:04:08
 * @remark
 */
@Service
public class KcsjAchievementServiceImpl implements IKcsjAchievementService {

    @Autowired
    private KcsjAchievementMapper kcsjAchievementMapper;


    public KcsjAchievement getKcsjAchievement(KcsjAchievement kcsjAchievement) {
        return kcsjAchievementMapper.getKcsjAchievement(kcsjAchievement);
    }

    public List<KcsjAchievement> getKcsjAchievementList(KcsjAchievement kcsjAchievement) {
        return kcsjAchievementMapper.getKcsjAchievementList(kcsjAchievement);
    }

    @Transactional
    public int insertKcsjAchievement(KcsjAchievement kcsjAchievement) {
        kcsjAchievement.setId(IdWorker.createId());
        kcsjAchievement.setCreateUser(SecurityUtils.getUserName());
        kcsjAchievement.setCreateTime(DateUtils.getNowDate());
        return kcsjAchievementMapper.insertKcsjAchievement(kcsjAchievement);
    }

    @Transactional
    public int insertKcsjAchievementList(List<KcsjAchievement> kcsjAchievementList) {
        for (KcsjAchievement kcsjAchievement : kcsjAchievementList) {
            kcsjAchievement.setId(IdWorker.createId());
            kcsjAchievement.setCreateUser(SecurityUtils.getUserName());
            kcsjAchievement.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjAchievementMapper.insertKcsjAchievementList(kcsjAchievementList);
    }

    @Transactional
    public int updateKcsjAchievement(KcsjAchievement kcsjAchievement) {
        kcsjAchievement.setUpdateUser(SecurityUtils.getUserName());
        kcsjAchievement.setUpdateTime(DateUtils.getNowDate());
        return kcsjAchievementMapper.updateKcsjAchievement(kcsjAchievement);
    }

    @Transactional
    public int updateKcsjAchievementList(List<KcsjAchievement> kcsjAchievementList) {
        for (KcsjAchievement kcsjAchievement : kcsjAchievementList) {
            kcsjAchievement.setUpdateUser(SecurityUtils.getUserName());
            kcsjAchievement.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjAchievementMapper.updateKcsjAchievementList(kcsjAchievementList);
    }

    @Transactional
    public int deleteKcsjAchievement(KcsjAchievement kcsjAchievement) {
        kcsjAchievement.setUpdateUser(SecurityUtils.getUserName());
        kcsjAchievement.setUpdateTime(DateUtils.getNowDate());
        return kcsjAchievementMapper.deleteKcsjAchievement(kcsjAchievement);
    }

    @Transactional
    public int deleteKcsjAchievementByPks(List<Long> kcsjAchievementPkList) {
        return kcsjAchievementMapper.deleteKcsjAchievementByPks(kcsjAchievementPkList);
    }
}
