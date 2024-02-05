package com.hhwy.pm.qqch.tax.qqchTaxCost.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCostDetail;
import com.hhwy.pm.qqch.tax.qqchTaxCost.mapper.QqchTaxCostDetailMapper;
import com.hhwy.pm.qqch.tax.qqchTaxCost.service.IQqchTaxCostDetailService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:26
 * @remark
 */
@Service
public class QqchTaxCostDetailServiceImpl implements IQqchTaxCostDetailService {
    private final static String TN = "qqch_tax_cost_detail";
    @Autowired
    private QqchTaxCostDetailMapper qqchTaxCostDetailMapper;


    public QqchTaxCostDetail getQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail) {
        return qqchTaxCostDetailMapper.getQqchTaxCostDetail(qqchTaxCostDetail);
    }

    public List<QqchTaxCostDetail> getQqchTaxCostDetailList(QqchTaxCostDetail qqchTaxCostDetail) {
        return qqchTaxCostDetailMapper.getQqchTaxCostDetailList(qqchTaxCostDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail) {
        qqchTaxCostDetail.setId(IdWorker.createId());
        qqchTaxCostDetail.setCreateUser(SecurityUtils.getUserName());
        qqchTaxCostDetail.setCreateTime(DateUtils.getNowDate());
        return qqchTaxCostDetailMapper.insertQqchTaxCostDetail(qqchTaxCostDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchTaxCostDetailList(List<QqchTaxCostDetail> qqchTaxCostDetailList) {
        for (QqchTaxCostDetail qqchTaxCostDetail : qqchTaxCostDetailList) {
            qqchTaxCostDetail.setId(IdWorker.createId());
            qqchTaxCostDetail.setCreateUser(SecurityUtils.getUserName());
            qqchTaxCostDetail.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxCostDetailMapper.insertQqchTaxCostDetailList(qqchTaxCostDetailList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail) {
        qqchTaxCostDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxCostDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxCostDetailMapper.updateQqchTaxCostDetail(qqchTaxCostDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchTaxCostDetailList(List<QqchTaxCostDetail> qqchTaxCostDetailList) {
        for (QqchTaxCostDetail qqchTaxCostDetail : qqchTaxCostDetailList) {
            qqchTaxCostDetail.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxCostDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxCostDetailMapper.updateQqchTaxCostDetailList(qqchTaxCostDetailList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail) {
        qqchTaxCostDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxCostDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxCostDetailMapper.deleteQqchTaxCostDetail(qqchTaxCostDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchTaxCostDetailByPks(List<Long> qqchTaxCostDetailPkList) {
        return qqchTaxCostDetailMapper.deleteQqchTaxCostDetailByPks(qqchTaxCostDetailPkList);
    }

    
    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void save(List<QqchTaxCostDetail> list) {
        if (CollectionUtils.isEmpty(list) || !(list.get(0) instanceof QqchTaxCostDetail)) return;
        this.qqchTaxCostDetailMapper.insertQqchTaxCostDetailList(list);
    }
}
