package com.hhwy.pm.qqch.wzch.specialmaterial.mapper;

import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialPlan;

import java.util.List;

/**
 * 专项物资发运策划Mapper接口
 * 
 * @author mls
 * @date 2022-12-07
 */
public interface WzchSpecialMaterialPlanMapper {
    /**
     * 查询专项物资发运策划
     * 
     * @param id 专项物资发运策划ID
     * @return 专项物资发运策划
     */
    WzchSpecialMaterialPlan selectWzchSpecialMaterialPlanById(Long id);

    /**
     * 查询专项物资发运策划列表
     * 
     * @param wzchSpecialMaterialPlan 专项物资发运策划
     * @return 专项物资发运策划集合
     */
    List<WzchSpecialMaterialPlan> selectWzchSpecialMaterialPlanList(WzchSpecialMaterialPlan wzchSpecialMaterialPlan);

    /**
     * 新增专项物资发运策划
     * 
     * @param wzchSpecialMaterialPlan 专项物资发运策划
     * @return 结果
     */
    int insertWzchSpecialMaterialPlan(WzchSpecialMaterialPlan wzchSpecialMaterialPlan);

    /**
     * 修改专项物资发运策划
     * 
     * @param wzchSpecialMaterialPlan 专项物资发运策划
     * @return 结果
     */
    int updateWzchSpecialMaterialPlan(WzchSpecialMaterialPlan wzchSpecialMaterialPlan);

    /**
     * 删除专项物资发运策划
     * 
     * @param id 专项物资发运策划ID
     * @return 结果
     */
    int deleteWzchSpecialMaterialPlanById(Long id);

    /**
     * 批量删除专项物资发运策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSpecialMaterialPlanByIds(List<Long> ids);
}
