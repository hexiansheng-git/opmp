package com.hhwy.pm.qqch.qqchWorkPlan.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import com.hhwy.pm.qqch.qqchWorkPlan.mapper.QqchWorkPlanDetailMapper;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanDetailService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author hwj
 * @date 2023-07-14 17:15:57
 * @remark
 */
@Service
public class QqchWorkPlanDetailServiceImpl implements IQqchWorkPlanDetailService {

    @Autowired
    private QqchWorkPlanDetailMapper qqchWorkPlanDetailMapper;


    public QqchWorkPlanDetail getQqchWorkPlanDetail(QqchWorkPlanDetail qqchWorkPlanDetail) {
        return qqchWorkPlanDetailMapper.getQqchWorkPlanDetail(qqchWorkPlanDetail);
    }

    public List<QqchWorkPlanDetail> getQqchWorkPlanDetailList(QqchWorkPlanDetail qqchWorkPlanDetail) {
        return qqchWorkPlanDetailMapper.getQqchWorkPlanDetailList(qqchWorkPlanDetail);
    }

    @Transactional
    public int insertQqchWorkPlanDetail(QqchWorkPlanDetail qqchWorkPlanDetail) {
        qqchWorkPlanDetail.setId(IdWorker.createId());
        qqchWorkPlanDetail.setCreateUser(SecurityUtils.getUserName());
        qqchWorkPlanDetail.setCreateTime(DateUtils.getNowDate());
        return qqchWorkPlanDetailMapper.insertQqchWorkPlanDetail(qqchWorkPlanDetail);
    }

    @Transactional
    public int insertQqchWorkPlanDetailList(List<QqchWorkPlanDetail> qqchWorkPlanDetailList) {
        for (QqchWorkPlanDetail qqchWorkPlanDetail : qqchWorkPlanDetailList) {
            qqchWorkPlanDetail.setId(IdWorker.createId());
            qqchWorkPlanDetail.setCreateUser(SecurityUtils.getUserName());
            qqchWorkPlanDetail.setCreateTime(DateUtils.getNowDate());
        }
        return qqchWorkPlanDetailMapper.insertQqchWorkPlanDetailList(qqchWorkPlanDetailList);
    }

    @Transactional
    public int updateQqchWorkPlanDetail(QqchWorkPlanDetail qqchWorkPlanDetail) {
        qqchWorkPlanDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlanDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlanDetailMapper.updateQqchWorkPlanDetail(qqchWorkPlanDetail);
    }

    @Transactional
    public int updateQqchWorkPlanDetailList(List<QqchWorkPlanDetail> qqchWorkPlanDetailList) {
        for (QqchWorkPlanDetail qqchWorkPlanDetail : qqchWorkPlanDetailList) {
            qqchWorkPlanDetail.setUpdateUser(SecurityUtils.getUserName());
            qqchWorkPlanDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchWorkPlanDetailMapper.updateQqchWorkPlanDetailList(qqchWorkPlanDetailList);
    }

    @Transactional
    public int deleteQqchWorkPlanDetail(QqchWorkPlanDetail qqchWorkPlanDetail) {
        qqchWorkPlanDetail.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlanDetail.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlanDetailMapper.deleteQqchWorkPlanDetail(qqchWorkPlanDetail);
    }

    @Transactional
    public int deleteQqchWorkPlanDetailByPks(List<Long> qqchWorkPlanDetailPkList) {
        return qqchWorkPlanDetailMapper.deleteQqchWorkPlanDetailByPks(qqchWorkPlanDetailPkList);
    }
}
