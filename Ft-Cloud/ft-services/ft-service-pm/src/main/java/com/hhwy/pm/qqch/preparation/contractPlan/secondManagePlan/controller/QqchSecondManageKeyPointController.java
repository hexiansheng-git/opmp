package com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.domain.QqchSecondManageKeyPoint;
import com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.domain.vo.QqchSecondManageKeyPointVo;
import com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.domain.vo.SecondManageKeyPointPlanVo;
import com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.service.IQqchSecondManageKeyPointService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:47:11
 * @remark 二次经营要点识别
 */
@Validated
@RestController
@RequestMapping("/qqchSecondManageKeyPoint")
public class QqchSecondManageKeyPointController extends BaseController {

    @Autowired
    private IQqchSecondManageKeyPointService qqchSecondManageKeyPointService;


    //    @PreAuthorize(hasPermi = "qqchSecondManageKeyPoint:list")
    @GetMapping
    public AjaxResult getQqchSecondManageKeyPoint(@Validated(ValidationGroups.Get.class) QqchSecondManageKeyPoint qqchSecondManageKeyPointParam) {
        QqchSecondManageKeyPoint qqchSecondManageKeyPoint = qqchSecondManageKeyPointService.getQqchSecondManageKeyPoint(qqchSecondManageKeyPointParam);
        return AjaxResult.success(qqchSecondManageKeyPoint);
    }

    /**
     * 获取二次经营要点识别集合
     *
     * @param qqchSecondManageKeyPoint
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQqchSecondManageKeyPointList(@Validated(ValidationGroups.Select.class) QqchSecondManageKeyPoint qqchSecondManageKeyPoint) {
        List<QqchSecondManageKeyPoint> qqchSecondManageKeyPointList = qqchSecondManageKeyPointService.getQqchSecondManageKeyPointList(qqchSecondManageKeyPoint);
        return AjaxResult.success(qqchSecondManageKeyPointList);
    }

    //    @PreAuthorize(hasPermi = "qqchSecondManageKeyPoint:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSecondManageKeyPoint(@Validated(ValidationGroups.Save.class) @RequestBody QqchSecondManageKeyPoint qqchSecondManageKeyPointParam) {
        qqchSecondManageKeyPointService.insertQqchSecondManageKeyPoint(qqchSecondManageKeyPointParam);
        return AjaxResult.success(qqchSecondManageKeyPointParam);
    }

    //    @PreAuthorize(hasPermi = "qqchSecondManageKeyPoint:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSecondManageKeyPoint(@Validated(ValidationGroups.Update.class) @RequestBody QqchSecondManageKeyPoint qqchSecondManageKeyPointParam) {
        return toAjax(qqchSecondManageKeyPointService.updateQqchSecondManageKeyPoint(qqchSecondManageKeyPointParam));
    }

    //    @PreAuthorize(hasPermi = "qqchSecondManageKeyPoint:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSecondManageKeyPointList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSecondManageKeyPoint> qqchSecondManageKeyPointListParam) {
        return toAjax(qqchSecondManageKeyPointService.updateQqchSecondManageKeyPointList(qqchSecondManageKeyPointListParam));
    }

    //    @PreAuthorize(hasPermi = "qqchSecondManageKeyPoint:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSecondManageKeyPoint(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSecondManageKeyPoint qqchSecondManageKeyPointParam) {
        return toAjax(qqchSecondManageKeyPointService.deleteQqchSecondManageKeyPoint(qqchSecondManageKeyPointParam));
    }

    //    @PreAuthorize(hasPermi = "qqchSecondManageKeyPoint:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSecondManageKeyPointByPks(@PathVariable Long[] ids) {
        List<Long> qqchSecondManageKeyPointPkList = Arrays.asList(ids);
        return toAjax(qqchSecondManageKeyPointService.deleteQqchSecondManageKeyPointByPks(qqchSecondManageKeyPointPkList));
    }

    /**
     * 获取普通要点策划/变更策划/索赔策划 Vo
     *
     * @param version      版本
     * @param keyPointType 要点类型
     * @return
     */
    @GetMapping("getSecondManageKeyPointPlanVo")
    public AjaxResult getSecondManageKeyPointPlanVo(BigDecimal version, String keyPointType) {
        SecondManageKeyPointPlanVo secondManageKeyPointPlanVo = qqchSecondManageKeyPointService.getSecondManageKeyPointPlanVo(version, keyPointType);
        return AjaxResult.success(secondManageKeyPointPlanVo);
    }

    /**
     * 保存/确认/提交  普通要点策划/变更策划/索赔策划  （操作附件）
     *
     * @param secondManageKeyPointPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSecondManageKeyPoint:save")
    @PostMapping("/saveSecondManageKeyPointPlanVo")
    @CustomLogger(title = "前期策划-前期策划编制-合同策划-4.3 二次经营策划", name =
            "4.3.2 普通要点策划/4.3.3 变更策划/4.3.4 索赔策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult saveSecondManageKeyPointPlanVo(@RequestBody SecondManageKeyPointPlanVo secondManageKeyPointPlanVo) {
        qqchSecondManageKeyPointService.saveSecondManageKeyPointPlanVo(secondManageKeyPointPlanVo);
        return AjaxResult.success();
    }

    /**
     * 获取二次经营要点识别Vo
     *
     * @param qqchSecondManageKeyPoint
     * @return
     */
    @GetMapping("getQqchSecondManageKeyPointVo")
    public AjaxResult getQqchSecondManageKeyPointVo(@Validated(ValidationGroups.Get.class) QqchSecondManageKeyPoint qqchSecondManageKeyPoint) {
        QqchSecondManageKeyPointVo qqchSecondManageKeyPointVo = qqchSecondManageKeyPointService.getQqchSecondManageKeyPointVo(qqchSecondManageKeyPoint);
        return AjaxResult.success(qqchSecondManageKeyPointVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchSecondManageKeyPointVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSecondManageKeyPoint:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-合同策划-4.3 二次经营策划", name =
            "4.3.1 二次经营要点识别", businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@RequestBody QqchSecondManageKeyPointVo qqchSecondManageKeyPointVo) {
        qqchSecondManageKeyPointService.save(qqchSecondManageKeyPointVo);
        return AjaxResult.success();
    }

}
