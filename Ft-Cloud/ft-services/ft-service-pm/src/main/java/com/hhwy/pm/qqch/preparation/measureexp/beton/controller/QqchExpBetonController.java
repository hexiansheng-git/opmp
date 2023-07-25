package com.hhwy.pm.qqch.preparation.measureexp.beton.controller;

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
import com.hhwy.pm.qqch.preparation.measureexp.beton.service.IQqchExpBetonService;
import com.hhwy.pm.qqch.preparation.measureexp.beton.domain.QqchExpBeton;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-07-25 18:31:38
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchExpBeton")
public class QqchExpBetonController extends BaseController{

    @Autowired
    private IQqchExpBetonService qqchExpBetonService;

                                                                                                                                                                                                                                                                                                                                                    

    @PreAuthorize(hasPermi = "qqchExpBeton:list")
    @GetMapping
    public AjaxResult getQqchExpBeton(@Validated(ValidationGroups.Get.class)  QqchExpBeton qqchExpBetonParam){
        QqchExpBeton qqchExpBeton =  qqchExpBetonService.getQqchExpBeton(qqchExpBetonParam);
        return AjaxResult.success(qqchExpBeton);
    }

    @PreAuthorize(hasPermi = "qqchExpBeton:list")
    @GetMapping("/list")
    public AjaxResult getQqchExpBetonList(@Validated(ValidationGroups.Select.class) QqchExpBeton qqchExpBetonParam){
        startPage();
        List<QqchExpBeton> qqchExpBetonList = qqchExpBetonService.getQqchExpBetonList(qqchExpBetonParam);
        return getDataTableAjaxResult(qqchExpBetonList);
    }

    @PreAuthorize(hasPermi = "qqchExpBeton:add")
    @PostMapping("/add")
    public AjaxResult insertQqchExpBeton(@Validated(ValidationGroups.Save.class) @RequestBody QqchExpBeton qqchExpBetonParam){
        qqchExpBetonService.insertQqchExpBeton(qqchExpBetonParam);
        return AjaxResult.success(qqchExpBetonParam);
    }

    @PreAuthorize(hasPermi = "qqchExpBeton:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchExpBetonList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchExpBeton> qqchExpBetonListParam){
        qqchExpBetonService.insertQqchExpBetonList(qqchExpBetonListParam);
        return AjaxResult.success(qqchExpBetonListParam);
    }

    @PreAuthorize(hasPermi = "qqchExpBeton:update")
    @PostMapping("/update")
    public AjaxResult updateQqchExpBeton(@Validated(ValidationGroups.Update.class) @RequestBody QqchExpBeton qqchExpBetonParam){
        return toAjax(qqchExpBetonService.updateQqchExpBeton(qqchExpBetonParam));
    }

            @PreAuthorize(hasPermi = "qqchExpBeton:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchExpBetonList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchExpBeton> qqchExpBetonListParam){
            return toAjax(qqchExpBetonService.updateQqchExpBetonList(qqchExpBetonListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchExpBeton:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchExpBeton(@Validated(ValidationGroups.Delete.class) @RequestBody QqchExpBeton qqchExpBetonParam){
        return toAjax(qqchExpBetonService.deleteQqchExpBeton(qqchExpBetonParam));
    }

            @PreAuthorize(hasPermi = "qqchExpBeton:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchExpBetonByPks(@PathVariable Long[] ids){
            List<Long> qqchExpBetonPkList = Arrays.asList(ids);
            return toAjax(qqchExpBetonService.deleteQqchExpBetonByPks(qqchExpBetonPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchExpBeton qqchExpBetonParam) throws IOException {
        List<QqchExpBeton> qqchExpBetonList = qqchExpBetonService.getQqchExpBetonList(qqchExpBetonParam);
        ExcelUtils<QqchExpBeton> util = new ExcelUtils<>(QqchExpBeton.class);
        util.exportExcel(response, qqchExpBetonList, DateUtils.getDate());
    }
}
