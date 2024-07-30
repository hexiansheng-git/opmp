package com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.hash.Hash;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techManagement.sgjsPaperScore.domain.SgjsPaperScore;
import com.hhwy.sp.techManagement.sgjsPaperScore.service.ISgjsPaperScoreService;
import com.hhwy.sp.techManagement.sgjsPaperScore.service.impl.SgjsPaperScoreServiceImpl;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.domain.SgjsPaperScoreRecord;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.mapper.SgjsPaperScoreRecordMapper;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.service.ISgjsPaperScoreRecordService;
import com.hhwy.utils.idworker.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fsd
 * @date 2024-07-10 16:38:37
 * @remark
 */
@Service
@Slf4j
public class SgjsPaperScoreRecordServiceImpl implements ISgjsPaperScoreRecordService {

    @Autowired
    private SgjsPaperScoreRecordMapper sgjsPaperScoreRecordMapper;
    @Autowired
    private ISgjsPaperScoreService sgjsPaperScoreService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    public SgjsPaperScoreRecord getSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord) {
        return sgjsPaperScoreRecordMapper.getSgjsPaperScoreRecord(sgjsPaperScoreRecord);
    }

    //查询
    public List<SgjsPaperScoreRecord> getSgjsPaperScoreRecordList(SgjsPaperScoreRecord sgjsPaperScoreRecord) {
        List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList = sgjsPaperScoreRecordMapper.getSgjsPaperScoreRecordList(sgjsPaperScoreRecord);
        if (CollUtil.isEmpty(sgjsPaperScoreRecordList)) return new ArrayList<>();
        //如果当前登陆用户是评分专家，则只展示自己的数据
        String usernames = sgjsPaperScoreRecordList.stream().map(SgjsPaperScoreRecord::getPtVar2).collect(Collectors.joining());
        if (usernames.contains(SecurityUtils.getUserName())) {
            sgjsPaperScoreRecordList = sgjsPaperScoreRecordList.stream().filter(p -> p.getPtVar2().equals(SecurityUtils.getUserName())).collect(Collectors.toList());
        }
        return sgjsPaperScoreRecordList;
    }

    //评分保存  （专家评分后保存）
    @Transactional
    public int updateSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord) {
        Assert.isTrue(sgjsPaperScoreRecord.getId() != null, "id不能为空");
        //保存评分信息
        sgjsPaperScoreRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperScoreRecord.setUpdateTime(DateUtils.getNowDate());
        int i = sgjsPaperScoreRecordMapper.updateSgjsPaperScoreRecord(sgjsPaperScoreRecord);
        Assert.isTrue(i > 0, "提交失败");
        //计算平均分
        ArrayList<Long> objects = new ArrayList<>();
        objects.add(sgjsPaperScoreRecord.getForeignId());
        List<SgjsPaperScoreRecord> listByForeignId = sgjsPaperScoreRecordMapper.getListByforeignId(objects);
        double collect = listByForeignId.stream().collect(Collectors.averagingInt(SgjsPaperScoreRecord::getWeightingScore));
        //需改主表平均分
        SgjsPaperScore sgjsPaperScore = new SgjsPaperScore();
        sgjsPaperScore.setId(sgjsPaperScoreRecord.getForeignId());
        sgjsPaperScore.setAverageScore((int)collect);
        sgjsPaperScoreService.updateSgjsPaperScore(sgjsPaperScore);
        //数据同步总部
        Map<String, Object> map = new HashMap<>();
        map.put("paperScoreRecord", sgjsPaperScoreRecord);
        map.put("paperScore", sgjsPaperScore);
        rocketMQTemplate.convertAndSend("gm_sgjs_paper_score:tenantSuccess", map);
        return i;
    }

    @Transactional
    public int insertSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord) {
        sgjsPaperScoreRecord.setId(IdWorker.createId());
        sgjsPaperScoreRecord.setCreateUser(SecurityUtils.getUserName());
        sgjsPaperScoreRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsPaperScoreRecordMapper.insertSgjsPaperScoreRecord(sgjsPaperScoreRecord);
    }

    @Transactional
    public int insertSgjsPaperScoreRecordList(List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList) {
        for (SgjsPaperScoreRecord sgjsPaperScoreRecord : sgjsPaperScoreRecordList) {
            sgjsPaperScoreRecord.setId(IdWorker.createId());
            sgjsPaperScoreRecord.setCreateUser(SecurityUtils.getUserName());
            sgjsPaperScoreRecord.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsPaperScoreRecordMapper.insertSgjsPaperScoreRecordList(sgjsPaperScoreRecordList);
    }

    @Transactional
    public int updateSgjsPaperScoreRecordList(List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList) {
        if (CollUtil.isEmpty(sgjsPaperScoreRecordList)) {
            log.warn("保存论文评分记录list为空");
            return 0;
        }
        for (SgjsPaperScoreRecord sgjsPaperScoreRecord : sgjsPaperScoreRecordList) {
//            sgjsPaperScoreRecord.setUpdateUser(SecurityUtils.getUserName());
            sgjsPaperScoreRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsPaperScoreRecordMapper.updateSgjsPaperScoreRecordList(sgjsPaperScoreRecordList);
    }

    @Transactional
    public int deleteSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord) {
        sgjsPaperScoreRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperScoreRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperScoreRecordMapper.deleteSgjsPaperScoreRecord(sgjsPaperScoreRecord);
    }

    @Transactional
    public int deleteSgjsPaperScoreRecordByPks(List<Long> sgjsPaperScoreRecordPkList) {
        return sgjsPaperScoreRecordMapper.deleteSgjsPaperScoreRecordByPks(sgjsPaperScoreRecordPkList);
    }

    @Override
    public List<SgjsPaperScoreRecord> getListByForeginId(List<Long> idList) {
        return sgjsPaperScoreRecordMapper.getListByforeignId(idList);
    }
}
