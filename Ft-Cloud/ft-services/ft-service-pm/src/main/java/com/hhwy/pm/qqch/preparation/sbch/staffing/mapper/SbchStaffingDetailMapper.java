package com.hhwy.pm.qqch.preparation.sbch.staffing.mapper;

import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 设备人员配置策划--设备人员详情Mapper接口
 * 
 * @author zq
 * @date 2022-11-28
 */
public interface SbchStaffingDetailMapper {
    /**
     * 查询设备人员配置策划--设备人员详情
     * 
     * @param id 设备人员配置策划--设备人员详情ID
     * @return 设备人员配置策划--设备人员详情
     */
    SbchStaffingDetail selectSbchStaffingDetailById(Long id);

    /**
     * 查询设备人员配置策划--设备人员详情列表
     * 
     * @param sbchStaffingDetail 设备人员配置策划--设备人员详情
     * @return 设备人员配置策划--设备人员详情集合
     */
    List<SbchStaffingDetail> selectSbchStaffingDetailList(SbchStaffingDetail sbchStaffingDetail);

    /**
     * 新增设备人员配置策划--设备人员详情
     * 
     * @param sbchStaffingDetail 设备人员配置策划--设备人员详情
     * @return 结果
     */
    int insertSbchStaffingDetail(SbchStaffingDetail sbchStaffingDetail);

    /**
     * 修改设备人员配置策划--设备人员详情
     * 
     * @param sbchStaffingDetail 设备人员配置策划--设备人员详情
     * @return 结果
     */
    int updateSbchStaffingDetail(SbchStaffingDetail sbchStaffingDetail);

    /**
     * 删除设备人员配置策划--设备人员详情
     * 
     * @param id 设备人员配置策划--设备人员详情ID
     * @return 结果
     */
    int deleteSbchStaffingDetailById(Long id);

    /**
     * 批量删除设备人员配置策划--设备人员详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchStaffingDetailByIds(String[] ids);

    void batchInsert(@Param("dataList") List<SbchStaffingDetail> sbchStaffingDetailList);

    void deleteSbchStaffingDetailByStaffingId(@Param("staffingId") Long id, @Param("delUser") Long userId, @Param("delTime") Date date);
}
