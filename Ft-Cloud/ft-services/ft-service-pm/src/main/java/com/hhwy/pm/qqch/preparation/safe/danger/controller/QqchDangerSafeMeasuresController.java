package com.hhwy.pm.qqch.preparation.safe.danger.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerSafeMeasuresVo;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerSafeMeasuresService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.3 危大工程管控策划", name = "\n" +
            "8.3.2 危大工程安全技术措施" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchDangerSafeMeasuresVo qqchDangerSafeMeasuresVo) {
        qqchDangerSafeMeasuresService.batchSave(qqchDangerSafeMeasuresVo);
        return AjaxResult.success();
    }

    @PostMapping("/sync")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.3 危大工程管控策划", name = "8.3.2 危大工程安全技术措施" 
            ,businessType = CustomBusinessType.SAVE)
    public AjaxResult sync(@RequestBody QqchDangerSafeMeasuresVo qqchDangerSafeMeasuresVo) {
        Assert.notNull(qqchDangerSafeMeasuresVo.getVersion(), "version不能为空");
        qqchDangerSafeMeasuresService.sync(qqchDangerSafeMeasuresVo.getVersion());
        return AjaxResult.success();
    }
}
