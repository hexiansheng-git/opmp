package com.hhwy.pm.qqch.wzch.revolverent.service;

import com.hhwy.pm.qqch.wzch.revolverent.domain.WzchRevolveRent;
import com.hhwy.pm.qqch.wzch.revolverent.dto.WzchRevolveRentDTO;
import com.hhwy.utils.common.CommonBaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 周转材租赁策划Service接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface IWzchRevolveRentService {
    /**
     * 查询周转材租赁策划
     *
     * @param id 周转材租赁策划ID
     * @return 周转材租赁策划
     */
    WzchRevolveRent selectWzchRevolveRentById(Long id);

    /**
     * 查询周转材租赁策划列表
     *
     * @param wzchRevolveRent 周转材租赁策划
     * @return 周转材租赁策划集合
     */
    List<WzchRevolveRent> selectWzchRevolveRentList(WzchRevolveRent wzchRevolveRent);

    /**
     * 新增周转材租赁策划
     *
     * @param wzchRevolveRent 周转材租赁策划
     * @return 结果
     */
    int insertWzchRevolveRent(WzchRevolveRent wzchRevolveRent);

    /**
     * 修改周转材租赁策划
     *
     * @param wzchRevolveRent 周转材租赁策划
     * @return 结果
     */
    int updateWzchRevolveRent(WzchRevolveRent wzchRevolveRent);

    /**
     * 批量删除周转材租赁策划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchRevolveRentByIds(String ids);

    /**
     * 删除周转材租赁策划信息
     *
     * @param id 周转材租赁策划ID
     * @return 结果
     */
    int deleteWzchRevolveRentById(Long id);

    /**
     * 新增 编辑 详情数据回显
     *
     * @param dto
     * @return
     */
    WzchRevolveRentDTO baseInfo(WzchRevolveRentDTO dto);

    /**
     * 新增
     *
     * @param wzchRevolveRent
     * @return
     */
    long insert(WzchRevolveRentDTO wzchRevolveRent);

    /**
     * 编辑
     *
     * @param wzchRevolveRent
     * @return
     */
    long edit(WzchRevolveRentDTO wzchRevolveRent);

    /**
     * 调整
     *
     * @param wzchRevolveRent
     * @return
     */
    long adjust(WzchRevolveRentDTO wzchRevolveRent);

    long save(WzchRevolveRentDTO wzchRevolveRent);

    /**
     * 同步来源策划
     * @param wzchRevolveRent
     * @return
     */
    void sync(WzchRevolveRentDTO wzchRevolveRent);

    /**
     * @param busId
     * @return
     */
    int updateValidStatus( String busId);
}
