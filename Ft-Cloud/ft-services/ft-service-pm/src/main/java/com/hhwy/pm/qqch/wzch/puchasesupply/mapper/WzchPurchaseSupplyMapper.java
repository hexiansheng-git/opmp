package com.hhwy.pm.qqch.wzch.puchasesupply.mapper;


import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupply;
import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupplyDetail;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseSupplyDetailDTO;

import java.util.List;

/**
 * 采购供应策划Mapper接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface WzchPurchaseSupplyMapper {
    /**
     * 查询采购供应策划
     *
     * @param id 采购供应策划ID
     * @return 采购供应策划
     */
    WzchPurchaseSupply selectWzchPurchaseSupplyById(Long id);

    /**
     * 查询采购供应策划列表
     *
     * @param wzchPurchaseSupply 采购供应策划
     * @return 采购供应策划集合
     */
    List<WzchPurchaseSupply> selectWzchPurchaseSupplyList(WzchPurchaseSupply wzchPurchaseSupply);

    /**
     * 新增采购供应策划
     *
     * @param wzchPurchaseSupply 采购供应策划
     * @return 结果
     */
    int insertWzchPurchaseSupply(WzchPurchaseSupply wzchPurchaseSupply);

    /**
     * 修改采购供应策划
     *
     * @param wzchPurchaseSupply 采购供应策划
     * @return 结果
     */
    int updateWzchPurchaseSupply(WzchPurchaseSupply wzchPurchaseSupply);

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

    /**
     * 根据物资类型和拟采购时间查询物资信息
     *
     * @param dto
     * @return
     */
    List<WzchPurchaseSupplyDetail> getMaterialsByTypeAndPlanTime(WzchPurchaseSupplyDetailDTO dto);

    int deleteDirectByMasterId(Long id);
    int deleteBatchDirectByMasterId(Long id);
}


