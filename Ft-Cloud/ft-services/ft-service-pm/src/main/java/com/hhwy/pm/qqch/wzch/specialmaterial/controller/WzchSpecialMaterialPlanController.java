package com.hhwy.pm.qqch.wzch.specialmaterial.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.qqch.wzch.specialmaterial.domain.WzchSpecialMaterialPlan;
import com.hhwy.pm.qqch.wzch.specialmaterial.service.IWzchSpecialMaterialPlanService;
import com.hhwy.pm.qqch.wzch.specialmaterial.vo.WzchSpecialMaterialPlanAddResponse;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 专项物资发运策划Controller
 * 
 * @author mls
 * @date 2022-12-07
 */
@RestController
@RequestMapping("/wzch/special")
public class WzchSpecialMaterialPlanController extends BaseController {

    @Autowired
    private IWzchSpecialMaterialPlanService wzchSpecialMaterialPlanService;

    /**
     * 查询专项物资发运策划列表
     */
//    @CustomLogger(title = "专项物资发运策划-列表查询",businessType = CustomBusinessType.SELECT)
//    @PreAuthorize(hasPermi ="wzch:special:list")
//    @PostMapping("/list")
//    public AjaxResult list(@RequestBody WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
//        startPage();
//        List<WzchSpecialMaterialPlan> list = wzchSpecialMaterialPlanService.selectWzchSpecialMaterialPlanList(wzchSpecialMaterialPlan);
//        TableDataInfo dataTable = getDataTable(list);
//        if(null==dataTable){
//            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
//        }
//        return  new AjaxResult(200,"成功",dataTable);
//    }

    /**
     * 导出专项物资发运策划列表
     */
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.3专项物资发运策划" ,businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(@RequestBody WzchSpecialMaterialPlan wzchSpecialMaterialPlan, HttpServletResponse response) {
        try{
            List<WzchSpecialMaterialPlan> list = wzchSpecialMaterialPlanService.selectWzchSpecialMaterialPlanList(wzchSpecialMaterialPlan);
            ExcelUtils<WzchSpecialMaterialPlan> util = new ExcelUtils<WzchSpecialMaterialPlan>(WzchSpecialMaterialPlan.class);
            util.exportExcel(response,list, "专项物资发运策划");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导出异常");
        }

    }

    /**
     * 新增专项物资发运策划
     */
//    @GetMapping("/add")
//    public AjaxResult add() {
//        WzchSpecialMaterialPlanAddResponse response = new WzchSpecialMaterialPlanAddResponse();
//        response.setId(IdWorker.createId());
//        response.setCreateTime(DateUtils.getNowDate());
//        response.setCreateUserName(SecurityUtils.getSysUser().getNickName());
//        response.setVersionCode("1.0");
//        response.setVersionCodeStr("V1.0");
//        return new AjaxResult(200,"成功",flowService.getFlowInfo(null, FlowEnum.WSMP, response));
//    }

    /**
     * 当地运输方案策划调整
     */
//    @PreAuthorize(hasPermi ="wzch:special:modify")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.3专项物资发运策划" ,businessType = CustomBusinessType.UPDATE)
    @PostMapping("/modify")
    public AjaxResult modify(@RequestBody WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
        try{
            WzchSpecialMaterialPlan plan = wzchSpecialMaterialPlanService.modify(wzchSpecialMaterialPlan);
            return new AjaxResult(200,"成功",plan);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("调整异常");
        }
    }


    /**
     * 专项物资发运策划编辑
     */
//    @PreAuthorize(hasPermi ="wzch:special:edit")
//    @CustomLogger(title = "专项物资发运策划-编辑",businessType = CustomBusinessType.SELECT)
//    @PostMapping("/edit")
//    @ResponseBody
//    public AjaxResult edit(@RequestBody WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
//        try{
//            WzchSpecialMaterialPlan plan = wzchSpecialMaterialPlanService.detail(wzchSpecialMaterialPlan);
//            System.out.println("专项物资发运策划-编辑="+new ObjectMapper().writeValueAsString(plan));
//            return new AjaxResult(200,"成功",flowService.getFlowInfo(plan.getId(), FlowEnum.WSMP, plan));
//        }catch (BaseException b){
//            b.printStackTrace();
//            throw new BaseException("编辑失败");
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new BaseException("编辑异常");
//        }
//
//    }


    /**
     * 专项物资发运策划详情
     */
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.3专项物资发运策划" ,businessType = CustomBusinessType.SELECT)
    @PostMapping("/detail")
    @ResponseBody
    public AjaxResult detail(@RequestBody WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
        try{
            WzchSpecialMaterialPlan plan = wzchSpecialMaterialPlanService.detail(wzchSpecialMaterialPlan);
            return new AjaxResult(200,"成功",plan);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException("编辑失败");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("编辑异常");
        }

    }

    /**
     * 删除专项物资发运策划
     */
//    @PreAuthorize(hasPermi ="wzch:special:remove")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.3专项物资发运策划" ,businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody WzchSpecialMaterialPlan wzchSpecialMaterialPlan) {
        try{
            boolean bo = wzchSpecialMaterialPlanService.remove(wzchSpecialMaterialPlan);
            return new AjaxResult(200,"成功",bo);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException("删除失败");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("删除异常");
        }
    }

}
