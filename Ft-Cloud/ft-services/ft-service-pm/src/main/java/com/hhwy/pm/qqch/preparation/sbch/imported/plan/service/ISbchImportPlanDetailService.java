package com.hhwy.pm.qqch.preparation.sbch.imported.plan.service;


import com.hhwy.pm.qqch.preparation.sbch.imported.plan.domain.SbchImportPlanDetail;

import java.util.List;
import java.util.Map;

/**
 * 进口方案详情Service接口
 * 
 * @author zq
 * @date 2022-12-06
 */
public interface ISbchImportPlanDetailService {
    /**
     * 查询进口方案详情
     * 
     * @param id 进口方案详情ID
     * @return 进口方案详情
     */
    SbchImportPlanDetail selectSbchImportPlanDetailById(Long id);

    /**
     * 查询进口方案详情列表
     * 
     * @param sbchImportPlanDetail 进口方案详情
     * @return 进口方案详情集合
     */
    List<SbchImportPlanDetail> selectSbchImportPlanDetailList(SbchImportPlanDetail sbchImportPlanDetail);

    /**
     * 新增进口方案详情
     * 
     * @param sbchImportPlanDetail 进口方案详情
     * @return 结果
     */
    int insertSbchImportPlanDetail(SbchImportPlanDetail sbchImportPlanDetail);

    /**
     * 修改进口方案详情
     * 
     * @param sbchImportPlanDetail 进口方案详情
     * @return 结果
     */
    int updateSbchImportPlanDetail(SbchImportPlanDetail sbchImportPlanDetail);

    /**
     * 批量删除进口方案详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchImportPlanDetailByIds(String ids);

    /**
     * 删除进口方案详情信息
     * 
     * @param id 进口方案详情ID
     * @return 结果
     */
    int deleteSbchImportPlanDetailById(Long id);

    void batchInsert(List<SbchImportPlanDetail> detailList);

    List<Map<String,Object>> getSbchImportPlanDetailList(SbchImportPlanDetail sbchImportPlanDetail);

    void deleteSbchImportPlanDetailByPlanId(Long id);
}
