package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.domain.SgsjTechnicalScienceTopicModify;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.mapper.SgsjTechnicalScienceTopicModifyMapper;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.service.ISgsjTechnicalScienceTopicModifyService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 功能描述: 科技管理 - 科研课题研发管理 修改记录
 *
 * @author fsd
 * @date 2024-01-29 14:14:11
 * @remark
 */
@Service
public class SgsjTechnicalScienceTopicModifyServiceImpl implements ISgsjTechnicalScienceTopicModifyService {

    @Autowired
    private SgsjTechnicalScienceTopicModifyMapper sgsjTechnicalScienceTopicModifyMapper;


    public SgsjTechnicalScienceTopicModify getSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify) {
        return sgsjTechnicalScienceTopicModifyMapper.getSgsjTechnicalScienceTopicModify(sgsjTechnicalScienceTopicModify);
    }

    public List<SgsjTechnicalScienceTopicModify> getSgsjTechnicalScienceTopicModifyList(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify) {
        return sgsjTechnicalScienceTopicModifyMapper.getSgsjTechnicalScienceTopicModifyList(sgsjTechnicalScienceTopicModify);
    }

    @Transactional
    public int insertSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify) {
        sgsjTechnicalScienceTopicModify.setId(IdWorker.createId());
        sgsjTechnicalScienceTopicModify.setCreateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopicModify.setCreateTime(DateUtils.getNowDate());
        return sgsjTechnicalScienceTopicModifyMapper.insertSgsjTechnicalScienceTopicModify(sgsjTechnicalScienceTopicModify);
    }

    @Transactional
    public int insertSgsjTechnicalScienceTopicModifyList(List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyList) {
        for (SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify : sgsjTechnicalScienceTopicModifyList) {
            sgsjTechnicalScienceTopicModify.setId(IdWorker.createId());
            sgsjTechnicalScienceTopicModify.setCreateUser(SecurityUtils.getUserName());
            sgsjTechnicalScienceTopicModify.setCreateTime(DateUtils.getNowDate());
        }
        return sgsjTechnicalScienceTopicModifyMapper.insertSgsjTechnicalScienceTopicModifyList(sgsjTechnicalScienceTopicModifyList);
    }

    @Transactional
    public int updateSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify) {
        sgsjTechnicalScienceTopicModify.setUpdateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopicModify.setUpdateTime(DateUtils.getNowDate());
        return sgsjTechnicalScienceTopicModifyMapper.updateSgsjTechnicalScienceTopicModify(sgsjTechnicalScienceTopicModify);
    }

    @Transactional
    public int updateSgsjTechnicalScienceTopicModifyList(List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyList) {
        for (SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify : sgsjTechnicalScienceTopicModifyList) {
            sgsjTechnicalScienceTopicModify.setUpdateUser(SecurityUtils.getUserName());
            sgsjTechnicalScienceTopicModify.setUpdateTime(DateUtils.getNowDate());
        }
        return sgsjTechnicalScienceTopicModifyMapper.updateSgsjTechnicalScienceTopicModifyList(sgsjTechnicalScienceTopicModifyList);
    }

    @Transactional
    public int deleteSgsjTechnicalScienceTopicModify(SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify) {
        sgsjTechnicalScienceTopicModify.setUpdateUser(SecurityUtils.getUserName());
        sgsjTechnicalScienceTopicModify.setUpdateTime(DateUtils.getNowDate());
        return sgsjTechnicalScienceTopicModifyMapper.deleteSgsjTechnicalScienceTopicModify(sgsjTechnicalScienceTopicModify);
    }

    @Transactional
    public int deleteSgsjTechnicalScienceTopicModifyByPks(List<Long> sgsjTechnicalScienceTopicModifyPkList) {
        return sgsjTechnicalScienceTopicModifyMapper.deleteSgsjTechnicalScienceTopicModifyByPks(sgsjTechnicalScienceTopicModifyPkList);
    }
}
