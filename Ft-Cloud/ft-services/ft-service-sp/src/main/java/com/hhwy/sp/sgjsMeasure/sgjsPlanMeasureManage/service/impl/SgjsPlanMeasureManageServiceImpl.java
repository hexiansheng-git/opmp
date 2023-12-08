package com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.mapper.SgjsPlanMeasureManageMapper;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.service.ISgjsPlanMeasureManageService;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManage;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author zmh
 * @date 2023-12-07 18:13:51
 * @remark 
 */
@Service
public class SgjsPlanMeasureManageServiceImpl implements ISgjsPlanMeasureManageService{

    @Autowired
    private SgjsPlanMeasureManageMapper sgjsPlanMeasureManageMapper;

                                                                                                                                                                                                                                                                                                                
    public SgjsPlanMeasureManage getSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage) {
        return sgjsPlanMeasureManageMapper.getSgjsPlanMeasureManage(sgjsPlanMeasureManage);
    }

    public List<SgjsPlanMeasureManage> getSgjsPlanMeasureManageList(SgjsPlanMeasureManage sgjsPlanMeasureManage) {
        return sgjsPlanMeasureManageMapper.getSgjsPlanMeasureManageList(sgjsPlanMeasureManage);
    }

    @Transactional
    public int insertSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage) {
        sgjsPlanMeasureManage.setId(IdWorker.createId());
        sgjsPlanMeasureManage.setCreateUser(SecurityUtils.getUserName());
        sgjsPlanMeasureManage.setCreateTime(DateUtils.getNowDate());
        return sgjsPlanMeasureManageMapper.insertSgjsPlanMeasureManage(sgjsPlanMeasureManage);
    }

    @Transactional
    public int insertSgjsPlanMeasureManageList(List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList) {
        for (SgjsPlanMeasureManage sgjsPlanMeasureManage : sgjsPlanMeasureManageList) {
            sgjsPlanMeasureManage.setId(IdWorker.createId());
            sgjsPlanMeasureManage.setCreateUser(SecurityUtils.getUserName());
            sgjsPlanMeasureManage.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsPlanMeasureManageMapper.insertSgjsPlanMeasureManageList(sgjsPlanMeasureManageList);
    }

    @Transactional
    public int updateSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage) {
        sgjsPlanMeasureManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsPlanMeasureManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsPlanMeasureManageMapper.updateSgjsPlanMeasureManage(sgjsPlanMeasureManage);
    }

            @Transactional
        public int updateSgjsPlanMeasureManageList(List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList) {
            for (SgjsPlanMeasureManage sgjsPlanMeasureManage : sgjsPlanMeasureManageList) {
                sgjsPlanMeasureManage.setUpdateUser(SecurityUtils.getUserName());
                sgjsPlanMeasureManage.setUpdateTime(DateUtils.getNowDate());
            }
            return sgjsPlanMeasureManageMapper.updateSgjsPlanMeasureManageList(sgjsPlanMeasureManageList);
        }
    
    @Transactional
    public int deleteSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage) {
        sgjsPlanMeasureManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsPlanMeasureManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsPlanMeasureManageMapper.deleteSgjsPlanMeasureManage(sgjsPlanMeasureManage);
    }

            @Transactional
        public int deleteSgjsPlanMeasureManageByPks(List<Long> sgjsPlanMeasureManagePkList) {
            return sgjsPlanMeasureManageMapper.deleteSgjsPlanMeasureManageByPks(sgjsPlanMeasureManagePkList);
        }
    }
