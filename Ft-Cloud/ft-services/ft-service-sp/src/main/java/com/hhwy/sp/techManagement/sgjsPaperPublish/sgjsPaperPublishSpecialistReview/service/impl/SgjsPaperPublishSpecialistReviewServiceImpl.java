package com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.domain.SgjsPaperPublishSpecialistReview;
import com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.mapper.SgjsPaperPublishSpecialistReviewMapper;
import com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.service.ISgjsPaperPublishSpecialistReviewService;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author fsd
 * @date 2024-07-24 14:15:53
 * @remark 论文申请专家评审记录
 */
@Service
public class SgjsPaperPublishSpecialistReviewServiceImpl implements ISgjsPaperPublishSpecialistReviewService {

    @Autowired
    private SgjsPaperPublishSpecialistReviewMapper sgjsPaperPublishSpecialistReviewMapper;


    public SgjsPaperPublishSpecialistReview getSgjsPaperPublishSpecialistReview(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview) {
        return sgjsPaperPublishSpecialistReviewMapper.getSgjsPaperPublishSpecialistReview(sgjsPaperPublishSpecialistReview);
    }

    public List<SgjsPaperPublishSpecialistReview> getSgjsPaperPublishSpecialistReviewList(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview) {
        return sgjsPaperPublishSpecialistReviewMapper.getSgjsPaperPublishSpecialistReviewList(sgjsPaperPublishSpecialistReview);
    }

    //保存
    @Transactional
    public int insertSgjsPaperPublishSpecialistReview(SgjsPaperPublishSpecialistReview param) {
        if (ObjectUtils.isEmpty(param)) {
            return 0;
        }
        Integer reviewStage = 1;
        SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview = new SgjsPaperPublishSpecialistReview();
        sgjsPaperPublishSpecialistReview.setForeignId(param.getForeignId());
        List<SgjsPaperPublishSpecialistReview> sgjsPaperPublishSpecialistReviewList = sgjsPaperPublishSpecialistReviewMapper.getSgjsPaperPublishSpecialistReviewList(sgjsPaperPublishSpecialistReview);
        if (CollUtil.isNotEmpty(sgjsPaperPublishSpecialistReviewList)) {
            SgjsPaperPublishSpecialistReview resultOne = sgjsPaperPublishSpecialistReviewList.stream().max(Comparator.comparing(SgjsPaperPublishSpecialistReview::getReviewStage)).get();
            reviewStage = resultOne.getReviewStage();
            reviewStage += 1;
        }
        SysUser sysUser = SecurityUtils.getSysUser();
        param.setReviewStage(reviewStage);
        param.setSpecialist(sysUser.getNickName());
        param.setSpecialistId(String.valueOf(sysUser.getUserId()));
        param.setPtVar2(sysUser.getUserName());
        param.setId(IdWorker.createId());
        param.setCreateUser(SecurityUtils.getUserName());
        param.setCreateTime(DateUtils.getNowDate());
        return sgjsPaperPublishSpecialistReviewMapper.insertSgjsPaperPublishSpecialistReview(param);
    }

    @Transactional
    public int insertSgjsPaperPublishSpecialistReviewList(List<SgjsPaperPublishSpecialistReview> sgjsPaperPublishSpecialistReviewList) {
        if (CollUtil.isEmpty(sgjsPaperPublishSpecialistReviewList)) {
            return 0;
        }
        for (SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview : sgjsPaperPublishSpecialistReviewList) {
            sgjsPaperPublishSpecialistReview.setId(IdWorker.createId());
            sgjsPaperPublishSpecialistReview.setCreateUser(SecurityUtils.getUserName());
            sgjsPaperPublishSpecialistReview.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsPaperPublishSpecialistReviewMapper.insertSgjsPaperPublishSpecialistReviewList(sgjsPaperPublishSpecialistReviewList);
    }

    @Transactional
    public int updateSgjsPaperPublishSpecialistReview(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview) {
        sgjsPaperPublishSpecialistReview.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperPublishSpecialistReview.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperPublishSpecialistReviewMapper.updateSgjsPaperPublishSpecialistReview(sgjsPaperPublishSpecialistReview);
    }

    @Transactional
    public int updateSgjsPaperPublishSpecialistReviewList(List<SgjsPaperPublishSpecialistReview> sgjsPaperPublishSpecialistReviewList) {
        for (SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview : sgjsPaperPublishSpecialistReviewList) {
            sgjsPaperPublishSpecialistReview.setUpdateUser(SecurityUtils.getUserName());
            sgjsPaperPublishSpecialistReview.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsPaperPublishSpecialistReviewMapper.updateSgjsPaperPublishSpecialistReviewList(sgjsPaperPublishSpecialistReviewList);
    }

    @Transactional
    public int deleteSgjsPaperPublishSpecialistReview(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview) {
        sgjsPaperPublishSpecialistReview.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperPublishSpecialistReview.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperPublishSpecialistReviewMapper.deleteSgjsPaperPublishSpecialistReview(sgjsPaperPublishSpecialistReview);
    }

    @Transactional
    public int deleteSgjsPaperPublishSpecialistReviewByPks(List<Long> sgjsPaperPublishSpecialistReviewPkList) {
        return sgjsPaperPublishSpecialistReviewMapper.deleteSgjsPaperPublishSpecialistReviewByPks(sgjsPaperPublishSpecialistReviewPkList);
    }
}
