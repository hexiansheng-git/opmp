package com.hhwy.pm.qqch.wzch.internaladjust.mapper;


import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchInternalAdjust;

import java.math.BigDecimal;
import java.util.List;

/**
 * 内部调剂材料策划Mapper接口
 * 
 * @author mls
 * @date 2022-11-17
 */
public interface WzchInternalAdjustMapper {
    /**
     * 查询内部调剂材料策划
     * 
     * @param id 内部调剂材料策划ID
     * @return 内部调剂材料策划
     */
    WzchInternalAdjust selectWzchInternalAdjustById(Long id);

    /**
     * 查询内部调剂材料策划列表
     * 
     * @param wzchInternalAdjust 内部调剂材料策划
     * @return 内部调剂材料策划集合
     */
    List<WzchInternalAdjust> selectWzchInternalAdjustList(WzchInternalAdjust wzchInternalAdjust);

    /**
     * 新增内部调剂材料策划
     * 
     * @param wzchInternalAdjust 内部调剂材料策划
     * @return 结果
     */
    int insertWzchInternalAdjust(WzchInternalAdjust wzchInternalAdjust);

    /**
     * 修改内部调剂材料策划
     * 
     * @param wzchInternalAdjust 内部调剂材料策划
     * @return 结果
     */
    int updateWzchInternalAdjust(WzchInternalAdjust wzchInternalAdjust);

    /**
     * 删除内部调剂材料策划
     * 
     * @param id 内部调剂材料策划ID
     * @return 结果
     */
    int deleteWzchInternalAdjustById(Long id);

    /**
     * 批量删除内部调剂材料策划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchInternalAdjustByIds(String[] ids);

    int deleteWzchInternalAdjustByVersion(BigDecimal version);

    int deleteWzchInternalAdjustDetailByVersion(BigDecimal version);

    int updateDetailValidStatus(String id);

    int updateValidStatus(String id);

    int deleteDirectByMasterId(Long id);
}
