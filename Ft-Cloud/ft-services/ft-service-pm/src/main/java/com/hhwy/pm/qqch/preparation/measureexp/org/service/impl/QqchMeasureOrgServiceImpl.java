package com.hhwy.pm.qqch.preparation.measureexp.org.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.measureexp.org.mapper.QqchMeasureOrgMapper;
import com.hhwy.pm.qqch.preparation.measureexp.org.service.IQqchMeasureOrgService;
import com.hhwy.pm.qqch.preparation.measureexp.org.domain.QqchMeasureOrg;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:01:39
 * @remark 
 */
@Service
public class QqchMeasureOrgServiceImpl implements IQqchMeasureOrgService{

    @Autowired
    private QqchMeasureOrgMapper qqchMeasureOrgMapper;

                                                                                                                                                                                                                                        
    public QqchMeasureOrg getQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg) {
        return qqchMeasureOrgMapper.getQqchMeasureOrg(qqchMeasureOrg);
    }

    public List<QqchMeasureOrg> getQqchMeasureOrgList(QqchMeasureOrg qqchMeasureOrg) {
        return qqchMeasureOrgMapper.getQqchMeasureOrgList(qqchMeasureOrg);
    }

    @Transactional
    public int insertQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg) {
        qqchMeasureOrg.setId(IdWorker.createId());
        qqchMeasureOrg.setCreateUser(SecurityUtils.getUserName());
        qqchMeasureOrg.setCreateTime(DateUtils.getNowDate());
        return qqchMeasureOrgMapper.insertQqchMeasureOrg(qqchMeasureOrg);
    }

    @Transactional
    public int insertQqchMeasureOrgList(List<QqchMeasureOrg> qqchMeasureOrgList) {
        for (QqchMeasureOrg qqchMeasureOrg : qqchMeasureOrgList) {
            qqchMeasureOrg.setId(IdWorker.createId());
            qqchMeasureOrg.setCreateUser(SecurityUtils.getUserName());
            qqchMeasureOrg.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMeasureOrgMapper.insertQqchMeasureOrgList(qqchMeasureOrgList);
    }

    @Transactional
    public int updateQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg) {
        qqchMeasureOrg.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureOrg.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureOrgMapper.updateQqchMeasureOrg(qqchMeasureOrg);
    }

            @Transactional
        public int updateQqchMeasureOrgList(List<QqchMeasureOrg> qqchMeasureOrgList) {
            for (QqchMeasureOrg qqchMeasureOrg : qqchMeasureOrgList) {
                qqchMeasureOrg.setUpdateUser(SecurityUtils.getUserName());
                qqchMeasureOrg.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchMeasureOrgMapper.updateQqchMeasureOrgList(qqchMeasureOrgList);
        }
    
    @Transactional
    public int deleteQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg) {
        qqchMeasureOrg.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureOrg.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureOrgMapper.deleteQqchMeasureOrg(qqchMeasureOrg);
    }

            @Transactional
        public int deleteQqchMeasureOrgByPks(List<Long> qqchMeasureOrgPkList) {
            return qqchMeasureOrgMapper.deleteQqchMeasureOrgByPks(qqchMeasureOrgPkList);
        }
    }
