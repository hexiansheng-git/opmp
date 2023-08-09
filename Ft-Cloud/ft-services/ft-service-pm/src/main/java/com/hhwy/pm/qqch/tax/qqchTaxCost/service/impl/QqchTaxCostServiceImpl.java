package com.hhwy.pm.qqch.tax.qqchTaxCost.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.tax.qqchTaxCost.mapper.QqchTaxCostMapper;
import com.hhwy.pm.qqch.tax.qqchTaxCost.service.IQqchTaxCostService;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCost;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-08-09 18:17:14
 * @remark 
 */
@Service
public class QqchTaxCostServiceImpl implements IQqchTaxCostService{

    @Autowired
    private QqchTaxCostMapper qqchTaxCostMapper;

                                                                                                                                                                                                                                                                                                                                                                                                                
    public QqchTaxCost getQqchTaxCost(QqchTaxCost qqchTaxCost) {
        return qqchTaxCostMapper.getQqchTaxCost(qqchTaxCost);
    }

    public List<QqchTaxCost> getQqchTaxCostList(QqchTaxCost qqchTaxCost) {
        return qqchTaxCostMapper.getQqchTaxCostList(qqchTaxCost);
    }

    @Transactional
    public int insertQqchTaxCost(QqchTaxCost qqchTaxCost) {
        qqchTaxCost.setId(IdWorker.createId());
        qqchTaxCost.setCreateUser(SecurityUtils.getUserName());
        qqchTaxCost.setCreateTime(DateUtils.getNowDate());
        return qqchTaxCostMapper.insertQqchTaxCost(qqchTaxCost);
    }

    @Transactional
    public int insertQqchTaxCostList(List<QqchTaxCost> qqchTaxCostList) {
        for (QqchTaxCost qqchTaxCost : qqchTaxCostList) {
            qqchTaxCost.setId(IdWorker.createId());
            qqchTaxCost.setCreateUser(SecurityUtils.getUserName());
            qqchTaxCost.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxCostMapper.insertQqchTaxCostList(qqchTaxCostList);
    }

    @Transactional
    public int updateQqchTaxCost(QqchTaxCost qqchTaxCost) {
        qqchTaxCost.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxCost.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxCostMapper.updateQqchTaxCost(qqchTaxCost);
    }

            @Transactional
        public int updateQqchTaxCostList(List<QqchTaxCost> qqchTaxCostList) {
            for (QqchTaxCost qqchTaxCost : qqchTaxCostList) {
                qqchTaxCost.setUpdateUser(SecurityUtils.getUserName());
                qqchTaxCost.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchTaxCostMapper.updateQqchTaxCostList(qqchTaxCostList);
        }
    
    @Transactional
    public int deleteQqchTaxCost(QqchTaxCost qqchTaxCost) {
        qqchTaxCost.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxCost.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxCostMapper.deleteQqchTaxCost(qqchTaxCost);
    }

            @Transactional
        public int deleteQqchTaxCostByPks(List<Long> qqchTaxCostPkList) {
            return qqchTaxCostMapper.deleteQqchTaxCostByPks(qqchTaxCostPkList);
        }
    }
