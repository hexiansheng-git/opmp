package com.hhwy.sd.organManage.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.sd.organManage.domain.KcsjOrganManageDetail;
import com.hhwy.sd.organManage.domain.KcsjOrganManageDetail4Update;
import com.hhwy.sd.organManage.service.IKcsjOrganManageDetailService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2023-12-14 11:31:53
 * @remark 勘察设计管理-勘察设计组织管理-进场/离场记录
 */
@Validated
@RestController
@RequestMapping("/kcsjOrganManageDetail")
public class KcsjOrganManageDetailController extends BaseController {

    @Autowired
    private IKcsjOrganManageDetailService kcsjOrganManageDetailService;


    @PreAuthorize(hasPermi = "kcsjOrganManageDetail:list")
    @GetMapping
    public AjaxResult getKcsjOrganManageDetail(@Validated(ValidationGroups.Get.class) KcsjOrganManageDetail kcsjOrganManageDetailParam) {
        KcsjOrganManageDetail kcsjOrganManageDetail = kcsjOrganManageDetailService.getKcsjOrganManageDetail(kcsjOrganManageDetailParam);
        return AjaxResult.success(kcsjOrganManageDetail);
    }

    @PreAuthorize(hasPermi = "kcsjOrganManageDetail:list")
    @GetMapping("/list")
    public AjaxResult getKcsjOrganManageDetailList(@Validated(ValidationGroups.Select.class) KcsjOrganManageDetail kcsjOrganManageDetailParam) {
        startPage();
        List<KcsjOrganManageDetail> kcsjOrganManageDetailList = kcsjOrganManageDetailService.getKcsjOrganManageDetailList(kcsjOrganManageDetailParam);
        return getDataTableAjaxResult(kcsjOrganManageDetailList);
    }

    @PreAuthorize(hasPermi = "kcsjOrganManageDetail:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjOrganManageDetail(@Validated(ValidationGroups.Save.class) @RequestBody KcsjOrganManageDetail kcsjOrganManageDetailParam) {
        kcsjOrganManageDetailService.insertKcsjOrganManageDetail(kcsjOrganManageDetailParam);
        return AjaxResult.success(kcsjOrganManageDetailParam);
    }

    @PreAuthorize(hasPermi = "kcsjOrganManageDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjOrganManageDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjOrganManageDetail> kcsjOrganManageDetailListParam) {
        kcsjOrganManageDetailService.insertKcsjOrganManageDetailList(kcsjOrganManageDetailListParam);
        return AjaxResult.success(kcsjOrganManageDetailListParam);
    }

    @PreAuthorize(hasPermi = "kcsjOrganManageDetail:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjOrganManageDetail(@Validated(ValidationGroups.Update.class) @RequestBody KcsjOrganManageDetail kcsjOrganManageDetailParam) {
        return toAjax(kcsjOrganManageDetailService.updateKcsjOrganManageDetail(kcsjOrganManageDetailParam));
    }

    @PreAuthorize(hasPermi = "kcsjOrganManageDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjOrganManageDetailList(@Validated(ValidationGroups.Update.class) @RequestBody KcsjOrganManageDetail4Update kcsjOrganManageDetail4Update) {
        return toAjax(kcsjOrganManageDetailService.updateKcsjOrganManageDetailList(kcsjOrganManageDetail4Update));
    }

    @PreAuthorize(hasPermi = "kcsjOrganManageDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjOrganManageDetail(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjOrganManageDetail kcsjOrganManageDetailParam) {
        return toAjax(kcsjOrganManageDetailService.deleteKcsjOrganManageDetail(kcsjOrganManageDetailParam));
    }

    @PreAuthorize(hasPermi = "kcsjOrganManageDetail:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjOrganManageDetailByPks(@PathVariable Long[] ids) {
        List<Long> kcsjOrganManageDetailPkList = Arrays.asList(ids);
        return toAjax(kcsjOrganManageDetailService.deleteKcsjOrganManageDetailByPks(kcsjOrganManageDetailPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjOrganManageDetail kcsjOrganManageDetailParam) throws IOException {
        List<KcsjOrganManageDetail> kcsjOrganManageDetailList = kcsjOrganManageDetailService.getKcsjOrganManageDetailList(kcsjOrganManageDetailParam);
        ExcelUtils<KcsjOrganManageDetail> util = new ExcelUtils<>(KcsjOrganManageDetail.class);
        util.exportExcel(response, kcsjOrganManageDetailList, DateUtils.getDate());
    }
}
