package com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsListDetail;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.mapper.KcsjMaterialsListDetailMapper;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service.IKcsjMaterialsListDetailService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wll
 * @date 2024-02-18 15:02:49
 * @remark 勘察设计-设计工程量管理-主材清单明细
 */
@Service
public class KcsjMaterialsListDetailServiceImpl implements IKcsjMaterialsListDetailService {

    @Autowired
    private KcsjMaterialsListDetailMapper kcsjMaterialsListDetailMapper;


    public KcsjMaterialsListDetail getKcsjMaterialsListDetail(KcsjMaterialsListDetail kcsjMaterialsListDetail) {
        return kcsjMaterialsListDetailMapper.getKcsjMaterialsListDetail(kcsjMaterialsListDetail);
    }

    public List<KcsjMaterialsListDetail> getKcsjMaterialsListDetailList(KcsjMaterialsListDetail kcsjMaterialsListDetail) {
        return kcsjMaterialsListDetailMapper.getKcsjMaterialsListDetailList(kcsjMaterialsListDetail);
    }

    @Transactional
    public int insertKcsjMaterialsListDetail(KcsjMaterialsListDetail kcsjMaterialsListDetail) {
        kcsjMaterialsListDetail.setId(IdWorker.createId());
        kcsjMaterialsListDetail.setCreateUser(SecurityUtils.getUserName());
        kcsjMaterialsListDetail.setCreateTime(DateUtils.getNowDate());
        return kcsjMaterialsListDetailMapper.insertKcsjMaterialsListDetail(kcsjMaterialsListDetail);
    }

    @Transactional
    public int insertKcsjMaterialsListDetailList(List<KcsjMaterialsListDetail> kcsjMaterialsListDetailList) {
        for (KcsjMaterialsListDetail kcsjMaterialsListDetail : kcsjMaterialsListDetailList) {
            kcsjMaterialsListDetail.setId(IdWorker.createId());
            kcsjMaterialsListDetail.setCreateUser(SecurityUtils.getUserName());
            kcsjMaterialsListDetail.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjMaterialsListDetailMapper.insertKcsjMaterialsListDetailList(kcsjMaterialsListDetailList);
    }

    @Transactional
    public int updateKcsjMaterialsListDetail(KcsjMaterialsListDetail kcsjMaterialsListDetail) {
        kcsjMaterialsListDetail.setUpdateUser(SecurityUtils.getUserName());
        kcsjMaterialsListDetail.setUpdateTime(DateUtils.getNowDate());
        return kcsjMaterialsListDetailMapper.updateKcsjMaterialsListDetail(kcsjMaterialsListDetail);
    }

    @Transactional
    public int updateKcsjMaterialsListDetailList(List<KcsjMaterialsListDetail> kcsjMaterialsListDetailList) {
        for (KcsjMaterialsListDetail kcsjMaterialsListDetail : kcsjMaterialsListDetailList) {
            kcsjMaterialsListDetail.setUpdateUser(SecurityUtils.getUserName());
            kcsjMaterialsListDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjMaterialsListDetailMapper.updateKcsjMaterialsListDetailList(kcsjMaterialsListDetailList);
    }

    @Transactional
    public int deleteKcsjMaterialsListDetail(KcsjMaterialsListDetail kcsjMaterialsListDetail) {
        kcsjMaterialsListDetail.setUpdateUser(SecurityUtils.getUserName());
        kcsjMaterialsListDetail.setUpdateTime(DateUtils.getNowDate());
        return kcsjMaterialsListDetailMapper.deleteKcsjMaterialsListDetail(kcsjMaterialsListDetail);
    }

    @Transactional
    public int deleteKcsjMaterialsListDetailByPks(List<Long> kcsjMaterialsListDetailPkList) {
        return kcsjMaterialsListDetailMapper.deleteKcsjMaterialsListDetailByPks(kcsjMaterialsListDetailPkList);
    }
}
