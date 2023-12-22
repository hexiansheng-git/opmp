package com.hhwy.pm.qqch.preparation.quality.emp.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.quality.emp.domain.QqchEmpItem;
import com.hhwy.pm.qqch.preparation.quality.emp.service.IQqchEmpItemService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
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
 * @author mls
 * @date 2023-08-15 09:36:29
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchEmpItem")
public class QqchEmpItemController extends BaseController {

    @Autowired
    private IQqchEmpItemService qqchEmpItemService;

    

    

//    @PreAuthorize(hasPermi = "qqchEmpItem:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.4重难点工程", name = "9.4.3重难点工程检查项" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getList(@Validated(ValidationGroups.Select.class) QqchEmpItem qqchEmpItemParam) {
        startPage();
        List<QqchEmpItem> qqchEmpItemList = qqchEmpItemService.getQqchEmpItemList(qqchEmpItemParam);
        return getDataTableAjaxResult(qqchEmpItemList);
    }

//    @PreAuthorize(hasPermi = "qqchEmpItem:add")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.4重难点工程", name = "9.4.3重难点工程检查项" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@RequestBody CompileEntity<List<List<QqchEmpItem>>> dto) {
      
        qqchEmpItemService.save(dto);
        return AjaxResult.success(dto);
    }



    @PostMapping("/wbsList")
    public AjaxResult wbsList(@Validated(ValidationGroups.Select.class) @RequestBody QqchEmpItem dto) {
        List<XmslWbs> xmslWbs = qqchEmpItemService.wbsList(dto);
        return AjaxResult.success(xmslWbs);
    }



    @GetMapping("/itemList")
    public AjaxResult itemList(@Validated(ValidationGroups.Select.class) QqchEmpItem dto) {
        CompileEntity<List<QqchEmpItem>> xmslWbs = qqchEmpItemService.itemList(dto);
        return AjaxResult.success(xmslWbs);
    }

//    @PreAuthorize(hasPermi = "qqchEmpItem:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.4重难点工程", name = "9.4.3重难点工程检查项" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchEmpItemList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchEmpItem> qqchEmpItemListParam) {
        qqchEmpItemService.insertQqchEmpItemList(qqchEmpItemListParam);
        return AjaxResult.success(qqchEmpItemListParam);
    }

//    @PreAuthorize(hasPermi = "qqchEmpItem:update")
    @PostMapping("/update")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.4重难点工程", name = "9.4.3重难点工程检查项" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateQqchEmpItem(@Validated(ValidationGroups.Update.class) @RequestBody QqchEmpItem qqchEmpItemParam) {
        return toAjax(qqchEmpItemService.updateQqchEmpItem(qqchEmpItemParam));
    }

//    @PreAuthorize(hasPermi = "qqchEmpItem:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchEmpItemList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchEmpItem> qqchEmpItemListParam) {
        return toAjax(qqchEmpItemService.updateQqchEmpItemList(qqchEmpItemListParam));
    }

//    @PreAuthorize(hasPermi = "qqchEmpItem:remove")
    @PostMapping("/delete")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.4重难点工程", name = "9.4.3重难点工程检查项" ,businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteQqchEmpItem(@Validated(ValidationGroups.Delete.class) @RequestBody QqchEmpItem qqchEmpItemParam) {
        return toAjax(qqchEmpItemService.deleteQqchEmpItem(qqchEmpItemParam));
    }

//    @PreAuthorize(hasPermi = "qqchEmpItem:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchEmpItemByPks(@PathVariable Long[] ids) {
        List<Long> qqchEmpItemPkList = Arrays.asList(ids);
        return toAjax(qqchEmpItemService.deleteQqchEmpItemByPks(qqchEmpItemPkList));
    }

    @GetMapping("/export")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.4重难点工程", name = "9.4.3重难点工程检查项" ,businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response, QqchEmpItem qqchEmpItemParam) throws IOException {
        List<QqchEmpItem> qqchEmpItemList = qqchEmpItemService.getQqchEmpItemList(qqchEmpItemParam);
        ExcelUtils<QqchEmpItem> util = new ExcelUtils<>(QqchEmpItem.class);
        util.exportExcel(response, qqchEmpItemList, DateUtils.getDate());
    }
}
