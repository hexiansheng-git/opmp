package com.hhwy.pm.qqch.preparation.sbch.material.service;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.pm.qqch.preparation.sbch.material.domain.SbchMaterialManager;

import java.math.BigDecimal;
import java.util.List;

/**
 * 设备现场管理Service接口
 * 
 * @author zq
 * @date 2022-12-20
 */
public interface ISbchMaterialManagerService {
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
     * 批量删除设备现场管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchMaterialManagerByIds(String ids);

    /**
     * 删除设备现场管理信息
     * 
     * @param id 设备现场管理ID
     * @return 结果
     */
    int deleteSbchMaterialManagerById(Long id);

    List<JSONObject> getTempleteList();

    SbchMaterialManager getList(BigDecimal version);

    void batchSave(SbchMaterialManager sbchMaterialManager);
}
