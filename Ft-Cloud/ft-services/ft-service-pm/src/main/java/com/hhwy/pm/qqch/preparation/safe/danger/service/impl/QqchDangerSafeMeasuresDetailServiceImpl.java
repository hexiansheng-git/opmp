package com.hhwy.pm.qqch.preparation.safe.danger.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerSafeMeasuresDetail;
import com.hhwy.pm.qqch.preparation.safe.danger.mapper.QqchDangerSafeMeasuresDetailMapper;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerSafeMeasuresDetailService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-08-17 13:32:20
 * @remark 8.3.2 危大工程安全技术措施明细
 */
@Service
public class QqchDangerSafeMeasuresDetailServiceImpl implements IQqchDangerSafeMeasuresDetailService {

    @Autowired
    private QqchDangerSafeMeasuresDetailMapper qqchDangerSafeMeasuresDetailMapper;

    public QqchDangerSafeMeasuresDetail getQqchDangerSafeMeasuresDetail(
        QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail) {
        return qqchDangerSafeMeasuresDetailMapper.getQqchDangerSafeMeasuresDetail(qqchDangerSafeMeasuresDetail);
    }

    public List<QqchDangerSafeMeasuresDetail> getQqchDangerSafeMeasuresDetailList(
        QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail) {
        return qqchDangerSafeMeasuresDetailMapper.getQqchDangerSafeMeasuresDetailList(qqchDangerSafeMeasuresDetail);
    }

    @Transactional
    public int insertQqchDangerSafeMeasuresDetail(QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail) {
        qqchDangerSafeMeasuresDetail.setId(IdWorker.createId());
        qqchDangerSafeMeasuresDetail.setCreateUser(SecurityUtils.getUserName());
        qqchDangerSafeMeasuresDetail.setCreateTime(DateUtils.getNowDate());
        return qqchDangerSafeMeasuresDetailMapper.insertQqchDangerSafeMeasuresDetail(qqchDangerSafeMeasuresDetail);
    }

    @Transactional
    public int insertQqchDangerSafeMeasuresDetailList(
        List<QqchDangerSafeMeasuresDetail> qqchDangerSafeMeasuresDetailList) {
        for (QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail : qqchDangerSafeMeasuresDetailList) {
            qqchDangerSafeMeasuresDetail.setId(IdWorker.createId());
            qqchDangerSafeMeasuresDetail.setCreateUser(SecurityUtils.getUserName());
            qqchDangerSafeMeasuresDetail.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDangerSafeMeasuresDetailMapper
            .insertQqchDangerSafeMeasuresDetailList(qqchDangerSafeMeasuresDetailList);
    }

    @Transactional
    public int updateQqchDangerSafeMeasuresDetail(QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail) {
        qqchDangerSafeMeasuresDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchDangerSafeMeasuresDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchDangerSafeMeasuresDetailMapper.updateQqchDangerSafeMeasuresDetail(qqchDangerSafeMeasuresDetail);
    }

    @Transactional
    public int updateQqchDangerSafeMeasuresDetailList(
        List<QqchDangerSafeMeasuresDetail> qqchDangerSafeMeasuresDetailList) {
        for (QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail : qqchDangerSafeMeasuresDetailList) {
            qqchDangerSafeMeasuresDetail.setUpdateUser(SecurityUtils.getUserName());
            qqchDangerSafeMeasuresDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchDangerSafeMeasuresDetailMapper
            .updateQqchDangerSafeMeasuresDetailList(qqchDangerSafeMeasuresDetailList);
    }

    @Transactional
    public int deleteQqchDangerSafeMeasuresDetail(QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail) {
        qqchDangerSafeMeasuresDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchDangerSafeMeasuresDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchDangerSafeMeasuresDetailMapper.deleteQqchDangerSafeMeasuresDetail(qqchDangerSafeMeasuresDetail);
    }

    @Transactional
    public int deleteQqchDangerSafeMeasuresDetailByPks(List<Long> qqchDangerSafeMeasuresDetailPkList) {
        return qqchDangerSafeMeasuresDetailMapper
            .deleteQqchDangerSafeMeasuresDetailByPks(qqchDangerSafeMeasuresDetailPkList);
    }
}
