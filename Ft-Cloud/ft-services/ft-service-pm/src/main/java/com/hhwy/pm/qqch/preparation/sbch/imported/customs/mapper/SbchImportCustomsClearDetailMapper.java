package com.hhwy.pm.qqch.preparation.sbch.imported.customs.mapper;

import com.hhwy.pm.qqch.preparation.sbch.imported.customs.domain.SbchImportCustomsClearDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 清关档案策划详情Mapper接口
 * 
 * @author zq
 * @date 2022-12-14
 */
public interface SbchImportCustomsClearDetailMapper {
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
     * 删除清关档案策划详情
     * 
     * @param id 清关档案策划详情ID
     * @return 结果
     */
    int deleteSbchImportCustomsClearDetailById(Long id);

    /**
     * 批量删除清关档案策划详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchImportCustomsClearDetailByIds(String[] ids);

    void batchInsert(@Param("detailList") List<SbchImportCustomsClearDetail> detailList);

    List<SbchImportCustomsClearDetail> selectDetailValidList(SbchImportCustomsClearDetail sbchImportCustonsClearDetail);

    void deleteSbchImportCustomsClearDetailByInfoId(@Param("infoId") Long id, @Param("delUser") Long userId, @Param("delTime") Date dateTime);
}
