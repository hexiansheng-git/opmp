package com.hhwy.sp.sgjsExperiment.sgjsExperProgressManage.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.sgjsExperiment.sgjsExperProgressManage.mapper.SgjsExperProgressManageMapper;
import com.hhwy.sp.sgjsExperiment.sgjsExperProgressManage.service.ISgjsExperProgressManageService;
import com.hhwy.sp.sgjsExperiment.sgjsExperProgressManage.domain.SgjsExperProgressManage;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author wll
 * @date 2023-12-09 11:06:27
 * @remark 
 */
@Service
public class SgjsExperProgressManageServiceImpl implements ISgjsExperProgressManageService{

    @Autowired
    private SgjsExperProgressManageMapper sgjsExperProgressManageMapper;

                                                                                                                                                                                                                                                                                                                                                                                        
    public SgjsExperProgressManage getSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage) {
        return sgjsExperProgressManageMapper.getSgjsExperProgressManage(sgjsExperProgressManage);
    }

    public List<SgjsExperProgressManage> getSgjsExperProgressManageList(SgjsExperProgressManage sgjsExperProgressManage) {
        return sgjsExperProgressManageMapper.getSgjsExperProgressManageList(sgjsExperProgressManage);
    }

    @Transactional
    public int insertSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage) {
        sgjsExperProgressManage.setId(IdWorker.createId());
        sgjsExperProgressManage.setCreateUser(SecurityUtils.getUserName());
        sgjsExperProgressManage.setCreateTime(DateUtils.getNowDate());
        return sgjsExperProgressManageMapper.insertSgjsExperProgressManage(sgjsExperProgressManage);
    }

    @Transactional
    public int insertSgjsExperProgressManageList(List<SgjsExperProgressManage> sgjsExperProgressManageList) {
        for (SgjsExperProgressManage sgjsExperProgressManage : sgjsExperProgressManageList) {
            sgjsExperProgressManage.setId(IdWorker.createId());
            sgjsExperProgressManage.setCreateUser(SecurityUtils.getUserName());
            sgjsExperProgressManage.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExperProgressManageMapper.insertSgjsExperProgressManageList(sgjsExperProgressManageList);
    }

    @Transactional
    public int updateSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage) {
        sgjsExperProgressManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperProgressManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperProgressManageMapper.updateSgjsExperProgressManage(sgjsExperProgressManage);
    }

            @Transactional
        public int updateSgjsExperProgressManageList(List<SgjsExperProgressManage> sgjsExperProgressManageList) {
            for (SgjsExperProgressManage sgjsExperProgressManage : sgjsExperProgressManageList) {
                sgjsExperProgressManage.setUpdateUser(SecurityUtils.getUserName());
                sgjsExperProgressManage.setUpdateTime(DateUtils.getNowDate());
            }
            return sgjsExperProgressManageMapper.updateSgjsExperProgressManageList(sgjsExperProgressManageList);
        }
    
    @Transactional
    public int deleteSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage) {
        sgjsExperProgressManage.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperProgressManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperProgressManageMapper.deleteSgjsExperProgressManage(sgjsExperProgressManage);
    }

            @Transactional
        public int deleteSgjsExperProgressManageByPks(List<Long> sgjsExperProgressManagePkList) {
            return sgjsExperProgressManageMapper.deleteSgjsExperProgressManageByPks(sgjsExperProgressManagePkList);
        }
    }
