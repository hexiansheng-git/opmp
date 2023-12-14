package com.hhwy.pm.qqch.preparation.survey.designCheckPlan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.QqchDesignCheckPlan;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.vo.QqchDesignCheckPlanVo;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.service.IQqchDesignCheckPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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
//    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计成果策划", name = "2.3.3设计成果验收计划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchDesignCheckPlanList(@Validated(ValidationGroups.Select.class) QqchDesignCheckPlan qqchDesignCheckPlanParam) {
        QqchDesignCheckPlanVo qqchDesignCheckPlanVo = qqchDesignCheckPlanService.getQqchDesignCheckPlanList(qqchDesignCheckPlanParam);
        return AjaxResult.success(qqchDesignCheckPlanVo);
    }


//    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计成果策划", name = "2.3.3设计成果验收计划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchDesignCheckPlanList(@Validated(ValidationGroups.Save.class) @RequestBody QqchDesignCheckPlanVo qqchDesignCheckPlanVo) {
        qqchDesignCheckPlanService.save(qqchDesignCheckPlanVo);
        return AjaxResult.success(qqchDesignCheckPlanVo);
    }

//    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:confirm")
    @PostMapping("/confirm")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计成果策划", name = "2.3.3设计成果验收计划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchDesignCheckPlanVo qqchDesignCheckPlanVo) {
        qqchDesignCheckPlanService.confirm(qqchDesignCheckPlanVo);
        return AjaxResult.success(qqchDesignCheckPlanVo);
    }


}
