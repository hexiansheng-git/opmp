package com.hhwy.pm.qqch.preparation.safe.danger.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerSafeMeasuresVo;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerSafeMeasuresService;
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
 * @date 2023-08-07 14:23:10
 * @remark 8.3.2 危大工程安全技术措施
 */
@Validated
@RestController
@RequestMapping("/qqchDangerSafeMeasures")
public class QqchDangerSafeMeasuresController extends BaseController {

    @Autowired
    private IQqchDangerSafeMeasuresService qqchDangerSafeMeasuresService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDangerSafeMeasures:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchDangerSafeMeasuresVo qqchDangerSafeMeasuresVo = qqchDangerSafeMeasuresService
            .getQqchDangerSafeMeasuresList(version);
        return AjaxResult.success(qqchDangerSafeMeasuresVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchDangerSafeMeasuresVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDangerSafeMeasures:update")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Update.class) @RequestBody QqchDangerSafeMeasuresVo qqchDangerSafeMeasuresVo) {
        qqchDangerSafeMeasuresService.batchSave(qqchDangerSafeMeasuresVo);
        return AjaxResult.success();
    }
}
