package com.hhwy.pm.qqch.wzch.specialmaterial.service;


import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialPlan;

import java.util.List;

/**
 * 专项物资发运策划Service接口
 * 
 * @author mls
 * @date 2022-12-07
 */
public interface IWzchSpecialMaterialPlanService {
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
     * 批量删除专项物资发运策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSpecialMaterialPlanByIds(List<Long> ids);

    /**
     * 删除专项物资发运策划信息
     * 
     * @param id 专项物资发运策划ID
     * @return 结果
     */
    int deleteWzchSpecialMaterialPlanById(Long id);

    /**
     * 详情
     * @param wzchSpecialMaterialPlan
     * @return
     */
    WzchSpecialMaterialPlan detail(WzchSpecialMaterialPlan wzchSpecialMaterialPlan);

    /**
     * 删除
     * @param wzchSpecialMaterialPlan
     * @return
     */
    boolean remove(WzchSpecialMaterialPlan wzchSpecialMaterialPlan);

    /**
     * 调整
     * @param wzchSpecialMaterialPlan
     * @return
     */
    WzchSpecialMaterialPlan modify(WzchSpecialMaterialPlan wzchSpecialMaterialPlan);

    void processStatus(WzchSpecialMaterialPlan wzchSpecialMaterialPlan);
}
