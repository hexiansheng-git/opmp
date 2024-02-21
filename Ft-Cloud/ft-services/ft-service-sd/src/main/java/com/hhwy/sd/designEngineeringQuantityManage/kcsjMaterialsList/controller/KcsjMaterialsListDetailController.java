package com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsListDetail;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.service.IKcsjMaterialsListDetailService;
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
 * @date 2024-02-18 15:02:49
 * @remark 勘察设计-设计工程量管理-主材清单明细
 */
@Validated
@RestController
@RequestMapping("/kcsjMaterialsListDetail")
public class KcsjMaterialsListDetailController extends BaseController {

    @Autowired
    private IKcsjMaterialsListDetailService kcsjMaterialsListDetailService;


    @PreAuthorize(hasPermi = "kcsjMaterialsListDetail:list")
    @GetMapping
    public AjaxResult getKcsjMaterialsListDetail(@Validated(ValidationGroups.Get.class) KcsjMaterialsListDetail kcsjMaterialsListDetailParam) {
        KcsjMaterialsListDetail kcsjMaterialsListDetail = kcsjMaterialsListDetailService.getKcsjMaterialsListDetail(kcsjMaterialsListDetailParam);
        return AjaxResult.success(kcsjMaterialsListDetail);
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsListDetail:list")
    @GetMapping("/list")
    public AjaxResult getKcsjMaterialsListDetailList(@Validated(ValidationGroups.Select.class) KcsjMaterialsListDetail kcsjMaterialsListDetailParam) {
        startPage();
        List<KcsjMaterialsListDetail> kcsjMaterialsListDetailList = kcsjMaterialsListDetailService.getKcsjMaterialsListDetailList(kcsjMaterialsListDetailParam);
        return getDataTableAjaxResult(kcsjMaterialsListDetailList);
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsListDetail:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjMaterialsListDetail(@Validated(ValidationGroups.Save.class) @RequestBody KcsjMaterialsListDetail kcsjMaterialsListDetailParam) {
        kcsjMaterialsListDetailService.insertKcsjMaterialsListDetail(kcsjMaterialsListDetailParam);
        return AjaxResult.success(kcsjMaterialsListDetailParam);
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsListDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjMaterialsListDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjMaterialsListDetail> kcsjMaterialsListDetailListParam) {
        kcsjMaterialsListDetailService.insertKcsjMaterialsListDetailList(kcsjMaterialsListDetailListParam);
        return AjaxResult.success(kcsjMaterialsListDetailListParam);
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsListDetail:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjMaterialsListDetail(@Validated(ValidationGroups.Update.class) @RequestBody KcsjMaterialsListDetail kcsjMaterialsListDetailParam) {
        return toAjax(kcsjMaterialsListDetailService.updateKcsjMaterialsListDetail(kcsjMaterialsListDetailParam));
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsListDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjMaterialsListDetailList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjMaterialsListDetail> kcsjMaterialsListDetailListParam) {
        return toAjax(kcsjMaterialsListDetailService.updateKcsjMaterialsListDetailList(kcsjMaterialsListDetailListParam));
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsListDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjMaterialsListDetail(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjMaterialsListDetail kcsjMaterialsListDetailParam) {
        return toAjax(kcsjMaterialsListDetailService.deleteKcsjMaterialsListDetail(kcsjMaterialsListDetailParam));
    }

    @PreAuthorize(hasPermi = "kcsjMaterialsListDetail:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjMaterialsListDetailByPks(@PathVariable Long[] ids) {
        List<Long> kcsjMaterialsListDetailPkList = Arrays.asList(ids);
        return toAjax(kcsjMaterialsListDetailService.deleteKcsjMaterialsListDetailByPks(kcsjMaterialsListDetailPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjMaterialsListDetail kcsjMaterialsListDetailParam) throws IOException {
        List<KcsjMaterialsListDetail> kcsjMaterialsListDetailList = kcsjMaterialsListDetailService.getKcsjMaterialsListDetailList(kcsjMaterialsListDetailParam);
        ExcelUtils<KcsjMaterialsListDetail> util = new ExcelUtils<>(KcsjMaterialsListDetail.class);
        util.exportExcel(response, kcsjMaterialsListDetailList, DateUtils.getDate());
    }
}
