package com.hhwy.sp.techOrg.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techOrg.domain.SgjsExperimentPosition;
import com.hhwy.sp.techOrg.mapper.SgjsExperimentPositionMapper;
import com.hhwy.sp.techOrg.service.ISgjsExperimentPositionService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lcf
 * @date 2023-11-20 11:39:49
 * @remark
 */
@Service
public class SgjsExperimentPositionServiceImpl implements ISgjsExperimentPositionService {

    @Autowired
    private SgjsExperimentPositionMapper sgjsExperimentPositionMapper;


    public SgjsExperimentPosition getSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition) {
        return sgjsExperimentPositionMapper.getSgjsExperimentPosition(sgjsExperimentPosition);
    }

    public List<SgjsExperimentPosition> getSgjsExperimentPositionList(SgjsExperimentPosition sgjsExperimentPosition) {
        return sgjsExperimentPositionMapper.getSgjsExperimentPositionList(sgjsExperimentPosition);
    }

    @Transactional
    public int insertSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition) {
        sgjsExperimentPosition.setId(IdWorker.createId());
        sgjsExperimentPosition.setCreateUser(SecurityUtils.getUserName());
        sgjsExperimentPosition.setCreateTime(DateUtils.getNowDate());
        return sgjsExperimentPositionMapper.insertSgjsExperimentPosition(sgjsExperimentPosition);
    }

    @Transactional
    public int insertSgjsExperimentPositionList(List<SgjsExperimentPosition> sgjsExperimentPositionList) {
        for (SgjsExperimentPosition sgjsExperimentPosition : sgjsExperimentPositionList) {
            sgjsExperimentPosition.setId(IdWorker.createId());
            sgjsExperimentPosition.setCreateUser(SecurityUtils.getUserName());
            sgjsExperimentPosition.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentPositionMapper.insertSgjsExperimentPositionList(sgjsExperimentPositionList);
    }

    @Transactional
    public int updateSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition) {
        sgjsExperimentPosition.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentPosition.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentPositionMapper.updateSgjsExperimentPosition(sgjsExperimentPosition);
    }

    @Transactional
    public int updateSgjsExperimentPositionList(List<SgjsExperimentPosition> sgjsExperimentPositionList) {
        for (SgjsExperimentPosition sgjsExperimentPosition : sgjsExperimentPositionList) {
            sgjsExperimentPosition.setUpdateUser(SecurityUtils.getUserName());
            sgjsExperimentPosition.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentPositionMapper.updateSgjsExperimentPositionList(sgjsExperimentPositionList);
    }

    @Transactional
    public int deleteSgjsExperimentPosition(SgjsExperimentPosition sgjsExperimentPosition) {
        sgjsExperimentPosition.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentPosition.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentPositionMapper.deleteSgjsExperimentPosition(sgjsExperimentPosition);
    }

    @Transactional
    public int deleteSgjsExperimentPositionByPks(List<Long> sgjsExperimentPositionPkList) {
        return sgjsExperimentPositionMapper.deleteSgjsExperimentPositionByPks(sgjsExperimentPositionPkList);
    }
}
