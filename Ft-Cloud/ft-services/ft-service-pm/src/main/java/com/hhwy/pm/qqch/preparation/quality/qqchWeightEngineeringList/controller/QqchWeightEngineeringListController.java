package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.QqchWeightEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo.QqchWeightEngineeringListHistory;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo.QqchWeightEngineeringListVo;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service.IQqchWeightEngineeringListService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ldd
 * @date 2023-08-04 14:24:06
 * @remark 9.4.1 重难点工程清单
 */
@Validated
@RestController
@RequestMapping("/qqchWeightEngineeringList")
public class QqchWeightEngineeringListController extends BaseController {

    @Autowired
    private IQqchWeightEngineeringListService qqchWeightEngineeringListService;


//    @PreAuthorize(hasPermi = "qqchWeightEngineeringList:list")
    @GetMapping("/list")
    public AjaxResult getQqchWeightEngineeringListList(@Validated(ValidationGroups.Select.class) QqchWeightEngineeringList qqchWeightEngineeringListParam) {
       QqchWeightEngineeringListVo vo = qqchWeightEngineeringListService.getQqchWeightEngineeringListList(qqchWeightEngineeringListParam);
        return AjaxResult.success(vo);
    }


//    @PreAuthorize(hasPermi = "qqchWeightEngineeringList:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchWeightEngineeringListVo vo) {
        qqchWeightEngineeringListService.save(vo);
        return AjaxResult.success();
    }


//    @PreAuthorize(hasPermi = "qqchWeightEngineeringList:list")
    @GetMapping
    public AjaxResult getQqchWeightEngineeringList(@Validated(ValidationGroups.Get.class) QqchWeightEngineeringList qqchWeightEngineeringListParam) {
        QqchWeightEngineeringList qqchWeightEngineeringList = qqchWeightEngineeringListService.getQqchWeightEngineeringList(qqchWeightEngineeringListParam);
        return AjaxResult.success(qqchWeightEngineeringList);
    }


//    @PreAuthorize(hasPermi = "qqchWeightEngineeringList:add")
    @PostMapping("/add")
    public AjaxResult insertQqchWeightEngineeringList(@Validated(ValidationGroups.Save.class) @RequestBody QqchWeightEngineeringList qqchWeightEngineeringListParam) {
        qqchWeightEngineeringListService.insertQqchWeightEngineeringList(qqchWeightEngineeringListParam);
        return AjaxResult.success(qqchWeightEngineeringListParam);
    }

//    @PreAuthorize(hasPermi = "qqchWeightEngineeringList:update")
    @PostMapping("/update")
    public AjaxResult updateQqchWeightEngineeringList(@Validated(ValidationGroups.Update.class) @RequestBody QqchWeightEngineeringList qqchWeightEngineeringListParam) {
        return toAjax(qqchWeightEngineeringListService.updateQqchWeightEngineeringList(qqchWeightEngineeringListParam));
    }

//    @PreAuthorize(hasPermi = "qqchWeightEngineeringList:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchWeightEngineeringListList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchWeightEngineeringList> qqchWeightEngineeringListListParam) {
        return toAjax(qqchWeightEngineeringListService.updateQqchWeightEngineeringListList(qqchWeightEngineeringListListParam));
    }

//    @PreAuthorize(hasPermi = "qqchWeightEngineeringList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchWeightEngineeringList(@Validated(ValidationGroups.Delete.class) @RequestBody QqchWeightEngineeringList qqchWeightEngineeringListParam) {
        return toAjax(qqchWeightEngineeringListService.deleteQqchWeightEngineeringList(qqchWeightEngineeringListParam));
    }

//    @PreAuthorize(hasPermi = "qqchWeightEngineeringList:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchWeightEngineeringListByPks(@PathVariable Long[] ids) {
        List<Long> qqchWeightEngineeringListPkList = Arrays.asList(ids);
        return toAjax(qqchWeightEngineeringListService.deleteQqchWeightEngineeringListByPks(qqchWeightEngineeringListPkList));
    }

    /**
     *  历史方案查询
     */
    @PostMapping("/querySameProject")
    public AjaxResult querySameProject(@Validated(ValidationGroups.Select.class) QqchWeightEngineeringListHistory param) {
        Map<String, List<QqchWeightEngineeringListHistory>> result = qqchWeightEngineeringListService.querySameProject(param);
        return AjaxResult.success(result);
    }


}
