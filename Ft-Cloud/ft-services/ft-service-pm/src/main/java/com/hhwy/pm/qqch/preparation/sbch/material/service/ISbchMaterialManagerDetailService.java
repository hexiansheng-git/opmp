package com.hhwy.pm.qqch.preparation.sbch.material.service;


import com.hhwy.pm.qqch.preparation.sbch.material.domain.SbchMaterialManagerDetail;

import java.util.List;

/**
 * 设备现场管理Service接口
 * 
 * @author zq
 * @date 2022-12-20
 */
public interface ISbchMaterialManagerDetailService {
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
     * 批量删除设备现场管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchMaterialManagerDetailByIds(String ids);

    /**
     * 删除设备现场管理信息
     * 
     * @param id 设备现场管理ID
     * @return 结果
     */
    int deleteSbchMaterialManagerDetailById(Long id);

    void batchInsert(List<SbchMaterialManagerDetail> detailList);

    List<SbchMaterialManagerDetail> selectValidDetailList(SbchMaterialManagerDetail sbchMaterialManagerDetail);

    void deleteSbchMaterialManagerDetailByInfoId(Long id);
}
