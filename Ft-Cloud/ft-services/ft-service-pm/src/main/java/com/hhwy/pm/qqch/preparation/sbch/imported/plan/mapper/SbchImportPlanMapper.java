package com.hhwy.pm.qqch.preparation.sbch.imported.plan.mapper;

import com.hhwy.pm.qqch.preparation.sbch.imported.plan.domain.SbchImportPlan;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 进口方案Mapper接口
 * 
 * @author zq
 * @date 2022-12-06
 */
public interface SbchImportPlanMapper {
    /**
     * 查询进口方案
     * 
     * @param id 进口方案ID
     * @return 进口方案
     */
    SbchImportPlan selectSbchImportPlanById(Long id);

    /**
     * 查询进口方案列表
     * 
     * @param sbchImportPlan 进口方案
     * @return 进口方案集合
     */
    List<SbchImportPlan> selectSbchImportPlanList(SbchImportPlan sbchImportPlan);

    /**
     * 新增进口方案
     * 
     * @param sbchImportPlan 进口方案
     * @return 结果
     */
    int insertSbchImportPlan(SbchImportPlan sbchImportPlan);

    /**
     * 修改进口方案
     * 
     * @param sbchImportPlan 进口方案
     * @return 结果
     */
    int updateSbchImportPlan(SbchImportPlan sbchImportPlan);

    /**
     * 删除进口方案
     * 
     * @param id 进口方案ID
     * @return 结果
     */
    int deleteSbchImportPlanById(Long id);

    /**
     * 批量删除进口方案
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchImportPlanByIds(@Param("ids") String[] ids, @Param("delUser") Long userId, @Param("delTime") Date date);
}
