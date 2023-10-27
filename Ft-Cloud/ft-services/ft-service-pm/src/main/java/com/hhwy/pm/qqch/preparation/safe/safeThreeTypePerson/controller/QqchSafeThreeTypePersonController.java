package com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.domain.QqchSafeThreeTypePerson;
import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.service.IQqchSafeThreeTypePersonService;
import com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.vo.QqchSafeThreeTypePersonVo;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import com.hhwy.common.security.annotation.PreAuthorize;

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


//    @PreAuthorize(hasPermi = "qqchSafeThreeTypePerson:list")
    @GetMapping
    public AjaxResult getQqchSafeThreeTypePerson(@Validated(ValidationGroups.Get.class) QqchSafeThreeTypePerson qqchSafeThreeTypePersonParam) {
        QqchSafeThreeTypePerson qqchSafeThreeTypePerson = qqchSafeThreeTypePersonService.getQqchSafeThreeTypePerson(qqchSafeThreeTypePersonParam);
        return AjaxResult.success(qqchSafeThreeTypePerson);
    }

//    @PreAuthorize(hasPermi = "qqchSafeThreeTypePerson:list")
    @GetMapping("/list")
    public AjaxResult getQqchSafeThreeTypePersonList(@Validated(ValidationGroups.Select.class) QqchSafeThreeTypePerson qqchSafeThreeTypePersonParam) {
        QqchSafeThreeTypePersonVo personList = qqchSafeThreeTypePersonService.getQqchSafeThreeTypePersonList(qqchSafeThreeTypePersonParam);
        return AjaxResult.success(personList);
    }

//    @PreAuthorize(hasPermi = "qqchSafeThreeTypePerson:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSafeThreeTypePersonList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeThreeTypePersonVo qqchSafeThreeTypePersonVo) {
        qqchSafeThreeTypePersonService.insertQqchSafeThreeTypePersonList(qqchSafeThreeTypePersonVo);
        return AjaxResult.success();
    }

    @PostMapping("/batchRefresh")
    public AjaxResult batchRefresh(@RequestBody QqchSafeThreeTypePersonVo qqchSafeThreeTypePersonVo) {
        qqchSafeThreeTypePersonService.batchRefresh(qqchSafeThreeTypePersonVo);
        return AjaxResult.success();
    }
}
