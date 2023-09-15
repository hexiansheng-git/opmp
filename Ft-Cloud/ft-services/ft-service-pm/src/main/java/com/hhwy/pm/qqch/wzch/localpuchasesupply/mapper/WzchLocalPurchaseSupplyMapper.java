package com.hhwy.pm.qqch.wzch.localpuchasesupply.mapper;

import com.hhwy.pm.qqch.wzch.localpuchasesupply.domain.WzchLocalPurchaseSupply;

import java.util.List;


/**
 * 采购供应策划Mapper接口
 * 
 * @author mls
 * @date 2022-11-17
 */
public interface WzchLocalPurchaseSupplyMapper {
    /**
     * 查询采购供应策划
     * 
     * @param id 采购供应策划ID
     * @return 采购供应策划
     */
    WzchLocalPurchaseSupply selectWzchPurchaseSupplyById(Long id);

    /**
     * 查询采购供应策划列表
     * 
     * @param wzchPurchaseSupply 采购供应策划
     * @return 采购供应策划集合
     */
    List<WzchLocalPurchaseSupply> selectWzchPurchaseSupplyList(WzchLocalPurchaseSupply wzchPurchaseSupply);

    /**
     * 新增采购供应策划
     * 
     * @param wzchPurchaseSupply 采购供应策划
     * @return 结果
     */
    int insertWzchPurchaseSupply(WzchLocalPurchaseSupply wzchPurchaseSupply);

    /**
     * 修改采购供应策划
     * 
     * @param wzchPurchaseSupply 采购供应策划
     * @return 结果
     */
    int updateWzchPurchaseSupply(WzchLocalPurchaseSupply wzchPurchaseSupply);

    /**
     * 删除采购供应策划
     * 
     * @param id 采购供应策划ID
     * @return 结果
     */
    int deleteWzchPurchaseSupplyById(Long id);

    /**
     * 批量删除采购供应策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPurchaseSupplyByIds(String[] ids);

    int updateDetailValidStatus(String id);

    int updateValidStatus(String id);

    int updateBacthDetailValidStatus(String id);
    
    int deleteDirectByMasterId(Long id);
    int deleteBatchDirectByMasterId(Long id);
}
