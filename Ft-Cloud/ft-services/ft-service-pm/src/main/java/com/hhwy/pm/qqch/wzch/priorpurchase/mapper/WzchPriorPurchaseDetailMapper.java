package com.hhwy.pm.qqch.wzch.priorpurchase.mapper;

import com.hhwy.pm.qqch.wzch.priorpurchase.domain.WzchPriorPurchaseDetail;
import com.hhwy.pm.qqch.wzch.priorpurchase.dto.WzchPriorPurchaseDetailDTO;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseSupplyDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优先进场物资设备采购策划物资详情Mapper接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface WzchPriorPurchaseDetailMapper {
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
    // TODO 执行标准
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
     * 删除优先进场物资设备采购策划物资详情
     *
     * @param id 优先进场物资设备采购策划物资详情ID
     * @return 结果
     */
    int deleteWzchPriorPurchaseDetailById(Long id);

    /**
     * 批量删除优先进场物资设备采购策划物资详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPriorPurchaseDetailByIds(String[] ids);


    /**
     * 批量新增或者更新
     *
     * @param entities
     * @return
     */
    int insertOrUpdateBatch(@Param("entities") List<WzchPriorPurchaseDetail> entities);

    /**
     * 数据进行删除
     *
     * @param purchaseId
     * @return
     */
    int deleteByPurchaseId(@Param("purchaseId") Long purchaseId);

    /**
     * 根据项目获取优先进场物资台账物资详情信息
     *
     * @param detail
     * @return
     */
    // TODO 执行标准
    List<WzchPriorPurchaseDetailDTO> getMtlDetailList(WzchPriorPurchaseDetail detail);

    /**
     * 删除
     *
     * @param priorPurchaseIds
     * @return
     */
    int deleteByPriorPurchaseIds(@Param("priorPurchaseIds") String[] priorPurchaseIds);

    List<WzchPurchaseSupplyDetailDTO> selectPurchaseSource(@Param("materialCodeList") List<String> materialCodeList);
}
