package com.hhwy.pm.qqch.preparation.sbch.sblease.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchase;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchaseDetails;
import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.LeaseVo;
import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.SbchEquipmentLeaseDetails;
import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.SbchEquipmentSupplierDetails;
import com.hhwy.pm.qqch.preparation.sbch.sblease.service.ISbchEquipmentService;
import com.hhwy.pm.qqch.preparation.sbch.sblease.vo.ImportSbchEquipmentSupplierDetails;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zqq
 * @create 2023-08-26 15:21
 *
 * 7.2.3
 */
@RestController
@RequestMapping("/leasesupplier/supplier")
public class SbchEquipmentSupplierController extends BaseController {
    @Autowired
    private ISbchEquipmentService sbchEquipmentService;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version){
        LeaseVo leaseVo = sbchEquipmentService.getList(version, null);
        return AjaxResult.success(leaseVo);
    }

    @PostMapping("/batchAdd")
    public AjaxResult batchAdd(@RequestBody LeaseVo leaseVo){
        try {
            sbchEquipmentService.batchAdd(leaseVo);
            return AjaxResult.success();
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
    /**
     * 导入总需用详情
     * @author hwj
     * @date 2022/11/25 14:06
     * @param file
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
//   // @CustomLogger(title = "设备租赁供应商-导入", businessType = CustomBusinessType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file){
        try{
            ExcelUtils<ImportSbchEquipmentSupplierDetails> util = new ExcelUtils(ImportSbchEquipmentSupplierDetails.class);
            List<ImportSbchEquipmentSupplierDetails> list = util.importExcel(file.getInputStream());
            if (list != null && list.size() > 0) {
                List<String> countryName = list.stream().map(t -> t.getCountry()).collect(Collectors.toList());
                AjaxResult ajaxResult = systemServiceApi.selectCountryInfoByNames(StringUtils.join(countryName, ","));
                Map<String, Long> countryMap = new HashMap<>();
                if(ajaxResult.get("code").toString().equals("200")){
                    List<CountryInfo> countryList = JSONArray.parseArray(JSONObject.toJSONString(ajaxResult.get("data")), CountryInfo.class);
                    if(CollectionUtils.isNotEmpty(countryList)){
                        countryMap = countryList.stream().collect(Collectors.groupingBy(t -> t.getCountryName(), Collectors.collectingAndThen(Collectors.toList(), v -> v.get(0).getId())));
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    ImportSbchEquipmentSupplierDetails item = list.get(i);
                    if (!ObjectNullUtil.isEmpty(countryMap.get(item.getCountry()))) {
                        item.setCountryId(countryMap.get(item.getCountry()));
                    } else {
                        return AjaxResult.error("序号为: " + item.getXh() + " 供应商名称为: "+item.getSupplier()+" 的数据国家填写有问题，请修改");
                    }
                }
            }
            return AjaxResult.success(list);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /***
     * 功能描述:
     * @param response
     * @param ids 导出行id
     * @param listType 1 供应商调查表  2 租赁设备信息调查
     * 作者: fushudong
     * 时间: 2023/10/21
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Long[] ids, int listType) throws IOException {
        LeaseVo map = sbchEquipmentService.getList(null, ids);
        if (ObjectUtils.isEmpty(map)){
            return;
        }
        //1 供应商调查表
        if (listType == 1) {
            List<SbchEquipmentSupplierDetails> supplierList = map.getSupplierList();
            FtExcelUtil<SbchEquipmentSupplierDetails> util = new FtExcelUtil<>(SbchEquipmentSupplierDetails.class);
            util.exportExcel(response, supplierList, DateUtils.getDate());
        }else {
            // 2 租赁设备信息调查
            List<SbchEquipmentLeaseDetails> leaseDetailsList = map.getLeaseDetailsList();
            FtExcelUtil<SbchEquipmentLeaseDetails> util = new FtExcelUtil<>(SbchEquipmentLeaseDetails.class);
            util.exportExcel(response, leaseDetailsList, DateUtils.getDate());

        }
    }

}
