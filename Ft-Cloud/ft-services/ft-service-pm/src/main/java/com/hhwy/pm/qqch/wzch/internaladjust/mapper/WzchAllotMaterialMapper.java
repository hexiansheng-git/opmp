package com.hhwy.pm.qqch.wzch.internaladjust.mapper;


import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchAllotMaterial;

import java.util.List;

/**
 * 可调拨材料Mapper接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface WzchAllotMaterialMapper {
    /**
     * 查询可调拨材料
     *
     * @param id 可调拨材料ID
     * @return 可调拨材料
     */
    WzchAllotMaterial selectWzchAllotMaterialById(Long id);

    /**
     * 查询可调拨材料列表
     *
     * @param wzchAllotMaterial 可调拨材料
     * @return 可调拨材料集合
     */
    List<WzchAllotMaterial> selectWzchAllotMaterialList(WzchAllotMaterial wzchAllotMaterial);
    List<WzchAllotMaterial> selectWzchAllotMaterialByProjectId(WzchAllotMaterial wzchAllotMaterial);

    /**
     * 新增可调拨材料
     *
     * @param wzchAllotMaterial 可调拨材料
     * @return 结果
     */
    int insertWzchAllotMaterial(WzchAllotMaterial wzchAllotMaterial);

    /**
     * 修改可调拨材料
     *
     * @param wzchAllotMaterial 可调拨材料
     * @return 结果
     */
    int updateWzchAllotMaterial(WzchAllotMaterial wzchAllotMaterial);

    /**
     * 删除可调拨材料
     *
     * @param id 可调拨材料ID
     * @return 结果
     */
    int deleteWzchAllotMaterialById(Long id);

    /**
     * 批量删除可调拨材料
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchAllotMaterialByIds(String[] ids);


    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    int batchInsert(List<WzchAllotMaterial> list);
}
