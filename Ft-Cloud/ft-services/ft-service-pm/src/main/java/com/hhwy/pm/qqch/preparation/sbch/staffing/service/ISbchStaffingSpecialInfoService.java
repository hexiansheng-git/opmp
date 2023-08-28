package com.hhwy.pm.qqch.preparation.sbch.staffing.service;


import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingInfo;
import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingSpecialInfo;
import com.hhwy.utils.common.CommonBaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 设备人员配置-特种设备爱人员Service接口
 * 
 * @author zq
 * @date 2022-11-30
 */
public interface ISbchStaffingSpecialInfoService {
    /**
     * 查询设备人员配置-特种设备爱人员
     * 
     * @param id 设备人员配置-特种设备爱人员ID
     * @return 设备人员配置-特种设备爱人员
     */
    SbchStaffingSpecialInfo selectSbchStaffingSpecialInfoById(Long id);


    /**
     * 新增设备人员配置-特种设备爱人员
     * 
     * @param sbchStaffingSpecialInfo 设备人员配置-特种设备爱人员
     * @return 结果
     */
    Long insertSbchStaffingSpecialInfo(SbchStaffingSpecialInfo sbchStaffingSpecialInfo);

    /**
     * 修改设备人员配置-特种设备爱人员
     * 
     * @param sbchStaffingSpecialInfo 设备人员配置-特种设备爱人员
     * @return 结果
     */
    Long updateSbchStaffingSpecialInfo(SbchStaffingSpecialInfo sbchStaffingSpecialInfo);

    /**
     * 批量删除设备人员配置-特种设备爱人员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchStaffingSpecialInfoByIds(String ids);

    /**
     * 删除设备人员配置-特种设备爱人员信息
     * 
     * @param id 设备人员配置-特种设备爱人员ID
     * @return 结果
     */
    int deleteSbchStaffingSpecialInfoById(Long id);

    Long changeVersion(SbchStaffingSpecialInfo sbchStaffingSpecialInfo);

    SbchStaffingSpecialInfo getList(BigDecimal version);
}
