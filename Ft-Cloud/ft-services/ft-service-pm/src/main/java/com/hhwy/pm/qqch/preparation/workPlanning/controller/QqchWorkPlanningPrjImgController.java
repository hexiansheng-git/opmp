package com.hhwy.pm.qqch.preparation.workPlanning.controller;

import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningPrjImg;
import com.hhwy.pm.qqch.preparation.workPlanning.service.IQqchWorkPlanningPrjImgService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author zq
 * @date 2023-07-17 14:18:26
 * @remark 1.4.1项目总平面图规划
 */
@Validated
@RestController
@RequestMapping("/qqchWorkPlanningPrjImg")
public class QqchWorkPlanningPrjImgController extends BaseController{

    @Autowired
    private IQqchWorkPlanningPrjImgService qqchWorkPlanningPrjImgService;


                                                                                                                                                                

    @PreAuthorize(hasPermi = "qqchWorkPlanningPrjImg:list")
    @GetMapping
    public AjaxResult getQqchWorkPlanningPrjImg(@Validated(ValidationGroups.Get.class) QqchWorkPlanningPrjImg qqchWorkPlanningPrjImgParam){
        QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg =  qqchWorkPlanningPrjImgService.getQqchWorkPlanningPrjImg(qqchWorkPlanningPrjImgParam);
        return AjaxResult.success(qqchWorkPlanningPrjImg);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanningPrjImg:add")
    @PostMapping("/save")
    public AjaxResult insertQqchWorkPlanningPrjImg(@Validated(ValidationGroups.Save.class) @RequestBody QqchWorkPlanningPrjImg qqchWorkPlanningPrjImgParam){
        try{
            qqchWorkPlanningPrjImgService.insertQqchWorkPlanningPrjImg(qqchWorkPlanningPrjImgParam);
            return AjaxResult.success(qqchWorkPlanningPrjImgParam);
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    @GetMapping("/detail")
    public AjaxResult detail(QqchWorkPlanningPrjImg  img){
        try{
            QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg = null;
            if(ObjectNullUtil.isEmpty(img.getVersion())){
                qqchWorkPlanningPrjImg = qqchWorkPlanningPrjImgService.getQqchWorkPlanningPrjIsValid(img);//拿版本号最大且有效的
            }else{
                qqchWorkPlanningPrjImg = qqchWorkPlanningPrjImgService.getQqchWorkPlanningPrjHistory(img);
            }

            return AjaxResult.success(qqchWorkPlanningPrjImg);
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
