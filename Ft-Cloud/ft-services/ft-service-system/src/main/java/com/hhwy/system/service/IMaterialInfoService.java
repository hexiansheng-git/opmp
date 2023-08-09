package com.hhwy.system.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.domain.base.system.material.MaterialInfoVo;
import com.hhwy.system.vo.ImportMaterialInfo;


import java.util.ArrayList;
import java.util.List;

/**
 * 中交同步物资设备库Service接口
 * 
 * @author lcf
 * @date 2022-10-21
 */
public interface IMaterialInfoService {

    //分页获取数据
    List<MaterialInfoVo> getMaterialInfoPage(MaterialInfo materialInfo);
    //分页获取数据
    int getMaterialInfoPageCount(MaterialInfo materialInfo);


    /**
     * 查询中交同步物资设备库
     * 
     * @param id 中交同步物资设备库ID
     * @return 中交同步物资设备库
     */
    MaterialInfo selectMaterialInfoById(String id);

    /**
     * 查询中交同步物资设备库列表
     * 
     * @param materialInfo 中交同步物资设备库
     * @return 中交同步物资设备库集合
     */
    List<MaterialInfo> selectMaterialInfoList(MaterialInfo materialInfo);


    /**
     * 查询中交同步物资设备库列表
     *
     * @param materialInfo 中交同步物资设备库
     * @return 中交同步物资设备库集合
     */
    List<MaterialInfo> newSelectMaterialInfoList(MaterialInfo materialInfo);

    /**
     * 新增中交同步物资设备库
     * 
     * @param materialInfo 中交同步物资设备库
     * @return 结果
     */
    AjaxResult insertMaterialInfo(MaterialInfo materialInfo);

    /**
     * 修改中交同步物资设备库
     * 
     * @param materialInfo 中交同步物资设备库
     * @return 结果
     */
    AjaxResult updateMaterialInfo(MaterialInfo materialInfo);

    /**
     * 批量删除中交同步物资设备库
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteMaterialInfoByIds(String ids);

    /**
     * 删除中交同步物资设备库信息
     * 
     * @param id 中交同步物资设备库ID
     * @return 结果
     */
    int deleteMaterialInfoById(String id);

    /**
     * 查总数
     *
     * @return
     */
    int selectCount();

    /**
     * 最近选择
     *
     * @param materialInfo
     * @return
     */
    AjaxResult recentSelect(MaterialInfo materialInfo);

    /**
     * 查询最近选择
     *
     * @param materialInfo
     * @return
     */
    AjaxResult selectRecentInfo(MaterialInfo materialInfo);

    /**
     * 移除最近选择
     *
     * @param materialInfo
     * @return
     */
    AjaxResult removeRecentInfo(MaterialInfo materialInfo);

    List<MaterialInfo> selectMaterialInfoListByCodes(ArrayList<String> materialCodeList);

    /**
     * 导入
     *
     * @param importList
     */
    void importData(List<ImportMaterialInfo> importList, Integer type);
}
