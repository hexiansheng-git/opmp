package com.hhwy.pm.qqch.wzch.specialproject.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.specialproject.domain.WzchSpecialProject;
import com.hhwy.pm.qqch.wzch.specialproject.domain.WzchSpecialProjectDetail;
import com.hhwy.pm.qqch.wzch.specialproject.dto.WzchSpecialProjectDTO;
import com.hhwy.pm.qqch.wzch.specialproject.service.IWzchSpecialProjectDetailService;
import com.hhwy.pm.qqch.wzch.specialproject.service.IWzchSpecialProjectService;
import com.hhwy.utils.MaterialUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.ValidationGroups;
import io.jsonwebtoken.lang.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专项物资策划Controller
 *
 * @author mls
 * @date 2022-12-11
 */
@RestController
@RequestMapping("/wzch/specialproject")
public class WzchSpecialProjectController extends BaseController {

    @Resource
    private IWzchSpecialProjectService wzchSpecialProjectService;
    @Resource
    private WzchCommonService wzchCommonService;

    @Resource
    private IWzchSpecialProjectDetailService detailService;

//    @PostMapping("listener")
//    @CustomLogger(title = "查询采购视角", businessType = CustomBusinessType.SELECT)
//    public AjaxResult listener(@RequestBody Map<String, Object> map) {
//        DelegateTask delegateTask = JSONObject.parseObject(JSONObject.toJSONString(map.get("execution")), DelegateTask.class);
//        Map varMap = delegateTask.getVariables();
//        String businessId = (String) varMap.get("businessId");
//        wzchSpecialProjectService.updateValidStatus(businessId);
//        return AjaxResult.success("成功");
//    }


    /**
     * 新增 编辑 详情数据回显
     */
    @GetMapping("baseInfo")
//    @CustomLogger(title = "新增 编辑 详情数据回显", businessType = CustomBusinessType.SELECT)
    public AjaxResult baseInfo(@RequestParam(required = false) WzchSpecialProjectDTO dto) {
        return AjaxResult.success(wzchSpecialProjectService.baseInfo(dto==null?new WzchSpecialProjectDTO():dto));
    }


    /**
     * 查询专项物资策划列表
     */
//    @PreAuthorize(hasPermi = "wzch:specialproject:list")
    @PostMapping("/list")
//    @CustomLogger(title = "查询专项物资策划列表", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody WzchSpecialProject wzchSpecialProject) {
        startPage();
        List<WzchSpecialProject> list = wzchSpecialProjectService.selectWzchSpecialProjectList(wzchSpecialProject);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出专项物资策划列表
     */
//    @CustomLogger(title = "导出优先进场物资设备采购策划列表", businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated(ValidationGroups.Other.class) @RequestBody WzchSpecialProject wzchSpecialProject, HttpServletResponse response) {
        try {
            List<WzchSpecialProject> list = wzchSpecialProjectService.selectWzchSpecialProjectList(wzchSpecialProject);
            ExcelUtils<WzchSpecialProject> util = new ExcelUtils<>(WzchSpecialProject.class);
            util.exportExcel(response, list, "specialproject");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }

    /**
     * 新增保存专项物资策划
     */
//    @PreAuthorize(hasPermi = "wzch:specialproject:add")
    @PostMapping("/add")
//    @CustomLogger(title = "新增保存专项物资策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult add(@Validated(ValidationGroups.Save.class) @RequestBody WzchSpecialProjectDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchSpecialProjectService.insert(dto)));
    }


    /**
     * 修改保存专项物资策划
     */
//    @PreAuthorize(hasPermi = "wzch:specialproject:edit")
    @PostMapping("/edit")
//    @CustomLogger(title = "修改保存专项物资策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody WzchSpecialProjectDTO wzchSpecialProject) {
        return AjaxResult.success("操作成功", String.valueOf(wzchSpecialProjectService.edit(wzchSpecialProject)));
    }


//    @PreAuthorize(hasPermi = "wzch:priorpurchase:adjust")
    @PostMapping("/adjust")
