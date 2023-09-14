package com.hhwy.pm.qqch.wzch.puchasesupply.mapper;

import java.util.List;
import java.util.Map;

import com.hhwy.pm.qqch.wzch.puchasesupply.domain.WzchPurchaseSupplyDetail;
import com.hhwy.pm.qqch.wzch.puchasesupply.dto.WzchPurchaseSupplyDetailDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 采购供应策划材料视角物资详情Mapper接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface WzchPurchaseSupplyDetailMapper {
    /**
     * 查询采购供应策划材料视角物资详情
     *
     * @param id 采购供应策划材料视角物资详情ID
     * @return 采购供应策划材料视角物资详情
     */
    WzchPurchaseSupplyDetail selectWzchPurchaseSupplyDetailById(Long id);

    /**
     * 查询采购供应策划材料视角物资详情列表
     *
     * @param wzchPurchaseSupplyDetail 采购供应策划材料视角物资详情
     * @return 采购供应策划材料视角物资详情集合
     */
    // TODO 类型 执行标准 非优先进场量
    List<WzchPurchaseSupplyDetailDTO> selectWzchPurchaseSupplyDetailList(WzchPurchaseSupplyDetail wzchPurchaseSupplyDetail);

    /**
     * 新增采购供应策划材料视角物资详情
     *
     * @param wzchPurchaseSupplyDetail 采购供应策划材料视角物资详情
     * @return 结果
     */
    int insertWzchPurchaseSupplyDetail(WzchPurchaseSupplyDetail wzchPurchaseSupplyDetail);

    /**
     * 修改采购供应策划材料视角物资详情
     *
     * @param wzchPurchaseSupplyDetail 采购供应策划材料视角物资详情
     * @return 结果
     */
    int updateWzchPurchaseSupplyDetail(WzchPurchaseSupplyDetail wzchPurchaseSupplyDetail);

    /**
     * 删除采购供应策划材料视角物资详情
     *
     * @param id 采购供应策划材料视角物资详情ID
     * @return 结果
     */
    int deleteWzchPurchaseSupplyDetailById(Long id);

    /**
     * 批量删除采购供应策划材料视角物资详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPurchaseSupplyDetailByIds(String[] ids);

    /**
     * 批量新增或者删除
     *
     * @param entities
     */
    int insertOrUpdateBatch(@Param("entities") List<WzchPurchaseSupplyDetailDTO> entities);

    /**
     * 批量插入物资批次详情
     *
     * @param entities
     * @return
     */
    int batchInsertOrUpdateBatchDetails(@Param("entities") List<WzchPurchaseSupplyDetailDTO> entities);

    /**
     * 根据采购供应id删除物资详情
     *
     * @param supplyId
     * @return
     */
    int deleteDetailBySupplyId(@Param("supplyId") Long supplyId);

    /**
     * 根据采购供应id删除物资批次详情
     *
     * @param supplyId
     * @return
     */
    int deleteBatchDetailBySupplyId(@Param("supplyId") Long supplyId);


    /**
     * 根据项目 id 查询物资信息
     *
     * @param dto
     * @return
     */
    // TODO 类型 执行标准
    List<WzchPurchaseSupplyDetailDTO> getListByPrjId(WzchPurchaseSupplyDetailDTO dto);
    List<WzchPurchaseSupplyDetailDTO> getLocalListByPrjId(WzchPurchaseSupplyDetailDTO dto);

    /**
     * 根据物资编码和项目id 查询物资采购来源
     *
     * @param materialCodeList
     * @param projectId
     * @return
     */
    List<WzchPurchaseSupplyDetailDTO> selectPurchaseSource(@Param("materialCodeList") List<String> materialCodeList);

    List<WzchPurchaseSupplyDetailDTO> selectPurchaseView(@Param("list") List<Map<String, Object>> list, @Param("projectId") String projectId, @Param("dataType")String dataType);


    /**
     * 根据单据id删除批次详情
     *
     * @param supplyIds
     * @return
     */
    int deleteBatchBySupplyIds(@Param("supplyIds") String[] supplyIds);

    /**
     * 根据单据id删除物资详情
     *
     * @param supplyIds
     * @return
     */
    int deleteBySupplyIds(@Param("supplyIds") String[] supplyIds);
}
