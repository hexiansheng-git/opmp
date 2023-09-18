package com.hhwy.pm.qqch.wzch.transport.mapper;


import com.hhwy.pm.qqch.wzch.transport.domain.WzchLocalTransportPlan;

import java.util.List;

/**
 * 当地运输方案策划Mapper接口
 * 
 * @author mls
 * @date 2022-12-06
 */
public interface WzchLocalTransportPlanMapper {
    /**
     * 查询当地运输方案策划
     * 
     * @param id 当地运输方案策划ID
     * @return 当地运输方案策划
     */
    WzchLocalTransportPlan selectWzchLocalTransportPlanById(Long id);

    /**
     * 查询当地运输方案策划列表
     * 
     * @param wzchLocalTransportPlan 当地运输方案策划
     * @return 当地运输方案策划集合
     */
    List<WzchLocalTransportPlan> selectWzchLocalTransportPlanList(WzchLocalTransportPlan wzchLocalTransportPlan);

    /**
     * 新增当地运输方案策划
     * 
     * @param wzchLocalTransportPlan 当地运输方案策划
     * @return 结果
     */
    int insertWzchLocalTransportPlan(WzchLocalTransportPlan wzchLocalTransportPlan);

    /**
     * 修改当地运输方案策划
     * 
     * @param wzchLocalTransportPlan 当地运输方案策划
     * @return 结果
     */
    int updateWzchLocalTransportPlan(WzchLocalTransportPlan wzchLocalTransportPlan);

    /**
     * 删除当地运输方案策划
     * 
     * @param id 当地运输方案策划ID
     * @return 结果
     */
    int deleteWzchLocalTransportPlanById(Long id);

    /**
     * 批量删除当地运输方案策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchLocalTransportPlanByIds(String[] ids);
}
