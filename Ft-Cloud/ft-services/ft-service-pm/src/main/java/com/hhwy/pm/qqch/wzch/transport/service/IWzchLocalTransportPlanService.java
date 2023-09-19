package com.hhwy.pm.qqch.wzch.transport.service;

import com.hhwy.pm.qqch.wzch.transport.domain.WzchLocalTransportPlan;
import java.util.List;

/**
 * 当地运输方案策划Service接口
 * 
 * @author mls
 * @date 2022-12-06
 */
public interface IWzchLocalTransportPlanService {
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
     * 批量删除当地运输方案策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchLocalTransportPlanByIds(String ids);

    /**
     * 删除当地运输方案策划信息
     * 
     * @param id 当地运输方案策划ID
     * @return 结果
     */
    int deleteWzchLocalTransportPlanById(Long id);

    /**
     * 编辑
     * @param wzchLocalTransportPlan
     * @return
     */
    WzchLocalTransportPlan edit(WzchLocalTransportPlan wzchLocalTransportPlan);

    /**
     * 删除
     * @param wzchLocalTransportPlan
     * @return
     */
    boolean remove(WzchLocalTransportPlan wzchLocalTransportPlan);

    /**
     * 调整
     * @param wzchLocalTransportPlan
     * @return
     */
    WzchLocalTransportPlan modify(WzchLocalTransportPlan wzchLocalTransportPlan);

    void processStatus(WzchLocalTransportPlan wzchLocalTransportPlan);
}
