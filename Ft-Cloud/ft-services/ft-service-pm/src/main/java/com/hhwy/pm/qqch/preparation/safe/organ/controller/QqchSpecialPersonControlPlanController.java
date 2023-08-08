package com.hhwy.pm.qqch.preparation.safe.organ.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.QqchSpecialPersonControlPlan;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchSpecialPersonControlPlanVo;
import com.hhwy.pm.qqch.preparation.safe.organ.service.IQqchSpecialPersonControlPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-08-07 13:50:54
 * @remark 8.1.3 特种作业人员管控策划
 */
@Validated
@RestController
@RequestMapping("/qqchSpecialPersonControlPlan")
public class QqchSpecialPersonControlPlanController extends BaseController {

    @Autowired
    private IQqchSpecialPersonControlPlanService qqchSpecialPersonControlPlanService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSpecialPersonControlPlan:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchSpecialPersonControlPlanVo qqchSpecialPersonControlPlanVo = qqchSpecialPersonControlPlanService
            .getQqchSpecialPersonControlPlanList(version);
        return AjaxResult.success(qqchSpecialPersonControlPlanVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchSpecialPersonControlPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSpecialPersonControlPlan:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchSpecialPersonControlPlanVo qqchSpecialPersonControlPlanVo) {
        qqchSpecialPersonControlPlanService
            .batchSave(qqchSpecialPersonControlPlanVo);
        return AjaxResult.success();
    }
}
