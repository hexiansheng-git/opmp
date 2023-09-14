package com.hhwy.pm.qqch.wzch.priorpurchase.mapper;

import com.hhwy.pm.qqch.wzch.priorpurchase.domain.WzchPriorPurchase;

import java.util.List;

/**
 * 优先进场物资设备采购策划Mapper接口
 * 
 * @author mls
 * @date 2022-11-17
 */
public interface WzchPriorPurchaseMapper {
    /**
     * 查询优先进场物资设备采购策划
     * 
     * @param id 优先进场物资设备采购策划ID
     * @return 优先进场物资设备采购策划
     */
    WzchPriorPurchase selectWzchPriorPurchaseById(Long id);

    /**
     * 查询优先进场物资设备采购策划列表
     * 
     * @param wzchPriorPurchase 优先进场物资设备采购策划
     * @return 优先进场物资设备采购策划集合
     */
    List<WzchPriorPurchase> selectWzchPriorPurchaseList(WzchPriorPurchase wzchPriorPurchase);

    /**
     * 新增优先进场物资设备采购策划
     * 
     * @param wzchPriorPurchase 优先进场物资设备采购策划
     * @return 结果
     */
    int insertWzchPriorPurchase(WzchPriorPurchase wzchPriorPurchase);

    /**
     * 修改优先进场物资设备采购策划
     * 
     * @param wzchPriorPurchase 优先进场物资设备采购策划
     * @return 结果
     */
    int updateWzchPriorPurchase(WzchPriorPurchase wzchPriorPurchase);

    /**
     * 删除优先进场物资设备采购策划
     * 
     * @param id 优先进场物资设备采购策划ID
     * @return 结果
     */
    int deleteWzchPriorPurchaseById(Long id);

    /**
     * 批量删除优先进场物资设备采购策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPriorPurchaseByIds(String[] ids);

    int deleteDetailDirectById(Long id);


}
