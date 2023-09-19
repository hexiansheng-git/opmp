package com.hhwy.pm.qqch.wzch.transport.mapper;


import com.hhwy.pm.qqch.wzch.transport.domain.WzchLocalTransportPlanDetail;

import java.util.List;

/**
 * 当地运输方案策划详情Mapper接口
 * 
 * @author mls
 * @date 2022-12-06
 */
public interface WzchLocalTransportPlanDetailMapper {
    /**
     * 查询当地运输方案策划详情
     * 
     * @param id 当地运输方案策划详情ID
     * @return 当地运输方案策划详情
     */
    WzchLocalTransportPlanDetail selectWzchLocalTransportPlanDetailById(Long id);

    /**
     * 查询当地运输方案策划详情列表
     * 
     * @param wzchLocalTransportPlanDetail 当地运输方案策划详情
     * @return 当地运输方案策划详情集合
     */
    List<WzchLocalTransportPlanDetail> selectWzchLocalTransportPlanDetailList(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail);

    /**
     * 新增当地运输方案策划详情
     * 
     * @param wzchLocalTransportPlanDetail 当地运输方案策划详情
     * @return 结果
     */
    int insertWzchLocalTransportPlanDetail(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail);

    /**
     * 修改当地运输方案策划详情
     * 
     * @param wzchLocalTransportPlanDetail 当地运输方案策划详情
     * @return 结果
     */
    int updateWzchLocalTransportPlanDetail(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail);

    /**
     * 删除当地运输方案策划详情
     * 
     * @param id 当地运输方案策划详情ID
     * @return 结果
     */
    int deleteWzchLocalTransportPlanDetailById(Long id);

    /**
     * 批量删除当地运输方案策划详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchLocalTransportPlanDetailByIds(List<Long> ids);

    int batchInsert(List<WzchLocalTransportPlanDetail> wzchLocalTransportPlanDetailList);

    int deleteByIds(List<Long> detailIds);

    int deleteByPlanId(Long planId);

    int updateValidByPlanId(WzchLocalTransportPlanDetail wzchLocalTransportPlanDetail);
}
