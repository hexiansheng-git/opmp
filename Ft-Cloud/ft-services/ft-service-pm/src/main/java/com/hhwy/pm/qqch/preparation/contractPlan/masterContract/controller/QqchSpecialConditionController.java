package com.hhwy.pm.qqch.preparation.contractPlan.masterContract.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.QqchSpecialCondition;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.vo.QqchSpecialConditionVo;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.service.IQqchSpecialConditionService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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
 * @date 2023-08-02 11:39:48
 * @remark 专用条件梳理
 */
@Validated
@RestController
@RequestMapping("/qqchSpecialCondition")
public class QqchSpecialConditionController extends BaseController {

    @Autowired
    private IQqchSpecialConditionService qqchSpecialConditionService;


    //    @PreAuthorize(hasPermi = "qqchSpecialCondition:list")
    @GetMapping
    public AjaxResult getQqchSpecialCondition(@Validated(ValidationGroups.Get.class) QqchSpecialCondition qqchSpecialConditionParam) {
        QqchSpecialCondition qqchSpecialCondition = qqchSpecialConditionService.getQqchSpecialCondition(qqchSpecialConditionParam);
        return AjaxResult.success(qqchSpecialCondition);
    }

    //    @PreAuthorize(hasPermi = "qqchSpecialCondition:list")
    @GetMapping("/list")
    public AjaxResult getQqchSpecialConditionList(@Validated(ValidationGroups.Select.class) QqchSpecialCondition qqchSpecialConditionParam) {
        startPage();
        List<QqchSpecialCondition> qqchSpecialConditionList = qqchSpecialConditionService.getQqchSpecialConditionList(qqchSpecialConditionParam);
        return getDataTableAjaxResult(qqchSpecialConditionList);
    }

    //    @PreAuthorize(hasPermi = "qqchSpecialCondition:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSpecialCondition(@Validated(ValidationGroups.Save.class) @RequestBody QqchSpecialCondition qqchSpecialConditionParam) {
        qqchSpecialConditionService.insertQqchSpecialCondition(qqchSpecialConditionParam);
        return AjaxResult.success(qqchSpecialConditionParam);
    }

    //    @PreAuthorize(hasPermi = "qqchSpecialCondition:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSpecialCondition(@Validated(ValidationGroups.Update.class) @RequestBody QqchSpecialCondition qqchSpecialConditionParam) {
        return toAjax(qqchSpecialConditionService.updateQqchSpecialCondition(qqchSpecialConditionParam));
    }

    //    @PreAuthorize(hasPermi = "qqchSpecialCondition:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSpecialConditionList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSpecialCondition> qqchSpecialConditionListParam) {
        return toAjax(qqchSpecialConditionService.updateQqchSpecialConditionList(qqchSpecialConditionListParam));
    }

    //    @PreAuthorize(hasPermi = "qqchSpecialCondition:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSpecialCondition(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSpecialCondition qqchSpecialConditionParam) {
        return toAjax(qqchSpecialConditionService.deleteQqchSpecialCondition(qqchSpecialConditionParam));
    }

    //    @PreAuthorize(hasPermi = "qqchSpecialCondition:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSpecialConditionByPks(@PathVariable Long[] ids) {
        List<Long> qqchSpecialConditionPkList = Arrays.asList(ids);
        return toAjax(qqchSpecialConditionService.deleteQqchSpecialConditionByPks(qqchSpecialConditionPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSpecialCondition qqchSpecialConditionParam) throws IOException {
        List<QqchSpecialCondition> qqchSpecialConditionList = qqchSpecialConditionService.getQqchSpecialConditionList(qqchSpecialConditionParam);
        ExcelUtils<QqchSpecialCondition> util = new ExcelUtils<>(QqchSpecialCondition.class);
        util.exportExcel(response, qqchSpecialConditionList, DateUtils.getDate());
    }

    /**
     * 获取专用条件梳理Vo
     *
     * @param qqchSpecialCondition
     * @return
     */
    @GetMapping("getQqchSpecialConditionVo")
    public AjaxResult getQqchSpecialConditionVo(@Validated(ValidationGroups.Get.class) QqchSpecialCondition qqchSpecialCondition) {
        QqchSpecialConditionVo qqchSpecialConditionVo = qqchSpecialConditionService.getQqchSpecialConditionVo(qqchSpecialCondition);
        return AjaxResult.success(qqchSpecialConditionVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchSpecialConditionVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSpecialCondition:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-合同策划-4.1 主合同分析", name =
            "4.1.1 专用条件梳理", businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchSpecialConditionVo qqchSpecialConditionVo) {
        qqchSpecialConditionService.save(qqchSpecialConditionVo);
        return AjaxResult.success();
    }

    /**
     * 10.1财务相关主合同条款 弹窗
     *
     * @param qqchSpecialCondition
     * @return
     */
    @GetMapping("popUpWindows")
    public AjaxResult popUpWindows(QqchSpecialCondition qqchSpecialCondition) {
        List<QqchSpecialCondition> list = qqchSpecialConditionService.popUpWindows(qqchSpecialCondition);
        return AjaxResult.success(list);
    }
}
