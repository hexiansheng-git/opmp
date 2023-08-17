package com.hhwy.pm.qqch.qqchPerformInspection.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspectionDetail;
import com.hhwy.pm.qqch.qqchPerformInspection.mapper.QqchPerformInspectionDetailMapper;
import com.hhwy.pm.qqch.qqchPerformInspection.service.IQqchPerformInspectionDetailService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zqq
 * @date 2023-08-17 10:58:17
 * @remark
 */
@Service
public class QqchPerformInspectionDetailServiceImpl implements IQqchPerformInspectionDetailService {

    @Autowired
    private QqchPerformInspectionDetailMapper qqchPerformInspectionDetailMapper;


    public QqchPerformInspectionDetail getQqchPerformInspectionDetail(QqchPerformInspectionDetail qqchPerformInspectionDetail) {
        return qqchPerformInspectionDetailMapper.getQqchPerformInspectionDetail(qqchPerformInspectionDetail);
    }

    public List<QqchPerformInspectionDetail> getQqchPerformInspectionDetailList(QqchPerformInspectionDetail qqchPerformInspectionDetail) {
        return qqchPerformInspectionDetailMapper.getQqchPerformInspectionDetailList(qqchPerformInspectionDetail);
    }

    @Transactional
    public int insertQqchPerformInspectionDetail(QqchPerformInspectionDetail qqchPerformInspectionDetail) {
        qqchPerformInspectionDetail.setId(IdWorker.createId());
        qqchPerformInspectionDetail.setCreateUser(SecurityUtils.getUserName());
        qqchPerformInspectionDetail.setCreateTime(DateUtils.getNowDate());
        return qqchPerformInspectionDetailMapper.insertQqchPerformInspectionDetail(qqchPerformInspectionDetail);
    }

    @Transactional
    public int insertQqchPerformInspectionDetailList(List<QqchPerformInspectionDetail> qqchPerformInspectionDetailList) {
        for (QqchPerformInspectionDetail qqchPerformInspectionDetail : qqchPerformInspectionDetailList) {
            qqchPerformInspectionDetail.setCreateUser(SecurityUtils.getUserName());
            qqchPerformInspectionDetail.setCreateTime(DateUtils.getNowDate());
        }
        return qqchPerformInspectionDetailMapper.insertQqchPerformInspectionDetailList(qqchPerformInspectionDetailList);
    }

    @Transactional
    public int updateQqchPerformInspectionDetail(QqchPerformInspectionDetail qqchPerformInspectionDetail) {
        qqchPerformInspectionDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchPerformInspectionDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchPerformInspectionDetailMapper.updateQqchPerformInspectionDetail(qqchPerformInspectionDetail);
    }

    @Transactional
    public int updateQqchPerformInspectionDetailList(List<QqchPerformInspectionDetail> qqchPerformInspectionDetailList) {
        for (QqchPerformInspectionDetail qqchPerformInspectionDetail : qqchPerformInspectionDetailList) {
            qqchPerformInspectionDetail.setUpdateUser(SecurityUtils.getUserName());
            qqchPerformInspectionDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchPerformInspectionDetailMapper.updateQqchPerformInspectionDetailList(qqchPerformInspectionDetailList);
    }

    @Transactional
    public int deleteQqchPerformInspectionDetail(QqchPerformInspectionDetail qqchPerformInspectionDetail) {
        qqchPerformInspectionDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchPerformInspectionDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchPerformInspectionDetailMapper.deleteQqchPerformInspectionDetail(qqchPerformInspectionDetail);
    }

    @Transactional
    public int deleteQqchPerformInspectionDetailByPks(List<Long> qqchPerformInspectionDetailPkList) {
        return qqchPerformInspectionDetailMapper.deleteQqchPerformInspectionDetailByPks(qqchPerformInspectionDetailPkList);
    }
}
