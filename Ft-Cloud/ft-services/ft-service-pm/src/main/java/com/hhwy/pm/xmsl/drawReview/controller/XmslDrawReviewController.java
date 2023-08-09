package com.hhwy.pm.xmsl.drawReview.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


    @PreAuthorize(hasPermi = "xmslDrawReview:list")
    @GetMapping("/list")
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
     * @param wbs {version}
     * @return
     */
    @PostMapping("/wbsList")
    public AjaxResult wbsList(@RequestBody XmslDrawReviewWbs wbs){
        if(wbs.getMainId() == null){
            XmslWbs query = new XmslWbs();
            query.setParentId(ObjectUtils.nvlString(wbs.getParentId()));
            List<XmslWbs> list = wbsService.latestData(query);
            for (int i = 0; i < list.size(); i++) {
                XmslWbs temp = list.get(i);
                temp.setWbsId(temp.getId());
                temp.setId(null);
            }
            return AjaxResult.success(list);
        }
        wbs.setVersionFlag(Constant.YES_INT);
        List<XmslDrawReviewWbs> list = drawReviewWbsService.getXmslDrawReviewWbsList(wbs);
        return AjaxResult.success(list);
    }

    /**
     * 工程量清单列表
     * @param list
     * @return
     */
    @PostMapping("/engineeringList")
    public AjaxResult engineeringList(@RequestBody XmslDrawReviewList list){
        if(list.getMainId() == null){
            XmslContractList queryList = new XmslContractList();
            queryList.setPid(list.getPid()==null?0L:list.getPid());
            List<XmslContractList> conList = listService.getEffectList(queryList);
            for (int i = 0; i < conList.size(); i++) {
                XmslContractList temp = conList.get(i);
                temp.setPtVar1(temp.getId());
                temp.setId(null);
            }
            return AjaxResult.success(list);
        }
        list.setVersionFlag(Constant.YES_INT);
        List<XmslDrawReviewList> resuList = drawReviewListService.getXmslDrawReviewListList(list);
        return AjaxResult.success(resuList);
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

        return null;
    }

//    @GetMapping("/export")
//    public void export(HttpServletResponse response, XmslDrawReview xmslDrawReviewParam) throws IOException {
//        List<XmslDrawReview> xmslDrawReviewList = xmslDrawReviewService.getXmslDrawReviewList(xmslDrawReviewParam);
//        ExcelUtils<XmslDrawReview> util = new ExcelUtils<>(XmslDrawReview.class);
//        util.exportExcel(response, xmslDrawReviewList, DateUtils.getDate());
//    }
}
