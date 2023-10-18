package com.hhwy.pm.qqch.preparation.sbch.plan.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlan;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.plan.service.ISbchTotalDemandPlanDetailService;
import com.hhwy.pm.qqch.preparation.sbch.plan.vo.ImportSbchTotalDemandPlanDetail;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchTopicResearchPlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchTopicResearchPlanExportVo;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 设备总部计划总需用详情Controller
 * 
 * @author zq
 * @date 2022-11-23
 */
@Controller
    @RequestMapping("/plan/detail")
public class SbchTotalDemandPlanDetailController extends BaseController {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ISbchTotalDemandPlanDetailService totalDemandPlanDetailService;


    /***
     * 功能描述: t
     * @param version
     * @return com.hhwy.common.core.web.domain.AjaxResult
     * 作者: fushudong
     * 时间: 2023/9/22
     */
    @GetMapping("/syncData")
    @ResponseBody
    public AjaxResult syncData(BigDecimal version){
        SbchTotalDemandPlan sbchTotalDemandPlan = totalDemandPlanDetailService.syncData(version);
        return AjaxResult.success(sbchTotalDemandPlan);
    }

    /**
     * 导入总需用详情
     * @author zq
     * @date 2022/11/25 14:06
     * @param file
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file){
        try{
            ExcelUtils<ImportSbchTotalDemandPlanDetail> util = new ExcelUtils(ImportSbchTotalDemandPlanDetail.class);
            List<ImportSbchTotalDemandPlanDetail> list = util.importExcel(file.getInputStream());
            Map<String, String> isSpecialMap = DictUtil.getDictData("is_special");
            //根据设备编号查询设备分类

            if(!ObjectNullUtil.isEmpty(list)){
                for (ImportSbchTotalDemandPlanDetail detail : list) {
                    String materialCode = detail.getMaterialCode();
                    detail.setIsSpecial(isSpecialMap.get(detail.getIsSpecial()));
                    Object materialInfo = redisUtils.hGet("materialInfoRedis", materialCode);
                    if(materialInfo!=null){
                        Map<String, Object> materialMap = JSON.parseObject(materialInfo.toString(), Map.class);
                        detail.setMaterialName(ObjectUtils.toString(materialMap.get("materialName")));
                        detail.setMaterialSpec(ObjectUtils.toString(materialMap.get("materialSpec")));
                        detail.setMaterialUnit(ObjectUtils.toString(materialMap.get("unit")));
                        detail.setMaterialType(ObjectUtils.toString(materialMap.get("categoryCode")));

                        //从categoryInfoRedis取出来分类名称
                        Object categoryInfo = redisUtils.hGet("categoryInfoRedis", ObjectUtils.toString(materialMap.get("categoryCode")));
                        Map<String, Object> categoryMap = JSON.parseObject(categoryInfo.toString(), Map.class);
                        detail.setPtVar1(ObjectUtils.toString(categoryMap.get("categoryName")));
                    }
                }
//                List<String> collect = list.stream().map(t -> t.getMaterialType()).collect(Collectors.toList());
//                materialCategory.setCategoryCodes(collect);
            }
//            List<MaterialCategory> materialCategories = materialCategoryService.selectMaterialCategoryList(materialCategory);
//            Map<String, String> categoryMap = materialCategories.stream().collect(Collectors.groupingBy(t -> t.getCategoryCode(), Collectors.collectingAndThen(Collectors.toList(), v -> v.get(0).getCategoryName())));
//            StringBuffer str = new StringBuffer("");
//            for (ImportSbchTotalDemandPlanDetail detail : list) {
//                detail.setPtVar1(categoryMap.get(detail.getMaterialType()));
//                if(ObjectNullUtil.isEmpty(categoryMap.get(detail.getMaterialType())) || ObjectNullUtil.isEmpty(materialCategories)){//找不到设备分类名称的设备
//                    str=str.append(detail.getMaterialType()+",");
//                }
//            }
//            if(!"".equals(str.toString())){
//                return AjaxResult.error(str+"设备分类编码不存在");
//            }
            return AjaxResult.success(list);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 导出
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail = new SbchTotalDemandPlanDetail();
        List<SbchTotalDemandPlanDetail> list = totalDemandPlanDetailService.selectSbchTotalDemandPlanDetailLeaderList(sbchTotalDemandPlanDetail);
        FtExcelUtil<SbchTotalDemandPlanDetail> util = new FtExcelUtil<>(SbchTotalDemandPlanDetail.class);
        util.exportExcel(response, list, DateUtils.getDate());
    }
}
