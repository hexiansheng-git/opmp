package com.hhwy.pm.qqch.sgch.qqchconst.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.common.domain.CompileDTO;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 16:08:05
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchConst")
public class QqchConstController extends BaseController {

    @Autowired
    private IQqchConstService qqchConstService;


    @PreAuthorize(hasPermi = "qqchConst:list")
    @GetMapping
    public AjaxResult getQqchConst(@Validated(ValidationGroups.Get.class) QqchConst qqchConstParam) {
        QqchConst qqchConst = qqchConstService.getQqchConst(qqchConstParam);
        return AjaxResult.success(qqchConst);
    }

    @PreAuthorize(hasPermi = "qqchConst:list")
    @GetMapping("/list")
    public AjaxResult getQqchConstList(@Validated(ValidationGroups.Select.class) CompileDTO<QqchConst> dto) {
        QqchConst qqchConst = CompileDTO.dealListDto(dto.getVersion(), new QqchConst());
        List<QqchConst> qqchConstList = qqchConstService.list(qqchConst);
        return AjaxResult.success(qqchConstList);
    }

    @PreAuthorize(hasPermi = "qqchConst:add")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody CompileDTO<List<QqchConst>> dtoList) {
        List<QqchConst> qqchConsts = dtoList.dealSaveDto();
        qqchConstService.save(qqchConsts);
        return AjaxResult.success(qqchConsts);
    }

    @PreAuthorize(hasPermi = "qqchConst:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchConstList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchConst> qqchConstListParam) {
        qqchConstService.insertQqchConstList(qqchConstListParam);
        return AjaxResult.success(qqchConstListParam);
    }

    @PreAuthorize(hasPermi = "qqchConst:update")
    @PostMapping("/update")
    public AjaxResult updateQqchConst(@Validated(ValidationGroups.Update.class) @RequestBody QqchConst qqchConstParam) {
        return toAjax(qqchConstService.updateQqchConst(qqchConstParam));
    }

    @PreAuthorize(hasPermi = "qqchConst:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchConstList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchConst> qqchConstListParam) {
        return toAjax(qqchConstService.updateQqchConstList(qqchConstListParam));
    }

    @PreAuthorize(hasPermi = "qqchConst:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchConst(@Validated(ValidationGroups.Delete.class) @RequestBody QqchConst qqchConstParam) {
        return toAjax(qqchConstService.deleteQqchConst(qqchConstParam));
    }

    @PreAuthorize(hasPermi = "qqchConst:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchConstByPks(@PathVariable Long[] ids) {
        List<Long> qqchConstPkList = Arrays.asList(ids);
        return toAjax(qqchConstService.deleteQqchConstByPks(qqchConstPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchConst qqchConstParam) throws IOException {
        List<QqchConst> qqchConstList = qqchConstService.getQqchConstList(qqchConstParam);
        ExcelUtils<QqchConst> util = new ExcelUtils<>(QqchConst.class);
        util.exportExcel(response, qqchConstList, DateUtils.getDate());
    }


}
