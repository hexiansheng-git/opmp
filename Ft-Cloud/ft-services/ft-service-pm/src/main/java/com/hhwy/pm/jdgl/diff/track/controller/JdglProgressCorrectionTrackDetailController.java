package com.hhwy.pm.jdgl.diff.track.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrackDetail;
import com.hhwy.pm.jdgl.diff.track.service.IJdglProgressCorrectionTrackDetailService;
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
 * @date 2023-08-24 16:51:15
 * @remark 进度纠偏跟踪详情
 */
@Validated
@RestController
@RequestMapping("/jdglProgressCorrectionTrackDetail")
public class JdglProgressCorrectionTrackDetailController extends BaseController {

    @Autowired
    private IJdglProgressCorrectionTrackDetailService jdglProgressCorrectionTrackDetailService;

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrackDetail:list")
    @GetMapping
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪详情", businessType = CustomBusinessType.SELECT)
    public AjaxResult getJdglProgressCorrectionTrackDetail(
        @Validated(ValidationGroups.Get.class) JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetailParam) {
        JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail = jdglProgressCorrectionTrackDetailService
            .getJdglProgressCorrectionTrackDetail(jdglProgressCorrectionTrackDetailParam);
        return AjaxResult.success(jdglProgressCorrectionTrackDetail);
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrackDetail:list")
    @GetMapping("/list")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪详情", businessType = CustomBusinessType.SELECT)
    public AjaxResult getJdglProgressCorrectionTrackDetailList(
        @Validated(ValidationGroups.Select.class) JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetailParam) {
        startPage();
        List<JdglProgressCorrectionTrackDetail> jdglProgressCorrectionTrackDetailList = jdglProgressCorrectionTrackDetailService
            .getJdglProgressCorrectionTrackDetailList(jdglProgressCorrectionTrackDetailParam);
        return getDataTableAjaxResult(jdglProgressCorrectionTrackDetailList);
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrackDetail:add")
    @PostMapping("/add")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪详情", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertJdglProgressCorrectionTrackDetail(
        @Validated(ValidationGroups.Save.class) @RequestBody JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetailParam) {
        jdglProgressCorrectionTrackDetailService
            .insertJdglProgressCorrectionTrackDetail(jdglProgressCorrectionTrackDetailParam);
        return AjaxResult.success(jdglProgressCorrectionTrackDetailParam);
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrackDetail:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪详情", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertJdglProgressCorrectionTrackDetailList(
        @Validated(ValidationGroups.Save.class) @RequestBody List<JdglProgressCorrectionTrackDetail> jdglProgressCorrectionTrackDetailListParam) {
        jdglProgressCorrectionTrackDetailService
            .insertJdglProgressCorrectionTrackDetailList(jdglProgressCorrectionTrackDetailListParam);
        return AjaxResult.success(jdglProgressCorrectionTrackDetailListParam);
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrackDetail:update")
    @PostMapping("/update")
    public AjaxResult updateJdglProgressCorrectionTrackDetail(
        @Validated(ValidationGroups.Update.class) @RequestBody JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetailParam) {
        return toAjax(jdglProgressCorrectionTrackDetailService
            .updateJdglProgressCorrectionTrackDetail(jdglProgressCorrectionTrackDetailParam));
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrackDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglProgressCorrectionTrackDetailList(
        @Validated(ValidationGroups.Update.class) @RequestBody List<JdglProgressCorrectionTrackDetail> jdglProgressCorrectionTrackDetailListParam) {
        return toAjax(jdglProgressCorrectionTrackDetailService
            .updateJdglProgressCorrectionTrackDetailList(jdglProgressCorrectionTrackDetailListParam));
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrackDetail:remove")
    @PostMapping("/delete")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪详情", businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteJdglProgressCorrectionTrackDetail(
        @Validated(ValidationGroups.Delete.class) @RequestBody JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetailParam) {
        return toAjax(jdglProgressCorrectionTrackDetailService
            .deleteJdglProgressCorrectionTrackDetail(jdglProgressCorrectionTrackDetailParam));
    }

    @PreAuthorize(hasPermi = "jdglProgressCorrectionTrackDetail:remove")
    @PostMapping("/{ids}")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪详情", businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteJdglProgressCorrectionTrackDetailByPks(@PathVariable Long[] ids) {
        List<Long> jdglProgressCorrectionTrackDetailPkList = Arrays.asList(ids);
        return toAjax(jdglProgressCorrectionTrackDetailService
            .deleteJdglProgressCorrectionTrackDetailByPks(jdglProgressCorrectionTrackDetailPkList));
    }

    @GetMapping("/export")
    @CustomLogger(title = "进度管理-进度纠偏跟踪", name = "进度纠偏跟踪详情", businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response,
        JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetailParam) throws IOException {
        List<JdglProgressCorrectionTrackDetail> jdglProgressCorrectionTrackDetailList = jdglProgressCorrectionTrackDetailService
            .getJdglProgressCorrectionTrackDetailList(jdglProgressCorrectionTrackDetailParam);
        ExcelUtils<JdglProgressCorrectionTrackDetail> util = new ExcelUtils<>(JdglProgressCorrectionTrackDetail.class);
        util.exportExcel(response, jdglProgressCorrectionTrackDetailList, DateUtils.getDate());
    }
}
