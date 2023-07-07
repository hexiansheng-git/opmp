package com.hhwy.pm.qqch.preparation.technique.clause.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechStandardIdentify;
import com.hhwy.pm.qqch.preparation.technique.clause.service.IQqchContractTechStandardIdentifyService;
import com.hhwy.utils.tree.TreeVO;
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
 * @date 2023-07-07 15:51:11
 * @remark 3.1.1合同执行技术标准识别
 */
@Validated
@RestController
@RequestMapping("/qqchContractTechStandardIdentify")
public class QqchContractTechStandardIdentifyController extends BaseController {

    @Autowired
    private IQqchContractTechStandardIdentifyService qqchContractTechStandardIdentifyService;


    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:list")
    @GetMapping
    public AjaxResult getQqchContractTechStandardIdentify(
        @Validated(ValidationGroups.Get.class) @RequestBody QqchContractTechStandardIdentify qqchContractTechStandardIdentifyParam) {
        QqchContractTechStandardIdentify qqchContractTechStandardIdentify = qqchContractTechStandardIdentifyService
            .getQqchContractTechStandardIdentify(qqchContractTechStandardIdentifyParam);
        return AjaxResult.success(qqchContractTechStandardIdentify);
    }

    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:list")
    @GetMapping("/list")
    public AjaxResult getQqchContractTechStandardIdentifyList(
        @Validated(ValidationGroups.Select.class) @RequestBody QqchContractTechStandardIdentify qqchContractTechStandardIdentifyParam) {
        List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyList = qqchContractTechStandardIdentifyService
            .getQqchContractTechStandardIdentifyList(qqchContractTechStandardIdentifyParam);
        return AjaxResult.success(qqchContractTechStandardIdentifyList);
    }

    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:add")
    @PostMapping("/add")
    public AjaxResult insertQqchContractTechStandardIdentify(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchContractTechStandardIdentify qqchContractTechStandardIdentifyParam) {
        qqchContractTechStandardIdentifyService
            .insertQqchContractTechStandardIdentify(qqchContractTechStandardIdentifyParam);
        return AjaxResult.success(qqchContractTechStandardIdentifyParam);
    }

    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchContractTechStandardIdentifyList(
        @Validated(ValidationGroups.Save.class) @RequestBody List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyListParam) {
        qqchContractTechStandardIdentifyService
            .insertQqchContractTechStandardIdentifyList(qqchContractTechStandardIdentifyListParam);
        return AjaxResult.success(qqchContractTechStandardIdentifyListParam);
    }

    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:update")
    @PostMapping("/update")
    public AjaxResult updateQqchContractTechStandardIdentify(
        @Validated(ValidationGroups.Update.class) @RequestBody QqchContractTechStandardIdentify qqchContractTechStandardIdentifyParam) {
        return toAjax(qqchContractTechStandardIdentifyService
            .updateQqchContractTechStandardIdentify(qqchContractTechStandardIdentifyParam));
    }

    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchContractTechStandardIdentifyList(
        @Validated(ValidationGroups.Update.class) @RequestBody List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyListParam) {
        return toAjax(qqchContractTechStandardIdentifyService
            .updateQqchContractTechStandardIdentifyList(qqchContractTechStandardIdentifyListParam));
    }

    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchContractTechStandardIdentify(
        @Validated(ValidationGroups.Delete.class) @RequestBody QqchContractTechStandardIdentify qqchContractTechStandardIdentifyParam) {
        return toAjax(qqchContractTechStandardIdentifyService
            .deleteQqchContractTechStandardIdentify(qqchContractTechStandardIdentifyParam));
    }

    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchContractTechStandardIdentifyByPks(@PathVariable Long[] ids) {
        List<Long> qqchContractTechStandardIdentifyPkList = Arrays.asList(ids);
        return toAjax(qqchContractTechStandardIdentifyService
            .deleteQqchContractTechStandardIdentifyByPks(qqchContractTechStandardIdentifyPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response,
        QqchContractTechStandardIdentify qqchContractTechStandardIdentifyParam) throws IOException {
        List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyList = qqchContractTechStandardIdentifyService
            .getQqchContractTechStandardIdentifyList(qqchContractTechStandardIdentifyParam);
        ExcelUtils<QqchContractTechStandardIdentify> util = new ExcelUtils<>(QqchContractTechStandardIdentify.class);
        util.exportExcel(response, qqchContractTechStandardIdentifyList, DateUtils.getDate());
    }

    /**
     * 查询列表
     *
     * @param qqchContractTechStandardIdentifyParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:list")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(
        @Validated(ValidationGroups.Select.class) @RequestBody QqchContractTechStandardIdentify qqchContractTechStandardIdentifyParam) {
        List<? extends TreeVO> treeList = qqchContractTechStandardIdentifyService
            .getTreeList(qqchContractTechStandardIdentifyParam);
        return AjaxResult.success(treeList);
    }

}
