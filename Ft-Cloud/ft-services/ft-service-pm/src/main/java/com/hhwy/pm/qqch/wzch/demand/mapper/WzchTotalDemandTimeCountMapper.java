package com.hhwy.pm.qqch.wzch.demand.mapper;

import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandTimeCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物资总需用详情时间数据Mapper接口
 * 
 * @author mls
 * @date 2022-11-16
 */
public interface WzchTotalDemandTimeCountMapper {
    /**
     * 查询物资总需用详情时间数据
     * 
     * @param id 物资总需用详情时间数据ID
     * @return 物资总需用详情时间数据
     */
    WzchTotalDemandTimeCount selectWzchTotalDemandTimeCountById(Long id);

    /**
     * 查询物资总需用详情时间数据列表
     * 
     * @param wzchTotalDemandTimeCount 物资总需用详情时间数据
     * @return 物资总需用详情时间数据集合
     */
    List<WzchTotalDemandTimeCount> selectWzchTotalDemandTimeCountList(WzchTotalDemandTimeCount wzchTotalDemandTimeCount);

    /**
     * 新增物资总需用详情时间数据
     * 
     * @param wzchTotalDemandTimeCount 物资总需用详情时间数据
     * @return 结果
     */
    int insertWzchTotalDemandTimeCount(WzchTotalDemandTimeCount wzchTotalDemandTimeCount);

    /**
     * 修改物资总需用详情时间数据
     * 
     * @param wzchTotalDemandTimeCount 物资总需用详情时间数据
     * @return 结果
     */
    int updateWzchTotalDemandTimeCount(WzchTotalDemandTimeCount wzchTotalDemandTimeCount);

    /**
     * 删除物资总需用详情时间数据
     * 
     * @param id 物资总需用详情时间数据ID
     * @return 结果
     */
    int deleteWzchTotalDemandTimeCountById(Long id);

    /**
     * 批量删除物资总需用详情时间数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchTotalDemandTimeCountByIds(List<Long> ids);

    List<WzchTotalDemandTimeCount> selectByTotalDemandDetailIds(List<Long> list);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(List<WzchTotalDemandTimeCount> list);

    /**
     * 批量修改
     * @param list
     * @return
     */
    int batchUpdate(List<WzchTotalDemandTimeCount> list);

    /**
     * 查询
     * @param list
     * @param viewType
     * @return
     */
    List<WzchTotalDemandTimeCount> selectTotalDemandTimeCounts(@Param("list")List<Long> list,@Param("viewType") String viewType);

    /**
     * 删除
     * @param detailIds
     * @return
     */
    int deleteByTotalDemandDetailIds(List<Long> detailIds);

    List<WzchTotalDemandTimeCount> selectByTotalDemandDetailIdsAndYears(@Param("detailIdList") List<Long> detailIdList, @Param("countYears") List<String> countYears);

    List<WzchTotalDemandTimeCount> selectByTotalDemandDetailIdsOfTotal(List<Long> detailIds);
}
