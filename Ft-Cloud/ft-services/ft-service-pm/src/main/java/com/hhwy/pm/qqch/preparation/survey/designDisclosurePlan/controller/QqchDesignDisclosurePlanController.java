package com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.domain.QqchDesignDisclosurePlan;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.domain.vo.QqchDesignDisclosurePlanVo;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.service.IQqchDesignDisclosurePlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author ldd
 * @date 2023-07-21 16:47:23
 * @remark 2.4 设计交底策划
 */
@Validated
@RestController
@RequestMapping("/qqchDesignDisclosurePlan")
public class QqchDesignDisclosurePlanController extends BaseController {

    @Autowired
    private IQqchDesignDisclosurePlanService qqchDesignDisclosurePlanService;



    /**
     * 列表接口
     *
     * @param qqchDesignDisclosurePlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignDisclosurePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchDesignDisclosurePlanList(@Validated(ValidationGroups.Select.class) QqchDesignDisclosurePlan qqchDesignDisclosurePlanParam) {
        QqchDesignDisclosurePlanVo qqchDesignDisclosurePlanVo = qqchDesignDisclosurePlanService.getQqchDesignDisclosurePlanList(qqchDesignDisclosurePlanParam);
        return AjaxResult.success(qqchDesignDisclosurePlanVo);
    }

    /**
     *  新增接口
     *
     * @param qqchDesignDisclosurePlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignDisclosurePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult batchAdd(@Validated(ValidationGroups.Save.class) @RequestBody QqchDesignDisclosurePlanVo qqchDesignDisclosurePlanVo) {
        qqchDesignDisclosurePlanService.save(qqchDesignDisclosurePlanVo);
        return AjaxResult.success(qqchDesignDisclosurePlanVo);
    }

    /**
     *  确认
     *
     * @param qqchDesignDisclosurePlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignDisclosurePlan:confirm")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchDesignDisclosurePlanVo qqchDesignDisclosurePlanVo) {
        qqchDesignDisclosurePlanService.confirm(qqchDesignDisclosurePlanVo);
        return AjaxResult.success(qqchDesignDisclosurePlanVo);
    }


}
