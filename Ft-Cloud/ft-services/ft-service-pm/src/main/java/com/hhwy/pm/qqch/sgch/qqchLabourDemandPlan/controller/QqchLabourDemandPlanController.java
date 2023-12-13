package com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanDto;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanVo;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.service.IQqchLabourDemandPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ldd
 * @date 2023-07-31 16:38:26
 * @remark 1.5.2 劳动力需求计划
 */
@Validated
@RestController
@RequestMapping("/qqchLabourDemandPlan")
public class QqchLabourDemandPlanController extends BaseController {

    @Autowired
    private IQqchLabourDemandPlanService qqchLabourDemandPlanService;


//    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:list")
    @GetMapping
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-人员总需计划", name = "1.5.2劳动力需求计划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchLabourDemandPlan(@Validated(ValidationGroups.Get.class) QqchLabourDemandPlan qqchLabourDemandPlanParam) {
        QqchLabourDemandPlan qqchLabourDemandPlan = qqchLabourDemandPlanService.getQqchLabourDemandPlan(qqchLabourDemandPlanParam);
        return AjaxResult.success(qqchLabourDemandPlan);
    }


//    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:add")
    @PostMapping("/add")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-人员总需计划", name = "1.5.2劳动力需求计划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchLabourDemandPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchLabourDemandPlan qqchLabourDemandPlanParam) {
        qqchLabourDemandPlanService.insertQqchLabourDemandPlan(qqchLabourDemandPlanParam);
        return AjaxResult.success(qqchLabourDemandPlanParam);
    }


//    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchLabourDemandPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchLabourDemandPlan qqchLabourDemandPlanParam) {
        return toAjax(qqchLabourDemandPlanService.updateQqchLabourDemandPlan(qqchLabourDemandPlanParam));
    }

//    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchLabourDemandPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchLabourDemandPlan> qqchLabourDemandPlanListParam) {
        return toAjax(qqchLabourDemandPlanService.updateQqchLabourDemandPlanList(qqchLabourDemandPlanListParam));
    }

//    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchLabourDemandPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchLabourDemandPlan qqchLabourDemandPlanParam) {
        return toAjax(qqchLabourDemandPlanService.deleteQqchLabourDemandPlan(qqchLabourDemandPlanParam));
    }

//    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchLabourDemandPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchLabourDemandPlanPkList = Arrays.asList(ids);
        return toAjax(qqchLabourDemandPlanService.deleteQqchLabourDemandPlanByPks(qqchLabourDemandPlanPkList));
    }

    /**
     *  列表右上角统计信息
     *  劳动力总需人数： 154   中方人数： 35  外方人数 67  外方人员比例： 10%
     */
//    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:list")
    @GetMapping("/personTypeStatistics")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-人员总需计划", name = "1.5.2劳动力需求计划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult personTypeStatistics(@Validated(ValidationGroups.Select.class) QqchLabourDemandPlan qqchLabourDemandPlanParam){
        Map<String, Integer> result =  qqchLabourDemandPlanService.personNumCalc(qqchLabourDemandPlanParam);
        return AjaxResult.success(result);
    }

    /**
     * 列表
     *
     * @param qqchLabourDemandPlanParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-人员总需计划", name = "1.5.2劳动力需求计划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchLabourDemandPlanList(@Validated(ValidationGroups.Select.class) QqchLabourDemandPlan qqchLabourDemandPlanParam) {
        QqchLabourDemandPlanVo vo = qqchLabourDemandPlanService.getQqchLabourDemandPlanList(qqchLabourDemandPlanParam);
        return AjaxResult.success(vo);
    }


    /**
     * 列表
     *
     * @param qqchLabourDemandPlanParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:list")
    @GetMapping("/listTreeWithSearch")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-人员总需计划", name = "1.5.2劳动力需求计划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult listTreeWithSearch(@Validated(ValidationGroups.Select.class) QqchLabourDemandPlan qqchLabourDemandPlanParam) {
        QqchLabourDemandPlanVo vo = qqchLabourDemandPlanService.getQqchLabourDemandPlanListWithSearch(qqchLabourDemandPlanParam);
        return AjaxResult.success(vo);
    }


    /**
     * 保存/确认
     *
     * @param qqchLabourDemandPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-人员总需计划", name = "1.5.2劳动力需求计划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchLabourDemandPlanList(@Validated(ValidationGroups.Save.class) @RequestBody QqchLabourDemandPlanVo qqchLabourDemandPlanVo) {
        qqchLabourDemandPlanService.save(qqchLabourDemandPlanVo);
        return AjaxResult.success();
    }

    /**
     * 全部工种
     *
     * @param qqchLabourDemandPlanVo
     * @return
     */
    @PostMapping("/getAllWorkType")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-人员总需计划", name = "1.5.2劳动力需求计划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getAllWorkType(@Validated(ValidationGroups.Save.class) @RequestBody QqchLabourDemandPlanVo qqchLabourDemandPlanVo) {
        List<String> list = qqchLabourDemandPlanService.getAllWorkType(qqchLabourDemandPlanVo);
        return AjaxResult.success(list);
    }

    /**
     * 查询统计
     *
     * @param
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:selectCount")
    @PostMapping("/selectCount")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-人员总需计划", name = "1.5.2劳动力需求计划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult select(@Validated(ValidationGroups.Save.class) @RequestBody QqchLabourDemandPlan qqchLabourDemandPlan) throws ParseException {
        if (qqchLabourDemandPlan.getStartTime() == null || qqchLabourDemandPlan.getEndTime() == null) {
            return AjaxResult.error("开始时间或者结束时间不能为空");
        }
        if (qqchLabourDemandPlan.getJobNames().size() == 0) {
            return AjaxResult.error("至少传一种工种");
        }
        List<QqchLabourDemandPlanDto> list = qqchLabourDemandPlanService.selectCount(qqchLabourDemandPlan);
        List<Date> dates = list.stream().map(QqchLabourDemandPlanDto::getTime).collect(Collectors.toList());
        List<String> stringDate = new ArrayList<>();
        for (Date date : dates) {
            stringDate.add(new SimpleDateFormat("yyyy-MM").format(date));
        }
        String[] dates1 = stringDate.toArray(new String[stringDate.size()]);
        List<BigDecimal> num = list.stream().map(QqchLabourDemandPlanDto::getNum).collect(Collectors.toList());
        BigDecimal[] num1 = num.toArray(new BigDecimal[num.size()]);

        List<Object[]> list1 = new ArrayList<>();
        list1.add(dates1);
        list1.add(num1);
        return AjaxResult.success(list1);
    }


    /**
     * 获取施工部署数据
     *
     * @param
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchLabourDemandPlan:sychData")
    @PostMapping("/sychData")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-人员总需计划", name = "1.5.2劳动力需求计划" ,businessType = CustomBusinessType.OTHER)
    public AjaxResult sychData(@RequestBody QqchLabourDemandPlanVo vo1) {
        QqchLabourDemandPlanVo vo = qqchLabourDemandPlanService.sychData(vo1);
        return AjaxResult.success(vo);
    }


    /***
     * 功能描述: 获取班组和进、出场时间  213弹窗
     * @param workTeam 工作班组
     * 作者: fushudong
     * 时间: 2023/11/14
     */
    @GetMapping("/getWorkTeamList")
    @CustomLogger(title = "前期策划-前期策划编制-施工策划-人员总需计划", name = "1.5.2劳动力需求计划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getWorkTeamList(@RequestParam(value = "workTeam", required = false) String workTeam){
        List<Map> result = qqchLabourDemandPlanService.getWorkTeamList(workTeam);
        return AjaxResult.success(result);
    }

}
