package com.hhwy.sp.buildSchemeManage.review.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeStaffOpinion;
import com.hhwy.sp.buildSchemeManage.review.mapper.SgjsBuildSchemeStaffOpinionMapper;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeStaffOpinionService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:59
 * @remark
 */
@Service
public class SgjsBuildSchemeStaffOpinionServiceImpl implements ISgjsBuildSchemeStaffOpinionService {

    @Autowired
    private SgjsBuildSchemeStaffOpinionMapper sgjsBuildSchemeStaffOpinionMapper;


    public SgjsBuildSchemeStaffOpinion getSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion) {
        return sgjsBuildSchemeStaffOpinionMapper.getSgjsBuildSchemeStaffOpinion(sgjsBuildSchemeStaffOpinion);
    }

    public List<SgjsBuildSchemeStaffOpinion> getSgjsBuildSchemeStaffOpinionList(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion) {
        return sgjsBuildSchemeStaffOpinionMapper.getSgjsBuildSchemeStaffOpinionList(sgjsBuildSchemeStaffOpinion);
    }

    @Transactional
    public int insertSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion) {
        sgjsBuildSchemeStaffOpinion.setId(IdWorker.createId());
        sgjsBuildSchemeStaffOpinion.setCreateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeStaffOpinion.setCreateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeStaffOpinionMapper.insertSgjsBuildSchemeStaffOpinion(sgjsBuildSchemeStaffOpinion);
    }

    @Transactional
    public int insertSgjsBuildSchemeStaffOpinionList(List<SgjsBuildSchemeStaffOpinion> sgjsBuildSchemeStaffOpinionList) {
        for (SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion : sgjsBuildSchemeStaffOpinionList) {
            sgjsBuildSchemeStaffOpinion.setId(IdWorker.createId());
            sgjsBuildSchemeStaffOpinion.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeStaffOpinion.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeStaffOpinionMapper.insertSgjsBuildSchemeStaffOpinionList(sgjsBuildSchemeStaffOpinionList);
    }

    @Transactional
    public int updateSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion) {
        sgjsBuildSchemeStaffOpinion.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeStaffOpinion.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeStaffOpinionMapper.updateSgjsBuildSchemeStaffOpinion(sgjsBuildSchemeStaffOpinion);
    }

    @Transactional
    public int updateSgjsBuildSchemeStaffOpinionList(List<SgjsBuildSchemeStaffOpinion> sgjsBuildSchemeStaffOpinionList) {
        for (SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion : sgjsBuildSchemeStaffOpinionList) {
            sgjsBuildSchemeStaffOpinion.setUpdateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeStaffOpinion.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeStaffOpinionMapper.updateSgjsBuildSchemeStaffOpinionList(sgjsBuildSchemeStaffOpinionList);
    }

    @Transactional
    public int deleteSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion) {
        sgjsBuildSchemeStaffOpinion.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeStaffOpinion.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeStaffOpinionMapper.deleteSgjsBuildSchemeStaffOpinion(sgjsBuildSchemeStaffOpinion);
    }

    @Transactional
    public int deleteSgjsBuildSchemeStaffOpinionByPks(List<Long> sgjsBuildSchemeStaffOpinionPkList) {
        return sgjsBuildSchemeStaffOpinionMapper.deleteSgjsBuildSchemeStaffOpinionByPks(sgjsBuildSchemeStaffOpinionPkList);
    }
}
