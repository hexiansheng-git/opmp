package com.hhwy.pm.qqch.preparation.sbch.staffing.service;


import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingInfo;
import com.hhwy.utils.common.CommonBaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 设备人员配置策划Service接口
 * 
 * @author zq
 * @date 2022-11-28
 */
public interface ISbchStaffingInfoService {
    /**
     * 查询设备人员配置策划
     * 
     * @param id 设备人员配置策划ID
     * @return 设备人员配置策划
     */
    SbchStaffingInfo selectSbchStaffingInfoById(Long id);

    /**
     * 查询设备人员配置策划列表
     * 
     * @param sbchStaffingInfo 设备人员配置策划
     * @return 设备人员配置策划集合
     */
    List<SbchStaffingInfo> selectSbchStaffingInfoList(SbchStaffingInfo sbchStaffingInfo);

    /**
     * 新增设备人员配置策划
     * 
     * @param sbchStaffingInfo 设备人员配置策划
     * @return 结果
     */
    Long insertSbchStaffingInfo(SbchStaffingInfo sbchStaffingInfo);

    /**
     * 修改设备人员配置策划
     * 
     * @param sbchStaffingInfo 设备人员配置策划
     * @return 结果
     */
    int updateSbchStaffingInfo(SbchStaffingInfo sbchStaffingInfo);

    /**
     * 批量删除设备人员配置策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchStaffingInfoByIds(String ids);

    /**
     * 删除设备人员配置策划信息
     * 
     * @param id 设备人员配置策划ID
     * @return 结果
     */
    int deleteSbchStaffingInfoById(Long id);

    Long changeVersionSave(SbchStaffingInfo sbchStaffingInfo);

    SbchStaffingInfo getList(BigDecimal version);
}
