package com.hhwy.sd.organManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.organManage.domain.KcsjOrganManage;
import com.hhwy.sd.organManage.domain.KcsjOrganManage4Update;
import com.hhwy.sd.organManage.service.IKcsjOrganManageService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author cjh
 * @date 2023-12-14 11:31:44
 * @remark 勘察设计管理-勘察设计组织管理
 */
@Validated
@RestController
@RequestMapping("/kcsjOrganManage")
public class KcsjOrganManageController extends BaseController {

    @Autowired
    private IKcsjOrganManageService kcsjOrganManageService;


    @PreAuthorize(hasPermi = "kcsjOrganManage:list")
    @GetMapping
    public AjaxResult getKcsjOrganManage(@Validated(ValidationGroups.Get.class) KcsjOrganManage kcsjOrganManageParam) {
        KcsjOrganManage kcsjOrganManage = kcsjOrganManageService.getKcsjOrganManage(kcsjOrganManageParam);
        return AjaxResult.success(kcsjOrganManage);
    }

    @PreAuthorize(hasPermi = "kcsjOrganManage:list")
    @GetMapping("/list")
    public AjaxResult getKcsjOrganManageList(@Validated(ValidationGroups.Select.class) KcsjOrganManage kcsjOrganManageParam) {
        startPage();
        List<KcsjOrganManage> kcsjOrganManageList = kcsjOrganManageService.getKcsjOrganManageList(kcsjOrganManageParam);
        return getDataTableAjaxResult(kcsjOrganManageList);
    }

    @GetMapping("/sync")
    public AjaxResult sync() {
        kcsjOrganManageService.sync();
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "kcsjOrganManage:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjOrganManage(@Validated(ValidationGroups.Save.class) @RequestBody KcsjOrganManage kcsjOrganManageParam) {
        kcsjOrganManageService.insertKcsjOrganManage(kcsjOrganManageParam);
        return AjaxResult.success(kcsjOrganManageParam);
    }

    @PreAuthorize(hasPermi = "kcsjOrganManage:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjOrganManageList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjOrganManage> kcsjOrganManageListParam) {
        kcsjOrganManageService.insertKcsjOrganManageList(kcsjOrganManageListParam);
        return AjaxResult.success(kcsjOrganManageListParam);
    }

    @PreAuthorize(hasPermi = "kcsjOrganManage:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjOrganManage(@Validated(ValidationGroups.Update.class) @RequestBody KcsjOrganManage kcsjOrganManageParam) {
        return toAjax(kcsjOrganManageService.updateKcsjOrganManage(kcsjOrganManageParam));
    }

    @PreAuthorize(hasPermi = "kcsjOrganManage:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjOrganManageList(@Validated(ValidationGroups.Update.class) @RequestBody KcsjOrganManage4Update kcsjOrganManage4Update) {
        int i = kcsjOrganManageService.newUpdateKcsjOrganManageList(kcsjOrganManage4Update);
        return toAjax(i);
    }

    @PreAuthorize(hasPermi = "kcsjOrganManage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjOrganManage(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjOrganManage kcsjOrganManageParam) {
        return toAjax(kcsjOrganManageService.deleteKcsjOrganManage(kcsjOrganManageParam));
    }

    @PreAuthorize(hasPermi = "kcsjOrganManage:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjOrganManageByPks(@PathVariable Long[] ids) {
        List<Long> kcsjOrganManagePkList = Arrays.asList(ids);
        return toAjax(kcsjOrganManageService.deleteKcsjOrganManageByPks(kcsjOrganManagePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjOrganManage kcsjOrganManageParam) throws IOException {
        List<KcsjOrganManage> kcsjOrganManageList = kcsjOrganManageService.getKcsjOrganManageList(kcsjOrganManageParam);
        ExcelUtils<KcsjOrganManage> util = new ExcelUtils<>(KcsjOrganManage.class);
        util.exportExcel(response, kcsjOrganManageList, DateUtils.getDate());
    }
}
