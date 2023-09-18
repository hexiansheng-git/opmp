package com.hhwy.pm.qqch.wzch.specialmaterial.service;

import java.io.IOException;
import java.util.List;

import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialPlan;
import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialPlanDetail;
import org.springframework.web.multipart.MultipartFile;

/**
 * 专项物资发运策划-发运策划Service接口
 * 
 * @author mls
 * @date 2022-12-07
 */
public interface IWzchSpecialMaterialPlanDetailService {
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
     * 批量删除专项物资发运策划-发运策划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSpecialMaterialPlanDetailByIds(List<Long> ids);

    /**
     * 删除专项物资发运策划-发运策划信息
     *
     * @param id 专项物资发运策划-发运策划ID
     * @return 结果
     */
    int deleteWzchSpecialMaterialPlanDetailById(Long id);

    /**
     * 导入
     * @param file
     * @return
     * @throws IOException
     */
    List<WzchSpecialMaterialPlanDetail> importPlanDetail(MultipartFile file) throws IOException;

    /**
     * 保存
     * @param wzchSpecialMaterialPlan
     * @return
     */
    Long save(WzchSpecialMaterialPlan wzchSpecialMaterialPlan);

    List<WzchSpecialMaterialPlanDetail> exportPlanDetail(List<WzchSpecialMaterialPlanDetail> list);

    int updateValidByPlanId(WzchSpecialMaterialPlanDetail wzchSpecialMaterialPlanDetail);
}



