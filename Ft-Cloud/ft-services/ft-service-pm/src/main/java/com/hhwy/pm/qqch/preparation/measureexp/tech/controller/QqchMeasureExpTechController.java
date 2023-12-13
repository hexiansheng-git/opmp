package com.hhwy.pm.qqch.preparation.measureexp.tech.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.measureexp.tech.domain.vo.QqchMeasureExpTechVo;
import com.hhwy.pm.qqch.preparation.measureexp.tech.service.IQqchMeasureExpTechService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-04 16:09:57
 * @remark 3.6.3测量技术方案计划、3.7.3试验方案计划
 */
@Validated
@RestController
@RequestMapping("/qqchMeasureExpTech")
public class QqchMeasureExpTechController extends BaseController {

    @Autowired
    private IQqchMeasureExpTechService qqchMeasureExpTechService;

    /**
     * 测量技术方案计划列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMeasureExpTech:list")
    @GetMapping("/getMeasureList")
    public AjaxResult getMeasureList(BigDecimal version) {
        QqchMeasureExpTechVo qqchMeasureExpTechVo = qqchMeasureExpTechService
            .getQqchMeasureExpTechList(version, "1");
        return AjaxResult.success(qqchMeasureExpTechVo);
    }

    /**
     * 试验方案计划列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMeasureExpTech:list")
    @GetMapping("/getExperimentList")
    public AjaxResult getExperimentList(BigDecimal version) {
        QqchMeasureExpTechVo qqchMeasureExpTechVo = qqchMeasureExpTechService
            .getQqchMeasureExpTechList(version, "2");
        return AjaxResult.success(qqchMeasureExpTechVo);
    }

    /**
     * 测量技术方案计划保存/确认/提交
     *
     * @param qqchMeasureExpTechVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMeasureExpTech:add")
    @PostMapping("/batchSaveMeasure")
    @CustomLogger(title = "前期策划-前期策划编制-施工技术策划-3.6 测量管理计划", name = "\n" +
            "3.6.3 测量技术方案计划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSaveMeasure(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureExpTechVo qqchMeasureExpTechVo) {
        qqchMeasureExpTechVo.setType("1");
        qqchMeasureExpTechService.insertQqchMeasureExpTechList(qqchMeasureExpTechVo);
        return AjaxResult.success();
    }

    /**
     * 试验方案计划保存/确认/提交
     *
     * @param qqchMeasureExpTechVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMeasureExpTech:add")
    @PostMapping("/batchSaveExperiment")
    @CustomLogger(title = "前期策划-前期策划编制-施工技术策划-3.7 试验管理计划", name = "\n" +
            "3.7.3 实验方案计划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSaveExperiment(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureExpTechVo qqchMeasureExpTechVo) {
        qqchMeasureExpTechVo.setType("2");
        qqchMeasureExpTechService.insertQqchMeasureExpTechList(qqchMeasureExpTechVo);
        return AjaxResult.success();
    }
}
