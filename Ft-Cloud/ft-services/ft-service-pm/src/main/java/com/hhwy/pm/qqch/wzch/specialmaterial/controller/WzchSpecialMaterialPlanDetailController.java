package com.hhwy.pm.qqch.wzch.specialmaterial.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.enums.YesOrNoEnum;
import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialPlan;
import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialPlanDetail;
import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialRequestDetail;
import com.hhwy.pm.qqch.wzch.specialmaterial.service.IWzchSpecialMaterialPlanDetailService;
import com.hhwy.pm.qqch.wzch.specialmaterial.service.IWzchSpecialMaterialRequestDetailService;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 专项物资发运策划-发运策划Controller
 * 
 * @author mls
 * @date 2022-12-07
 */
@RestController
@RequestMapping("/wzch/transportPlan")
public class WzchSpecialMaterialPlanDetailController extends BaseController {

    @Autowired
    private IWzchSpecialMaterialPlanDetailService wzchSpecialMaterialPlanDetailService;
    @Resource
    private WzchCommonService wzchCommonService;

    /**
     * 导出专项物资发运策划-发运策划列表
     */
//    @CustomLogger(title = "专项物资发运策划-列表查询",businessType = CustomBusinessType.SELECT)
    // @PreAuthorize(hasPermi ="wzch:transportPlan:export")
    @PostMapping("/export")
    @ResponseBody
    public void exportPlanDetail(@RequestBody List<WzchSpecialMaterialPlanDetail> list, HttpServletResponse response) {
       try{
           list = wzchSpecialMaterialPlanDetailService.exportPlanDetail(list);
           ExcelUtils<WzchSpecialMaterialPlanDetail> util = new ExcelUtils<WzchSpecialMaterialPlanDetail>(WzchSpecialMaterialPlanDetail.class);
           util.exportExcel(response,list, "发运策划");
       }catch (Exception e){
           e.printStackTrace();
           throw new BaseException("导出异常");
       }

    }

    /**
     * 当地运输方案策划详情导入
     */
    // @PreAuthorize(hasPermi ="wzch:transportPlan:import")
//    @CustomLogger(title = "专项物资发运策划-发运策划-导入",businessType = CustomBusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importPlanDetail(MultipartFile file) {
        try{
            List<WzchSpecialMaterialPlanDetail> list = wzchSpecialMaterialPlanDetailService.importPlanDetail(file);
            return new AjaxResult(200,"导入成功",list);
        }catch (IOException o){
            o.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导入异常");
        }
        return null;
    }


    /**
     * 导出专项物资发运策划-发运要求
     */
    // @PreAuthorize(hasPermi ="wzch:transportPlan:import")
//    @CustomLogger(title = "项物资发运策划-发运要求-导出",businessType = CustomBusinessType.IMPORT)
    @PostMapping("/request/export")
    public void exportRequestDetail(@RequestBody List<WzchSpecialMaterialRequestDetail> list, HttpServletResponse response) {
        try{
            if(CollectionUtils.isEmpty(list)){
                list.stream().forEach(w ->{
                    w.setWarn(YesOrNoEnum.parseDesc(w.getWarn()));
                });
            }
            ExcelUtils<WzchSpecialMaterialRequestDetail> util = new ExcelUtils<WzchSpecialMaterialRequestDetail>(WzchSpecialMaterialRequestDetail.class);util.exportExcel(response,list, "专项物资发运策划");
            util.exportExcel(response,list, "发运要求");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导出异常");
        }
    }


    /**
     * 专项物资发运策划-发运要求-导入
     */
    // @PreAuthorize(hasPermi ="wzch:transportPlan:export")
//    @CustomLogger(title = "专项物资发运策划-发运要求-导入",businessType = CustomBusinessType.IMPORT)
    @PostMapping("/request/import")
    public AjaxResult importRequestDetail(MultipartFile file) {
        try{
            // List<WzchSpecialMaterialRequestDetail> list = wzchSpecialMaterialRequestDetailService.importRequestDetail(file);
            ExcelUtils<WzchSpecialMaterialRequestDetail> util = new ExcelUtils<>(WzchSpecialMaterialRequestDetail.class);
            List<WzchSpecialMaterialRequestDetail> dtoList = util.importExcel(file.getInputStream());
            Map<String, String> dic = new HashMap<>();
            dic.put("warn","warn_flag");
            wzchCommonService.importDealDict(dtoList,dic);
            return new AjaxResult(200,"导入成功",dtoList);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导入异常");
        }
    }

    /**
     * 专项物资发运策划-发运要求-保存
     */
    // @PreAuthorize(hasPermi ="wzch:transportPlan:save")
//    @CustomLogger(title = "专项物资发运策划-发运策划-保存",businessType = CustomBusinessType.SAVE)
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
        try{
            Long id = wzchSpecialMaterialPlanDetailService.save(wzchSpecialMaterialPlan);
            return new AjaxResult(200,"保存成功",id);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }



}
