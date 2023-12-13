package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;

import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeam;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeamDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.dto.SbchEquipmentTeamDTO;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.service.ISbchEquipmentTeamService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.vo.ImportSbchEquipmentTeamDetailsDetails;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 协作单位设备管理Controller
 * 
 * @author hwj
 * @date 2022-11-30
 *
 * 7.2.4
 */
@Controller
@RequestMapping("/equipmentteam/team")
public class SbchEquipmentTeamController extends BaseController {

    @Autowired
    private ISbchEquipmentTeamService sbchEquipmentTeamService;
    
    /**
     * 查询协作单位设备管理列表
     */
//    @PreAuthorize(hasPermi ="equipmentteam:team:list")
    @GetMapping("/getList")
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备配置与选型", name = "7.2.4协作单位设备管理", businessType = CustomBusinessType.SELECT)
    @ResponseBody
    public AjaxResult list(BigDecimal version) {

        SbchEquipmentTeam team = sbchEquipmentTeamService.getList(version);
        return AjaxResult.success(team);
    }

    /**
     * 新增保存协作单位设备管理
     */
    @PostMapping("/add")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备配置与选型", name = "7.2.4协作单位设备管理", businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody SbchEquipmentTeam sbchEquipmentTeam) {
        try {
            return new AjaxResult(200,"成功",sbchEquipmentTeamService.insertSbchEquipmentTeamAndDetails(sbchEquipmentTeam));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 导入协作单位详情
     * @param file
     * @return
     */
    @PostMapping("/importUnitSb")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备配置与选型", name = "7.2.4协作单位设备管理", businessType = CustomBusinessType.IMPORT)
    public AjaxResult importUnitSb(MultipartFile file){
        try{
            ExcelUtils<ImportSbchEquipmentTeamDetailsDetails> util = new ExcelUtils(ImportSbchEquipmentTeamDetailsDetails.class);
            List<ImportSbchEquipmentTeamDetailsDetails> list = util.importExcel(file.getInputStream());
            return AjaxResult.success(list);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    @PostMapping("/importUnit")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备配置与选型", name = "7.2.4协作单位设备管理", businessType = CustomBusinessType.IMPORT)
    public AjaxResult importUnit(MultipartFile file){
        try{
            ExcelUtils<SbchEquipmentTeamDetails> util = new ExcelUtils(SbchEquipmentTeamDetails.class);
            List<SbchEquipmentTeamDetails> list = util.importExcel(file.getInputStream());
            return AjaxResult.success(list);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
