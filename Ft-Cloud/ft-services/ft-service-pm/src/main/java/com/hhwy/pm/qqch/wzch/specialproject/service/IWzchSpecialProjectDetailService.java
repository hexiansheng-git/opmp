package com.hhwy.pm.qqch.wzch.specialproject.service;

import com.hhwy.pm.qqch.wzch.specialproject.domain.WzchSpecialProjectDetail;

import java.util.List;


/**
 * 专项物资策划物资详情Service接口
 *
 * @author mls
 * @date 2022-12-11
 */
public interface IWzchSpecialProjectDetailService {
    /**
     * 查询专项物资策划物资详情
     *
     * @param id 专项物资策划物资详情ID
     * @return 专项物资策划物资详情
     */
    WzchSpecialProjectDetail selectWzchSpecialProjectDetailById(Long id);

    /**
     * 查询专项物资策划物资详情列表
     *
     * @param wzchSpecialProjectDetail 专项物资策划物资详情
     * @return 专项物资策划物资详情集合
     */
    List<WzchSpecialProjectDetail> selectWzchSpecialProjectDetailList(WzchSpecialProjectDetail wzchSpecialProjectDetail);

    /**
     * 新增专项物资策划物资详情
     *
     * @param wzchSpecialProjectDetail 专项物资策划物资详情
     * @return 结果
     */
    int insertWzchSpecialProjectDetail(WzchSpecialProjectDetail wzchSpecialProjectDetail);

    /**
     * 修改专项物资策划物资详情
     *
     * @param wzchSpecialProjectDetail 专项物资策划物资详情
     * @return 结果
     */
    int updateWzchSpecialProjectDetail(WzchSpecialProjectDetail wzchSpecialProjectDetail);

    /**
     * 批量删除专项物资策划物资详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSpecialProjectDetailByIds(String ids);

    /**
     * 删除专项物资策划物资详情信息
     *
     * @param id 专项物资策划物资详情ID
     * @return 结果
     */
    int deleteWzchSpecialProjectDetailById(Long id);

    /**
     * 批量新增或者更新
     *
     * @param detailList
     * @param specialProjectId
     * @return
     */
    int insertOrUpdateBatch(List<WzchSpecialProjectDetail> detailList, Long specialProjectId);

    /**
     * 根据项目id查询物资总需求物资详情
     *
     * @param dto
     * @return
     */
    List<WzchSpecialProjectDetail> getMtlDetailList(WzchSpecialProjectDetail dto);
}
