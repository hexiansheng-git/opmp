package com.hhwy.pm.qqch.wzch.source.mapper;


import com.hhwy.pm.qqch.wzch.source.domain.WzchSource;

import java.math.BigDecimal;
import java.util.List;

/**
 * 来源策划Mapper接口
 * 
 * @author mls
 * @date 2022-11-21
 */
public interface WzchSourceMapper {
    /**
     * 查询来源策划
     * 
     * @param id 来源策划ID
     * @return 来源策划
     */
    WzchSource selectWzchSourceById(Long id);

    /**
     * 查询来源策划列表
     * 
     * @param wzchSource 来源策划
     * @return 来源策划集合
     */
    List<WzchSource> selectWzchSourceList(WzchSource wzchSource);

    /**
     * 获取指定version 来源策划
     * @param version
     * @return
     */
    WzchSource selectWzchSourceByVersion(BigDecimal version);

    /**
     * 新增来源策划
     * 
     * @param wzchSource 来源策划
     * @return 结果
     */
    int insertWzchSource(WzchSource wzchSource);

    /**
     * 修改来源策划
     * 
     * @param wzchSource 来源策划
     * @return 结果
     */
    int updateWzchSource(WzchSource wzchSource);

    /**
     * 删除来源策划
     * 
     * @param id 来源策划ID
     * @return 结果
     */
    int deleteWzchSourceById(Long id);

    /**
     * 批量删除来源策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSourceByIds(String[] ids);

    /**
     * 获取项目ID和物资总需版本
     * @return
     */
    List<WzchSource> selectProjectIdAndDemandVersion();
}
