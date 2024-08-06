package com.hhwy.sp.techManagement.sgjsPaperScore.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sp.common.warn.CommonBusiness;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.SgjsPaperPublish;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.vo.PaperPublishQueryVo;
import com.hhwy.sp.techManagement.sgjsPaperPublish.service.ISgjsPaperPublishService;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.domain.SgjsPaperScoreRecord;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.service.ISgjsPaperScoreRecordService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techManagement.sgjsPaperScore.mapper.SgjsPaperScoreMapper;
import com.hhwy.sp.techManagement.sgjsPaperScore.service.ISgjsPaperScoreService;
import com.hhwy.sp.techManagement.sgjsPaperScore.domain.SgjsPaperScore;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.Assert;

/**
 * @author fsd
 * @date 2024-07-10 16:38:08
 * @remark
 */
@Service
@Slf4j
public class SgjsPaperScoreServiceImpl implements ISgjsPaperScoreService {

    @Autowired
    private SgjsPaperScoreMapper sgjsPaperScoreMapper;
    @Autowired
    private ISgjsPaperPublishService sgjsPaperPublishService;
    @Autowired
    private ISgjsPaperScoreRecordService sgjsPaperScoreRecordService;
    @Autowired
    private PmServiceApi pmServiceApi;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${warn.paperScoreUrl}")
    private String paperScoreUrl;


    public SgjsPaperScore getSgjsPaperScore(SgjsPaperScore sgjsPaperScore) {
        return sgjsPaperScoreMapper.getSgjsPaperScore(sgjsPaperScore);
    }

    //导出
    public List<SgjsPaperScore> getExportData(SgjsPaperScore sgjsPaperScore) {
        List<Long> ids = sgjsPaperScore.getIds();
        List<SgjsPaperScore> result;
        if (CollUtil.isEmpty(ids)) {
            result = sgjsPaperScoreMapper.getSgjsPaperScoreList(sgjsPaperScore);
        } else {
            result = sgjsPaperScoreMapper.getListByIds(ids);
        }
        if (CollUtil.isEmpty(result)) {
            return new ArrayList<>();
        }else {
            this.setChildData(result);
            return result;
        }
    }

    //台账查询
    public List<SgjsPaperScore> getSgjsPaperScoreList(SgjsPaperScore sgjsPaperScore) {
        /* 查询表是否为空，为空则获取论文申请的数据 */
        List<SgjsPaperScore> allList = sgjsPaperScoreMapper.getSgjsPaperScoreList(new SgjsPaperScore());
        if (CollUtil.isEmpty(allList)) {
            //表为空，查询论文申请表（审批通过的）
            PaperPublishQueryVo paperPublishQueryVo = new PaperPublishQueryVo();
            paperPublishQueryVo.setCurrentState("3");
            List<SgjsPaperPublish> sgjsPaperPublishList = sgjsPaperPublishService.getSgjsPaperPublishList(paperPublishQueryVo);
            if (CollUtil.isEmpty(sgjsPaperPublishList)) {
                return new ArrayList<>();
            }
            List<SgjsPaperScore> sgjsPaperScores = BeanUtil.copyToList(sgjsPaperPublishList, SgjsPaperScore.class);
            sgjsPaperScores.forEach( p -> {
                p.setPtVar3(p.getId()+"");
                p.setProjectCode(p.getPtVar5());
                p.setPtVar5(null);
                p.setTaskStatus("0");
            });
            this.insertSgjsPaperScoreList(sgjsPaperScores);
            return sgjsPaperScoreMapper.getSgjsPaperScoreList(sgjsPaperScore);
        }
        /* 评分表有数据 */
        List<SgjsPaperScore> sgjsPaperScoreList = sgjsPaperScoreMapper.getSgjsPaperScoreList(sgjsPaperScore);
        if (CollUtil.isEmpty(sgjsPaperScoreList)) {
            return new ArrayList<>();
        }
        //set评分人信息
        this.setChildData(sgjsPaperScoreList);
        return sgjsPaperScoreList;
    }

    private void setChildData(List<SgjsPaperScore> sgjsPaperScoreList) {
        List<Long> idList = sgjsPaperScoreList.stream().map(SgjsPaperScore::getId).collect(Collectors.toList());
        //查询评分记录表，得到评分人
        List<SgjsPaperScoreRecord> scoreRecordList = sgjsPaperScoreRecordService.getListByForeginId(idList);
        if (CollUtil.isEmpty(scoreRecordList)) return;
        Map<Long, List<SgjsPaperScoreRecord>> scoreRecordMap = scoreRecordList.stream().collect(Collectors.groupingBy(SgjsPaperScoreRecord::getForeignId));
        for (SgjsPaperScore item : sgjsPaperScoreList) {
            List<SgjsPaperScoreRecord> sgjsPaperScoreRecords = scoreRecordMap.get(item.getId());
            if (CollUtil.isEmpty(sgjsPaperScoreRecords)) continue;
            String nikeNames = sgjsPaperScoreRecords.stream().map(SgjsPaperScoreRecord::getSpecialist).collect(Collectors.joining(","));
            //set评分专家姓名
            item.setSpecialist(nikeNames);
        }
    }

