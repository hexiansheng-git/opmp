package com.hhwy.sd.groupManage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.groupManage.domain.KcsjGroupManageDetail;
import com.hhwy.sd.groupManage.mapper.KcsjGroupManageDetailMapper;
import com.hhwy.sd.groupManage.service.IKcsjGroupManageDetailService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-12-13 15:27:13
 * @remark
 */
@Service
public class KcsjGroupManageDetailServiceImpl implements IKcsjGroupManageDetailService {

    @Autowired
    private KcsjGroupManageDetailMapper kcsjGroupManageDetailMapper;


    public KcsjGroupManageDetail getKcsjGroupManageDetail(KcsjGroupManageDetail kcsjGroupManageDetail) {
        return kcsjGroupManageDetailMapper.getKcsjGroupManageDetail(kcsjGroupManageDetail);
    }

    public List<KcsjGroupManageDetail> getKcsjGroupManageDetailList(KcsjGroupManageDetail kcsjGroupManageDetail) {
        return kcsjGroupManageDetailMapper.getKcsjGroupManageDetailList(kcsjGroupManageDetail);
    }

    @Transactional
    public int insertKcsjGroupManageDetail(KcsjGroupManageDetail kcsjGroupManageDetail) {
        kcsjGroupManageDetail.setId(IdWorker.createId());
        kcsjGroupManageDetail.setCreateUser(SecurityUtils.getUserName());
        kcsjGroupManageDetail.setCreateTime(DateUtils.getNowDate());
        return kcsjGroupManageDetailMapper.insertKcsjGroupManageDetail(kcsjGroupManageDetail);
    }

    @Transactional
    public int insertKcsjGroupManageDetailList(List<KcsjGroupManageDetail> kcsjGroupManageDetailList) {
        for (KcsjGroupManageDetail kcsjGroupManageDetail : kcsjGroupManageDetailList) {
            kcsjGroupManageDetail.setId(IdWorker.createId());
            kcsjGroupManageDetail.setCreateUser(SecurityUtils.getUserName());
            kcsjGroupManageDetail.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjGroupManageDetailMapper.insertKcsjGroupManageDetailList(kcsjGroupManageDetailList);
    }

    @Transactional
    public int updateKcsjGroupManageDetail(KcsjGroupManageDetail kcsjGroupManageDetail) {
        kcsjGroupManageDetail.setUpdateUser(SecurityUtils.getUserName());
        kcsjGroupManageDetail.setUpdateTime(DateUtils.getNowDate());
        return kcsjGroupManageDetailMapper.updateKcsjGroupManageDetail(kcsjGroupManageDetail);
    }

    @Transactional
    public int updateKcsjGroupManageDetailList(List<KcsjGroupManageDetail> kcsjGroupManageDetailList) {
        for (KcsjGroupManageDetail kcsjGroupManageDetail : kcsjGroupManageDetailList) {
            kcsjGroupManageDetail.setUpdateUser(SecurityUtils.getUserName());
            kcsjGroupManageDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjGroupManageDetailMapper.updateKcsjGroupManageDetailList(kcsjGroupManageDetailList);
    }

    @Transactional
    public int deleteKcsjGroupManageDetail(KcsjGroupManageDetail kcsjGroupManageDetail) {
        kcsjGroupManageDetail.setUpdateUser(SecurityUtils.getUserName());
        kcsjGroupManageDetail.setUpdateTime(DateUtils.getNowDate());
        return kcsjGroupManageDetailMapper.deleteKcsjGroupManageDetail(kcsjGroupManageDetail);
    }

    @Transactional
    public int deleteKcsjGroupManageDetailByPks(List<Long> kcsjGroupManageDetailPkList) {
        return kcsjGroupManageDetailMapper.deleteKcsjGroupManageDetailByPks(kcsjGroupManageDetailPkList);
    }
}
