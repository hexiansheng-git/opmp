package com.hhwy.pm.qqch.preparation.measureexp.equ.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.measureexp.equ.domain.QqchMeasureExpEqu;
import com.hhwy.pm.qqch.preparation.measureexp.equ.mapper.QqchMeasureExpEquMapper;
import com.hhwy.pm.qqch.preparation.measureexp.equ.service.IQqchMeasureExpEquService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:00:16
 * @remark 
 */
@Service
public class QqchMeasureExpEquServiceImpl implements IQqchMeasureExpEquService {

    @Autowired
    private QqchMeasureExpEquMapper qqchMeasureExpEquMapper;

                                                                                                                                                                                                                                                                                                                                                    
    public QqchMeasureExpEqu getQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu) {
        return qqchMeasureExpEquMapper.getQqchMeasureExpEqu(qqchMeasureExpEqu);
    }

    public List<QqchMeasureExpEqu> getQqchMeasureExpEquList(QqchMeasureExpEqu qqchMeasureExpEqu) {
        return qqchMeasureExpEquMapper.getQqchMeasureExpEquList(qqchMeasureExpEqu);
    }

    @Transactional
    public int insertQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu) {
        qqchMeasureExpEqu.setId(IdWorker.createId());
        qqchMeasureExpEqu.setCreateUser(SecurityUtils.getUserName());
        qqchMeasureExpEqu.setCreateTime(DateUtils.getNowDate());
        return qqchMeasureExpEquMapper.insertQqchMeasureExpEqu(qqchMeasureExpEqu);
    }

    @Transactional
    public int insertQqchMeasureExpEquList(List<QqchMeasureExpEqu> qqchMeasureExpEquList) {
        for (QqchMeasureExpEqu qqchMeasureExpEqu : qqchMeasureExpEquList) {
            qqchMeasureExpEqu.setId(IdWorker.createId());
            qqchMeasureExpEqu.setCreateUser(SecurityUtils.getUserName());
            qqchMeasureExpEqu.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpEquMapper.insertQqchMeasureExpEquList(qqchMeasureExpEquList);
    }

    @Transactional
    public int updateQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu) {
        qqchMeasureExpEqu.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpEqu.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpEquMapper.updateQqchMeasureExpEqu(qqchMeasureExpEqu);
    }

            @Transactional
        public int updateQqchMeasureExpEquList(List<QqchMeasureExpEqu> qqchMeasureExpEquList) {
            for (QqchMeasureExpEqu qqchMeasureExpEqu : qqchMeasureExpEquList) {
                qqchMeasureExpEqu.setUpdateUser(SecurityUtils.getUserName());
                qqchMeasureExpEqu.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchMeasureExpEquMapper.updateQqchMeasureExpEquList(qqchMeasureExpEquList);
        }
    
    @Transactional
    public int deleteQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu) {
        qqchMeasureExpEqu.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpEqu.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpEquMapper.deleteQqchMeasureExpEqu(qqchMeasureExpEqu);
    }

            @Transactional
        public int deleteQqchMeasureExpEquByPks(List<Long> qqchMeasureExpEquPkList) {
            return qqchMeasureExpEquMapper.deleteQqchMeasureExpEquByPks(qqchMeasureExpEquPkList);
        }
    }
