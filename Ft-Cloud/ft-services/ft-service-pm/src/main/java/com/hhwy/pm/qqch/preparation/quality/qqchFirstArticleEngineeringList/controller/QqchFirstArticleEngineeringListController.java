package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.QqchFirstArticleEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.domain.vo.QqchFirstArticleEngineeringListVo;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringList.service.IQqchFirstArticleEngineeringListService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 16:09:42
 * @remark  9.5.1 首件工程清单
 */
@Validated
@RestController
@RequestMapping("/qqchFirstArticleEngineeringList")
public class QqchFirstArticleEngineeringListController extends BaseController {

    @Autowired
    private IQqchFirstArticleEngineeringListService qqchFirstArticleEngineeringListService;


    /**
     *  列表接口
     * @param qqchFirstArticleEngineeringListParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringList:list")
    @GetMapping("/list")
    public AjaxResult getQqchFirstArticleEngineeringListList(@Validated(ValidationGroups.Select.class) QqchFirstArticleEngineeringList qqchFirstArticleEngineeringListParam) {
        QqchFirstArticleEngineeringListVo vo=qqchFirstArticleEngineeringListService.getQqchFirstArticleEngineeringListList(qqchFirstArticleEngineeringListParam);
        return AjaxResult.success(vo);
    }


    /**
     *  保存/确认/提交
     * @param vo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringList:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchFirstArticleEngineeringListVo vo) {
        qqchFirstArticleEngineeringListService.save(vo);
        return AjaxResult.success();
    }




    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringList:list")
    @GetMapping
    public AjaxResult getQqchFirstArticleEngineeringList(@Validated(ValidationGroups.Get.class) QqchFirstArticleEngineeringList qqchFirstArticleEngineeringListParam) {
        QqchFirstArticleEngineeringList qqchFirstArticleEngineeringList = qqchFirstArticleEngineeringListService.getQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringListParam);
        return AjaxResult.success(qqchFirstArticleEngineeringList);
    }



    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringList:add")
    @PostMapping("/add")
    public AjaxResult insertQqchFirstArticleEngineeringList(@Validated(ValidationGroups.Save.class) @RequestBody QqchFirstArticleEngineeringList qqchFirstArticleEngineeringListParam) {
        qqchFirstArticleEngineeringListService.insertQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringListParam);
        return AjaxResult.success(qqchFirstArticleEngineeringListParam);
    }


    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringList:update")
    @PostMapping("/update")
    public AjaxResult updateQqchFirstArticleEngineeringList(@Validated(ValidationGroups.Update.class) @RequestBody QqchFirstArticleEngineeringList qqchFirstArticleEngineeringListParam) {
        return toAjax(qqchFirstArticleEngineeringListService.updateQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringListParam));
    }

    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringList:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchFirstArticleEngineeringListList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchFirstArticleEngineeringList> qqchFirstArticleEngineeringListListParam) {
        return toAjax(qqchFirstArticleEngineeringListService.updateQqchFirstArticleEngineeringListList(qqchFirstArticleEngineeringListListParam));
    }

    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchFirstArticleEngineeringList(@Validated(ValidationGroups.Delete.class) @RequestBody QqchFirstArticleEngineeringList qqchFirstArticleEngineeringListParam) {
        return toAjax(qqchFirstArticleEngineeringListService.deleteQqchFirstArticleEngineeringList(qqchFirstArticleEngineeringListParam));
    }

    @PreAuthorize(hasPermi = "qqchFirstArticleEngineeringList:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchFirstArticleEngineeringListByPks(@PathVariable Long[] ids) {
        List<Long> qqchFirstArticleEngineeringListPkList = Arrays.asList(ids);
        return toAjax(qqchFirstArticleEngineeringListService.deleteQqchFirstArticleEngineeringListByPks(qqchFirstArticleEngineeringListPkList));
    }

}
