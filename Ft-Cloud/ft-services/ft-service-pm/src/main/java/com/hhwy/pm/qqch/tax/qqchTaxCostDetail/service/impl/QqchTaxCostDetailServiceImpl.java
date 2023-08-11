package com.hhwy.pm.qqch.tax.qqchTaxCostDetail.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.tax.qqchTaxCostDetail.domain.QqchTaxCostDetail;
import com.hhwy.pm.qqch.tax.qqchTaxCostDetail.mapper.QqchTaxCostDetailMapper;
import com.hhwy.pm.qqch.tax.qqchTaxCostDetail.service.IQqchTaxCostDetailService;
import com.hhwy.utils.idworker.IdWorker;
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

    @Autowired
    private QqchTaxCostDetailMapper qqchTaxCostDetailMapper;


    public QqchTaxCostDetail getQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail) {
        return qqchTaxCostDetailMapper.getQqchTaxCostDetail(qqchTaxCostDetail);
    }

    public List<QqchTaxCostDetail> getQqchTaxCostDetailList(QqchTaxCostDetail qqchTaxCostDetail) {
        return qqchTaxCostDetailMapper.getQqchTaxCostDetailList(qqchTaxCostDetail);
    }

    @Transactional
    public int insertQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail) {
        qqchTaxCostDetail.setId(IdWorker.createId());
        qqchTaxCostDetail.setCreateUser(SecurityUtils.getUserName());
        qqchTaxCostDetail.setCreateTime(DateUtils.getNowDate());
        return qqchTaxCostDetailMapper.insertQqchTaxCostDetail(qqchTaxCostDetail);
    }

    @Transactional
    public int insertQqchTaxCostDetailList(List<QqchTaxCostDetail> qqchTaxCostDetailList) {
        for (QqchTaxCostDetail qqchTaxCostDetail : qqchTaxCostDetailList) {
            qqchTaxCostDetail.setId(IdWorker.createId());
            qqchTaxCostDetail.setCreateUser(SecurityUtils.getUserName());
            qqchTaxCostDetail.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxCostDetailMapper.insertQqchTaxCostDetailList(qqchTaxCostDetailList);
    }

    @Transactional
    public int updateQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail) {
        qqchTaxCostDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxCostDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxCostDetailMapper.updateQqchTaxCostDetail(qqchTaxCostDetail);
    }

    @Transactional
    public int updateQqchTaxCostDetailList(List<QqchTaxCostDetail> qqchTaxCostDetailList) {
        for (QqchTaxCostDetail qqchTaxCostDetail : qqchTaxCostDetailList) {
            qqchTaxCostDetail.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxCostDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxCostDetailMapper.updateQqchTaxCostDetailList(qqchTaxCostDetailList);
    }

    @Transactional
    public int deleteQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail) {
        qqchTaxCostDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxCostDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxCostDetailMapper.deleteQqchTaxCostDetail(qqchTaxCostDetail);
    }

    @Transactional
    public int deleteQqchTaxCostDetailByPks(List<Long> qqchTaxCostDetailPkList) {
        return qqchTaxCostDetailMapper.deleteQqchTaxCostDetailByPks(qqchTaxCostDetailPkList);
    }
}