//    @CustomLogger(title = "调整优先进场物资设备采购策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult adjust(@Validated(ValidationGroups.Update.class) @RequestBody WzchSpecialProjectDTO wzchPriorPurchaseDTO) {
        return AjaxResult.success("操作成功", String.valueOf(wzchSpecialProjectService.adjust(wzchPriorPurchaseDTO)));
    }
    /**
     * 新增保存专项物资策划
     */
//    @PreAuthorize(hasPermi = "wzch:specialproject:save")
    @PostMapping("/save")
//    @CustomLogger(title = "新增保存专项物资策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody WzchSpecialProjectDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchSpecialProjectService.save(dto)));
    }

    /**
     * 删除专项物资策划
     */
//    @PreAuthorize(hasPermi = "wzch:specialproject:remove")
    @PostMapping("/remove")
    public AjaxResult remove(String ids) {
        return toAjax(wzchSpecialProjectService.deleteWzchSpecialProjectByIds(ids));
    }


    private Map<String, String> getDictMap() {
        HashMap<String, String> map = new HashMap<>(12);
        map.put("checkStandard", "check_standard");
        map.put("prodCert", "prod_cert");
        map.put("despatchType", "despatch_type");
        map.put("designProvide", "provide_status");
        map.put("designApprove", "approve_status");
        map.put("demand", "demand");
        map.put("laterApprove", "approve_status");
        map.put("constructApprove", "approve_status");
        map.put("factoryProvide", "provide_status");
        map.put("factoryApprove", "approve_status");
        map.put("laterProvide", "provide_status");
        map.put("purchaseProcess", "purchase_process");
        map.put("bom", "bom_type");
        return map;

    }

    /**
     * 导出专项物资策划详情
     */
    @PostMapping("detail/export")
//    @CustomLogger(title = "导出专项物资策划详情", businessType = CustomBusinessType.EXPORT)
    public void exportDetail(@Validated(ValidationGroups.Other.class) @RequestBody Map<String, List<WzchSpecialProjectDetail>> params, HttpServletResponse response) {
        try {
            List<WzchSpecialProjectDetail> detailList = params.get("detailList");
            wzchCommonService.exportDealDict(detailList, this.getDictMap());
            ExcelUtils<WzchSpecialProjectDetail> util = new ExcelUtils<>(WzchSpecialProjectDetail.class);
            util.exportExcel(response, detailList, "专项物资策划详情");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }


    @PostMapping("detail/importData")
//    @CustomLogger(title = "导入专项物资策划详情", businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file, @RequestParam Map map) {
        try {
            ExcelUtils<WzchSpecialProjectDetail> util = new ExcelUtils<>(WzchSpecialProjectDetail.class);
            List<WzchSpecialProjectDetail> dtoList = util.importExcel(file.getInputStream());

            dtoList = wzchCommonService.importDealDict(dtoList, this.getDictMap());
            StringBuilder sb = new StringBuilder();
            for (WzchSpecialProjectDetail wzchSpecialProjectDetail : dtoList) {
                //从物资信息中拿物资名称
                MaterialInfo materialInfo = MaterialUtils.getMaterialInfoByCode(wzchSpecialProjectDetail.getMaterialCode());
                //校验物资信息物资编码有效
                if(materialInfo == null || materialInfo.getMaterialCode() == null){
                    if(sb.length() < 1)
                        sb.append("物资编码:");
                    sb.append(","+wzchSpecialProjectDetail.getMaterialCode());
                    continue;
                }
                wzchSpecialProjectDetail.setMaterialName(materialInfo.getMaterialName());
                wzchSpecialProjectDetail.setId(IdWorker.createId());
            }
            if(sb.length() > 0)
                return AjaxResult.error(sb+"不存在，请检查");
            return AjaxResult.success(dtoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "获取导入数据异常");
        }
    }


    /**
     * 根据项目id获取物资详情
     */
    @PostMapping("detail/getMtlDetailList")
//    @CustomLogger(title = "根据项目id获取物资详情", businessType = CustomBusinessType.SELECT)
    public AjaxResult getMtlDetailList(@RequestBody WzchSpecialProjectDetail dto) {
        try {
            List<WzchSpecialProjectDetail> list = detailService.getMtlDetailList(dto);
            return AjaxResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");

        }
    }


}
