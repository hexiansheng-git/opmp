package com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllot;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.dto.SbchEquipmentAllotDTO;
import com.hhwy.utils.common.CommonBaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 同国别设备Service接口
 *
 * @author hwj
 * @date 2022-11-25
 */
public interface ISbchEquipmentAllotService {

    /**
     * 查询同国别设备列表
     *
     * @param sbchEquipmentAllot 同国别设备
     * @return 同国别设备集合
     */
    List<SbchEquipmentAllot> selectSbchEquipmentAllotList(SbchEquipmentAllot sbchEquipmentAllot);

    /**
     * 新增同国别设备
     *
     * @param sbchEquipmentAllot 同国别设备
     * @return 结果
     */
    String insertSbchEquipmentAllotAndDetails(SbchEquipmentAllotDTO sbchEquipmentAllot);
}
