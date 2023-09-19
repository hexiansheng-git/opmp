package com.hhwy.pm.qqch.wzch.importplan.controller;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.importplan.domain.WzchImportExportPlan;
import com.hhwy.pm.qqch.wzch.importplan.domain.WzchImportExportPlanDetail;
import com.hhwy.pm.qqch.wzch.importplan.service.IWzchImportExportPlanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * 进出口策划详情Controller
 * 
 * @author mls
 * @date 2022-12-05
 */
@RestController
@RequestMapping("/wzch/importPlanDetail")
public class WzchImportExportPlanDetailController extends BaseController {

    @Autowired
    private IWzchImportExportPlanDetailService wzchImportExportPlanDetailService;
    @Resource
    private WzchCommonService wzchCommonService;


    /**
     * 导出进出口策划详情列表
     */
    // @PreAuthorize(hasPermi ="wzch:importPlanDetail:export")
//    @CustomLogger(title = "进出口策划情-导出",businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@RequestBody List<WzchImportExportPlanDetail> list, HttpServletResponse response) {
        try{
            wzchImportExportPlanDetailService.export(list,response);

        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导出异常");
        }
    }

    /**
     * 导出进出口策划详情列表
     */
    // @PreAuthorize(hasPermi ="wzch:importPlanDetail:import")
//    @CustomLogger(title = "进出口策划详情-导入",businessType = CustomBusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) {
        try{
            // List<WzchImportExportPlanDetail> list = wzchImportExportPlanDetailService.importData(file);
            ExcelUtils<WzchImportExportPlanDetail> util = new ExcelUtils<>(WzchImportExportPlanDetail.class);
            List<WzchImportExportPlanDetail> dtoList = util.importExcel(file.getInputStream());
            HashMap<String, String> dictMap = new HashMap<>(1);
            dictMap.put("customsMode", "customs_mode");
            return AjaxResult.success(wzchCommonService.importDealDict(dtoList, dictMap));
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导出异常");
        }
    }

    /**
     * 导出进出口策划详情列表
     */
    // @PreAuthorize(hasPermi ="wzch:importPlanDetail:save")
//    @CustomLogger(title = "进出口策划详情-保存",businessType = CustomBusinessType.SAVE)
    @PostMapping("/save")
    public AjaxResult save(@RequestBody WzchImportExportPlan wzchImportExportPlan) {
        try {
            Long id = wzchImportExportPlanDetailService.save(wzchImportExportPlan);
            return new AjaxResult(200, "保存成功",id);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException("保存失败");
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("保存异常");
        }
    }


}
