package com.hhwy.pm.qqch.wzch.internaladjust.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchInternalAdjust;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchInternalAdjustDetail;
import com.hhwy.pm.qqch.wzch.internaladjust.dto.WzchInternalAdjustDTO;
import com.hhwy.pm.qqch.wzch.internaladjust.service.IWzchInternalAdjustDetailService;
import com.hhwy.pm.qqch.wzch.internaladjust.service.IWzchInternalAdjustService;
import com.hhwy.pm.qqch.wzch.localpuchasesupply.domain.WzchLocalPurchaseSupply;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 内部调剂材料策划Controller
 *
 * @author mls
 * @date 2022-11-17
 */
@RestController
@RequestMapping("wzch/internalAdjust")
public class WzchInternalAdjustController extends BaseController {

    @Resource
    private IWzchInternalAdjustService wzchInternalAdjustService;

    @Resource
    private IWzchInternalAdjustDetailService detailService;
    @Resource
    private WzchCommonService wzchCommonService;

//    @PostMapping("listener")
//    @CustomLogger(title = "流程审批完成", businessType = CustomBusinessType.SELECT)
//    public AjaxResult listener(@RequestBody Map<String, Object> map) {
//        DelegateTask delegateTask = JSONObject.parseObject(JSONObject.toJSONString(map.get("execution")), DelegateTask.class);
//        Map varMap = delegateTask.getVariables();
//        String businessId = (String) varMap.get("businessId");
//        wzchInternalAdjustService.updateValidStatus(businessId);
//        return AjaxResult.success("成功");
//    }

    /**
     * 新增 编辑 详情数据回显
     */
    @GetMapping("baseInfo")
    @CustomLogger(title = "前期策划编制-6.2组织供应策划", name = "6.2.4内部调剂材料策划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult baseInfo(@RequestParam(required = false) WzchInternalAdjustDTO dto) {
        return AjaxResult.success(wzchInternalAdjustService.baseInfo(dto==null?new WzchInternalAdjustDTO():dto));
    }


    /**
     * 查询内部调剂材料策划列表
     */
//    @PreAuthorize(hasPermi = "wzch:internaladjust:list")
    @PostMapping("/list")
    @CustomLogger(title = "前期策划编制-6.2组织供应策划", name = "6.2.4内部调剂材料策划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody WzchInternalAdjust wzchInternalAdjust) {
        startPage();
        List<WzchInternalAdjust> list = wzchInternalAdjustService.selectWzchInternalAdjustList(wzchInternalAdjust);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出内部调剂材料策划列表
     */
    @PostMapping("/export")
    @CustomLogger(title = "前期策划编制-6.2组织供应策划", name = "6.2.4内部调剂材料策划" ,businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody Map<String, List<WzchInternalAdjust>> params, HttpServletResponse response) {
        try {
            List<WzchInternalAdjust> detailList = params.get("detailList");
            ExcelUtils<WzchInternalAdjust> util = new ExcelUtils<WzchInternalAdjust>(WzchInternalAdjust.class);
            util.exportExcel(response, detailList, "部调剂材料策划列表");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }


    /**
     * 新增保存内部调剂材料策划
     */
//    @PreAuthorize(hasPermi = "wzch:internaladjust:add")
    @PostMapping("/add")
    public AjaxResult addSave(@RequestBody WzchInternalAdjustDTO dto) {
        long id = wzchInternalAdjustService.insert(dto);
        return AjaxResult.success("操作成功", String.valueOf(id));
    }

    /**
     * 修改保存内部调剂材料策划
     */
//    @PreAuthorize(hasPermi = "wzch:internaladjust:edit")
    @PostMapping("/edit")
    public AjaxResult editSave(@RequestBody WzchInternalAdjustDTO dto) {
        long id = wzchInternalAdjustService.edit(dto);
        return AjaxResult.success("操作成功", String.valueOf(id));
    }

//    @PreAuthorize(hasPermi = "wzch:internaladjust:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划编制-6.2组织供应策划", name = "6.2.4内部调剂材料策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@RequestBody WzchInternalAdjustDTO dto) {
        long id = wzchInternalAdjustService.save(dto);
        return AjaxResult.success("操作成功", String.valueOf(id));
    }

    /**
     * 调整数据
     */
//    @PreAuthorize(hasPermi = "wzch:internaladjust:adjust")
    @PostMapping("/adjust")
    public AjaxResult adjust(@RequestBody WzchInternalAdjustDTO dto) {
        long id = wzchInternalAdjustService.adjust(dto);
        return AjaxResult.success("操作成功", String.valueOf(id));
    }


    /**
     * 删除内部调剂材料策划
     */
//    @PreAuthorize(hasPermi = "wzch:internaladjust:remove")
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody Map<String, String> params) {
        String ids = params.get("ids");
        return toAjax(wzchInternalAdjustService.deleteWzchInternalAdjustByIds(ids));
    }


