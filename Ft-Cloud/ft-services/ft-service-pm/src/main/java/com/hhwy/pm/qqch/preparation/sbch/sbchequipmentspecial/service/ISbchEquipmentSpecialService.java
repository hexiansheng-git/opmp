package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain.SbchEquipmentSpecial;
import com.hhwy.utils.common.CommonBaseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 特种设备管理Service接口
 * 
 * @author hwj
 * @date 2022-12-05
 */
public interface ISbchEquipmentSpecialService {
//    /**
//     * 新增 编辑 详情数据回显
//     *
//     * @param map 参数
//     * @return
//     */
//    CommonBaseEntity baseInfo(Map<String, String> map);
    /**
     * 查询特种设备管理
     * 
     * @param id 特种设备管理ID
     * @return 特种设备管理
     */
    SbchEquipmentSpecial selectSbchEquipmentSpecialById(Long id);

    /**
     * 查询特种设备管理列表
     * 
     * @param sbchEquipmentSpecial 特种设备管理
     * @return 特种设备管理集合
     */
    List<SbchEquipmentSpecial> selectSbchEquipmentSpecialList(SbchEquipmentSpecial sbchEquipmentSpecial);

    /**
     * 新增特种设备管理
     * 
     * @param sbchEquipmentSpecial 特种设备管理
     * @return 结果
     */
    int insertSbchEquipmentSpecial(SbchEquipmentSpecial sbchEquipmentSpecial);

    /**
     * 修改特种设备管理
     * 
     * @param sbchEquipmentSpecial 特种设备管理
     * @return 结果
     */
    int updateSbchEquipmentSpecial(SbchEquipmentSpecial sbchEquipmentSpecial);

    /**
     * 批量删除特种设备管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialByIds(String ids);

    /**
     * 删除特种设备管理信息
     * 
     * @param id 特种设备管理ID
     * @return 结果
     */
    int deleteSbchEquipmentSpecialById(Long id);

    SbchEquipmentSpecial getList(BigDecimal version);

    AjaxResult batchSave(SbchEquipmentSpecial sbchEquipmentSpecial);
}
