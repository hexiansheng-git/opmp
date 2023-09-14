package com.hhwy.pm.qqch.wzch.priorpurchase.service;


import com.hhwy.pm.qqch.wzch.priorpurchase.domain.WzchPriorPurchaseDetail;
import com.hhwy.pm.qqch.wzch.priorpurchase.dto.WzchPriorPurchaseDetailDTO;

import java.util.List;

/**
 * 优先进场物资设备采购策划物资详情Service接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface IWzchPriorPurchaseDetailService {
    /**
     * 查询优先进场物资设备采购策划物资详情
     *
     * @param id 优先进场物资设备采购策划物资详情ID
     * @return 优先进场物资设备采购策划物资详情
     */
    WzchPriorPurchaseDetail selectWzchPriorPurchaseDetailById(Long id);

    /**
     * 查询优先进场物资设备采购策划物资详情列表
     *
     * @param wzchPriorPurchaseDetail 优先进场物资设备采购策划物资详情
     * @return 优先进场物资设备采购策划物资详情集合
     */
    List<WzchPriorPurchaseDetailDTO> selectWzchPriorPurchaseDetailList(WzchPriorPurchaseDetail wzchPriorPurchaseDetail);

    /**
     * 新增优先进场物资设备采购策划物资详情
     *
     * @param wzchPriorPurchaseDetail 优先进场物资设备采购策划物资详情
     * @return 结果
     */
    int insertWzchPriorPurchaseDetail(WzchPriorPurchaseDetail wzchPriorPurchaseDetail);

    /**
     * 修改优先进场物资设备采购策划物资详情
     *
     * @param wzchPriorPurchaseDetail 优先进场物资设备采购策划物资详情
     * @return 结果
     */
    int updateWzchPriorPurchaseDetail(WzchPriorPurchaseDetail wzchPriorPurchaseDetail);

    /**
     * 批量删除优先进场物资设备采购策划物资详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPriorPurchaseDetailByIds(String ids);

    /**
     * 删除优先进场物资设备采购策划物资详情信息
     *
     * @param id 优先进场物资设备采购策划物资详情ID
     * @return 结果
     */
    int deleteWzchPriorPurchaseDetailById(Long id);

    /**
     * 新增物资明细
     *
     * @param detailList 物资明细
     * @param purchaseId 优先进场id
     * @return
     */
    int insertOrEditBatchByPurchaseId(List<WzchPriorPurchaseDetailDTO> detailList, Long purchaseId);

    /**
     * 根据项目获取优先进场物资台账物资详情信息
     *
     * @param projectId
     * @return
     */
    List<WzchPriorPurchaseDetailDTO> getMtlDetailList(WzchPriorPurchaseDetail projectId);

    /**
     * 根据单据id删除数据
     *
     * @param ids
     * @return
     */
    int deleteByPriorPurchaseIds(String ids);
}
