package com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.domain.QqchSurveyResultAsk;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.service.IQqchSurveyResultAskService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:45:52
 * @remark 2.3.2 勘察成果验收内容形式审查要求
 */
@Validated
@RestController
@RequestMapping("/qqchSurveyResultAsk")
public class QqchSurveyResultAskController extends BaseController {

    @Autowired
    private IQqchSurveyResultAskService qqchSurveyResultAskService;


    @PreAuthorize(hasPermi = "qqchSurveyResultAsk:list")
    @GetMapping
    public AjaxResult getQqchSurveyResultAsk(@Validated(ValidationGroups.Get.class) QqchSurveyResultAsk qqchSurveyResultAskParam) {
        QqchSurveyResultAsk qqchSurveyResultAsk = qqchSurveyResultAskService.getQqchSurveyResultAsk(qqchSurveyResultAskParam);
        return AjaxResult.success(qqchSurveyResultAsk);
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultAsk:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyResultAskList(@Validated(ValidationGroups.Select.class) QqchSurveyResultAsk qqchSurveyResultAskParam) {
        startPage();
        List<QqchSurveyResultAsk> qqchSurveyResultAskList = qqchSurveyResultAskService.getQqchSurveyResultAskList(qqchSurveyResultAskParam);
        return getDataTableAjaxResult(qqchSurveyResultAskList);
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultAsk:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSurveyResultAsk(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyResultAsk qqchSurveyResultAskParam) {
        qqchSurveyResultAskService.insertQqchSurveyResultAsk(qqchSurveyResultAskParam);
        return AjaxResult.success(qqchSurveyResultAskParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultAsk:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSurveyResultAskList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSurveyResultAsk> qqchSurveyResultAskListParam) {
        qqchSurveyResultAskService.insertQqchSurveyResultAskList(qqchSurveyResultAskListParam);
        return AjaxResult.success(qqchSurveyResultAskListParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultAsk:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSurveyResultAsk(@Validated(ValidationGroups.Update.class) @RequestBody QqchSurveyResultAsk qqchSurveyResultAskParam) {
        return toAjax(qqchSurveyResultAskService.updateQqchSurveyResultAsk(qqchSurveyResultAskParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultAsk:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSurveyResultAskList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSurveyResultAsk> qqchSurveyResultAskListParam) {
        return toAjax(qqchSurveyResultAskService.updateQqchSurveyResultAskList(qqchSurveyResultAskListParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultAsk:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSurveyResultAsk(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSurveyResultAsk qqchSurveyResultAskParam) {
        return toAjax(qqchSurveyResultAskService.deleteQqchSurveyResultAsk(qqchSurveyResultAskParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultAsk:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSurveyResultAskByPks(@PathVariable Long[] ids) {
        List<Long> qqchSurveyResultAskPkList = Arrays.asList(ids);
        return toAjax(qqchSurveyResultAskService.deleteQqchSurveyResultAskByPks(qqchSurveyResultAskPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSurveyResultAsk qqchSurveyResultAskParam) throws IOException {
        List<QqchSurveyResultAsk> qqchSurveyResultAskList = qqchSurveyResultAskService.getQqchSurveyResultAskList(qqchSurveyResultAskParam);
        ExcelUtils<QqchSurveyResultAsk> util = new ExcelUtils<>(QqchSurveyResultAsk.class);
        util.exportExcel(response, qqchSurveyResultAskList, DateUtils.getDate());
    }
}
