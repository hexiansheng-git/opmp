package com.hhwy.pm.xmsl.drawReview.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewRelation;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;
import com.hhwy.pm.xmsl.drawReview.dto.XmslDrawReviewDto;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewWbsService;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * 图纸复核
 * @author wk
 * @date 2023-08-07 11:23:32
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/xmslDrawReview")
public class XmslDrawReviewController extends BaseController{
    Logger logger = LoggerFactory.getLogger(XmslDrawReviewController.class);
    @Autowired
    private IXmslDrawReviewService xmslDrawReviewService;
    @Autowired
    private IXmslDrawReviewWbsService drawReviewWbsService;
    @Autowired
    private IXmslDrawReviewListService drawReviewListService;
    @Autowired
    private IXmslWbsService wbsService;
    @Autowired
    private IXmslContractListService listService;


    @PreAuthorize(hasPermi = "xmslDrawReview:historyList")
    @GetMapping("/historyList")
    public AjaxResult getXmslDrawReviewList(@Validated(ValidationGroups.Select.class) XmslDrawReview xmslDrawReviewParam){
        startPage();
        List<XmslDrawReview> xmslDrawReviewList = xmslDrawReviewService.getXmslDrawReviewList(xmslDrawReviewParam);
        return getDataTableAjaxResult(xmslDrawReviewList);
    }

    @PreAuthorize(hasAnyPermi = {"xmslDrawReview:list"})
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody XmslDrawReview drawReview) {
        if(drawReview.getId() == null){
            drawReview =xmslDrawReviewService.getLast();
        }else{
            drawReview = xmslDrawReviewService.getById(drawReview.getId());
        }
        //是否有调整记录
        Integer hasChange = xmslDrawReviewService.hasChange();
        if(drawReview != null)
            drawReview.setParams(ObjectUtils.toMap(Constant.HISTORY_NOTE_FIELD_NAME,hasChange));
        return AjaxResult.success(drawReview==null?new HashMap<>(2):drawReview);
    }

    /**
     * wbs列表
     * @param map {version,valid,parentId}
     * @return
     */
    @PostMapping("/wbsList")
    public AjaxResult wbsList(@RequestBody Map map){
        List list = xmslDrawReviewService.wbsList(map);
        return AjaxResult.success(list);
    }

    /**
     * 工程量清单列表
     * @param map {version,valid,parentId}
     * @return
     */
    @PostMapping("/engineeringList")
    public AjaxResult engineeringList(@RequestBody Map map){
        List list = xmslDrawReviewService.engineeringList(map);
        return AjaxResult.success(list);
    }

    /**
     * 获取wbs下清单、细目、配合比数据
     * @param dto
     * @return
     */
    @PostMapping("/relationWbsList")
    public AjaxResult relationWbsList(@RequestBody XmslDrawReviewDto dto){
        long beginMills = System.currentTimeMillis();
        try{
            List<XmslDrawReviewList> list = xmslDrawReviewService.relationWbsList(dto.getVersion(),dto.getMainId(),dto.getWbsCode(),dto.getWbsId());
            return AjaxResult.success(list);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            logger.debug("获取wbs的挂接数据耗时:{},mainId:{},wbsId:{}",System.currentTimeMillis()-beginMills,dto.getMainId(),dto.getWbsId());
        }
        return AjaxResult.success(new ArrayList<>(2));
    }


    /**
     * 获取清单下wbs、细目、配合比数据
     * @param dto
     * @return
     */
    @PostMapping("/relationList")
    public AjaxResult relationList(@RequestBody XmslDrawReviewDto dto){
        long beginMills = System.currentTimeMillis();
        try{
            List<XmslDrawReviewWbs> list = xmslDrawReviewService.relationList(dto.getVersion(),dto.getMainId(),dto.getListCode(),dto.getListId());
            return AjaxResult.success(list);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            logger.debug("获取清单的挂接数据耗时:{},mainId:{},wbsId:{}",System.currentTimeMillis()-beginMills,dto.getMainId(),dto.getWbsId());
        }
        return AjaxResult.success(new ArrayList<>(2));
    }


    @PreAuthorize(hasAnyPermi = {"xmslDrawReview:save"})
    @PostMapping("/save")
    public AjaxResult save(@RequestBody XmslDrawReviewDto dto){
        try{
            xmslDrawReviewService.save(dto);
            return AjaxResult.success("",ObjectUtils.toMap("id",dto.getId(),"version",dto.getVersion()));
        }catch(Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    @PreAuthorize(hasAnyPermi = {"xmslDrawReview:delete"})
    @PostMapping("/delete")
    public AjaxResult delete(@Validated(ValidationGroups.Delete.class) @RequestBody XmslDrawReview drawReview){
        xmslDrawReviewService.deleteXmslDrawReview(drawReview);
        return AjaxResult.success();
    }

//    @GetMapping("/export")
//    public void export(HttpServletResponse response, XmslDrawReview xmslDrawReviewParam) throws IOException {
//        List<XmslDrawReview> xmslDrawReviewList = xmslDrawReviewService.getXmslDrawReviewList(xmslDrawReviewParam);
//        ExcelUtils<XmslDrawReview> util = new ExcelUtils<>(XmslDrawReview.class);
//        util.exportExcel(response, xmslDrawReviewList, DateUtils.getDate());
//    }
}
