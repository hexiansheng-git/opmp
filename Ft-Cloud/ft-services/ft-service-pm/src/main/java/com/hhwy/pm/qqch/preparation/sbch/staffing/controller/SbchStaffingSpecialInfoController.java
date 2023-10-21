package com.hhwy.pm.qqch.preparation.sbch.staffing.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingInfo;
import com.hhwy.pm.qqch.preparation.sbch.staffing.domain.SbchStaffingSpecialInfo;
import com.hhwy.pm.qqch.preparation.sbch.staffing.service.ISbchStaffingSpecialInfoService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 设备人员配置-特种设备爱人员Controller
 * 
 * @author zq
 * @date 2022-11-30
 *
 * 7.3.2
 */
@Controller
@RequestMapping("/staffingSpecial/info")
public class SbchStaffingSpecialInfoController extends BaseController {

    @Autowired
    private ISbchStaffingSpecialInfoService sbchStaffingSpecialInfoService;


    /**
     * 查询特种设备人员配置策划列表
     */
    @PreAuthorize(hasPermi="staffing:info:list")
    //@CustomLogger(title = "种设备人员配置策划列表-查询", businessType = CustomBusinessType.SELECT)
    @GetMapping("/getList")
    @ResponseBody
    public AjaxResult list(BigDecimal version) {
        SbchStaffingSpecialInfo info  = sbchStaffingSpecialInfoService.getList(version);
        return AjaxResult.success(info);
    }

    /**
     * 新增保存设备人员配置-特种设备爱人员
     */
    @PreAuthorize(hasPermi="specialStaffing:info:save")
    //@CustomLogger(title = "特种设备人员列表-添加保存", businessType = CustomBusinessType.SAVE)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchStaffingSpecialInfo sbchStaffingSpecialInfo) {
        try{
            Long id = sbchStaffingSpecialInfoService.insertSbchStaffingSpecialInfo(sbchStaffingSpecialInfo);
            return AjaxResult.success("操作成功",String.valueOf(id));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
