package com.hhwy.sp.techOrg.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManage;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageVo;
import com.hhwy.sp.techOrg.service.ISgjsTechnicalManageService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 技术管理机构管理
 *
 * @author lcf
 * @date 2023-11-17 11:29:23
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsTechnicalManage")
public class SgjsTechnicalManageController extends BaseController {

    @Autowired
    private ISgjsTechnicalManageService sgjsTechnicalManageService;

    @PreAuthorize(hasPermi = "sgjsTechnicalManage:list")
    @GetMapping
    public AjaxResult getSgjsTechnicalManage(@Validated(ValidationGroups.Get.class) SgjsTechnicalManage sgjsTechnicalManageParam) {
        SgjsTechnicalManage sgjsTechnicalManage = sgjsTechnicalManageService.getSgjsTechnicalManage(sgjsTechnicalManageParam);
        return AjaxResult.success(sgjsTechnicalManage);
    }

    /**
     * 列表
     *
     * @param sgjsTechnicalManageParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsTechnicalManage:list")
    @GetMapping("/list")
    public AjaxResult getSgjsTechnicalManageList(@Validated(ValidationGroups.Select.class) SgjsTechnicalManage sgjsTechnicalManageParam) {
        SgjsTechnicalManageVo vo = sgjsTechnicalManageService.list(sgjsTechnicalManageParam);
        return AjaxResult.success(vo);
    }

    /**
     * 新增
     *
     * @param sgjsTechnicalManageParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsTechnicalManage:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsTechnicalManage(@Validated(ValidationGroups.Save.class) @RequestBody SgjsTechnicalManage sgjsTechnicalManageParam) {
        sgjsTechnicalManageService.insertSgjsTechnicalManage(sgjsTechnicalManageParam);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalManage:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsTechnicalManageList(@Validated(ValidationGroups.Save.class) @RequestBody SgjsTechnicalManageVo sgjsTechnicalManageVo) {
        sgjsTechnicalManageService.batchAdd(sgjsTechnicalManageVo);
        return AjaxResult.success();
    }



    /**
     * 修改
     *
     * @param sgjsTechnicalManageParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsTechnicalManage:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsTechnicalManage(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechnicalManage sgjsTechnicalManageParam) {
        return toAjax(sgjsTechnicalManageService.updateSgjsTechnicalManage(sgjsTechnicalManageParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalManage:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsTechnicalManageList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsTechnicalManage> sgjsTechnicalManageListParam) {
        return toAjax(sgjsTechnicalManageService.updateSgjsTechnicalManageList(sgjsTechnicalManageListParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalManage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsTechnicalManage(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsTechnicalManage sgjsTechnicalManageParam) {
        return toAjax(sgjsTechnicalManageService.deleteSgjsTechnicalManage(sgjsTechnicalManageParam));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsTechnicalManage:remove")
    @PostMapping("/del")
    public AjaxResult deleteSgjsTechnicalManageByPks(@RequestBody List<Long> ids) {
        return toAjax(sgjsTechnicalManageService.deleteSgjsTechnicalManageByPks(ids));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsTechnicalManage sgjsTechnicalManageParam) throws IOException {
        List<SgjsTechnicalManage> sgjsTechnicalManageList = sgjsTechnicalManageService.getSgjsTechnicalManageList(sgjsTechnicalManageParam);
        ExcelUtils<SgjsTechnicalManage> util = new ExcelUtils<>(SgjsTechnicalManage.class);
        util.exportExcel(response, sgjsTechnicalManageList, DateUtils.getDate());
    }

    @GetMapping("/sync")
    public AjaxResult sync(){
        return sgjsTechnicalManageService.sync();
    }
}
