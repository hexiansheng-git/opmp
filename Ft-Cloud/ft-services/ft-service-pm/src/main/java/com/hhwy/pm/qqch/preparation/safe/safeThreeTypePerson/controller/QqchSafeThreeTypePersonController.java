package com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.domain.QqchSafeThreeTypePerson;
import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.service.IQqchSafeThreeTypePersonService;
import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.vo.QqchSafeThreeTypePersonVo;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author zq
 * @date 2023-08-08 17:22:03
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchSafeThreeTypePerson")
public class QqchSafeThreeTypePersonController extends BaseController {

    @Autowired
    private IQqchSafeThreeTypePersonService qqchSafeThreeTypePersonService;


    @PreAuthorize(hasPermi = "qqchSafeThreeTypePerson:list")
    @GetMapping
    public AjaxResult getQqchSafeThreeTypePerson(@Validated(ValidationGroups.Get.class) QqchSafeThreeTypePerson qqchSafeThreeTypePersonParam) {
        QqchSafeThreeTypePerson qqchSafeThreeTypePerson = qqchSafeThreeTypePersonService.getQqchSafeThreeTypePerson(qqchSafeThreeTypePersonParam);
        return AjaxResult.success(qqchSafeThreeTypePerson);
    }

    @PreAuthorize(hasPermi = "qqchSafeThreeTypePerson:list")
    @GetMapping("/list")
    public AjaxResult getQqchSafeThreeTypePersonList(@Validated(ValidationGroups.Select.class) QqchSafeThreeTypePerson qqchSafeThreeTypePersonParam) {
        List<QqchSafeThreeTypePerson> qqchSafeThreeTypePersonList = qqchSafeThreeTypePersonService.getQqchSafeThreeTypePersonList(qqchSafeThreeTypePersonParam);
        return getDataTableAjaxResult(qqchSafeThreeTypePersonList);
    }

    @PreAuthorize(hasPermi = "qqchSafeThreeTypePerson:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSafeThreeTypePersonList(@RequestBody QqchSafeThreeTypePersonVo qqchSafeThreeTypePersonVo) {
        qqchSafeThreeTypePersonService.insertQqchSafeThreeTypePersonList(qqchSafeThreeTypePersonVo);
        return AjaxResult.success();
    }
}
