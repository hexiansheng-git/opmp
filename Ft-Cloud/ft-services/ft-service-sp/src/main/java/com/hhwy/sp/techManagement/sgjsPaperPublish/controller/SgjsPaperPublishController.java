package com.hhwy.sp.techManagement.sgjsPaperPublish.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.enums.FlowEnum;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.SgjsPaperPublish;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.vo.PaperPublishQueryVo;
import com.hhwy.sp.techManagement.sgjsPaperPublish.service.ISgjsPaperPublishService;
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
 * @date 2024-01-25 11:01:37
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsPaperPublish")
public class SgjsPaperPublishController extends BaseController {

    @Autowired
    private ISgjsPaperPublishService sgjsPaperPublishService;

    /**
     * 论文发表管理详情数据
     * @param id
     * @param type
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsPaperPublish:list")
    @GetMapping("getSgjsPaperPublishById")
    public AjaxResult getSgjsPaperPublishById(Long id,String type) {
        SgjsPaperPublish sgjsPaperPublish = sgjsPaperPublishService.getSgjsPaperPublishById(id,type);
        FlowInfoSearchUtil.getFlowInfo(sgjsPaperPublish, FlowEnum.SGJS_PAPER_PUBLISH);
        return AjaxResult.success(sgjsPaperPublish);
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublish:list")
    @GetMapping
    public AjaxResult getSgjsPaperPublish(@Validated(ValidationGroups.Get.class) SgjsPaperPublish sgjsPaperPublishParam) {
        SgjsPaperPublish sgjsPaperPublish = sgjsPaperPublishService.getSgjsPaperPublish(sgjsPaperPublishParam);
        return AjaxResult.success(sgjsPaperPublish);
    }

    /**
     * 论文发表管理数据列表
     * @param queryVo
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsPaperPublish:list")
    @GetMapping("/list")
    public AjaxResult getSgjsPaperPublishList(@Validated(ValidationGroups.Select.class) PaperPublishQueryVo queryVo) {
        startPage();
        List<SgjsPaperPublish> sgjsPaperPublishList = sgjsPaperPublishService.getSgjsPaperPublishList(queryVo);
        FlowInfoSearchUtil.getFlowInfo(sgjsPaperPublishList, FlowEnum.SGJS_PAPER_PUBLISH);
        return getDataTableAjaxResult(sgjsPaperPublishList);
    }

    /**
     * 保存
     * @param paperPublish
     * @return
     */
    @PostMapping("save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody SgjsPaperPublish paperPublish){
        sgjsPaperPublishService.save(paperPublish);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublish:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsPaperPublish(@Validated(ValidationGroups.Save.class) @RequestBody SgjsPaperPublish sgjsPaperPublishParam) {
        sgjsPaperPublishService.insertSgjsPaperPublish(sgjsPaperPublishParam);
        return AjaxResult.success(sgjsPaperPublishParam);
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublish:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsPaperPublishList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsPaperPublish> sgjsPaperPublishListParam) {
        sgjsPaperPublishService.insertSgjsPaperPublishList(sgjsPaperPublishListParam);
        return AjaxResult.success(sgjsPaperPublishListParam);
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublish:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsPaperPublish(@Validated(ValidationGroups.Update.class) @RequestBody SgjsPaperPublish sgjsPaperPublishParam) {
        return toAjax(sgjsPaperPublishService.updateSgjsPaperPublish(sgjsPaperPublishParam));
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublish:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsPaperPublishList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsPaperPublish> sgjsPaperPublishListParam) {
        return toAjax(sgjsPaperPublishService.updateSgjsPaperPublishList(sgjsPaperPublishListParam));
    }

    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsPatentDeclare:remove")
    @PostMapping("/deleteById/{id}")
    public AjaxResult deleteSgjsPaperPublishById(@PathVariable Long id) {
        sgjsPaperPublishService.deleteSgjsPaperPublishById(id);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublish:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsPaperPublish(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsPaperPublish sgjsPaperPublishParam) {
        return toAjax(sgjsPaperPublishService.deleteSgjsPaperPublish(sgjsPaperPublishParam));
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublish:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsPaperPublishByPks(@PathVariable Long[] ids) {
        List<Long> sgjsPaperPublishPkList = Arrays.asList(ids);
        return toAjax(sgjsPaperPublishService.deleteSgjsPaperPublishByPks(sgjsPaperPublishPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, PaperPublishQueryVo queryVo) throws IOException {
        List<SgjsPaperPublish> sgjsPaperPublishList = sgjsPaperPublishService.getSgjsPaperPublishList(queryVo);
        ExcelUtils<SgjsPaperPublish> util = new ExcelUtils<>(SgjsPaperPublish.class);
        util.exportExcel(response, sgjsPaperPublishList, DateUtils.getDate());
    }
}
