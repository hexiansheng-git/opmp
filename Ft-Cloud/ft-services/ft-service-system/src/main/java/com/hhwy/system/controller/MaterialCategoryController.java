package com.hhwy.system.controller;


import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;

import com.hhwy.domain.base.system.material.CascaderMaterialCategoryVo;
import com.hhwy.domain.base.system.material.MaterialCategory;
import com.hhwy.domain.base.system.material.MaterialCategoryVo;
import com.hhwy.system.mapper.MaterialCategoryMapper;
import com.hhwy.system.service.IMaterialCategoryService;
import com.hhwy.utils.common.PmsConstant;

import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 物料分类名称Controller
 * 
 * @author lcf
 * @date 2022-10-21
 */
@RestController
@RequestMapping("/material/category")
public class MaterialCategoryController extends BaseController {

    @Autowired
    private IMaterialCategoryService materialCategoryService;
    @Autowired
    private MaterialCategoryMapper materialCategoryMapper;

    /**
     * 查询物料分类名称列表
     */
//    @CustomLogger(title = "材料编码类型-根据层级懒加载",businessType = CustomBusinessType.SELECT)
    @PostMapping("/getTree")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody MaterialCategoryVo materialCategoryVo) {
        startPage(materialCategoryVo.getPageNum(),materialCategoryVo.getPageSize());
        Long pid = materialCategoryVo.getPid() == null ? 0l : materialCategoryVo.getPid();
        materialCategoryVo.setPid(pid);
        List<MaterialCategoryVo> list = materialCategoryService.getTree(materialCategoryVo);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    /**
     * 根据材料编码定位分类
     */
//    @CustomLogger(title = "根据材料编码定位分类",businessType = CustomBusinessType.SELECT)
    @GetMapping("/getPosition")
    public AjaxResult getPosition(Long categoryId, String type) {
        List<MaterialCategoryVo> list = materialCategoryService.getPosition(categoryId, type);
        return AjaxResult.success(list);
    }

    /**
     * 导出物料分类名称列表
     */
    @PostMapping("/export")
//    @CustomLogger(title = "材料编码类型-导出",businessType = CustomBusinessType.EXPORT)
    public void export(MaterialCategory materialCategory, HttpServletResponse response) throws Exception{
        List<MaterialCategory> list = materialCategoryService.selectMaterialCategoryList(materialCategory);
        ExcelUtils<MaterialCategory> util = new ExcelUtils<MaterialCategory>(MaterialCategory.class);
        util.exportExcel(response,list, "材料编码类型");
    }

    /**
     * 新增保存物料分类名称
     */
    @PreAuthorize(hasPermi = "material:category:add")
    @PostMapping("/add")
//    @CustomLogger(title = "材料编码类型-保存",businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody MaterialCategory materialCategory) {
        return materialCategoryService.insertMaterialCategory(materialCategory);
    }

    /**
     * 修改保存物料分类名称
     */
    @PreAuthorize(hasPermi = "material:category:edit")
    @PostMapping("/edit")
//    @CustomLogger(title = "材料编码类型-编辑",businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody MaterialCategory materialCategory) {
        return materialCategoryService.updateMaterialCategory(materialCategory);
    }

    /**
     * 删除物料分类名称
     */
    @PreAuthorize(hasPermi = "material:category:remove")
    @PostMapping( "/remove")
//    @CustomLogger(title = "材料编码类型-删除",businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(@Validated(ValidationGroups.Delete.class) @RequestBody MaterialCategory materialCategory) {
        Long id = materialCategory.getId();
        return toAjax(materialCategoryService.deleteMaterialCategoryByIds(String.valueOf(id)));
    }

    @PreAuthorize(hasPermi = "material:category:updateNode")
    @PostMapping("/updateNode")
//    @CustomLogger(title = "材料编码类型-修改",businessType = CustomBusinessType.OTHER)
    public AjaxResult updateNode(@Validated(ValidationGroups.Other.class) @RequestBody MaterialCategory materialCategory){
        return materialCategoryService.updateNode(materialCategory);
    }


    @PostMapping("/materialFistCategory")
