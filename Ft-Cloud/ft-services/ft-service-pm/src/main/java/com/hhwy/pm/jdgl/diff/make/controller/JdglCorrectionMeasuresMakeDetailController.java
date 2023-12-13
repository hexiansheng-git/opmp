package com.hhwy.pm.jdgl.diff.make.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMakeDetail;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeDetailService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-08-25 15:26:48
 * @remark 纠偏措施制定详情
 */
@Validated
@RestController
@RequestMapping("/jdglCorrectionMeasuresMakeDetail")
public class JdglCorrectionMeasuresMakeDetailController extends BaseController {

    @Autowired
    private IJdglCorrectionMeasuresMakeDetailService jdglCorrectionMeasuresMakeDetailService;

    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMakeDetail:list")
    @GetMapping
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定详情", businessType = CustomBusinessType.SELECT)
    public AjaxResult getJdglCorrectionMeasuresMakeDetail(
        @Validated(ValidationGroups.Get.class) JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetailParam) {
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail = jdglCorrectionMeasuresMakeDetailService
            .getJdglCorrectionMeasuresMakeDetail(jdglCorrectionMeasuresMakeDetailParam);
        return AjaxResult.success(jdglCorrectionMeasuresMakeDetail);
    }

    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMakeDetail:list")
    @GetMapping("/list")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定详情", businessType = CustomBusinessType.SELECT)
    public AjaxResult getJdglCorrectionMeasuresMakeDetailList(
        @Validated(ValidationGroups.Select.class) JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetailParam) {
        startPage();
        List<JdglCorrectionMeasuresMakeDetail> jdglCorrectionMeasuresMakeDetailList = jdglCorrectionMeasuresMakeDetailService
            .getJdglCorrectionMeasuresMakeDetailList(jdglCorrectionMeasuresMakeDetailParam);
        return getDataTableAjaxResult(jdglCorrectionMeasuresMakeDetailList);
    }

    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMakeDetail:add")
    @PostMapping("/add")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定详情", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertJdglCorrectionMeasuresMakeDetail(
        @Validated(ValidationGroups.Save.class) @RequestBody JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetailParam) {
        jdglCorrectionMeasuresMakeDetailService
            .insertJdglCorrectionMeasuresMakeDetail(jdglCorrectionMeasuresMakeDetailParam);
        return AjaxResult.success(jdglCorrectionMeasuresMakeDetailParam);
    }

    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMakeDetail:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定详情", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertJdglCorrectionMeasuresMakeDetailList(
        @Validated(ValidationGroups.Save.class) @RequestBody List<JdglCorrectionMeasuresMakeDetail> jdglCorrectionMeasuresMakeDetailListParam) {
        jdglCorrectionMeasuresMakeDetailService
            .insertJdglCorrectionMeasuresMakeDetailList(jdglCorrectionMeasuresMakeDetailListParam);
        return AjaxResult.success(jdglCorrectionMeasuresMakeDetailListParam);
    }

    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMakeDetail:update")
    @PostMapping("/update")
    public AjaxResult updateJdglCorrectionMeasuresMakeDetail(
        @Validated(ValidationGroups.Update.class) @RequestBody JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetailParam) {
        return toAjax(jdglCorrectionMeasuresMakeDetailService
            .updateJdglCorrectionMeasuresMakeDetail(jdglCorrectionMeasuresMakeDetailParam));
    }

    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMakeDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglCorrectionMeasuresMakeDetailList(
        @Validated(ValidationGroups.Update.class) @RequestBody List<JdglCorrectionMeasuresMakeDetail> jdglCorrectionMeasuresMakeDetailListParam) {
        return toAjax(jdglCorrectionMeasuresMakeDetailService
            .updateJdglCorrectionMeasuresMakeDetailList(jdglCorrectionMeasuresMakeDetailListParam));
    }

    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMakeDetail:remove")
    @PostMapping("/delete")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定详情", businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteJdglCorrectionMeasuresMakeDetail(
        @Validated(ValidationGroups.Delete.class) @RequestBody JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetailParam) {
        return toAjax(jdglCorrectionMeasuresMakeDetailService
            .deleteJdglCorrectionMeasuresMakeDetail(jdglCorrectionMeasuresMakeDetailParam));
    }

    @PreAuthorize(hasPermi = "jdglCorrectionMeasuresMakeDetail:remove")
    @PostMapping("/{ids}")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定详情", businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteJdglCorrectionMeasuresMakeDetailByPks(@PathVariable Long[] ids) {
        List<Long> jdglCorrectionMeasuresMakeDetailPkList = Arrays.asList(ids);
        return toAjax(jdglCorrectionMeasuresMakeDetailService
            .deleteJdglCorrectionMeasuresMakeDetailByPks(jdglCorrectionMeasuresMakeDetailPkList));
    }

    @GetMapping("/export")
    @CustomLogger(title = "进度管理-差异化管控", name = "纠偏措施制定详情", businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response,
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetailParam) throws IOException {
        List<JdglCorrectionMeasuresMakeDetail> jdglCorrectionMeasuresMakeDetailList = jdglCorrectionMeasuresMakeDetailService
            .getJdglCorrectionMeasuresMakeDetailList(jdglCorrectionMeasuresMakeDetailParam);
        ExcelUtils<JdglCorrectionMeasuresMakeDetail> util = new ExcelUtils<>(JdglCorrectionMeasuresMakeDetail.class);
        util.exportExcel(response, jdglCorrectionMeasuresMakeDetailList, DateUtils.getDate());
    }
}
