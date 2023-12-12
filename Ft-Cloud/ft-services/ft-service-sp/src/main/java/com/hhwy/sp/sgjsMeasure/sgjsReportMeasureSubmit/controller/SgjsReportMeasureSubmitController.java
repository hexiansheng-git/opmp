package com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.controller;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManage;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManageVo;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.SgjsReportMeasureSubmitVo;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.service.ISgjsReportMeasureSubmitService;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.SgjsReportMeasureSubmit;

/**
 * 测量管理--测量报告提交
 *
 * @author zmh
 * @date 2023-12-08 16:19:52
 * @remark
 */
@RestController
@RequestMapping("/sgjsReportMeasureSubmit")
public class SgjsReportMeasureSubmitController extends BaseController {

    @Autowired
    private ISgjsReportMeasureSubmitService sgjsReportMeasureSubmitService;

    @PreAuthorize(hasPermi = "sgjsReportMeasureSubmit:list")
    @GetMapping
    public AjaxResult getSgjsReportMeasureSubmit(
        SgjsReportMeasureSubmit sgjsReportMeasureSubmitParam) {
        SgjsReportMeasureSubmit sgjsReportMeasureSubmit = sgjsReportMeasureSubmitService.getSgjsReportMeasureSubmit(
            sgjsReportMeasureSubmitParam);
        return AjaxResult.success(sgjsReportMeasureSubmit);
    }

    /**
     * 台账列表数据
     * @param sgjsReportMeasureSubmitParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsReportMeasureSubmit:list")
    @GetMapping("/list")
    public AjaxResult getSgjsReportMeasureSubmitList(
        SgjsReportMeasureSubmit sgjsReportMeasureSubmitParam) {
        SgjsReportMeasureSubmitVo sgjsReportMeasureSubmitVo = sgjsReportMeasureSubmitService.list(sgjsReportMeasureSubmitParam);
        return AjaxResult.success(sgjsReportMeasureSubmitVo);
    }

    @PreAuthorize(hasPermi = "sgjsReportMeasureSubmit:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsReportMeasureSubmit(
        @RequestBody SgjsReportMeasureSubmit sgjsReportMeasureSubmitParam) {
        sgjsReportMeasureSubmitService.insertSgjsReportMeasureSubmit(sgjsReportMeasureSubmitParam);
        return AjaxResult.success(sgjsReportMeasureSubmitParam);
    }

    /**
     * 批量新增
     * @param sgjsReportMeasureSubmitVo
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsReportMeasureSubmit:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsReportMeasureSubmitList(
        @Validated(ValidationGroups.Save.class) @RequestBody SgjsReportMeasureSubmitVo sgjsReportMeasureSubmitVo) {
        AjaxResult ajaxResult =  sgjsReportMeasureSubmitService.batchAdd(sgjsReportMeasureSubmitVo);
        return ajaxResult;
    }

    @PreAuthorize(hasPermi = "sgjsReportMeasureSubmit:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsReportMeasureSubmit(
        @RequestBody SgjsReportMeasureSubmit sgjsReportMeasureSubmitParam) {
        return toAjax(sgjsReportMeasureSubmitService.updateSgjsReportMeasureSubmit(
            sgjsReportMeasureSubmitParam));
    }

    @PreAuthorize(hasPermi = "sgjsReportMeasureSubmit:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsReportMeasureSubmitList(
        @RequestBody List<SgjsReportMeasureSubmit> sgjsReportMeasureSubmitListParam) {
        return toAjax(sgjsReportMeasureSubmitService.updateSgjsReportMeasureSubmitList(
            sgjsReportMeasureSubmitListParam));
    }

    @PreAuthorize(hasPermi = "sgjsReportMeasureSubmit:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsReportMeasureSubmit(
        @RequestBody SgjsReportMeasureSubmit sgjsReportMeasureSubmitParam) {
        return toAjax(sgjsReportMeasureSubmitService.deleteSgjsReportMeasureSubmit(
            sgjsReportMeasureSubmitParam));
    }

    @PreAuthorize(hasPermi = "sgjsReportMeasureSubmit:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsReportMeasureSubmitByPks(@PathVariable Long[] pks) {
        List<Long> sgjsReportMeasureSubmitPkList = Arrays.asList(pks);
        return toAjax(sgjsReportMeasureSubmitService.deleteSgjsReportMeasureSubmitByPks(
            sgjsReportMeasureSubmitPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response,
        SgjsReportMeasureSubmit sgjsReportMeasureSubmitParam) throws IOException {
        SgjsReportMeasureSubmitVo sgjsReportMeasureSubmitVo = sgjsReportMeasureSubmitService.list(sgjsReportMeasureSubmitParam);
        List<SgjsReportMeasureSubmit> treeList = sgjsReportMeasureSubmitVo.getTreeList();
        if(CollectionUtils.isNotEmpty(treeList)){
            treeList = TreeUtil.treeToList(treeList);
        }
        ExcelUtils<SgjsReportMeasureSubmit> utils = new ExcelUtils<>(SgjsReportMeasureSubmit.class);
        utils.exportExcel(response,treeList,DateUtils.getDate());
    }
}