    //发起评审
    @Override
    public void reviewStart(Long[] ids) {
//        Assert.isTrue(ids != null, "ids不能为空");
//        /* 待评分论文信息查询 */
//        List<SgjsPaperScoreRecord> scoreRecordList = sgjsPaperScoreRecordService.getListByForeginId(Arrays.asList(ids));
//        Assert.isTrue(CollUtil.isEmpty(scoreRecordList), "待评分论文信息查询 查无数据");
//        Map<Long, List<SgjsPaperScoreRecord>> scoreRecordMap = scoreRecordList.stream().collect(Collectors.groupingBy(SgjsPaperScoreRecord::getForeignId));
//        /* 发送待办 */
//        //消息参数
//        ProjectDto projectDto = pmServiceApi.getProjectDto();
//        String warnMessage = "您好，【项目名称】的【预警项名称】需要进行评分操作 ，请及时进行查看。 预警规则：【预警规则】";
//        String warnItem = WarnItem.SGJS_PAPER_SCORE.getWarnItem();
//        String warnRule = WarnItem.SGJS_PAPER_SCORE.getWarnRule();
//        String warnContent = CommonBusiness.warnMessageHandle(warnMessage, projectDto.getProjectName(), warnItem, warnRule);
//        ArrayList<TWarn> warnInfo = new ArrayList<>();
//        scoreRecordMap.forEach((k, v) -> {
//            String userNames = v.stream().map(SgjsPaperScoreRecord::getPtVar2).collect(Collectors.joining(","));
//            TWarn tWarn = new TWarn();
//            tWarn.setWarnItem(warnItem);
//            tWarn.setWarnItemId(WarnItem.SGJS_PAPER_SCORE.getWarnItemId());
//            tWarn.setWarnScope(userNames);
//            tWarn.setWarnUrl(paperScoreUrl);
//            tWarn.setBusinessId(k);
//            tWarn.setWarnScopeType("3");
//            tWarn.setWarnContent(warnContent);
//            tWarn.setProjectName(projectDto.getProjectName());
//            tWarn.setTenantKey(projectDto.getProjectCode());
//            warnInfo.add(tWarn);
//        });
//        //发送
//        systemServiceApi.insertTWarnListToGm(warnInfo);
//        log.info("论文评分-发起评审 待办发送完成: {}", JSON.toJSONString(warnInfo));
//        /* 修改评审状态 */
//        String taskStatus = "1";
//        int i = sgjsPaperScoreMapper.updateByIds(Arrays.asList(ids), taskStatus);
//        /* 数据同步项目版 */
//        if (i > 0) {
//            List<SgjsPaperScore> listByIds = sgjsPaperScoreMapper.getListByIds(Arrays.asList(ids));
//            rocketMQTemplate.convertAndSend("sp_sgjs_paper_score:tenantSuccess", listByIds);
//            log.info("论文评分-发起评审 数据发送项目完成：{}", JSON.toJSONString(listByIds));
//        }
//        log.info("论文评分-发起评审 完成");
    }

    //结束流程
    @Override
    public void reviewEnd(Long[] ids) {
//        Assert.isTrue(ids != null, "ids不能为空");
//        //修改主表状态
//        String taskStatus = "2";
//        sgjsPaperScoreMapper.updateByIds(Arrays.asList(ids), taskStatus);
//        List<SgjsPaperScoreRecord> scoreRecordList = sgjsPaperScoreRecordService.getListByForeginId(Arrays.asList(ids));
//        //将未评分的记录删除
//        List<SgjsPaperScoreRecord> collect = scoreRecordList.stream().filter(p -> p.getSubmitTime() == null).collect(Collectors.toList());
//        if (CollUtil.isNotEmpty(collect)) {
//            List<Long> collect1 = collect.stream().map(SgjsPaperScoreRecord::getId).collect(Collectors.toList());
//            sgjsPaperScoreRecordService.deleteSgjsPaperScoreRecordByPks(collect1);
//        }
    }

    @Transactional
    public int insertSgjsPaperScore(SgjsPaperScore sgjsPaperScore) {
        sgjsPaperScore.setId(IdWorker.createId());
        sgjsPaperScore.setCreateUser(SecurityUtils.getUserName());
        sgjsPaperScore.setCreateTime(DateUtils.getNowDate());
        return sgjsPaperScoreMapper.insertSgjsPaperScore(sgjsPaperScore);
    }

    //保存
    public int insertSgjsPaperScoreList(List<SgjsPaperScore> sgjsPaperScoreList) {
        for (SgjsPaperScore sgjsPaperScore : sgjsPaperScoreList) {
            sgjsPaperScore.setId(IdWorker.createId());
            sgjsPaperScore.setCreateUser(SecurityUtils.getUserName());
            sgjsPaperScore.setCreateTime(DateUtils.getNowDate());
        }
        int i = sgjsPaperScoreMapper.insertSgjsPaperScoreList(sgjsPaperScoreList);
        log.info("论文评分-保存完成, 发送总部数据......");
        if (i > 0) {
            //推送总部
            HashMap<String, Object> map = new HashMap<>();
//            map.put("paperScoreRecord", sgjsPaperScoreRecord);
            map.put("paperScore", sgjsPaperScoreList);
            log.info("论文评分-发送总部数据：{}", JSON.toJSONString(map));
            rocketMQTemplate.convertAndSend("gm_sgjs_paper_score:tenantSuccess", map);
        }
        return i;
    }

    //修改
    @Transactional
    public int updateSgjsPaperScore(SgjsPaperScore sgjsPaperScore) {
        sgjsPaperScore.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperScore.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperScoreMapper.updateSgjsPaperScore(sgjsPaperScore);
    }

    @Transactional
    public int updateSgjsPaperScoreList(List<SgjsPaperScore> sgjsPaperScoreList) {
        if (CollUtil.isEmpty(sgjsPaperScoreList)) {
            log.warn("保存论文list为空");
            return 0;
        }
        for (SgjsPaperScore sgjsPaperScore : sgjsPaperScoreList) {
//            sgjsPaperScore.setUpdateUser(SecurityUtils.getUserName());
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
