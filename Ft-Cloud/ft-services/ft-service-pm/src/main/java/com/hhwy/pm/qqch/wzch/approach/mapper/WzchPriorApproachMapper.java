package com.hhwy.pm.qqch.wzch.approach.mapper;

import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproach;
import com.hhwy.pm.qqch.wzch.approach.vo.WzchPriorApproachExportRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优先进场物资Mapper接口
 * 
 * @author mls
 * @date 2022-11-28
 */
public interface WzchPriorApproachMapper {
    /**
     * 查询优先进场物资
     * 
     * @param id 优先进场物资ID
     * @return 优先进场物资
     */
    WzchPriorApproach selectWzchPriorApproachById(Long id);

    /**
     * 查询优先进场物资列表
     * 
     * @param wzchPriorApproach 优先进场物资
     * @return 优先进场物资集合
     */
    List<WzchPriorApproach> selectWzchPriorApproachList(WzchPriorApproach wzchPriorApproach);

    /**
     * 新增优先进场物资
     * 
     * @param wzchPriorApproach 优先进场物资
     * @return 结果
     */
    int insertWzchPriorApproach(WzchPriorApproach wzchPriorApproach);

    /**
     * 修改优先进场物资
     * 
     * @param wzchPriorApproach 优先进场物资
     * @return 结果
     */
    int updateWzchPriorApproach(WzchPriorApproach wzchPriorApproach);

    /**
     * 删除优先进场物资
     * 
     * @param id 优先进场物资ID
     * @return 结果
     */
    int deleteWzchPriorApproachById(Long id);

    /**
     * 批量删除优先进场物资
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPriorApproachByIds(String[] ids);

    int deleteDirectByVersion(BigDecimal version);

    List<WzchPriorApproach> selectByIdsAndTitleAndProjectNameAndRegionName(WzchPriorApproachExportRequest request);
}
