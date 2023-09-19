package com.hhwy.pm.qqch.wzch.importplan.mapper;

import com.hhwy.pm.qqch.wzch.importplan.domain.WzchImportExportPlan;

import java.util.List;

/**
 * 进出口策划Mapper接口
 * 
 * @author mls
 * @date 2022-12-05
 */
public interface WzchImportExportPlanMapper {
    /**
     * 查询进出口策划
     * 
     * @param id 进出口策划ID
     * @return 进出口策划
     */
    WzchImportExportPlan selectWzchImportExportPlanById(Long id);

    /**
     * 查询进出口策划列表
     * 
     * @param wzchImportExportPlan 进出口策划
     * @return 进出口策划集合
     */
    List<WzchImportExportPlan> selectWzchImportExportPlanList(WzchImportExportPlan wzchImportExportPlan);

    /**
     * 新增进出口策划
     * 
     * @param wzchImportExportPlan 进出口策划
     * @return 结果
     */
    int insertWzchImportExportPlan(WzchImportExportPlan wzchImportExportPlan);

    /**
     * 修改进出口策划
     * 
     * @param wzchImportExportPlan 进出口策划
     * @return 结果
     */
    int updateWzchImportExportPlan(WzchImportExportPlan wzchImportExportPlan);

    /**
     * 删除进出口策划
     * 
     * @param id 进出口策划ID
     * @return 结果
     */
    int deleteWzchImportExportPlanById(Long id);

    /**
     * 批量删除进出口策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchImportExportPlanByIds(String[] ids);
}
