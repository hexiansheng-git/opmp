package com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.domain.QqchQualityRiskList;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.domain.vo.QqchQualityRiskListVo;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.service.IQqchQualityRiskListService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 10:02:39
 * @remark
 * 9.3.1 质量风险清单
 */
@Validated
@RestController
@RequestMapping("/qqchQualityRiskList")
public class QqchQualityRiskListController extends BaseController {

    @Autowired
    private IQqchQualityRiskListService qqchQualityRiskListService;


    /**
     * 列表接口
     *
     * @param qqchQualityRiskListParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchQualityRiskList:list")
    @GetMapping("/list")
    public AjaxResult getQqchQualityRiskListList(@Validated(ValidationGroups.Select.class) QqchQualityRiskList qqchQualityRiskListParam) {
        QqchQualityRiskListVo vo = qqchQualityRiskListService.getQqchQualityRiskListList(qqchQualityRiskListParam);
        return AjaxResult.success(vo);
    }

    /**
     * 保存/确认/提交
     *
     * @param vo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchQualityRiskList:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchQualityRiskListVo vo) {
        qqchQualityRiskListService.save(vo);
        return AjaxResult.success();
    }


    //    @PreAuthorize(hasPermi = "qqchQualityRiskList:list")
    @GetMapping
    public AjaxResult getQqchQualityRiskList(@Validated(ValidationGroups.Get.class) QqchQualityRiskList qqchQualityRiskListParam) {
        QqchQualityRiskList qqchQualityRiskList = qqchQualityRiskListService.getQqchQualityRiskList(qqchQualityRiskListParam);
        return AjaxResult.success(qqchQualityRiskList);
    }


    //    @PreAuthorize(hasPermi = "qqchQualityRiskList:add")
    @PostMapping("/add")
    public AjaxResult insertQqchQualityRiskList(@Validated(ValidationGroups.Save.class) @RequestBody QqchQualityRiskList qqchQualityRiskListParam) {
        qqchQualityRiskListService.insertQqchQualityRiskList(qqchQualityRiskListParam);
        return AjaxResult.success(qqchQualityRiskListParam);
    }


    //    @PreAuthorize(hasPermi = "qqchQualityRiskList:update")
    @PostMapping("/update")
    public AjaxResult updateQqchQualityRiskList(@Validated(ValidationGroups.Update.class) @RequestBody QqchQualityRiskList qqchQualityRiskListParam) {
        return toAjax(qqchQualityRiskListService.updateQqchQualityRiskList(qqchQualityRiskListParam));
    }

//    @PreAuthorize(hasPermi = "qqchQualityRiskList:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchQualityRiskListList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchQualityRiskList> qqchQualityRiskListListParam) {
        return toAjax(qqchQualityRiskListService.updateQqchQualityRiskListList(qqchQualityRiskListListParam));
    }

//    @PreAuthorize(hasPermi = "qqchQualityRiskList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchQualityRiskList(@Validated(ValidationGroups.Delete.class) @RequestBody QqchQualityRiskList qqchQualityRiskListParam) {
        return toAjax(qqchQualityRiskListService.deleteQqchQualityRiskList(qqchQualityRiskListParam));
    }

//    @PreAuthorize(hasPermi = "qqchQualityRiskList:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchQualityRiskListByPks(@PathVariable Long[] ids) {
        List<Long> qqchQualityRiskListPkList = Arrays.asList(ids);
        return toAjax(qqchQualityRiskListService.deleteQqchQualityRiskListByPks(qqchQualityRiskListPkList));
    }


}
