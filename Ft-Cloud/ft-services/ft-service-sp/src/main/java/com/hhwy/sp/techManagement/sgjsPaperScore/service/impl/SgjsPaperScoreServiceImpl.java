package com.hhwy.sp.techManagement.sgjsPaperScore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.domain.SgjsPaperScoreRecord;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.service.ISgjsPaperScoreRecordService;
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
public class SgjsPaperScoreServiceImpl implements ISgjsPaperScoreService {

    @Autowired
    private SgjsPaperScoreMapper sgjsPaperScoreMapper;
    @Autowired
    private ISgjsPaperScoreRecordService sgjsPaperScoreRecordService;


    public SgjsPaperScore getSgjsPaperScore(SgjsPaperScore sgjsPaperScore) {
        return sgjsPaperScoreMapper.getSgjsPaperScore(sgjsPaperScore);
    }

    //台账查询
    public List<SgjsPaperScore> getSgjsPaperScoreList(SgjsPaperScore sgjsPaperScore) {
        List<SgjsPaperScore> sgjsPaperScoreList = sgjsPaperScoreMapper.getSgjsPaperScoreList(sgjsPaperScore);
        if (CollUtil.isEmpty(sgjsPaperScoreList)) return new ArrayList<>();
        List<Long> idList = sgjsPaperScoreList.stream().map(SgjsPaperScore::getId).collect(Collectors.toList());
        //查询评分记录表，得到评分人
        List<SgjsPaperScoreRecord> scoreRecordList = sgjsPaperScoreRecordService.getListByForeginId(idList);
        if (CollUtil.isEmpty(scoreRecordList)) return sgjsPaperScoreList;
        Map<Long, List<SgjsPaperScoreRecord>> scoreRecordMap = scoreRecordList.stream().collect(Collectors.groupingBy(SgjsPaperScoreRecord::getForeginId));
        for (SgjsPaperScore item : sgjsPaperScoreList ) {
            List<SgjsPaperScoreRecord> sgjsPaperScoreRecords = scoreRecordMap.get(item.getId());
            if (CollUtil.isEmpty(sgjsPaperScoreRecords)) continue;
            String collect = sgjsPaperScoreRecords.stream().map(SgjsPaperScoreRecord::getSpecialist).collect(Collectors.joining(","));
            item.setSpecialist(collect);
        }
        return sgjsPaperScoreList;
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
