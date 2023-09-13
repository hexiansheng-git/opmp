package com.hhwy.pm.qqch.preparation.technique.techManagePlan.controller;

import cn.hutool.core.date.DateException;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAdvancedVindicatePlanImportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAdvancedVindicatePlanVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchAdvancedVindicatePlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-07-27 15:51:15
 * @remark 高新维护计划
 */
@Validated
@RestController
@RequestMapping("/qqchAdvancedVindicatePlan")
public class QqchAdvancedVindicatePlanController extends BaseController {

    @Autowired
    private IQqchAdvancedVindicatePlanService qqchAdvancedVindicatePlanService;


    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlan:list")
    @GetMapping
    public AjaxResult getQqchAdvancedVindicatePlan(@Validated(ValidationGroups.Get.class) QqchAdvancedVindicatePlan qqchAdvancedVindicatePlanParam) {
        QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan = qqchAdvancedVindicatePlanService.getQqchAdvancedVindicatePlan(qqchAdvancedVindicatePlanParam);
        return AjaxResult.success(qqchAdvancedVindicatePlan);
    }

    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchAdvancedVindicatePlanList(@Validated(ValidationGroups.Select.class) QqchAdvancedVindicatePlan qqchAdvancedVindicatePlanParam) {
        startPage();
        List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanList = qqchAdvancedVindicatePlanService.getQqchAdvancedVindicatePlanList(qqchAdvancedVindicatePlanParam);
        return getDataTableAjaxResult(qqchAdvancedVindicatePlanList);
    }

    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchAdvancedVindicatePlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchAdvancedVindicatePlan qqchAdvancedVindicatePlanParam) {
        qqchAdvancedVindicatePlanService.insertQqchAdvancedVindicatePlan(qqchAdvancedVindicatePlanParam);
        return AjaxResult.success(qqchAdvancedVindicatePlanParam);
    }

    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchAdvancedVindicatePlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchAdvancedVindicatePlan qqchAdvancedVindicatePlanParam) {
        return toAjax(qqchAdvancedVindicatePlanService.updateQqchAdvancedVindicatePlan(qqchAdvancedVindicatePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchAdvancedVindicatePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanListParam) {
        return toAjax(qqchAdvancedVindicatePlanService.updateQqchAdvancedVindicatePlanList(qqchAdvancedVindicatePlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchAdvancedVindicatePlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchAdvancedVindicatePlan qqchAdvancedVindicatePlanParam) {
        return toAjax(qqchAdvancedVindicatePlanService.deleteQqchAdvancedVindicatePlan(qqchAdvancedVindicatePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchAdvancedVindicatePlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchAdvancedVindicatePlanPkList = Arrays.asList(ids);
        return toAjax(qqchAdvancedVindicatePlanService.deleteQqchAdvancedVindicatePlanByPks(qqchAdvancedVindicatePlanPkList));
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("/import")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        List<QqchAdvancedVindicatePlanImportVo> qqchAdvancedVindicatePlanImportVoList = null;
        try {
            qqchAdvancedVindicatePlanImportVoList = qqchAdvancedVindicatePlanService.importExcel(file);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件不存在！");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (DateException e){
            throw new DateException("日期类型错误！");
        }
        return AjaxResult.success(qqchAdvancedVindicatePlanImportVoList);
    }

    /**
     * 导出
     * @param response
     * @param qqchAdvancedVindicatePlan
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan) throws IOException {
        qqchAdvancedVindicatePlanService.export(response,qqchAdvancedVindicatePlan);
    }

    /**
     * 获取高新维护计划Vo
     * @param qqchAdvancedVindicatePlan
     * @return
     */
    @GetMapping("getQqchAdvancedVindicatePlanVo")
    public AjaxResult getQqchAdvancedVindicatePlanVo(@Validated(ValidationGroups.Get.class) QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan) {
        QqchAdvancedVindicatePlanVo qqchAdvancedVindicatePlanVo = qqchAdvancedVindicatePlanService.getQqchAdvancedVindicatePlanVo(qqchAdvancedVindicatePlan);
        return AjaxResult.success(qqchAdvancedVindicatePlanVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchAdvancedVindicatePlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlan:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchAdvancedVindicatePlanVo qqchAdvancedVindicatePlanVo) {
        qqchAdvancedVindicatePlanService.save(qqchAdvancedVindicatePlanVo);
        return AjaxResult.success();
    }
}
