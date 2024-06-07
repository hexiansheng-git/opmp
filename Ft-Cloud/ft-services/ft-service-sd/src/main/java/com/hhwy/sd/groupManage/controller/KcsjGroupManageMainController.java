package com.hhwy.sd.groupManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.groupManage.domain.KcsjGroupManageMain;
import com.hhwy.sd.groupManage.domain.vo.KcsjGroupManageMainVo;
import com.hhwy.sd.groupManage.service.IKcsjGroupManageMainService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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
 * @date 2023-12-13 15:27:15
 * @remark 勘察设计队伍管理-主表
 */
@Validated
@RestController
@RequestMapping("/kcsjGroupManageMain")
public class KcsjGroupManageMainController extends BaseController {

    @Autowired
    private IKcsjGroupManageMainService kcsjGroupManageMainService;


    @PreAuthorize(hasPermi = "kcsjGroupManageMain:list")
    @GetMapping
    public AjaxResult getKcsjGroupManageMain(@Validated(ValidationGroups.Get.class) KcsjGroupManageMain kcsjGroupManageMainParam) {
        KcsjGroupManageMain kcsjGroupManageMain = kcsjGroupManageMainService.getKcsjGroupManageMain(kcsjGroupManageMainParam);
        return AjaxResult.success(kcsjGroupManageMain);
    }

    /**
     * 页面数据
     * @param kcsjGroupManageMain
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjGroupManageMain:list")
    @GetMapping("/getKcsjGroupManageMainVo")
    public AjaxResult getKcsjGroupManageMainVo(@Validated(ValidationGroups.Select.class) KcsjGroupManageMain kcsjGroupManageMain) {
        KcsjGroupManageMainVo kcsjGroupManageMainVo = kcsjGroupManageMainService.getKcsjGroupManageMainVo(kcsjGroupManageMain);
        return AjaxResult.success(kcsjGroupManageMainVo);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageMain:save")
    @PostMapping("/save")
    @CustomLogger(title = "勘察设计-勘察设计单位管理",name = "勘察设计单位管理",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertKcsjGroupManageMain(@RequestBody KcsjGroupManageMainVo kcsjGroupManageMainVo) {
        kcsjGroupManageMainService.save(kcsjGroupManageMainVo);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageMain:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "勘察设计-勘察设计单位管理",name = "勘察设计单位管理",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertKcsjGroupManageMainList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjGroupManageMain> kcsjGroupManageMainListParam) {
        kcsjGroupManageMainService.insertKcsjGroupManageMainList(kcsjGroupManageMainListParam);
        return AjaxResult.success(kcsjGroupManageMainListParam);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageMain:update")
    @PostMapping("/update")
    @CustomLogger(title = "勘察设计-勘察设计单位管理",name = "勘察设计单位管理",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateKcsjGroupManageMain(@Validated(ValidationGroups.Update.class) @RequestBody KcsjGroupManageMain kcsjGroupManageMainParam) {
        return toAjax(kcsjGroupManageMainService.updateKcsjGroupManageMain(kcsjGroupManageMainParam));
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageMain:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "勘察设计-勘察设计单位管理",name = "勘察设计单位管理",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateKcsjGroupManageMainList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjGroupManageMain> kcsjGroupManageMainListParam) {
        return toAjax(kcsjGroupManageMainService.updateKcsjGroupManageMainList(kcsjGroupManageMainListParam));
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageMain:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjGroupManageMain(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjGroupManageMain kcsjGroupManageMainParam) {
        return toAjax(kcsjGroupManageMainService.deleteKcsjGroupManageMain(kcsjGroupManageMainParam));
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageMain:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjGroupManageMainByPks(@PathVariable Long[] ids) {
        List<Long> kcsjGroupManageMainPkList = Arrays.asList(ids);
        return toAjax(kcsjGroupManageMainService.deleteKcsjGroupManageMainByPks(kcsjGroupManageMainPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjGroupManageMain kcsjGroupManageMainParam) throws IOException {
        List<KcsjGroupManageMain> kcsjGroupManageMainList = kcsjGroupManageMainService.getKcsjGroupManageMainList(kcsjGroupManageMainParam);
        ExcelUtils<KcsjGroupManageMain> util = new ExcelUtils<>(KcsjGroupManageMain.class);
        util.exportExcel(response, kcsjGroupManageMainList, DateUtils.getDate());
    }
}