//    @CustomLogger(title = "材料编码类型-获取第一层级分类",businessType = CustomBusinessType.OTHER)
    public AjaxResult materialFistCategory(@RequestBody MaterialCategory category){
        List<MaterialCategory> list = materialCategoryService.materialFistCategory(category);
        return AjaxResult.success(list);
    }

    @PostMapping("/treeList")
//    @CustomLogger(title = "材料编码类型-树形",businessType = CustomBusinessType.SELECT)
    public AjaxResult treeList(){
        List<Map> list = materialCategoryService.categoryTreeList();
        return AjaxResult.success(list);
    }

    @PostMapping("/getAllCascaderMaterialCategorys")
//    @CustomLogger(title = "获取所有分类",businessType = CustomBusinessType.SELECT)
    public AjaxResult getAllCascaderMaterialCategorys(){
        List<CascaderMaterialCategoryVo> list = materialCategoryMapper.getAllCascaderMaterialCategorys();
        List<CascaderMaterialCategoryVo> newData = new ArrayList<>();
        /* List<TreeUtil> list = myCommonMapper.getDeptTree(where);*/
        if(!ObjectNullUtil.isEmpty(list)){
            //把部门信息放到map中
            Map<String,CascaderMaterialCategoryVo> map = new HashMap<>();
            list.stream().forEach(temp -> {
                map.put(temp.getId() + "", temp);
            });
            for(CascaderMaterialCategoryVo temp : list){
                if(!map.containsKey(temp.getPid() + "")){
                    //顶级节点
                    newData.add(temp);
                }
            }
            for(CascaderMaterialCategoryVo temp : list){
                CascaderMaterialCategoryVo parent = map.get(temp.getPid() + "");
                if(!ObjectNullUtil.isEmpty(parent)){ // 不等于null，也就意味着有父节点
                    if(parent.getChildren() == null){
                        parent.setChildren(new ArrayList<CascaderMaterialCategoryVo>());
                    }
                    parent.getChildren().add(temp); // 添加到父节点的ChildList集合下
                    map.put(temp.getPid() + "",parent);  // 把放好的数据放回到map中
                }
            }
        }

        return AjaxResult.success(newData);
    }

    /**
     * 根据名称模糊查询
     * 带分类层级
     *
     * @param category
     * @return
     */
    @PostMapping("/selectMaterialCategory")
//    @CustomLogger(title = "根据名称模糊查询(带分类层级)",businessType = CustomBusinessType.SELECT)
    public AjaxResult selectMaterialCategoryByName(@RequestBody MaterialCategory category){
        List<MaterialCategory> list=materialCategoryService.selectMaterialCategoryByName(category);
        return AjaxResult.success(list);
    }

    @GetMapping("/selectMaterialCodeList")
    public AjaxResult selectMaterialCodeList(MaterialCategory materialCategory){
        startPage();
        List<MaterialCategory> list = materialCategoryService.selectMaterialCategoryList(materialCategory);
        TableDataInfo dataTable = getDataTable(list);
        return AjaxResult.success(dataTable);
    }

    @PostMapping("/resetPath")
    public AjaxResult resetPath(@RequestBody MaterialCategory materialCategory) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        materialCategoryService.resetPath(materialCategory);
        return AjaxResult.success();
    }

    /**
     *根据categoryCode 查询三 四层级名称
     *
     * @param map
     * @return
     */
    @PostMapping("/selectMaterialCategoryPath")
    public AjaxResult selectMaterialCategoryPath(@RequestBody Map<String,Object> map){
        Integer type=(Integer)map.get("type");
        List list=(List)map.get("list");
        List<Map> mapList = materialCategoryService.selectMaterialCategoryPath(list, type);
        if(CollectionUtils.isEmpty(mapList)){
            return AjaxResult.error("数据处理异常");
        }
        return AjaxResult.success(mapList);
    }
}
