package com.hhwy.sp.techManagement.sgjsPatentDeclare.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.enums.FlowEnum;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.SgjsPatentDeclare;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.vo.PatentDeclareQueryVo;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.service.ISgjsPatentDeclareService;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:25
 * @remark 专利申报管理
 */
@Validated
@RestController
@RequestMapping("/sgjsPatentDeclare")
public class SgjsPatentDeclareController extends BaseController {

    @Autowired
    private ISgjsPatentDeclareService sgjsPatentDeclareService;


    /**
     * 根据id获取数据
     * @param id
     * @param type 1：编辑   2：成果登记
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsPatentDeclare:list")
    @GetMapping("getSgjsPatentDeclareById")
    public AjaxResult getSgjsPatentDeclareById(Long id,String type) {
        SgjsPatentDeclare sgjsPatentDeclare = sgjsPatentDeclareService.getSgjsPatentDeclareById(id, type);
        FlowInfoSearchUtil.getFlowInfo(sgjsPatentDeclare, FlowEnum.SGJS_PATENT_DECLARE);
        return AjaxResult.success(sgjsPatentDeclare);
    }

    /**
     * 专利申报管理台账
     * @param queryVo
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsPatentDeclare:list")
    @GetMapping("/list")
    public AjaxResult getSgjsPatentDeclareList(@Validated(ValidationGroups.Select.class) PatentDeclareQueryVo queryVo) {
        startPage();
        List<SgjsPatentDeclare> sgjsPatentDeclareList = sgjsPatentDeclareService.getSgjsPatentDeclareList(queryVo);
        FlowInfoSearchUtil.getFlowInfo(sgjsPatentDeclareList, FlowEnum.SGJS_PATENT_DECLARE);
        return getDataTableAjaxResult(sgjsPatentDeclareList);
    }

    /**
     * 保存
     * @param patentDeclare
     * @return
     */
    @PostMapping("save")
    public AjaxResult save(@RequestBody SgjsPatentDeclare patentDeclare){
        sgjsPatentDeclareService.save(patentDeclare);
        return AjaxResult.success();
    }

    /**
     * 提交
     * @param patentDeclare
     * @return
     */
    @PostMapping("submit")
    public AjaxResult submit(@RequestBody SgjsPatentDeclare patentDeclare){
        sgjsPatentDeclareService.submit(patentDeclare);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "sgjsPatentDeclare:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsPatentDeclare(@Validated(ValidationGroups.Save.class) @RequestBody SgjsPatentDeclare sgjsPatentDeclareParam) {
        sgjsPatentDeclareService.insertSgjsPatentDeclare(sgjsPatentDeclareParam);
        return AjaxResult.success(sgjsPatentDeclareParam);
    }

    @PreAuthorize(hasPermi = "sgjsPatentDeclare:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsPatentDeclareList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsPatentDeclare> sgjsPatentDeclareListParam) {
        sgjsPatentDeclareService.insertSgjsPatentDeclareList(sgjsPatentDeclareListParam);
        return AjaxResult.success(sgjsPatentDeclareListParam);
    }

    @PreAuthorize(hasPermi = "sgjsPatentDeclare:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsPatentDeclare(@Validated(ValidationGroups.Update.class) @RequestBody SgjsPatentDeclare sgjsPatentDeclareParam) {
        return toAjax(sgjsPatentDeclareService.updateSgjsPatentDeclare(sgjsPatentDeclareParam));
    }

    @PreAuthorize(hasPermi = "sgjsPatentDeclare:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsPatentDeclareList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsPatentDeclare> sgjsPatentDeclareListParam) {
        return toAjax(sgjsPatentDeclareService.updateSgjsPatentDeclareList(sgjsPatentDeclareListParam));
    }

    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsPatentDeclare:remove")
    @PostMapping("/deleteById/{id}")
    public AjaxResult deleteSgjsPatentDeclareById(@PathVariable Long id) {
        sgjsPatentDeclareService.deleteSgjsPatentDeclareById(id);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "sgjsPatentDeclare:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsPatentDeclareByPks(@PathVariable Long[] ids) {
        List<Long> sgjsPatentDeclarePkList = Arrays.asList(ids);
        return toAjax(sgjsPatentDeclareService.deleteSgjsPatentDeclareByPks(sgjsPatentDeclarePkList));
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody PatentDeclareQueryVo queryVo) throws IOException {
        List<Long> ids = queryVo.getIds();
        List<SgjsPatentDeclare> sgjsPatentDeclareList;
        if(CollectionUtils.isEmpty(ids)){
            sgjsPatentDeclareList = sgjsPatentDeclareService.getListByIds(ids);
        }else {
            sgjsPatentDeclareList = sgjsPatentDeclareService.getSgjsPatentDeclareList(queryVo);
        }

        FtExcelUtil<SgjsPatentDeclare> util = new FtExcelUtil<>(SgjsPatentDeclare.class);
        util.exportExcel(response, sgjsPatentDeclareList, DateUtils.getDate());
    }
}
