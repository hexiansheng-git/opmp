package com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.service.impl;

import cn.afterturn.easypoi.cache.manager.IFileLoader;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsWarnConfig;
import com.hhwy.sp.common.warn.CommonBusiness;
import com.hhwy.sp.techManagement.sgjsPaperScore.domain.SgjsPaperScore;
import com.hhwy.sp.techManagement.sgjsPaperScore.domain.SgjsPaperScoreAverageRule;
import com.hhwy.sp.techManagement.sgjsPaperScore.service.ISgjsPaperScoreService;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.domain.SgjsPaperScoreRecord;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.mapper.SgjsPaperScoreRecordMapper;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.service.ISgjsPaperScoreRecordService;
import com.hhwy.system.api.domain.SysRole;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.idworker.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
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

    @Value("${gm.url}")
    private String gmUrl;

    public SgjsPaperScoreRecord getSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord) {
        return sgjsPaperScoreRecordMapper.getSgjsPaperScoreRecord(sgjsPaperScoreRecord);
    }

    //查询
    public List<SgjsPaperScoreRecord> getSgjsPaperScoreRecordList(SgjsPaperScoreRecord sgjsPaperScoreRecord) {
        List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList = sgjsPaperScoreRecordMapper.getSgjsPaperScoreRecordList(sgjsPaperScoreRecord);
        if (CollUtil.isEmpty(sgjsPaperScoreRecordList)) return new ArrayList<>();
        SysUser sysUser = SecurityUtils.getSysUser();
        String userName = sysUser.getUserName();
//        String roleList = sysUser.getRoleList().stream().map(SysRole::getRoleKey).collect(Collectors.joining(","));
//        //如果当前登陆用户是领导或者超管角色, 展示全部数据
//        if (roleList.contains("leader") || roleList.contains("admin") || userName.equals("admin")) {
//            sgjsPaperScoreRecordList.forEach(p -> {
//                if (p.getPtVar2().equals(userName) && p.getWeightingScore() == null) {
//                    p.setPtVar3("1");
//                }else {
//                    p.setPtVar3(null);
//                }
//            });
//            return sgjsPaperScoreRecordList;
//        }
//        //如果当前登陆用户非领导或者超管角色，则只展示自己的数据
        //只展示自己的数据
        List<SgjsPaperScoreRecord> resultList = sgjsPaperScoreRecordList.stream().filter(p -> p.getPtVar2().equals(userName)).collect(Collectors.toList());
        String usernames = sgjsPaperScoreRecordList.stream().map(SgjsPaperScoreRecord::getPtVar2).collect(Collectors.joining());
        resultList.forEach(p -> {
            //设置可编辑状态
            if (usernames.contains(userName) && p.getWeightingScore() == null) {
                p.setPtVar3("1");
            }else {
                p.setPtVar3(null);
            }
        });
        return resultList;
    }

    //评分保存  （专家评分后保存）
    @Transactional
    public int updateSgjsPaperScoreRecord(List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList) {
        for (SgjsPaperScoreRecord sgjsPaperScoreRecord : sgjsPaperScoreRecordList) {
            Assert.isTrue(sgjsPaperScoreRecord.getId() != null, "id不能为空");
            //保存评分信息
            sgjsPaperScoreRecord.setSubmitTime(DateUtils.getNowDate());
            sgjsPaperScoreRecord.setUpdateUser(SecurityUtils.getUserName());
            sgjsPaperScoreRecord.setUpdateTime(DateUtils.getNowDate());
            int i = sgjsPaperScoreRecordMapper.updateSgjsPaperScoreRecord(sgjsPaperScoreRecord);
            Assert.isTrue(i > 0, "提交失败");
        }
        SgjsPaperScoreRecord sgjsPaperScoreRecord = sgjsPaperScoreRecordList.get(0);
        /*计算平均分*/
        ArrayList<Long> objects = new ArrayList<>();
        objects.add(sgjsPaperScoreRecord.getForeignId());
        List<SgjsPaperScoreRecord> listByForeignId = sgjsPaperScoreRecordMapper.getListByforeignId(objects);
        //判断是否所有专家完成评分
        List<SgjsPaperScoreRecord> collect = listByForeignId.stream().filter(p -> p.getWeightingScore() == null).collect(Collectors.toList());
        double averagingInt = 0L;
        if (CollUtil.isNotEmpty(collect)) {
            //部分专家完成评分
            averagingInt = listByForeignId.stream().filter(p -> p.getWeightingScore() != null ).collect(Collectors.averagingInt(SgjsPaperScoreRecord::getWeightingScore));
        } else {
            //所有专家都完成评分
            //过滤最高分和最低分
            String url = gmUrl + "/gm/sgjsPaperScoreAverageRule/list";
            //总部获取平均分规则
            SgjsPaperScoreAverageRule sgjsPaperScoreAverageRule = CommonBusiness.getSgjsAverageRule(url);
            if (null != sgjsPaperScoreAverageRule && (sgjsPaperScoreAverageRule.getHighNum() > 0 || sgjsPaperScoreAverageRule.getLowNum() > 0)
                                                    && sgjsPaperScoreAverageRule.getHighNum() + sgjsPaperScoreAverageRule.getLowNum() < listByForeignId.size()) {
                log.error("获取平均分计算规则：{}", JSON.toJSONString(sgjsPaperScoreAverageRule));
                Integer highNum = sgjsPaperScoreAverageRule.getHighNum();
                Integer lowNum = sgjsPaperScoreAverageRule.getLowNum();
                List<SgjsPaperScoreRecord> allList = new ArrayList<>();
                if (highNum > 0) {
                    listByForeignId.sort(Comparator.comparing(SgjsPaperScoreRecord::getWeightingScore).reversed());
                    allList = listByForeignId.subList(highNum, listByForeignId.size());
                }
                if (lowNum > 0) {
                    if (CollUtil.isEmpty(allList)) {
                        allList = listByForeignId;
                    }
                    allList.sort(Comparator.comparing(SgjsPaperScoreRecord::getWeightingScore));
                    allList = allList.subList(lowNum, allList.size());
                }
                averagingInt = allList.stream().filter(p -> p.getWeightingScore() != null ).collect(Collectors.averagingInt(SgjsPaperScoreRecord::getWeightingScore));
            }else {
                averagingInt = listByForeignId.stream().filter(p -> p.getWeightingScore() != null ).collect(Collectors.averagingInt(SgjsPaperScoreRecord::getWeightingScore));
                log.info("获取平均分计算规则无数据或配置为0：{}", JSON.toJSONString(sgjsPaperScoreAverageRule));
            }
        }
        //改主表平均分
        SgjsPaperScore sgjsPaperScore = new SgjsPaperScore();
//        if (CollUtil.isEmpty(collect)) {
//            //所有专家评分完成，则结束
//            sgjsPaperScore.setTaskStatus("2");
//        }
        sgjsPaperScore.setId(sgjsPaperScoreRecord.getForeignId());
        sgjsPaperScore.setAverageScore((int)averagingInt);
        sgjsPaperScoreService.updateSgjsPaperScore(sgjsPaperScore);
        //数据同步总部
        Map<String, Object> map = new HashMap<>();
//        ArrayList<SgjsPaperScoreRecord> objects1 = new ArrayList<>();
//        objects1.add(sgjsPaperScoreRecord);
        map.put("paperScoreRecord", sgjsPaperScoreRecordList);
        SgjsPaperScore sgjsPaperScore1 = new SgjsPaperScore();
        sgjsPaperScore1.setId(sgjsPaperScoreRecord.getForeignId());
        ArrayList<SgjsPaperScore> objects2 = new ArrayList<>();
        objects2.add(sgjsPaperScoreService.getSgjsPaperScore(sgjsPaperScore1));
        map.put("paperScore", objects2);
        rocketMQTemplate.convertAndSend("gm_sgjs_paper_score:tenantSuccess", map);
        log.info("论文评分 数据同步总部：{}", JSON.toJSONString(map));
        return 1;
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
        if (CollUtil.isEmpty(sgjsPaperScoreRecordList)) {
            log.warn("保存论文评分list为空");
            return 0;
        }
        for (SgjsPaperScoreRecord sgjsPaperScoreRecord : sgjsPaperScoreRecordList) {
//            sgjsPaperScoreRecord.setId(IdWorker.createId());
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
