package com.hhwy.pm.xmsl.contractInfo.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.utils.tree.TreeVO;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:42
 * @remark  主合同清单
 */
@Validated
@RestController
@RequestMapping("/xmslContractList")
public class XmslContractListController extends BaseController {

    @Autowired
    private IXmslContractListService xmslContractListService;


    @PreAuthorize(hasPermi = "xmslContractList:list")
    @GetMapping
    public AjaxResult getXmslContractList(@Validated(ValidationGroups.Get.class) @RequestBody XmslContractList xmslContractListParam) {
        List<? extends TreeVO> treeList  = xmslContractListService.getXmslContractList(xmslContractListParam);
        return AjaxResult.success(treeList);
    }

    @PreAuthorize(hasPermi = "xmslContractList:list")
    @GetMapping("/list")
    public AjaxResult getXmslContractListList(@Validated(ValidationGroups.Select.class) @RequestBody XmslContractList xmslContractListParam) {
        startPage();
        List<XmslContractList> xmslContractListList = xmslContractListService.getXmslContractListList(xmslContractListParam);
        return getDataTableAjaxResult(xmslContractListList);
    }

    @PreAuthorize(hasPermi = "xmslContractList:add")
    @PostMapping("/add")
    public AjaxResult insertXmslContractList(@Validated(ValidationGroups.Save.class) @RequestBody XmslContractList xmslContractListParam) {
        xmslContractListService.insertXmslContractList(xmslContractListParam);
        return AjaxResult.success(xmslContractListParam);
    }

    @PreAuthorize(hasPermi = "xmslContractList:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertXmslContractListList(@Validated(ValidationGroups.Save.class) @RequestBody List<XmslContractList> xmslContractListListParam) {
        xmslContractListService.insertXmslContractListList(xmslContractListListParam);
        return AjaxResult.success(xmslContractListListParam);
    }

    @PreAuthorize(hasPermi = "xmslContractList:update")
    @PostMapping("/update")
    public AjaxResult updateXmslContractList(@Validated(ValidationGroups.Update.class) @RequestBody XmslContractList xmslContractListParam) {
        return toAjax(xmslContractListService.updateXmslContractList(xmslContractListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractList:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateXmslContractListList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslContractList> xmslContractListListParam) {
        return toAjax(xmslContractListService.updateXmslContractListList(xmslContractListListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslContractList(@Validated(ValidationGroups.Delete.class) @RequestBody XmslContractList xmslContractListParam) {
        return toAjax(xmslContractListService.deleteXmslContractList(xmslContractListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractList:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteXmslContractListByPks(@PathVariable Long[] ids) {
        List<Long> xmslContractListPkList = Arrays.asList(ids);
        return toAjax(xmslContractListService.deleteXmslContractListByPks(xmslContractListPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslContractList xmslContractListParam) throws IOException {
        List<XmslContractList> xmslContractListList = xmslContractListService.getXmslContractListList(xmslContractListParam);
        ExcelUtils<XmslContractList> util = new ExcelUtils<>(XmslContractList.class);
        util.exportExcel(response, xmslContractListList, DateUtils.getDate());
    }
}
