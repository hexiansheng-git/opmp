package com.hhwy.pm.qqch.preparation.sbch.material.mapper;

import com.hhwy.pm.qqch.preparation.sbch.material.domain.SbchMaterialManagerDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 设备现场管理Mapper接口
 * 
 * @author zq
 * @date 2022-12-20
 */
public interface SbchMaterialManagerDetailMapper {
    /**
     * 查询设备现场管理
     * 
     * @param id 设备现场管理ID
     * @return 设备现场管理
     */
    SbchMaterialManagerDetail selectSbchMaterialManagerDetailById(Long id);

    /**
     * 查询设备现场管理列表
     * 
     * @param sbchMaterialManagerDetail 设备现场管理
     * @return 设备现场管理集合
     */
    List<SbchMaterialManagerDetail> selectSbchMaterialManagerDetailList(SbchMaterialManagerDetail sbchMaterialManagerDetail);

    /**
     * 新增设备现场管理
     * 
     * @param sbchMaterialManagerDetail 设备现场管理
     * @return 结果
     */
    int insertSbchMaterialManagerDetail(SbchMaterialManagerDetail sbchMaterialManagerDetail);

    /**
     * 修改设备现场管理
     * 
     * @param sbchMaterialManagerDetail 设备现场管理
     * @return 结果
     */
    int updateSbchMaterialManagerDetail(SbchMaterialManagerDetail sbchMaterialManagerDetail);

    /**
     * 删除设备现场管理
     * 
     * @param id 设备现场管理ID
     * @return 结果
     */
    int deleteSbchMaterialManagerDetailById(Long id);

    /**
     * 批量删除设备现场管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchMaterialManagerDetailByIds(String[] ids);

    void batchInsert(@Param("detailList") List<SbchMaterialManagerDetail> detailList);

    List<SbchMaterialManagerDetail> selectValidDetailList(SbchMaterialManagerDetail sbchMaterialManagerDetail);

    void deleteSbchMaterialManagerDetailByInfoId(@Param("infoId") Long id, @Param("delUser") Long userId, @Param("delTime") Date date);
}
