package com.hhwy.pm.qqch.wzch.importplan.service;


import com.hhwy.pm.qqch.wzch.importplan.domain.WzchImportExportPlan;

import java.util.List;

/**
 * 进出口策划Service接口
 * 
 * @author mls
 * @date 2022-12-05
 */
public interface IWzchImportExportPlanService {
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
     * 批量删除进出口策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchImportExportPlanByIds(String ids);

    /**
     * 删除进出口策划信息
     * 
     * @param id 进出口策划ID
     * @return 结果
     */
    int deleteWzchImportExportPlanById(Long id);

    /**
     * 详情
     * @param wzchImportExportPlan
     * @return
     */
    WzchImportExportPlan detail(WzchImportExportPlan wzchImportExportPlan);

    /**
     * 删除
     * @param wzchImportExportPlan
     * @return
     */
    boolean remove(WzchImportExportPlan wzchImportExportPlan);

    /**
     * 调账
     * @param wzchImportExportPlan
     * @return
     */
    WzchImportExportPlan modify(WzchImportExportPlan wzchImportExportPlan);

    void processStatus(WzchImportExportPlan wzchImportExportPlan);
}
