package com.hhwy.pm.qqch.qqchChange.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChangeDetail;
import com.hhwy.pm.qqch.qqchChange.mapper.QqchChangeDetailMapper;
import com.hhwy.pm.qqch.qqchChange.service.IQqchChangeDetailService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wk
 * @date 2023-11-06 17:41:50
 * @remark
 */
@Service
public class QqchChangeDetailServiceImpl implements IQqchChangeDetailService {

    @Autowired
    private QqchChangeDetailMapper qqchChangeDetailMapper;


    public QqchChangeDetail getQqchChangeDetail(QqchChangeDetail qqchChangeDetail) {
        return qqchChangeDetailMapper.getQqchChangeDetail(qqchChangeDetail);
    }

    public List<QqchChangeDetail> getQqchChangeDetailList(QqchChangeDetail qqchChangeDetail) {
        return qqchChangeDetailMapper.getQqchChangeDetailList(qqchChangeDetail);
    }

    @Transactional
    public int insertQqchChangeDetail(QqchChangeDetail qqchChangeDetail) {
        qqchChangeDetail.setId(IdWorker.createId());
        qqchChangeDetail.setCreateUser(SecurityUtils.getUserName());
        qqchChangeDetail.setCreateTime(DateUtils.getNowDate());
        return qqchChangeDetailMapper.insertQqchChangeDetail(qqchChangeDetail);
    }

    @Transactional
    public int insertQqchChangeDetailList(List<QqchChangeDetail> qqchChangeDetailList) {
        for (QqchChangeDetail qqchChangeDetail : qqchChangeDetailList) {
            qqchChangeDetail.setId(IdWorker.createId());
            qqchChangeDetail.setCreateUser(SecurityUtils.getUserName());
            qqchChangeDetail.setCreateTime(DateUtils.getNowDate());
        }
        return qqchChangeDetailMapper.insertQqchChangeDetailList(qqchChangeDetailList);
    }

    @Transactional
    public int updateQqchChangeDetail(QqchChangeDetail qqchChangeDetail) {
        qqchChangeDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchChangeDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchChangeDetailMapper.updateQqchChangeDetail(qqchChangeDetail);
    }

    @Transactional
    public int updateQqchChangeDetailList(List<QqchChangeDetail> qqchChangeDetailList) {
        for (QqchChangeDetail qqchChangeDetail : qqchChangeDetailList) {
            qqchChangeDetail.setUpdateUser(SecurityUtils.getUserName());
            qqchChangeDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchChangeDetailMapper.updateQqchChangeDetailList(qqchChangeDetailList);
    }

    @Transactional
    public int deleteQqchChangeDetail(QqchChangeDetail qqchChangeDetail) {
        qqchChangeDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchChangeDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchChangeDetailMapper.deleteQqchChangeDetail(qqchChangeDetail);
    }

    @Transactional
    public int deleteQqchChangeDetailByPks(List<Long> qqchChangeDetailPkList) {
        return qqchChangeDetailMapper.deleteQqchChangeDetailByPks(qqchChangeDetailPkList);
    }
}
