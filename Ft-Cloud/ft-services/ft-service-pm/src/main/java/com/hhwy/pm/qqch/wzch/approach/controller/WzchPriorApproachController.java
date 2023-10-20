package com.hhwy.pm.qqch.wzch.approach.controller;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.wzch.approach.domain.WzchPriorApproach;
import com.hhwy.pm.qqch.wzch.approach.service.IWzchPriorApproachService;
import com.hhwy.pm.qqch.wzch.approach.vo.WzchPriorApproachAddResponse;
import com.hhwy.pm.qqch.wzch.approach.vo.WzchPriorApproachExportRequest;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 优先进场物资Controller
 * 
 * @author mls
 * @date 2022-11-28
 */
@RestController
@RequestMapping("/wzch/approach")
public class WzchPriorApproachController extends BaseController {

    @Resource
    private IWzchPriorApproachService wzchPriorApproachService;


//    @PreAuthorize(hasPermi ="wzch:approach:list")
    @PostMapping("/list")
//    @CustomLogger(title = "优先进场物资-列表查询",businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody WzchPriorApproach wzchPriorApproach) {
        //分页
        startPage();
        List<WzchPriorApproach> list = wzchPriorApproachService.selectWzchPriorApproachList(wzchPriorApproach);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return AjaxResult.success("未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }


    /**
     * 导出优先进场物资列表
     */
//    @CustomLogger(title = "优先进场物资-导出",businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@RequestBody WzchPriorApproachExportRequest request, HttpServletResponse response) {
        try {
            List<WzchPriorApproach> list = wzchPriorApproachService.exportData(request);
            ExcelUtils<WzchPriorApproach> util = new ExcelUtils<WzchPriorApproach>(WzchPriorApproach.class);
            util.exportExcel(response ,list, "优先进场物资");
        } catch (IOException e) {
           throw new BaseException("导出数据异常");
        }

    }

    /**
     * 新增保存优先进场物资
     */
//    @PreAuthorize(hasPermi ="wzch:approach:add")
    @GetMapping("/add")
    public AjaxResult addSave() {
        WzchPriorApproachAddResponse response = new WzchPriorApproachAddResponse();
        response.setId(IdWorker.createId());
        response.setCreateTime(DateUtils.getNowDate());
        response.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        return new AjaxResult(200,"成功",response);
    }

    /**
     * 修改优先进场物资
     */
//    @PreAuthorize(hasPermi ="wzch:approach:edit")
//    @CustomLogger(title = "优先进场物资-编辑",businessType = CustomBusinessType.SELECT)
    @GetMapping("/edit")
    public AjaxResult edit(Long id) {
        try{
            WzchPriorApproach wzchPriorApproach = wzchPriorApproachService.edit(id);
            return new AjaxResult(200,"成功",wzchPriorApproach);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("编辑异常");
        }
    }


    /**
     * 删除优先进场物资
     */
//    @PreAuthorize(hasPermi ="wzch:approach:remove")
//    @CustomLogger(title = "优先进场物资-删除",businessType = CustomBusinessType.DELETE)
    @PostMapping( "/remove")
    public AjaxResult remove(@RequestBody WzchPriorApproach wzchPriorApproach) {
        boolean remove = wzchPriorApproachService.remove(wzchPriorApproach);
        return new AjaxResult(200,"删除成功",remove);
    }

    /**
     * 修改优先进场物资
     */
//    @PreAuthorize(hasPermi ="wzch:approach:edit")
//    @CustomLogger(title = "优先进场物资-编辑",businessType = CustomBusinessType.SELECT)
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody WzchPriorApproach approach) {
        try{
            WzchPriorApproach wzchPriorApproach = wzchPriorApproachService.detail(approach);
            return new AjaxResult(200,"成功",wzchPriorApproach);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }
    
    /**
     * 
     * @param version
     * @return
     */
    @PostMapping("/sync")
    public AjaxResult sync(String version) {
        try{
            Assert.isTrue(StringUtils.isNotBlank(version), "version不能为空");
            wzchPriorApproachService.sync(new BigDecimal(version));
            return AjaxResult.success();
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("保存异常");
        }
    }
}
