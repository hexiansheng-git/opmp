package com.hhwy.sgjsEquipEntryRecordInfoDetail.sgjsEquipEntryRecordInfoDetail.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sgjsEquipEntryRecordInfoDetail.sgjsEquipEntryRecordInfoDetail.mapper.SgjsEquipEntryRecordInfoDetailMapper;
import com.hhwy.sgjsEquipEntryRecordInfoDetail.sgjsEquipEntryRecordInfoDetail.service.ISgjsEquipEntryRecordInfoDetailService;
import com.hhwy.sgjsEquipEntryRecordInfoDetail.sgjsEquipEntryRecordInfoDetail.domain.SgjsEquipEntryRecordInfoDetail;
import com.hhwy.utils.idworker.IdWorker;

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
