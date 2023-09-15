package com.hhwy.pm.qqch.wzch.revolverent.mapper;

import com.hhwy.pm.qqch.wzch.revolverent.domain.WzchRevolveRentDetail;
import com.hhwy.pm.qqch.wzch.revolverent.dto.WzchRevolveRentDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 周转材租赁策划物资详情Mapper接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface WzchRevolveRentDetailMapper {
    /**
     * 查询周转材租赁策划物资详情
     *
     * @param id 周转材租赁策划物资详情ID
     * @return 周转材租赁策划物资详情
     */
    WzchRevolveRentDetail selectWzchRevolveRentDetailById(Long id);

    /**
     * 查询周转材租赁策划物资详情列表
     *
     * @param wzchRevolveRentDetail 周转材租赁策划物资详情
     * @return 周转材租赁策划物资详情集合
     */
    // TODO 类型 执行标准
    List<WzchRevolveRentDetailDTO> selectWzchRevolveRentDetailList(WzchRevolveRentDetail wzchRevolveRentDetail);

    /**
     * 新增周转材租赁策划物资详情
     *
     * @param wzchRevolveRentDetail 周转材租赁策划物资详情
     * @return 结果
     */
    int insertWzchRevolveRentDetail(WzchRevolveRentDetail wzchRevolveRentDetail);

    /**
     * 修改周转材租赁策划物资详情
     *
     * @param wzchRevolveRentDetail 周转材租赁策划物资详情
     * @return 结果
     */
    int updateWzchRevolveRentDetail(WzchRevolveRentDetail wzchRevolveRentDetail);

    /**
     * 删除周转材租赁策划物资详情
     *
     * @param id 周转材租赁策划物资详情ID
     * @return 结果
     */
    int deleteWzchRevolveRentDetailById(Long id);

    /**
     * 批量删除周转材租赁策划物资详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchRevolveRentDetailByIds(String[] ids);

    /**
     * 根据项目id获取物资详情  来源策划中类型为周转材，来源为租赁的数据
     *
     * @param dto
     * @return
     */
    // TODO 物资类型记得改 执行标准
    List<WzchRevolveRentDetailDTO> getMtlDetailList(WzchRevolveRentDetailDTO dto);

    /**
     * 根据周转租赁id删除物资详情
     *
     * @param rentId
     * @return
     */
    int deleteByRentId(@Param("rentId") Long rentId);

    /**
     * 批量新增或者编辑
     *
     * @param entities
     * @return
     */
    int insertOrUpdateBatch(@Param("entities") List<WzchRevolveRentDetail> entities);

    int deleteByRentIds(@Param("rentIds") String[] rentIds);
}
