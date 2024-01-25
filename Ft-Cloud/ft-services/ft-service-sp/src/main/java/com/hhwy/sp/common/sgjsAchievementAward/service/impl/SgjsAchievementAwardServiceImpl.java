package com.hhwy.sp.common.sgjsAchievementAward.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsAchievementAward.mapper.SgjsAchievementAwardMapper;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 09:45:38
 * @remark
 */
@Service
public class SgjsAchievementAwardServiceImpl implements ISgjsAchievementAwardService {

    @Autowired
    private SgjsAchievementAwardMapper sgjsAchievementAwardMapper;


    public SgjsAchievementAward getSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward) {
        return sgjsAchievementAwardMapper.getSgjsAchievementAward(sgjsAchievementAward);
    }

    public List<SgjsAchievementAward> getSgjsAchievementAwardList(SgjsAchievementAward sgjsAchievementAward) {
        return sgjsAchievementAwardMapper.getSgjsAchievementAwardList(sgjsAchievementAward);
    }

    @Transactional
    public int insertSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward) {
        sgjsAchievementAward.setId(IdWorker.createId());
        sgjsAchievementAward.setCreateUser(SecurityUtils.getUserName());
        sgjsAchievementAward.setCreateTime(DateUtils.getNowDate());
        return sgjsAchievementAwardMapper.insertSgjsAchievementAward(sgjsAchievementAward);
    }

    @Transactional
    public int insertSgjsAchievementAwardList(List<SgjsAchievementAward> sgjsAchievementAwardList) {
        for (SgjsAchievementAward sgjsAchievementAward : sgjsAchievementAwardList) {
            sgjsAchievementAward.setId(IdWorker.createId());
            sgjsAchievementAward.setCreateUser(SecurityUtils.getUserName());
            sgjsAchievementAward.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsAchievementAwardMapper.insertSgjsAchievementAwardList(sgjsAchievementAwardList);
    }

    @Transactional
    public int updateSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward) {
        sgjsAchievementAward.setUpdateUser(SecurityUtils.getUserName());
        sgjsAchievementAward.setUpdateTime(DateUtils.getNowDate());
        return sgjsAchievementAwardMapper.updateSgjsAchievementAward(sgjsAchievementAward);
    }

    @Transactional
    public int updateSgjsAchievementAwardList(List<SgjsAchievementAward> sgjsAchievementAwardList) {
        for (SgjsAchievementAward sgjsAchievementAward : sgjsAchievementAwardList) {
            sgjsAchievementAward.setUpdateUser(SecurityUtils.getUserName());
            sgjsAchievementAward.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsAchievementAwardMapper.updateSgjsAchievementAwardList(sgjsAchievementAwardList);
    }

    @Transactional
    public int deleteSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward) {
        sgjsAchievementAward.setUpdateUser(SecurityUtils.getUserName());
        sgjsAchievementAward.setUpdateTime(DateUtils.getNowDate());
        return sgjsAchievementAwardMapper.deleteSgjsAchievementAward(sgjsAchievementAward);
    }

    @Transactional
    public int deleteSgjsAchievementAwardByPks(List<Long> sgjsAchievementAwardPkList) {
        return sgjsAchievementAwardMapper.deleteSgjsAchievementAwardByPks(sgjsAchievementAwardPkList);
    }
}
