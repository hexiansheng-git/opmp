package com.hhwy.pm.qqch.wzch.internaladjust.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchAllotMaterial;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchAllotMaterialRange;
import com.hhwy.pm.qqch.wzch.internaladjust.service.IWzchAllotMaterialService;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.log.annotation.Log;
import com.hhwy.common.log.enums.BusinessType;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 可调拨材料Controller
 *
 * @author mls
 * @date 2022-11-17
 */
@RestController
@RequestMapping("/wzch/allotMaterial")
public class WzchAllotMaterialController extends BaseController {
    @Value("${WSPlatform}")
    private String WSPlatform;
    @Autowired
    private IWzchAllotMaterialService wzchAllotMaterialService;


    @Resource
    private WzchCommonService wzchCommonService;


//    @Autowired
//    private ICountryInfoService countryInfoService;
    
    /**
     * 查询可调拨材料列表
     */
//    @PostMapping("country/list")
//    @CustomLogger(title = "查询可调拨材料列表", businessType = CustomBusinessType.SELECT)
//    public AjaxResult country(@Validated(ValidationGroups.Select.class) @RequestBody Map<String, String> wzchAllotMaterial) {
//        List<CountryInfo> list = countryInfoService.selectCountryInfoList(new CountryInfo());
//        return AjaxResult.success(getDataTable(list));
//    }


    /**
     * 查询可调拨材料列表
     */
//    @PreAuthorize(hasPermi = "wzch:internalajust:list")
    @PostMapping("/list")
//    @CustomLogger(title = "查询可调拨材料列表", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody WzchAllotMaterial wzchAllotMaterial) {
        startPage();
        List<WzchAllotMaterial> list = wzchAllotMaterialService.selectWzchAllotMaterialList(wzchAllotMaterial);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出可调拨材料列表
     */
    @PostMapping("/export")
//    @CustomLogger(title = "导出可调拨材料列表", businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody WzchAllotMaterial wzchAllotMaterial, HttpServletResponse response) {
        try {
            List<WzchAllotMaterial> list = wzchAllotMaterialService.selectWzchAllotMaterialList(wzchAllotMaterial);
            ExcelUtils<WzchAllotMaterial> util = new ExcelUtils<>(WzchAllotMaterial.class);
            util.exportExcel(response, list, "可调拨材料");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }

    @PostMapping("/importData")
//    @CustomLogger(title = "导入可调拨材料列表", businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file, @RequestParam Map<String, String> map) {
        try {
            ExcelUtils<WzchAllotMaterial> util = new ExcelUtils<>(WzchAllotMaterial.class);
            List<WzchAllotMaterial> wzchAllotMaterials = util.importExcel(file.getInputStream());
            WzchAllotMaterial wzchAllotMaterial = JSONObject.parseObject(JSONObject.toJSONString(map), WzchAllotMaterial.class);
            wzchAllotMaterialService.importData(wzchAllotMaterials, wzchAllotMaterial);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
        return AjaxResult.success("成功");
    }


    /**
     * 新增保存可调拨材料
     */
//    @PreAuthorize(hasPermi = "wzch:internalajust:add")
    @Log(title = "可调拨材料", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult save(@RequestBody WzchAllotMaterial wzchAllotMaterial) {
        long id = wzchAllotMaterialService.save(wzchAllotMaterial);
        return AjaxResult.success("操作成功", String.valueOf(id));
    }

    /**
     * 删除可调拨材料
     */
//    @PreAuthorize(hasPermi = "wzch:internalajust:remove")
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody Map<String, String> map) {
        String ids = map.get("ids");
        return toAjax(wzchAllotMaterialService.deleteWzchAllotMaterialByIds(ids));
    }

    /**
     * 更改范围
     *
     * @param wzchAllotMaterial
     * @return
     */
    @PostMapping("/changeRange")
    public AjaxResult changeRange(@RequestBody WzchAllotMaterial wzchAllotMaterial) {
        return toAjax(wzchAllotMaterialService.changeRange(wzchAllotMaterial));
    }


    /**
     * 查询范围
     *
     * @param range
     * @return
     */
    @PostMapping("/selectRange")
    public AjaxResult selectRange(@RequestBody WzchAllotMaterialRange range) {
        return AjaxResult.success(wzchAllotMaterialService.selectRange(range));
    }



    /**
     * 可调拨计划表
     * 调用物设
     */
    @PostMapping("adjustMtlList")
    @ResponseBody
//    @CustomLogger(title = "可调拨计划表", businessType = CustomBusinessType.SELECT)
    public AjaxResult adjustMtlList(@RequestBody MtlDTO dto) {
        String url = WSPlatform + "/basic-api/pms/wzch/allotMaterial/adjustMtlListForPm";
        AjaxResult ajaxResult;
        String tenantKey = SecurityUtils.getTenantKey();
//        tenantKey = "PJ2022016704";
        Map map = ObjectUtils.toMap("projectCode",tenantKey,"materialCode",ObjectUtils.nvlString(dto.getMaterialCode()));
        try {
            String resp = HttpUtil.post(url, JSON.toJSONString(map), 3000);
            ajaxResult = JSON.parseObject(resp, AjaxResult.class);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult = AjaxResult.error("网络异常，请求无法到达物设系统");
        }
        return ajaxResult;
    }


//    @PreAuthorize(hasPermi = "wzch:internalajust:add")
    @Log(title = "可调拨材料", businessType = BusinessType.INSERT)
    @PostMapping("/saveList")
    public AjaxResult saveList(@RequestBody AllotMaterialDTO dto) {
        long id = wzchAllotMaterialService.saveList(dto.getRange(), dto.projectId, dto.getProjectName(), dto.countryCode, dto.mtlInfoList);
        return AjaxResult.success("操作成功", String.valueOf(id));
    }


    @Data
    @ToString
    public static class MtlDTO implements Serializable {
        private String projectId;
        private String materialCode;
    }


    @Data
    @ToString
    public static class AllotMaterialDTO implements Serializable {
        // 可调拨范围
        private String range;
        // 国家编码
        private List<String> countryCode;
        // 项目id
        private String projectId;
        // 项目名称
        private String projectName;
        // 可调拨物资
        private List<WzchAllotMaterial> mtlInfoList;
    }
}
