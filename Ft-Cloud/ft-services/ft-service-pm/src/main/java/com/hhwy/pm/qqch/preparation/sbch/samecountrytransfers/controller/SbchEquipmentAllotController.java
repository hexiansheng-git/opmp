package com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllot;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.dto.SbchEquipmentAllotDTO;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.service.ISbchEquipmentAllotService;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 同国别设备Controller
 *
 * @author hwj
 * @date 2022-11-25
 */
@Validated
@RestController
@RequestMapping("/samecountry/transfers")
public class SbchEquipmentAllotController extends BaseController {
    @Autowired
    private ISbchEquipmentAllotService sbchEquipmentAllotService;


    /**
     * 查询同国别设备列表
     */
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备配置与选型", name = "7.2.2设备调拨策划-同国别设备列表", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody SbchEquipmentAllot sbchEquipmentAllot) {
        List<SbchEquipmentAllot> list = sbchEquipmentAllotService.selectSbchEquipmentAllotList(sbchEquipmentAllot);
        TableDataInfo dataTable = getDataTable(list);
        if (null == dataTable) {
            return new AjaxResult(PmsConstant.WARN_CODE, "未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }


    /**
     * 新增保存同国别设备
     */
    @PostMapping("/add")
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备配置与选型", name = "7.2.2设备调拨策划-同国别设备列表", businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchEquipmentAllotDTO sbchEquipmentAllot) {
        try {
            return new AjaxResult(200,"成功",sbchEquipmentAllotService.insertSbchEquipmentAllotAndDetails(sbchEquipmentAllot));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }


//    /**
//     * 选择现场设备
//     */
//    @PreAuthorize(hasPermi ="samecountry:transfers:xzxcsb")
//    //@CustomLogger(title = "同国别-选择现场设备",businessType = CustomBusinessType.SELECT)
//    @PostMapping("/xzxcsb")
//    public AjaxResult xzxcsb(@Validated(ValidationGroups.Select.class) @RequestBody XcsbMonthSelfEquInfo xcsbMonthSelfEquInfo) {
//        try {
//            return sbchEquipmentAllotService.xzxcsb(xcsbMonthSelfEquInfo);
//        }catch (CustomBusinessException e){
//            e.printStackTrace();
//            return AjaxResult.error(e.getMsg());
//        }catch (Exception e){
//            e.printStackTrace();
//            return AjaxResult.error(e.getMessage());
//        }
//    }

}
