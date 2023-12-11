package com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.mapper.SgjsExperimentRecordInfoDetailMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.service.ISgjsExperimentRecordInfoDetailService;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.domain.SgjsExperimentRecordInfoDetail;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author lcf--自检自校表记录
 * @date 2023-12-11 15:04:20
 * @remark 
 */
@Service
public class SgjsExperimentRecordInfoDetailServiceImpl implements ISgjsExperimentRecordInfoDetailService{

    @Autowired
    private SgjsExperimentRecordInfoDetailMapper sgjsExperimentRecordInfoDetailMapper;

                                                                                                                                                                                                                                                                                                    
    public SgjsExperimentRecordInfoDetail getSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail) {
        return sgjsExperimentRecordInfoDetailMapper.getSgjsExperimentRecordInfoDetail(sgjsExperimentRecordInfoDetail);
    }

    public List<SgjsExperimentRecordInfoDetail> getSgjsExperimentRecordInfoDetailList(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail) {
        return sgjsExperimentRecordInfoDetailMapper.getSgjsExperimentRecordInfoDetailList(sgjsExperimentRecordInfoDetail);
    }

    @Transactional
    public int insertSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail) {
        sgjsExperimentRecordInfoDetail.setId(IdWorker.createId());
        sgjsExperimentRecordInfoDetail.setCreateUser(SecurityUtils.getUserName());
        sgjsExperimentRecordInfoDetail.setCreateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordInfoDetailMapper.insertSgjsExperimentRecordInfoDetail(sgjsExperimentRecordInfoDetail);
    }

    @Transactional
    public int insertSgjsExperimentRecordInfoDetailList(List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailList) {
        for (SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail : sgjsExperimentRecordInfoDetailList) {
            sgjsExperimentRecordInfoDetail.setId(IdWorker.createId());
            sgjsExperimentRecordInfoDetail.setCreateUser(SecurityUtils.getUserName());
            sgjsExperimentRecordInfoDetail.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentRecordInfoDetailMapper.insertSgjsExperimentRecordInfoDetailList(sgjsExperimentRecordInfoDetailList);
    }

    @Transactional
    public int updateSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail) {
        sgjsExperimentRecordInfoDetail.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecordInfoDetail.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordInfoDetailMapper.updateSgjsExperimentRecordInfoDetail(sgjsExperimentRecordInfoDetail);
    }

            @Transactional
        public int updateSgjsExperimentRecordInfoDetailList(List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailList) {
            for (SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail : sgjsExperimentRecordInfoDetailList) {
                sgjsExperimentRecordInfoDetail.setUpdateUser(SecurityUtils.getUserName());
                sgjsExperimentRecordInfoDetail.setUpdateTime(DateUtils.getNowDate());
            }
            return sgjsExperimentRecordInfoDetailMapper.updateSgjsExperimentRecordInfoDetailList(sgjsExperimentRecordInfoDetailList);
        }
    
    @Transactional
    public int deleteSgjsExperimentRecordInfoDetail(SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail) {
        sgjsExperimentRecordInfoDetail.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecordInfoDetail.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordInfoDetailMapper.deleteSgjsExperimentRecordInfoDetail(sgjsExperimentRecordInfoDetail);
    }

            @Transactional
        public int deleteSgjsExperimentRecordInfoDetailByPks(List<Long> sgjsExperimentRecordInfoDetailPkList) {
            return sgjsExperimentRecordInfoDetailMapper.deleteSgjsExperimentRecordInfoDetailByPks(sgjsExperimentRecordInfoDetailPkList);
        }
    }
