package com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlanVo;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.service.IQqchSurveyWorkPlanService;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
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

    @Autowired
    IQqchMainPlanItemService qqchMainPlanItemService;



    /**
     *  列表接口
     *
     * @param qqchSurveyWorkPlanParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyWorkPlan:list")
    @GetMapping("/list")
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
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyWorkPlanVo qqchSurveyWorkPlanVo) {
        qqchSurveyWorkPlanService.confirm(qqchSurveyWorkPlanVo);
        return AjaxResult.success(qqchSurveyWorkPlanVo);
    }


    /**
     *  弹窗功能，获取选中的作业的所有上下级作业
     */
    @PostMapping("/getActivityByids")
    public AjaxResult getActivityByids(Long[] ids) {
        List<QqchMainPlanItem> allLinkList = qqchMainPlanItemService.getAllLinkList(Arrays.asList(ids));
        List<QqchMainPlanItem> build = TreeUtil.build(allLinkList, null);
        return AjaxResult.success(build);
    }



}
