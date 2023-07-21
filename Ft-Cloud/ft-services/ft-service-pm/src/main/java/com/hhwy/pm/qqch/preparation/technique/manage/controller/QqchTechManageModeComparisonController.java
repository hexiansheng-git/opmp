package com.hhwy.pm.qqch.preparation.technique.manage.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.vo.QqchTechManageModeComparisonVo;
import com.hhwy.pm.qqch.preparation.technique.manage.service.IQqchTechManageModeComparisonService;
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
 * @date 2023-07-11 15:17:31
 * @remark 3.3.1技术管理模式比选
 */
@Validated
@RestController
@RequestMapping("/qqchTechManageModeComparison")
public class QqchTechManageModeComparisonController extends BaseController {

    @Autowired
    private IQqchTechManageModeComparisonService qqchTechManageModeComparisonService;

    @PreAuthorize(hasPermi = "qqchTechManageModeComparison:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchTechManageModeComparisonVo qqchTechManageModeComparisonVo = qqchTechManageModeComparisonService
            .getQqchTechManageModeComparisonList(version);
        return AjaxResult.success(qqchTechManageModeComparisonVo);
    }

    @PreAuthorize(hasPermi = "qqchTechManageModeComparison:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchTechManageModeComparisonVo qqchTechManageModeComparisonVo) {
        qqchTechManageModeComparisonService.batchSave(qqchTechManageModeComparisonVo);
        return AjaxResult.success();
    }
}
