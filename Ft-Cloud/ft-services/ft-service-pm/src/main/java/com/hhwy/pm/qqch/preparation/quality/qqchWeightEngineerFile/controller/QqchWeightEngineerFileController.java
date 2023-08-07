package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.domain.QqchWeightEngineerFile;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.domain.vo.QqchWeightEngineerFileVo;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.service.IQqchWeightEngineerFileService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-07 09:51:59
 * @remark 9.7.1 重难点工程档案
 */
@Validated
@RestController
@RequestMapping("/qqchWeightEngineerFile")
public class QqchWeightEngineerFileController extends BaseController {

    @Autowired
    private IQqchWeightEngineerFileService qqchWeightEngineerFileService;


    /**
     * 列表接口
     *
     * @param qqchWeightEngineerFileParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchWeightEngineerFile:list")
    @GetMapping("/list")
    public AjaxResult getQqchWeightEngineerFileList(@Validated(ValidationGroups.Select.class) QqchWeightEngineerFile qqchWeightEngineerFileParam) {
        QqchWeightEngineerFileVo vo = qqchWeightEngineerFileService.getQqchWeightEngineerFileList(qqchWeightEngineerFileParam);
        return AjaxResult.success(vo);
    }

    @PreAuthorize(hasPermi = "qqchWeightEngineerFile:save")
    @PostMapping("/save")
    public AjaxResult insertQqchWeightEngineerFileList(@Validated(ValidationGroups.Save.class) @RequestBody QqchWeightEngineerFileVo vo) {
        qqchWeightEngineerFileService.save(vo);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchWeightEngineerFile:list")
    @GetMapping
    public AjaxResult getQqchWeightEngineerFile(@Validated(ValidationGroups.Get.class) QqchWeightEngineerFile qqchWeightEngineerFileParam) {
        QqchWeightEngineerFile qqchWeightEngineerFile = qqchWeightEngineerFileService.getQqchWeightEngineerFile(qqchWeightEngineerFileParam);
        return AjaxResult.success(qqchWeightEngineerFile);
    }

    @PreAuthorize(hasPermi = "qqchWeightEngineerFile:add")
    @PostMapping("/add")
    public AjaxResult insertQqchWeightEngineerFile(@Validated(ValidationGroups.Save.class) @RequestBody QqchWeightEngineerFile qqchWeightEngineerFileParam) {
        qqchWeightEngineerFileService.insertQqchWeightEngineerFile(qqchWeightEngineerFileParam);
        return AjaxResult.success(qqchWeightEngineerFileParam);
    }


    @PreAuthorize(hasPermi = "qqchWeightEngineerFile:update")
    @PostMapping("/update")
    public AjaxResult updateQqchWeightEngineerFile(@Validated(ValidationGroups.Update.class) @RequestBody QqchWeightEngineerFile qqchWeightEngineerFileParam) {
        return toAjax(qqchWeightEngineerFileService.updateQqchWeightEngineerFile(qqchWeightEngineerFileParam));
    }

    @PreAuthorize(hasPermi = "qqchWeightEngineerFile:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchWeightEngineerFileList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchWeightEngineerFile> qqchWeightEngineerFileListParam) {
        return toAjax(qqchWeightEngineerFileService.updateQqchWeightEngineerFileList(qqchWeightEngineerFileListParam));
    }

    @PreAuthorize(hasPermi = "qqchWeightEngineerFile:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchWeightEngineerFile(@Validated(ValidationGroups.Delete.class) @RequestBody QqchWeightEngineerFile qqchWeightEngineerFileParam) {
        return toAjax(qqchWeightEngineerFileService.deleteQqchWeightEngineerFile(qqchWeightEngineerFileParam));
    }

    @PreAuthorize(hasPermi = "qqchWeightEngineerFile:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchWeightEngineerFileByPks(@PathVariable Long[] ids) {
        List<Long> qqchWeightEngineerFilePkList = Arrays.asList(ids);
        return toAjax(qqchWeightEngineerFileService.deleteQqchWeightEngineerFileByPks(qqchWeightEngineerFilePkList));
    }

}
