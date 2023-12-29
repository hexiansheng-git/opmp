package com.hhwy.pm.qqch.wzch.fund.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.fund.domain.WzchFund;
import com.hhwy.pm.qqch.wzch.fund.domain.WzchFundDetail;
import com.hhwy.pm.qqch.wzch.fund.dto.WzchFundDTO;
import com.hhwy.pm.qqch.wzch.fund.service.IWzchFundService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 资金策划Controller
 *
 * @author mls
 * @date 2022-12-08
 */
@RestController
@RequestMapping("/wzch/fund")
public class WzchFundController extends BaseController {

    @Resource
    private IWzchFundService wzchFundService;

    @Resource
    private WzchCommonService wzchCommonService;

//    @PostMapping("listener")
//    @CustomLogger(title = "查询采购视角", businessType = CustomBusinessType.SELECT)
//    public AjaxResult listener(@RequestBody Map<String, Object> map) {
//        DelegateTask delegateTask = JSONObject.parseObject(JSONObject.toJSONString(map.get("execution")), DelegateTask.class);
//        Map varMap = delegateTask.getVariables();
//        String businessId = (String) varMap.get("businessId");
//        wzchFundService.updateValidStatus(businessId);
//        return AjaxResult.success("成功");
//    }

    /**
     * 新增 编辑 详情数据回显
     */
    @GetMapping("baseInfo")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.7资金策划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult baseInfo(WzchFundDTO dto) {
        return AjaxResult.success(wzchFundService.baseInfo(dto==null?new WzchFundDTO():dto));
    }



    /**
     * 导出资金策划列表
     */
    @PostMapping("/export")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.7资金策划" ,businessType = CustomBusinessType.EXPORT)
    public void export(WzchFund wzchFund, HttpServletResponse response) {
        try {
            List<WzchFund> list = wzchFundService.selectWzchFundList(wzchFund);
            ExcelUtils<WzchFund> util = new ExcelUtils<WzchFund>(WzchFund.class);
            util.exportExcel(response, list, "fund");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出失败");
        }
    }

    /**
     * 新增保存资金策划
     */
//    @PreAuthorize(hasPermi = "wzch:fund:add")
    @PostMapping("/add")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.7资金策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody WzchFundDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchFundService.insert(dto)));
    }


    /**
     * 修改保存资金策划
     */
//    @PreAuthorize(hasPermi = "wzch:fund:edit")
    @PostMapping("/edit")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.7资金策划" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody WzchFundDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchFundService.edit(dto)));
    }

    /**
     * 删除资金策划
     */
    @PostMapping("/remove")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.7资金策划" ,businessType = CustomBusinessType.DELETE)
    public AjaxResult remove(@RequestBody Map<String, String> params) {
        String ids = params.get("ids");
        return toAjax(wzchFundService.deleteWzchFundByIds(ids));
    }

//    @PreAuthorize(hasPermi = "wzch:fund:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.7资金策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody WzchFundDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchFundService.save(dto)));
    }


    /**
     * 导出资金策划详情
     */
    @PostMapping("detail/export")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.7资金策划" ,businessType = CustomBusinessType.EXPORT)
    public void exportDetail(@Validated(ValidationGroups.Other.class) @RequestBody Map<String, List<WzchFundDetail>> params, HttpServletResponse response) {
        try {
            List<WzchFundDetail> wzchFundDetails = params.get("detailList");
            FtExcelUtil<WzchFundDetail> util = new FtExcelUtil<>(WzchFundDetail.class);
            util.exportExcel(response, wzchFundDetails, "资金策划详情");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }


    @PostMapping("detail/importData")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.2组织供应策划", name = "6.2.7资金策划" ,businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file, @RequestParam Map map) {
        try {
            FtExcelUtil<WzchFundDetail> util = new FtExcelUtil<>(WzchFundDetail.class);
            List<WzchFundDetail> dtoList = util.importExcel(file.getInputStream());
            return AjaxResult.success(dtoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "获取导入数据异常");

        }
    }


}
