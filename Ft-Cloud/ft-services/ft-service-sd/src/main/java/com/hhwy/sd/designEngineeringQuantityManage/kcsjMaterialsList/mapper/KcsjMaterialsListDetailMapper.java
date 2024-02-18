package com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.mapper;

import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsListDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wll
 * @date 2024-02-18 15:02:49
 * @remark 勘察设计-设计工程量管理-主材清单
 */
public interface KcsjMaterialsListDetailMapper {

    KcsjMaterialsListDetail getKcsjMaterialsListDetail(KcsjMaterialsListDetail kcsjMaterialsListDetail);

    List<KcsjMaterialsListDetail> getKcsjMaterialsListDetailList(KcsjMaterialsListDetail kcsjMaterialsListDetail);

    int insertKcsjMaterialsListDetail(KcsjMaterialsListDetail kcsjMaterialsListDetail);

    int insertKcsjMaterialsListDetailList(@Param("kcsjMaterialsListDetailList") List<KcsjMaterialsListDetail> kcsjMaterialsListDetailList);

    int updateKcsjMaterialsListDetail(KcsjMaterialsListDetail kcsjMaterialsListDetail);

    int updateKcsjMaterialsListDetailList(@Param("kcsjMaterialsListDetailList") List<KcsjMaterialsListDetail> kcsjMaterialsListDetailList);

    int deleteKcsjMaterialsListDetail(KcsjMaterialsListDetail kcsjMaterialsListDetail);

    int deleteKcsjMaterialsListDetailByPks(@Param("kcsjMaterialsListDetailPkList") List<Long> kcsjMaterialsListDetailPkList);
}
