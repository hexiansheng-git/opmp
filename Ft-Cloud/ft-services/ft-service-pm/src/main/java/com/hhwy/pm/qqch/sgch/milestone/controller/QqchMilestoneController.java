package com.hhwy.pm.qqch.sgch.milestone.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.milestone.domain.QqchMilestone;
import com.hhwy.pm.qqch.sgch.milestone.service.IQqchMilestoneService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.validation.ValidationGroups;
import jdk.nashorn.internal.runtime.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 11:18:27
 * @remark
 */
@Validated
@RestController
@RequestMapping("/milestone")
public class QqchMilestoneController extends BaseController {

    @Autowired
    private IQqchMilestoneService qqchMilestoneService;


    @PreAuthorize(hasPermi = "qqchMilestone:list")
    @GetMapping
    public AjaxResult getQqchMilestone(@Validated(ValidationGroups.Get.class) CompileEntity<QqchMilestone> qqchMilestoneParam) {
        QqchMilestone qqchMilestone = qqchMilestoneService.getQqchMilestone(qqchMilestoneParam.dealListDto());
        return AjaxResult.success(qqchMilestone);
    }

    @PreAuthorize(hasPermi = "qqchMilestone:list")
    @GetMapping("/list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) QqchMilestone qqchMilestoneParam) {
        BigDecimal version = VersionUtil.getVersion(QqchMilestone.TABLE_NAME, qqchMilestoneParam.getVersion());
        qqchMilestoneParam.setVersion(version);
        startPage();
        List<QqchMilestone> qqchMilestoneList = qqchMilestoneService.getQqchMilestoneList(qqchMilestoneParam);

        CompileEntity returnVo = qqchMilestoneService.list(qqchMilestoneParam);
        returnVo.setDto(qqchMilestoneList);
        return AjaxResult.success(returnVo);
    }

    @PreAuthorize(hasPermi = "qqchMilestone:add")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody CompileEntity<List<QqchMilestone>> dtoList) {
        List<QqchMilestone> dto = dtoList.dealSaveDto();
        qqchMilestoneService.save(dto);
        return AjaxResult.success(dto);
    }

    @PreAuthorize(hasPermi = "qqchMilestone:add")
    @PostMapping("/saveDataFromMainP6")
    public AjaxResult saveDataFromMainP6(@Validated(ValidationGroups.Select.class) QqchMilestone qqchMilestoneParam) {
        List<QqchMilestone> dto = qqchMilestoneService.saveDataFromMainP6(qqchMilestoneParam);
        return AjaxResult.success(dto);
    }

    @PreAuthorize(hasPermi = "qqchMilestone:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchMilestoneList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchMilestone> qqchMilestoneListParam) {
        qqchMilestoneService.insertQqchMilestoneList(qqchMilestoneListParam);
        return AjaxResult.success(qqchMilestoneListParam);
    }

    @PreAuthorize(hasPermi = "qqchMilestone:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMilestone(@Validated(ValidationGroups.Update.class) @RequestBody QqchMilestone qqchMilestoneParam) {
        return toAjax(qqchMilestoneService.updateQqchMilestone(qqchMilestoneParam));
    }

    @PreAuthorize(hasPermi = "qqchMilestone:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchMilestoneList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMilestone> qqchMilestoneListParam) {
        return toAjax(qqchMilestoneService.updateQqchMilestoneList(qqchMilestoneListParam));
    }

    @PreAuthorize(hasPermi = "qqchMilestone:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMilestone(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMilestone qqchMilestoneParam) {
        return toAjax(qqchMilestoneService.deleteQqchMilestone(qqchMilestoneParam));
    }

    @PreAuthorize(hasPermi = "qqchMilestone:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchMilestoneByPks(@PathVariable Long[] ids) {
        List<Long> qqchMilestonePkList = Arrays.asList(ids);
        return toAjax(qqchMilestoneService.deleteQqchMilestoneByPks(qqchMilestonePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMilestone qqchMilestoneParam) throws IOException {
        List<QqchMilestone> qqchMilestoneList = qqchMilestoneService.getQqchMilestoneList(qqchMilestoneParam);
        ExcelUtils<QqchMilestone> util = new ExcelUtils<>(QqchMilestone.class);
        util.exportExcel(response, qqchMilestoneList, DateUtils.getDate());
    }
}
