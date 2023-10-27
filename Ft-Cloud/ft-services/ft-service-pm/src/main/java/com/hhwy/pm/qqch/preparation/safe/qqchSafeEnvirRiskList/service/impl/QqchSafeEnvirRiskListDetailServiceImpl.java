package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.mapper.QqchSafeEnvirRiskListDetailMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service.IQqchSafeEnvirRiskListDetailService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-14 14:00:39
 * @remark
 */
@Service
public class QqchSafeEnvirRiskListDetailServiceImpl implements IQqchSafeEnvirRiskListDetailService {

    @Autowired
    private QqchSafeEnvirRiskListDetailMapper qqchSafeEnvirRiskListDetailMapper;


    public QqchSafeEnvirRiskListDetail getQqchSafeEnvirRiskListDetail(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail) {
        return qqchSafeEnvirRiskListDetailMapper.getQqchSafeEnvirRiskListDetail(qqchSafeEnvirRiskListDetail);
    }

    public List<QqchSafeEnvirRiskListDetail> getQqchSafeEnvirRiskListDetailList(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail) {
        return qqchSafeEnvirRiskListDetailMapper.getQqchSafeEnvirRiskListDetailList(qqchSafeEnvirRiskListDetail);
    }

    @Transactional
    public int insertQqchSafeEnvirRiskListDetail(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail) {
        qqchSafeEnvirRiskListDetail.setId(IdWorker.createId());
        qqchSafeEnvirRiskListDetail.setCreateUser(SecurityUtils.getUserName());
        qqchSafeEnvirRiskListDetail.setCreateTime(DateUtils.getNowDate());
        return qqchSafeEnvirRiskListDetailMapper.insertQqchSafeEnvirRiskListDetail(qqchSafeEnvirRiskListDetail);
    }

    @Transactional
    public int insertQqchSafeEnvirRiskListDetailList(List<QqchSafeEnvirRiskListDetail> qqchSafeEnvirRiskListDetailList) {
        for (QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail : qqchSafeEnvirRiskListDetailList) {
            qqchSafeEnvirRiskListDetail.setId(IdWorker.createId());
            qqchSafeEnvirRiskListDetail.setCreateUser(SecurityUtils.getUserName());
            qqchSafeEnvirRiskListDetail.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSafeEnvirRiskListDetailMapper.insertQqchSafeEnvirRiskListDetailList(qqchSafeEnvirRiskListDetailList);
    }

    @Transactional
    public int updateQqchSafeEnvirRiskListDetail(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail) {
        qqchSafeEnvirRiskListDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeEnvirRiskListDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeEnvirRiskListDetailMapper.updateQqchSafeEnvirRiskListDetail(qqchSafeEnvirRiskListDetail);
    }

    @Transactional
    public int updateQqchSafeEnvirRiskListDetailList(List<QqchSafeEnvirRiskListDetail> qqchSafeEnvirRiskListDetailList) {
        for (QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail : qqchSafeEnvirRiskListDetailList) {
            qqchSafeEnvirRiskListDetail.setUpdateUser(SecurityUtils.getUserName());
            qqchSafeEnvirRiskListDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSafeEnvirRiskListDetailMapper.updateQqchSafeEnvirRiskListDetailList(qqchSafeEnvirRiskListDetailList);
    }

    @Transactional
    public int deleteQqchSafeEnvirRiskListDetail(QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail) {
        qqchSafeEnvirRiskListDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeEnvirRiskListDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeEnvirRiskListDetailMapper.deleteQqchSafeEnvirRiskListDetail(qqchSafeEnvirRiskListDetail);
    }

    @Transactional
    public int deleteQqchSafeEnvirRiskListDetailByPks(List<Long> qqchSafeEnvirRiskListDetailPkList) {
        return qqchSafeEnvirRiskListDetailMapper.deleteQqchSafeEnvirRiskListDetailByPks(qqchSafeEnvirRiskListDetailPkList);
    }

    @Override
    public List<QqchSafeEnvirRiskListDetail> getQqchSafeEnvirRiskListDetailListByInfoId(List<Long> infoIdList) {
        return qqchSafeEnvirRiskListDetailMapper.getQqchSafeEnvirRiskListDetailListByInfoId(infoIdList);
    }

    @Override
    @Transactional
    public void deleteByInfoId(Long infoId, String delUser, Date delTime) {
        qqchSafeEnvirRiskListDetailMapper.deleteByInfoId(infoId,delUser,delTime);
    }
}
