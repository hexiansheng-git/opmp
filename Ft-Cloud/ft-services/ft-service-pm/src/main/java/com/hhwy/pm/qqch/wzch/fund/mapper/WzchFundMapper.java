package com.hhwy.pm.qqch.wzch.fund.mapper;

import com.hhwy.pm.qqch.wzch.fund.domain.WzchFund;
import java.util.List;

/**
 * 资金策划Mapper接口
 *
 * @author mls
 * @date 2022-12-08
 */
public interface WzchFundMapper {
    /**
     * 查询资金策划
     *
     * @param id 资金策划ID
     * @return 资金策划
     */
    WzchFund selectWzchFundById(Long id);

    /**
     * 查询资金策划列表
     *
     * @param wzchFund 资金策划
     * @return 资金策划集合
     */
    List<WzchFund> selectWzchFundList(WzchFund wzchFund);

    /**
     * 新增资金策划
     *
     * @param wzchFund 资金策划
     * @return 结果
     */
    int insertWzchFund(WzchFund wzchFund);

    /**
     * 修改资金策划
     *
     * @param wzchFund 资金策划
     * @return 结果
     */
    int updateWzchFund(WzchFund wzchFund);

    /**
     * 删除资金策划
     *
     * @param id 资金策划ID
     * @return 结果
     */
    int deleteWzchFundById(Long id);

    /**
     * 批量删除资金策划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchFundByIds(String[] ids);

    int deleteDetailsByFundId(String[] ids);

    int updateDetailValidStatus(String id);

    int updateValidStatus(String id);
}
