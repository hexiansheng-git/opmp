package com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service;

import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsList;

import java.util.List;

/**
 * @author wll
 * @date 2024-02-18 14:39:34
 * @remark 勘察设计-设计工程量管理-主材清单
 */
public interface IKcsjMaterialsListService {
    //详情
    KcsjMaterialsList getKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList);
    //列表页
    List<KcsjMaterialsList> getKcsjMaterialsListList(KcsjMaterialsList kcsjMaterialsList);

    int insertKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList);

    int insertKcsjMaterialsListList(List<KcsjMaterialsList> kcsjMaterialsListList);

    int updateKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList);

    int updateKcsjMaterialsListList(List<KcsjMaterialsList> kcsjMaterialsListList);

    int deleteKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList);

    int deleteKcsjMaterialsListByPks(List<Long> kcsjMaterialsListPkList);
}
