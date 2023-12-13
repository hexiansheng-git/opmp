package com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlanVo;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.service.IQqchSurveyWorkPlanService;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-20 11:49:55
 * @remark 2.2 勘察设计工作计划
 */
@Validated
@RestController
@RequestMapping("/qqchSurveyWorkPlan")
public class QqchSurveyWorkPlanController extends BaseController {

    @Autowired
    private IQqchSurveyWorkPlanService qqchSurveyWorkPlanService;



    /**
     *  列表接口
     *
     * @param qqchSurveyWorkPlanParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyWorkPlan:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计工作计划", name = "2.2勘察设计工作计划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchSurveyWorkPlanList(@Validated(ValidationGroups.Select.class) QqchSurveyWorkPlan qqchSurveyWorkPlanParam) {
        QqchSurveyWorkPlanVo vo = qqchSurveyWorkPlanService.getQqchSurveyWorkPlanList(qqchSurveyWorkPlanParam);
        return AjaxResult.success(vo);
    }


    /**
     *  批增
     *
     * @param qqchSurveyWorkPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyWorkPlan:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计工作计划", name = "2.2勘察设计工作计划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchSurveyWorkPlanList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyWorkPlanVo qqchSurveyWorkPlanVo) {
        qqchSurveyWorkPlanService.save(qqchSurveyWorkPlanVo);
        return AjaxResult.success(qqchSurveyWorkPlanVo);
    }

    /**
     *  确认
     *
     * @param qqchSurveyWorkPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyWorkPlan:confirm")
    @PostMapping("/confirm")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计工作计划", name = "2.2勘察设计工作计划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyWorkPlanVo qqchSurveyWorkPlanVo) {
        qqchSurveyWorkPlanService.confirm(qqchSurveyWorkPlanVo);
        return AjaxResult.success(qqchSurveyWorkPlanVo);
    }


    /**
     *  弹窗功能，整合弹框选中和列表中的数据
     */
    @PostMapping("/getActivityByids")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计工作计划", name = "2.2勘察设计工作计划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult handleActivityData(@RequestBody QqchSurveyWorkPlanVo qqchSurveyWorkPlanVo) {
        List<QqchSurveyWorkPlan> build = qqchSurveyWorkPlanService.handleActivityData(qqchSurveyWorkPlanVo);
        return AjaxResult.success(build);
    }



}
