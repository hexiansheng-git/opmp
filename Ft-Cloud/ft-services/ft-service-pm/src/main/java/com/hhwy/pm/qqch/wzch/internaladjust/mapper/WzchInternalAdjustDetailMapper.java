package com.hhwy.pm.qqch.wzch.internaladjust.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchInternalAdjustDetail;
import org.apache.ibatis.annotations.Param;

/**
 * 内部调剂材料策划物资详情Mapper接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface WzchInternalAdjustDetailMapper {
    /**
     * 查询内部调剂材料策划物资详情
     *
     * @param id 内部调剂材料策划物资详情ID
     * @return 内部调剂材料策划物资详情
     */
    WzchInternalAdjustDetail selectWzchInternalAdjustDetailById(Long id);

    BigDecimal selectWzchSourceVersion(BigDecimal version);

    /**
     * 查询内部调剂材料策划物资详情列表
     *
     * @param wzchInternalAdjustDetail 内部调剂材料策划物资详情
     * @return 内部调剂材料策划物资详情集合
     */
    List<WzchInternalAdjustDetail> selectWzchInternalAdjustDetailList(WzchInternalAdjustDetail wzchInternalAdjustDetail);

    /**
     * 新增内部调剂材料策划物资详情
     *
     * @param wzchInternalAdjustDetail 内部调剂材料策划物资详情
     * @return 结果
     */
    int insertWzchInternalAdjustDetail(WzchInternalAdjustDetail wzchInternalAdjustDetail);

    /**
     * 修改内部调剂材料策划物资详情
     *
     * @param wzchInternalAdjustDetail 内部调剂材料策划物资详情
     * @return 结果
     */
    int updateWzchInternalAdjustDetail(WzchInternalAdjustDetail wzchInternalAdjustDetail);

    /**
     * 删除内部调剂材料策划物资详情
     *
     * @param id 内部调剂材料策划物资详情ID
     * @return 结果
     */
    int deleteWzchInternalAdjustDetailById(Long id);

    /**
     * 批量删除内部调剂材料策划物资详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchInternalAdjustDetailByIds(String[] ids);

    /**
     * 根据内部调拨id删除子数据
     *
     * @param adjustId
     * @return
     */
    int deleteByAdjustId(@Param("adjustId") Long adjustId);

    /**
     * 批量新增或者编辑
     *
     * @param entities
     * @return
     */
    int insertOrUpdateBatch(@Param("entities") List<WzchInternalAdjustDetail> entities);

    /**
     * 根据项目id 查询物资详情
     *
     * @param dto
     * @return
     */
    List<WzchInternalAdjustDetail> getMtlDetailList(WzchInternalAdjustDetail dto);

    /**
     * 删除
     *
     * @param adjustIds
     * @return
     */
    int deleteByAdjustIds(@Param("adjustIds") String[] adjustIds);
}
