package com.hhwy.sp.techManagement.sgjsPatentDeclare.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.enums.FlowEnum;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.common.constant.BelongBusiness;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.SgjsPatentDeclare;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.vo.PatentDeclareQueryVo;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.service.ISgjsPatentDeclareService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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

    @Autowired
    private ISgjsAchievementAwardService sgjsAchievementAwardService;


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
        sgjsAchievementAwardService.setLedger(sgjsPatentDeclareList,SgjsPatentDeclare::getId, BelongBusiness.BELONG_BUSINESS_7, SgjsPatentDeclare::setAllAwardName,SgjsPatentDeclare::setAwardList);
        FlowInfoSearchUtil.getFlowInfo(sgjsPatentDeclareList, FlowEnum.SGJS_PATENT_DECLARE);
        return getDataTableAjaxResult(sgjsPatentDeclareList);
    }

    /**
     * 保存
     * @param patentDeclare
     * @return
     */
    @PostMapping("save")
    @PreAuthorize(hasPermi = "sgjsPatentDeclare:save")
    @CustomLogger(title = "施工技术-科技管理-专利申报管理",name = "专利申报管理",businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@RequestBody SgjsPatentDeclare patentDeclare){
        Long id = sgjsPatentDeclareService.save(patentDeclare);
        return AjaxResult.success(id);
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
        if(CollectionUtils.isNotEmpty(ids)){
            sgjsPatentDeclareList = sgjsPatentDeclareService.getListByIds(ids);
        }else {
            sgjsPatentDeclareList = sgjsPatentDeclareService.getSgjsPatentDeclareList(queryVo);
        }
        sgjsAchievementAwardService.setAllAwards(sgjsPatentDeclareList,SgjsPatentDeclare::getId,SgjsPatentDeclare::setAllAward, BelongBusiness.BELONG_BUSINESS_7);
        FtExcelUtil<SgjsPatentDeclare> util = new FtExcelUtil<>(SgjsPatentDeclare.class);
        util.exportExcel(response, sgjsPatentDeclareList, DateUtils.getDate());
    }

    @PostMapping("listener")
    public AjaxResult updatePatentDeclareProcess(@RequestParam("id") Long id,@RequestParam("pass") String pass){
        sgjsPatentDeclareService.updatePatentDeclareProcess(id,pass);
        return AjaxResult.success();
    }

    @PostMapping("submit")
    public AjaxResult submitPatentDeclareProcess(@RequestParam("id") Long id){
        sgjsPatentDeclareService.submitPatentDeclareProcess(id);
        return AjaxResult.success();
    }
    /***
     * 功能描述: 消息发布
     * 作者: fushudong
     * 时间: 2024/2/1
     */
    @RequestMapping("/messagePublic")
    public AjaxResult messagePublic(String message){
        return sgjsPatentDeclareService.messagePublic(message);
    }
}
