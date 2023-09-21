package com.hhwy.pm.qqch.tax.qqchTaxCost.controller;

import com.alibaba.excel.EasyExcel;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCost;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCostDetail;
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
import java.util.stream.Collectors;

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

    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody CompileEntity<TaxCostVO> dto) throws IOException {
        TaxCostVO dto1 = dto.getDto();
        List<String> yearList = dto1.getYearList();
        // 排个序
        yearList = yearList.stream().sorted(Comparator.comparing(String::valueOf)).collect(Collectors.toList());

        if (PmConstant.ONE.equals(dto.getDataType())) {
            List<QqchTaxCost> costList = dto1.getCostList();
            exportDetail(response, costList, yearList, "成本明细");

        }

        if (PmConstant.TWO.equals(dto.getDataType())) {
            List<QqchTaxCost> otherList = dto1.getOtherList();
            exportDetail(response, otherList, yearList, "其他成本明细");
        }

        if (PmConstant.THREE.equals(dto.getDataType())) {
            List<QqchTaxCost> taxList = dto1.getTaxList();
            exportDetail(response, taxList, yearList, "所得税明细");

        }


    }


    private void exportDetail(HttpServletResponse response, List<QqchTaxCost> costList, List<String> yearList, String sheetName) {

        List<QqchTaxCost> taxCostList = TreeUtil.treeToList(costList);


        try {

            if ("所得税明细".equals(sheetName)) {
                // 两个其实就差一个字段
                EasyExcel.write(response.getOutputStream())
                        // 这里放入动态头
                        .head(getTaxHead(yearList)).sheet(sheetName)
                        // 当然这里数据也可以用 List<List<String>> 去传入
                        .doWrite(getTaxData(yearList, taxCostList));
            } else {

                EasyExcel.write(response.getOutputStream())
                        // 这里放入动态头
                        .head(getHead(yearList)).sheet(sheetName)
                        // 当然这里数据也可以用 List<List<String>> 去传入
                        .doWrite(getData(yearList, taxCostList));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    private List<List<String>> getTaxData(List<String> yearList, List<QqchTaxCost> resList) {

        List<List<String>> lists = new ArrayList<>();

        for (QqchTaxCost qqchTaxCost : resList) {
            List<String> row = new ArrayList<>();
            row.add(qqchTaxCost.getSerNum());
            row.add(qqchTaxCost.getFeeName());
            row.add(qqchTaxCost.getInnerAmt() == null ? "" : qqchTaxCost.getInnerAmt() + "");
            row.add(qqchTaxCost.getLocalAmt() == null ? "" : qqchTaxCost.getLocalAmt() + "");
            List<QqchTaxCostDetail> detailList = qqchTaxCost.getDetailList();
            for (String year : yearList) {


                if (!CollectionUtils.isEmpty(detailList)) {
                    detailList.stream().filter(item -> year.equals(item.getYear())).findFirst().ifPresent(i -> {
                        row.add(i.getInnerAmt() == null ? "" : i.getInnerAmt() + "");
                        row.add(i.getLocalAmt() == null ? "" : i.getLocalAmt() + "");
                    });
                }
            }
            lists.add(row);
        }


        return lists;
    }


    private List<List<String>> getData(List<String> yearList, List<QqchTaxCost> resList) {

        List<List<String>> lists = new ArrayList<>();

        for (QqchTaxCost qqchTaxCost : resList) {
            List<String> row = new ArrayList<>();
            row.add(qqchTaxCost.getSerNum());
            row.add(qqchTaxCost.getFeeName());
            row.add(qqchTaxCost.getInnerAmt() == null ? "" : qqchTaxCost.getInnerAmt() + "");
            row.add(qqchTaxCost.getReqAmt() == null ? "" : qqchTaxCost.getReqAmt() + "");
            row.add(qqchTaxCost.getLocalAmt() == null ? "" : qqchTaxCost.getLocalAmt() + "");
            List<QqchTaxCostDetail> detailList = qqchTaxCost.getDetailList();
            for (String year : yearList) {


                if (!CollectionUtils.isEmpty(detailList)) {
                    detailList.stream().filter(item -> year.equals(item.getYear())).findFirst().ifPresent(i -> {
                        row.add(i.getInnerAmt() == null ? "" : i.getInnerAmt() + "");
                        row.add(i.getReqAmt() == null ? "" : i.getReqAmt() + "");
                        row.add(i.getLocalAmt() == null ? "" : i.getLocalAmt() + "");
                    });
                }


            }

            lists.add(row);
        }


        return lists;
    }



    private List<List<String>> getTaxHead(List<String> yearList) {

        List<List<String>> list = new ArrayList<>();


        List<String> ser = new ArrayList<>();
        ser.add("序号");
        list.add(ser);

        List<String> hMaeCode = new ArrayList<>();
        hMaeCode.add("费用名称");
        list.add(hMaeCode);

        List<String> sum1 = new ArrayList<>();
        sum1.add("合计");
        sum1.add("内账");
        list.add(sum1);


        List<String> sum3 = new ArrayList<>();
        sum3.add("合计");
        sum3.add("属地账策划");
        list.add(sum3);


        for (String year : yearList) {
            List<String> head0 = new ArrayList<>();
            head0.add(year);
            head0.add("内账");
            list.add(head0);


            List<String> head02 = new ArrayList<>();
            head02.add(year);
            head02.add("属地账策划");
            list.add(head02);
        }


        return list;
    }
    

    private List<List<String>> getHead(List<String> yearList) {

        List<List<String>> list = new ArrayList<>();


        List<String> ser = new ArrayList<>();
        ser.add("序号");
        list.add(ser);

        List<String> hMaeCode = new ArrayList<>();
        hMaeCode.add("费用名称");
        list.add(hMaeCode);

        List<String> sum1 = new ArrayList<>();
        sum1.add("合计");
        sum1.add("内账成本");
        list.add(sum1);


        List<String> sum2 = new ArrayList<>();
        sum2.add("合计");
        sum2.add("符合属地账要求成本");
        list.add(sum2);

        List<String> sum3 = new ArrayList<>();
        sum3.add("合计");
        sum3.add("属地账策划成本");
        list.add(sum3);


        for (String year : yearList) {
            List<String> head0 = new ArrayList<>();
            head0.add(year);
            head0.add("内账成本");
            list.add(head0);

            List<String> head01 = new ArrayList<>();
            head01.add(year);
            head01.add("符合属地账要求成本");
            list.add(head01);

            List<String> head02 = new ArrayList<>();
            head02.add(year);
            head02.add("属地账策划成本");
            list.add(head02);
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
