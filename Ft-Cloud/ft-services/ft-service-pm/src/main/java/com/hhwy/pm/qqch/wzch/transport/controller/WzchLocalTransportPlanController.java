package com.hhwy.pm.qqch.wzch.transport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.wzch.transport.domain.WzchLocalTransportPlan;
import com.hhwy.pm.qqch.wzch.transport.service.IWzchLocalTransportPlanService;
import com.hhwy.pm.qqch.wzch.transport.vo.WzchLocalTransportPlanAddResponse;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 当地运输方案策划Controller
 * 
 * @author mls
 * @date 2022-12-06
 */
@RestController
@RequestMapping("/wzch/transport")
public class WzchLocalTransportPlanController extends BaseController {

    @Autowired
    private IWzchLocalTransportPlanService wzchLocalTransportPlanService;
//    @Resource
//    private IActivityBusinessInfoService activityBusinessInfoService;

    /**
     * 查询当地运输方案策划列表
     */
//    @PreAuthorize(hasPermi ="wzch:transport:list")
    @PostMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.4当地运输方案策划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@RequestBody WzchLocalTransportPlan wzchLocalTransportPlan) {
        startPage();
        List<WzchLocalTransportPlan> list = wzchLocalTransportPlanService.selectWzchLocalTransportPlanList(wzchLocalTransportPlan);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return  new AjaxResult(200,"成功",dataTable);
    }

    /**
     * 导出当地运输方案策划列表
     */
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.4当地运输方案策划" ,businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@RequestBody WzchLocalTransportPlan wzchLocalTransportPlan, HttpServletResponse response) {
        try{
            List<WzchLocalTransportPlan> list = wzchLocalTransportPlanService.selectWzchLocalTransportPlanList(wzchLocalTransportPlan);
            ExcelUtils<WzchLocalTransportPlan> util = new ExcelUtils<WzchLocalTransportPlan>(WzchLocalTransportPlan.class);
            util.exportExcel(response,list, "当地运输方案策划");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导出异常");
        }

    }

    /**
     * 当地运输方案策划调整
     */
//    @PreAuthorize(hasPermi ="wzch:transport:edit")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.4当地运输方案策划" ,businessType = CustomBusinessType.UPDATE)
    @PostMapping("/modify")
    public AjaxResult modify(@RequestBody WzchLocalTransportPlan wzchLocalTransportPlan) {
        try{
            WzchLocalTransportPlan plan = wzchLocalTransportPlanService.modify(wzchLocalTransportPlan);
            return new AjaxResult(200,"成功",plan);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException("调整失败");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("调整异常");
        }
    }



    /**
     * 新增当地运输方案策划
     */
//    @PreAuthorize(hasPermi ="wzch:transport:add")
    @GetMapping("/add")
    public AjaxResult add() {
        WzchLocalTransportPlanAddResponse wzchLocalTransportPlanAddResponse = new WzchLocalTransportPlanAddResponse();
        wzchLocalTransportPlanAddResponse.setId(IdWorker.createId());
        wzchLocalTransportPlanAddResponse.setCreateTime(DateUtils.getNowDate());
        wzchLocalTransportPlanAddResponse.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        wzchLocalTransportPlanAddResponse.setVersionCode("1.0");
        return new AjaxResult(200,"成功",wzchLocalTransportPlanAddResponse);
    }

    /**
     * 当地运输方案策划详情
     */
//    @PreAuthorize(hasPermi ="wzch:transport:detail")
//    @CustomLogger(title = "当地运输方案策划-详情",businessType = CustomBusinessType.SELECT)
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody WzchLocalTransportPlan wzchLocalTransportPlan) {
        try{
            WzchLocalTransportPlan plan = wzchLocalTransportPlanService.edit(wzchLocalTransportPlan);
            return new AjaxResult(200,"成功",plan);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException("查询详情失败");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询详情异常");
        }
    }

    /**
     * 修改保存当地运输方案策划
     */
//    @PreAuthorize(hasPermi ="wzch:transport:edit")
//    @CustomLogger(title = "当地运输方案策划-编辑",businessType = CustomBusinessType.SELECT)
    @PostMapping("/edit")
    public AjaxResult editSave(@RequestBody WzchLocalTransportPlan wzchLocalTransportPlan) {
        try{
            WzchLocalTransportPlan plan = wzchLocalTransportPlanService.edit(wzchLocalTransportPlan);
            System.out.println("当地运输方案策划: "+new ObjectMapper().writeValueAsString(plan));

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
     * 删除当地运输方案策划
     */
//    @PreAuthorize(hasPermi ="wzch:transport:remove")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.4当地运输方案策划" ,businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    public AjaxResult remove(@RequestBody WzchLocalTransportPlan wzchLocalTransportPlan) {
        try{
            boolean bo = wzchLocalTransportPlanService.remove(wzchLocalTransportPlan);
            return new AjaxResult(200,"删除成功",bo);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException("删除失败");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("删除异常");
        }

    }

    /**
     * 删除当地运输方案策划
     */
    @PostMapping( "/valid")
    public void processStatus(@RequestBody WzchLocalTransportPlan wzchLocalTransportPlan) {
        try {
            wzchLocalTransportPlanService.processStatus(wzchLocalTransportPlan);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("系统异常");
        }

    }
}
