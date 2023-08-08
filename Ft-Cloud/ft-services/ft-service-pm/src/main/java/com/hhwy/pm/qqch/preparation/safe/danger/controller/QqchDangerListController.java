package com.hhwy.pm.qqch.preparation.safe.danger.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerListVo;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerListService;
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
 * @date 2023-08-07 14:22:57
 * @remark 8.3.1 危大工程清单
 */
@Validated
@RestController
@RequestMapping("/qqchDangerList")
public class QqchDangerListController extends BaseController {

    @Autowired
    private IQqchDangerListService qqchDangerListService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDangerList:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchDangerListVo qqchDangerListVo = qqchDangerListService.getQqchDangerListList(version);
        return AjaxResult.success(qqchDangerListVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchDangerListVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDangerList:update")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Update.class) @RequestBody QqchDangerListVo qqchDangerListVo) {
        qqchDangerListService.updateQqchDangerListList(qqchDangerListVo);
        return AjaxResult.success();
    }
}
