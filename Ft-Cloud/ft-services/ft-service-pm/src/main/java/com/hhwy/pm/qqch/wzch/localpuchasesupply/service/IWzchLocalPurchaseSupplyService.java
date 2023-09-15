package com.hhwy.pm.qqch.wzch.localpuchasesupply.service;


import com.hhwy.pm.qqch.wzch.localpuchasesupply.domain.WzchLocalPurchaseSupply;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.dto.WzchLocalPurchaseSupplyDTO;
import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupply;
import com.hhwy.utils.common.CommonBaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 采购供应策划Service接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface IWzchLocalPurchaseSupplyService {
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
     * 批量删除采购供应策划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPurchaseSupplyByIds(String ids);

    /**
     * 删除采购供应策划信息
     *
     * @param id 采购供应策划ID
     * @return 结果
     */
    int deleteWzchPurchaseSupplyById(Long id);

    /**
     * 新增 编辑 详情数据回显
     *
     * @param dto
     * @return
     */
    public WzchLocalPurchaseSupplyDTO baseInfo(WzchLocalPurchaseSupplyDTO dto);

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    Long insert(WzchLocalPurchaseSupplyDTO dto);

    /**
     * 编辑
     *
     * @param dto
     * @return
     */
    Long edit(WzchLocalPurchaseSupplyDTO dto);

    /**
     * 保存
     * @param dto
     * @return
     */
    Long save(WzchLocalPurchaseSupplyDTO dto);

    /**
     * 调整
     *
     * @param dto
     * @return
     */
    Long adjust(WzchLocalPurchaseSupplyDTO dto);

    public void sync(WzchLocalPurchaseSupply purchaseSupply);

    int updateValidStatus(String businessId);
}
