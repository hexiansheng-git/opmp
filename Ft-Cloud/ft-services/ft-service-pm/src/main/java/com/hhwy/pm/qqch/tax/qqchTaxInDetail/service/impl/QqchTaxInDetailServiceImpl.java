package com.hhwy.pm.qqch.tax.qqchTaxInDetail.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.tax.qqchTaxInDetail.mapper.QqchTaxInDetailMapper;
import com.hhwy.pm.qqch.tax.qqchTaxInDetail.service.IQqchTaxInDetailService;
import com.hhwy.pm.qqch.tax.qqchTaxInDetail.domain.QqchTaxInDetail;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author mls
 * @date 2023-08-09 18:17:35
 * @remark 
 */
@Service
public class QqchTaxInDetailServiceImpl implements IQqchTaxInDetailService{

    @Autowired
    private QqchTaxInDetailMapper qqchTaxInDetailMapper;

                                                                                                                                                                                                                                                                                                                                                                                                    
    public QqchTaxInDetail getQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail) {
        return qqchTaxInDetailMapper.getQqchTaxInDetail(qqchTaxInDetail);
    }

    public List<QqchTaxInDetail> getQqchTaxInDetailList(QqchTaxInDetail qqchTaxInDetail) {
        return qqchTaxInDetailMapper.getQqchTaxInDetailList(qqchTaxInDetail);
    }

    @Transactional
    public int insertQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail) {
        qqchTaxInDetail.setId(IdWorker.createId());
        qqchTaxInDetail.setCreateUser(SecurityUtils.getUserName());
        qqchTaxInDetail.setCreateTime(DateUtils.getNowDate());
        return qqchTaxInDetailMapper.insertQqchTaxInDetail(qqchTaxInDetail);
    }

    @Transactional
    public int insertQqchTaxInDetailList(List<QqchTaxInDetail> qqchTaxInDetailList) {
        for (QqchTaxInDetail qqchTaxInDetail : qqchTaxInDetailList) {
            qqchTaxInDetail.setId(IdWorker.createId());
            qqchTaxInDetail.setCreateUser(SecurityUtils.getUserName());
            qqchTaxInDetail.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTaxInDetailMapper.insertQqchTaxInDetailList(qqchTaxInDetailList);
    }

    @Transactional
    public int updateQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail) {
        qqchTaxInDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxInDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInDetailMapper.updateQqchTaxInDetail(qqchTaxInDetail);
    }

            @Transactional
        public int updateQqchTaxInDetailList(List<QqchTaxInDetail> qqchTaxInDetailList) {
            for (QqchTaxInDetail qqchTaxInDetail : qqchTaxInDetailList) {
                qqchTaxInDetail.setUpdateUser(SecurityUtils.getUserName());
                qqchTaxInDetail.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchTaxInDetailMapper.updateQqchTaxInDetailList(qqchTaxInDetailList);
        }
    
    @Transactional
    public int deleteQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail) {
        qqchTaxInDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxInDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInDetailMapper.deleteQqchTaxInDetail(qqchTaxInDetail);
    }

            @Transactional
        public int deleteQqchTaxInDetailByPks(List<Long> qqchTaxInDetailPkList) {
            return qqchTaxInDetailMapper.deleteQqchTaxInDetailByPks(qqchTaxInDetailPkList);
        }
    }
