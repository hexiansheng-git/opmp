package com.hhwy.pm.qqch.wzch.localpuchasesupply.service;

import com.hhwy.pm.qqch.wzch.localpuchasesupply.domain.WzchLocalPurchaseSupplyDetail;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.dto.WzchLocalPurchaseSupplyDetailDTO;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.dto.WzchLocalPurchaseViewDetailDTO;

import java.util.List;
import java.util.Map;

/**
 * 采购供应策划材料视角物资详情Service接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface IWzchLocalPurchaseSupplyDetailService {
    /**
     * 查询采购供应策划材料视角物资详情
     *
     * @param id 采购供应策划材料视角物资详情ID
     * @return 采购供应策划材料视角物资详情
     */
    WzchLocalPurchaseSupplyDetail selectWzchPurchaseSupplyDetailById(Long id);

    /**
     * 查询采购供应策划材料视角物资详情列表
     *
     * @param wzchPurchaseSupplyDetail 采购供应策划材料视角物资详情
     * @return 采购供应策划材料视角物资详情集合
     */
    List<WzchLocalPurchaseSupplyDetailDTO> selectWzchPurchaseSupplyDetailList(WzchLocalPurchaseSupplyDetail wzchPurchaseSupplyDetail);

    /**
     * 新增采购供应策划材料视角物资详情
     *
     * @param wzchPurchaseSupplyDetail 采购供应策划材料视角物资详情
     * @return 结果
     */
    int insertWzchPurchaseSupplyDetail(WzchLocalPurchaseSupplyDetail wzchPurchaseSupplyDetail);

    /**
     * 修改采购供应策划材料视角物资详情
     *
     * @param wzchPurchaseSupplyDetail 采购供应策划材料视角物资详情
     * @return 结果
     */
    int updateWzchPurchaseSupplyDetail(WzchLocalPurchaseSupplyDetail wzchPurchaseSupplyDetail);

    /**
     * 批量删除采购供应策划材料视角物资详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPurchaseSupplyDetailByIds(String ids);

    /**
     * 删除采购供应策划材料视角物资详情信息
     *
     * @param id 采购供应策划材料视角物资详情ID
     * @return 结果
     */
    int deleteWzchPurchaseSupplyDetailById(Long id);

    /**
     * 根据项目id获取物资详情 获取的是来源策划的数据  如果物资在有采购相关的数据 就拿取物资 如果没有 就不用拿取
     *
     * @param dto
     * @return
     */
    List<WzchLocalPurchaseSupplyDetailDTO> getListByPrjId(WzchLocalPurchaseSupplyDetailDTO dto);

    /**
     * 批量新增获取删除
     *
     * @param detailList
     * @param supplyId
     * @return
     */
    int insertOrUpdateBatch(List<WzchLocalPurchaseSupplyDetailDTO> detailList, Long supplyId,boolean ignoreBatch);

    /**
     * 根据采购供应策划id获取物资详情
     *
     * @param dto
     * @return
     */
    List<WzchLocalPurchaseSupplyDetailDTO> selectSupplyDetailListBySupplyId(WzchLocalPurchaseSupplyDetailDTO dto);

    /**
     * 将数据进行分级
     *
     * @param dtoList
     * @return
     */
    List<WzchLocalPurchaseSupplyDetailDTO> getLevelList(List<WzchLocalPurchaseSupplyDetailDTO> dtoList);

    /**
     * 采购视角接口
     *
     * @param map
     * @return
     */
    List<WzchLocalPurchaseViewDetailDTO> purchaseView(Map<String, String> map);

    /**
     * 保存采购视角
     *
     * @param list
     */
    void savePurchaseView(List<WzchLocalPurchaseViewDetailDTO> list, String projectId );

    /**
     * 根据单据id删除数据
     *
     * @param ids
     * @return
     */
    int deleteBySupplyIds(String ids);


    /**
     * 处理集合 给集合数据加序号 1   1.1   2    2.1
     *
     * @param detailList
     * @return
     */
    public List<WzchLocalPurchaseSupplyDetailDTO> dealList(List<WzchLocalPurchaseSupplyDetailDTO> detailList);


}
