package com.hhwy.system.mapper;

import com.hhwy.domain.base.system.material.CascaderMaterialCategoryVo;
import com.hhwy.domain.base.system.material.MaterialCategory;
import com.hhwy.domain.base.system.material.MaterialCategoryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 物料分类名称Mapper接口
 * 
 * @author lcf
 * @date 2022-10-21
 */
public interface MaterialCategoryMapper {

    @Select("select id,pid,category_code value,category_name label from t_material_category  where type='0' and `status`=0 and del_flag=0")
    List<CascaderMaterialCategoryVo> getAllCascaderMaterialCategorys();

    //根据材料获取分类定位 update: zxb 2023-04-24
    List<MaterialCategoryVo> getPosition(@Param("categoryId") Long categoryId, @Param("type") String type);

    List<MaterialCategoryVo> getTree(MaterialCategoryVo materialCategoryVo);

    int batchInsert(@Param("dataList") List<MaterialCategory> dataList);

    @Select("select id, category_code categoryCode, category_name categoryName from t_material_category where del_flag=0 and `level` = 4")
    List<Map<String, Object>> getAllCategory();
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
    int insertMaterialCategory(MaterialCategory materialCategory);

    /**
     * 修改物料分类名称
     * 
     * @param materialCategory 物料分类名称
     * @return 结果
     */
    int updateMaterialCategory(MaterialCategory materialCategory);

    /**
     * 删除物料分类名称
     * 
     * @param id 物料分类名称ID
     * @return 结果
     */
    int deleteMaterialCategoryById(String id);

    /**
     * 批量删除物料分类名称
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteMaterialCategoryByIds(String[] ids);


    /**
     * materialCategory查询
     *
     * @param materialCategory
     * @return
     */
    List<MaterialCategory> selectCategoryByPcode(MaterialCategory materialCategory);

    /**
     * 查总数
     *
     * @return
     */
    int selectCount();

    /**
     * 查询编码库一级分类类型
     *
     * @param materialCategory
     * @return
     */
    List<MaterialCategory> materialFistCategory(MaterialCategory materialCategory);

    /**
     * 批量查询
     *
     * @param categoryCodeList
     * @return
     */
    List<MaterialCategory> selectInfoByCategoryCodes(@Param(value = "categoryCodeList") List<String> categoryCodeList);

    /**
     * 批量修改
     *
     * @param dataList
     * @return
     */
    int batchUpdate(@Param(value = "dataList") List<MaterialCategory> dataList);

    int batchUpdatePath(List<MaterialCategory> list);
    /**
     * 批量查询
     *
     * @param idList
     * @return
     */
    List<MaterialCategory> selectBathByPath(@Param(value = "idList") List<Long> idList);

    /**
     * 根据code分类信息
     *
     * @param materialCodeList
     * @return
     */
    List<MaterialCategory> selectBathByCode(List<String> materialCodeList);
}
