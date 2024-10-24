package com.hhwy.sp.techFile.sgjsCheckDataCatalog.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.techFile.sgjsCheckDataCatalog.service.ISgjsCheckDataCatalogService;
import com.hhwy.sp.techFile.sgjsCheckDataCatalog.domain.SgjsCheckDataCatalog;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;

/**
 * @author xuzl
 * @date 2024-10-18 16:45:49
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/sgjsCheckDataCatalog")
public class SgjsCheckDataCatalogController extends BaseController{

    @Autowired
    private ISgjsCheckDataCatalogService sgjsCheckDataCatalogService;

                                                                                                                                                                                                                                                                                                                            
    @GetMapping
    public AjaxResult getSgjsCheckDataCatalog(@Validated(ValidationGroups.Get.class) SgjsCheckDataCatalog sgjsCheckDataCatalogParam){
        SgjsCheckDataCatalog sgjsCheckDataCatalog =  sgjsCheckDataCatalogService.getSgjsCheckDataCatalog(sgjsCheckDataCatalogParam);
        return AjaxResult.success(sgjsCheckDataCatalog);
    }

    @GetMapping("/list")
    public AjaxResult getSgjsCheckDataCatalogList(@Validated(ValidationGroups.Select.class) SgjsCheckDataCatalog sgjsCheckDataCatalogParam){
        startPage();
        List<SgjsCheckDataCatalog> sgjsCheckDataCatalogList = sgjsCheckDataCatalogService.getSgjsCheckDataCatalogList(sgjsCheckDataCatalogParam);
        return getDataTableAjaxResult(sgjsCheckDataCatalogList);
    }

    @PostMapping("/add")
    public AjaxResult insertSgjsCheckDataCatalog(@Validated(ValidationGroups.Save.class) @RequestBody SgjsCheckDataCatalog sgjsCheckDataCatalogParam){
        sgjsCheckDataCatalogService.insertSgjsCheckDataCatalog(sgjsCheckDataCatalogParam);
        return AjaxResult.success(sgjsCheckDataCatalogParam);
    }

    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsCheckDataCatalogList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsCheckDataCatalog> sgjsCheckDataCatalogListParam){
        sgjsCheckDataCatalogService.insertSgjsCheckDataCatalogList(sgjsCheckDataCatalogListParam);
        return AjaxResult.success(sgjsCheckDataCatalogListParam);
    }

    @PostMapping("/update")
    public AjaxResult updateSgjsCheckDataCatalog(@Validated(ValidationGroups.Update.class) @RequestBody SgjsCheckDataCatalog sgjsCheckDataCatalogParam){
        return toAjax(sgjsCheckDataCatalogService.updateSgjsCheckDataCatalog(sgjsCheckDataCatalogParam));
    }

            @PostMapping("/batchUpdate")
        public AjaxResult updateSgjsCheckDataCatalogList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsCheckDataCatalog> sgjsCheckDataCatalogListParam){
            return toAjax(sgjsCheckDataCatalogService.updateSgjsCheckDataCatalogList(sgjsCheckDataCatalogListParam));
        }
    
    @PostMapping("/delete")
    public AjaxResult deleteSgjsCheckDataCatalog(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsCheckDataCatalog sgjsCheckDataCatalogParam){
        return toAjax(sgjsCheckDataCatalogService.deleteSgjsCheckDataCatalog(sgjsCheckDataCatalogParam));
    }

            @PostMapping("/{ids}")
        public AjaxResult deleteSgjsCheckDataCatalogByPks(@PathVariable Long[] ids){
            List<Long> sgjsCheckDataCatalogPkList = Arrays.asList(ids);
            return toAjax(sgjsCheckDataCatalogService.deleteSgjsCheckDataCatalogByPks(sgjsCheckDataCatalogPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsCheckDataCatalog sgjsCheckDataCatalogParam) throws IOException {
        List<SgjsCheckDataCatalog> sgjsCheckDataCatalogList = sgjsCheckDataCatalogService.getSgjsCheckDataCatalogList(sgjsCheckDataCatalogParam);
        ExcelUtils<SgjsCheckDataCatalog> util = new ExcelUtils<>(SgjsCheckDataCatalog.class);
        util.exportExcel(response, sgjsCheckDataCatalogList, DateUtils.getDate());
    }
}
