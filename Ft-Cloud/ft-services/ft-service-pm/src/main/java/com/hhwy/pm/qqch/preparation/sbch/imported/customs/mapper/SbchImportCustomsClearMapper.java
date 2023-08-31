package com.hhwy.pm.qqch.preparation.sbch.imported.customs.mapper;


import com.hhwy.pm.qqch.preparation.sbch.imported.customs.domain.SbchImportCustomsClear;

import java.util.List;

/**
 * 清关档案策划Mapper接口
 * 
 * @author zq
 * @date 2022-12-14
 */
public interface SbchImportCustomsClearMapper {
    /**
     * 查询清关档案策划
     * 
     * @param id 清关档案策划ID
     * @return 清关档案策划
     */
    SbchImportCustomsClear selectSbchImportCustomsClearById(Long id);

    /**
     * 查询清关档案策划列表
     * 
     * @param sbchImportCustomsClear 清关档案策划
     * @return 清关档案策划集合
     */
    List<SbchImportCustomsClear> selectSbchImportCustomsClearList(SbchImportCustomsClear sbchImportCustomsClear);

    /**
     * 新增清关档案策划
     * 
     * @param sbchImportCustomsClear 清关档案策划
     * @return 结果
     */
    int insertSbchImportCustomsClear(SbchImportCustomsClear sbchImportCustomsClear);

    /**
     * 修改清关档案策划
     * 
     * @param sbchImportCustomsClear 清关档案策划
     * @return 结果
     */
    int updateSbchImportCustomsClear(SbchImportCustomsClear sbchImportCustomsClear);

    /**
     * 删除清关档案策划
     * 
     * @param id 清关档案策划ID
     * @return 结果
     */
    int deleteSbchImportCustomsClearById(Long id);

    /**
     * 批量删除清关档案策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchImportCustomsClearByIds(String[] ids);

    void updateInfoNotValid();
}
