package com.hhwy.sp.buildSchemeManage.review.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.file.FileUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.FlowServiceApi;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReview;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeReviewDetailQueryVo;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeReviewOpinionRecordVo;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeReviewQueryVo;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.core.system.SystemApiService;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsBuildSchemeReview")
public class SgjsBuildSchemeReviewController extends BaseController {

    @Autowired
    private ISgjsBuildSchemeReviewService sgjsBuildSchemeReviewService;
    @Autowired
    private SystemApiService systemApiService;
    @Autowired
    private FlowServiceApi flowServiceApi;

    /**
     * 台账
     * @param queryVo
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:list")
    @GetMapping("/list")
    public AjaxResult getListByQueryVo(@Validated(ValidationGroups.Select.class) BuildSchemeReviewQueryVo queryVo) {
        startPage();
        List<SgjsBuildSchemeReview> reviewList = sgjsBuildSchemeReviewService.getListByQueryVo(queryVo);
        for (SgjsBuildSchemeReview review : reviewList) {
            String schemeLevel = review.getSchemeLevel();
            if("1".equals(schemeLevel)){
                continue;
            }
            if("2".equals(schemeLevel) || "3".equals(schemeLevel)){
                FlowInfoSearchUtil.getFlowInfo(review, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_2_3);
            }
            if("4".equals(schemeLevel)){
                FlowInfoSearchUtil.getFlowInfo(review, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_4);
            }
        }
        String userName = SecurityUtils.getUserName();
        for (SgjsBuildSchemeReview review : reviewList) {
            String currentTaskIds = review.getCurrentTaskIds();
            if(StringUtils.isBlank(currentTaskIds)){
                continue;
            }
            String[] currentTaskIdStr = currentTaskIds.split(",");
            String processTaskManId = review.getProcessTaskManId();
            String[] taskManIdStr = processTaskManId.split(",");
            for (int i = 0; i < taskManIdStr.length; i++) {
                if(userName.equals(taskManIdStr[i])){
                    review.setCurrentTaskId(currentTaskIdStr[i]);
                    break;
                }
            }
        }
        //格式化方案类型
        sgjsBuildSchemeReviewService.formatSchemeType(reviewList,
                r->r.getSchemeType(),
                (t,v)->{t.setSchemeType(v); return v;});
        return getDataTableAjaxResult(reviewList);
    }

    /**
     * 详情
     * @param detailQueryVo
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:list")
    @GetMapping("/getDetail")
    public AjaxResult getDetail(BuildSchemeReviewDetailQueryVo detailQueryVo) {
        SgjsBuildSchemeReview review = sgjsBuildSchemeReviewService.getDetail(detailQueryVo);
        return AjaxResult.success(review);
    }

    /**
     * 保存
     * @param review
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:save")
    @PostMapping("/save")
    @CustomLogger(title = "施工技术-施工方案管理-施工方案评审", name = "施工方案评审保存" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody SgjsBuildSchemeReview review) {
//        Long id = sgjsBuildSchemeReviewService.save(review);
        Long id = review.getId();
        return AjaxResult.success(id);
    }

    /**
     * 导出
     * @param response
     * @param queryVo
     * @throws IOException
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody BuildSchemeReviewQueryVo queryVo) throws IOException {
        List<Long> ids = queryVo.getIds();
        List<SgjsBuildSchemeReview> reviewList;
        if(CollectionUtils.isEmpty(ids)){
            reviewList = sgjsBuildSchemeReviewService.getListByQueryVo(queryVo);
        }else {
            reviewList = sgjsBuildSchemeReviewService.getListByIds(ids);
        }
        //格式化方案类型
        sgjsBuildSchemeReviewService.formatSchemeType(reviewList,
                r->r.getSchemeType(),
                (t,v)->{t.setSchemeType(v); return v;});
        for (SgjsBuildSchemeReview review : reviewList) {
            String schemeLevel = review.getSchemeLevel();
            if("1".equals(schemeLevel)){
                continue;
            }
            if("2".equals(schemeLevel) || "3".equals(schemeLevel)){
                FlowInfoSearchUtil.getFlowInfo(review, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_2_3);
            }
            if("4".equals(schemeLevel)){
                FlowInfoSearchUtil.getFlowInfo(review, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_4);
            }
        }
        FtExcelUtil<SgjsBuildSchemeReview> util = new FtExcelUtil<>(SgjsBuildSchemeReview.class);
        util.exportExcel(response, reviewList, DateUtils.getDate());
    }

    /**
     * 同步方案清单
     * @return
     */
    @GetMapping("/sync")
    @CustomLogger(title = "施工技术-施工方案管理-施工方案评审", name = "同步" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult sync() {
        String remind = sgjsBuildSchemeReviewService.sync();
        return AjaxResult.success(remind);
    }

    /**
     * 区域总工不通过/海外事业部总工不通过/海外事业部总工修改后通过  记录历史意见并清除所有数据
     * @return
     */
    @GetMapping("/turnDown")
    @CustomLogger(title = "施工技术-施工方案管理-施工方案评审", name = "施工方案评审驳回" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult turnDown(Long reviewId) {
        sgjsBuildSchemeReviewService.turnDown(reviewId);
        return AjaxResult.success();
    }

    /**
     * 弹窗选择方案
     * @return
     */
    @GetMapping("/getSchemeList")
    public AjaxResult getSchemeList(SgjsBuildSchemeList schemeList) {
        List<SgjsBuildSchemeList> schemeListList = sgjsBuildSchemeReviewService.getSchemeList(schemeList);
        return AjaxResult.success(schemeListList);
    }

    /**
     * 获取方案评审记录
     * @param reviewId
     * @return
     */
    @GetMapping("/getSchemeReviewRecord")
    public AjaxResult getSchemeReviewRecord(Long reviewId){
        BuildSchemeReviewOpinionRecordVo recordVo = sgjsBuildSchemeReviewService.getSchemeReviewRecordVo(reviewId);
        return AjaxResult.success(recordVo);
    }

    @GetMapping("/submit")
    @CustomLogger(title = "施工技术-施工方案管理-施工方案评审", name = "施工方案评审提交" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult submitBuildSchemeReviewProcess(@RequestParam("id") Long id){
        sgjsBuildSchemeReviewService.submitBuildSchemeReviewProcess(id);
        return AjaxResult.success();
    }

    @GetMapping("/listener")
    @CustomLogger(title = "施工技术-施工方案管理-施工方案评审", name = "施工方案评审流程结束监听" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult updateBuildSchemeReviewProcess(@RequestParam("id") Long id){
        sgjsBuildSchemeReviewService.updateBuildSchemeReviewProcess(id);
        return AjaxResult.success();
    }

    @GetMapping("/checkAuditOpinon")
    @CustomLogger(title = "施工技术-施工方案管理-施工方案评审", name = "校验意见是否填写" ,businessType = CustomBusinessType.OTHER)
    public AjaxResult checkAuditOpinon(@RequestParam("flowNodeMark") String flowNodeMark,@RequestParam("username") String username
            ,@RequestParam("reviewId") Long reviewId){
        try{
            sgjsBuildSchemeReviewService.checkFillOpinoin(flowNodeMark,username,reviewId);
            return AjaxResult.success();
        }catch(Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    @GetMapping("/deleteProcess")
    @CustomLogger(title = "施工技术-施工方案管理-施工方案评审", name = "施工方案评审流程删除监听" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult deleteProcess(@RequestParam("id") Long id){
        sgjsBuildSchemeReviewService.updateBuildSchemeReviewProcess2Init(id);
        return AjaxResult.success();
    }
    

    /*
     * 功能描述: 预警消息发送
     */
    @GetMapping("/warnMessage")
    public void warnMessageSchemeReview(){
        sgjsBuildSchemeReviewService.warnMessage();
    }

    /**
     * 删除
     * @param id
     */
    @PostMapping("/deleteById")
    @CustomLogger(title = "施工技术-施工方案管理-施工方案评审", name = "施工方案评审删除" ,businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteById(Long id){
        sgjsBuildSchemeReviewService.deleteById(id);
        return AjaxResult.success("删除成功！");
    }
    
    @PostMapping("/transferTask")
    @CustomLogger(title = "施工技术-施工方案管理-施工方案评审", name = "转办" ,businessType = CustomBusinessType.OTHER)
    public AjaxResult transferTask(@RequestBody SgjsBuildSchemeReview review ) {
        String taskId = review.getTaskId();
        String username = review.getUserName();
        String nickName = review.getNickName();
        sgjsBuildSchemeReviewService.transferTask(taskId, username, nickName);
        return AjaxResult.success();
    }

    /**
     * 导出意见
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportSuggestion")
    public void export(HttpServletResponse response, HttpServletRequest request, BuildSchemeReviewDetailQueryVo detailQueryVo) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        SgjsBuildSchemeReview review = sgjsBuildSchemeReviewService.exportSuggestion(response, detailQueryVo);
        response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, review.getSchemeName()+".xls"));
    }

    @GetMapping("/getNameById")
    public AjaxResult getNameById(@RequestParam Long id) {
        SgjsBuildSchemeReview query = new SgjsBuildSchemeReview();
        query.setId(id);
        SgjsBuildSchemeReview review = this.sgjsBuildSchemeReviewService.getSgjsBuildSchemeReview(query);
        String name = "";
        if(review!=null)
            name = review.getSchemeName();
        return AjaxResult.success("",name);
    }
}

                                   