package com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service;

import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsListDetail;

import java.util.List;

/**
 * @author wll
 * @date 2024-02-18 15:02:49
 * @remark 勘察设计-设计工程量管理-主材清单明细
 */
public interface IKcsjMaterialsListDetailService {

    KcsjMaterialsListDetail getKcsjMaterialsListDetail(KcsjMaterialsListDetail kcsjMaterialsListDetail);

    List<KcsjMaterialsListDetail> getKcsjMaterialsListDetailList(KcsjMaterialsListDetail kcsjMaterialsListDetail);

    int insertKcsjMaterialsListDetail(KcsjMaterialsListDetail kcsjMaterialsListDetail);

    //批量新增
    int insertKcsjMaterialsListDetailList(List<KcsjMaterialsListDetail> kcsjMaterialsListDetailList);

    int updateKcsjMaterialsListDetail(KcsjMaterialsListDetail kcsjMaterialsListDetail);

    int updateKcsjMaterialsListDetailList(List<KcsjMaterialsListDetail> kcsjMaterialsListDetailList);

    int deleteKcsjMaterialsListDetail(KcsjMaterialsListDetail kcsjMaterialsListDetail);

    int deleteKcsjMaterialsListDetailByPks(List<Long> kcsjMaterialsListDetailPkList);
}
