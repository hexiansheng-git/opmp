package com.hhwy.pm.qqch.wzch.transport.controller;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.wzch.transport.domain.WzchLocalTransportPlan;
import com.hhwy.pm.qqch.wzch.transport.domain.WzchLocalTransportPlanDetail;
import com.hhwy.pm.qqch.wzch.transport.service.IWzchLocalTransportPlanDetailService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 当地运输方案策划详情Controller
 * 
 * @author mls
 * @date 2022-12-06
 */
@RestController
@RequestMapping("/wzch/transportDetail")
public class WzchLocalTransportPlanDetailController extends BaseController {

    @Autowired
    private IWzchLocalTransportPlanDetailService wzchLocalTransportPlanDetailService;


    /**
     * 导出当地运输方案策划详情列表
     */
   //  @PreAuthorize(hasPermi ="wzch:transportDetail:export")
    @PostMapping("/export")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.4当地运输方案策划" ,businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody List<WzchLocalTransportPlanDetail> list, HttpServletResponse response) {
        try{
            ExcelUtils<WzchLocalTransportPlanDetail> util = new ExcelUtils<WzchLocalTransportPlanDetail>(WzchLocalTransportPlanDetail.class);
            util.exportExcel(response,list, "当地运输方案策划详情");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导出异常");
        }

    }


    /**
     * 新增保存当地运输方案策划详情
     */
   //  @PreAuthorize(hasPermi ="wzch:transportDetail:save")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.4当地运输方案策划" ,businessType = CustomBusinessType.SAVE)
    @PostMapping("/save")
    public AjaxResult save(@RequestBody WzchLocalTransportPlan wzchLocalTransportPlan) {
        try{
            wzchLocalTransportPlanDetailService.save(wzchLocalTransportPlan);
            return new AjaxResult(200,"保存成功",wzchLocalTransportPlan.getId());
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (RuntimeException b){
            b.printStackTrace();
            throw new BaseException(b.getMessage());
        }catch (Exception b){
            b.printStackTrace();
            throw new BaseException("保存异常");
        }
    }


    /**
     * 当地运输方案策划详情导入
     */
    // @PreAuthorize(hasPermi ="wzch:transportDetail:import")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.3物流运输策划", name = "6.3.4当地运输方案策划" ,businessType = CustomBusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) {
        try{
            ExcelUtils<WzchLocalTransportPlanDetail> util = new ExcelUtils<>(WzchLocalTransportPlanDetail.class);
            List<WzchLocalTransportPlanDetail> dtoList = util.importExcel(file.getInputStream());
            return new AjaxResult(200,"导入成功",dtoList);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导入异常");
        }
    }


}
