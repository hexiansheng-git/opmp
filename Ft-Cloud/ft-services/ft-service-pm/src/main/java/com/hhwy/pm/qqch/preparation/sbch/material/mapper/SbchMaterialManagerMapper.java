package com.hhwy.pm.qqch.preparation.sbch.material.mapper;


import com.hhwy.pm.qqch.preparation.sbch.material.domain.SbchMaterialManager;

import java.util.List;

/**
 * 设备现场管理Mapper接口
 * 
 * @author zq
 * @date 2022-12-20
 */
public interface SbchMaterialManagerMapper {
    /**
     * 查询设备现场管理
     * 
     * @param id 设备现场管理ID
     * @return 设备现场管理
     */
    SbchMaterialManager selectSbchMaterialManagerById(Long id);

    /**
     * 查询设备现场管理列表
     * 
     * @param sbchMaterialManager 设备现场管理
     * @return 设备现场管理集合
     */
    List<SbchMaterialManager> selectSbchMaterialManagerList(SbchMaterialManager sbchMaterialManager);

    /**
     * 新增设备现场管理
     * 
     * @param sbchMaterialManager 设备现场管理
     * @return 结果
     */
    int insertSbchMaterialManager(SbchMaterialManager sbchMaterialManager);

    /**
     * 修改设备现场管理
     * 
     * @param sbchMaterialManager 设备现场管理
     * @return 结果
     */
    int updateSbchMaterialManager(SbchMaterialManager sbchMaterialManager);

    /**
     * 删除设备现场管理
     * 
     * @param id 设备现场管理ID
     * @return 结果
     */
    int deleteSbchMaterialManagerById(Long id);

    /**
     * 批量删除设备现场管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchMaterialManagerByIds(String[] ids);

    void updateInfoNotValid();
}
