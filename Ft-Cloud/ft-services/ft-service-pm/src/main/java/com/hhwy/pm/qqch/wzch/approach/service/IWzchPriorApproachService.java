package com.hhwy.pm.qqch.wzch.approach.service;

import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproach;
import com.hhwy.pm.qqch.wzch.approach.vo.WzchPriorApproachExportRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优先进场物资Service接口
 * 
 * @author mls
 * @date 2022-11-28
 */
public interface IWzchPriorApproachService {
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
     * 同步物资总需、来源策划 到 优先进场
     * @param version
     */
    void sync(BigDecimal version);

    /**
     * 批量删除优先进场物资
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchPriorApproachByIds(String ids);

    /**
     * 删除优先进场物资信息
     * 
     * @param id 优先进场物资ID
     * @return 结果
     */
    int deleteWzchPriorApproachById(Long id);

    /**
     * 导出
     * @param request
     * @return
     */
    List<WzchPriorApproach> exportData(WzchPriorApproachExportRequest request);

    /**
     * 删除
     * @param wzchPriorApproach
     * @return
     */
    boolean remove(WzchPriorApproach wzchPriorApproach);

    /**
     * 编辑
     * @param id
     * @return
     */
    WzchPriorApproach edit(Long id);

    WzchPriorApproach detail(WzchPriorApproach priorApproach);
}
