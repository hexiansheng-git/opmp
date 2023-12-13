package com.hhwy.pm.qqch.preparation.quality.qualityRecord.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchGeneralProjectArchives;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo.GeneralProjectArchivesWbs;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo.GeneralProjectArchivesWbsVo;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.service.IQqchGeneralProjectArchivesService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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
 * @date 2023-08-24 15:32:35
 * @remark 9.7.2 一般工程档案
 */
@Validated
@RestController
@RequestMapping("/qqchGeneralProjectArchives")
public class QqchGeneralProjectArchivesController extends BaseController {

    @Autowired
    private IQqchGeneralProjectArchivesService qqchGeneralProjectArchivesService;


//    @PreAuthorize(hasPermi = "qqchGeneralProjectArchives:list")
    @GetMapping
    public AjaxResult getQqchGeneralProjectArchives(@Validated(ValidationGroups.Get.class) QqchGeneralProjectArchives qqchGeneralProjectArchivesParam) {
        QqchGeneralProjectArchives qqchGeneralProjectArchives = qqchGeneralProjectArchivesService.getQqchGeneralProjectArchives(qqchGeneralProjectArchivesParam);
        return AjaxResult.success(qqchGeneralProjectArchives);
    }

//    @PreAuthorize(hasPermi = "qqchGeneralProjectArchives:list")
    @GetMapping("/list")
    public AjaxResult getQqchGeneralProjectArchivesList(@Validated(ValidationGroups.Select.class) QqchGeneralProjectArchives qqchGeneralProjectArchivesParam) {
        startPage();
        List<QqchGeneralProjectArchives> qqchGeneralProjectArchivesList = qqchGeneralProjectArchivesService.getQqchGeneralProjectArchivesList(qqchGeneralProjectArchivesParam);
        return getDataTableAjaxResult(qqchGeneralProjectArchivesList);
    }

//    @PreAuthorize(hasPermi = "qqchGeneralProjectArchives:add")
    @PostMapping("/add")
    public AjaxResult insertQqchGeneralProjectArchives(@Validated(ValidationGroups.Save.class) @RequestBody QqchGeneralProjectArchives qqchGeneralProjectArchivesParam) {
        qqchGeneralProjectArchivesService.insertQqchGeneralProjectArchives(qqchGeneralProjectArchivesParam);
        return AjaxResult.success(qqchGeneralProjectArchivesParam);
    }

//    @PreAuthorize(hasPermi = "qqchGeneralProjectArchives:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchGeneralProjectArchivesList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchGeneralProjectArchives> qqchGeneralProjectArchivesListParam) {
        qqchGeneralProjectArchivesService.insertQqchGeneralProjectArchivesList(qqchGeneralProjectArchivesListParam);
        return AjaxResult.success(qqchGeneralProjectArchivesListParam);
    }

//    @PreAuthorize(hasPermi = "qqchGeneralProjectArchives:update")
    @PostMapping("/update")
    public AjaxResult updateQqchGeneralProjectArchives(@Validated(ValidationGroups.Update.class) @RequestBody QqchGeneralProjectArchives qqchGeneralProjectArchivesParam) {
        return toAjax(qqchGeneralProjectArchivesService.updateQqchGeneralProjectArchives(qqchGeneralProjectArchivesParam));
    }

//    @PreAuthorize(hasPermi = "qqchGeneralProjectArchives:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchGeneralProjectArchivesList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchGeneralProjectArchives> qqchGeneralProjectArchivesListParam) {
        return toAjax(qqchGeneralProjectArchivesService.updateQqchGeneralProjectArchivesList(qqchGeneralProjectArchivesListParam));
    }

//    @PreAuthorize(hasPermi = "qqchGeneralProjectArchives:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchGeneralProjectArchives(@Validated(ValidationGroups.Delete.class) @RequestBody QqchGeneralProjectArchives qqchGeneralProjectArchivesParam) {
        return toAjax(qqchGeneralProjectArchivesService.deleteQqchGeneralProjectArchives(qqchGeneralProjectArchivesParam));
    }

//    @PreAuthorize(hasPermi = "qqchGeneralProjectArchives:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchGeneralProjectArchivesByPks(@PathVariable Long[] ids) {
        List<Long> qqchGeneralProjectArchivesPkList = Arrays.asList(ids);
        return toAjax(qqchGeneralProjectArchivesService.deleteQqchGeneralProjectArchivesByPks(qqchGeneralProjectArchivesPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchGeneralProjectArchives qqchGeneralProjectArchivesParam) throws IOException {
        List<QqchGeneralProjectArchives> qqchGeneralProjectArchivesList = qqchGeneralProjectArchivesService.getQqchGeneralProjectArchivesList(qqchGeneralProjectArchivesParam);
        ExcelUtils<QqchGeneralProjectArchives> util = new ExcelUtils<>(QqchGeneralProjectArchives.class);
        util.exportExcel(response, qqchGeneralProjectArchivesList, DateUtils.getDate());
    }

    /**
     * 获取台账Vo
     * @param qqchGeneralProjectArchives
     * @return
     */
    @GetMapping("getGeneralProjectArchivesWbsVo")
    public AjaxResult getGeneralProjectArchivesWbsVo(@Validated(ValidationGroups.Get.class) QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        GeneralProjectArchivesWbsVo generalProjectArchivesWbsVo = qqchGeneralProjectArchivesService.getGeneralProjectArchivesWbsVo(qqchGeneralProjectArchives);
        return AjaxResult.success(generalProjectArchivesWbsVo);
    }

    /**
     * 点击获取下级
     * @param qqchGeneralProjectArchives
     * @return
     */
    @GetMapping("getLowerLevel")
    public AjaxResult getLowerLevel(QqchGeneralProjectArchives qqchGeneralProjectArchives) {
        List<GeneralProjectArchivesWbs> list = qqchGeneralProjectArchivesService.getLowerLevel(qqchGeneralProjectArchives);
        return AjaxResult.success(list);
    }

    /**
     * 保存/确认/提交
     * @param generalProjectArchivesWbsVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchGeneralProjectArchives:add")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.7 质量档案", name = "\n" +
            "9.7.2 一般工程档案" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody GeneralProjectArchivesWbsVo generalProjectArchivesWbsVo) {
        qqchGeneralProjectArchivesService.save(generalProjectArchivesWbsVo);
        return AjaxResult.success();
    }
}
