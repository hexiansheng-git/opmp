package com.hhwy.system.controller;

import com.alibaba.fastjson.JSONObject;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.system.service.IMaterialCategoryService;
import com.hhwy.system.service.IMaterialInfoService;
import com.hhwy.system.vo.ImportMaterialInfo;
import com.hhwy.utils.ThreadPoolUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 中交同步物资设备库Controller
 * 
 * @author lcf
 * @date 2022-10-21
 */
@RestController
@RequestMapping("/material/info")
public class MaterialInfoController extends BaseController {

    @Autowired
    private IMaterialInfoService materialInfoService;

    @Autowired
    private IMaterialCategoryService materialCategoryService;

    /**
     * 查询中交同步物资设备库列表
     * 该方法能不用就不用 用newList
     */
//    @PreAuthorize(hasPermi="material:baseInfo:list")
    @PostMapping("/list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody MaterialInfo materialInfo) {
        startPage(materialInfo.getPageNum(),materialInfo.getPageSize());
        List<MaterialInfo> list = materialInfoService.selectMaterialInfoList(materialInfo);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return AjaxResult.error("未查询到数据");
        }
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 根据名称 模糊搜索所有设备
     */
    @PostMapping("/codeList")
    public AjaxResult codeList(@RequestBody MaterialInfo materialInfo) {
        List<MaterialInfo> list = materialInfoService.selectMaterialInfoList(materialInfo);
        return AjaxResult.success(list);
    }


    /**
     * 查询中交同步物资设备库列表
     */
    @PostMapping("/newList")
    public AjaxResult newList(@Validated(ValidationGroups.Select.class) @RequestBody MaterialInfo materialInfo) {
        //处理分页信息
        int pageNum = materialInfo.getPageNum() == null ? 1 : materialInfo.getPageNum();
        int pageSize = (materialInfo.getPageSize() == null || materialInfo.getPageSize() > 200) ? 200 : materialInfo.getPageSize();
        pageNum = (pageNum - 1) * pageSize;
        materialInfo.setPageNum(pageNum);
        materialInfo.setPageSize(pageSize);
        materialInfo.setIsFalg("1");
        //分页查询材料和材料的数量
        Future<List<MaterialInfo>> future1  = ThreadPoolUtil.getThreadPool().submit(() -> materialInfoService.selectMaterialInfoList(materialInfo));
        Future<Integer> future2  = ThreadPoolUtil.getThreadPool().submit(() -> materialInfoService.getMaterialInfoPageCount(materialInfo));
        try{
            List<MaterialInfo> list  = future1.get();

            //根据categoryId批量查询category信息
            List<MaterialInfo> rstList =materialCategoryService.handleLogicCategoryIds(list);

            int total = future2.get();
            JSONObject data = new JSONObject();
            data.put("total", 0);
            data.put("rows", list);
            if(!CollectionUtils.isEmpty(rstList)){
                data.put("total", total);
                data.put("rows", rstList);
            }
            return AjaxResult.success("查询成功！", data);
        }catch (Exception e){
            return AjaxResult.error("查询异常！", e.getMessage());
        }
    }

    /**
     * 导出中交同步物资设备库列表
     */
    @PostMapping("/export")
    public void export(MaterialInfo materialInfo, HttpServletResponse response)throws IOException {
        List<MaterialInfo> list = materialInfoService.selectMaterialInfoList(materialInfo);
        ExcelUtils<MaterialInfo> util = new ExcelUtils<MaterialInfo>(MaterialInfo.class);
        util.exportExcel(response,list, "物资材料编码");
    }

    /**
     * 新增保存中交同步物资设备库
     */
    @PreAuthorize(hasPermi="material:baseInfo:add")
    @PostMapping("/add")
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody MaterialInfo materialInfo) {
        return materialInfoService.insertMaterialInfo(materialInfo);
    }

    /**
     * 修改保存中交同步物资设备库
     */
    @PreAuthorize(hasPermi="material:baseInfo:edit")
    @PostMapping("/edit")
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody MaterialInfo materialInfo) {
        return materialInfoService.updateMaterialInfo(materialInfo);
    }

    /**
     * 删除中交同步物资设备库
     */
    @PreAuthorize(hasPermi="material:baseInfo:remove")
    @PostMapping( "/remove")
    public AjaxResult remove(@Validated(ValidationGroups.Delete.class) @RequestBody MaterialInfo materialInfo) {
        Long id = materialInfo.getId();
        return toAjax(materialInfoService.deleteMaterialInfoByIds(String.valueOf(id)));
    }

    /**
     *新增最近选择
     *
     * @return
     */
    @PostMapping( "/recentSelect")
    public AjaxResult recentSelect(@RequestBody MaterialInfo materialInfo){
        AjaxResult result = materialInfoService.recentSelect(materialInfo);
        return result;
    }

    /**
     *查询最近选择
     *
     * @return
     */
    @PostMapping( "/selectRecentInfo")
    public AjaxResult selectRecentInfo(@RequestBody MaterialInfo materialInfo){
        AjaxResult result = materialInfoService.selectRecentInfo(materialInfo);
        return result;
    }

    /**
     *查询最近选择
     *
     * @return
     */
    @PostMapping( "/removeRecentInfo")
    public AjaxResult removeRecentInfo(@RequestBody MaterialInfo materialInfo){
        return materialInfoService.removeRecentInfo(materialInfo);
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, Integer type) {
        try{
            ExcelUtils<ImportMaterialInfo> util = new ExcelUtils<>(ImportMaterialInfo.class);
            List<ImportMaterialInfo> importList = util.importExcel(file.getInputStream());
            materialInfoService.importData(importList,type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AjaxResult.success();
    }


}
