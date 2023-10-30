package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.domain.QqchSpecialBigProcessControl;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.domain.vo.QqchSpecialBigProcessControlVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.service.IQqchSpecialBigProcessControlService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 16:17:26
 * @remark 8.4.3 特种设备及大型设备过程管控策划
 */
@Validated
@RestController
@RequestMapping("/qqchSpecialBigProcessControl")
public class QqchSpecialBigProcessControlController extends BaseController {

    @Autowired
    private IQqchSpecialBigProcessControlService qqchSpecialBigProcessControlService;

    /**
     *  列表接口
     * @param qqchSpecialBigProcessControlParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSpecialBigProcessControl:list")
    @GetMapping("/list")
    public AjaxResult getQqchSpecialBigProcessControlList(@Validated(ValidationGroups.Select.class) QqchSpecialBigProcessControl qqchSpecialBigProcessControlParam) {
        QqchSpecialBigProcessControlVo vo = qqchSpecialBigProcessControlService.getQqchSpecialBigProcessControlList(qqchSpecialBigProcessControlParam);
        return AjaxResult.success(vo);
    }


//    @PreAuthorize(hasPermi = "qqchSpecialBigProcessControl:save")
    @PostMapping("/save")
    public AjaxResult insertQqchSpecialBigProcessControlList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSpecialBigProcessControlVo vo) {
        qqchSpecialBigProcessControlService.save(vo);
        return AjaxResult.success();
    }


//    @PreAuthorize(hasPermi = "qqchSpecialBigProcessControl:list")
    @GetMapping
    public AjaxResult getQqchSpecialBigProcessControl(@Validated(ValidationGroups.Get.class) QqchSpecialBigProcessControl qqchSpecialBigProcessControlParam) {
        QqchSpecialBigProcessControl qqchSpecialBigProcessControl = qqchSpecialBigProcessControlService.getQqchSpecialBigProcessControl(qqchSpecialBigProcessControlParam);
        return AjaxResult.success(qqchSpecialBigProcessControl);
    }


//    @PreAuthorize(hasPermi = "qqchSpecialBigProcessControl:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSpecialBigProcessControl(@Validated(ValidationGroups.Save.class) @RequestBody QqchSpecialBigProcessControl qqchSpecialBigProcessControlParam) {
        qqchSpecialBigProcessControlService.insertQqchSpecialBigProcessControl(qqchSpecialBigProcessControlParam);
        return AjaxResult.success(qqchSpecialBigProcessControlParam);
    }


//    @PreAuthorize(hasPermi = "qqchSpecialBigProcessControl:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSpecialBigProcessControl(@Validated(ValidationGroups.Update.class) @RequestBody QqchSpecialBigProcessControl qqchSpecialBigProcessControlParam) {
        return toAjax(qqchSpecialBigProcessControlService.updateQqchSpecialBigProcessControl(qqchSpecialBigProcessControlParam));
    }

//    @PreAuthorize(hasPermi = "qqchSpecialBigProcessControl:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSpecialBigProcessControlList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSpecialBigProcessControl> qqchSpecialBigProcessControlListParam) {
        return toAjax(qqchSpecialBigProcessControlService.updateQqchSpecialBigProcessControlList(qqchSpecialBigProcessControlListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSpecialBigProcessControl:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSpecialBigProcessControl(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSpecialBigProcessControl qqchSpecialBigProcessControlParam) {
        return toAjax(qqchSpecialBigProcessControlService.deleteQqchSpecialBigProcessControl(qqchSpecialBigProcessControlParam));
    }

//    @PreAuthorize(hasPermi = "qqchSpecialBigProcessControl:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSpecialBigProcessControlByPks(@PathVariable Long[] ids) {
        List<Long> qqchSpecialBigProcessControlPkList = Arrays.asList(ids);
        return toAjax(qqchSpecialBigProcessControlService.deleteQqchSpecialBigProcessControlByPks(qqchSpecialBigProcessControlPkList));
    }
}
