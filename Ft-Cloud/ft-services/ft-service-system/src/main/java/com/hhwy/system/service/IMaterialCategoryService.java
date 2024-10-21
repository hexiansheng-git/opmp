package com.hhwy.system.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.material.MaterialCategory;
import com.hhwy.domain.base.system.material.MaterialCategoryVo;
import com.hhwy.domain.base.system.material.MaterialCategoryVo2;
import com.hhwy.domain.base.system.material.MaterialInfo;


import java.util.List;
import java.util.Map;

/**
 * 物料分类名称Service接口
 * 
 * @author lcf
 * @date 2022-10-21
 */
public interface IMaterialCategoryService {

    //根据材料获取分类定位 update: zxb 2023-04-24
    List<MaterialCategoryVo> getPosition(Long categoryId, String type);

    List<MaterialCategoryVo> getTree(MaterialCategoryVo materialCategoryVo);


    //初始化物资编码分类
    int initMaterialCategory();
    //初始化物资编码
    int initMaterial();
    /**
     * 查询物料分类名称
     * 
     * @param id 物料分类名称ID
     * @return 物料分类名称
     */
    MaterialCategory selectMaterialCategoryById(Long id);

    /**
     * 查询物料分类名称列表
     * 
     * @param materialCategory 物料分类名称
     * @return 物料分类名称集合
     */
    List<MaterialCategory> selectMaterialCategoryList(MaterialCategory materialCategory);


    /**
     * 查询物料分类名称列表
     *
     * @param materialCategory 物料分类名称
     * @return 物料分类名称集合
     */
    List<MaterialCategory> newSelectMaterialCategoryList(MaterialCategory materialCategory);

    /**
     * 新增物料分类名称
     * 
     * @param materialCategory 物料分类名称
     * @return 结果
     */
    AjaxResult insertMaterialCategory(MaterialCategory materialCategory);

    /**
     * 修改物料分类名称
     * 
     * @param materialCategory 物料分类名称
     * @return 结果
     */
    AjaxResult updateMaterialCategory(MaterialCategory materialCategory);

    /**
     * 批量删除物料分类名称
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    int deleteMaterialCategoryByIds(String id);

    /**
     * 删除物料分类名称信息
     * 
     * @param id 物料分类名称ID
     * @return 结果
     */
    int deleteMaterialCategoryById(String id);

    /**
     * 节点拖拽
     *
     * @param materialCategory
     * @return
     */
    AjaxResult updateNode(MaterialCategory materialCategory);

    /**
     * 查总数
     *
     * @return
     */
    int selectCount();

    /**
     * 获取分类编码的第一层级
     *
     * @param category
     * @return
     */
    List<MaterialCategory> materialFistCategory(MaterialCategory category);

    /**
     * 全物资编码类型树
     * @return
     */
    List<Map> categoryTreeList();

    /**
     * 根据categoryCode处理
     *
     * @param list
     * @return
     */
    List<MaterialInfo> handleLogicCategoryIds(List<MaterialInfo> list);

    /**
     * 根据名称模糊搜索（带层级）
     *
     * @param category
     * @return
     */
    List<MaterialCategory> selectMaterialCategoryByName(MaterialCategory category);

    /**
     * 重置物资分类的path 
     */
    void resetPath(MaterialCategory category);

    /**
     * 根据categoryCode查询层级信息  第三层级 第四层级
     *
     * @param list
     * @param type 0物资  1设备  2配件库
     * @return
     */
    List<Map> selectMaterialCategoryPath(List<String> list,Integer type);

    //材料分类名称模糊搜索
    List<MaterialCategoryVo2> getTreeListByCategoryName(MaterialCategoryVo materialCategoryVo);
}
