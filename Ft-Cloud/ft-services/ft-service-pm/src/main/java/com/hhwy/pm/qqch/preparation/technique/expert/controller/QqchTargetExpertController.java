package com.hhwy.pm.qqch.preparation.technique.expert.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetExpert;
import com.hhwy.pm.qqch.preparation.technique.expert.domain.vo.QqchTargetExpertVo;
import com.hhwy.pm.qqch.preparation.technique.expert.service.IQqchTargetExpertService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:56:35
 * @remark 内外部目标专家选择
 */
@Validated
@RestController
@RequestMapping("/qqchTargetExpert")
public class QqchTargetExpertController extends BaseController {

    @Autowired
    private IQqchTargetExpertService qqchTargetExpertService;


//    @PreAuthorize(hasPermi = "qqchTargetExpert:list")
    @GetMapping
    public AjaxResult getQqchTargetExpert(@Validated(ValidationGroups.Get.class) QqchTargetExpert qqchTargetExpertParam) {
        QqchTargetExpert qqchTargetExpert = qqchTargetExpertService.getQqchTargetExpert(qqchTargetExpertParam);
        return AjaxResult.success(qqchTargetExpert);
    }

//    @PreAuthorize(hasPermi = "qqchTargetExpert:list")
    @GetMapping("/list")
    public AjaxResult getQqchTargetExpertList(@Validated(ValidationGroups.Select.class) QqchTargetExpert qqchTargetExpertParam) {
        startPage();
        List<QqchTargetExpert> qqchTargetExpertList = qqchTargetExpertService.getQqchTargetExpertList(qqchTargetExpertParam);
        return getDataTableAjaxResult(qqchTargetExpertList);
    }

//    @PreAuthorize(hasPermi = "qqchTargetExpert:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTargetExpert(@Validated(ValidationGroups.Save.class) @RequestBody QqchTargetExpert qqchTargetExpertParam) {
        qqchTargetExpertService.insertQqchTargetExpert(qqchTargetExpertParam);
        return AjaxResult.success(qqchTargetExpertParam);
    }

//    @PreAuthorize(hasPermi = "qqchTargetExpert:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTargetExpert(@Validated(ValidationGroups.Update.class) @RequestBody QqchTargetExpert qqchTargetExpertParam) {
        return toAjax(qqchTargetExpertService.updateQqchTargetExpert(qqchTargetExpertParam));
    }

//    @PreAuthorize(hasPermi = "qqchTargetExpert:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTargetExpertList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTargetExpert> qqchTargetExpertListParam) {
        return toAjax(qqchTargetExpertService.updateQqchTargetExpertList(qqchTargetExpertListParam));
    }

//    @PreAuthorize(hasPermi = "qqchTargetExpert:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTargetExpert(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTargetExpert qqchTargetExpertParam) {
        return toAjax(qqchTargetExpertService.deleteQqchTargetExpert(qqchTargetExpertParam));
    }

//    @PreAuthorize(hasPermi = "qqchTargetExpert:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTargetExpertByPks(@PathVariable Long[] ids) {
        List<Long> qqchTargetExpertPkList = Arrays.asList(ids);
        return toAjax(qqchTargetExpertService.deleteQqchTargetExpertByPks(qqchTargetExpertPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTargetExpert qqchTargetExpertParam) throws IOException {
        List<QqchTargetExpert> qqchTargetExpertList = qqchTargetExpertService.getQqchTargetExpertList(qqchTargetExpertParam);
        ExcelUtils<QqchTargetExpert> util = new ExcelUtils<>(QqchTargetExpert.class);
        util.exportExcel(response, qqchTargetExpertList, DateUtils.getDate());
    }

    /**
     * 获取内外部目标专家选择Vo
     * @param qqchTargetExpert
     * @return
     */
    @GetMapping("getQqchTargetExpertVo")
    public AjaxResult getQqchTargetExpertVo(@Validated(ValidationGroups.Select.class) QqchTargetExpert qqchTargetExpert) {
        QqchTargetExpertVo qqchTargetExpertVo = qqchTargetExpertService.getQqchTargetExpertVo(qqchTargetExpert);
        return AjaxResult.success(qqchTargetExpertVo);
    }

    /**
     * 添加到专家库
     * @param list
     * @return
     */
    @PostMapping("addToQyzsSpeciallistLibrary")
    public AjaxResult addToQyzsSpeciallistLibrary(@RequestBody List<QqchTargetExpert> list){
        qqchTargetExpertService.addToQyzsSpeciallistLibrary(list);
        return AjaxResult.success();
    }

    /**
     * 保存/确认/提交
     * @param qqchTargetExpertVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchTargetExpert:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchTargetExpertVo qqchTargetExpertVo) {
        qqchTargetExpertService.save(qqchTargetExpertVo);
        return AjaxResult.success();
    }
}
