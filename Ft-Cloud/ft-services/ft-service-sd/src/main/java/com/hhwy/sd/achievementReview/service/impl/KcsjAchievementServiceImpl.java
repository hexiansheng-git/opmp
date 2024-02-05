package com.hhwy.sd.achievementReview.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.achievementReview.domain.KcsjAchievement;
import com.hhwy.sd.achievementReview.domain.vo.AchievementQueryVo;
import com.hhwy.sd.achievementReview.domain.vo.AchievementVo;
import com.hhwy.sd.achievementReview.mapper.KcsjAchievementMapper;
import com.hhwy.sd.achievementReview.service.IKcsjAchievementService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<KcsjAchievement> getKcsjAchievementList(AchievementQueryVo queryVo) {
        return kcsjAchievementMapper.getKcsjAchievementList(queryVo);
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

    @Override
    @Transactional
    public void save(AchievementVo achievementVo) {
        List<KcsjAchievement> achievementList = achievementVo.getAchievementList();
        List<Long> delIdList = achievementVo.getDelIdList();
        Set<Long> delIdSet = new HashSet<>(delIdList);
        achievementList = achievementList.stream().filter(o -> !delIdSet.contains(o.getId())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(achievementList) && CollectionUtils.isEmpty(delIdList)){
            return;
        }
        List<KcsjAchievement> addList = new ArrayList<>();
        List<KcsjAchievement> updateList = new ArrayList<>();

        for (KcsjAchievement achievement : achievementList) {
            if("1".equals(achievement.getIsAdd())) {
                addList.add(achievement);
            } else {
                achievement.setUpdateUser(SecurityUtils.getSysUser().getNickName());
                achievement.setUpdateTime(DateUtils.getNowDate());
                updateList.add(achievement);
            }
        }
        if(CollectionUtils.isNotEmpty(addList)) {
            this.insertKcsjAchievementList(addList);
        }
        if(CollectionUtils.isNotEmpty(updateList)) {
            kcsjAchievementMapper.updateKcsjAchievementList(updateList);
        }

        if(CollectionUtils.isNotEmpty(delIdList)){
            kcsjAchievementMapper.deleteKcsjAchievementByPks(delIdList);
        }
    }

    @Override
    public List<KcsjAchievement> getListByIds(List<Long> ids) {
        return kcsjAchievementMapper.getListByIds(ids);
    }
}
