package com.hhwy.pm.qqch.wzch.specialmaterial.mapper;

import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialRequestDetail;

import java.util.List;


/**
 * 专项物资发运策划-发运要求Mapper接口
 * 
 * @author mls
 * @date 2022-12-07
 */
public interface WzchSpecialMaterialRequestDetailMapper {
    /**
     * 查询专项物资发运策划-发运要求
     * 
     * @param id 专项物资发运策划-发运要求ID
     * @return 专项物资发运策划-发运要求
     */
    WzchSpecialMaterialRequestDetail selectWzchSpecialMaterialRequestDetailById(Long id);

    /**
     * 查询专项物资发运策划-发运要求列表
     * 
     * @param wzchSpecialMaterialRequestDetail 专项物资发运策划-发运要求
     * @return 专项物资发运策划-发运要求集合
     */
    List<WzchSpecialMaterialRequestDetail> selectWzchSpecialMaterialRequestDetailList(WzchSpecialMaterialRequestDetail wzchSpecialMaterialRequestDetail);

    /**
     * 新增专项物资发运策划-发运要求
     * 
     * @param wzchSpecialMaterialRequestDetail 专项物资发运策划-发运要求
     * @return 结果
     */
    int insertWzchSpecialMaterialRequestDetail(WzchSpecialMaterialRequestDetail wzchSpecialMaterialRequestDetail);

    /**
     * 修改专项物资发运策划-发运要求
     * 
     * @param wzchSpecialMaterialRequestDetail 专项物资发运策划-发运要求
     * @return 结果
     */
    int updateWzchSpecialMaterialRequestDetail(WzchSpecialMaterialRequestDetail wzchSpecialMaterialRequestDetail);

    /**
     * 删除专项物资发运策划-发运要求
     * 
     * @param id 专项物资发运策划-发运要求ID
     * @return 结果
     */
    int deleteWzchSpecialMaterialRequestDetailById(Long id);

    /**
     * 批量删除专项物资发运策划-发运要求
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSpecialMaterialRequestDetailByIds(List<Long> ids);

    /**
     * 批量插入
     * @param planDetailList
     * @return
     */
    int batchInsert(List<WzchSpecialMaterialRequestDetail> planDetailList);

    int deleteByPlanId(Long planId);

    int updateValidByPlanId(WzchSpecialMaterialRequestDetail wzchSpecialMaterialRequestDetail);
}
