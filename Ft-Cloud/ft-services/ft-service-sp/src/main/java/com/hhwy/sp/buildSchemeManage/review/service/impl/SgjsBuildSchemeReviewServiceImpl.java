package com.hhwy.sp.buildSchemeManage.review.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReview;
import com.hhwy.sp.buildSchemeManage.review.mapper.SgjsBuildSchemeReviewMapper;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark
 */
@Service
public class SgjsBuildSchemeReviewServiceImpl implements ISgjsBuildSchemeReviewService {

    @Autowired
    private SgjsBuildSchemeReviewMapper sgjsBuildSchemeReviewMapper;


    public SgjsBuildSchemeReview getSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        return sgjsBuildSchemeReviewMapper.getSgjsBuildSchemeReview(sgjsBuildSchemeReview);
    }

    public List<SgjsBuildSchemeReview> getSgjsBuildSchemeReviewList(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        return sgjsBuildSchemeReviewMapper.getSgjsBuildSchemeReviewList(sgjsBuildSchemeReview);
    }

    @Transactional
    public int insertSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        sgjsBuildSchemeReview.setId(IdWorker.createId());
        sgjsBuildSchemeReview.setCreateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReview.setCreateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewMapper.insertSgjsBuildSchemeReview(sgjsBuildSchemeReview);
    }

    @Transactional
    public int insertSgjsBuildSchemeReviewList(List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList) {
        for (SgjsBuildSchemeReview sgjsBuildSchemeReview : sgjsBuildSchemeReviewList) {
            sgjsBuildSchemeReview.setId(IdWorker.createId());
            sgjsBuildSchemeReview.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeReview.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeReviewMapper.insertSgjsBuildSchemeReviewList(sgjsBuildSchemeReviewList);
    }

    @Transactional
    public int updateSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        sgjsBuildSchemeReview.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReview.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewMapper.updateSgjsBuildSchemeReview(sgjsBuildSchemeReview);
    }

    @Transactional
    public int updateSgjsBuildSchemeReviewList(List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList) {
        for (SgjsBuildSchemeReview sgjsBuildSchemeReview : sgjsBuildSchemeReviewList) {
            sgjsBuildSchemeReview.setUpdateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeReview.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeReviewMapper.updateSgjsBuildSchemeReviewList(sgjsBuildSchemeReviewList);
    }

    @Transactional
    public int deleteSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview) {
        sgjsBuildSchemeReview.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReview.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewMapper.deleteSgjsBuildSchemeReview(sgjsBuildSchemeReview);
    }

    @Transactional
    public int deleteSgjsBuildSchemeReviewByPks(List<Long> sgjsBuildSchemeReviewPkList) {
        return sgjsBuildSchemeReviewMapper.deleteSgjsBuildSchemeReviewByPks(sgjsBuildSchemeReviewPkList);
    }
}
