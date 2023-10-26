package com.hhwy.pm.qqch.preparation.workPlanning.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningPrjImg;
import com.hhwy.pm.qqch.preparation.workPlanning.service.IQqchWorkPlanningPrjImgService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.myEnum.InitVersionConstant;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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

    @Autowired
    private IQqchReviewService iQqchReviewService;


    @GetMapping
    public AjaxResult getQqchWorkPlanningPrjImg(@Validated(ValidationGroups.Get.class) QqchWorkPlanningPrjImg qqchWorkPlanningPrjImgParam){
        QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg =  qqchWorkPlanningPrjImgService.getQqchWorkPlanningPrjImg(qqchWorkPlanningPrjImgParam);
        return AjaxResult.success(qqchWorkPlanningPrjImg);
    }

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
            BigDecimal version = VersionUtil.getVersion("qqch_work_planning_prj_img", img.getVersion());
            img.setVersion(version);
            QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg = qqchWorkPlanningPrjImgService.getQqchWorkPlanningPrjImg(img);
            if(ObjectNullUtil.isEmpty(qqchWorkPlanningPrjImg)){
                qqchWorkPlanningPrjImg =new QqchWorkPlanningPrjImg();
                qqchWorkPlanningPrjImg.setVersion(new BigDecimal(InitVersionConstant.INIT_VERSION));
            }
            //查询阶段
            String stage = iQqchReviewService.getStage();
            qqchWorkPlanningPrjImg.setStageIdentity(stage);
            return AjaxResult.success(qqchWorkPlanningPrjImg);
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 监听器（不确定格式，临时这样写，后续会改）
     * @param businessId
     */
    @PostMapping("/listener")
    @ResponseBody
    public void listener(Long businessId){
        try{
            qqchWorkPlanningPrjImgService.listener(businessId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
