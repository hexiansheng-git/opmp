package com.hhwy.pm.qqch.preparation.sbch.single.mapper;

import com.hhwy.pm.qqch.preparation.sbch.single.domain.SbchSingleCheck;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 单机核算策划Mapper接口
 * 
 * @author zq
 * @date 2022-12-22
 */
public interface SbchSingleCheckMapper {
    /**
     * 查询单机核算策划
     * 
     * @param id 单机核算策划ID
     * @return 单机核算策划
     */
    SbchSingleCheck selectSbchSingleCheckById(Long id);

    /**
     * 查询单机核算策划列表
     * 
     * @param sbchSingleCheck 单机核算策划
     * @return 单机核算策划集合
     */
    List<SbchSingleCheck> selectSbchSingleCheckList(SbchSingleCheck sbchSingleCheck);

    /**
     * 新增单机核算策划
     * 
     * @param sbchSingleCheck 单机核算策划
     * @return 结果
     */
    int insertSbchSingleCheck(SbchSingleCheck sbchSingleCheck);

    /**
     * 修改单机核算策划
     * 
     * @param sbchSingleCheck 单机核算策划
     * @return 结果
     */
    int updateSbchSingleCheck(SbchSingleCheck sbchSingleCheck);

    /**
     * 删除单机核算策划
     * 
     * @param id 单机核算策划ID
     * @return 结果
     */
    int deleteSbchSingleCheckById(Long id);

    /**
     * 批量删除单机核算策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchSingleCheckByIds(@Param("ids") String[] ids, @Param("delUser") Long delUser, @Param("delTime") Date delTime);

    void updateInfoNotValid(@Param("projectId") Long projectId);
}
