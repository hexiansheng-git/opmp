package com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.QqchSameEngineerFile;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.vo.QqchSameEngineerFileVo;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.service.IQqchSameEngineerFileService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-07 09:52:57
 * @remark   9.7.2 一般工程档案
 */
@Validated
@RestController
@RequestMapping("/qqchSameEngineerFile")
public class QqchSameEngineerFileController extends BaseController{

    @Autowired
    private IQqchSameEngineerFileService qqchSameEngineerFileService;

    /**
     *  列表接口
     * @param qqchSameEngineerFileParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSameEngineerFile:list")
    @GetMapping("/list")
    public AjaxResult getQqchSameEngineerFileList(@Validated(ValidationGroups.Select.class) QqchSameEngineerFile qqchSameEngineerFileParam){
        QqchSameEngineerFileVo vo = qqchSameEngineerFileService.getQqchSameEngineerFileList(qqchSameEngineerFileParam);
        return AjaxResult.success(vo);
    }

    @PreAuthorize(hasPermi = "qqchSameEngineerFile:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchSameEngineerFileVo vo){
        qqchSameEngineerFileService.save(vo);
        return AjaxResult.success();
    }






    @PreAuthorize(hasPermi = "qqchSameEngineerFile:list")
    @GetMapping
    public AjaxResult getQqchSameEngineerFile(@Validated(ValidationGroups.Get.class)  QqchSameEngineerFile qqchSameEngineerFileParam){
        QqchSameEngineerFile qqchSameEngineerFile =  qqchSameEngineerFileService.getQqchSameEngineerFile(qqchSameEngineerFileParam);
        return AjaxResult.success(qqchSameEngineerFile);
    }

    @PreAuthorize(hasPermi = "qqchSameEngineerFile:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSameEngineerFile(@Validated(ValidationGroups.Save.class) @RequestBody QqchSameEngineerFile qqchSameEngineerFileParam){
        qqchSameEngineerFileService.insertQqchSameEngineerFile(qqchSameEngineerFileParam);
        return AjaxResult.success(qqchSameEngineerFileParam);
    }



    @PreAuthorize(hasPermi = "qqchSameEngineerFile:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSameEngineerFile(@Validated(ValidationGroups.Update.class) @RequestBody QqchSameEngineerFile qqchSameEngineerFileParam){
        return toAjax(qqchSameEngineerFileService.updateQqchSameEngineerFile(qqchSameEngineerFileParam));
    }

            @PreAuthorize(hasPermi = "qqchSameEngineerFile:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchSameEngineerFileList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSameEngineerFile> qqchSameEngineerFileListParam){
            return toAjax(qqchSameEngineerFileService.updateQqchSameEngineerFileList(qqchSameEngineerFileListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchSameEngineerFile:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSameEngineerFile(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSameEngineerFile qqchSameEngineerFileParam){
        return toAjax(qqchSameEngineerFileService.deleteQqchSameEngineerFile(qqchSameEngineerFileParam));
    }

            @PreAuthorize(hasPermi = "qqchSameEngineerFile:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchSameEngineerFileByPks(@PathVariable Long[] ids){
            List<Long> qqchSameEngineerFilePkList = Arrays.asList(ids);
            return toAjax(qqchSameEngineerFileService.deleteQqchSameEngineerFileByPks(qqchSameEngineerFilePkList));
        }
}
