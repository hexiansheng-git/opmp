package com.hhwy.pm.qqch.preparation.sbch.staffing.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingInfo;
import com.hhwy.pm.qqch.preparation.sbch.staffing.service.ISbchStaffingInfoService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 设备人员配置策划Controller
 * 
 * @author zq
 * @date 2022-11-28
 *
 *
 * 7.3.1
 */
@Controller
@RequestMapping("/staffing/info")
public class SbchStaffingInfoController extends BaseController {
    private String prefix = "staffing/info";

    @Autowired
    private ISbchStaffingInfoService sbchStaffingInfoService;
    /**
     * 查询设备人员配置策划列表
     */
//    @PreAuthorize(hasPermi="staffing:info:list")
    //@CustomLogger(title = "人员配置策划列表-查询", businessType = CustomBusinessType.SELECT)
    @GetMapping("/getList")
    @ResponseBody
    public AjaxResult list(BigDecimal version) {
        SbchStaffingInfo info  = sbchStaffingInfoService.getList(version);
        return AjaxResult.success(info);
    }

   

    /**
     * 新增保存设备人员配置策划
     */
//    @PreAuthorize(hasPermi="staffing:info:add")
    //@CustomLogger(title = "设备人员配置策划-添加保存", businessType = CustomBusinessType.SAVE)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchStaffingInfo sbchStaffingInfo) {
        try{
            Long aLong = sbchStaffingInfoService.insertSbchStaffingInfo(sbchStaffingInfo);
            return AjaxResult.success("操作成功",String.valueOf(aLong));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
