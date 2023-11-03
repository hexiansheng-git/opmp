package com.hhwy.pm.qqch.tax.qqchTaxGlobal.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobalFormula;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.IQqchTaxGlobalFormulaService;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.IQqchTaxGlobalService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.excel.FtExcelEnum;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeNode;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 整体资金计划
 * @author mls
 * @date 2023-08-17 16:19:06
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchTaxGlobal")
public class QqchTaxGlobalController extends BaseController {

    @Resource
    private IQqchTaxGlobalService qqchTaxGlobalService;

    @Resource
    private IQqchTaxGlobalFormulaService taxGlobalFormulaService;


//    @PreAuthorize(hasPermi = "qqchTaxGlobal:list")
    @GetMapping
    public AjaxResult getQqchTaxGlobal(@Validated(ValidationGroups.Get.class) QqchTaxGlobal qqchTaxGlobalParam) {
        QqchTaxGlobal qqchTaxGlobal = qqchTaxGlobalService.getQqchTaxGlobal(qqchTaxGlobalParam);
        return AjaxResult.success(qqchTaxGlobal);
    }

//    @PreAuthorize(hasPermi = "qqchTaxGlobal:list")
    @GetMapping("/list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) QqchTaxGlobal dto) {
        CompileEntity<List<QqchTaxGlobal>> qqchTaxGlobalList = null;
        try {
            qqchTaxGlobalList = qqchTaxGlobalService.list(CompileEntity.dealListDto(dto.getVersion(),dto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success(qqchTaxGlobalList);
    }

//    @PreAuthorize(hasPermi = "qqchTaxGlobal:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody CompileEntity<List<QqchTaxGlobal>> params) {
        String yearStr = params.getYearStr();
        CommonAssert.notBlank(yearStr,"年份不能为空");
        List<QqchTaxGlobal> qqchTaxGlobals = params.dealSaveDto();
        qqchTaxGlobals = treeToListWithLevel(qqchTaxGlobals);
        for (QqchTaxGlobal qqchTaxGlobal : qqchTaxGlobals) {
            qqchTaxGlobal.setYear(Integer.valueOf(yearStr));
            EntityUtils.setCreateUpdateInfo(qqchTaxGlobal);
        }
        qqchTaxGlobalService.save(qqchTaxGlobals);
        return AjaxResult.success(params);
    }



    public static  List<QqchTaxGlobal> treeToListWithLevel(List<QqchTaxGlobal> source) {
        List<QqchTaxGlobal> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(source)) {
            return result;
        }

        int sort = 1;
        for (QqchTaxGlobal node : source) {
            node.setSort(sort++);
            node.setLevel(splitWithLevel(node, result, 0));
        }
        return result;
    }



    private static  int splitWithLevel(QqchTaxGlobal node, List<QqchTaxGlobal> resultList, int level) {
        level++;
        Long id = IdWorker.createId();
        int sort = 1;
        List<QqchTaxGlobal> children = node.getChildren();
        node.setId(id);
        node.setChildren(null);
        resultList.add(node);
        if (!CollectionUtils.isEmpty(children)) {
            for (QqchTaxGlobal child : children) {
                child.setPid(id);
                child.setSort(sort++);
                child.setLevel(splitWithLevel(child, resultList, level));
            }
        }
        return level;
    }

//    @PreAuthorize(hasPermi = "qqchTaxGlobal:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxGlobalList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxGlobal> qqchTaxGlobalListParam) {
        qqchTaxGlobalService.insertQqchTaxGlobalList(qqchTaxGlobalListParam);
        return AjaxResult.success(qqchTaxGlobalListParam);
    }

//    @PreAuthorize(hasPermi = "qqchTaxGlobal:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxGlobal(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxGlobal qqchTaxGlobalParam) {
        return toAjax(qqchTaxGlobalService.updateQqchTaxGlobal(qqchTaxGlobalParam));
    }

//    @PreAuthorize(hasPermi = "qqchTaxGlobal:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTaxGlobalList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxGlobal> qqchTaxGlobalListParam) {
        return toAjax(qqchTaxGlobalService.updateQqchTaxGlobalList(qqchTaxGlobalListParam));
    }

//    @PreAuthorize(hasPermi = "qqchTaxGlobal:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxGlobal(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxGlobal qqchTaxGlobalParam) {
        return toAjax(qqchTaxGlobalService.deleteQqchTaxGlobal(qqchTaxGlobalParam));
    }

//    @PreAuthorize(hasPermi = "qqchTaxGlobal:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTaxGlobalByPks(@PathVariable Long[] ids) {
        List<Long> qqchTaxGlobalPkList = Arrays.asList(ids);
        return toAjax(qqchTaxGlobalService.deleteQqchTaxGlobalByPks(qqchTaxGlobalPkList));
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody List<QqchTaxGlobal> qqchTaxGlobalParam) throws IOException {
        qqchTaxGlobalParam = TreeUtil.treeToList(qqchTaxGlobalParam);
        FtExcelUtil<QqchTaxGlobal> util = new FtExcelUtil<>(QqchTaxGlobal.class);
        util.exportWithTemplate(response, qqchTaxGlobalParam, 3, "exportTaxGlobal.xlsx", "sheet1");
    }


    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) {

        try {
            FtExcelUtil<QqchTaxGlobal> excelUtil = new FtExcelUtil<>(QqchTaxGlobal.class);
            List<QqchTaxGlobal> qqchTaxGlobals = excelUtil.importTaxGlobal(file.getInputStream(), 3);
            return AjaxResult.success(qqchTaxGlobals);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    @PostMapping("/saveFormula")
    public AjaxResult saveFormula(@RequestBody CompileEntity<QqchTaxGlobalFormula> param) {
        taxGlobalFormulaService.save(param.dealSaveDto());
        return AjaxResult.success(param);
    }

    /**
     * 获取明细填表数据
     * @param param {year}
     * @return
     */
    @GetMapping("/getFormula")
    public AjaxResult getFormula(QqchTaxGlobalFormula param) {
        QqchTaxGlobalFormula qqchTaxGlobalFormula1 = CompileEntity.dealListDto(param.getVersion(),param);
        CompileEntity<QqchTaxGlobalFormula> qqchTaxGlobalFormula = taxGlobalFormulaService.getFormula(qqchTaxGlobalFormula1);
        return AjaxResult.success(qqchTaxGlobalFormula);
    }

    @GetMapping("/getGlobalByFormula")
    public AjaxResult getGlobalByFormula(QqchTaxGlobalFormula param) {
        List<QqchTaxGlobal> qqchTaxGlobalFormula = taxGlobalFormulaService.getGlobalByFormula(param);
        return AjaxResult.success(qqchTaxGlobalFormula);
    }


    @GetMapping("/getPrjInfo")
    public AjaxResult getPrjInfo(QqchTaxGlobalFormula param) {
        Map<String, Object> res = taxGlobalFormulaService.getPrjInfo(param);
        return AjaxResult.success(res);
    }


}
