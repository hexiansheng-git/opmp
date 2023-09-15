package com.hhwy.pm.qqch.wzch.revolverent.mapper;


import com.hhwy.pm.qqch.wzch.revolverent.domain.WzchRevolveRent;

import java.util.List;

/**
 * 周转材租赁策划Mapper接口
 * 
 * @author mls
 * @date 2022-11-17
 */
public interface WzchRevolveRentMapper {
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
     * 删除周转材租赁策划
     * 
     * @param id 周转材租赁策划ID
     * @return 结果
     */
    int deleteWzchRevolveRentById(Long id);

    /**
     * 批量删除周转材租赁策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchRevolveRentByIds(String[] ids);

    int deleteDirectByMasterId(Long id);
}
