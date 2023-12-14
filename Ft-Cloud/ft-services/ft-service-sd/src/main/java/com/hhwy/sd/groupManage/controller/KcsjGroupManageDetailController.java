package com.hhwy.sd.groupManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.groupManage.domain.KcsjGroupManageDetail;
import com.hhwy.sd.groupManage.service.IKcsjGroupManageDetailService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-12-13 15:27:13
 * @remark 勘察设计队伍管理-详情表
 */
@Validated
@RestController
@RequestMapping("/kcsjGroupManageDetail")
public class KcsjGroupManageDetailController extends BaseController {

    @Autowired
    private IKcsjGroupManageDetailService kcsjGroupManageDetailService;


    @PreAuthorize(hasPermi = "kcsjGroupManageDetail:list")
    @GetMapping
    public AjaxResult getKcsjGroupManageDetail(@Validated(ValidationGroups.Get.class) KcsjGroupManageDetail kcsjGroupManageDetailParam) {
        KcsjGroupManageDetail kcsjGroupManageDetail = kcsjGroupManageDetailService.getKcsjGroupManageDetail(kcsjGroupManageDetailParam);
        return AjaxResult.success(kcsjGroupManageDetail);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageDetail:list")
    @GetMapping("/list")
    public AjaxResult getKcsjGroupManageDetailList(@Validated(ValidationGroups.Select.class) KcsjGroupManageDetail kcsjGroupManageDetailParam) {
        startPage();
        List<KcsjGroupManageDetail> kcsjGroupManageDetailList = kcsjGroupManageDetailService.getKcsjGroupManageDetailList(kcsjGroupManageDetailParam);
        return getDataTableAjaxResult(kcsjGroupManageDetailList);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageDetail:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjGroupManageDetail(@Validated(ValidationGroups.Save.class) @RequestBody KcsjGroupManageDetail kcsjGroupManageDetailParam) {
        kcsjGroupManageDetailService.insertKcsjGroupManageDetail(kcsjGroupManageDetailParam);
        return AjaxResult.success(kcsjGroupManageDetailParam);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjGroupManageDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjGroupManageDetail> kcsjGroupManageDetailListParam) {
        kcsjGroupManageDetailService.insertKcsjGroupManageDetailList(kcsjGroupManageDetailListParam);
        return AjaxResult.success(kcsjGroupManageDetailListParam);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageDetail:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjGroupManageDetail(@Validated(ValidationGroups.Update.class) @RequestBody KcsjGroupManageDetail kcsjGroupManageDetailParam) {
        return toAjax(kcsjGroupManageDetailService.updateKcsjGroupManageDetail(kcsjGroupManageDetailParam));
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjGroupManageDetailList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjGroupManageDetail> kcsjGroupManageDetailListParam) {
        return toAjax(kcsjGroupManageDetailService.updateKcsjGroupManageDetailList(kcsjGroupManageDetailListParam));
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjGroupManageDetail(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjGroupManageDetail kcsjGroupManageDetailParam) {
        return toAjax(kcsjGroupManageDetailService.deleteKcsjGroupManageDetail(kcsjGroupManageDetailParam));
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageDetail:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjGroupManageDetailByPks(@PathVariable Long[] ids) {
        List<Long> kcsjGroupManageDetailPkList = Arrays.asList(ids);
        return toAjax(kcsjGroupManageDetailService.deleteKcsjGroupManageDetailByPks(kcsjGroupManageDetailPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjGroupManageDetail kcsjGroupManageDetailParam) throws IOException {
        List<KcsjGroupManageDetail> kcsjGroupManageDetailList = kcsjGroupManageDetailService.getKcsjGroupManageDetailList(kcsjGroupManageDetailParam);
        ExcelUtils<KcsjGroupManageDetail> util = new ExcelUtils<>(KcsjGroupManageDetail.class);
        util.exportExcel(response, kcsjGroupManageDetailList, DateUtils.getDate());
    }
}
