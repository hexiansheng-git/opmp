package com.hhwy.pm.qqch.preparation.safe.organ.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchGridDivideVo;
import com.hhwy.pm.qqch.preparation.safe.organ.service.IQqchGridDivideService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-07 13:52:31
 * @remark 8.1.4 格子划分
 */
@Validated
@RestController
@RequestMapping("/qqchGridDivide")
public class QqchGridDivideController extends BaseController {

    @Autowired
    private IQqchGridDivideService qqchGridDivideService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchGridDivide:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchGridDivideVo qqchGridDivideVo = qqchGridDivideService.getQqchGridDivideList(version);
        return AjaxResult.success(qqchGridDivideVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchGridDivideVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchGridDivide:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.1 安全组织机构及人员策划", name = "\n" +
            "8.1.4 格子划分" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody QqchGridDivideVo qqchGridDivideVo) {
        qqchGridDivideService.batchSave(qqchGridDivideVo);
        return AjaxResult.success();
    }
}
