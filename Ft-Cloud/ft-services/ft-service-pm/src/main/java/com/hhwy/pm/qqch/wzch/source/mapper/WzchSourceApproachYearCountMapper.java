package com.hhwy.pm.qqch.wzch.source.mapper;


import com.hhwy.pm.qqch.wzch.source.domain.WzchSourceApproachYearCount;
import java.util.List;

/**
 * 来源策划物资详情年份数据,优先进场物资详情年份数据Mapper接口
 * 
 * @author mls
 * @date 2022-11-21
 */
public interface WzchSourceApproachYearCountMapper {
    /**
     * 查询来源策划物资详情年份数据,优先进场物资详情年份数据
     * 
     * @param id 来源策划物资详情年份数据,优先进场物资详情年份数据ID
     * @return 来源策划物资详情年份数据,优先进场物资详情年份数据
     */
    WzchSourceApproachYearCount selectWzchSourceApproachYearCountById(Long id);

    /**
     * 查询来源策划物资详情年份数据,优先进场物资详情年份数据列表
     * 
     * @param wzchSourceApproachYearCount 来源策划物资详情年份数据,优先进场物资详情年份数据
     * @return 来源策划物资详情年份数据,优先进场物资详情年份数据集合
     */
    List<WzchSourceApproachYearCount> selectWzchSourceApproachYearCountList(WzchSourceApproachYearCount wzchSourceApproachYearCount);

    /**
     * 新增来源策划物资详情年份数据,优先进场物资详情年份数据
     * 
     * @param wzchSourceApproachYearCount 来源策划物资详情年份数据,优先进场物资详情年份数据
     * @return 结果
     */
    int insertWzchSourceApproachYearCount(WzchSourceApproachYearCount wzchSourceApproachYearCount);

    /**
     * 修改来源策划物资详情年份数据,优先进场物资详情年份数据
     * 
     * @param wzchSourceApproachYearCount 来源策划物资详情年份数据,优先进场物资详情年份数据
     * @return 结果
     */
    int updateWzchSourceApproachYearCount(WzchSourceApproachYearCount wzchSourceApproachYearCount);

    /**
     * 删除来源策划物资详情年份数据,优先进场物资详情年份数据
     * 
     * @param id 来源策划物资详情年份数据,优先进场物资详情年份数据ID
     * @return 结果
     */
    int deleteWzchSourceApproachYearCountById(Long id);

    /**
     * 批量删除来源策划物资详情年份数据,优先进场物资详情年份数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSourceApproachYearCountByIds(List<Long> ids);

    /**
     * 获取来源计划年季月数据
     * @param detailIds
     * @return
     */
    List<WzchSourceApproachYearCount> selectWzchSourceApproachYearCountByDetailIds(List<Long> detailIds);

    /**
     *  新增
     * @param list
     * @return
     */
    int batchInsert(List<WzchSourceApproachYearCount> list);

    /**
     * 批次修改
     * @param dataList
     * @return
     */
    int batchUpdate(List<WzchSourceApproachYearCount> dataList);

    int deleteWzchSourceApproachYearCountByDetailIds(List<Long> detailIds);
}
