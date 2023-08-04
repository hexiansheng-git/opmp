package com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.domain.QqchTotalDemandTimeCount;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.service.IQqchTotalDemandTimeCountService;
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
 * @date 2023-08-02 10:55:17
 * @remark  1.6 物资总需计划
 */
@Validated
@RestController
@RequestMapping("/qqchTotalDemandTimeCount")
public class QqchTotalDemandTimeCountController extends BaseController{

    @Autowired
    private IQqchTotalDemandTimeCountService qqchTotalDemandTimeCountService;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    

    @PreAuthorize(hasPermi = "qqchTotalDemandTimeCount:list")
    @GetMapping
    public AjaxResult getQqchTotalDemandTimeCount(@Validated(ValidationGroups.Get.class)  QqchTotalDemandTimeCount qqchTotalDemandTimeCountParam){
        QqchTotalDemandTimeCount qqchTotalDemandTimeCount =  qqchTotalDemandTimeCountService.getQqchTotalDemandTimeCount(qqchTotalDemandTimeCountParam);
        return AjaxResult.success(qqchTotalDemandTimeCount);
    }

    @PreAuthorize(hasPermi = "qqchTotalDemandTimeCount:list")
    @GetMapping("/list")
    public AjaxResult getQqchTotalDemandTimeCountList(@Validated(ValidationGroups.Select.class) QqchTotalDemandTimeCount qqchTotalDemandTimeCountParam){
        startPage();
        List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCountList = qqchTotalDemandTimeCountService.getQqchTotalDemandTimeCountList(qqchTotalDemandTimeCountParam);
        return getDataTableAjaxResult(qqchTotalDemandTimeCountList);
    }

    @PreAuthorize(hasPermi = "qqchTotalDemandTimeCount:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTotalDemandTimeCount(@Validated(ValidationGroups.Save.class) @RequestBody QqchTotalDemandTimeCount qqchTotalDemandTimeCountParam){
        qqchTotalDemandTimeCountService.insertQqchTotalDemandTimeCount(qqchTotalDemandTimeCountParam);
        return AjaxResult.success(qqchTotalDemandTimeCountParam);
    }

    @PreAuthorize(hasPermi = "qqchTotalDemandTimeCount:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTotalDemandTimeCountList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCountListParam){
        qqchTotalDemandTimeCountService.insertQqchTotalDemandTimeCountList(qqchTotalDemandTimeCountListParam);
        return AjaxResult.success(qqchTotalDemandTimeCountListParam);
    }

    @PreAuthorize(hasPermi = "qqchTotalDemandTimeCount:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTotalDemandTimeCount(@Validated(ValidationGroups.Update.class) @RequestBody QqchTotalDemandTimeCount qqchTotalDemandTimeCountParam){
        return toAjax(qqchTotalDemandTimeCountService.updateQqchTotalDemandTimeCount(qqchTotalDemandTimeCountParam));
    }

            @PreAuthorize(hasPermi = "qqchTotalDemandTimeCount:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchTotalDemandTimeCountList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCountListParam){
            return toAjax(qqchTotalDemandTimeCountService.updateQqchTotalDemandTimeCountList(qqchTotalDemandTimeCountListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchTotalDemandTimeCount:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTotalDemandTimeCount(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTotalDemandTimeCount qqchTotalDemandTimeCountParam){
        return toAjax(qqchTotalDemandTimeCountService.deleteQqchTotalDemandTimeCount(qqchTotalDemandTimeCountParam));
    }

            @PreAuthorize(hasPermi = "qqchTotalDemandTimeCount:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchTotalDemandTimeCountByPks(@PathVariable Long[] ids){
            List<Long> qqchTotalDemandTimeCountPkList = Arrays.asList(ids);
            return toAjax(qqchTotalDemandTimeCountService.deleteQqchTotalDemandTimeCountByPks(qqchTotalDemandTimeCountPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTotalDemandTimeCount qqchTotalDemandTimeCountParam) throws IOException {
        List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCountList = qqchTotalDemandTimeCountService.getQqchTotalDemandTimeCountList(qqchTotalDemandTimeCountParam);
        ExcelUtils<QqchTotalDemandTimeCount> util = new ExcelUtils<>(QqchTotalDemandTimeCount.class);
        util.exportExcel(response, qqchTotalDemandTimeCountList, DateUtils.getDate());
    }
}
