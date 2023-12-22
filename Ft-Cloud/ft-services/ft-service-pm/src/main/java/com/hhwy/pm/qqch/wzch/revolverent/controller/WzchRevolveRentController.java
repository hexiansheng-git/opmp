package com.hhwy.pm.qqch.wzch.revolverent.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.domain.WzchLocalPurchaseSupply;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.dto.WzchLocalPurchaseSupplyDetailDTO;
import com.hhwy.pm.qqch.wzch.revolverent.domain.WzchRevolveRent;
import com.hhwy.pm.qqch.wzch.revolverent.domain.WzchRevolveRentDetail;
import com.hhwy.pm.qqch.wzch.revolverent.dto.WzchRevolveRentDTO;
import com.hhwy.pm.qqch.wzch.revolverent.dto.WzchRevolveRentDetailDTO;
import com.hhwy.pm.qqch.wzch.revolverent.service.IWzchRevolveRentDetailService;
import com.hhwy.pm.qqch.wzch.revolverent.service.IWzchRevolveRentService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 周转材租赁策划Controller
 *
 * @author mls
 * @date 2022-11-17
 */
@RestController
@RequestMapping("wzch/revolveRent")
public class WzchRevolveRentController extends BaseController {

    @Resource
    private IWzchRevolveRentService wzchRevolveRentService;

    @Resource
    private IWzchRevolveRentDetailService detailService;

    @Resource
    private WzchCommonService wzchCommonService;

    @PostMapping("cghtZzc")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.3周转材租赁策划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult cghtZzc(@RequestBody Map<String, String> map) {
        String projectId = map.get("projectId");
        WzchRevolveRentDetail wzchRevolveRentDetail = new WzchRevolveRentDetail();
        wzchRevolveRentDetail.setProjectId(Long.parseLong(projectId));
        wzchRevolveRentDetail.setValid("1");
        wzchRevolveRentDetail.setDelFlag("0");
        List<WzchRevolveRentDetailDTO> wzchRevolveRentDetailDTOS = detailService.selectWzchRevolveRentDetailList(wzchRevolveRentDetail);
        return AjaxResult.success("成功", wzchRevolveRentDetailDTOS);
    }

    /**
     * 新增 编辑 详情数据回显
     */
    @GetMapping("baseInfo")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.3周转材租赁策划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult baseInfo(@RequestParam(required = false) WzchRevolveRentDTO vo) {
        return AjaxResult.success(wzchRevolveRentService.baseInfo(vo==null?new WzchRevolveRentDTO():vo));
    }


    /**
     * 查询周转材租赁策划列表
     */
//    @PreAuthorize(hasPermi = "wzch:revolverent:list")
    @PostMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.3周转材租赁策划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody WzchRevolveRent wzchRevolveRent) {
        startPage();
        List<WzchRevolveRent> list = wzchRevolveRentService.selectWzchRevolveRentList(wzchRevolveRent);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出周转材租赁策划列表
     */
    @PostMapping("/export")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.3周转材租赁策划" ,businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody WzchRevolveRent wzchRevolveRent, HttpServletResponse response) {
        try {
            List<WzchRevolveRent> list = wzchRevolveRentService.selectWzchRevolveRentList(wzchRevolveRent);
            ExcelUtils<WzchRevolveRent> util = new ExcelUtils<>(WzchRevolveRent.class);
            util.exportExcel(response, list, "revolverent");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }


    /**
     * 新增保存周转材租赁策划
     */
//    @PreAuthorize(hasPermi = "wzch:revolverent:add")
    @PostMapping("/add")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.3周转材租赁策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody WzchRevolveRentDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchRevolveRentService.insert(dto)));
    }


    /**
     * 修改保存周转材租赁策划
     */
//    @PreAuthorize(hasPermi = "wzch:revolverent:edit")
    @PostMapping("/edit")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.3周转材租赁策划" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody WzchRevolveRentDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchRevolveRentService.edit(dto)));
    }


    /**
     * 修改保存周转材租赁策划
     */
//    @PreAuthorize(hasPermi = "wzch:revolverent:adjust")
    @PostMapping("/adjust")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.3周转材租赁策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult adjust(@Validated(ValidationGroups.Update.class) @RequestBody WzchRevolveRentDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchRevolveRentService.adjust(dto)));
    }

//    @PreAuthorize(hasPermi = "wzch:revolverent:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.3周转材租赁策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody WzchRevolveRentDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchRevolveRentService.save(dto)));
    }

    /**
     * 删除周转材租赁策划
     */
