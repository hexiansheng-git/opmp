package com.hhwy.sp.buildSchemeManage.review.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewStaff;
import com.hhwy.sp.buildSchemeManage.review.mapper.SgjsBuildSchemeReviewStaffMapper;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewStaffService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:55
 * @remark
 */
@Service
public class SgjsBuildSchemeReviewStaffServiceImpl implements ISgjsBuildSchemeReviewStaffService {

    @Autowired
    private SgjsBuildSchemeReviewStaffMapper sgjsBuildSchemeReviewStaffMapper;


    public SgjsBuildSchemeReviewStaff getSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff) {
        return sgjsBuildSchemeReviewStaffMapper.getSgjsBuildSchemeReviewStaff(sgjsBuildSchemeReviewStaff);
    }

    public List<SgjsBuildSchemeReviewStaff> getSgjsBuildSchemeReviewStaffList(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff) {
        return sgjsBuildSchemeReviewStaffMapper.getSgjsBuildSchemeReviewStaffList(sgjsBuildSchemeReviewStaff);
    }

    @Transactional
    public int insertSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff) {
        sgjsBuildSchemeReviewStaff.setId(IdWorker.createId());
        sgjsBuildSchemeReviewStaff.setCreateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReviewStaff.setCreateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewStaffMapper.insertSgjsBuildSchemeReviewStaff(sgjsBuildSchemeReviewStaff);
    }

    @Transactional
    public int insertSgjsBuildSchemeReviewStaffList(List<SgjsBuildSchemeReviewStaff> sgjsBuildSchemeReviewStaffList) {
        for (SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff : sgjsBuildSchemeReviewStaffList) {
            sgjsBuildSchemeReviewStaff.setId(IdWorker.createId());
            sgjsBuildSchemeReviewStaff.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeReviewStaff.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeReviewStaffMapper.insertSgjsBuildSchemeReviewStaffList(sgjsBuildSchemeReviewStaffList);
    }

    @Transactional
    public int updateSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff) {
        sgjsBuildSchemeReviewStaff.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReviewStaff.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewStaffMapper.updateSgjsBuildSchemeReviewStaff(sgjsBuildSchemeReviewStaff);
    }

    @Transactional
    public int updateSgjsBuildSchemeReviewStaffList(List<SgjsBuildSchemeReviewStaff> sgjsBuildSchemeReviewStaffList) {
        for (SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff : sgjsBuildSchemeReviewStaffList) {
            sgjsBuildSchemeReviewStaff.setUpdateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeReviewStaff.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeReviewStaffMapper.updateSgjsBuildSchemeReviewStaffList(sgjsBuildSchemeReviewStaffList);
    }

    @Transactional
    public int deleteSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff) {
        sgjsBuildSchemeReviewStaff.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReviewStaff.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewStaffMapper.deleteSgjsBuildSchemeReviewStaff(sgjsBuildSchemeReviewStaff);
    }

    @Transactional
    public int deleteSgjsBuildSchemeReviewStaffByPks(List<Long> sgjsBuildSchemeReviewStaffPkList) {
        return sgjsBuildSchemeReviewStaffMapper.deleteSgjsBuildSchemeReviewStaffByPks(sgjsBuildSchemeReviewStaffPkList);
    }
}
