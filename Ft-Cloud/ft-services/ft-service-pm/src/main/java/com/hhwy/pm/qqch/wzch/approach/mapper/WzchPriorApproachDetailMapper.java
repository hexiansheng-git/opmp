package com.hhwy.pm.qqch.wzch.approach.mapper;

import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproachDetail;
import com.hhwy.pm.qqch.wzch.approach.vo.WzchPriorApproachDetailExportRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优先进场物资详情Mapper接口
 * 
 * @author mls
 * @date 2022-11-30
 */
public interface WzchPriorApproachDetailMapper {
    /**
     * 查询优先进场物资详情
     * 
     * @param id 优先进场物资详情ID
     * @return 优先进场物资详情
     */
    WzchPriorApproachDetail selectWzchPriorApproachDetailById(Long id);

    /**
     * 查询优先进场物资详情列表
     * 
     * @param wzchPriorApproachDetail 优先进场物资详情
     * @return 优先进场物资详情集合
     */
    List<WzchPriorApproachDetail> selectWzchPriorApproachDetailList(WzchPriorApproachDetail wzchPriorApproachDetail);

    /**
     * 新增优先进场物资详情
     * 
     * @param wzchPriorApproachDetail 优先进场物资详情
     * @return 结果
     */
    int insertWzchPriorApproachDetail(WzchPriorApproachDetail wzchPriorApproachDetail);

    /**
     * 修改优先进场物资详情
     * 
     * @param wzchPriorApproachDetail 优先进场物资详情
     * @return 结果
     */
    int updateWzchPriorApproachDetail(WzchPriorApproachDetail wzchPriorApproachDetail);

    /**
     * 删除优先进场物资详情
     * 
     * @param id 优先进场物资详情ID
     * @return 结果
     */
    int deleteWzchPriorApproachDetailById(Long id);

    /**
     * 批量删除优先进场物资详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPriorApproachDetailByIds(List<Long> ids);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(List<WzchPriorApproachDetail> list);

    /**
     * 查询通过优先进场物资ID和ID列表
     * @param request
     * @return
     */
    List<WzchPriorApproachDetail> selectByPriorApproachIdAndIdList(WzchPriorApproachDetailExportRequest request);

    int deleteByIds(List<Long> ids);

    int deleteDirectByVersion(BigDecimal version);
    int deleteYearDirectByVersion(BigDecimal version);
}
