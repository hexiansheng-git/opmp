package com.hhwy.pm.qqch.wzch.fund.mapper;

import com.hhwy.pm.qqch.wzch.fund.domain.WzchFundDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资金策划详情Mapper接口
 *
 * @author mls
 * @date 2022-12-08
 */
public interface WzchFundDetailMapper {
    /**
     * 查询资金策划详情
     *
     * @param id 资金策划详情ID
     * @return 资金策划详情
     */
    WzchFundDetail selectWzchFundDetailById(Long id);

    /**
     * 查询资金策划详情列表
     *
     * @param wzchFundDetail 资金策划详情
     * @return 资金策划详情集合
     */
    List<WzchFundDetail> selectWzchFundDetailList(WzchFundDetail wzchFundDetail);

    /**
     * 新增资金策划详情
     *
     * @param wzchFundDetail 资金策划详情
     * @return 结果
     */
    int insertWzchFundDetail(WzchFundDetail wzchFundDetail);

    /**
     * 修改资金策划详情
     *
     * @param wzchFundDetail 资金策划详情
     * @return 结果
     */
    int updateWzchFundDetail(WzchFundDetail wzchFundDetail);

    /**
     * 删除资金策划详情
     *
     * @param id 资金策划详情ID
     * @return 结果
     */
    int deleteWzchFundDetailById(Long id);

    /**
     * 批量删除资金策划详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchFundDetailByIds(String[] ids);

    /**
     * 根据资金策划id删除数据
     *
     * @param fundId
     * @return
     */
    int deleteByFundId(@Param("fundId") Long fundId);

    /**
     * 批量更新 新增
     *
     * @param entities
     * @return
     */
    int insertOrUpdateBatch(@Param("entities") List<WzchFundDetail> entities);
}
