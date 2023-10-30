package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.QqchFirstArticleEngineeringControl;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.vo.QqchFirstArticleEngineeringControlVo;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.service.IQqchFirstArticleEngineeringControlService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 17:06:12
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchFirstArticleEngineeringControl")
public class QqchFirstArticleEngineeringControlController extends BaseController {

    @Autowired
    private IQqchFirstArticleEngineeringControlService qqchFirstArticleEngineeringControlService;


    /**
     * 列表接口
     *
     * @param qqchFirstArticleEngineeringControlParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:list")
    @GetMapping("/list")
    public AjaxResult getQqchFirstArticleEngineeringControlList(@Validated(ValidationGroups.Select.class) QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControlParam) {
        QqchFirstArticleEngineeringControlVo vo = qqchFirstArticleEngineeringControlService.getQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlParam);
        return AjaxResult.success(vo);
    }

    /**
     * 保存/确认/提交
     *
     * @param vo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:save")
    @PostMapping("/save")
    public AjaxResult insertQqchFirstArticleEngineeringControlList(@Validated(ValidationGroups.Save.class) @RequestBody QqchFirstArticleEngineeringControlVo vo) {
        qqchFirstArticleEngineeringControlService.save(vo);
        return AjaxResult.success();
    }


//    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:list")
    @GetMapping
    public AjaxResult getQqchFirstArticleEngineeringControl(@Validated(ValidationGroups.Get.class) QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControlParam) {
        QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl = qqchFirstArticleEngineeringControlService.getQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControlParam);
        return AjaxResult.success(qqchFirstArticleEngineeringControl);
    }

//    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:add")
    @PostMapping("/add")
    public AjaxResult insertQqchFirstArticleEngineeringControl(@Validated(ValidationGroups.Save.class) @RequestBody QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControlParam) {
        qqchFirstArticleEngineeringControlService.insertQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControlParam);
        return AjaxResult.success(qqchFirstArticleEngineeringControlParam);
    }


//    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:update")
    @PostMapping("/update")
    public AjaxResult updateQqchFirstArticleEngineeringControl(@Validated(ValidationGroups.Update.class) @RequestBody QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControlParam) {
        return toAjax(qqchFirstArticleEngineeringControlService.updateQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControlParam));
    }

//    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchFirstArticleEngineeringControlList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlListParam) {
        return toAjax(qqchFirstArticleEngineeringControlService.updateQqchFirstArticleEngineeringControlList(qqchFirstArticleEngineeringControlListParam));
    }

//    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchFirstArticleEngineeringControl(@Validated(ValidationGroups.Delete.class) @RequestBody QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControlParam) {
        return toAjax(qqchFirstArticleEngineeringControlService.deleteQqchFirstArticleEngineeringControl(qqchFirstArticleEngineeringControlParam));
    }

//    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringControl:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchFirstArticleEngineeringControlByPks(@PathVariable Long[] ids) {
        List<Long> qqchFirstArticleEngineeringControlPkList = Arrays.asList(ids);
        return toAjax(qqchFirstArticleEngineeringControlService.deleteQqchFirstArticleEngineeringControlByPks(qqchFirstArticleEngineeringControlPkList));
    }

}
