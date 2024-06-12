package com.hhwy.sd.designDocumentApproval.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApproval;
import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApprovalVo;
import com.hhwy.sd.designDocumentApproval.service.IKcsjDesignDocumentApprovalService;
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
 * @author wll
 * @date 2024-01-19 17:39:21
 * @remark 勘察设计-设计文件报批
 */
@Validated
@RestController
@RequestMapping("/kcsjDesignDocumentApproval")
public class KcsjDesignDocumentApprovalController extends BaseController {

    @Autowired
    private IKcsjDesignDocumentApprovalService kcsjDesignDocumentApprovalService;


    @PreAuthorize(hasPermi = "kcsjDesignDocumentApproval:list")
    @GetMapping
    public AjaxResult getKcsjDesignDocumentApproval(@Validated(ValidationGroups.Get.class) KcsjDesignDocumentApproval kcsjDesignDocumentApprovalParam) {
        KcsjDesignDocumentApproval kcsjDesignDocumentApproval = kcsjDesignDocumentApprovalService.getKcsjDesignDocumentApproval(kcsjDesignDocumentApprovalParam);
        return AjaxResult.success(kcsjDesignDocumentApproval);
    }

    /**
     * 分页列表查询
     *
     * @param kcsjDesignDocumentApprovalParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjDesignDocumentApproval:list")
    @GetMapping("/list")
    public AjaxResult getKcsjDesignDocumentApprovalList(@Validated(ValidationGroups.Select.class) KcsjDesignDocumentApproval kcsjDesignDocumentApprovalParam) {
        startPage();
        List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList = kcsjDesignDocumentApprovalService.getKcsjDesignDocumentApprovalList(kcsjDesignDocumentApprovalParam);
        return getDataTableAjaxResult(kcsjDesignDocumentApprovalList);
    }

    @PreAuthorize(hasPermi = "kcsjDesignDocumentApproval:add")
    @PostMapping("/add")
    @CustomLogger(title = "勘察设计-勘察设计文件报批",name = "勘察设计文件报批",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertKcsjDesignDocumentApproval(@Validated(ValidationGroups.Save.class) @RequestBody KcsjDesignDocumentApproval kcsjDesignDocumentApprovalParam) {
        kcsjDesignDocumentApprovalService.insertKcsjDesignDocumentApproval(kcsjDesignDocumentApprovalParam);
        return AjaxResult.success(kcsjDesignDocumentApprovalParam);
    }

    /**
     * 批量保存
     *
     * @param kcsjDesignDocumentApprovalListVo
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjDesignDocumentApproval:save")
    @PostMapping("/batchSave")
    @CustomLogger(title = "勘察设计-勘察设计文件报批",name = "勘察设计文件报批",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertKcsjDesignDocumentApprovalList(@Validated(ValidationGroups.Save.class) @RequestBody KcsjDesignDocumentApprovalVo kcsjDesignDocumentApprovalListVo) {
        return kcsjDesignDocumentApprovalService.saveKcsjDesignDocumentApprovalList(kcsjDesignDocumentApprovalListVo);

    }

    @PreAuthorize(hasPermi = "kcsjDesignDocumentApproval:update")
    @PostMapping("/update")
    @CustomLogger(title = "勘察设计-勘察设计文件报批",name = "勘察设计文件报批",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateKcsjDesignDocumentApproval(@Validated(ValidationGroups.Update.class) @RequestBody KcsjDesignDocumentApproval kcsjDesignDocumentApprovalParam) {
        return toAjax(kcsjDesignDocumentApprovalService.updateKcsjDesignDocumentApproval(kcsjDesignDocumentApprovalParam));
    }

    @PreAuthorize(hasPermi = "kcsjDesignDocumentApproval:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "勘察设计-勘察设计文件报批",name = "勘察设计文件报批",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateKcsjDesignDocumentApprovalList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalListParam) {
        return toAjax(kcsjDesignDocumentApprovalService.updateKcsjDesignDocumentApprovalList(kcsjDesignDocumentApprovalListParam));
    }

    @PreAuthorize(hasPermi = "kcsjDesignDocumentApproval:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjDesignDocumentApproval(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjDesignDocumentApproval kcsjDesignDocumentApprovalParam) {
        return toAjax(kcsjDesignDocumentApprovalService.deleteKcsjDesignDocumentApproval(kcsjDesignDocumentApprovalParam));
    }

    @PreAuthorize(hasPermi = "kcsjDesignDocumentApproval:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjDesignDocumentApprovalByPks(@PathVariable Long[] ids) {
        List<Long> kcsjDesignDocumentApprovalPkList = Arrays.asList(ids);
        return toAjax(kcsjDesignDocumentApprovalService.deleteKcsjDesignDocumentApprovalByPks(kcsjDesignDocumentApprovalPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjDesignDocumentApproval kcsjDesignDocumentApprovalParam) throws IOException {
        List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList = kcsjDesignDocumentApprovalService.getKcsjDesignDocumentApprovalList(kcsjDesignDocumentApprovalParam);
        ExcelUtils<KcsjDesignDocumentApproval> util = new ExcelUtils<>(KcsjDesignDocumentApproval.class);
        util.exportExcel(response, kcsjDesignDocumentApprovalList, DateUtils.getDate());
    }

    /**
     * 设计文件报批每晚8点发送消息
     *
     * @auth lcf
     * @date 2024-01-22
     */
    @GetMapping("/designFileTask")
    public void designFileTask(){
        kcsjDesignDocumentApprovalService.designFileTask();
    }

}
