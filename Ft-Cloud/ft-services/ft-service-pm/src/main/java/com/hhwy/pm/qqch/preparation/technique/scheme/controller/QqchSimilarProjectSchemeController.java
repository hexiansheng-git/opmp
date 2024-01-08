package com.hhwy.pm.qqch.preparation.technique.scheme.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchSimilarProjectScheme;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.SimilarProjectSchemeQueryVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchSimilarProjectSchemeService;
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
 * @date 2024-01-05 11:49:06
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchSimilarProjectScheme")
public class QqchSimilarProjectSchemeController extends BaseController {

    @Autowired
    private IQqchSimilarProjectSchemeService qqchSimilarProjectSchemeService;


    @PreAuthorize(hasPermi = "qqchSimilarProjectScheme:list")
    @GetMapping
    public AjaxResult getQqchSimilarProjectScheme(@Validated(ValidationGroups.Get.class) QqchSimilarProjectScheme qqchSimilarProjectSchemeParam) {
        QqchSimilarProjectScheme qqchSimilarProjectScheme = qqchSimilarProjectSchemeService.getQqchSimilarProjectScheme(qqchSimilarProjectSchemeParam);
        return AjaxResult.success(qqchSimilarProjectScheme);
    }

    @PreAuthorize(hasPermi = "qqchSimilarProjectScheme:list")
    @GetMapping("/list")
    public AjaxResult getQqchSimilarProjectSchemeList(@Validated(ValidationGroups.Select.class) QqchSimilarProjectScheme qqchSimilarProjectSchemeParam) {
        startPage();
        List<QqchSimilarProjectScheme> qqchSimilarProjectSchemeList = qqchSimilarProjectSchemeService.getQqchSimilarProjectSchemeList(qqchSimilarProjectSchemeParam);
        return getDataTableAjaxResult(qqchSimilarProjectSchemeList);
    }

    @PreAuthorize(hasPermi = "qqchSimilarProjectScheme:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSimilarProjectScheme(@Validated(ValidationGroups.Save.class) @RequestBody QqchSimilarProjectScheme qqchSimilarProjectSchemeParam) {
        qqchSimilarProjectSchemeService.insertQqchSimilarProjectScheme(qqchSimilarProjectSchemeParam);
        return AjaxResult.success(qqchSimilarProjectSchemeParam);
    }

    @PreAuthorize(hasPermi = "qqchSimilarProjectScheme:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSimilarProjectSchemeList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSimilarProjectScheme> qqchSimilarProjectSchemeListParam) {
        qqchSimilarProjectSchemeService.insertQqchSimilarProjectSchemeList(qqchSimilarProjectSchemeListParam);
        return AjaxResult.success(qqchSimilarProjectSchemeListParam);
    }

    @PreAuthorize(hasPermi = "qqchSimilarProjectScheme:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSimilarProjectScheme(@Validated(ValidationGroups.Update.class) @RequestBody QqchSimilarProjectScheme qqchSimilarProjectSchemeParam) {
        return toAjax(qqchSimilarProjectSchemeService.updateQqchSimilarProjectScheme(qqchSimilarProjectSchemeParam));
    }

    @PreAuthorize(hasPermi = "qqchSimilarProjectScheme:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSimilarProjectSchemeList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSimilarProjectScheme> qqchSimilarProjectSchemeListParam) {
        return toAjax(qqchSimilarProjectSchemeService.updateQqchSimilarProjectSchemeList(qqchSimilarProjectSchemeListParam));
    }

    @PreAuthorize(hasPermi = "qqchSimilarProjectScheme:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSimilarProjectScheme(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSimilarProjectScheme qqchSimilarProjectSchemeParam) {
        return toAjax(qqchSimilarProjectSchemeService.deleteQqchSimilarProjectScheme(qqchSimilarProjectSchemeParam));
    }

    @PreAuthorize(hasPermi = "qqchSimilarProjectScheme:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSimilarProjectSchemeByPks(@PathVariable Long[] ids) {
        List<Long> qqchSimilarProjectSchemePkList = Arrays.asList(ids);
        return toAjax(qqchSimilarProjectSchemeService.deleteQqchSimilarProjectSchemeByPks(qqchSimilarProjectSchemePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSimilarProjectScheme qqchSimilarProjectSchemeParam) throws IOException {
        List<QqchSimilarProjectScheme> qqchSimilarProjectSchemeList = qqchSimilarProjectSchemeService.getQqchSimilarProjectSchemeList(qqchSimilarProjectSchemeParam);
        ExcelUtils<QqchSimilarProjectScheme> util = new ExcelUtils<>(QqchSimilarProjectScheme.class);
        util.exportExcel(response, qqchSimilarProjectSchemeList, DateUtils.getDate());
    }

    @GetMapping("testPushData")
    public AjaxResult testPushData() {
        qqchSimilarProjectSchemeService.pushData();
        return AjaxResult.success();
    }

    /**
     * 同类项目方案查询
     * @param queryVo
     * @return
     */
    @GetMapping("getSimilarProjectScheme")
    public AjaxResult getSimilarProjectScheme(SimilarProjectSchemeQueryVo queryVo){
        List<QqchSimilarProjectScheme> similarProjectSchemeVoList = qqchSimilarProjectSchemeService.getSimilarProjectScheme(queryVo);
        return AjaxResult.success(similarProjectSchemeVoList);
    }
}
