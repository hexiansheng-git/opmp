package com.hhwy.pm.qqch.preparation.measureexp.tech.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.measureexp.tech.domain.QqchMeasureExpTech;
import com.hhwy.pm.qqch.preparation.measureexp.tech.mapper.QqchMeasureExpTechMapper;
import com.hhwy.pm.qqch.preparation.measureexp.tech.service.IQqchMeasureExpTechService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:01:36
 * @remark 
 */
@Service
public class QqchMeasureExpTechServiceImpl implements IQqchMeasureExpTechService {

    @Autowired
    private QqchMeasureExpTechMapper qqchMeasureExpTechMapper;

                                                                                                                                                                                                                                                                                                                
    public QqchMeasureExpTech getQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech) {
        return qqchMeasureExpTechMapper.getQqchMeasureExpTech(qqchMeasureExpTech);
    }

    public List<QqchMeasureExpTech> getQqchMeasureExpTechList(QqchMeasureExpTech qqchMeasureExpTech) {
        return qqchMeasureExpTechMapper.getQqchMeasureExpTechList(qqchMeasureExpTech);
    }

    @Transactional
    public int insertQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech) {
        qqchMeasureExpTech.setId(IdWorker.createId());
        qqchMeasureExpTech.setCreateUser(SecurityUtils.getUserName());
        qqchMeasureExpTech.setCreateTime(DateUtils.getNowDate());
        return qqchMeasureExpTechMapper.insertQqchMeasureExpTech(qqchMeasureExpTech);
    }

    @Transactional
    public int insertQqchMeasureExpTechList(List<QqchMeasureExpTech> qqchMeasureExpTechList) {
        for (QqchMeasureExpTech qqchMeasureExpTech : qqchMeasureExpTechList) {
            qqchMeasureExpTech.setId(IdWorker.createId());
            qqchMeasureExpTech.setCreateUser(SecurityUtils.getUserName());
            qqchMeasureExpTech.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpTechMapper.insertQqchMeasureExpTechList(qqchMeasureExpTechList);
    }

    @Transactional
    public int updateQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech) {
        qqchMeasureExpTech.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpTech.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpTechMapper.updateQqchMeasureExpTech(qqchMeasureExpTech);
    }

            @Transactional
        public int updateQqchMeasureExpTechList(List<QqchMeasureExpTech> qqchMeasureExpTechList) {
            for (QqchMeasureExpTech qqchMeasureExpTech : qqchMeasureExpTechList) {
                qqchMeasureExpTech.setUpdateUser(SecurityUtils.getUserName());
                qqchMeasureExpTech.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchMeasureExpTechMapper.updateQqchMeasureExpTechList(qqchMeasureExpTechList);
        }
    
    @Transactional
    public int deleteQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech) {
        qqchMeasureExpTech.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpTech.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpTechMapper.deleteQqchMeasureExpTech(qqchMeasureExpTech);
    }

            @Transactional
        public int deleteQqchMeasureExpTechByPks(List<Long> qqchMeasureExpTechPkList) {
            return qqchMeasureExpTechMapper.deleteQqchMeasureExpTechByPks(qqchMeasureExpTechPkList);
        }
    }
