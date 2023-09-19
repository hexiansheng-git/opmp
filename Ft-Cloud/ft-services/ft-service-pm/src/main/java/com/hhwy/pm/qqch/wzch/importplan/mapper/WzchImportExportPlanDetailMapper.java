package com.hhwy.pm.qqch.wzch.importplan.mapper;


import com.hhwy.pm.qqch.wzch.importplan.domain.WzchImportExportPlanDetail;
import java.util.List;

/**
 * 进出口策划详情Mapper接口
 * 
 * @author mls
 * @date 2022-12-05
 */
public interface WzchImportExportPlanDetailMapper {
    /**
     * 查询进出口策划详情
     * 
     * @param id 进出口策划详情ID
     * @return 进出口策划详情
     */
    WzchImportExportPlanDetail selectWzchImportExportPlanDetailById(Long id);

    /**
     * 查询进出口策划详情列表
     * 
     * @param wzchImportExportPlanDetail 进出口策划详情
     * @return 进出口策划详情集合
     */
    List<WzchImportExportPlanDetail> selectWzchImportExportPlanDetailList(WzchImportExportPlanDetail wzchImportExportPlanDetail);

    /**
     * 新增进出口策划详情
     * 
     * @param wzchImportExportPlanDetail 进出口策划详情
     * @return 结果
     */
    int insertWzchImportExportPlanDetail(WzchImportExportPlanDetail wzchImportExportPlanDetail);

    /**
     * 修改进出口策划详情
     * 
     * @param wzchImportExportPlanDetail 进出口策划详情
     * @return 结果
     */
    int updateWzchImportExportPlanDetail(WzchImportExportPlanDetail wzchImportExportPlanDetail);

    /**
     * 删除进出口策划详情
     * 
     * @param id 进出口策划详情ID
     * @return 结果
     */
    int deleteWzchImportExportPlanDetailById(Long id);

    /**
     * 批量删除进出口策划详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchImportExportPlanDetailByIds(List<Long> ids);

    int batchInsert(List<WzchImportExportPlanDetail> wzchImportExportPlanDetailList);

    int deleteByPlanId(Long planId);

//    int updateValidByPlanId(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail);

}
