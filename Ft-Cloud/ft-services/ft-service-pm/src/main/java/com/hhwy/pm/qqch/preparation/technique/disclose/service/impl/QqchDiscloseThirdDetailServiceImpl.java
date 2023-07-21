package com.hhwy.pm.qqch.preparation.technique.disclose.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThirdDetail;
import com.hhwy.pm.qqch.preparation.technique.disclose.mapper.QqchDiscloseThirdDetailMapper;
import com.hhwy.pm.qqch.preparation.technique.disclose.service.IQqchDiscloseThirdDetailService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-21 14:26:47
 * @remark 3.5.2三级交底详情
 */
@Service
public class QqchDiscloseThirdDetailServiceImpl implements IQqchDiscloseThirdDetailService {

    @Autowired
    private QqchDiscloseThirdDetailMapper qqchDiscloseThirdDetailMapper;

    public QqchDiscloseThirdDetail getQqchDiscloseThirdDetail(QqchDiscloseThirdDetail qqchDiscloseThirdDetail) {
        return qqchDiscloseThirdDetailMapper.getQqchDiscloseThirdDetail(qqchDiscloseThirdDetail);
    }

    public List<QqchDiscloseThirdDetail> getQqchDiscloseThirdDetailList(
        QqchDiscloseThirdDetail qqchDiscloseThirdDetail) {
        return qqchDiscloseThirdDetailMapper.getQqchDiscloseThirdDetailList(qqchDiscloseThirdDetail);
    }

    @Transactional
    public int insertQqchDiscloseThirdDetail(QqchDiscloseThirdDetail qqchDiscloseThirdDetail) {
        qqchDiscloseThirdDetail.setId(IdWorker.createId());
        qqchDiscloseThirdDetail.setCreateUser(SecurityUtils.getUserName());
        qqchDiscloseThirdDetail.setCreateTime(DateUtils.getNowDate());
        return qqchDiscloseThirdDetailMapper.insertQqchDiscloseThirdDetail(qqchDiscloseThirdDetail);
    }

    @Transactional
    public int insertQqchDiscloseThirdDetailList(List<QqchDiscloseThirdDetail> qqchDiscloseThirdDetailList) {
        for (QqchDiscloseThirdDetail qqchDiscloseThirdDetail : qqchDiscloseThirdDetailList) {
            qqchDiscloseThirdDetail.setId(IdWorker.createId());
            qqchDiscloseThirdDetail.setCreateUser(SecurityUtils.getUserName());
            qqchDiscloseThirdDetail.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDiscloseThirdDetailMapper.insertQqchDiscloseThirdDetailList(qqchDiscloseThirdDetailList);
    }

    @Transactional
    public int updateQqchDiscloseThirdDetail(QqchDiscloseThirdDetail qqchDiscloseThirdDetail) {
        qqchDiscloseThirdDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchDiscloseThirdDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchDiscloseThirdDetailMapper.updateQqchDiscloseThirdDetail(qqchDiscloseThirdDetail);
    }

    @Transactional
    public int updateQqchDiscloseThirdDetailList(List<QqchDiscloseThirdDetail> qqchDiscloseThirdDetailList) {
        for (QqchDiscloseThirdDetail qqchDiscloseThirdDetail : qqchDiscloseThirdDetailList) {
            qqchDiscloseThirdDetail.setUpdateUser(SecurityUtils.getUserName());
            qqchDiscloseThirdDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchDiscloseThirdDetailMapper.updateQqchDiscloseThirdDetailList(qqchDiscloseThirdDetailList);
    }

    @Transactional
    public int deleteQqchDiscloseThirdDetail(QqchDiscloseThirdDetail qqchDiscloseThirdDetail) {
        qqchDiscloseThirdDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchDiscloseThirdDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchDiscloseThirdDetailMapper.deleteQqchDiscloseThirdDetail(qqchDiscloseThirdDetail);
    }

    @Transactional
    public int deleteQqchDiscloseThirdDetailByPks(List<Long> qqchDiscloseThirdDetailPkList) {
        return qqchDiscloseThirdDetailMapper.deleteQqchDiscloseThirdDetailByPks(qqchDiscloseThirdDetailPkList);
    }
}
