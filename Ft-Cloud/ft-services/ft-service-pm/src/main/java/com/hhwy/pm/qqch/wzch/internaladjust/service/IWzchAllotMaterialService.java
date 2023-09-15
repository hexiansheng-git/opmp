package com.hhwy.pm.qqch.wzch.internaladjust.service;

import java.util.List;

import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchAllotMaterial;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchAllotMaterialRange;

/**
 * 可调拨材料Service接口
 *
 * @author mls
 * @date 2022-11-17
 */
public interface IWzchAllotMaterialService {
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
     * 批量删除可调拨材料
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteWzchAllotMaterialByIds(String ids);

    /**
     * 删除可调拨材料信息
     *
     * @param id 可调拨材料ID
     * @return 结果
     */
    int deleteWzchAllotMaterialById(Long id);

    /**
     * 保存数据
     *
     * @param wzchAllotMaterial
     * @return
     */
    long save(WzchAllotMaterial wzchAllotMaterial);


    /**
     * 导入数据
     *
     * @param wzchAllotMaterials
     * @param wzchAllotMaterial
     * @return
     */
    int importData(List<WzchAllotMaterial> wzchAllotMaterials, WzchAllotMaterial wzchAllotMaterial);

    /**
     * 可调拨计划表
     *
     * @param materialCode
     * @param projectId
     * @return
     */
    List<WzchAllotMaterial> adjustMtlList(String materialCode, String projectId);


    /**
     * 选中物资新增
     *
     * @param range
     * @param projectId
     * @param projectName
     * @param countryCodes
     * @param mtlInfoList
     * @return
     */
    long saveList(String range, String projectId, String projectName, List<String> countryCodes, List<WzchAllotMaterial> mtlInfoList);

    /**
     * 更改范围
     *
     * @param wzchAllotMaterial
     * @return
     */
    int changeRange(WzchAllotMaterial wzchAllotMaterial);

    /**
     * 查询范围
     * @param wzchAllotMaterial
     * @return
     */
    WzchAllotMaterialRange selectRange(WzchAllotMaterialRange wzchAllotMaterial);

}