    /**
     * 根据项目id获取物资详情
     */
    @PostMapping("detail/getMtlDetailList")
//    @CustomLogger(title = "根据项目id获取物资详情", businessType = CustomBusinessType.SELECT)
    public AjaxResult getMtlDetailList(@RequestBody WzchInternalAdjustDetail dto) {
        try {
            List<WzchInternalAdjustDetail> list = detailService.getMtlDetailList(dto);
            list = wzchCommonService.setDicValue(list, this.getDictNameMap());
            return AjaxResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");

        }
    }

    private Map<String,String> getDictNameMap(){
        HashMap<String, String> dictMap = new HashMap<>();
        dictMap.put("materialStandard_materialStandardName", "material_standard");
        dictMap.put("currency_currencyName", "remittance_currency_type");
        dictMap.put("categoryName_categoryNameName", "total_demand_category_name");
        return dictMap;
    }

    private Map<String,String> getDictMap(){
        HashMap<String, String> dictMap = new HashMap<>();
        dictMap.put("materialStandard", "material_standard");
        dictMap.put("currency", "remittance_currency_type");
        dictMap.put("categoryName", "total_demand_category_name");
        return dictMap;
    }

    /**
     * 导出采购供应策划详情列表
     */
    @PostMapping("detail/export")
    @CustomLogger(title = "前期策划编制-6.2组织供应策划", name = "6.2.4内部调剂材料策划" ,businessType = CustomBusinessType.EXPORT)
    public void exportDetail(@Validated(ValidationGroups.Other.class) @RequestBody Map<String, List<WzchInternalAdjustDetail>> map, HttpServletResponse response) {
        try {
            List<WzchInternalAdjustDetail> detailList = map.get("detailList");
            wzchCommonService.exportDealDict(detailList, this.getDictMap());
            ExcelUtils<WzchInternalAdjustDetail> util = new ExcelUtils<>(WzchInternalAdjustDetail.class);
            util.exportExcel(response, detailList, "采购供应策划物资详情");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }


    @PostMapping("detail/importData")
    @CustomLogger(title = "前期策划编制-6.2组织供应策划", name = "6.2.4内部调剂材料策划" ,businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file, @RequestParam Map map) {
        try {
            ExcelUtils<WzchInternalAdjustDetail> util = new ExcelUtils<>(WzchInternalAdjustDetail.class);
            List<WzchInternalAdjustDetail> dtoList = util.importExcel(file.getInputStream());
            for (int i = 0; i < dtoList.size(); i++) {
                dtoList.get(i).setId(IdWorker.createId());
            }
            wzchInternalAdjustService.checkImportData(dtoList, ObjectUtils.nvlBigDecimal(map.get("version")));
            wzchCommonService.importDealDict(dtoList, this.getDictMap());
            wzchCommonService.setDicValue(dtoList, this.getDictNameMap());
            return AjaxResult.success(dtoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(e.getMessage());
        }
    }

    @PostMapping("/sync")
    @CustomLogger(title = "前期策划编制-6.2组织供应策划", name = "6.2.4内部调剂材料策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult sync(@RequestBody WzchInternalAdjustDTO dto) {
        try{
            Assert.notNull(dto.getVersion(), "version不能为空");
            wzchInternalAdjustService.sync(dto);
            return AjaxResult.success(dto.getId());
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("同步数据异常:"+e.getMessage());
        }
    }
}
