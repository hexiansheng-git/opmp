package com.hhwy.pm.qqch.wzch.specialmaterial.service;

import java.io.IOException;
import java.util.List;

import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialRequestDetail;
import org.springframework.web.multipart.MultipartFile;

/**
 * 专项物资发运策划-发运要求Service接口
 * 
 * @author mls
 * @date 2022-12-07
 */
public interface IWzchSpecialMaterialRequestDetailService {
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
     * 批量删除专项物资发运策划-发运要求
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSpecialMaterialRequestDetailByIds(List<Long> ids);

    /**
     * 删除专项物资发运策划-发运要求信息
     * 
     * @param id 专项物资发运策划-发运要求ID
     * @return 结果
     */
    int deleteWzchSpecialMaterialRequestDetailById(Long id);

    /**
     * 导入
     * @param file
     * @return
     */
    List<WzchSpecialMaterialRequestDetail> importRequestDetail(MultipartFile file) throws IOException;

    /**
     * 批量插入
     * @param planDetailList
     * @return
     */
    int batchInsert(List<WzchSpecialMaterialRequestDetail> planDetailList);

    int deleteByPlanId(Long planId);

    int updateValidByPlanId(WzchSpecialMaterialRequestDetail wzchSpecialMaterialRequestDetail);
}
