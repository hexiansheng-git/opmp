package com.hhwy.pm.jdgl.diff.analysis.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisPath;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisPathService;
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
 * @author 陈锦豪
 * @date 2023-08-28 16:24:21
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/jdglDiffAnalysisPath")
public class JdglDiffAnalysisPathController extends BaseController{

    @Autowired
    private IJdglDiffAnalysisPathService jdglDiffAnalysisPathService;

                                                                                                                                                                                                                                                                                                                                                                                                                                        

    @PreAuthorize(hasPermi = "jdglDiffAnalysisPath:list")
    @GetMapping
    public AjaxResult getJdglDiffAnalysisPath(@Validated(ValidationGroups.Get.class) JdglDiffAnalysisPath jdglDiffAnalysisPathParam){
        JdglDiffAnalysisPath jdglDiffAnalysisPath =  jdglDiffAnalysisPathService.getJdglDiffAnalysisPath(jdglDiffAnalysisPathParam);
        return AjaxResult.success(jdglDiffAnalysisPath);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisPath:list")
    @GetMapping("/list")
    public AjaxResult getJdglDiffAnalysisPathList(@Validated(ValidationGroups.Select.class) JdglDiffAnalysisPath jdglDiffAnalysisPathParam){
        startPage();
        List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList = jdglDiffAnalysisPathService.getJdglDiffAnalysisPathList(jdglDiffAnalysisPathParam);
        return getDataTableAjaxResult(jdglDiffAnalysisPathList);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisPath:add")
    @PostMapping("/add")
    public AjaxResult insertJdglDiffAnalysisPath(@Validated(ValidationGroups.Save.class) @RequestBody JdglDiffAnalysisPath jdglDiffAnalysisPathParam){
        jdglDiffAnalysisPathService.insertJdglDiffAnalysisPath(jdglDiffAnalysisPathParam);
        return AjaxResult.success(jdglDiffAnalysisPathParam);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisPath:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglDiffAnalysisPathList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglDiffAnalysisPath> jdglDiffAnalysisPathListParam){
        jdglDiffAnalysisPathService.insertJdglDiffAnalysisPathList(jdglDiffAnalysisPathListParam);
        return AjaxResult.success(jdglDiffAnalysisPathListParam);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisPath:update")
    @PostMapping("/update")
    public AjaxResult updateJdglDiffAnalysisPath(@Validated(ValidationGroups.Update.class) @RequestBody JdglDiffAnalysisPath jdglDiffAnalysisPathParam){
        return toAjax(jdglDiffAnalysisPathService.updateJdglDiffAnalysisPath(jdglDiffAnalysisPathParam));
    }

            @PreAuthorize(hasPermi = "jdglDiffAnalysisPath:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateJdglDiffAnalysisPathList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglDiffAnalysisPath> jdglDiffAnalysisPathListParam){
            return toAjax(jdglDiffAnalysisPathService.updateJdglDiffAnalysisPathList(jdglDiffAnalysisPathListParam));
        }
    
    @PreAuthorize(hasPermi = "jdglDiffAnalysisPath:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglDiffAnalysisPath(@Validated(ValidationGroups.Delete.class) @RequestBody JdglDiffAnalysisPath jdglDiffAnalysisPathParam){
        return toAjax(jdglDiffAnalysisPathService.deleteJdglDiffAnalysisPath(jdglDiffAnalysisPathParam));
    }

            @PreAuthorize(hasPermi = "jdglDiffAnalysisPath:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteJdglDiffAnalysisPathByPks(@PathVariable Long[] ids){
            List<Long> jdglDiffAnalysisPathPkList = Arrays.asList(ids);
            return toAjax(jdglDiffAnalysisPathService.deleteJdglDiffAnalysisPathByPks(jdglDiffAnalysisPathPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglDiffAnalysisPath jdglDiffAnalysisPathParam) throws IOException {
        List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList = jdglDiffAnalysisPathService.getJdglDiffAnalysisPathList(jdglDiffAnalysisPathParam);
        ExcelUtils<JdglDiffAnalysisPath> util = new ExcelUtils<>(JdglDiffAnalysisPath.class);
        util.exportExcel(response, jdglDiffAnalysisPathList, DateUtils.getDate());
    }
}
