package com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBillDetail;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.mapper.KcsjEngineeringQuantitiesBillDetailMapper;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service.IKcsjEngineeringQuantitiesBillDetailService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wll
 * @date 2024-02-04 14:05:09
 * @remark 
 */
@Service
public class KcsjEngineeringQuantitiesBillDetailServiceImpl implements IKcsjEngineeringQuantitiesBillDetailService {

    @Autowired
    private KcsjEngineeringQuantitiesBillDetailMapper kcsjEngineeringQuantitiesBillDetailMapper;

                                                                                                                                                                                                                                                                                                                                                                                                    
    public KcsjEngineeringQuantitiesBillDetail getKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail) {
        return kcsjEngineeringQuantitiesBillDetailMapper.getKcsjEngineeringQuantitiesBillDetail(kcsjEngineeringQuantitiesBillDetail);
    }

    public List<KcsjEngineeringQuantitiesBillDetail> getKcsjEngineeringQuantitiesBillDetailList(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail) {
        return kcsjEngineeringQuantitiesBillDetailMapper.getKcsjEngineeringQuantitiesBillDetailList(kcsjEngineeringQuantitiesBillDetail);
    }

    @Transactional
    public int insertKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail) {
        kcsjEngineeringQuantitiesBillDetail.setId(IdWorker.createId());
        kcsjEngineeringQuantitiesBillDetail.setCreateUser(SecurityUtils.getUserName());
        kcsjEngineeringQuantitiesBillDetail.setCreateTime(DateUtils.getNowDate());
        return kcsjEngineeringQuantitiesBillDetailMapper.insertKcsjEngineeringQuantitiesBillDetail(kcsjEngineeringQuantitiesBillDetail);
    }

    @Transactional
    public int insertKcsjEngineeringQuantitiesBillDetailList(List<KcsjEngineeringQuantitiesBillDetail> kcsjEngineeringQuantitiesBillDetailList) {
        for (KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail : kcsjEngineeringQuantitiesBillDetailList) {
            kcsjEngineeringQuantitiesBillDetail.setId(IdWorker.createId());
            kcsjEngineeringQuantitiesBillDetail.setCreateUser(SecurityUtils.getUserName());
            kcsjEngineeringQuantitiesBillDetail.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjEngineeringQuantitiesBillDetailMapper.insertKcsjEngineeringQuantitiesBillDetailList(kcsjEngineeringQuantitiesBillDetailList);
    }

    @Transactional
    public int updateKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail) {
        kcsjEngineeringQuantitiesBillDetail.setUpdateUser(SecurityUtils.getUserName());
        kcsjEngineeringQuantitiesBillDetail.setUpdateTime(DateUtils.getNowDate());
        return kcsjEngineeringQuantitiesBillDetailMapper.updateKcsjEngineeringQuantitiesBillDetail(kcsjEngineeringQuantitiesBillDetail);
    }

            @Transactional
        public int updateKcsjEngineeringQuantitiesBillDetailList(List<KcsjEngineeringQuantitiesBillDetail> kcsjEngineeringQuantitiesBillDetailList) {
            for (KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail : kcsjEngineeringQuantitiesBillDetailList) {
                kcsjEngineeringQuantitiesBillDetail.setUpdateUser(SecurityUtils.getUserName());
                kcsjEngineeringQuantitiesBillDetail.setUpdateTime(DateUtils.getNowDate());
            }
            return kcsjEngineeringQuantitiesBillDetailMapper.updateKcsjEngineeringQuantitiesBillDetailList(kcsjEngineeringQuantitiesBillDetailList);
        }
    
    @Transactional
    public int deleteKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail) {
        kcsjEngineeringQuantitiesBillDetail.setUpdateUser(SecurityUtils.getUserName());
        kcsjEngineeringQuantitiesBillDetail.setUpdateTime(DateUtils.getNowDate());
        return kcsjEngineeringQuantitiesBillDetailMapper.deleteKcsjEngineeringQuantitiesBillDetail(kcsjEngineeringQuantitiesBillDetail);
    }

            @Transactional
        public int deleteKcsjEngineeringQuantitiesBillDetailByPks(List<Long> kcsjEngineeringQuantitiesBillDetailPkList) {
            return kcsjEngineeringQuantitiesBillDetailMapper.deleteKcsjEngineeringQuantitiesBillDetailByPks(kcsjEngineeringQuantitiesBillDetailPkList);
        }
    }
