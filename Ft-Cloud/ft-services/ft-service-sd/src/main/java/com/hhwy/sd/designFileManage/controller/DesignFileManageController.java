package com.hhwy.sd.designFileManage.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.designFileManage.domain.KcsjDesignFileManage;
import com.hhwy.sd.designFileManage.domain.KcsjDesignFileManageVo;
import com.hhwy.sd.designFileManage.domain.vo.KcsjDesignFileManageQueryVo;
import com.hhwy.sd.designFileManage.service.IKcsjDesignFileManageService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author zmh
 * @date 2023-12-19 11:06:31
 * @remark 勘察设计文件管理
 */
@Validated
@RestController
@RequestMapping("/kcsjDesignFileManage")
public class DesignFileManageController extends BaseController {

    @Autowired
    private IKcsjDesignFileManageService kcsjDesignFileManageService;


    @GetMapping
    public AjaxResult getKcsjDesignFileManage(@Validated(ValidationGroups.Get.class) KcsjDesignFileManage kcsjDesignFileManageParam) {
        KcsjDesignFileManage kcsjDesignFileManage = kcsjDesignFileManageService.getKcsjDesignFileManage(kcsjDesignFileManageParam);
        return AjaxResult.success(kcsjDesignFileManage);
    }

    /**
     * 列表list
     * @param queryVo
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjDesignFileManage:list")
    @GetMapping("/list")
    public AjaxResult getKcsjDesignFileManageList(@Validated(ValidationGroups.Select.class) KcsjDesignFileManageQueryVo queryVo) {
        KcsjDesignFileManageVo kcsjDesignFileManageVo = kcsjDesignFileManageService.getKcsjDesignFileManageList(queryVo);
        return AjaxResult.success(kcsjDesignFileManageVo);
    }

    @PreAuthorize(hasPermi = "kcsjDesignFileManage:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjDesignFileManage(@Validated(ValidationGroups.Save.class) @RequestBody KcsjDesignFileManage kcsjDesignFileManageParam) {
        kcsjDesignFileManageService.insertKcsjDesignFileManage(kcsjDesignFileManageParam);
        return AjaxResult.success(kcsjDesignFileManageParam);
    }

    /**
     * 批量新增
     * @param kcsjDesignFileManageVo
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjDesignFileManage:batchAdd")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjDesignFileManageList(@Validated(ValidationGroups.Save.class) @RequestBody KcsjDesignFileManageVo kcsjDesignFileManageVo) {
        AjaxResult ajaxResult =  kcsjDesignFileManageService.insertKcsjDesignFileManageList(kcsjDesignFileManageVo);
        return ajaxResult;
    }

    @PreAuthorize(hasPermi = "kcsjDesignFileManage:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjDesignFileManage(@Validated(ValidationGroups.Update.class) @RequestBody KcsjDesignFileManage kcsjDesignFileManageParam) {
        return toAjax(kcsjDesignFileManageService.updateKcsjDesignFileManage(kcsjDesignFileManageParam));
    }

    @PreAuthorize(hasPermi = "kcsjDesignFileManage:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjDesignFileManageList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjDesignFileManage> kcsjDesignFileManageListParam) {
        return toAjax(kcsjDesignFileManageService.updateKcsjDesignFileManageList(kcsjDesignFileManageListParam));
    }

    @PreAuthorize(hasPermi = "kcsjDesignFileManage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjDesignFileManage(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjDesignFileManage kcsjDesignFileManageParam) {
        return toAjax(kcsjDesignFileManageService.deleteKcsjDesignFileManage(kcsjDesignFileManageParam));
    }

    @PreAuthorize(hasPermi = "kcsjDesignFileManage:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjDesignFileManageByPks(@PathVariable Long[] ids) {
        List<Long> kcsjDesignFileManagePkList = Arrays.asList(ids);
        return toAjax(kcsjDesignFileManageService.deleteKcsjDesignFileManageByPks(kcsjDesignFileManagePkList));
    }
}
