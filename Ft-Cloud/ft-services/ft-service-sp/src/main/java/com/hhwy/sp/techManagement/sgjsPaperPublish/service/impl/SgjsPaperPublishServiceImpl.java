package com.hhwy.sp.techManagement.sgjsPaperPublish.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.SgjsPaperPublish;
import com.hhwy.sp.techManagement.sgjsPaperPublish.mapper.SgjsPaperPublishMapper;
import com.hhwy.sp.techManagement.sgjsPaperPublish.service.ISgjsPaperPublishService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:37
 * @remark
 */
@Service
public class SgjsPaperPublishServiceImpl implements ISgjsPaperPublishService {

    @Autowired
    private SgjsPaperPublishMapper sgjsPaperPublishMapper;


    public SgjsPaperPublish getSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        return sgjsPaperPublishMapper.getSgjsPaperPublish(sgjsPaperPublish);
    }

    public List<SgjsPaperPublish> getSgjsPaperPublishList(SgjsPaperPublish sgjsPaperPublish) {
        return sgjsPaperPublishMapper.getSgjsPaperPublishList(sgjsPaperPublish);
    }

    @Transactional
    public int insertSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        sgjsPaperPublish.setId(IdWorker.createId());
        sgjsPaperPublish.setCreateUser(SecurityUtils.getUserName());
        sgjsPaperPublish.setCreateTime(DateUtils.getNowDate());
        return sgjsPaperPublishMapper.insertSgjsPaperPublish(sgjsPaperPublish);
    }

    @Transactional
    public int insertSgjsPaperPublishList(List<SgjsPaperPublish> sgjsPaperPublishList) {
        for (SgjsPaperPublish sgjsPaperPublish : sgjsPaperPublishList) {
            sgjsPaperPublish.setId(IdWorker.createId());
            sgjsPaperPublish.setCreateUser(SecurityUtils.getUserName());
            sgjsPaperPublish.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsPaperPublishMapper.insertSgjsPaperPublishList(sgjsPaperPublishList);
    }

    @Transactional
    public int updateSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        sgjsPaperPublish.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperPublish.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperPublishMapper.updateSgjsPaperPublish(sgjsPaperPublish);
    }

    @Transactional
    public int updateSgjsPaperPublishList(List<SgjsPaperPublish> sgjsPaperPublishList) {
        for (SgjsPaperPublish sgjsPaperPublish : sgjsPaperPublishList) {
            sgjsPaperPublish.setUpdateUser(SecurityUtils.getUserName());
            sgjsPaperPublish.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsPaperPublishMapper.updateSgjsPaperPublishList(sgjsPaperPublishList);
    }

    @Transactional
    public int deleteSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish) {
        sgjsPaperPublish.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperPublish.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperPublishMapper.deleteSgjsPaperPublish(sgjsPaperPublish);
    }

    @Transactional
    public int deleteSgjsPaperPublishByPks(List<Long> sgjsPaperPublishPkList) {
        return sgjsPaperPublishMapper.deleteSgjsPaperPublishByPks(sgjsPaperPublishPkList);
    }
}
