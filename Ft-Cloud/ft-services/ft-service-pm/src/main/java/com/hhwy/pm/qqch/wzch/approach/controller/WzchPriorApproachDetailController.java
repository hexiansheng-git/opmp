package com.hhwy.pm.qqch.wzch.approach.controller;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproach;
import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproachDetail;
import com.hhwy.pm.qqch.wzch.approach.service.IWzchPriorApproachDetailService;
import com.hhwy.pm.qqch.wzch.approach.vo.WzchPriorApproachDetailResponse;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 优先进场物资详情Controller
 * 
 * @author mls
 * @date 2022-11-30
 */
@RestController
@RequestMapping("/wzch/approachDetail")
public class WzchPriorApproachDetailController extends BaseController {

    @Autowired
    private IWzchPriorApproachDetailService wzchPriorApproachDetailService;


    /**
     * 查询优先进场物资详情列表
     */

    /*@PreAuthorize(hasPermi ="wzch:approachDetail:list")*/
    @PostMapping("/initInfo")
//    @CustomLogger(title = "优先进场物资-列表查询",businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@RequestBody WzchPriorApproachDetail wzchPriorApproachDetail) {

        try{
            List<WzchPriorApproachDetailResponse> list = wzchPriorApproachDetailService.selectList(wzchPriorApproachDetail);
            return new AjaxResult(200,"成功",list);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询异常");
        }
    }


    /**
     * 导出优先进场物资详情列表
     */
//    @PreAuthorize(hasPermi = "wzch:approachDetail:export")
//    @CustomLogger(title = "优先进场物资详情-导出",businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.1物资总需用", name = "6.1.3优先进场物资台账" ,businessType = CustomBusinessType.EXPORT)
    public void export(@RequestBody List<WzchPriorApproachDetail> wzchPriorApproachDetailList, HttpServletResponse response) throws IOException {
        wzchPriorApproachDetailService.export(wzchPriorApproachDetailList,response);
    }

    /**
     * 导入优先进场物资详情列表
     */
//    @PreAuthorize(hasPermi ="wzch:approachDetail:import")
//    @CustomLogger(title = "优先进场物资详情列表-导入",businessType = CustomBusinessType.IMPORT)
    @PostMapping("/import")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.1物资总需用", name = "6.1.3优先进场物资台账" ,businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file) {
        try{
            List<WzchPriorApproachDetail> list =  wzchPriorApproachDetailService.importData(file);
            return new AjaxResult(200,"导入成功",list);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导入异常");
        }
    }


    /**
     * 保存优先进场物资
     */
    /*@PreAuthorize(hasPermi ="wzch:approach:save")*/
//    @CustomLogger(title = "优先进场物资-保存",businessType = CustomBusinessType.SAVE)
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-物资策划-6.1物资总需用", name = "6.1.3优先进场物资台账" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@RequestBody WzchPriorApproach wzchPriorApproach) {
        try {
            wzchPriorApproachDetailService.save(wzchPriorApproach);
            return new AjaxResult(200, "保存成功");
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (RuntimeException b){
            b.printStackTrace();
            throw new BaseException(b.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("保存失败");
        }

    }



}
