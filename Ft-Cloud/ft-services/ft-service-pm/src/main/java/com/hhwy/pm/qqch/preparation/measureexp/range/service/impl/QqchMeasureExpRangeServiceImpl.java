package com.hhwy.pm.qqch.preparation.measureexp.range.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.measureexp.range.mapper.QqchMeasureExpRangeMapper;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpRangeService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:01:34
 * @remark 
 */
@Service
public class QqchMeasureExpRangeServiceImpl implements IQqchMeasureExpRangeService {

    @Autowired
    private QqchMeasureExpRangeMapper qqchMeasureExpRangeMapper;

                                                                                                                                                                                                                                                                                        
    public QqchMeasureExpRange getQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange) {
        return qqchMeasureExpRangeMapper.getQqchMeasureExpRange(qqchMeasureExpRange);
    }

    public List<QqchMeasureExpRange> getQqchMeasureExpRangeList(QqchMeasureExpRange qqchMeasureExpRange) {
        return qqchMeasureExpRangeMapper.getQqchMeasureExpRangeList(qqchMeasureExpRange);
    }

    @Transactional
    public int insertQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange) {
        qqchMeasureExpRange.setId(IdWorker.createId());
        qqchMeasureExpRange.setCreateUser(SecurityUtils.getUserName());
        qqchMeasureExpRange.setCreateTime(DateUtils.getNowDate());
        return qqchMeasureExpRangeMapper.insertQqchMeasureExpRange(qqchMeasureExpRange);
    }

    @Transactional
    public int insertQqchMeasureExpRangeList(List<QqchMeasureExpRange> qqchMeasureExpRangeList) {
        for (QqchMeasureExpRange qqchMeasureExpRange : qqchMeasureExpRangeList) {
            qqchMeasureExpRange.setId(IdWorker.createId());
            qqchMeasureExpRange.setCreateUser(SecurityUtils.getUserName());
            qqchMeasureExpRange.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpRangeMapper.insertQqchMeasureExpRangeList(qqchMeasureExpRangeList);
    }

    @Transactional
    public int updateQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange) {
        qqchMeasureExpRange.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpRange.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpRangeMapper.updateQqchMeasureExpRange(qqchMeasureExpRange);
    }

            @Transactional
        public int updateQqchMeasureExpRangeList(List<QqchMeasureExpRange> qqchMeasureExpRangeList) {
            for (QqchMeasureExpRange qqchMeasureExpRange : qqchMeasureExpRangeList) {
                qqchMeasureExpRange.setUpdateUser(SecurityUtils.getUserName());
                qqchMeasureExpRange.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchMeasureExpRangeMapper.updateQqchMeasureExpRangeList(qqchMeasureExpRangeList);
        }
    
    @Transactional
    public int deleteQqchMeasureExpRange(QqchMeasureExpRange qqchMeasureExpRange) {
        qqchMeasureExpRange.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpRange.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpRangeMapper.deleteQqchMeasureExpRange(qqchMeasureExpRange);
    }

            @Transactional
        public int deleteQqchMeasureExpRangeByPks(List<Long> qqchMeasureExpRangePkList) {
            return qqchMeasureExpRangeMapper.deleteQqchMeasureExpRangeByPks(qqchMeasureExpRangePkList);
        }
    }
