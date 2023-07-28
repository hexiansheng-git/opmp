package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchPigeonholeDutyDivision;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.vo.QqchPigeonholeDutyDivisionVo;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.service.IQqchPigeonholeDutyDivisionService;
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
 * @date 2023-07-25 10:48:27
 * @remark 技术档案归档责任分工
 */
@Validated
@RestController
@RequestMapping("/qqchPigeonholeDutyDivision")
public class QqchPigeonholeDutyDivisionController extends BaseController {

    @Autowired
    private IQqchPigeonholeDutyDivisionService qqchPigeonholeDutyDivisionService;


    @PreAuthorize(hasPermi = "qqchPigeonholeDutyDivision:list")
    @GetMapping
    public AjaxResult getQqchPigeonholeDutyDivision(@Validated(ValidationGroups.Get.class) QqchPigeonholeDutyDivision qqchPigeonholeDutyDivisionParam) {
        QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision = qqchPigeonholeDutyDivisionService.getQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivisionParam);
        return AjaxResult.success(qqchPigeonholeDutyDivision);
    }

    @PreAuthorize(hasPermi = "qqchPigeonholeDutyDivision:list")
    @GetMapping("/list")
    public AjaxResult getQqchPigeonholeDutyDivisionList(@Validated(ValidationGroups.Select.class) QqchPigeonholeDutyDivision qqchPigeonholeDutyDivisionParam) {
        startPage();
        List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList = qqchPigeonholeDutyDivisionService.getQqchPigeonholeDutyDivisionList(qqchPigeonholeDutyDivisionParam);
        return getDataTableAjaxResult(qqchPigeonholeDutyDivisionList);
    }

    @PreAuthorize(hasPermi = "qqchPigeonholeDutyDivision:add")
    @PostMapping("/add")
    public AjaxResult insertQqchPigeonholeDutyDivision(@Validated(ValidationGroups.Save.class) @RequestBody QqchPigeonholeDutyDivision qqchPigeonholeDutyDivisionParam) {
        qqchPigeonholeDutyDivisionService.insertQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivisionParam);
        return AjaxResult.success(qqchPigeonholeDutyDivisionParam);
    }

    @PreAuthorize(hasPermi = "qqchPigeonholeDutyDivision:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchPigeonholeDutyDivisionList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionListParam) {
        qqchPigeonholeDutyDivisionService.insertQqchPigeonholeDutyDivisionList(qqchPigeonholeDutyDivisionListParam);
        return AjaxResult.success(qqchPigeonholeDutyDivisionListParam);
    }

    @PreAuthorize(hasPermi = "qqchPigeonholeDutyDivision:update")
    @PostMapping("/update")
    public AjaxResult updateQqchPigeonholeDutyDivision(@Validated(ValidationGroups.Update.class) @RequestBody QqchPigeonholeDutyDivision qqchPigeonholeDutyDivisionParam) {
        return toAjax(qqchPigeonholeDutyDivisionService.updateQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivisionParam));
    }

    @PreAuthorize(hasPermi = "qqchPigeonholeDutyDivision:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchPigeonholeDutyDivisionList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionListParam) {
        return toAjax(qqchPigeonholeDutyDivisionService.updateQqchPigeonholeDutyDivisionList(qqchPigeonholeDutyDivisionListParam));
    }

    @PreAuthorize(hasPermi = "qqchPigeonholeDutyDivision:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchPigeonholeDutyDivision(@Validated(ValidationGroups.Delete.class) @RequestBody QqchPigeonholeDutyDivision qqchPigeonholeDutyDivisionParam) {
        return toAjax(qqchPigeonholeDutyDivisionService.deleteQqchPigeonholeDutyDivision(qqchPigeonholeDutyDivisionParam));
    }

    @PreAuthorize(hasPermi = "qqchPigeonholeDutyDivision:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchPigeonholeDutyDivisionByPks(@PathVariable Long[] ids) {
        List<Long> qqchPigeonholeDutyDivisionPkList = Arrays.asList(ids);
        return toAjax(qqchPigeonholeDutyDivisionService.deleteQqchPigeonholeDutyDivisionByPks(qqchPigeonholeDutyDivisionPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchPigeonholeDutyDivision qqchPigeonholeDutyDivisionParam) throws IOException {
        List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList = qqchPigeonholeDutyDivisionService.getQqchPigeonholeDutyDivisionList(qqchPigeonholeDutyDivisionParam);
        ExcelUtils<QqchPigeonholeDutyDivision> util = new ExcelUtils<>(QqchPigeonholeDutyDivision.class);
        util.exportExcel(response, qqchPigeonholeDutyDivisionList, DateUtils.getDate());
    }

    /**
     * 获取技术档案归档责任分工Vo
     * @param qqchPigeonholeDutyDivision
     * @return
     */
    @PreAuthorize(hasPermi = "qqchPigeonholeDutyDivision:list")
    @GetMapping("getQqchPigeonholeDutyDivisionVo")
    public AjaxResult getQqchPigeonholeDutyDivisionVo(@Validated(ValidationGroups.Get.class) QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision) {
        QqchPigeonholeDutyDivisionVo qqchPigeonholeDutyDivisionVo = qqchPigeonholeDutyDivisionService.getQqchPigeonholeDutyDivisionVo(qqchPigeonholeDutyDivision);
        return AjaxResult.success(qqchPigeonholeDutyDivisionVo);
    }
}
