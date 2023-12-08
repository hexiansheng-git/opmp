package com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.mapper.SgjsReportMeasureSubmitMapper;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.service.ISgjsReportMeasureSubmitService;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.SgjsReportMeasureSubmit;

/**
 * @author zmh
 * @date 2023-12-08 16:19:52
 * @remark 
 */
@Service
public class SgjsReportMeasureSubmitServiceImpl implements ISgjsReportMeasureSubmitService{

    @Autowired
    private SgjsReportMeasureSubmitMapper sgjsReportMeasureSubmitMapper;

                                                                                                                                                                                                                                                                                                                                                    
    public SgjsReportMeasureSubmit getSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit) {
        return sgjsReportMeasureSubmitMapper.getSgjsReportMeasureSubmit(sgjsReportMeasureSubmit);
    }

    public List<SgjsReportMeasureSubmit> getSgjsReportMeasureSubmitList(SgjsReportMeasureSubmit sgjsReportMeasureSubmit) {
        return sgjsReportMeasureSubmitMapper.getSgjsReportMeasureSubmitList(sgjsReportMeasureSubmit);
    }

    @Transactional
    public int insertSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit) {
        sgjsReportMeasureSubmit.setCreateUser(SecurityUtils.getUserName());
        sgjsReportMeasureSubmit.setCreateTime(DateUtils.getNowDate());
        return sgjsReportMeasureSubmitMapper.insertSgjsReportMeasureSubmit(sgjsReportMeasureSubmit);
    }

    @Transactional
    public int insertSgjsReportMeasureSubmitList(List<SgjsReportMeasureSubmit> sgjsReportMeasureSubmitList) {
        for (SgjsReportMeasureSubmit sgjsReportMeasureSubmit : sgjsReportMeasureSubmitList) {
            sgjsReportMeasureSubmit.setCreateUser(SecurityUtils.getUserName());
            sgjsReportMeasureSubmit.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsReportMeasureSubmitMapper.insertSgjsReportMeasureSubmitList(sgjsReportMeasureSubmitList);
    }

    @Transactional
    public int updateSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit) {
        sgjsReportMeasureSubmit.setUpdateUser(SecurityUtils.getUserName());
        sgjsReportMeasureSubmit.setUpdateTime(DateUtils.getNowDate());
        return sgjsReportMeasureSubmitMapper.updateSgjsReportMeasureSubmit(sgjsReportMeasureSubmit);
    }

            @Transactional
        public int updateSgjsReportMeasureSubmitList(List<SgjsReportMeasureSubmit> sgjsReportMeasureSubmitList) {
            for (SgjsReportMeasureSubmit sgjsReportMeasureSubmit : sgjsReportMeasureSubmitList) {
                sgjsReportMeasureSubmit.setUpdateUser(SecurityUtils.getUserName());
                sgjsReportMeasureSubmit.setUpdateTime(DateUtils.getNowDate());
            }
            return sgjsReportMeasureSubmitMapper.updateSgjsReportMeasureSubmitList(sgjsReportMeasureSubmitList);
        }
    
    @Transactional
    public int deleteSgjsReportMeasureSubmit(SgjsReportMeasureSubmit sgjsReportMeasureSubmit) {
        sgjsReportMeasureSubmit.setUpdateUser(SecurityUtils.getUserName());
        sgjsReportMeasureSubmit.setUpdateTime(DateUtils.getNowDate());
        return sgjsReportMeasureSubmitMapper.deleteSgjsReportMeasureSubmit(sgjsReportMeasureSubmit);
    }

            @Transactional
        public int deleteSgjsReportMeasureSubmitByPks(List<Long> sgjsReportMeasureSubmitPkList) {
            return sgjsReportMeasureSubmitMapper.deleteSgjsReportMeasureSubmitByPks(sgjsReportMeasureSubmitPkList);
        }
    }
