package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.mapper.SgjsEquipEntryRecordInfoMapper;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.service.ISgjsEquipEntryRecordInfoService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:49:36
 * @remark
 */
@Service
public class SgjsEquipEntryRecordInfoServiceImpl implements ISgjsEquipEntryRecordInfoService{

    @Autowired
    private SgjsEquipEntryRecordInfoMapper sgjsEquipEntryRecordInfoMapper;


    public SgjsEquipEntryRecordInfo getSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo) {
        return sgjsEquipEntryRecordInfoMapper.getSgjsEquipEntryRecordInfo(sgjsEquipEntryRecordInfo);
    }

    public List<SgjsEquipEntryRecordInfo> getSgjsEquipEntryRecordInfoList(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo) {
        return sgjsEquipEntryRecordInfoMapper.getSgjsEquipEntryRecordInfoList(sgjsEquipEntryRecordInfo);
    }

    @Transactional
    public int insertSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo) {
        sgjsEquipEntryRecordInfo.setId(IdWorker.createId());
        sgjsEquipEntryRecordInfo.setCreateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecordInfo.setCreateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordInfoMapper.insertSgjsEquipEntryRecordInfo(sgjsEquipEntryRecordInfo);
    }

    @Transactional
    public int insertSgjsEquipEntryRecordInfoList(List<SgjsEquipEntryRecordInfo> sgjsEquipEntryRecordInfoList) {
        for (SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo : sgjsEquipEntryRecordInfoList) {
            sgjsEquipEntryRecordInfo.setId(IdWorker.createId());
            sgjsEquipEntryRecordInfo.setCreateUser(SecurityUtils.getUserName());
            sgjsEquipEntryRecordInfo.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsEquipEntryRecordInfoMapper.insertSgjsEquipEntryRecordInfoList(sgjsEquipEntryRecordInfoList);
    }

    @Transactional
    public int updateSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo) {
        sgjsEquipEntryRecordInfo.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecordInfo.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordInfoMapper.updateSgjsEquipEntryRecordInfo(sgjsEquipEntryRecordInfo);
    }

    @Transactional
    public int updateSgjsEquipEntryRecordInfoList(List<SgjsEquipEntryRecordInfo> sgjsEquipEntryRecordInfoList) {
        for (SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo : sgjsEquipEntryRecordInfoList) {
            sgjsEquipEntryRecordInfo.setUpdateUser(SecurityUtils.getUserName());
            sgjsEquipEntryRecordInfo.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsEquipEntryRecordInfoMapper.updateSgjsEquipEntryRecordInfoList(sgjsEquipEntryRecordInfoList);
    }

    @Transactional
    public int deleteSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo) {
        sgjsEquipEntryRecordInfo.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecordInfo.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordInfoMapper.deleteSgjsEquipEntryRecordInfo(sgjsEquipEntryRecordInfo);
    }

    @Transactional
    public int deleteSgjsEquipEntryRecordInfoByPks(List<Long> sgjsEquipEntryRecordInfoPkList) {
        return sgjsEquipEntryRecordInfoMapper.deleteSgjsEquipEntryRecordInfoByPks(sgjsEquipEntryRecordInfoPkList);
    }
}
