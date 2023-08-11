package com.hhwy.pm.qqch.tax.qqchTaxIn.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxInDetail;
import com.hhwy.pm.qqch.tax.qqchTaxIn.mapper.QqchTaxInDetailMapper;
import com.hhwy.pm.qqch.tax.qqchTaxIn.service.IQqchTaxInDetailService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:35
 * @remark
 */
@Service
public class QqchTaxInDetailServiceImpl implements IQqchTaxInDetailService {
    
    private final static String TN = "qqch_tax_in_detail";

    @Autowired
    private QqchTaxInDetailMapper qqchTaxInDetailMapper;


    public QqchTaxInDetail getQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail) {
        return qqchTaxInDetailMapper.getQqchTaxInDetail(qqchTaxInDetail);
    }

    public List<QqchTaxInDetail> getQqchTaxInDetailList(QqchTaxInDetail qqchTaxInDetail) {
        return qqchTaxInDetailMapper.getQqchTaxInDetailList(qqchTaxInDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail) {
        qqchTaxInDetail.setId(IdWorker.createId());
        qqchTaxInDetail.setCreateUser(SecurityUtils.getUserName());
        qqchTaxInDetail.setCreateTime(DateUtils.getNowDate());
        return qqchTaxInDetailMapper.insertQqchTaxInDetail(qqchTaxInDetail);
    }

    public int insertQqchTaxInDetailList(List<QqchTaxInDetail> qqchTaxInDetailList) {
        return qqchTaxInDetailMapper.insertQqchTaxInDetailList(qqchTaxInDetailList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail) {
        qqchTaxInDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxInDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInDetailMapper.updateQqchTaxInDetail(qqchTaxInDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchTaxInDetailList(List<QqchTaxInDetail> qqchTaxInDetailList) {
        for (QqchTaxInDetail qqchTaxInDetail : qqchTaxInDetailList) {
            qqchTaxInDetail.setUpdateUser(SecurityUtils.getUserName());
            qqchTaxInDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTaxInDetailMapper.updateQqchTaxInDetailList(qqchTaxInDetailList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail) {
        qqchTaxInDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchTaxInDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchTaxInDetailMapper.deleteQqchTaxInDetail(qqchTaxInDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchTaxInDetailByPks(List<Long> qqchTaxInDetailPkList) {
        return qqchTaxInDetailMapper.deleteQqchTaxInDetailByPks(qqchTaxInDetailPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void save(List<QqchTaxInDetail> allDetails) {
        this.qqchTaxInDetailMapper.insertQqchTaxInDetailList(allDetails);
    }
}
