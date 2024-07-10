package com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreStandard.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreStandard.domain.SgjsPaperScoreStandard;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreStandard.mapper.SgjsPaperScoreStandardMapper;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreStandard.service.ISgjsPaperScoreStandardService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fsd
 * @date 2024-07-10 16:38:46
 * @remark
 */
@Service
public class SgjsPaperScoreStandardServiceImpl implements ISgjsPaperScoreStandardService {

    @Autowired
    private SgjsPaperScoreStandardMapper sgjsPaperScoreStandardMapper;


    public SgjsPaperScoreStandard getSgjsPaperScoreStandard(SgjsPaperScoreStandard sgjsPaperScoreStandard) {
        return sgjsPaperScoreStandardMapper.getSgjsPaperScoreStandard(sgjsPaperScoreStandard);
    }

    public List<SgjsPaperScoreStandard> getSgjsPaperScoreStandardList(SgjsPaperScoreStandard sgjsPaperScoreStandard) {
        return sgjsPaperScoreStandardMapper.getSgjsPaperScoreStandardList(sgjsPaperScoreStandard);
    }

    @Transactional
    public int insertSgjsPaperScoreStandard(SgjsPaperScoreStandard sgjsPaperScoreStandard) {
        sgjsPaperScoreStandard.setId(IdWorker.createId());
        sgjsPaperScoreStandard.setCreateUser(SecurityUtils.getUserName());
        sgjsPaperScoreStandard.setCreateTime(DateUtils.getNowDate());
        return sgjsPaperScoreStandardMapper.insertSgjsPaperScoreStandard(sgjsPaperScoreStandard);
    }

    @Transactional
    public int insertSgjsPaperScoreStandardList(List<SgjsPaperScoreStandard> sgjsPaperScoreStandardList) {
        for (SgjsPaperScoreStandard sgjsPaperScoreStandard : sgjsPaperScoreStandardList) {
            sgjsPaperScoreStandard.setId(IdWorker.createId());
            sgjsPaperScoreStandard.setCreateUser(SecurityUtils.getUserName());
            sgjsPaperScoreStandard.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsPaperScoreStandardMapper.insertSgjsPaperScoreStandardList(sgjsPaperScoreStandardList);
    }

    @Transactional
    public int updateSgjsPaperScoreStandard(SgjsPaperScoreStandard sgjsPaperScoreStandard) {
        sgjsPaperScoreStandard.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperScoreStandard.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperScoreStandardMapper.updateSgjsPaperScoreStandard(sgjsPaperScoreStandard);
    }

    @Transactional
    public int updateSgjsPaperScoreStandardList(List<SgjsPaperScoreStandard> sgjsPaperScoreStandardList) {
        for (SgjsPaperScoreStandard sgjsPaperScoreStandard : sgjsPaperScoreStandardList) {
            sgjsPaperScoreStandard.setUpdateUser(SecurityUtils.getUserName());
            sgjsPaperScoreStandard.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsPaperScoreStandardMapper.updateSgjsPaperScoreStandardList(sgjsPaperScoreStandardList);
    }

    @Transactional
    public int deleteSgjsPaperScoreStandard(SgjsPaperScoreStandard sgjsPaperScoreStandard) {
        sgjsPaperScoreStandard.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperScoreStandard.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperScoreStandardMapper.deleteSgjsPaperScoreStandard(sgjsPaperScoreStandard);
    }

    @Transactional
    public int deleteSgjsPaperScoreStandardByPks(List<Long> sgjsPaperScoreStandardPkList) {
        return sgjsPaperScoreStandardMapper.deleteSgjsPaperScoreStandardByPks(sgjsPaperScoreStandardPkList);
    }
}
