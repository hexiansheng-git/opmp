package com.hhwy.pm.qqch.preparation.sbch.imported.material.service;


import com.hhwy.pm.qqch.preparation.sbch.imported.material.domain.SbchMaterialTranPlanDetail;

import java.util.List;

/**
 * 大型成套设备运输方案详情Service接口
 * 
 * @author zq
 * @date 2022-12-12
 */
public interface ISbchMaterialTranPlanDetailService {
    /**
     * 查询大型成套设备运输方案详情
     * 
     * @param id 大型成套设备运输方案详情ID
     * @return 大型成套设备运输方案详情
     */
    SbchMaterialTranPlanDetail selectSbchMaterialTranPlanDetailById(Long id);

    /**
     * 查询大型成套设备运输方案详情列表
     * 
     * @param sbchMaterialTranPlanDetail 大型成套设备运输方案详情
     * @return 大型成套设备运输方案详情集合
     */
    List<SbchMaterialTranPlanDetail> selectSbchMaterialTranPlanDetailList(SbchMaterialTranPlanDetail sbchMaterialTranPlanDetail);

    /**
     * 新增大型成套设备运输方案详情
     * 
     * @param sbchMaterialTranPlanDetail 大型成套设备运输方案详情
     * @return 结果
     */
    int insertSbchMaterialTranPlanDetail(SbchMaterialTranPlanDetail sbchMaterialTranPlanDetail);

    /**
     * 修改大型成套设备运输方案详情
     * 
     * @param sbchMaterialTranPlanDetail 大型成套设备运输方案详情
     * @return 结果
     */
    int updateSbchMaterialTranPlanDetail(SbchMaterialTranPlanDetail sbchMaterialTranPlanDetail);

    /**
     * 批量删除大型成套设备运输方案详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchMaterialTranPlanDetailByIds(String ids);

    /**
     * 删除大型成套设备运输方案详情信息
     * 
     * @param id 大型成套设备运输方案详情ID
     * @return 结果
     */
    int deleteSbchMaterialTranPlanDetailById(Long id);

    void batchInsert(List<SbchMaterialTranPlanDetail> detailList);

    void deleteSbchMaterialTranPlanByPlanId(Long id);
}
