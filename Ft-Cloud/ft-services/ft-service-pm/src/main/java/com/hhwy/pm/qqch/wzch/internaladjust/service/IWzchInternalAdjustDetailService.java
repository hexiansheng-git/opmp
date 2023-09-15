package com.hhwy.pm.qqch.wzch.internaladjust.service;


import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchInternalAdjustDetail;

import java.util.List;

/**
 * 内部调剂材料策划物资详情Service接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface IWzchInternalAdjustDetailService {
    /**
     * 查询内部调剂材料策划物资详情
     *
     * @param id 内部调剂材料策划物资详情ID
     * @return 内部调剂材料策划物资详情
     */
    WzchInternalAdjustDetail selectWzchInternalAdjustDetailById(Long id);

    /**
     * 查询内部调剂材料策划物资详情列表
     *
     * @param wzchInternalAdjustDetail 内部调剂材料策划物资详情
     * @return 内部调剂材料策划物资详情集合
     */
    List<WzchInternalAdjustDetail> selectWzchInternalAdjustDetailList(WzchInternalAdjustDetail wzchInternalAdjustDetail);

    /**
     * 新增内部调剂材料策划物资详情
     *
     * @param wzchInternalAdjustDetail 内部调剂材料策划物资详情
     * @return 结果
     */
    int insertWzchInternalAdjustDetail(WzchInternalAdjustDetail wzchInternalAdjustDetail);

    /**
     * 修改内部调剂材料策划物资详情
     *
     * @param wzchInternalAdjustDetail 内部调剂材料策划物资详情
     * @return 结果
     */
    int updateWzchInternalAdjustDetail(WzchInternalAdjustDetail wzchInternalAdjustDetail);

    /**
     * 批量删除内部调剂材料策划物资详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchInternalAdjustDetailByIds(String ids);

    /**
     * 删除内部调剂材料策划物资详情信息
     *
     * @param id 内部调剂材料策划物资详情ID
     * @return 结果
     */
    int deleteWzchInternalAdjustDetailById(Long id);

    /**
     * 获取物资详情
     *
     * @param dto
     * @return
     */
    List<WzchInternalAdjustDetail> getMtlDetailList(WzchInternalAdjustDetail dto);

    /**
     * 批量新增或者编辑
     *
     * @param detailList
     * @param adjustId
     * @param projectId
     * @return
     */
    int insertOrUpdateBatch(List<WzchInternalAdjustDetail> detailList, Long adjustId);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    int deleteByAdjustIds(String ids);
}
