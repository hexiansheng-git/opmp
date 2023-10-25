package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnational;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalImport;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.vo.ImportSbchEquipmentAllotTransnationalCost;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * 跨国别设备调拨Controller
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Controller
@RequestMapping("/crosscountry/transfers")
public class SbchEquipmentAllotTransnationalController extends BaseController {
    @Autowired
    private ISbchEquipmentAllotTransnationalService sbchEquipmentAllotTransnationalService;
    @Autowired
    private ISbchEquipmentAllotTransnationalDetailsService sbchEquipmentAllotTransnationalDetailsService;


    /**
     * 跨国设备调拨详情数据回显
     */
    @GetMapping("baseInfo")
    @ResponseBody
    //@CustomDatascope((title = "同国别-新增/编辑/详情查询数据",businessType = CustomBusinessType.SELECT)
    public AjaxResult baseInfo(@RequestParam Map<String, String> map) {
        return AjaxResult.success(sbchEquipmentAllotTransnationalService.baseInfo(map));
    }

    /**
     * 查询跨国别设备调拨列表
     */
//    @PreAuthorize(hasPermi="crosscountry:transfers:list")
    @PostMapping("/list")
    //@CustomDatascope((title = "跨国别设备调拨-列表查询",businessType = CustomBusinessType.SELECT)
    @ResponseBody
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational) {
        //分页
        List<SbchEquipmentAllotTransnational> list = sbchEquipmentAllotTransnationalService.selectSbchEquipmentAllotTransnationalList(sbchEquipmentAllotTransnational);
        return AjaxResult.success(list);
    }

}
