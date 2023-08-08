package com.hhwy.pm.qqch.preparation.safe.organ.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchGridDivideVo;
import com.hhwy.pm.qqch.preparation.safe.organ.service.IQqchGridDivideService;
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
    @PreAuthorize(hasPermi = "qqchGridDivide:list")
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
    @PreAuthorize(hasPermi = "qqchGridDivide:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchGridDivideVo qqchGridDivideVo) {
        qqchGridDivideService.batchSave(qqchGridDivideVo);
        return AjaxResult.success();
    }
}
