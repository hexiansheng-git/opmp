package com.hhwy.pm.qqch.preparation.sbch.staffing.service;


import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingDetail;

import java.util.List;

/**
 * 设备人员配置策划--设备人员详情Service接口
 * 
 * @author zq
 * @date 2022-11-28
 */
public interface ISbchStaffingDetailService {
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
     * 批量删除设备人员配置策划--设备人员详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchStaffingDetailByIds(String ids);

    /**
     * 删除设备人员配置策划--设备人员详情信息
     * 
     * @param id 设备人员配置策划--设备人员详情ID
     * @return 结果
     */
    int deleteSbchStaffingDetailById(Long id);

    void batchInsert(List<SbchStaffingDetail> sbchStaffingDetailList);

    void deleteSbchStaffingDetailByStaffingId(Long id);
}
