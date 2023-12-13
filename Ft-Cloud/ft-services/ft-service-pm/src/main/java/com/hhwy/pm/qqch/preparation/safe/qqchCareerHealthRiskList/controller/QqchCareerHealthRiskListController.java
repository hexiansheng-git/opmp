package com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.domain.QqchCareerHealthRiskList;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.domain.vo.QqchCareerHealthRiskListVo;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.service.IQqchCareerHealthRiskListService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 15:01:27
 * @remark 8.7.1 职业健康风险清单
 */
@Validated
@RestController
@RequestMapping("/qqchCareerHealthRiskList")
public class QqchCareerHealthRiskListController extends BaseController {

    @Autowired
    private IQqchCareerHealthRiskListService qqchCareerHealthRiskListService;

    /**
     * 列表接口
     *
     * @param qqchCareerHealthRiskListParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchCareerHealthRiskList:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-职业健康策划", name = "8.7.1职业健康风险清单" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchCareerHealthRiskListList(@Validated(ValidationGroups.Select.class) QqchCareerHealthRiskList qqchCareerHealthRiskListParam) {
        QqchCareerHealthRiskListVo vo = qqchCareerHealthRiskListService.getQqchCareerHealthRiskListList(qqchCareerHealthRiskListParam);
        return AjaxResult.success(vo);
    }

//    @PreAuthorize(hasPermi = "qqchCareerHealthRiskList:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-职业健康策划", name = "8.7.1职业健康风险清单" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchCareerHealthRiskListList(@Validated(ValidationGroups.Save.class) @RequestBody QqchCareerHealthRiskListVo vo) {
        qqchCareerHealthRiskListService.save(vo);
        return AjaxResult.success();
    }


//    @PreAuthorize(hasPermi = "qqchCareerHealthRiskList:list")
    @GetMapping
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-职业健康策划", name = "8.7.1职业健康风险清单" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchCareerHealthRiskList(@Validated(ValidationGroups.Get.class) QqchCareerHealthRiskList qqchCareerHealthRiskListParam) {
        QqchCareerHealthRiskList qqchCareerHealthRiskList = qqchCareerHealthRiskListService.getQqchCareerHealthRiskList(qqchCareerHealthRiskListParam);
        return AjaxResult.success(qqchCareerHealthRiskList);
    }


//    @PreAuthorize(hasPermi = "qqchCareerHealthRiskList:add")
    @PostMapping("/add")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-职业健康策划", name = "8.7.1职业健康风险清单" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchCareerHealthRiskList(@Validated(ValidationGroups.Save.class) @RequestBody QqchCareerHealthRiskList qqchCareerHealthRiskListParam) {
        qqchCareerHealthRiskListService.insertQqchCareerHealthRiskList(qqchCareerHealthRiskListParam);
        return AjaxResult.success(qqchCareerHealthRiskListParam);
    }


//    @PreAuthorize(hasPermi = "qqchCareerHealthRiskList:update")
    @PostMapping("/update")
    public AjaxResult updateQqchCareerHealthRiskList(@Validated(ValidationGroups.Update.class) @RequestBody QqchCareerHealthRiskList qqchCareerHealthRiskListParam) {
        return toAjax(qqchCareerHealthRiskListService.updateQqchCareerHealthRiskList(qqchCareerHealthRiskListParam));
    }

//    @PreAuthorize(hasPermi = "qqchCareerHealthRiskList:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchCareerHealthRiskListList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchCareerHealthRiskList> qqchCareerHealthRiskListListParam) {
        return toAjax(qqchCareerHealthRiskListService.updateQqchCareerHealthRiskListList(qqchCareerHealthRiskListListParam));
    }

//    @PreAuthorize(hasPermi = "qqchCareerHealthRiskList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchCareerHealthRiskList(@Validated(ValidationGroups.Delete.class) @RequestBody QqchCareerHealthRiskList qqchCareerHealthRiskListParam) {
        return toAjax(qqchCareerHealthRiskListService.deleteQqchCareerHealthRiskList(qqchCareerHealthRiskListParam));
    }


}
