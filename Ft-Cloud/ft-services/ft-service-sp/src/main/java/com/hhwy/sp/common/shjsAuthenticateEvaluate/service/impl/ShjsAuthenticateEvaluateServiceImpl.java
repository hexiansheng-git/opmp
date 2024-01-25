package com.hhwy.sp.common.shjsAuthenticateEvaluate.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.mapper.ShjsAuthenticateEvaluateMapper;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.service.IShjsAuthenticateEvaluateService;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.domain.ShjsAuthenticateEvaluate;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author fsd
 * @date 2024-01-25 10:17:37
 * @remark
 */
@Service
public class ShjsAuthenticateEvaluateServiceImpl implements IShjsAuthenticateEvaluateService {

    @Autowired
    private ShjsAuthenticateEvaluateMapper shjsAuthenticateEvaluateMapper;


    public ShjsAuthenticateEvaluate getShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        return shjsAuthenticateEvaluateMapper.getShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    public List<ShjsAuthenticateEvaluate> getShjsAuthenticateEvaluateList(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        return shjsAuthenticateEvaluateMapper.getShjsAuthenticateEvaluateList(shjsAuthenticateEvaluate);
    }

    @Transactional
    public int insertShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        shjsAuthenticateEvaluate.setId(IdWorker.createId());
        shjsAuthenticateEvaluate.setCreateUser(SecurityUtils.getUserName());
        shjsAuthenticateEvaluate.setCreateTime(DateUtils.getNowDate());
        return shjsAuthenticateEvaluateMapper.insertShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    @Transactional
    public int insertShjsAuthenticateEvaluateList(List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList) {
        for (ShjsAuthenticateEvaluate shjsAuthenticateEvaluate : shjsAuthenticateEvaluateList) {
            shjsAuthenticateEvaluate.setId(IdWorker.createId());
            shjsAuthenticateEvaluate.setCreateUser(SecurityUtils.getUserName());
            shjsAuthenticateEvaluate.setCreateTime(DateUtils.getNowDate());
        }
        return shjsAuthenticateEvaluateMapper.insertShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateList);
    }

    @Transactional
    public int updateShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        shjsAuthenticateEvaluate.setUpdateUser(SecurityUtils.getUserName());
        shjsAuthenticateEvaluate.setUpdateTime(DateUtils.getNowDate());
        return shjsAuthenticateEvaluateMapper.updateShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    @Transactional
    public int updateShjsAuthenticateEvaluateList(List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList) {
        for (ShjsAuthenticateEvaluate shjsAuthenticateEvaluate : shjsAuthenticateEvaluateList) {
            shjsAuthenticateEvaluate.setUpdateUser(SecurityUtils.getUserName());
            shjsAuthenticateEvaluate.setUpdateTime(DateUtils.getNowDate());
        }
        return shjsAuthenticateEvaluateMapper.updateShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateList);
    }

    @Transactional
    public int deleteShjsAuthenticateEvaluate(ShjsAuthenticateEvaluate shjsAuthenticateEvaluate) {
        shjsAuthenticateEvaluate.setUpdateUser(SecurityUtils.getUserName());
        shjsAuthenticateEvaluate.setUpdateTime(DateUtils.getNowDate());
        return shjsAuthenticateEvaluateMapper.deleteShjsAuthenticateEvaluate(shjsAuthenticateEvaluate);
    }

    @Transactional
    public int deleteShjsAuthenticateEvaluateByPks(List<Long> shjsAuthenticateEvaluatePkList) {
        return shjsAuthenticateEvaluateMapper.deleteShjsAuthenticateEvaluateByPks(shjsAuthenticateEvaluatePkList);
    }
}
