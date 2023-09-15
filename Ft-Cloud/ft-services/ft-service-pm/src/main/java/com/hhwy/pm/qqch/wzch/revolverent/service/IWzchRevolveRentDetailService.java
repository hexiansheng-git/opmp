package com.hhwy.pm.qqch.wzch.revolverent.service;


import com.hhwy.pm.qqch.wzch.revolverent.domain.WzchRevolveRentDetail;
import com.hhwy.pm.qqch.wzch.revolverent.dto.WzchRevolveRentDetailDTO;

import java.util.List;

/**
 * 周转材租赁策划物资详情Service接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface IWzchRevolveRentDetailService {
    /**
     * 查询周转材租赁策划物资详情
     *
     * @param id 周转材租赁策划物资详情ID
     * @return 周转材租赁策划物资详情
     */
    WzchRevolveRentDetail selectWzchRevolveRentDetailById(Long id);

    /**
     * 查询周转材租赁策划物资详情列表
     *
     * @param wzchRevolveRentDetail 周转材租赁策划物资详情
     * @return 周转材租赁策划物资详情集合
     */
    List<WzchRevolveRentDetailDTO> selectWzchRevolveRentDetailList(WzchRevolveRentDetail wzchRevolveRentDetail);

    /**
     * 新增周转材租赁策划物资详情
     *
     * @param wzchRevolveRentDetail 周转材租赁策划物资详情
     * @return 结果
     */
    int insertWzchRevolveRentDetail(WzchRevolveRentDetail wzchRevolveRentDetail);

    /**
     * 修改周转材租赁策划物资详情
     *
     * @param wzchRevolveRentDetail 周转材租赁策划物资详情
     * @return 结果
     */
    int updateWzchRevolveRentDetail(WzchRevolveRentDetail wzchRevolveRentDetail);

    /**
     * 批量删除周转材租赁策划物资详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchRevolveRentDetailByIds(String ids);

    /**
     * 删除周转材租赁策划物资详情信息
     *
     * @param id 周转材租赁策划物资详情ID
     * @return 结果
     */
    int deleteWzchRevolveRentDetailById(Long id);


    /**
     * 根据项目id获取物资详情
     *
     * @param dto
     * @return
     */
    List<WzchRevolveRentDetailDTO> getMtlDetailList(WzchRevolveRentDetailDTO dto);

    /**
     * 批量新增或者更新详情
     *
     * @param detailList
     * @param rentId
     * @return
     */
    int insertOrUpdateBatch(List<WzchRevolveRentDetailDTO> detailList, Long rentId);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    int deleteByRentIds(String ids);
}
