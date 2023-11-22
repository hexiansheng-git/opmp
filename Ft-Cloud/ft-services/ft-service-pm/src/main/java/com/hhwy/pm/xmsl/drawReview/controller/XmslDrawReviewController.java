package com.hhwy.pm.xmsl.drawReview.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.bean.BeanUtils;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.*;
import com.hhwy.pm.xmsl.drawReview.dto.XmslDrawReviewDto;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewWbsService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;

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


    @PreAuthorize(hasPermi = "xmslDrawReview:historyList")
    @GetMapping("/historyList")
    public AjaxResult getXmslDrawReviewList(@Validated(ValidationGroups.Select.class) XmslDrawReview xmslDrawReviewParam){
        startPage();
        List<XmslDrawReview> xmslDrawReviewList = xmslDrawReviewService.getXmslDrawReviewList(xmslDrawReviewParam);
        FlowInfoSearchUtil.getFlowInfo(xmslDrawReviewList, FlowEnum.XMSL_DRAW_REVIEW);
        return getDataTableAjaxResult(xmslDrawReviewList);
    }

    @PreAuthorize(hasPermi = "xmslDrawReview:list")
    @PostMapping("/detail")
    public AjaxResult detali(@RequestBody XmslDrawReview drawReview) {
        if(drawReview.getId() == null){
            drawReview =xmslDrawReviewService.getEffectLast();
        }else{
            drawReview = xmslDrawReviewService.getById(drawReview.getId());
        }
        drawReview = drawReview==null?new XmslDrawReview():drawReview;
        //是否有调整记录
        Integer hasChange = xmslDrawReviewService.hasChange();
        if(drawReview != null)
            drawReview.setParams(ObjectUtils.toMap(Constant.HISTORY_NOTE_FIELD_NAME,hasChange));
        FlowInfoSearchUtil.getFlowInfo(drawReview, FlowEnum.XMSL_DRAW_REVIEW);
        return AjaxResult.success(drawReview);
    }

    /**
     * 获取调整明细
     * @return
     */
    @PreAuthorize(hasPermi = "xmslDrawReview:adjust")
    @PostMapping("/adjustDetail")
    public AjaxResult adjustDetail() {
        XmslDrawReview last =xmslDrawReviewService.getLast();
        if(last.getValid() == Constant.YES_INT){
            last.setVersion(last.getVersion()+1);
            last.setValid(Constant.NO_INT);
            new AddBaseInfoUtil<>().add(last);
            last.setId(null);
        }
        FlowInfoSearchUtil.getFlowInfo(last, FlowEnum.XMSL_DRAW_REVIEW);
        //是否有调整记录
        Integer hasChange = xmslDrawReviewService.hasChange();
        if(last != null)
            last.setParams(ObjectUtils.toMap(Constant.HISTORY_NOTE_FIELD_NAME,hasChange));
        return AjaxResult.success(last);
    }

    /**
     * wbs列表
     * @param map {version,valid,parentId,ptVar1(编号|名称条件)}
     * @return
     */
    @PostMapping("/wbsList")
    public AjaxResult wbsList(@RequestBody Map map){
        List list = xmslDrawReviewService.wbsList(map);
        return AjaxResult.success(list);
    }

    /**
     * 工程量清单列表
     * @param map {version,valid,parentId,ptVar1(编号|名称条件)}
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


    /**
     * 加载项目WbS的wbs|清单挂接关系
     * @param dto
     * @return
     */
    @PostMapping("/defaultRelationList")
    public AjaxResult defaultRelationList(@RequestBody XmslDrawReviewDto dto){
        try{
            List list = null;
            if(StringUtils.isNotBlank(dto.getWbsCode())){
                list = xmslDrawReviewService.getDefaultListRelation(dto.getWbsCode());
            }else if(StringUtils.isNotBlank(dto.getListCode())){
                list = xmslDrawReviewService.getDefaultWbsRelation(dto.getListCode());
            }else{
                return AjaxResult.error("参数有误，必须传入wbsCode或listCode");
            }
            return AjaxResult.success(list);
        }catch(Exception e){

            e.printStackTrace();
        }finally {
        }
        return AjaxResult.success(new ArrayList<>(2));
    }

    /**
     * 完整同步wbs的挂接关系
     * @param dto
     * @return
     */
    @PostMapping("/syncWbsRelation")
    public AjaxResult syncWbsRelation(@RequestBody XmslDrawReviewDto dto){
        try{
            Long id = xmslDrawReviewService.syncWbsRelation(dto.getId());
            return AjaxResult.success("success",id);
        }catch(Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }



    @PreAuthorize(hasPermi = "xmslDrawReview:save")
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

    @PreAuthorize(hasPermi = "xmslDrawReview:delete")
    @PostMapping("/delete")
    public AjaxResult delete(@Validated(ValidationGroups.Delete.class) @RequestBody XmslDrawReview drawReview){
        xmslDrawReviewService.deleteXmslDrawReview(drawReview);
        return AjaxResult.success();
    }

    @PostMapping("/listener")
    public AjaxResult listener(@RequestParam("id") Long businessId){
        xmslDrawReviewService.finishFlow(businessId);
        return AjaxResult.success();
    }

    
//    @GetMapping("/export")
//    public void export(HttpServletResponse response, XmslDrawReview xmslDrawReviewParam) throws IOException {
//        List<XmslDrawReview> xmslDrawReviewList = xmslDrawReviewService.getXmslDrawReviewList(xmslDrawReviewParam);
//        ExcelUtils<XmslDrawReview> util = new ExcelUtils<>(XmslDrawReview.class);
//        util.exportExcel(response, xmslDrawReviewList, DateUtils.getDate());
//    }

    /**
     * 图纸复核-细目-原材料
     * @param 
     * @return
     */
    @PostMapping("/sourceMaterList")
    public AjaxResult sourceMaterList(){
        List list = xmslDrawReviewService.sourceMaterList();
        return AjaxResult.success(list);
    }

    //转换物资信息为图纸复核细目
    @PostMapping("/getMaterInfo")
    public AjaxResult getMaterInfo(@RequestBody List<MaterialInfo> materList){
        List<XmslDrawReviewMaterial> list = new ArrayList<>();
        for (int i = 0; i < materList.size(); i++) {
            MaterialInfo mater = materList.get(i);
            //沥青混凝土、改性沥青混凝土、混凝土
            //TODO 挪字典项里去  030501、030502、020402
            String[] mixCategoryCodes = new String[]{"030501","030502","020402"};
            boolean isMix = false;
            for (int j = 0; j < mixCategoryCodes.length; j++) {
                if(mater.getCategoryCode().startsWith(mixCategoryCodes[j])){
                    isMix = true;
                    break;
                }
            }
            XmslDrawReviewMaterial review = new XmslDrawReviewMaterial();
            BeanUtils.copyProperties(mater,review);
            review.setMixFlag(isMix?1:0);
            review.setSourceMaterialList(new ArrayList<>(2));
            review.setCode(mater.getMaterialCode());
            review.setName(mater.getMaterialName());
            review.setSpec(mater.getMaterialSpec());
            review.setType(mater.getCategoryName());
            review.setPtVar1(mater.getCategoryCode());
            list.add(review);
        }
        return AjaxResult.success(list);
    }

}
