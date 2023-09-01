package com.hhwy.pm.qqch.preparation.sbch.single.service;


import com.hhwy.pm.qqch.preparation.sbch.single.domain.SbchSingleCheckDetail;

import java.util.List;

/**
 * 单机核算策划Service接口
 * 
 * @author zq
 * @date 2022-12-22
 */
public interface ISbchSingleCheckDetailService {
    /**
     * 查询单机核算策划
     * 
     * @param id 单机核算策划ID
     * @return 单机核算策划
     */
    SbchSingleCheckDetail selectSbchSingleCheckDetailById(Long id);

    /**
     * 查询单机核算策划列表
     * 
     * @param sbchSingleCheckDetail 单机核算策划
     * @return 单机核算策划集合
     */
    List<SbchSingleCheckDetail> selectSbchSingleCheckDetailList(SbchSingleCheckDetail sbchSingleCheckDetail);

    /**
     * 新增单机核算策划
     * 
     * @param sbchSingleCheckDetail 单机核算策划
     * @return 结果
     */
    int insertSbchSingleCheckDetail(SbchSingleCheckDetail sbchSingleCheckDetail);

    /**
     * 修改单机核算策划
     * 
     * @param sbchSingleCheckDetail 单机核算策划
     * @return 结果
     */
    int updateSbchSingleCheckDetail(SbchSingleCheckDetail sbchSingleCheckDetail);

    /**
     * 批量删除单机核算策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchSingleCheckDetailByIds(String ids);

    /**
     * 删除单机核算策划信息
     * 
     * @param id 单机核算策划ID
     * @return 结果
     */
    int deleteSbchSingleCheckDetailById(Long id);

    void batchInsert(List<SbchSingleCheckDetail> detailList);

    void deleteSbchSingleCheckDetailByInfoId(Long id);

    List<SbchSingleCheckDetail> validList(SbchSingleCheckDetail sbchSingleCheckDetail);
}
