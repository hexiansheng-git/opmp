package com.hhwy.pm.qqch.preparation.sbch.imported.customs.service;


import com.hhwy.pm.qqch.preparation.sbch.imported.customs.domain.SbchImportCustomsClearDetail;

import java.util.List;

/**
 * 清关档案策划详情Service接口
 * 
 * @author zq
 * @date 2022-12-14
 */
public interface ISbchImportCustomsClearDetailService {
    /**
     * 查询清关档案策划详情
     * 
     * @param id 清关档案策划详情ID
     * @return 清关档案策划详情
     */
    SbchImportCustomsClearDetail selectSbchImportCustomsClearDetailById(Long id);

    /**
     * 查询清关档案策划详情列表
     * 
     * @param sbchImportCustomsClearDetail 清关档案策划详情
     * @return 清关档案策划详情集合
     */
    List<SbchImportCustomsClearDetail> selectSbchImportCustomsClearDetailList(SbchImportCustomsClearDetail sbchImportCustomsClearDetail);

    /**
     * 新增清关档案策划详情
     * 
     * @param sbchImportCustomsClearDetail 清关档案策划详情
     * @return 结果
     */
    int insertSbchImportCustomsClearDetail(SbchImportCustomsClearDetail sbchImportCustomsClearDetail);

    /**
     * 修改清关档案策划详情
     * 
     * @param sbchImportCustomsClearDetail 清关档案策划详情
     * @return 结果
     */
    int updateSbchImportCustomsClearDetail(SbchImportCustomsClearDetail sbchImportCustomsClearDetail);

    /**
     * 批量删除清关档案策划详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchImportCustomsClearDetailByIds(String ids);

    /**
     * 删除清关档案策划详情信息
     * 
     * @param id 清关档案策划详情ID
     * @return 结果
     */
    int deleteSbchImportCustomsClearDetailById(Long id);

    void batchInsert(List<SbchImportCustomsClearDetail> detailList);

    List<SbchImportCustomsClearDetail> selectDetailList(SbchImportCustomsClearDetail sbchImportCustonsClearDetail);

    void deleteSbchImportCustomsClearDetailByInfoId(Long id);
}
