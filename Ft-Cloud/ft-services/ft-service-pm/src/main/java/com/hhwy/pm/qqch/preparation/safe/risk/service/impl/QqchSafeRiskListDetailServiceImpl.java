package com.hhwy.pm.qqch.preparation.safe.risk.service.impl;

import java.util.Date;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.risk.mapper.QqchSafeRiskListDetailMapper;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskListDetailService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hhwy.utils.idworker.IdWorker;

/**
 * @author zq
 * @date 2023-08-11 13:41:38
 * @remark 
 */
@Service
public class QqchSafeRiskListDetailServiceImpl implements IQqchSafeRiskListDetailService {

    @Autowired
    private QqchSafeRiskListDetailMapper qqchSafeRiskListDetailMapper;

                                                                                                                                                                                                                                                                            
    public QqchSafeRiskListDetail getQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail) {
        return qqchSafeRiskListDetailMapper.getQqchSafeRiskListDetail(qqchSafeRiskListDetail);
    }

    public List<QqchSafeRiskListDetail> getQqchSafeRiskListDetailList(QqchSafeRiskListDetail qqchSafeRiskListDetail) {
        return qqchSafeRiskListDetailMapper.getQqchSafeRiskListDetailList(qqchSafeRiskListDetail);
    }

    @Transactional
    public int insertQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail) {
        qqchSafeRiskListDetail.setId(IdWorker.createId());
        qqchSafeRiskListDetail.setCreateUser(SecurityUtils.getUserName());
        qqchSafeRiskListDetail.setCreateTime(DateUtils.getNowDate());
        return qqchSafeRiskListDetailMapper.insertQqchSafeRiskListDetail(qqchSafeRiskListDetail);
    }

    @Transactional
    public int insertQqchSafeRiskListDetailList(List<QqchSafeRiskListDetail> qqchSafeRiskListDetailList) {
        for (QqchSafeRiskListDetail qqchSafeRiskListDetail : qqchSafeRiskListDetailList) {
            qqchSafeRiskListDetail.setId(IdWorker.createId());
            qqchSafeRiskListDetail.setCreateUser(SecurityUtils.getUserName());
            qqchSafeRiskListDetail.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSafeRiskListDetailMapper.insertQqchSafeRiskListDetailList(qqchSafeRiskListDetailList);
    }

    @Transactional
    public int updateQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail) {
        qqchSafeRiskListDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeRiskListDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeRiskListDetailMapper.updateQqchSafeRiskListDetail(qqchSafeRiskListDetail);
    }

            @Transactional
        public int updateQqchSafeRiskListDetailList(List<QqchSafeRiskListDetail> qqchSafeRiskListDetailList) {
            for (QqchSafeRiskListDetail qqchSafeRiskListDetail : qqchSafeRiskListDetailList) {
                qqchSafeRiskListDetail.setUpdateUser(SecurityUtils.getUserName());
                qqchSafeRiskListDetail.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchSafeRiskListDetailMapper.updateQqchSafeRiskListDetailList(qqchSafeRiskListDetailList);
        }
    
    @Transactional
    public int deleteQqchSafeRiskListDetail(QqchSafeRiskListDetail qqchSafeRiskListDetail) {
        qqchSafeRiskListDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchSafeRiskListDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchSafeRiskListDetailMapper.deleteQqchSafeRiskListDetail(qqchSafeRiskListDetail);
    }

            @Transactional
        public int deleteQqchSafeRiskListDetailByPks(List<Long> qqchSafeRiskListDetailPkList) {
            return qqchSafeRiskListDetailMapper.deleteQqchSafeRiskListDetailByPks(qqchSafeRiskListDetailPkList);
        }

    @Override
    @Transactional
    public void deleteByInfoIds(List<Long> infoIdList, String userId, String userName, Date nowDate) {
        qqchSafeRiskListDetailMapper.deleteByInfoIds(infoIdList,userName,nowDate);
    }
}
