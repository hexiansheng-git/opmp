package com.hhwy.pm.qqch.preparation.quality.duty.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.vo.QqchPersonControlPlanVo;
import com.hhwy.pm.qqch.preparation.quality.duty.service.IQqchPersonControlPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-08-03 14:29:43
 * @remark 9.1.2 人员管控策划
 */
@Validated
@RestController
@RequestMapping("/qqchPersonControlPlan")
public class QqchPersonControlPlanController extends BaseController {

    @Autowired
    private IQqchPersonControlPlanService qqchPersonControlPlanService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchPersonControlPlan:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchPersonControlPlanVo qqchPersonControlPlanVo = qqchPersonControlPlanService
            .getQqchPersonControlPlanList(version);
        return AjaxResult.success(qqchPersonControlPlanVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchPersonControlPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchPersonControlPlan:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchPersonControlPlanVo qqchPersonControlPlanVo) {
        qqchPersonControlPlanService.insertQqchPersonControlPlanList(qqchPersonControlPlanVo);
        return AjaxResult.success();
    }
}
