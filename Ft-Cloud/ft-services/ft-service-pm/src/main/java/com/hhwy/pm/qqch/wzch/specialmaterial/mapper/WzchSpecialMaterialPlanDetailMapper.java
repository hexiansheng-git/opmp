package com.hhwy.pm.qqch.wzch.specialmaterial.mapper;

import java.util.List;

import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialPlanDetail;

/**
 * 专项物资发运策划-发运策划Mapper接口
 * 
 * @author mls
 * @date 2022-12-07
 */
public interface WzchSpecialMaterialPlanDetailMapper {
    /**
     * 查询专项物资发运策划-发运策划
     * 
     * @param id 专项物资发运策划-发运策划ID
     * @return 专项物资发运策划-发运策划
     */
    WzchSpecialMaterialPlanDetail selectWzchSpecialMaterialPlanDetailById(Long id);

    /**
     * 查询专项物资发运策划-发运策划列表
     * 
     * @param wzchSpecialMaterialPlanDetail 专项物资发运策划-发运策划
     * @return 专项物资发运策划-发运策划集合
     */
    List<WzchSpecialMaterialPlanDetail> selectWzchSpecialMaterialPlanDetailList(WzchSpecialMaterialPlanDetail wzchSpecialMaterialPlanDetail);

    /**
     * 新增专项物资发运策划-发运策划
     * 
     * @param wzchSpecialMaterialPlanDetail 专项物资发运策划-发运策划
     * @return 结果
     */
    int insertWzchSpecialMaterialPlanDetail(WzchSpecialMaterialPlanDetail wzchSpecialMaterialPlanDetail);

    /**
     * 修改专项物资发运策划-发运策划
     * 
     * @param wzchSpecialMaterialPlanDetail 专项物资发运策划-发运策划
     * @return 结果
     */
    int updateWzchSpecialMaterialPlanDetail(WzchSpecialMaterialPlanDetail wzchSpecialMaterialPlanDetail);

    /**
     * 删除专项物资发运策划-发运策划
     * 
     * @param id 专项物资发运策划-发运策划ID
     * @return 结果
     */
    int deleteWzchSpecialMaterialPlanDetailById(Long id);

    /**
     * 批量删除专项物资发运策划-发运策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSpecialMaterialPlanDetailByIds(List<Long> ids);

    int batchInsert(List<WzchSpecialMaterialPlanDetail> planDetailList);

    int deleteByPlanId(Long planId);

    int updateValidByPlanId(WzchSpecialMaterialPlanDetail wzchSpecialMaterialPlanDetail);
}
