package com.hhwy.pm.qqch.preparation.sbch.staffing.mapper;


import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingSpecialDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 设备人员配置策划--特种设备人员详情Mapper接口
 * 
 * @author zq
 * @date 2022-11-30
 */
public interface SbchStaffingSpecialDetailMapper {
    /**
     * 查询设备人员配置策划--特种设备人员详情
     * 
     * @param id 设备人员配置策划--特种设备人员详情ID
     * @return 设备人员配置策划--特种设备人员详情
     */
    SbchStaffingSpecialDetail selectSbchStaffingSpecialDetailById(Long id);

    /**
     * 查询设备人员配置策划--特种设备人员详情列表
     * 
     * @param sbchStaffingSpecialDetail 设备人员配置策划--特种设备人员详情
     * @return 设备人员配置策划--特种设备人员详情集合
     */
    List<SbchStaffingSpecialDetail> selectSbchStaffingSpecialDetailList(SbchStaffingSpecialDetail sbchStaffingSpecialDetail);

    /**
     * 新增设备人员配置策划--特种设备人员详情
     * 
     * @param sbchStaffingSpecialDetail 设备人员配置策划--特种设备人员详情
     * @return 结果
     */
    int insertSbchStaffingSpecialDetail(SbchStaffingSpecialDetail sbchStaffingSpecialDetail);

    /**
     * 修改设备人员配置策划--特种设备人员详情
     * 
     * @param sbchStaffingSpecialDetail 设备人员配置策划--特种设备人员详情
     * @return 结果
     */
    int updateSbchStaffingSpecialDetail(SbchStaffingSpecialDetail sbchStaffingSpecialDetail);

    /**
     * 删除设备人员配置策划--特种设备人员详情
     * 
     * @param id 设备人员配置策划--特种设备人员详情ID
     * @return 结果
     */
    int deleteSbchStaffingSpecialDetailById(Long id);

    /**
     * 批量删除设备人员配置策划--特种设备人员详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchStaffingSpecialDetailByIds(String[] ids);

    void batchInsert(@Param("dataList") List<SbchStaffingSpecialDetail> detailList);

    void deleteSbchStaffingSpecialDetailByInfoId(@Param("infoId") Long id, @Param("delUser") Long delUser, @Param("delTime") Date delTime);
}
