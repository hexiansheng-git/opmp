package com.hhwy.sp.buildSchemeManage.review.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewOpinion;
import com.hhwy.sp.buildSchemeManage.review.mapper.SgjsBuildSchemeReviewOpinionMapper;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewOpinionService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:39
 * @remark
 */
@Service
public class SgjsBuildSchemeReviewOpinionServiceImpl implements ISgjsBuildSchemeReviewOpinionService {

    @Autowired
    private SgjsBuildSchemeReviewOpinionMapper sgjsBuildSchemeReviewOpinionMapper;


    public SgjsBuildSchemeReviewOpinion getSgjsBuildSchemeReviewOpinion(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion) {
        return sgjsBuildSchemeReviewOpinionMapper.getSgjsBuildSchemeReviewOpinion(sgjsBuildSchemeReviewOpinion);
    }

    public List<SgjsBuildSchemeReviewOpinion> getSgjsBuildSchemeReviewOpinionList(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion) {
        return sgjsBuildSchemeReviewOpinionMapper.getSgjsBuildSchemeReviewOpinionList(sgjsBuildSchemeReviewOpinion);
    }

    @Transactional
    public int insertSgjsBuildSchemeReviewOpinion(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion) {
        sgjsBuildSchemeReviewOpinion.setId(IdWorker.createId());
        sgjsBuildSchemeReviewOpinion.setCreateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReviewOpinion.setCreateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewOpinionMapper.insertSgjsBuildSchemeReviewOpinion(sgjsBuildSchemeReviewOpinion);
    }

    @Transactional
    public int insertSgjsBuildSchemeReviewOpinionList(List<SgjsBuildSchemeReviewOpinion> sgjsBuildSchemeReviewOpinionList) {
        for (SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion : sgjsBuildSchemeReviewOpinionList) {
            sgjsBuildSchemeReviewOpinion.setId(IdWorker.createId());
            sgjsBuildSchemeReviewOpinion.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeReviewOpinion.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeReviewOpinionMapper.insertSgjsBuildSchemeReviewOpinionList(sgjsBuildSchemeReviewOpinionList);
    }

    @Transactional
    public int updateSgjsBuildSchemeReviewOpinion(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion) {
        sgjsBuildSchemeReviewOpinion.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReviewOpinion.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewOpinionMapper.updateSgjsBuildSchemeReviewOpinion(sgjsBuildSchemeReviewOpinion);
    }

    @Transactional
    public int updateSgjsBuildSchemeReviewOpinionList(List<SgjsBuildSchemeReviewOpinion> sgjsBuildSchemeReviewOpinionList) {
        for (SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion : sgjsBuildSchemeReviewOpinionList) {
            sgjsBuildSchemeReviewOpinion.setUpdateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeReviewOpinion.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeReviewOpinionMapper.updateSgjsBuildSchemeReviewOpinionList(sgjsBuildSchemeReviewOpinionList);
    }

    @Transactional
    public int deleteSgjsBuildSchemeReviewOpinion(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion) {
        sgjsBuildSchemeReviewOpinion.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReviewOpinion.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewOpinionMapper.deleteSgjsBuildSchemeReviewOpinion(sgjsBuildSchemeReviewOpinion);
    }

    @Transactional
    public int deleteSgjsBuildSchemeReviewOpinionByPks(List<Long> sgjsBuildSchemeReviewOpinionPkList) {
        return sgjsBuildSchemeReviewOpinionMapper.deleteSgjsBuildSchemeReviewOpinionByPks(sgjsBuildSchemeReviewOpinionPkList);
    }
}
