package com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.mapper.QqchTaxGlobalMapper;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.IQqchTaxGlobalService;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-08-17 16:19:06
 * @remark 
 */
@Service
public class QqchTaxGlobalServiceImpl implements IQqchTaxGlobalService{

    @Autowired
    private QqchTaxGlobalMapper qqchTaxGlobalMapper;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    public QqchTaxGlobal getQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal) {
        return qqchTaxGlobalMapper.getQqchTaxGlobal(qqchTaxGlobal);
    }

    public List<QqchTaxGlobal> getQqchTaxGlobalList(QqchTaxGlobal qqchTaxGlobal) {
        return qqchTaxGlobalMapper.getQqchTaxGlobalList(qqchTaxGlobal);
    }

    @Transactional
    public int insertQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal) {
        qqchTaxGlobal.setId(IdWorker.createId());
        qqchTaxGlobal.setCreateUser(SecurityUtils.getUserName());
        qqchTaxGlobal.setCreateTime(DateUtils.getNowDate());
        return qqchTaxGlobalMapper.insertQqchTaxGlobal(qqchTaxGlobal);
    }

    @Transactional
    public int insertQqchTaxGlobalList(List<QqchTaxGlobal> qqchTaxGlobalList) {
        for (QqchTaxGlobal qqchTaxGlobal : qqchTaxGlobalList) {
            qqchTaxGlobal.setId(IdWorker.createId());
            qqchTaxGlobal.setCreateUser(SecurityUtils.getUserName());
            qqchTaxGlobal.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxGlobalMapper.insertQqchTaxGlobalList(qqchTaxGlobalList);
    }

    @Transactional
    public int updateQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal) {
        qqchTaxGlobal.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxGlobal.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxGlobalMapper.updateQqchTaxGlobal(qqchTaxGlobal);
    }

            @Transactional
        public int updateQqchTaxGlobalList(List<QqchTaxGlobal> qqchTaxGlobalList) {
            for (QqchTaxGlobal qqchTaxGlobal : qqchTaxGlobalList) {
                qqchTaxGlobal.setUpdateUser(SecurityUtils.getUserName());
                qqchTaxGlobal.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchTaxGlobalMapper.updateQqchTaxGlobalList(qqchTaxGlobalList);
        }
    
    @Transactional
    public int deleteQqchTaxGlobal(QqchTaxGlobal qqchTaxGlobal) {
        qqchTaxGlobal.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxGlobal.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxGlobalMapper.deleteQqchTaxGlobal(qqchTaxGlobal);
    }

            @Transactional
        public int deleteQqchTaxGlobalByPks(List<Long> qqchTaxGlobalPkList) {
            return qqchTaxGlobalMapper.deleteQqchTaxGlobalByPks(qqchTaxGlobalPkList);
        }
    }
