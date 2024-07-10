package com.hhwy.sp.techManagement.sgjsPaperScore.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techManagement.sgjsPaperScore.mapper.SgjsPaperScoreMapper;
import com.hhwy.sp.techManagement.sgjsPaperScore.service.ISgjsPaperScoreService;
import com.hhwy.sp.techManagement.sgjsPaperScore.domain.SgjsPaperScore;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author fsd
 * @date 2024-07-10 16:38:08
 * @remark 
 */
@Service
public class SgjsPaperScoreServiceImpl implements ISgjsPaperScoreService{

    @Autowired
    private SgjsPaperScoreMapper sgjsPaperScoreMapper;

                                                                                                                                                                                                                                                                                                                                                                            
    public SgjsPaperScore getSgjsPaperScore(SgjsPaperScore sgjsPaperScore) {
        return sgjsPaperScoreMapper.getSgjsPaperScore(sgjsPaperScore);
    }

    public List<SgjsPaperScore> getSgjsPaperScoreList(SgjsPaperScore sgjsPaperScore) {
        return sgjsPaperScoreMapper.getSgjsPaperScoreList(sgjsPaperScore);
    }

    @Transactional
    public int insertSgjsPaperScore(SgjsPaperScore sgjsPaperScore) {
        sgjsPaperScore.setId(IdWorker.createId());
        sgjsPaperScore.setCreateUser(SecurityUtils.getUserName());
        sgjsPaperScore.setCreateTime(DateUtils.getNowDate());
        return sgjsPaperScoreMapper.insertSgjsPaperScore(sgjsPaperScore);
    }

    @Transactional
    public int insertSgjsPaperScoreList(List<SgjsPaperScore> sgjsPaperScoreList) {
        for (SgjsPaperScore sgjsPaperScore : sgjsPaperScoreList) {
            sgjsPaperScore.setId(IdWorker.createId());
            sgjsPaperScore.setCreateUser(SecurityUtils.getUserName());
            sgjsPaperScore.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsPaperScoreMapper.insertSgjsPaperScoreList(sgjsPaperScoreList);
    }

    @Transactional
    public int updateSgjsPaperScore(SgjsPaperScore sgjsPaperScore) {
        sgjsPaperScore.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperScore.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperScoreMapper.updateSgjsPaperScore(sgjsPaperScore);
    }

            @Transactional
        public int updateSgjsPaperScoreList(List<SgjsPaperScore> sgjsPaperScoreList) {
            for (SgjsPaperScore sgjsPaperScore : sgjsPaperScoreList) {
                sgjsPaperScore.setUpdateUser(SecurityUtils.getUserName());
                sgjsPaperScore.setUpdateTime(DateUtils.getNowDate());
            }
            return sgjsPaperScoreMapper.updateSgjsPaperScoreList(sgjsPaperScoreList);
        }
    
    @Transactional
    public int deleteSgjsPaperScore(SgjsPaperScore sgjsPaperScore) {
        sgjsPaperScore.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperScore.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperScoreMapper.deleteSgjsPaperScore(sgjsPaperScore);
    }

            @Transactional
        public int deleteSgjsPaperScoreByPks(List<Long> sgjsPaperScorePkList) {
            return sgjsPaperScoreMapper.deleteSgjsPaperScoreByPks(sgjsPaperScorePkList);
        }
    }
