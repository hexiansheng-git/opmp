package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.vo.QqchSafeMostEnvirRiskListVo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service.IQqchSafeMostEnvirRiskListService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskList;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author zq
 * @date 2023-08-14 14:04:02
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchSafeMostEnvirRiskList")
public class QqchSafeMostEnvirRiskListController extends BaseController {

    @Autowired
    private IQqchSafeMostEnvirRiskListService qqchSafeMostEnvirRiskListService;


    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskList:list")
    @GetMapping
    public AjaxResult getQqchSafeMostEnvirRiskList(@Validated(ValidationGroups.Get.class) QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskListParam) {
        QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList = qqchSafeMostEnvirRiskListService.getQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskListParam);
        return AjaxResult.success(qqchSafeMostEnvirRiskList);
    }

    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskList:list")
    @GetMapping("/list")
    public AjaxResult getQqchSafeMostEnvirRiskListList(@Validated(ValidationGroups.Select.class) QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskListParam) {
        startPage();
        List<QqchSafeMostEnvirRiskList> qqchSafeMostEnvirRiskListList = qqchSafeMostEnvirRiskListService.getQqchSafeMostEnvirRiskListList(qqchSafeMostEnvirRiskListParam);
        return getDataTableAjaxResult(qqchSafeMostEnvirRiskListList);
    }

    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskList:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSafeMostEnvirRiskList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskListParam) {
        qqchSafeMostEnvirRiskListService.insertQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskListParam);
        return AjaxResult.success(qqchSafeMostEnvirRiskListParam);
    }

    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskList:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSafeMostEnvirRiskListList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeMostEnvirRiskListVo qqchSafeMostEnvirRiskListVo) {
        qqchSafeMostEnvirRiskListService.insertQqchSafeMostEnvirRiskListList(qqchSafeMostEnvirRiskListVo);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskList:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSafeMostEnvirRiskList(@Validated(ValidationGroups.Update.class) @RequestBody QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskListParam) {
        return toAjax(qqchSafeMostEnvirRiskListService.updateQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskListParam));
    }

    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskList:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSafeMostEnvirRiskListList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSafeMostEnvirRiskList> qqchSafeMostEnvirRiskListListParam) {
        return toAjax(qqchSafeMostEnvirRiskListService.updateQqchSafeMostEnvirRiskListList(qqchSafeMostEnvirRiskListListParam));
    }

    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSafeMostEnvirRiskList(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskListParam) {
        return toAjax(qqchSafeMostEnvirRiskListService.deleteQqchSafeMostEnvirRiskList(qqchSafeMostEnvirRiskListParam));
    }

    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskList:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSafeMostEnvirRiskListByPks(@PathVariable Long[] ids) {
        List<Long> qqchSafeMostEnvirRiskListPkList = Arrays.asList(ids);
        return toAjax(qqchSafeMostEnvirRiskListService.deleteQqchSafeMostEnvirRiskListByPks(qqchSafeMostEnvirRiskListPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskListParam) throws IOException {
        List<QqchSafeMostEnvirRiskList> qqchSafeMostEnvirRiskListList = qqchSafeMostEnvirRiskListService.getQqchSafeMostEnvirRiskListList(qqchSafeMostEnvirRiskListParam);
        ExcelUtils<QqchSafeMostEnvirRiskList> util = new ExcelUtils<>(QqchSafeMostEnvirRiskList.class);
        util.exportExcel(response, qqchSafeMostEnvirRiskListList, DateUtils.getDate());
    }

     @PreAuthorize(hasAnyPermi = "qqchSafeMostEnvirRiskList:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version){
        QqchSafeMostEnvirRiskListVo qqchSafeEnvirRiskListVo = qqchSafeMostEnvirRiskListService.getList(version);
        return AjaxResult.success(qqchSafeEnvirRiskListVo);
    }
}
