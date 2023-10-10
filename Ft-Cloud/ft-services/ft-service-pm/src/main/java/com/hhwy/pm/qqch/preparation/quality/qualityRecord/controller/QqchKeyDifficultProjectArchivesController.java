package com.hhwy.pm.qqch.preparation.quality.qualityRecord.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchKeyDifficultProjectArchives;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo.KeyDifficultWbsVo;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.service.IQqchKeyDifficultProjectArchivesService;
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
 * @date 2023-08-24 15:32:19
 * @remark 重难点工程档案
 */
@Validated
@RestController
@RequestMapping("/qqchKeyDifficultProjectArchives")
public class QqchKeyDifficultProjectArchivesController extends BaseController {

    @Autowired
    private IQqchKeyDifficultProjectArchivesService qqchKeyDifficultProjectArchivesService;


//    @PreAuthorize(hasPermi = "qqchKeyDifficultProjectArchives:list")
    @GetMapping
    public AjaxResult getQqchKeyDifficultProjectArchives(@Validated(ValidationGroups.Get.class) QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchivesParam) {
        QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives = qqchKeyDifficultProjectArchivesService.getQqchKeyDifficultProjectArchives(qqchKeyDifficultProjectArchivesParam);
        return AjaxResult.success(qqchKeyDifficultProjectArchives);
    }

//    @PreAuthorize(hasPermi = "qqchKeyDifficultProjectArchives:list")
    @GetMapping("/list")
    public AjaxResult getQqchKeyDifficultProjectArchivesList(@Validated(ValidationGroups.Select.class) QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchivesParam) {
        startPage();
        List<QqchKeyDifficultProjectArchives> qqchKeyDifficultProjectArchivesList = qqchKeyDifficultProjectArchivesService.getQqchKeyDifficultProjectArchivesList(qqchKeyDifficultProjectArchivesParam);
        return getDataTableAjaxResult(qqchKeyDifficultProjectArchivesList);
    }

//    @PreAuthorize(hasPermi = "qqchKeyDifficultProjectArchives:add")
    @PostMapping("/add")
    public AjaxResult insertQqchKeyDifficultProjectArchives(@Validated(ValidationGroups.Save.class) @RequestBody QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchivesParam) {
        qqchKeyDifficultProjectArchivesService.insertQqchKeyDifficultProjectArchives(qqchKeyDifficultProjectArchivesParam);
        return AjaxResult.success(qqchKeyDifficultProjectArchivesParam);
    }

//    @PreAuthorize(hasPermi = "qqchKeyDifficultProjectArchives:update")
    @PostMapping("/update")
    public AjaxResult updateQqchKeyDifficultProjectArchives(@Validated(ValidationGroups.Update.class) @RequestBody QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchivesParam) {
        return toAjax(qqchKeyDifficultProjectArchivesService.updateQqchKeyDifficultProjectArchives(qqchKeyDifficultProjectArchivesParam));
    }

//    @PreAuthorize(hasPermi = "qqchKeyDifficultProjectArchives:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchKeyDifficultProjectArchivesList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchKeyDifficultProjectArchives> qqchKeyDifficultProjectArchivesListParam) {
        return toAjax(qqchKeyDifficultProjectArchivesService.updateQqchKeyDifficultProjectArchivesList(qqchKeyDifficultProjectArchivesListParam));
    }

//    @PreAuthorize(hasPermi = "qqchKeyDifficultProjectArchives:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchKeyDifficultProjectArchives(@Validated(ValidationGroups.Delete.class) @RequestBody QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchivesParam) {
        return toAjax(qqchKeyDifficultProjectArchivesService.deleteQqchKeyDifficultProjectArchives(qqchKeyDifficultProjectArchivesParam));
    }

//    @PreAuthorize(hasPermi = "qqchKeyDifficultProjectArchives:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchKeyDifficultProjectArchivesByPks(@PathVariable Long[] ids) {
        List<Long> qqchKeyDifficultProjectArchivesPkList = Arrays.asList(ids);
        return toAjax(qqchKeyDifficultProjectArchivesService.deleteQqchKeyDifficultProjectArchivesByPks(qqchKeyDifficultProjectArchivesPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchivesParam) throws IOException {
        List<QqchKeyDifficultProjectArchives> qqchKeyDifficultProjectArchivesList = qqchKeyDifficultProjectArchivesService.getQqchKeyDifficultProjectArchivesList(qqchKeyDifficultProjectArchivesParam);
        ExcelUtils<QqchKeyDifficultProjectArchives> util = new ExcelUtils<>(QqchKeyDifficultProjectArchives.class);
        util.exportExcel(response, qqchKeyDifficultProjectArchivesList, DateUtils.getDate());
    }

    /**
     * 获取台账页Vo
     * @param qqchKeyDifficultProjectArchives
     * @return
     */
    @GetMapping("getKeyDifficultWbsVo")
    public AjaxResult getKeyDifficultWbsVo(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives) {
        KeyDifficultWbsVo keyDifficultWbsVo = qqchKeyDifficultProjectArchivesService.getKeyDifficultWbsVo(qqchKeyDifficultProjectArchives);
        return AjaxResult.success(keyDifficultWbsVo);
    }

    /**
     * 保存/确认/提交
     * @param keyDifficultWbsVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchKeyDifficultProjectArchives:save")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody KeyDifficultWbsVo keyDifficultWbsVo) {
        qqchKeyDifficultProjectArchivesService.save(keyDifficultWbsVo);
        return AjaxResult.success();
    }

}
