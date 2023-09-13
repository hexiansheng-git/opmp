package com.hhwy.pm.qqch.wzch.source.mapper;


import com.hhwy.pm.qqch.wzch.source.domain.WzchSourceDetail;
import com.hhwy.pm.qqch.wzch.source.vo.WzchSourceDetailExportRequest;

import java.util.List;

/**
 * 来源策划物资详情Mapper接口
 * 
 * @author mls
 * @date 2022-11-21
 */
public interface WzchSourceDetailMapper {
    /**
     * 查询来源策划物资详情
     * 
     * @param id 来源策划物资详情ID
     * @return 来源策划物资详情
     */
    WzchSourceDetail selectWzchSourceDetailById(Long id);

    /**
     * 查询来源策划物资详情列表
     * 
     * @param wzchSourceDetail 来源策划物资详情
     * @return 来源策划物资详情集合
     */
    List<WzchSourceDetail> selectWzchSourceDetailList(WzchSourceDetail wzchSourceDetail);

    /**
     * 新增来源策划物资详情
     * 
     * @param wzchSourceDetail 来源策划物资详情
     * @return 结果
     */
    int insertWzchSourceDetail(WzchSourceDetail wzchSourceDetail);

    /**
     * 修改来源策划物资详情
     * 
     * @param wzchSourceDetail 来源策划物资详情
     * @return 结果
     */
    int updateWzchSourceDetail(WzchSourceDetail wzchSourceDetail);

    /**
     * 删除来源策划物资详情
     * 
     * @param id 来源策划物资详情ID
     * @return 结果
     */
    int deleteWzchSourceDetailById(Long id);

    /**
     * 批量删除来源策划物资详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchSourceDetailByIds(List<Long> ids);

    /**
     * 获取来源策划详情 通过sourceId
     * @param sourceId
     * @return
     */
    List<WzchSourceDetail> selectWzchSourceDetailBySourceId(Long sourceId);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(List<WzchSourceDetail> list);

    /**
     * 批量修改
     * @param list
     * @return
     */
    int batchUpdate(List<WzchSourceDetail> list);


    /**
     * 通过来源策划ID和ID列表获取
     * @param reuqest
     * @return
     */
    List<WzchSourceDetail> selectBySourceIdAndIdList(WzchSourceDetailExportRequest reuqest);

    int deleteWzchSourceDetailBySourdeId(Long sourceId);

    List<WzchSourceDetail> selectByMaterialCodeAndMaterialStandard(List<WzchSourceDetail> removeWzchSourceDetail);
}