//    @PreAuthorize(hasPermi = "wzch:revolverent:remove")
    @PostMapping("/remove")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.3周转材租赁策划" ,businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(@Validated(ValidationGroups.Delete.class) @RequestBody Map map) {
        String ids = String.valueOf(map.get("ids"));
        return toAjax(wzchRevolveRentService.deleteWzchRevolveRentByIds(ids));
    }


    /**
     * 根据项目id获取物资详情
     */
    @PostMapping("detail/getMtlDetailList")
//    @CustomLogger(title = "根据项目id获取物资详情", businessType = CustomBusinessType.SELECT)
    public AjaxResult getMtlDetailList(@RequestBody WzchRevolveRentDetailDTO dto) {
        try {
            List<WzchRevolveRentDetailDTO> list = detailService.getMtlDetailList(dto);

            HashMap<String, String> dicMap = new HashMap<>(3);
            dicMap.put("materialStandard_materialStandardName", "material_standard");
            dicMap.put("categoryName_categoryNameName", "total_demand_category_name");
            dicMap.put("currency_currencyName", "remittance_currency_type");
            list = wzchCommonService.setDicValue(list, dicMap);

            return AjaxResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");

        }
    }


    /**
     * 导出周转材料详情列表
     */
    @PostMapping("detail/export")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.3周转材租赁策划" ,businessType = CustomBusinessType.EXPORT)
    public void exportDetail(@Validated(ValidationGroups.Other.class) @RequestBody Map<String, List<WzchRevolveRentDetailDTO>> params, HttpServletResponse response) {
        try {
            List<WzchRevolveRentDetailDTO> detailList = params.get("detailList");
            HashMap<String, String> dicMap = new HashMap<>(3);
            dicMap.put("materialStandard", "material_standard");
            dicMap.put("categoryName", "total_demand_category_name");
            dicMap.put("currency", "remittance_currency_type");
            detailList = wzchCommonService.exportDealDict(detailList, dicMap);
            ExcelUtils<WzchRevolveRentDetailDTO> util = new ExcelUtils<>(WzchRevolveRentDetailDTO.class);
            util.exportExcel(response, detailList, "采购供应策划物资详情");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }

    @PostMapping("detail/importData")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.3周转材租赁策划" ,businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file, @RequestParam Map map) {
        try {
            Assert.isTrue(!ObjectUtils.isBlank(map.get("version")),"version不能为空");
            ExcelUtils<WzchRevolveRentDetailDTO> util = new ExcelUtils<>(WzchRevolveRentDetailDTO.class);
            List<WzchRevolveRentDetailDTO> dtoList = util.importExcel(file.getInputStream());
            HashMap<String, String> dicMap = new HashMap<>(3);
            dicMap.put("materialStandard", "material_standard");
            dicMap.put("categoryName", "total_demand_category_name");
            dicMap.put("currency", "remittance_currency_type");
            dtoList = wzchCommonService.importDealDict(dtoList, dicMap);

            //校验物资信息必须存在于来源策划
            BigDecimal version = ObjectUtils.nvlBigDecimal(map.get("version"));
            List<WzchRevolveRentDetailDTO> list = detailService.getMtlDetailList(new WzchRevolveRentDetailDTO(version));
            Set<String> materCodeSet = list.stream().map(r->r.getMaterialCode()).collect(Collectors.toSet());
            for (int i = 0; i < dtoList.size(); i++) {
                String materCode = dtoList.get(i).getMaterialCode();
                Assert.isTrue(materCodeSet.contains(materCode),"物资编码["+materCode+"]不存在于来源策划中，无法进行导入");
            }
            HashMap<String, String> dm = new HashMap<>(3);
            dm.put("materialStandard_materialStandardName", "material_standard");
            dm.put("categoryName_categoryNameName", "total_demand_category_name");
            dm.put("currency_currencyName", "remittance_currency_type");
            dtoList = wzchCommonService.setDicValue(dtoList, dm);

            return AjaxResult.success(dtoList);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "获取导入数据异常");
        }
    }

    @PostMapping("/sync")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.3周转材租赁策划" ,businessType = CustomBusinessType.OTHER)
    public AjaxResult sync(@RequestBody WzchRevolveRentDTO wzchRevolveRent) {
        try{
            Assert.notNull(wzchRevolveRent.getVersion(), "version不能为空");
            wzchRevolveRentService.sync(wzchRevolveRent);
            return AjaxResult.success();
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("同步数据异常:"+e.getMessage());
        }
    }
}
