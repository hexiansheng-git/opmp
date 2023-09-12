package com.hhwy.pm.qqch.wzch.demand.service;

import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandTimeCount;

import java.math.BigDecimal;
import java.util.List;

/**
 * 物资总需用详情时间数据Service接口
 * 
 * @author mls
 * @date 2022-11-16
 */
public interface IWzchTotalDemandTimeCountService {
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
     * 批量删除物资总需用详情时间数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchTotalDemandTimeCountByIds(List<Long> ids);

    /**
     * 删除物资总需用详情时间数据信息
     * 
     * @param id 物资总需用详情时间数据ID
     * @return 结果
     */
    int deleteWzchTotalDemandTimeCountById(Long id);
    
    int deleteByVersion(BigDecimal version);
    /**
     * 查询通过详情id
     *
     * @param  totalDemandDetailIds
     * @return 结果
     */
    List<WzchTotalDemandTimeCount> selectByTotalDemandDetailIds(List<Long> totalDemandDetailIds);

    /**
     * 查询
     * @param  totalDemandDetailIds
     * @param viewType
     * @return 结果
     */
    List<WzchTotalDemandTimeCount> selectTotalDemandTimeCounts(List<Long> totalDemandDetailIds,String viewType);

    /**
     * 批量插入
     * @param wzchTotalDemandTimeCountList
     * @return
     */
    int batchInsert( List<WzchTotalDemandTimeCount> wzchTotalDemandTimeCountList);

    /**
     * 批次修改
     * @param fillWzchTotalDemandTimeCountList
     * @return
     */
    int batchUpdate(List<WzchTotalDemandTimeCount> fillWzchTotalDemandTimeCountList);

    /**
     * 删除
     * @param idArr
     */
     int deleteWzchTotalDemandTimeCountByintTotalDemandDetailId(String[] idArr);

    /**
     * 删除
     * @param detailIds
     * @return
     */
    int deleteByTotalDemandDetailIds(List<Long> detailIds);

    List<WzchTotalDemandTimeCount> selectByTotalDemandDetailIdsAndYears(List<Long> detailIdList, List<String> countYear);

    List<WzchTotalDemandTimeCount> selectByTotalDemandDetailIdsOfTotal(List<Long> detailIds);
}
