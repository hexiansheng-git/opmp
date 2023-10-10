package com.hhwy.pm.qqch.preparation.costControl.postDuty.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.QqchCostControlPostDuty;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.vo.QqchCostControlPostDutyVo;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.service.IQqchCostControlPostDutyService;
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
 * @date 2023-08-08 17:48:45
 * @remark 成本管控岗位责任
 */
@Validated
@RestController
@RequestMapping("/qqchCostControlPostDuty")
public class QqchCostControlPostDutyController extends BaseController {

    @Autowired
    private IQqchCostControlPostDutyService qqchCostControlPostDutyService;


//    @PreAuthorize(hasPermi = "qqchCostControlPostDuty:list")
    @GetMapping
    public AjaxResult getQqchCostControlPostDuty(@Validated(ValidationGroups.Get.class) QqchCostControlPostDuty qqchCostControlPostDutyParam) {
        QqchCostControlPostDuty qqchCostControlPostDuty = qqchCostControlPostDutyService.getQqchCostControlPostDuty(qqchCostControlPostDutyParam);
        return AjaxResult.success(qqchCostControlPostDuty);
    }

//    @PreAuthorize(hasPermi = "qqchCostControlPostDuty:list")
    @GetMapping("/list")
    public AjaxResult getQqchCostControlPostDutyList(@Validated(ValidationGroups.Select.class) QqchCostControlPostDuty qqchCostControlPostDutyParam) {
        startPage();
        List<QqchCostControlPostDuty> qqchCostControlPostDutyList = qqchCostControlPostDutyService.getQqchCostControlPostDutyList(qqchCostControlPostDutyParam);
        return getDataTableAjaxResult(qqchCostControlPostDutyList);
    }

//    @PreAuthorize(hasPermi = "qqchCostControlPostDuty:add")
    @PostMapping("/add")
    public AjaxResult insertQqchCostControlPostDuty(@Validated(ValidationGroups.Save.class) @RequestBody QqchCostControlPostDuty qqchCostControlPostDutyParam) {
        qqchCostControlPostDutyService.insertQqchCostControlPostDuty(qqchCostControlPostDutyParam);
        return AjaxResult.success(qqchCostControlPostDutyParam);
    }

//    @PreAuthorize(hasPermi = "qqchCostControlPostDuty:update")
    @PostMapping("/update")
    public AjaxResult updateQqchCostControlPostDuty(@Validated(ValidationGroups.Update.class) @RequestBody QqchCostControlPostDuty qqchCostControlPostDutyParam) {
        return toAjax(qqchCostControlPostDutyService.updateQqchCostControlPostDuty(qqchCostControlPostDutyParam));
    }

//    @PreAuthorize(hasPermi = "qqchCostControlPostDuty:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchCostControlPostDutyList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchCostControlPostDuty> qqchCostControlPostDutyListParam) {
        return toAjax(qqchCostControlPostDutyService.updateQqchCostControlPostDutyList(qqchCostControlPostDutyListParam));
    }

//    @PreAuthorize(hasPermi = "qqchCostControlPostDuty:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchCostControlPostDuty(@Validated(ValidationGroups.Delete.class) @RequestBody QqchCostControlPostDuty qqchCostControlPostDutyParam) {
        return toAjax(qqchCostControlPostDutyService.deleteQqchCostControlPostDuty(qqchCostControlPostDutyParam));
    }

//    @PreAuthorize(hasPermi = "qqchCostControlPostDuty:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchCostControlPostDutyByPks(@PathVariable Long[] ids) {
        List<Long> qqchCostControlPostDutyPkList = Arrays.asList(ids);
        return toAjax(qqchCostControlPostDutyService.deleteQqchCostControlPostDutyByPks(qqchCostControlPostDutyPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchCostControlPostDuty qqchCostControlPostDutyParam) throws IOException {
        List<QqchCostControlPostDuty> qqchCostControlPostDutyList = qqchCostControlPostDutyService.getQqchCostControlPostDutyList(qqchCostControlPostDutyParam);
        ExcelUtils<QqchCostControlPostDuty> util = new ExcelUtils<>(QqchCostControlPostDuty.class);
        util.exportExcel(response, qqchCostControlPostDutyList, DateUtils.getDate());
    }

    /**
     * 获取Vo
     * @param qqchCostControlPostDuty
     * @return
     */
    @GetMapping("getQqchCostControlPostDutyVo")
    public AjaxResult getQqchCostControlPostDutyVo(@Validated(ValidationGroups.Get.class) QqchCostControlPostDuty qqchCostControlPostDuty) {
        QqchCostControlPostDutyVo qqchCostControlPostDutyVo = qqchCostControlPostDutyService.getQqchCostControlPostDutyVo(qqchCostControlPostDuty);
        return AjaxResult.success(qqchCostControlPostDutyVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchCostControlPostDutyVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchCostControlPostDuty:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchCostControlPostDutyVo qqchCostControlPostDutyVo) {
        qqchCostControlPostDutyService.save(qqchCostControlPostDutyVo);
        return AjaxResult.success();
    }

    /**
     * 同步人员总需计划
     * @param qqchCostControlPostDutyVo
     * @return
     */
////    @PreAuthorize(hasPermi = "qqchCostControlPostDuty:save")
//    @PostMapping("/synchronization")
//    public AjaxResult synchronization(@Validated(ValidationGroups.Save.class) @RequestBody QqchCostControlPostDutyVo qqchCostControlPostDutyVo){
//        qqchCostControlPostDutyService.synchronization(qqchCostControlPostDutyVo, );
//        return AjaxResult.success();
//    }
}
