package com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsList;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service.IKcsjMaterialsListService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author wll
 * @date 2024-02-18 14:39:34
 * @remark 勘察设计-设计工程量管理-主材清单
 */
@Validated
@RestController
@RequestMapping("/kcsjMaterialsList")
public class KcsjMaterialsListController extends BaseController {

    @Autowired
    private IKcsjMaterialsListService kcsjMaterialsListService;


    /**
     * 详情
     *
     * @param kcsjMaterialsListParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjMaterialsList:select")
    @GetMapping
    public AjaxResult getKcsjMaterialsList(@Validated(ValidationGroups.Get.class) KcsjMaterialsList kcsjMaterialsListParam) {
        KcsjMaterialsList kcsjMaterialsList = kcsjMaterialsListService.getKcsjMaterialsList(kcsjMaterialsListParam);
        return AjaxResult.success(kcsjMaterialsList);
    }

    /**
     * 列表页
     *
     * @param kcsjMaterialsListParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjMaterialsList:list")
    @GetMapping("/list")
    public AjaxResult getKcsjMaterialsListList(@Validated(ValidationGroups.Select.class) KcsjMaterialsList kcsjMaterialsListParam) {
        startPage();
        List<KcsjMaterialsList> kcsjMaterialsListList = kcsjMaterialsListService.getKcsjMaterialsListList(kcsjMaterialsListParam);
        return getDataTableAjaxResult(kcsjMaterialsListList);
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsList:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjMaterialsList(@Validated(ValidationGroups.Save.class) @RequestBody KcsjMaterialsList kcsjMaterialsListParam) {
        kcsjMaterialsListService.insertKcsjMaterialsList(kcsjMaterialsListParam);
        return AjaxResult.success(kcsjMaterialsListParam);
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsList:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjMaterialsListList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjMaterialsList> kcsjMaterialsListListParam) {
        kcsjMaterialsListService.insertKcsjMaterialsListList(kcsjMaterialsListListParam);
        return AjaxResult.success(kcsjMaterialsListListParam);
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsList:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjMaterialsList(@Validated(ValidationGroups.Update.class) @RequestBody KcsjMaterialsList kcsjMaterialsListParam) {
        return toAjax(kcsjMaterialsListService.updateKcsjMaterialsList(kcsjMaterialsListParam));
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsList:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjMaterialsListList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjMaterialsList> kcsjMaterialsListListParam) {
        return toAjax(kcsjMaterialsListService.updateKcsjMaterialsListList(kcsjMaterialsListListParam));
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjMaterialsList(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjMaterialsList kcsjMaterialsListParam) {
        return toAjax(kcsjMaterialsListService.deleteKcsjMaterialsList(kcsjMaterialsListParam));
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsList:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjMaterialsListByPks(@PathVariable Long[] ids) {
        List<Long> kcsjMaterialsListPkList = Arrays.asList(ids);
        return toAjax(kcsjMaterialsListService.deleteKcsjMaterialsListByPks(kcsjMaterialsListPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjMaterialsList kcsjMaterialsListParam) throws IOException {
        List<KcsjMaterialsList> kcsjMaterialsListList = kcsjMaterialsListService.getKcsjMaterialsListList(kcsjMaterialsListParam);
        ExcelUtils<KcsjMaterialsList> util = new ExcelUtils<>(KcsjMaterialsList.class);
        util.exportExcel(response, kcsjMaterialsListList, DateUtils.getDate());
    }
}
