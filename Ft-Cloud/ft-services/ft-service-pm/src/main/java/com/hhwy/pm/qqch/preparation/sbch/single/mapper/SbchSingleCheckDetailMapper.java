package com.hhwy.pm.qqch.preparation.sbch.single.mapper;

import com.hhwy.pm.qqch.preparation.sbch.single.domain.SbchSingleCheckDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 单机核算策划Mapper接口
 * 
 * @author zq
 * @date 2022-12-22
 */
public interface SbchSingleCheckDetailMapper {
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
     * 删除单机核算策划
     * 
     * @param id 单机核算策划ID
     * @return 结果
     */
    int deleteSbchSingleCheckDetailById(Long id);

    /**
     * 批量删除单机核算策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSbchSingleCheckDetailByIds(String[] ids);

    void batchInsert(@Param("dataList") List<SbchSingleCheckDetail> detailList);

    void deleteSbchSingleCheckDetailByInfoId(@Param("infoId") Long id, @Param("delUser") Long userId, @Param("delTime") Date date);

    List<SbchSingleCheckDetail> validList(SbchSingleCheckDetail sbchSingleCheckDetail);
}
