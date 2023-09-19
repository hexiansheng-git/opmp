package com.hhwy.pm.qqch.wzch.fund.service;


import com.hhwy.pm.qqch.wzch.fund.domain.WzchFund;
import com.hhwy.pm.qqch.wzch.fund.dto.WzchFundDTO;
import com.hhwy.utils.common.CommonBaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 资金策划Service接口
 *
 * @author mls
 * @date 2022-12-08
 */
public interface IWzchFundService {
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
     * 批量删除资金策划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchFundByIds(String ids);

    /**
     * 删除资金策划信息
     *
     * @param id 资金策划ID
     * @return 结果
     */
    int deleteWzchFundById(Long id);

    /**
     * 新增 编辑 详情数据回显
     *
     * @param dto
     * @return
     */
    WzchFundDTO baseInfo(WzchFundDTO dto);

    /**
     * 新增
     *
     * @param wzchFund
     * @return
     */
    long insert(WzchFundDTO wzchFund);

    /**
     * 编辑
     *
     * @param wzchFund
     * @return
     */
    long edit(WzchFundDTO wzchFund);

    /**
     * 调整
     *
     * @param wzchFund
     * @return
     */
    long adjust(WzchFundDTO wzchFund);

    long save(WzchFundDTO wzchFund);

    /**

     * @param busId
     * @return
     */
    int updateValidStatus(String busId);
}
