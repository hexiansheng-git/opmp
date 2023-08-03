package com.hhwy.pm.qqch.preparation.costControl.masterContract.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchKeyInventoryContent;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.vo.QqchKeyInventoryContentVo;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.service.IQqchKeyInventoryContentService;
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
 * @date 2023-08-03 13:34:18
 * @remark 须重点关注的清单及内容
 */
@Validated
@RestController
@RequestMapping("/qqchKeyInventoryContent")
public class QqchKeyInventoryContentController extends BaseController {

    @Autowired
    private IQqchKeyInventoryContentService qqchKeyInventoryContentService;


    @PreAuthorize(hasPermi = "qqchKeyInventoryContent:list")
    @GetMapping
    public AjaxResult getQqchKeyInventoryContent(@Validated(ValidationGroups.Get.class) QqchKeyInventoryContent qqchKeyInventoryContentParam) {
        QqchKeyInventoryContent qqchKeyInventoryContent = qqchKeyInventoryContentService.getQqchKeyInventoryContent(qqchKeyInventoryContentParam);
        return AjaxResult.success(qqchKeyInventoryContent);
    }

    @PreAuthorize(hasPermi = "qqchKeyInventoryContent:add")
    @PostMapping("/add")
    public AjaxResult insertQqchKeyInventoryContent(@Validated(ValidationGroups.Save.class) @RequestBody QqchKeyInventoryContent qqchKeyInventoryContentParam) {
        qqchKeyInventoryContentService.insertQqchKeyInventoryContent(qqchKeyInventoryContentParam);
        return AjaxResult.success(qqchKeyInventoryContentParam);
    }

    @PreAuthorize(hasPermi = "qqchKeyInventoryContent:update")
    @PostMapping("/update")
    public AjaxResult updateQqchKeyInventoryContent(@Validated(ValidationGroups.Update.class) @RequestBody QqchKeyInventoryContent qqchKeyInventoryContentParam) {
        return toAjax(qqchKeyInventoryContentService.updateQqchKeyInventoryContent(qqchKeyInventoryContentParam));
    }

    @PreAuthorize(hasPermi = "qqchKeyInventoryContent:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchKeyInventoryContentList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchKeyInventoryContent> qqchKeyInventoryContentListParam) {
        return toAjax(qqchKeyInventoryContentService.updateQqchKeyInventoryContentList(qqchKeyInventoryContentListParam));
    }

    @PreAuthorize(hasPermi = "qqchKeyInventoryContent:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchKeyInventoryContent(@Validated(ValidationGroups.Delete.class) @RequestBody QqchKeyInventoryContent qqchKeyInventoryContentParam) {
        return toAjax(qqchKeyInventoryContentService.deleteQqchKeyInventoryContent(qqchKeyInventoryContentParam));
    }

    @PreAuthorize(hasPermi = "qqchKeyInventoryContent:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchKeyInventoryContentByPks(@PathVariable Long[] ids) {
        List<Long> qqchKeyInventoryContentPkList = Arrays.asList(ids);
        return toAjax(qqchKeyInventoryContentService.deleteQqchKeyInventoryContentByPks(qqchKeyInventoryContentPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchKeyInventoryContent qqchKeyInventoryContentParam) throws IOException {
        List<QqchKeyInventoryContent> qqchKeyInventoryContentList = qqchKeyInventoryContentService.getQqchKeyInventoryContentList(qqchKeyInventoryContentParam);
        ExcelUtils<QqchKeyInventoryContent> util = new ExcelUtils<>(QqchKeyInventoryContent.class);
        util.exportExcel(response, qqchKeyInventoryContentList, DateUtils.getDate());
    }

    /**
     * 获取须重点关注的清单及内容集合
     * @param qqchKeyInventoryContentParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchKeyInventoryContent:list")
    @GetMapping("/list")
    public AjaxResult getQqchKeyInventoryContentList(@Validated(ValidationGroups.Select.class) QqchKeyInventoryContent qqchKeyInventoryContentParam) {
        List<QqchKeyInventoryContent> qqchKeyInventoryContentList = qqchKeyInventoryContentService.getQqchKeyInventoryContentList(qqchKeyInventoryContentParam);
        return AjaxResult.success(qqchKeyInventoryContentList);
    }

    /**
     * 获取须重点关注的清单及内容Vo
     * @param qqchKeyInventoryContent
     * @return
     */
    @PreAuthorize(hasPermi = "qqchKeyInventoryContent:list")
    @GetMapping("getQqchKeyInventoryContentVo")
    public AjaxResult getQqchKeyInventoryContentVo(@Validated(ValidationGroups.Get.class) QqchKeyInventoryContent qqchKeyInventoryContent) {
        QqchKeyInventoryContentVo qqchKeyInventoryContentVo = qqchKeyInventoryContentService.getQqchKeyInventoryContentVo(qqchKeyInventoryContent);
        return AjaxResult.success(qqchKeyInventoryContentVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchKeyInventoryContentVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchKeyInventoryContent:add")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody QqchKeyInventoryContentVo qqchKeyInventoryContentVo) {
        qqchKeyInventoryContentService.save(qqchKeyInventoryContentVo);
        return AjaxResult.success();
    }
}
