package com.hhwy.pm.qqch.wzch.source.service;


import com.hhwy.pm.qqch.wzch.source.domain.WzchSourceApproachYearCount;

import java.util.List;

/**
 * 来源策划物资详情年份数据,优先进场物资详情年份数据Service接口
 * 
 * @author mls
 * @date 2022-11-21
 */
public interface IWzchSourceApproachYearCountService {
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
     * 删除来源策划物资详情年份数据,优先进场物资详情年份数据信息
     * 
     * @param id 来源策划物资详情年份数据,优先进场物资详情年份数据ID
     * @return 结果
     */
    int deleteWzchSourceApproachYearCountById(Long id);
}
