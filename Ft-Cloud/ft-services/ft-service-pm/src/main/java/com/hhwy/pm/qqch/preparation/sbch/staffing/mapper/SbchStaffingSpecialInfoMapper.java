package com.hhwy.pm.qqch.preparation.sbch.staffing.mapper;

import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingSpecialInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 设备人员配置-特种设备爱人员Mapper接口
 * 
 * @author zq
 * @date 2022-11-30
 */
public interface SbchStaffingSpecialInfoMapper {
    /**
     * 查询设备人员配置-特种设备爱人员
     * 
     * @param id 设备人员配置-特种设备爱人员ID
     * @return 设备人员配置-特种设备爱人员
     */
    SbchStaffingSpecialInfo selectSbchStaffingSpecialInfoById(Long id);

    /**
     * 查询设备人员配置-特种设备爱人员列表
     * 
     * @param sbchStaffingSpecialInfo 设备人员配置-特种设备爱人员
     * @return 设备人员配置-特种设备爱人员集合
     */
    List<SbchStaffingSpecialInfo> selectSbchStaffingSpecialInfoList(SbchStaffingSpecialInfo sbchStaffingSpecialInfo);

    /**
     * 新增设备人员配置-特种设备爱人员
     * 
     * @param sbchStaffingSpecialInfo 设备人员配置-特种设备爱人员
     * @return 结果
     */
    int insertSbchStaffingSpecialInfo(SbchStaffingSpecialInfo sbchStaffingSpecialInfo);

    /**
     * 修改设备人员配置-特种设备爱人员
     * 
     * @param sbchStaffingSpecialInfo 设备人员配置-特种设备爱人员
     * @return 结果
     */
    int updateSbchStaffingSpecialInfo(SbchStaffingSpecialInfo sbchStaffingSpecialInfo);

    /**
     * 删除设备人员配置-特种设备爱人员
     * 
     * @param id 设备人员配置-特种设备爱人员ID
     * @return 结果
     */
    int deleteSbchStaffingSpecialInfoById(Long id);

    /**
     * 批量删除设备人员配置-特种设备爱人员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchStaffingSpecialInfoByIds(@Param("ids") String[] ids, @Param("delUser") Long delUser, @Param("delTime") Date delTime);
}
