package com.hhwy.pm.qqch.wzch.importplan.controller;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.wzch.importplan.domain.WzchImportExportPlan;
import com.hhwy.pm.qqch.wzch.importplan.service.IWzchImportExportPlanService;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 进出口策划Controller
 * 
 * @author mls
 * @date 2022-12-05
 */
@RestController
@RequestMapping("/wzch/importPlan")
public class WzchImportExportPlanController extends BaseController {

    @Autowired
    private IWzchImportExportPlanService wzchImportExportPlanService;
    /**
     * 查询进出口策划列表
     */
//    @PreAuthorize(hasPermi ="wzch:importPlan:list")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.2进出口策划" ,businessType = CustomBusinessType.SELECT)
    @PostMapping("/list")
    public AjaxResult list(@RequestBody WzchImportExportPlan wzchImportExportPlan) {
        try{
            startPage();
            List<WzchImportExportPlan> list = wzchImportExportPlanService.selectWzchImportExportPlanList(wzchImportExportPlan);
            TableDataInfo dataTable = getDataTable(list);
            if(null==dataTable){
                return new AjaxResult(301,"未查询到数据");
            }
            return AjaxResult.success(dataTable);
        }catch (Exception e){
            return new AjaxResult(500,"查询异常");
        }
    }

    /**
     * 导出进出口策划列表
     */
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.2进出口策划" ,businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@RequestBody WzchImportExportPlan wzchImportExportPlan , HttpServletResponse response) {
        try{
            List<WzchImportExportPlan> list = wzchImportExportPlanService.selectWzchImportExportPlanList(wzchImportExportPlan);
            ExcelUtils<WzchImportExportPlan> util = new ExcelUtils<WzchImportExportPlan>(WzchImportExportPlan.class);
            util.exportExcel(response,list, "importPlan");
        }catch(Exception e){
            e.printStackTrace();
            throw new BaseException("导出异常");
        }

    }

    /**
     * 修改保存进出口策划
     */
//    @PreAuthorize(hasPermi ="wzch:importPlan:modify")
    @PostMapping("/modify")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.2进出口策划" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult modify(@RequestBody WzchImportExportPlan wzchImportExportPlan) {
        try{
            WzchImportExportPlan list =  wzchImportExportPlanService.modify(wzchImportExportPlan);
            list.setCreateTime(new Date());
            SysUser sysUser = SecurityUtils.getSysUser();
            list.setCreateUserName(sysUser.getNickName());
            list.setCreateUser(sysUser.getUserId()+"");
            return new AjaxResult(200,"成功",list);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("编辑异常");
        }
    }

    /**
     * 修改保存进出口策划
     */
//    @PreAuthorize(hasPermi ="wzch:importPlan:edit")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.2进出口策划" ,businessType = CustomBusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody WzchImportExportPlan wzchImportExportPlan) {
        try{
            WzchImportExportPlan list =  wzchImportExportPlanService.detail(wzchImportExportPlan);
            return new AjaxResult(200,"成功",list);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException("编辑失败");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("编辑异常");
        }
    }

    /**
     * 进出口策划详情
     */
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.2进出口策划" ,businessType = CustomBusinessType.SELECT)
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody WzchImportExportPlan wzchImportExportPlan) {
        try{
            WzchImportExportPlan list =  wzchImportExportPlanService.detail(wzchImportExportPlan);
            return new AjaxResult(200,"成功",list);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException("查询详情失败");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询详情异常");
        }
    }

    /**
     * 删除进出口策划
     */
//    @PreAuthorize(hasPermi ="wzch:importPlan:remove")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.2进出口策划" ,businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    public AjaxResult remove(@RequestBody WzchImportExportPlan wzchImportExportPlan) {
        try {
            boolean bool = wzchImportExportPlanService.remove(wzchImportExportPlan);
            if (bool) {
                return new AjaxResult(200, "删除成功", bool);
            } else {
                return new AjaxResult(500, "删除失败", bool);
            }
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException("删除失败");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("删除异常");
        }
    }

}
