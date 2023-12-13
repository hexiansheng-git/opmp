package com.hhwy.pm.qqch.preparation.technique.disclose.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThirdDetail;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo.QqchDiscloseThirdVo;
import com.hhwy.pm.qqch.preparation.technique.disclose.service.IQqchDiscloseThirdService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-21 14:26:42
 * @remark 3.5.2三级交底
 */
@Validated
@RestController
@RequestMapping("/qqchDiscloseThird")
public class QqchDiscloseThirdController extends BaseController {

    @Autowired
    private IQqchDiscloseThirdService qqchDiscloseThirdService;

//    @PreAuthorize(hasPermi = "qqchDiscloseThird:list")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(BigDecimal version) {
        QqchDiscloseThirdVo qqchDiscloseThirdVo = qqchDiscloseThirdService.getQqchDiscloseThirdList(version);
        return AjaxResult.success(qqchDiscloseThirdVo);
    }

    /**
     * 交底明细
     * @param masterId
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchDiscloseThird:list")
    @GetMapping("/getDetailList")
    public AjaxResult getDetailList(Long masterId) {
        List<QqchDiscloseThirdDetail> list = qqchDiscloseThirdService.getDetailList(masterId);
        return AjaxResult.success(list);
    }

//    @PreAuthorize(hasPermi = "qqchDiscloseThird:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-施工技术策划-3.5 交底清单及记录", name = "\n" +
            "3.5.2 三级交底" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchDiscloseThirdVo qqchDiscloseThirdVo) {
        qqchDiscloseThirdService.save(qqchDiscloseThirdVo);
        return AjaxResult.success();
    }
}
