package com.hhwy.pm.qqch.tax.qqchTaxIn.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.tax.qqchTaxIn.mapper.QqchTaxInMapper;
import com.hhwy.pm.qqch.tax.qqchTaxIn.service.IQqchTaxInService;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxIn;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-08-09 18:17:32
 * @remark 
 */
@Service
public class QqchTaxInServiceImpl implements IQqchTaxInService{

    @Autowired
    private QqchTaxInMapper qqchTaxInMapper;

                                                                                                                                                                                                                                                                                                                                                                                        
    public QqchTaxIn getQqchTaxIn(QqchTaxIn qqchTaxIn) {
        return qqchTaxInMapper.getQqchTaxIn(qqchTaxIn);
    }

    public List<QqchTaxIn> getQqchTaxInList(QqchTaxIn qqchTaxIn) {
        return qqchTaxInMapper.getQqchTaxInList(qqchTaxIn);
    }

    @Transactional
    public int insertQqchTaxIn(QqchTaxIn qqchTaxIn) {
        qqchTaxIn.setId(IdWorker.createId());
        qqchTaxIn.setCreateUser(SecurityUtils.getUserName());
        qqchTaxIn.setCreateTime(DateUtils.getNowDate());
        return qqchTaxInMapper.insertQqchTaxIn(qqchTaxIn);
    }

    @Transactional
    public int insertQqchTaxInList(List<QqchTaxIn> qqchTaxInList) {
        for (QqchTaxIn qqchTaxIn : qqchTaxInList) {
            qqchTaxIn.setId(IdWorker.createId());
            qqchTaxIn.setCreateUser(SecurityUtils.getUserName());
            qqchTaxIn.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxInMapper.insertQqchTaxInList(qqchTaxInList);
    }

    @Transactional
    public int updateQqchTaxIn(QqchTaxIn qqchTaxIn) {
        qqchTaxIn.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxIn.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInMapper.updateQqchTaxIn(qqchTaxIn);
    }

            @Transactional
        public int updateQqchTaxInList(List<QqchTaxIn> qqchTaxInList) {
            for (QqchTaxIn qqchTaxIn : qqchTaxInList) {
                qqchTaxIn.setUpdateUser(SecurityUtils.getUserName());
                qqchTaxIn.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchTaxInMapper.updateQqchTaxInList(qqchTaxInList);
        }
    
    @Transactional
    public int deleteQqchTaxIn(QqchTaxIn qqchTaxIn) {
        qqchTaxIn.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxIn.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInMapper.deleteQqchTaxIn(qqchTaxIn);
    }

            @Transactional
        public int deleteQqchTaxInByPks(List<Long> qqchTaxInPkList) {
            return qqchTaxInMapper.deleteQqchTaxInByPks(qqchTaxInPkList);
        }
    }
