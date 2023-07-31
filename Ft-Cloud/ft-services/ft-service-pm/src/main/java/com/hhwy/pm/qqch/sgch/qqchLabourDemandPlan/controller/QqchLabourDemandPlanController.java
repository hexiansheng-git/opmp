package com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanVo;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.service.IQqchLabourDemandPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-31 16:38:26
 * @remark 1.5.2 劳动力需求计划
 */
@Validated
@RestController
@RequestMapping("/qqchLabourDemandPlan")
public class QqchLabourDemandPlanController extends BaseController{

    @Autowired
    private IQqchLabourDemandPlanService qqchLabourDemandPlanService;

                                                                                                                                                                                                                                                                                                                                                                                                                

    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:list")
    @GetMapping
    public AjaxResult getQqchLabourDemandPlan(@Validated(ValidationGroups.Get.class)  QqchLabourDemandPlan qqchLabourDemandPlanParam){
        QqchLabourDemandPlan qqchLabourDemandPlan =  qqchLabourDemandPlanService.getQqchLabourDemandPlan(qqchLabourDemandPlanParam);
        return AjaxResult.success(qqchLabourDemandPlan);
    }


    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchLabourDemandPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchLabourDemandPlan qqchLabourDemandPlanParam){
        qqchLabourDemandPlanService.insertQqchLabourDemandPlan(qqchLabourDemandPlanParam);
        return AjaxResult.success(qqchLabourDemandPlanParam);
    }


    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchLabourDemandPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchLabourDemandPlan qqchLabourDemandPlanParam){
        return toAjax(qqchLabourDemandPlanService.updateQqchLabourDemandPlan(qqchLabourDemandPlanParam));
    }

            @PreAuthorize(hasPermi = "qqchLabourDemandPlan:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchLabourDemandPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchLabourDemandPlan> qqchLabourDemandPlanListParam){
            return toAjax(qqchLabourDemandPlanService.updateQqchLabourDemandPlanList(qqchLabourDemandPlanListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchLabourDemandPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchLabourDemandPlan qqchLabourDemandPlanParam){
        return toAjax(qqchLabourDemandPlanService.deleteQqchLabourDemandPlan(qqchLabourDemandPlanParam));
    }

            @PreAuthorize(hasPermi = "qqchLabourDemandPlan:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchLabourDemandPlanByPks(@PathVariable Long[] ids){
            List<Long> qqchLabourDemandPlanPkList = Arrays.asList(ids);
            return toAjax(qqchLabourDemandPlanService.deleteQqchLabourDemandPlanByPks(qqchLabourDemandPlanPkList));
        }



    /**
     *  列表
     *
     * @param qqchLabourDemandPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchLabourDemandPlanList(@Validated(ValidationGroups.Select.class) QqchLabourDemandPlan qqchLabourDemandPlanParam){
       QqchLabourDemandPlanVo vo = qqchLabourDemandPlanService.getQqchLabourDemandPlanList(qqchLabourDemandPlanParam);
        return AjaxResult.success(vo);
    }


    /**
     *  保存/确认
     * @param qqchLabourDemandPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:save")
    @PostMapping("/save")
    public AjaxResult insertQqchLabourDemandPlanList(@Validated(ValidationGroups.Save.class) @RequestBody QqchLabourDemandPlanVo qqchLabourDemandPlanVo){
        qqchLabourDemandPlanService.save(qqchLabourDemandPlanVo);
        return AjaxResult.success();
    }

    /**
     *  查询统计
     * @param qqchLabourDemandPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:selectCount")
    @PostMapping("/selectCount")
    public AjaxResult select(@Validated(ValidationGroups.Save.class) @RequestBody QqchLabourDemandPlanVo qqchLabourDemandPlanVo){
        qqchLabourDemandPlanService.selectCount(qqchLabourDemandPlanVo);
        return AjaxResult.success();
    }


}
