package com.hhwy.pm.qqch.preparation.costControl.breakEvenPoint.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.costControl.breakEvenPoint.domain.QqchProjectBreakEvenPoint;
import com.hhwy.pm.qqch.preparation.costControl.breakEvenPoint.domain.vo.QqchProjectBreakEvenPointVo;
import com.hhwy.pm.qqch.preparation.costControl.breakEvenPoint.service.IQqchProjectBreakEvenPointService;
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
 * @date 2023-08-03 13:39:59
 * @remark 项目主要盈亏点分析
 */
@Validated
@RestController
@RequestMapping("/qqchProjectBreakEvenPoint")
public class QqchProjectBreakEvenPointController extends BaseController {

    @Autowired
    private IQqchProjectBreakEvenPointService qqchProjectBreakEvenPointService;


    @PreAuthorize(hasPermi = "qqchProjectBreakEvenPoint:list")
    @GetMapping
    public AjaxResult getQqchProjectBreakEvenPoint(@Validated(ValidationGroups.Get.class) QqchProjectBreakEvenPoint qqchProjectBreakEvenPointParam) {
        QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint = qqchProjectBreakEvenPointService.getQqchProjectBreakEvenPoint(qqchProjectBreakEvenPointParam);
        return AjaxResult.success(qqchProjectBreakEvenPoint);
    }

    @PreAuthorize(hasPermi = "qqchProjectBreakEvenPoint:list")
    @GetMapping("/list")
    public AjaxResult getQqchProjectBreakEvenPointList(@Validated(ValidationGroups.Select.class) QqchProjectBreakEvenPoint qqchProjectBreakEvenPointParam) {
        startPage();
        List<QqchProjectBreakEvenPoint> qqchProjectBreakEvenPointList = qqchProjectBreakEvenPointService.getQqchProjectBreakEvenPointList(qqchProjectBreakEvenPointParam);
        return getDataTableAjaxResult(qqchProjectBreakEvenPointList);
    }

    @PreAuthorize(hasPermi = "qqchProjectBreakEvenPoint:add")
    @PostMapping("/add")
    public AjaxResult insertQqchProjectBreakEvenPoint(@Validated(ValidationGroups.Save.class) @RequestBody QqchProjectBreakEvenPoint qqchProjectBreakEvenPointParam) {
        qqchProjectBreakEvenPointService.insertQqchProjectBreakEvenPoint(qqchProjectBreakEvenPointParam);
        return AjaxResult.success(qqchProjectBreakEvenPointParam);
    }

    @PreAuthorize(hasPermi = "qqchProjectBreakEvenPoint:update")
    @PostMapping("/update")
    public AjaxResult updateQqchProjectBreakEvenPoint(@Validated(ValidationGroups.Update.class) @RequestBody QqchProjectBreakEvenPoint qqchProjectBreakEvenPointParam) {
        return toAjax(qqchProjectBreakEvenPointService.updateQqchProjectBreakEvenPoint(qqchProjectBreakEvenPointParam));
    }

    @PreAuthorize(hasPermi = "qqchProjectBreakEvenPoint:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchProjectBreakEvenPointList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchProjectBreakEvenPoint> qqchProjectBreakEvenPointListParam) {
        return toAjax(qqchProjectBreakEvenPointService.updateQqchProjectBreakEvenPointList(qqchProjectBreakEvenPointListParam));
    }

    @PreAuthorize(hasPermi = "qqchProjectBreakEvenPoint:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchProjectBreakEvenPoint(@Validated(ValidationGroups.Delete.class) @RequestBody QqchProjectBreakEvenPoint qqchProjectBreakEvenPointParam) {
        return toAjax(qqchProjectBreakEvenPointService.deleteQqchProjectBreakEvenPoint(qqchProjectBreakEvenPointParam));
    }

    @PreAuthorize(hasPermi = "qqchProjectBreakEvenPoint:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchProjectBreakEvenPointByPks(@PathVariable Long[] ids) {
        List<Long> qqchProjectBreakEvenPointPkList = Arrays.asList(ids);
        return toAjax(qqchProjectBreakEvenPointService.deleteQqchProjectBreakEvenPointByPks(qqchProjectBreakEvenPointPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchProjectBreakEvenPoint qqchProjectBreakEvenPointParam) throws IOException {
        List<QqchProjectBreakEvenPoint> qqchProjectBreakEvenPointList = qqchProjectBreakEvenPointService.getQqchProjectBreakEvenPointList(qqchProjectBreakEvenPointParam);
        ExcelUtils<QqchProjectBreakEvenPoint> util = new ExcelUtils<>(QqchProjectBreakEvenPoint.class);
        util.exportExcel(response, qqchProjectBreakEvenPointList, DateUtils.getDate());
    }

    /**
     * 获取项目主要盈亏点分析Vo
     * @param qqchProjectBreakEvenPoint
     * @return
     */
    @PreAuthorize(hasPermi = "qqchProjectBreakEvenPoint:list")
    @GetMapping("getQqchProjectBreakEvenPointVo")
    public AjaxResult getQqchProjectBreakEvenPointVo(@Validated(ValidationGroups.Get.class) QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint) {
        QqchProjectBreakEvenPointVo qqchProjectBreakEvenPointVo = qqchProjectBreakEvenPointService.getQqchProjectBreakEvenPointVo(qqchProjectBreakEvenPoint);
        return AjaxResult.success(qqchProjectBreakEvenPointVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchProjectBreakEvenPointVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchProjectBreakEvenPoint:add")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody QqchProjectBreakEvenPointVo qqchProjectBreakEvenPointVo) {
        qqchProjectBreakEvenPointService.save(qqchProjectBreakEvenPointVo);
        return AjaxResult.success(qqchProjectBreakEvenPointVo);
    }
}
