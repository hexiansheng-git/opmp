package com.hhwy.pm.qqch.wzch.source.controller;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchSourceTotalDemandDetailVO;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchSourceTotalDemandVO;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSource;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSourceDetail;
import com.hhwy.pm.qqch.wzch.source.service.IWzchSourceDetailService;
import com.hhwy.pm.qqch.wzch.source.vo.WzchSourceDetailReminderOfChangeRequest;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 来源策划物资详情Controller
 * 
 * @author mls
 * @date 2022-11-21
 */
@RestController
@RequestMapping("/wzch/sourceDetail")
public class WzchSourceDetailController extends BaseController {

    @Autowired
    private IWzchSourceDetailService wzchSourceDetailService;



    /**
     * 查询来源策划物资详情列表
     */
  /*  @PreAuthorize(hasPermi = "wzch:sourceDetail:list")*/
    @PostMapping("/list")
    public TableDataInfo list(WzchSourceDetail wzchSourceDetail) {
        startPage();
        List<WzchSourceDetail> list = wzchSourceDetailService.selectDetailList(wzchSourceDetail);
        return getDataTable(list);
    }

    /**
     * 导出来源策划物资详情列表
     */
//    @PreAuthorize(hasPermi = "wzch:sourceDetail:export")
//    @CustomLogger(title = "来源策划详情-导出",businessType = CustomBusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@RequestBody List<WzchSourceDetail> request, HttpServletResponse response) throws IOException {
       try{
           wzchSourceDetailService.export(request,response);
       }catch (BaseException b){
           b.printStackTrace();
           throw new BaseException(b.getDefaultMessage());
       }catch (Exception e){
           e.printStackTrace();
           throw new BaseException("导出异常");
       }

    }

    /**
     * 导入来源策划物资详情列表
     */
//    @PreAuthorize(hasPermi ="wzch:sourceDetail:import")
//    @CustomLogger(title = "来源策划物资详情列表-导入",businessType = CustomBusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file,BigDecimal version) {
        try{
            List<WzchSourceDetail> list =  wzchSourceDetailService.importData(file,version);
            return new AjaxResult(200,"导入成功",list);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("请检查导入的模板以及数据是否有误");
        }
    }


    /**
     * 查询来源策划物资详情列表
     */
//    @PreAuthorize(hasPermi = "wzch:sourceDetail:save")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody WzchSource wzchSource) {
        try{
            Long id = wzchSourceDetailService.save(wzchSource);
            return new AjaxResult(200,"保存成功",id);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("保存异常");
        }
    }

    /**
     * 从物资总需同步数据到来源策划
     * @param version
     * @return
     */
    @PostMapping("/sync")
    public AjaxResult sync(String version) {
        try{
            Assert.isTrue(StringUtils.isNotBlank(version), "version不能为空");
            wzchSourceDetailService.sync(new BigDecimal(version));
            return AjaxResult.success();
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("保存异常");
        }
    }

    /**
     * 查询来源策划物资详情列表
     */
//    @PreAuthorize(hasPermi = "wzch:sourceDetail:add")
    @GetMapping("/getProjectTotalDemandDetail")
    public AjaxResult getProjectTotalDemandDetail(@RequestBody WzchSourceTotalDemandVO demandVO) {
        try {
            demandVO = wzchSourceDetailService.getProjectTotalDemandDetail(demandVO);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("获取详情异常");
        }
        return new AjaxResult(200,"成功",demandVO);
    }



    /**
     * 查询来源策划物资详情列表
     * @Param sourceId 来源策划ID
     */
    // @PreAuthorize(hasPermi = "wzch:sourceDetail:select")
    @PostMapping("/reminderOfChange")
    public AjaxResult reminderOfChange(@Validated(ValidationGroups.Select.class)  @RequestBody WzchSourceDetailReminderOfChangeRequest reminderOfChangeRequest) {
        try {
            boolean bool = wzchSourceDetailService.reminderOfChange(reminderOfChangeRequest);
            List<WzchSourceTotalDemandDetailVO> wzchSourceTotalDemandDetailVOS = wzchSourceDetailService.reminderOfChangeDetail(reminderOfChangeRequest);
            return new AjaxResult(200, "成功", bool && CollectionUtils.isNotEmpty(wzchSourceTotalDemandDetailVOS));
        } catch (BaseException b) {
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询异常");
        }
    }

    /**
     * 查询来源策划物资详情列表
     * @Param sourceId 来源策划ID
     */
//    @PreAuthorize(hasPermi = "wzch:sourceDetail:select")
    @PostMapping("/reminderOfChangeDetail")
    public AjaxResult reminderOfChangeDetail(@Validated(ValidationGroups.Select.class)  @RequestBody WzchSourceDetailReminderOfChangeRequest reminderOfChangeRequest) {
        try{
            List<WzchSourceTotalDemandDetailVO> wzchSourceTotalDemandDetailVOS = wzchSourceDetailService.reminderOfChangeDetail(reminderOfChangeRequest);
            return new AjaxResult(200,"成功",wzchSourceTotalDemandDetailVOS);
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("查询异常");
        }
    }





}
