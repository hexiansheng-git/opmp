package com.hhwy.pm.qqch.tax.qqchTaxCost.controller;

import com.alibaba.excel.EasyExcel;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCost;
import com.hhwy.pm.qqch.tax.qqchTaxCost.service.IQqchTaxCostService;
import com.hhwy.pm.qqch.tax.qqchTaxCost.vo.TaxCostVO;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author mls
 * @date 2023-08-09 18:17:14
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchTaxCost")
public class QqchTaxCostController extends BaseController {

    @Autowired
    private IQqchTaxCostService qqchTaxCostService;


    @PreAuthorize(hasPermi = "qqchTaxCost:list")
    @GetMapping
    public AjaxResult getQqchTaxCost(@Validated(ValidationGroups.Get.class) QqchTaxCost qqchTaxCostParam) {
        QqchTaxCost qqchTaxCost = qqchTaxCostService.getQqchTaxCost(qqchTaxCostParam);
        return AjaxResult.success(qqchTaxCost);
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:list")
    @GetMapping("/list")
    public AjaxResult getQqchTaxCostList(@Validated(ValidationGroups.Select.class) QqchTaxCost qqchTaxCostParam) {
        startPage();
        List<QqchTaxCost> qqchTaxCostList = qqchTaxCostService.getQqchTaxCostList(qqchTaxCostParam);
        return getDataTableAjaxResult(qqchTaxCostList);
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTaxCost(@Validated(ValidationGroups.Save.class) @RequestBody QqchTaxCost qqchTaxCostParam) {
        qqchTaxCostService.insertQqchTaxCost(qqchTaxCostParam);
        return AjaxResult.success(qqchTaxCostParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxCostList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxCost> qqchTaxCostListParam) {
        qqchTaxCostService.insertQqchTaxCostList(qqchTaxCostListParam);
        return AjaxResult.success(qqchTaxCostListParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxCost(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxCost qqchTaxCostParam) {
        return toAjax(qqchTaxCostService.updateQqchTaxCost(qqchTaxCostParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTaxCostList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxCost> qqchTaxCostListParam) {
        return toAjax(qqchTaxCostService.updateQqchTaxCostList(qqchTaxCostListParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxCost(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxCost qqchTaxCostParam) {
        return toAjax(qqchTaxCostService.deleteQqchTaxCost(qqchTaxCostParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTaxCostByPks(@PathVariable Long[] ids) {
        List<Long> qqchTaxCostPkList = Arrays.asList(ids);
        return toAjax(qqchTaxCostService.deleteQqchTaxCostByPks(qqchTaxCostPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, @RequestBody CompileEntity<TaxCostVO> dto) throws IOException {
        TaxCostVO dto1 = dto.getDto();
        List<String> yearList = dto1.getYearList();


        if (PmConstant.ONE.equals(dto.getDataType())) {
            List<QqchTaxCost> costList = dto1.getCostList();
            ExcelUtils<QqchTaxCost> util = new ExcelUtils<>(QqchTaxCost.class);
            util.exportExcel(response, costList, "成本明细");

        }

        if (PmConstant.TWO.equals(dto.getDataType())) {
            List<QqchTaxCost> otherList = dto1.getOtherList();
            ExcelUtils<QqchTaxCost> util = new ExcelUtils<>(QqchTaxCost.class);
            util.exportExcel(response, otherList, "其他成本明细");
        }

        if (PmConstant.THREE.equals(dto.getDataType())) {
            List<QqchTaxCost> taxList = dto1.getTaxList();
            ExcelUtils<QqchTaxCost> util = new ExcelUtils<>(QqchTaxCost.class);
            util.exportExcel(response, taxList, "所得税明细");
        }


    }


    private void exportDetail(HttpServletResponse response, List<QqchTaxCost> costList, List<String> yearList, String sheetName) {

        List<QqchTaxCost> taxCostList = TreeUtil.treeToList(costList);


        try {
            EasyExcel.write(response.getOutputStream())
                    // 这里放入动态头
                    .head(getHead(yearList)).sheet("模板")
                    // 当然这里数据也可以用 List<List<String>> 去传入
                    .doWrite(getData(yearList, taxCostList));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




    private List<List<String>> getData(List<String> headers, List<QqchTaxCost> resList) {
        
        return null;
    }

    private List<List<String>> getHead(List<String> headers) {
        List<List<String>> list = new ArrayList<>();

        List<String> hMaeCode = new ArrayList<>();
        hMaeCode.add("物资编码");
        list.add(hMaeCode);

        List<String> hMaeName = new ArrayList<>();
        hMaeName.add("物资名称");
        list.add(hMaeName);

        List<String> hMaeSpec = new ArrayList<>();
        hMaeSpec.add("规格型号");
        list.add(hMaeSpec);

        List<String> hMaeUnit = new ArrayList<>();
        hMaeUnit.add("单位");
        list.add(hMaeUnit);


        for (String header : headers) {
            List<String> head0 = new ArrayList<>();
            head0.add(header);
            head0.add("物资实际消耗(元)");
            head0.add("实际消耗数量");
            list.add(head0);

            List<String> head1 = new ArrayList<>();
            head1.add(header);
            head1.add("物资实际消耗(元)");
            head1.add("加权平均单价");
            list.add(head1);

            List<String> head2 = new ArrayList<>();
            head2.add(header);
            head2.add("物资实际消耗(元)");
            head2.add("实际消耗金额");
            list.add(head2);

            List<String> head3 = new ArrayList<>();
            head3.add(header);
            head3.add("量差核算情况");
            head3.add("设计用量");
            list.add(head3);

            List<String> head4 = new ArrayList<>();
            head4.add(header);
            head4.add("量差核算情况");
            head4.add("局定额损耗率");
            list.add(head4);

            List<String> head5 = new ArrayList<>();
            head5.add(header);
            head5.add("量差核算情况");
            head5.add("理论消耗量");
            list.add(head5);

            List<String> head6 = new ArrayList<>();
            head6.add(header);
            head6.add("量差核算情况");
            head6.add("与设计用量对比(节+超-)");
            head6.add("节超数量");
            list.add(head6);

            List<String> head7 = new ArrayList<>();
            head7.add(header);
            head7.add("量差核算情况");
            head7.add("与设计用量对比(节+超-)");
            head7.add("节超金额");
            list.add(head7);

            List<String> head8 = new ArrayList<>();
            head8.add(header);
            head8.add("量差核算情况");
            head8.add("与设计用量对比(节+超-)");
            head8.add("节超率");
            list.add(head8);

            List<String> head9 = new ArrayList<>();
            head9.add(header);
            head9.add("量差核算情况");
            head9.add("与理论用量对比(节+超-)");
            head9.add("节超数量");
            list.add(head9);

            List<String> head10 = new ArrayList<>();
            head10.add(header);
            head10.add("量差核算情况");
            head10.add("与理论用量对比(节+超-)");
            head10.add("节超金额");
            list.add(head10);


            List<String> head11 = new ArrayList<>();
            head11.add(header);
            head11.add("量差核算情况");
            head11.add("与理论用量对比(节+超-)");
            head11.add("节超率");
            list.add(head11);
        }


        return list;
    }
    
    
    
    
    
    


    @PreAuthorize(hasPermi = "qqchTaxCost:list")
    @GetMapping("/getList")
    public AjaxResult getList(@Validated(ValidationGroups.Select.class) QqchTaxCost taxCost) {
        QqchTaxCost qqchTaxCost = CompileEntity.dealListDto(taxCost.getVersion(), new QqchTaxCost());
        CompileEntity<TaxCostVO> qqchTaxInList = qqchTaxCostService.getList(qqchTaxCost);
        return AjaxResult.success(qqchTaxInList);
    }


    @PreAuthorize(hasPermi = "qqchTaxCost:taxList")
    @GetMapping("/taxList")
    public AjaxResult taxList(@Validated(ValidationGroups.Select.class) QqchTaxCost taxCost) {
        QqchTaxCost qqchTaxCost = CompileEntity.dealListDto(taxCost.getVersion(), new QqchTaxCost());
        CompileEntity<TaxCostVO> qqchTaxInList = qqchTaxCostService.taxList(qqchTaxCost);
        return AjaxResult.success(qqchTaxInList);
    }


    @PreAuthorize(hasPermi = "qqchTaxCost:add")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody CompileEntity<TaxCostVO> dto) {
        qqchTaxCostService.save(dto);
        return AjaxResult.success(dto);
    }


    @PostMapping("/downTemp")
    public void downTemp(HttpServletResponse response, @RequestBody QqchTaxCost params) {
        try {
            qqchTaxCostService.downTemp(response, params);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> params) {
        try {
            return AjaxResult.success(qqchTaxCostService.importData(file, params));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
