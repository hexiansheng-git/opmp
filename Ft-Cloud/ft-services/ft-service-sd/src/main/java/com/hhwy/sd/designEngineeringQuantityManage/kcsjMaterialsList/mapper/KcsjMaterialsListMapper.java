package com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.mapper;

import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wll
 * @date 2024-02-18 14:39:34
 * @remark 勘察设计-设计工程量管理-主材清单
 */
public interface KcsjMaterialsListMapper {

    KcsjMaterialsList getKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList);

    List<KcsjMaterialsList> getKcsjMaterialsListList(KcsjMaterialsList kcsjMaterialsList);

    int insertKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList);

    int insertKcsjMaterialsListList(@Param("kcsjMaterialsListList") List<KcsjMaterialsList> kcsjMaterialsListList);

    int updateKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList);

    int updateKcsjMaterialsListList(@Param("kcsjMaterialsListList") List<KcsjMaterialsList> kcsjMaterialsListList);

    int deleteKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList);

    int deleteKcsjMaterialsListByPks(@Param("kcsjMaterialsListPkList") List<Long> kcsjMaterialsListPkList);
}
