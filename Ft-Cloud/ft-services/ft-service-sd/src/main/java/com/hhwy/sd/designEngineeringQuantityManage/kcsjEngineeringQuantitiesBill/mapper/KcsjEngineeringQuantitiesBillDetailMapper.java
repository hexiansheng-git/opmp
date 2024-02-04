package com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.mapper;

import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBillDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wll
 * @date 2024-02-04 14:05:09
 * @remark 
 */
public interface KcsjEngineeringQuantitiesBillDetailMapper {
                                                                                                                                                                                                                                                                                                                                                                                                    
    KcsjEngineeringQuantitiesBillDetail getKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);
    //根据主表查询子表
    List<KcsjEngineeringQuantitiesBillDetail> getKcsjEngineeringQuantitiesBillDetailList(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

    int insertKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

    int insertKcsjEngineeringQuantitiesBillDetailList(@Param("kcsjEngineeringQuantitiesBillDetailList") List<KcsjEngineeringQuantitiesBillDetail> kcsjEngineeringQuantitiesBillDetailList);

    int updateKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

            int updateKcsjEngineeringQuantitiesBillDetailList(@Param("kcsjEngineeringQuantitiesBillDetailList") List<KcsjEngineeringQuantitiesBillDetail> kcsjEngineeringQuantitiesBillDetailList);
    
    int deleteKcsjEngineeringQuantitiesBillDetail(KcsjEngineeringQuantitiesBillDetail kcsjEngineeringQuantitiesBillDetail);

            int deleteKcsjEngineeringQuantitiesBillDetailByPks(@Param("kcsjEngineeringQuantitiesBillDetailPkList") List<Long> kcsjEngineeringQuantitiesBillDetailPkList);
    }
