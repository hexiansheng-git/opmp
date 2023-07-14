package com.hhwy.pm.xmsl.contractInfo.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSpecial;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ImportXmslContractSpecial;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractSpecialService;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:51
 * @remark
 */
@Validated
@RestController
@RequestMapping("/xmslContractSpecial")
public class XmslContractSpecialController extends BaseController {

    @Autowired
    private IXmslContractSpecialService xmslContractSpecialService;


    @PreAuthorize(hasPermi = "xmslContractSpecial:list")
    @GetMapping
    public AjaxResult getXmslContractSpecial(@Validated(ValidationGroups.Get.class) @RequestBody XmslContractSpecial xmslContractSpecialParam) {
        List<XmslContractSpecial> treeVOS  = xmslContractSpecialService.getXmslContractSpecial(xmslContractSpecialParam);
        return AjaxResult.success(treeVOS);
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:list")
    @GetMapping("/list")
    public AjaxResult getXmslContractSpecialList(@Validated(ValidationGroups.Select.class) @RequestBody XmslContractSpecial xmslContractSpecialParam) {
        startPage();
        List<XmslContractSpecial> xmslContractSpecialList = xmslContractSpecialService.getXmslContractSpecialList(xmslContractSpecialParam);
        return getDataTableAjaxResult(xmslContractSpecialList);
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:add")
    @PostMapping("/add")
    public AjaxResult insertXmslContractSpecial(@Validated(ValidationGroups.Save.class) @RequestBody XmslContractSpecial xmslContractSpecialParam) {
        xmslContractSpecialService.insertXmslContractSpecial(xmslContractSpecialParam);
        return AjaxResult.success(xmslContractSpecialParam);
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertXmslContractSpecialList(@Validated(ValidationGroups.Save.class) @RequestBody List<XmslContractSpecial> xmslContractSpecialListParam) {
        xmslContractSpecialService.insertXmslContractSpecialList(xmslContractSpecialListParam);
        return AjaxResult.success(xmslContractSpecialListParam);
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:update")
    @PostMapping("/update")
    public AjaxResult updateXmslContractSpecial(@Validated(ValidationGroups.Update.class) @RequestBody XmslContractSpecial xmslContractSpecialParam) {
        return toAjax(xmslContractSpecialService.updateXmslContractSpecial(xmslContractSpecialParam));
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateXmslContractSpecialList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslContractSpecial> xmslContractSpecialListParam) {
        return toAjax(xmslContractSpecialService.updateXmslContractSpecialList(xmslContractSpecialListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslContractSpecial(@Validated(ValidationGroups.Delete.class) @RequestBody XmslContractSpecial xmslContractSpecialParam) {
        return toAjax(xmslContractSpecialService.deleteXmslContractSpecial(xmslContractSpecialParam));
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:remove")
    @PostMapping("/remove")
    public AjaxResult deleteXmslContractSpecialByPks(@RequestBody XmslContractSpecial xmslContractSpecialParam) {
        List<Long> xmslContractSpecialPkList = Arrays.asList(xmslContractSpecialParam.getIds());
        return toAjax(xmslContractSpecialService.deleteXmslContractSpecialByPks(xmslContractSpecialPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response,@RequestBody XmslContractSpecial xmslContractSpecialParam) throws IOException {
        List<XmslContractSpecial> xmslContractSpecialList = xmslContractSpecialService.getXmslContractSpecialList(xmslContractSpecialParam);
        ExcelUtils<XmslContractSpecial> util = new ExcelUtils<>(XmslContractSpecial.class);
        util.exportExcel(response, xmslContractSpecialList, DateUtils.getDate());
    }

    @GetMapping("/import")
    public AjaxResult importDate(@RequestPart("file") MultipartFile file) {
        ExcelUtils<ImportXmslContractSpecial> util = new ExcelUtils<>(ImportXmslContractSpecial.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<ImportXmslContractSpecial> xmslContractLists = util.importExcel(inputStream);
            List<ImportXmslContractSpecial> dateList = ListTreeUtil.formatTree(xmslContractLists, o -> o.getParentInnerCode()==0, (r, n) -> r.getInnerCode().equals(n.getParentInnerCode()), ImportXmslContractSpecial::getChildren, ImportXmslContractSpecial::setChildren);
            return AjaxResult.success(dateList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
}
