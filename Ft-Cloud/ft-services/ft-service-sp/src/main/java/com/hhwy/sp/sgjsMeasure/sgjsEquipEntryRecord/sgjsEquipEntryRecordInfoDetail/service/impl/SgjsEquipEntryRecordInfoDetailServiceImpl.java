package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.domain.SgjsEquipEntryRecordInfoDetail;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.mapper.SgjsEquipEntryRecordInfoDetailMapper;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.service.ISgjsEquipEntryRecordInfoDetailService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:49:49
 * @remark 
 */
@Service
public class SgjsEquipEntryRecordInfoDetailServiceImpl implements ISgjsEquipEntryRecordInfoDetailService{

    @Autowired
    private SgjsEquipEntryRecordInfoDetailMapper sgjsEquipEntryRecordInfoDetailMapper;

                                                                                                                                                                                                                                                                                        
    public SgjsEquipEntryRecordInfoDetail getSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail) {
        return sgjsEquipEntryRecordInfoDetailMapper.getSgjsEquipEntryRecordInfoDetail(sgjsEquipEntryRecordInfoDetail);
    }

    public List<SgjsEquipEntryRecordInfoDetail> getSgjsEquipEntryRecordInfoDetailList(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail) {
        return sgjsEquipEntryRecordInfoDetailMapper.getSgjsEquipEntryRecordInfoDetailList(sgjsEquipEntryRecordInfoDetail);
    }

    @Transactional
    public int insertSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail) {
        sgjsEquipEntryRecordInfoDetail.setId(IdWorker.createId());
        sgjsEquipEntryRecordInfoDetail.setCreateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecordInfoDetail.setCreateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordInfoDetailMapper.insertSgjsEquipEntryRecordInfoDetail(sgjsEquipEntryRecordInfoDetail);
    }

    @Transactional
    public int insertSgjsEquipEntryRecordInfoDetailList(List<SgjsEquipEntryRecordInfoDetail> sgjsEquipEntryRecordInfoDetailList) {
        //添加之前先删掉库中原有数据
        SgjsEquipEntryRecordInfoDetail detail=new SgjsEquipEntryRecordInfoDetail();
        detail.setUpdateTime(DateUtils.getNowDate());
        detail.setUpdateUser(SecurityUtils.getUserId()+"");
        sgjsEquipEntryRecordInfoDetailMapper.deleteAll(detail);
        for (SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail : sgjsEquipEntryRecordInfoDetailList) {
            sgjsEquipEntryRecordInfoDetail.setId(IdWorker.createId());
            sgjsEquipEntryRecordInfoDetail.setCreateUser(SecurityUtils.getUserName());
            sgjsEquipEntryRecordInfoDetail.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsEquipEntryRecordInfoDetailMapper.insertSgjsEquipEntryRecordInfoDetailList(sgjsEquipEntryRecordInfoDetailList);
    }

    @Transactional
    public int updateSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail) {
        sgjsEquipEntryRecordInfoDetail.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecordInfoDetail.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordInfoDetailMapper.updateSgjsEquipEntryRecordInfoDetail(sgjsEquipEntryRecordInfoDetail);
    }

            @Transactional
        public int updateSgjsEquipEntryRecordInfoDetailList(List<SgjsEquipEntryRecordInfoDetail> sgjsEquipEntryRecordInfoDetailList) {
            for (SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail : sgjsEquipEntryRecordInfoDetailList) {
                sgjsEquipEntryRecordInfoDetail.setUpdateUser(SecurityUtils.getUserName());
                sgjsEquipEntryRecordInfoDetail.setUpdateTime(DateUtils.getNowDate());
            }
            return sgjsEquipEntryRecordInfoDetailMapper.updateSgjsEquipEntryRecordInfoDetailList(sgjsEquipEntryRecordInfoDetailList);
        }
    
    @Transactional
    public int deleteSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail) {
        sgjsEquipEntryRecordInfoDetail.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecordInfoDetail.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordInfoDetailMapper.deleteSgjsEquipEntryRecordInfoDetail(sgjsEquipEntryRecordInfoDetail);
    }

            @Transactional
        public int deleteSgjsEquipEntryRecordInfoDetailByPks(List<Long> sgjsEquipEntryRecordInfoDetailPkList) {
            return sgjsEquipEntryRecordInfoDetailMapper.deleteSgjsEquipEntryRecordInfoDetailByPks(sgjsEquipEntryRecordInfoDetailPkList);
        }
    }
