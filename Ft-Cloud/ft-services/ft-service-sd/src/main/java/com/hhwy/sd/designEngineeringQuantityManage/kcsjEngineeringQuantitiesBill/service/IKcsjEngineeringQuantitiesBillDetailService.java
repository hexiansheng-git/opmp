package com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.service;

import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBillDetail;

import java.util.List;

/**
 * @author wll
 * @date 2024-02-04 14:05:09
 * @remark
 */
public interface IKcsjEngineeringQuantitiesBillDetailService {

    KcsjEngineeringQuantitiesBillDetail getKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

    List<KcsjEngineeringQuantitiesBillDetail> getKcsjEngineeringQuantitiesBillDetailList(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

    int insertKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

    int insertKcsjEngineeringQuantitiesBillDetailList(List<KcsjEngineeringQuantitiesBillDetail> kcsjEngineeringQuantitiesBillDetailList);

    int updateKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

    int updateKcsjEngineeringQuantitiesBillDetailList(List<KcsjEngineeringQuantitiesBillDetail> kcsjEngineeringQuantitiesBillDetailList);

    int deleteKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

    int deleteKcsjEngineeringQuantitiesBillDetailByPks(List<Long> kcsjEngineeringQuantitiesBillDetailPkList);

    void deleteByIds(List<Long> kcsjEngineeringQuantitiesBillDetailPkList);

    List<KcsjEngineeringQuantitiesBillDetail> getDetailList(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetailParam);

    List<KcsjEngineeringQuantitiesBillDetail> getKcsjEngineeringQuantitiesBillDetailListByMainId(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetailParam);
}
