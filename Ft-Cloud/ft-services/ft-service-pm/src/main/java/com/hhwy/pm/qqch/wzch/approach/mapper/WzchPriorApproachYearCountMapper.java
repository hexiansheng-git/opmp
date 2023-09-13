package com.hhwy.pm.qqch.wzch.approach.mapper;

import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproachYearCount;

import java.util.List;

/**
 * 优先进场物资详情年份数据Mapper接口
 * 
 * @author mls
 * @date 2022-11-30
 */
public interface WzchPriorApproachYearCountMapper {
    /**
     * 查询优先进场物资详情年份数据
     * 
     * @param id 优先进场物资详情年份数据ID
     * @return 优先进场物资详情年份数据
     */
    WzchPriorApproachYearCount selectWzchPriorApproachYearCountById(Long id);

    /**
     * 查询优先进场物资详情年份数据列表
     * 
     * @param wzchPriorApproachYearCount 优先进场物资详情年份数据
     * @return 优先进场物资详情年份数据集合
     */
    List<WzchPriorApproachYearCount> selectWzchPriorApproachYearCountList(WzchPriorApproachYearCount wzchPriorApproachYearCount);

    /**
     * 新增优先进场物资详情年份数据
     * 
     * @param wzchPriorApproachYearCount 优先进场物资详情年份数据
     * @return 结果
     */
    int insertWzchPriorApproachYearCount(WzchPriorApproachYearCount wzchPriorApproachYearCount);

    /**
     * 修改优先进场物资详情年份数据
     * 
     * @param wzchPriorApproachYearCount 优先进场物资详情年份数据
     * @return 结果
     */
    int updateWzchPriorApproachYearCount(WzchPriorApproachYearCount wzchPriorApproachYearCount);

    /**
     * 删除优先进场物资详情年份数据
     * 
     * @param id 优先进场物资详情年份数据ID
     * @return 结果
     */
    int deleteWzchPriorApproachYearCountById(Long id);

    /**
     * 批量删除优先进场物资详情年份数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPriorApproachYearCountByIds(String[] ids);

    /**
     * 通过详情ID列表获取
     * @param detailIds
     * @return
     */
    List<WzchPriorApproachYearCount> selectByDetailIds(List<Long> detailIds);

    /**
     * 删除
     * @param detailIds
     * @return
     */
    int deleteWzchPriorApproachYearCountByDetailIds(List<Long> detailIds);

    /**
     * 删除
     * @param detailIds
     * @return
     */
    int deleteByDetails(List<Long> detailIds);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(List<WzchPriorApproachYearCount> list);

    int deleteByIds(List<Long> ids);
}
