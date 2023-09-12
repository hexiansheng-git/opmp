package com.hhwy.pm.qqch.wzch.demand.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.pm.qqch.wzch.common.service.WzchCommonService;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemand;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandDetail;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandDetailService;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandValidVO;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 物资总需用详情Controller
 * 
 * @author mls
 * @date 2022-11-15
 */
@RestController
@RequestMapping("/wzch/demandDetail")
public class WzchTotalDemandDetailController extends BaseController {

    @Resource
    private IWzchTotalDemandDetailService wzchTotalDemandDetailService;


    @Resource
    private WzchCommonService wzchCommonService;


    /**
     * 查询物资总需用详情列表
     */
   /* @PreAuthorize(hasPermi ="wzch:demandDetail:list")*/
//    @CustomLogger(title = "物资总需详情-列表查询",businessType = CustomBusinessType.SELECT)
    @PostMapping("/list")
    public AjaxResult list(@RequestBody WzchTotalDemandDetail wzchTotalDemandDetail) {
        try{
            if(wzchTotalDemandDetail.getPageNum()==null ){
                return new AjaxResult(500,"页码不能为空");
            }
            if(wzchTotalDemandDetail.getPageSize()==null){
                return new AjaxResult(500,"页数不能为空");
            }
            startPage(wzchTotalDemandDetail.getPageNum(),wzchTotalDemandDetail.getPageSize());
            List<WzchTotalDemandDetail> list = wzchTotalDemandDetailService.selectDemandDetail(wzchTotalDemandDetail);
            TableDataInfo dataTable = getDataTable(list);
            if(null==dataTable){
                return new AjaxResult(301,"未查询到数据");
            }
            return AjaxResult.success(dataTable);
        }catch (Exception e){
            return new AjaxResult(500,"查询物资总需用详情列表异常");
        }
    }

    /**
     * 导出物资总需用详情列表
     */
    /*@PreAuthorize(hasPermi ="wzch:demandDetail:export")*/
//    @CustomLogger(title = "物资总需用详情列表-导出",businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@RequestBody WzchTotalDemand demand , HttpServletResponse response) {
        try{
            String leaderFlag = "0";
            wzchTotalDemandDetailService.export(demand,leaderFlag,response);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("导出异常");
        }
    }


    /**
     * 导入物资总需用详情列表
     */
    /*@PreAuthorize(hasPermi ="wzch:demandDetail:import")*/
//    @CustomLogger(title = "物资总需用详情列表-导入",businessType = CustomBusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file,@RequestParam(value = "viewType",required = true) String viewType) {
        try {
            List<WzchTotalDemandDetail> list = wzchTotalDemandDetailService.importData(file, viewType);
            for (WzchTotalDemandDetail wzchTotalDemandDetail : list) {
                wzchTotalDemandDetail.setId(IdWorker.createId());
            }
            List<String> collect = list.stream().map(WzchTotalDemandDetail::getMaterialCode).distinct().collect(Collectors.toList());
            wzchCommonService.verifyMtlCodes(collect);
            return new AjaxResult(200, "导入成功", list);
        } catch (CustomBusinessException e) {
            return AjaxResult.error(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("导入异常");
        }
    }

    /**
     * 新增保存物资总需用详情
     */
   /* @PreAuthorize(hasPermi ="wzch:demandDetail:add")*/
//    @CustomLogger(title = "物资总需用详情列表-保存",businessType = CustomBusinessType.SAVE)
    @PostMapping("/save")
    public AjaxResult save( @Validated(ValidationGroups.Save.class) @RequestBody WzchTotalDemand wzchTotalDemand) {
        try{
            return AjaxResult.success("操作成功",String.valueOf(wzchTotalDemandDetailService.save(wzchTotalDemand)));
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("保存异常");
        }
    }

    /**
     * 新增保存物资总需用详情
     */
  /*  @PreAuthorize(hasPermi ="wzch:demandDetail:remove")*/
//    @CustomLogger(title = "物资总需用详情列表-删除",businessType = CustomBusinessType.DELETE)
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return new AjaxResult(500,"删除失败",null);
        }
        return toAjax(wzchTotalDemandDetailService.deleteWzchTotalDemandDetailByIds(ids));
    }


    @PostMapping("/demandPlan")
    public AjaxResult validMaterial(@RequestBody WzchTotalDemandDetail wzchTotalDemandDetail) {
        try {
            List<WzchTotalDemandValidVO> wzchTotalDemandValidVOS = wzchTotalDemandDetailService.validMaterial(wzchTotalDemandDetail);
            return new AjaxResult(200,"成功",wzchTotalDemandValidVOS);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询异常");
        }


    }


}
