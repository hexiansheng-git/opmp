package com.hhwy.pm.qqch.preparation.measureexp.beton.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.preparation.measureexp.beton.mapper.QqchExpBetonMapper;
import com.hhwy.pm.qqch.preparation.measureexp.beton.service.IQqchExpBetonService;
import com.hhwy.pm.qqch.preparation.measureexp.beton.domain.QqchExpBeton;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-07-25 18:31:38
 * @remark 
 */
@Service
public class QqchExpBetonServiceImpl implements IQqchExpBetonService{

    @Autowired
    private QqchExpBetonMapper qqchExpBetonMapper;

                                                                                                                                                                                                                                                                                                                                                    
    public QqchExpBeton getQqchExpBeton(QqchExpBeton qqchExpBeton) {
        return qqchExpBetonMapper.getQqchExpBeton(qqchExpBeton);
    }

    public List<QqchExpBeton> getQqchExpBetonList(QqchExpBeton qqchExpBeton) {
        return qqchExpBetonMapper.getQqchExpBetonList(qqchExpBeton);
    }

    @Transactional
    public int insertQqchExpBeton(QqchExpBeton qqchExpBeton) {
        qqchExpBeton.setId(IdWorker.createId());
        qqchExpBeton.setCreateUser(SecurityUtils.getUserName());
        qqchExpBeton.setCreateTime(DateUtils.getNowDate());
        return qqchExpBetonMapper.insertQqchExpBeton(qqchExpBeton);
    }

    @Transactional
    public int insertQqchExpBetonList(List<QqchExpBeton> qqchExpBetonList) {
        for (QqchExpBeton qqchExpBeton : qqchExpBetonList) {
            qqchExpBeton.setId(IdWorker.createId());
            qqchExpBeton.setCreateUser(SecurityUtils.getUserName());
            qqchExpBeton.setCreateTime(DateUtils.getNowDate());
        }
        return qqchExpBetonMapper.insertQqchExpBetonList(qqchExpBetonList);
    }

    @Transactional
    public int updateQqchExpBeton(QqchExpBeton qqchExpBeton) {
        qqchExpBeton.setUpdateUser(SecurityUtils.getUserName());
        qqchExpBeton.setUpdateTime(DateUtils.getNowDate());
        return qqchExpBetonMapper.updateQqchExpBeton(qqchExpBeton);
    }

            @Transactional
        public int updateQqchExpBetonList(List<QqchExpBeton> qqchExpBetonList) {
            for (QqchExpBeton qqchExpBeton : qqchExpBetonList) {
                qqchExpBeton.setUpdateUser(SecurityUtils.getUserName());
                qqchExpBeton.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchExpBetonMapper.updateQqchExpBetonList(qqchExpBetonList);
        }
    
    @Transactional
    public int deleteQqchExpBeton(QqchExpBeton qqchExpBeton) {
        qqchExpBeton.setUpdateUser(SecurityUtils.getUserName());
        qqchExpBeton.setUpdateTime(DateUtils.getNowDate());
        return qqchExpBetonMapper.deleteQqchExpBeton(qqchExpBeton);
    }

            @Transactional
        public int deleteQqchExpBetonByPks(List<Long> qqchExpBetonPkList) {
            return qqchExpBetonMapper.deleteQqchExpBetonByPks(qqchExpBetonPkList);
        }
    }
