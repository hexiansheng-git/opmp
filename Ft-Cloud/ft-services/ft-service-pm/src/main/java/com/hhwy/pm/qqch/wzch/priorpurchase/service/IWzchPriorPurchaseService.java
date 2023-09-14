package com.hhwy.pm.qqch.wzch.priorpurchase.service;

import com.hhwy.pm.qqch.wzch.priorpurchase.domain.WzchPriorPurchase;
import com.hhwy.pm.qqch.wzch.priorpurchase.dto.WzchPriorPurchaseDTO;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseSupplyDTO;

import java.util.List;
import java.util.Map;

/**
 * 优先进场物资设备采购策划Service接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface IWzchPriorPurchaseService {
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
     * 批量删除优先进场物资设备采购策划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPriorPurchaseByIds(String ids);

    /**
     * 删除优先进场物资设备采购策划信息
     *
     * @param id 优先进场物资设备采购策划ID
     * @return 结果
     */
    int deleteWzchPriorPurchaseById(Long id);


    /**
     * 新增 编辑 详情数据回显
     *
     * @param vo 参数
     * @return
     */
    public WzchPriorPurchaseDTO baseInfo(WzchPriorPurchaseDTO vo) ;
    

    /**
     * 更新是否有效状态
     *
     * @param busId
     * @return
     */
    int updateValidStatus(String busId);


    /**
     * 新增
     *
     * @param wzchPriorPurchaseDTO
     * @return
     */
    public Long insert(WzchPriorPurchaseDTO wzchPriorPurchaseDTO);

    /**
     * 编辑
     *
     * @param wzchPriorPurchaseDTO
     * @return
     */
    public Long edit(WzchPriorPurchaseDTO wzchPriorPurchaseDTO);
    
    public Long save(WzchPriorPurchaseDTO wzchPriorPurchaseDTO);

    /**
     * 调整
     *
     * @param wzchPriorPurchaseDTO
     * @return
     */
    public Long adjust(WzchPriorPurchaseDTO wzchPriorPurchaseDTO);
    
    public void sync(WzchPriorPurchaseDTO dto);

}
