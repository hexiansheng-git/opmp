package com.hhwy.pm.qqch.preparation.sbch.imported.plan.mapper;

import com.hhwy.pm.qqch.preparation.sbch.imported.plan.domain.SbchImportPlanDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 进口方案详情Mapper接口
 * 
 * @author zq
 * @date 2022-12-06
 */
public interface SbchImportPlanDetailMapper {
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
     * 删除进口方案详情
     * 
     * @param id 进口方案详情ID
     * @return 结果
     */
    int deleteSbchImportPlanDetailById(Long id);

    /**
     * 批量删除进口方案详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchImportPlanDetailByIds(String[] ids);

    void batchInsert(@Param("dataList") List<SbchImportPlanDetail> detailList);

    List<SbchImportPlanDetail> getSbchImportPlanDetailList(SbchImportPlanDetail sbchImportPlanDetail);

    void deleteSbchImportPlanDetailByPlanId(@Param("planId") Long id, @Param("delUser") Long userId, @Param("delTime") Date date);
}
