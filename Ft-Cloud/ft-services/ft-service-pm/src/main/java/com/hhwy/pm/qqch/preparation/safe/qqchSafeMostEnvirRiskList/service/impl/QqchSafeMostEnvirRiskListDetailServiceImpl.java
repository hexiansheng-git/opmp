package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service.impl;

import java.util.Date;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.mapper.QqchSafeMostEnvirRiskListDetailMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service.IQqchSafeMostEnvirRiskListDetailService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author zq
 * @date 2023-08-14 14:04:04
 * @remark
 */
@Service
public class QqchSafeMostEnvirRiskListDetailServiceImpl implements IQqchSafeMostEnvirRiskListDetailService {

    @Autowired
    private QqchSafeMostEnvirRiskListDetailMapper qqchSafeMostEnvirRiskListDetailMapper;


    public QqchSafeMostEnvirRiskListDetail getQqchSafeMostEnvirRiskListDetail(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail) {
        return qqchSafeMostEnvirRiskListDetailMapper.getQqchSafeMostEnvirRiskListDetail(qqchSafeMostEnvirRiskListDetail);
    }

    public List<QqchSafeMostEnvirRiskListDetail> getQqchSafeMostEnvirRiskListDetailList(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail) {
        return qqchSafeMostEnvirRiskListDetailMapper.getQqchSafeMostEnvirRiskListDetailList(qqchSafeMostEnvirRiskListDetail);
    }

    @Transactional
    public int insertQqchSafeMostEnvirRiskListDetail(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail) {
        qqchSafeMostEnvirRiskListDetail.setId(IdWorker.createId());
        qqchSafeMostEnvirRiskListDetail.setCreateUser(SecurityUtils.getUserName());
        qqchSafeMostEnvirRiskListDetail.setCreateTime(DateUtils.getNowDate());
        return qqchSafeMostEnvirRiskListDetailMapper.insertQqchSafeMostEnvirRiskListDetail(qqchSafeMostEnvirRiskListDetail);
    }

    @Transactional
    public int insertQqchSafeMostEnvirRiskListDetailList(List<QqchSafeMostEnvirRiskListDetail> qqchSafeMostEnvirRiskListDetailList) {
        for (QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail : qqchSafeMostEnvirRiskListDetailList) {
            qqchSafeMostEnvirRiskListDetail.setId(IdWorker.createId());
            qqchSafeMostEnvirRiskListDetail.setCreateUser(SecurityUtils.getUserName());
            qqchSafeMostEnvirRiskListDetail.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSafeMostEnvirRiskListDetailMapper.insertQqchSafeMostEnvirRiskListDetailList(qqchSafeMostEnvirRiskListDetailList);
    }

    @Transactional
    public int updateQqchSafeMostEnvirRiskListDetail(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail) {
        qqchSafeMostEnvirRiskListDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeMostEnvirRiskListDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeMostEnvirRiskListDetailMapper.updateQqchSafeMostEnvirRiskListDetail(qqchSafeMostEnvirRiskListDetail);
    }

    @Transactional
    public int updateQqchSafeMostEnvirRiskListDetailList(List<QqchSafeMostEnvirRiskListDetail> qqchSafeMostEnvirRiskListDetailList) {
        for (QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail : qqchSafeMostEnvirRiskListDetailList) {
            qqchSafeMostEnvirRiskListDetail.setUpdateUser(SecurityUtils.getUserName());
            qqchSafeMostEnvirRiskListDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSafeMostEnvirRiskListDetailMapper.updateQqchSafeMostEnvirRiskListDetailList(qqchSafeMostEnvirRiskListDetailList);
    }

    @Transactional
    public int deleteQqchSafeMostEnvirRiskListDetail(QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail) {
        qqchSafeMostEnvirRiskListDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeMostEnvirRiskListDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeMostEnvirRiskListDetailMapper.deleteQqchSafeMostEnvirRiskListDetail(qqchSafeMostEnvirRiskListDetail);
    }

    @Transactional
    public int deleteQqchSafeMostEnvirRiskListDetailByPks(List<Long> qqchSafeMostEnvirRiskListDetailPkList) {
        return qqchSafeMostEnvirRiskListDetailMapper.deleteQqchSafeMostEnvirRiskListDetailByPks(qqchSafeMostEnvirRiskListDetailPkList);
    }

    @Override
    public List<QqchSafeMostEnvirRiskListDetail> getListByInfoIds(List<Long> infoIdList) {
        return qqchSafeMostEnvirRiskListDetailMapper.getListByInfoIds(infoIdList);
    }

    @Override
    @Transactional
    public void deleteByInfoIds(List<Long> infoIdList, String delUser, Date nowDate) {
        qqchSafeMostEnvirRiskListDetailMapper.deleteByInfoIds(infoIdList,delUser,nowDate);
    }
}
