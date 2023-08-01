package com.hhwy.pm.qqch.preparation.survey.designCheckPlan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.QqchDesignCheckPlan;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.vo.QqchDesignCheckPlanVo;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.service.IQqchDesignCheckPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author ldd
 * @date 2023-07-21 16:48:41
 * @remark 2.3.3 设计成果验收计划
 */
@Validated
@RestController
@RequestMapping("/qqchDesignCheckPlan")
public class QqchDesignCheckPlanController extends BaseController {

    @Autowired
    private IQqchDesignCheckPlanService qqchDesignCheckPlanService;


    /**
     *  列表查询
     *
     * @param qqchDesignCheckPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchDesignCheckPlanList(@Validated(ValidationGroups.Select.class) QqchDesignCheckPlan qqchDesignCheckPlanParam) {
        QqchDesignCheckPlanVo qqchDesignCheckPlanVo = qqchDesignCheckPlanService.getQqchDesignCheckPlanList(qqchDesignCheckPlanParam);
        return AjaxResult.success(qqchDesignCheckPlanVo);
    }


    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchDesignCheckPlanList(@Validated(ValidationGroups.Save.class) @RequestBody QqchDesignCheckPlanVo qqchDesignCheckPlanVo) {
        qqchDesignCheckPlanService.save(qqchDesignCheckPlanVo);
        return AjaxResult.success(qqchDesignCheckPlanVo);
    }

    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:confirm")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchDesignCheckPlanVo qqchDesignCheckPlanVo) {
        qqchDesignCheckPlanService.confirm(qqchDesignCheckPlanVo);
        return AjaxResult.success(qqchDesignCheckPlanVo);
    }


}
