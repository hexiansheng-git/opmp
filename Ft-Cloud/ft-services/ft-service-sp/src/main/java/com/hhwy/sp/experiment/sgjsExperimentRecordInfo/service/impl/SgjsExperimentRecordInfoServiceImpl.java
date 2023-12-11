package com.hhwy.sp.experiment.sgjsExperimentRecordInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.domain.SgjsExperimentRecordInfo;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.mapper.SgjsExperimentRecordInfoMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.service.ISgjsExperimentRecordInfoService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lcf--设备实际进场记录
 * @date 2023-12-11 15:03:58
 * @remark
 */
@Service
public class SgjsExperimentRecordInfoServiceImpl implements ISgjsExperimentRecordInfoService{

    @Autowired
    private SgjsExperimentRecordInfoMapper sgjsExperimentRecordInfoMapper;


    public SgjsExperimentRecordInfo getSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo) {
        return sgjsExperimentRecordInfoMapper.getSgjsExperimentRecordInfo(sgjsExperimentRecordInfo);
    }

    public List<SgjsExperimentRecordInfo> getSgjsExperimentRecordInfoList(SgjsExperimentRecordInfo sgjsExperimentRecordInfo) {
        return sgjsExperimentRecordInfoMapper.getSgjsExperimentRecordInfoList(sgjsExperimentRecordInfo);
    }

    @Transactional
    public int insertSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo) {
        sgjsExperimentRecordInfo.setId(IdWorker.createId());
        sgjsExperimentRecordInfo.setCreateUser(SecurityUtils.getUserName());
        sgjsExperimentRecordInfo.setCreateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordInfoMapper.insertSgjsExperimentRecordInfo(sgjsExperimentRecordInfo);
    }

    @Transactional
    public int insertSgjsExperimentRecordInfoList(List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoList) {
        for (SgjsExperimentRecordInfo sgjsExperimentRecordInfo : sgjsExperimentRecordInfoList) {
            sgjsExperimentRecordInfo.setId(IdWorker.createId());
            sgjsExperimentRecordInfo.setCreateUser(SecurityUtils.getUserName());
            sgjsExperimentRecordInfo.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentRecordInfoMapper.insertSgjsExperimentRecordInfoList(sgjsExperimentRecordInfoList);
    }

    @Transactional
    public int updateSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo) {
        sgjsExperimentRecordInfo.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecordInfo.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordInfoMapper.updateSgjsExperimentRecordInfo(sgjsExperimentRecordInfo);
    }

    @Transactional
    public int updateSgjsExperimentRecordInfoList(List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoList) {
        for (SgjsExperimentRecordInfo sgjsExperimentRecordInfo : sgjsExperimentRecordInfoList) {
            sgjsExperimentRecordInfo.setUpdateUser(SecurityUtils.getUserName());
            sgjsExperimentRecordInfo.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentRecordInfoMapper.updateSgjsExperimentRecordInfoList(sgjsExperimentRecordInfoList);
    }

    @Transactional
    public int deleteSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo) {
        sgjsExperimentRecordInfo.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecordInfo.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordInfoMapper.deleteSgjsExperimentRecordInfo(sgjsExperimentRecordInfo);
    }

    @Transactional
    public int deleteSgjsExperimentRecordInfoByPks(List<Long> sgjsExperimentRecordInfoPkList) {
        return sgjsExperimentRecordInfoMapper.deleteSgjsExperimentRecordInfoByPks(sgjsExperimentRecordInfoPkList);
    }
}
