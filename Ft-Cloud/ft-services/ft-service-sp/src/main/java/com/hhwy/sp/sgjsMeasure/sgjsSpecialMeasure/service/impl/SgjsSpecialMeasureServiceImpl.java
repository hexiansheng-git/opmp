package com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.domain.SgjsSpecialMeasure;
import com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.mapper.SgjsSpecialMeasureMapper;
import com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.service.ISgjsSpecialMeasureService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 施工技术--测量管理--特殊工程监控量测
 *
 * @author lcf
 * @date 2024-03-12 14:54:37
 * @remark
 */
@Service
public class SgjsSpecialMeasureServiceImpl implements ISgjsSpecialMeasureService {

    @Autowired
    private SgjsSpecialMeasureMapper sgjsSpecialMeasureMapper;


    public SgjsSpecialMeasure getSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure) {
        return sgjsSpecialMeasureMapper.getSgjsSpecialMeasure(sgjsSpecialMeasure);
    }

    public List<SgjsSpecialMeasure> getSgjsSpecialMeasureList(SgjsSpecialMeasure sgjsSpecialMeasure) {
        return sgjsSpecialMeasureMapper.getSgjsSpecialMeasureList(sgjsSpecialMeasure);
    }

    @Transactional
    public int insertSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure) {
        sgjsSpecialMeasure.setId(IdWorker.createId());
        sgjsSpecialMeasure.setCreateUser(SecurityUtils.getUserName());
        sgjsSpecialMeasure.setCreateTime(DateUtils.getNowDate());
        return sgjsSpecialMeasureMapper.insertSgjsSpecialMeasure(sgjsSpecialMeasure);
    }

    @Transactional
    public int insertSgjsSpecialMeasureList(List<SgjsSpecialMeasure> sgjsSpecialMeasureList) {
        SgjsSpecialMeasure info=new SgjsSpecialMeasure();
        info.setUpdateTime(DateUtils.getNowDate());
        info.setUpdateUser(SecurityUtils.getUserId().toString());
        info.setDelFlag("1");
        sgjsSpecialMeasureMapper.updateSgjsSpecialMeasure(info);
        //入库
        for (SgjsSpecialMeasure sgjsSpecialMeasure : sgjsSpecialMeasureList) {
            sgjsSpecialMeasure.setId(IdWorker.createId());
            sgjsSpecialMeasure.setCreateUser(SecurityUtils.getUserName());
            sgjsSpecialMeasure.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsSpecialMeasureMapper.insertSgjsSpecialMeasureList(sgjsSpecialMeasureList);
    }

    @Transactional
    public int updateSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure) {
        sgjsSpecialMeasure.setUpdateUser(SecurityUtils.getUserName());
        sgjsSpecialMeasure.setUpdateTime(DateUtils.getNowDate());
        return sgjsSpecialMeasureMapper.updateSgjsSpecialMeasure(sgjsSpecialMeasure);
    }

    @Transactional
    public int updateSgjsSpecialMeasureList(List<SgjsSpecialMeasure> sgjsSpecialMeasureList) {
        for (SgjsSpecialMeasure sgjsSpecialMeasure : sgjsSpecialMeasureList) {
            sgjsSpecialMeasure.setUpdateUser(SecurityUtils.getUserName());
            sgjsSpecialMeasure.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsSpecialMeasureMapper.updateSgjsSpecialMeasureList(sgjsSpecialMeasureList);
    }

    @Transactional
    public int deleteSgjsSpecialMeasure(SgjsSpecialMeasure sgjsSpecialMeasure) {
        sgjsSpecialMeasure.setUpdateUser(SecurityUtils.getUserName());
        sgjsSpecialMeasure.setUpdateTime(DateUtils.getNowDate());
        return sgjsSpecialMeasureMapper.deleteSgjsSpecialMeasure(sgjsSpecialMeasure);
    }

    @Transactional
    public int deleteSgjsSpecialMeasureByPks(List<Long> sgjsSpecialMeasurePkList) {
        return sgjsSpecialMeasureMapper.deleteSgjsSpecialMeasureByPks(sgjsSpecialMeasurePkList);
    }
}
