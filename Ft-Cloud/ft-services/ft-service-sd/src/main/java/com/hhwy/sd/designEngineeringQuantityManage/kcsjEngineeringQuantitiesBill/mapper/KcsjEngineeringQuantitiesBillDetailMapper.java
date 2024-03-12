package com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.mapper;

import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBillDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wll
 * @date 2024-02-04 14:05:09
 * @remark 勘察设计-设计工程量管理-工程量清单
 */
public interface KcsjEngineeringQuantitiesBillDetailMapper {

    KcsjEngineeringQuantitiesBillDetail getKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

    //根据主表查询子表
    List<KcsjEngineeringQuantitiesBillDetail> getKcsjEngineeringQuantitiesBillDetailList(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

    int insertKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

    int insertKcsjEngineeringQuantitiesBillDetailList(@Param("kcsjEngineeringQuantitiesBillDetailList") List<KcsjEngineeringQuantitiesBillDetail> kcsjEngineeringQuantitiesBillDetailList);

    int updateKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

    int updateKcsjEngineeringQuantitiesBillDetailList(@Param("list") List<KcsjEngineeringQuantitiesBillDetail> list);

    int deleteKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

    int deleteKcsjEngineeringQuantitiesBillDetailByPks(@Param("kcsjEngineeringQuantitiesBillDetailPkList") List<Long> kcsjEngineeringQuantitiesBillDetailPkList);

    void deleteInfoData(@Param("list") List<Long> list,@Param("delUser") String delUser);

    int deleteKcsjEngineeringQuantitiesBillDetailByMainId(@Param("kcsjEngineeringQuantitiesBillPkList") List<Long> kcsjEngineeringQuantitiesBillPkList,@Param("delUser") String delUser);

    List<KcsjEngineeringQuantitiesBillDetail> getIds(@Param("ids") List<Long> ids);

    List<KcsjEngineeringQuantitiesBillDetail> getKcsjEngineeringQuantitiesBillDetailListByMainId(@Param("mainId") Long mainId);
}
