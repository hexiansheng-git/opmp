package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonScheme;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchComparisonSchemeVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-07 18:35:38
 * @remark 重大设计方案比选-方案
 */
@Validated
@RestController
@RequestMapping("/qqchComparisonScheme")
public class QqchComparisonSchemeController extends BaseController {

    @Autowired
    private IQqchComparisonSchemeService qqchComparisonSchemeService;


    /**
     * 重大设计方案比选-方案台账
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQqchComparisonSchemeVo(BigDecimal version) {
        QqchComparisonSchemeVo qqchComparisonSchemeVo = qqchComparisonSchemeService.getQqchComparisonSchemeVo(version);
        return AjaxResult.success(qqchComparisonSchemeVo);
    }

    /**
     * 获取初始化表格
     * @return
     */
    @GetMapping("init")
    public AjaxResult initTable(){
        QqchComparisonScheme init = qqchComparisonSchemeService.init();
        return AjaxResult.success(init);
    }

    /**
     * 保存
     * @param qqchComparisonSchemeVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchComparisonScheme:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计优化变更策划", name = "\n" +
            "2.5.5 重大设计方案比选" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchComparisonSchemeVo qqchComparisonSchemeVo) {
        qqchComparisonSchemeService.save(qqchComparisonSchemeVo);
        return AjaxResult.success();
    }

    /**
     * 确认
     * @param qqchComparisonSchemeVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchComparisonScheme:save")
    @PostMapping("/confirm")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-2.5 勘察设计优化变更策划", name = "\n" +
            "2.5.5 重大设计方案比选" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchComparisonSchemeVo qqchComparisonSchemeVo) {
        qqchComparisonSchemeService.confirm(qqchComparisonSchemeVo);
        return AjaxResult.success("确认成功！");
    }
}
