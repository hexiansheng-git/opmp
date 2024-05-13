package com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.controller;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManageVo;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.tree.TreeUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.service.ISgjsPlanMeasureManageService;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManage;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zmh 测量计划进度管理
 * @date 2023-12-07 18:13:51
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsPlanMeasureManage")
public class SgjsPlanMeasureManageController extends BaseController {

    @Autowired
    private ISgjsPlanMeasureManageService sgjsPlanMeasureManageService;


    @GetMapping
    public AjaxResult getSgjsPlanMeasureManage(
        @Validated(ValidationGroups.Get.class) SgjsPlanMeasureManage sgjsPlanMeasureManageParam) {
        SgjsPlanMeasureManage sgjsPlanMeasureManage = sgjsPlanMeasureManageService.getSgjsPlanMeasureManage(
            sgjsPlanMeasureManageParam);
        return AjaxResult.success(sgjsPlanMeasureManage);
    }

    /**
     * 台账页列表查询
     *
     * @param sgjsPlanMeasureManageParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:list")
    @GetMapping("/list")
    public AjaxResult getSgjsPlanMeasureManageList(
        @Validated(ValidationGroups.Select.class) SgjsPlanMeasureManage sgjsPlanMeasureManageParam) throws ParseException {
        SgjsPlanMeasureManageVo sgjsPlanMeasureManageVo = sgjsPlanMeasureManageService.list(
            sgjsPlanMeasureManageParam);
        return AjaxResult.success(sgjsPlanMeasureManageVo);
    }

    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsPlanMeasureManage(
        @Validated(ValidationGroups.Save.class) @RequestBody SgjsPlanMeasureManage sgjsPlanMeasureManageParam) {
        sgjsPlanMeasureManageService.insertSgjsPlanMeasureManage(sgjsPlanMeasureManageParam);
        return AjaxResult.success(sgjsPlanMeasureManageParam);
    }

    /**
     * 批量新增
     *
     * @param sgjsPlanMeasureManageVo
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:batchAdd")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsPlanMeasureManageList(
        @Validated(ValidationGroups.Save.class) @RequestBody SgjsPlanMeasureManageVo sgjsPlanMeasureManageVo) {
        AjaxResult ajaxResult = sgjsPlanMeasureManageService.batchAdd(sgjsPlanMeasureManageVo);
        return ajaxResult;
    }

    /**
     * 同步
     *
     * @param
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:sync")
    @GetMapping("/qqchMeasureExpPlanSelect")
    public AjaxResult qqchMeasureExpPlanSelect() {
        SgjsPlanMeasureManageVo sgjsPlanMeasureManageVo = sgjsPlanMeasureManageService.qqchMeasureExpPlanSelect();
        return AjaxResult.success(sgjsPlanMeasureManageVo);
    }


    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsPlanMeasureManage(
        @Validated(ValidationGroups.Update.class) @RequestBody SgjsPlanMeasureManage sgjsPlanMeasureManageParam) {
        return toAjax(
            sgjsPlanMeasureManageService.updateSgjsPlanMeasureManage(sgjsPlanMeasureManageParam));
    }

    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsPlanMeasureManageList(
        @Validated(ValidationGroups.Update.class) @RequestBody List<SgjsPlanMeasureManage> sgjsPlanMeasureManageListParam) {
        return toAjax(sgjsPlanMeasureManageService.updateSgjsPlanMeasureManageList(
            sgjsPlanMeasureManageListParam));
    }

    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsPlanMeasureManage(
        @Validated(ValidationGroups.Delete.class) @RequestBody SgjsPlanMeasureManage sgjsPlanMeasureManageParam) {
        return toAjax(
            sgjsPlanMeasureManageService.deleteSgjsPlanMeasureManage(sgjsPlanMeasureManageParam));
    }

    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsPlanMeasureManageByPks(@PathVariable Long[] ids) {
        List<Long> sgjsPlanMeasureManagePkList = Arrays.asList(ids);
        return toAjax(sgjsPlanMeasureManageService.deleteSgjsPlanMeasureManageByPks(
            sgjsPlanMeasureManagePkList));
    }

    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody SgjsPlanMeasureManage sgjsPlanMeasureManageParam)
        throws IOException, ParseException {
        List<String> ids = sgjsPlanMeasureManageParam.getIds();
        List<SgjsPlanMeasureManage> treeList = null;
        if(CollectionUtils.isEmpty(ids)){
            SgjsPlanMeasureManageVo sgjsPlanMeasureManageVo = sgjsPlanMeasureManageService.list(sgjsPlanMeasureManageParam);
            treeList = sgjsPlanMeasureManageVo.getTreeList();
            if(CollectionUtils.isNotEmpty(treeList)){
                treeList = TreeUtil.treeToList(treeList);
            }
        }else{
            List<SgjsPlanMeasureManage> list = sgjsPlanMeasureManageService.getIds(ids);
            if(CollectionUtils.isNotEmpty(list)){
                list.forEach(plan -> {
                    plan.setPlanStartDateStr(plan.getPlanStartDate() == null ? null : new SimpleDateFormat("yyyy年MM月dd日").format(plan.getPlanStartDate()));
                    plan.setPlanEndDateStr(plan.getPlanEndDate() == null ? null : new SimpleDateFormat("yyyy年MM月dd日").format(plan.getPlanEndDate()));
                    plan.setRealStartDateStr(plan.getRealStartDate() == null ? null : new SimpleDateFormat("yyyy年MM月dd日").format(plan.getRealStartDate()));
                    plan.setRealEndDateStr(plan.getRealEndDate() == null ? null : new SimpleDateFormat("yyyy年MM月dd日").format(plan.getRealEndDate()));
                });
                treeList = list;

            }
        }
        FtExcelUtil<SgjsPlanMeasureManage> utils = new FtExcelUtil<>(SgjsPlanMeasureManage.class);
        utils.exportExcel(response,treeList,DateUtils.getDate());
    }

    @PostMapping("/exportTemplate")
//    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:import")
    public void exportTemplate(HttpServletResponse response) throws IOException {
        FtExcelUtil<SgjsPlanMeasureManage> utils = new FtExcelUtil<>(SgjsPlanMeasureManage.class);
        utils.exportExcel(response,new ArrayList<>(),DateUtils.getDate(),"测量计划进度管理.xls");
    }

    @PostMapping("/importData")
//    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:import")
    public AjaxResult importData(HttpServletResponse response, MultipartFile file) throws Exception {
        FtExcelUtil<SgjsPlanMeasureManage> utils = new FtExcelUtil<>(SgjsPlanMeasureManage.class);
        List<SgjsPlanMeasureManage> list = utils.importExcel(file.getInputStream());
        for (int i = 0; i < list.size(); i++) {
            SgjsPlanMeasureManage temp = list.get(i);
            if(temp.getPlanStartDate() != null && temp.getPlanEndDate() != null)
                Assert.isTrue(temp.getPlanStartDate().before(temp.getPlanEndDate()),"计划开始日期必须早于计划结束日期");
            if(temp.getRealStartDate() != null && temp.getRealEndDate() != null)
                Assert.isTrue(temp.getRealStartDate().before(temp.getRealEndDate()),"实际开始日期必须早于实际结束日期");
        }
        return AjaxResult.success(list);
    }
    
}
