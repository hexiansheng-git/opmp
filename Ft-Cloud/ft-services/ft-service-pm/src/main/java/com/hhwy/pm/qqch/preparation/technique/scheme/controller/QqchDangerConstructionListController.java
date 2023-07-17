package com.hhwy.pm.qqch.preparation.technique.scheme.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchDangerConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchDangerConstructionListVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchDangerConstructionListService;
import com.hhwy.utils.validation.ValidationGroups;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-17 14:26:41
 * @remark 3.4.3危大工程方案清单
 */
@Validated
@RestController
@RequestMapping("/qqchDangerConstructionList")
public class QqchDangerConstructionListController extends BaseController {

    @Autowired
    private IQqchDangerConstructionListService qqchDangerConstructionListService;

    @PreAuthorize(hasPermi = "qqchDangerConstructionList:list")
    @GetMapping("/getList")
    public AjaxResult getList() {
        QqchDangerConstructionListVo qqchDangerConstructionListVo = qqchDangerConstructionListService
            .getQqchDangerConstructionListList();
        return AjaxResult.success(qqchDangerConstructionListVo);
    }

    @PreAuthorize(hasPermi = "qqchDangerConstructionList:add")
    @PostMapping("/syncData")
    public AjaxResult syncData() {
        qqchDangerConstructionListService.syncData();
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchDangerConstructionList:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDangerConstructionList(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchDangerConstructionList qqchDangerConstructionListParam) {
        qqchDangerConstructionListService.insertQqchDangerConstructionList(qqchDangerConstructionListParam);
        return AjaxResult.success(qqchDangerConstructionListParam);
    }

    @PreAuthorize(hasPermi = "qqchDangerConstructionList:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchDangerConstructionListList(
        @Validated(ValidationGroups.Save.class) @RequestBody List<QqchDangerConstructionList> qqchDangerConstructionListListParam) {
        qqchDangerConstructionListService.insertQqchDangerConstructionListList(qqchDangerConstructionListListParam);
        return AjaxResult.success(qqchDangerConstructionListListParam);
    }

    @PreAuthorize(hasPermi = "qqchDangerConstructionList:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDangerConstructionList(
        @Validated(ValidationGroups.Update.class) @RequestBody QqchDangerConstructionList qqchDangerConstructionListParam) {
        return toAjax(
            qqchDangerConstructionListService.updateQqchDangerConstructionList(qqchDangerConstructionListParam));
    }

    @PreAuthorize(hasPermi = "qqchDangerConstructionList:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchDangerConstructionListList(
        @Validated(ValidationGroups.Update.class) @RequestBody List<QqchDangerConstructionList> qqchDangerConstructionListListParam) {
        return toAjax(qqchDangerConstructionListService
            .updateQqchDangerConstructionListList(qqchDangerConstructionListListParam));
    }

    @PreAuthorize(hasPermi = "qqchDangerConstructionList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDangerConstructionList(
        @Validated(ValidationGroups.Delete.class) @RequestBody QqchDangerConstructionList qqchDangerConstructionListParam) {
        return toAjax(
            qqchDangerConstructionListService.deleteQqchDangerConstructionList(qqchDangerConstructionListParam));
    }

    @PreAuthorize(hasPermi = "qqchDangerConstructionList:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchDangerConstructionListByPks(@PathVariable Long[] ids) {
        List<Long> qqchDangerConstructionListPkList = Arrays.asList(ids);
        return toAjax(
            qqchDangerConstructionListService.deleteQqchDangerConstructionListByPks(qqchDangerConstructionListPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchDangerConstructionList qqchDangerConstructionListParam)
        throws IOException {
        QqchDangerConstructionListVo qqchDangerConstructionListVo = qqchDangerConstructionListService
            .getQqchDangerConstructionListList();
        ExcelUtils<QqchDangerConstructionList> util = new ExcelUtils<>(QqchDangerConstructionList.class);
        util.exportExcel(response, qqchDangerConstructionListVo.getList(), DateUtils.getDate());
    }
}
