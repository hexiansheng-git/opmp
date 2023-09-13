package com.hhwy.pm.qqch.wzch.source.controller;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchSourceTotalDemandDetailVO;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchSourceTotalDemandVO;
import com.hhwy.pm.qqch.wzch.source.domain.WzchSource;
import com.hhwy.pm.qqch.wzch.source.service.IWzchSourceService;
import com.hhwy.pm.qqch.wzch.source.vo.ProjectOfChangeInfoRequest;
import com.hhwy.pm.qqch.wzch.source.vo.ReminderOfChangeResponse;
import com.hhwy.pm.qqch.wzch.source.vo.WzchSourceAddResponse;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 来源策划Controller
 * 
 * @author mls
 * @date 2022-11-21
 */
@RestController
@RequestMapping("/wzch/source")
public class WzchSourceController extends BaseController {

    @Autowired
    private IWzchSourceService wzchSourceService;


    /**
     * 查询来源策划列表
     */
    @PreAuthorize(hasPermi ="wzch:source:list")
//    @CustomLogger(title = "来源策划-列表查询",businessType = CustomBusinessType.SELECT)
    @PostMapping("/list")
    public AjaxResult list(@RequestBody WzchSource wzchSource){
        //分页
        startPage();
        List<WzchSource> list = wzchSourceService.selectWzchSourceList(wzchSource);
        TableDataInfo dataTable = getDataTable(list);
        if(null==dataTable){
            return new AjaxResult(PmsConstant.WARN_CODE,"未查询到数据");
        }
        return AjaxResult.success(dataTable);
    }

    /**
     * 导出来源策划列表
     */
//    @CustomLogger(title = "来源策划-导出",businessType = CustomBusinessType.SELECT)
    @PostMapping("/export")
    public void export( @RequestBody WzchSource wzchSource, HttpServletResponse response) {
        try {
            List<WzchSource> list = wzchSourceService.selectWzchSourceList(wzchSource);
            ExcelUtils<WzchSource> util = new ExcelUtils<WzchSource>(WzchSource.class);
            util.exportExcel(response,list, "来源策划");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 编辑操作获取详情
     */
//    @CustomLogger(title = "来源策划-详情",businessType = CustomBusinessType.SELECT)
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody WzchSourceTotalDemandVO vo) {
        //通过来源策划获取详情
        WzchSource wzchSource = wzchSourceService.detail(vo);

        return AjaxResult.success(wzchSource);
    }

    /**
     * 删除来源策划
     */
    @PreAuthorize(hasPermi ="wzch:source:remove")
//    @CustomLogger(title = "来源策划-删除",businessType = CustomBusinessType.DELETE)
    @GetMapping( "/remove")
    public AjaxResult remove(String id) {
        try{
            return new AjaxResult(200,"删除成功",wzchSourceService.remove(id));
        }catch (BaseException b){
            b.printStackTrace();
            throw new BaseException(b.getDefaultMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("删除异常");
        }

    }

    /**
     * 变更情况提醒
     */
//    @CustomLogger(title = "来源策划-变更情况提醒",businessType = CustomBusinessType.SELECT)
    @GetMapping( "/reminderOfChange")
    public AjaxResult reminderOfChange(){
        //1.根据项目ID查 和当前版本查物资总需当前版本是否有效，
        try{
            // List<ReminderOfChangeResponse> reminderOfChangeResponses = wzchSourceService.reminderOfChange();
            List<WzchSource> wzchSources = wzchSourceService.selectWzchSourceList(new WzchSource());
            if (CollectionUtils.isEmpty(wzchSources)) {
                return new AjaxResult(201,"没有变更数据");
            }
            List<ReminderOfChangeResponse> reminderOfChangeResponses = new ArrayList<>();
            wzchSources.forEach(w ->{
                if(StringUtils.isNotBlank(w.getDemandNewVersion()) && StringUtils.isNotBlank(w.getDemandVersion())
                        && !w.getDemandNewVersion().equals(w.getDemandVersion()) ){
                    ReminderOfChangeResponse reminderOfChangeResponse = new ReminderOfChangeResponse();
                    reminderOfChangeResponse.setProjectId(w.getProjectId());
                    reminderOfChangeResponse.setProjectName(w.getProjectName());
                    reminderOfChangeResponse.setDemandNewVersion(w.getDemandNewVersion());
                    reminderOfChangeResponse.setDemandValidDate(w.getDemandValidDate());
                    reminderOfChangeResponses.add(reminderOfChangeResponse);
                }
            });

            return new AjaxResult(200,"成功",reminderOfChangeResponses);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("变更情况提醒异常");
        }

    }

    /**
     * 变更清情况提醒
     */
    @PreAuthorize(hasPermi ="wzch:source:add")
//    @CustomLogger(title = "来源策划-新增按钮",businessType = CustomBusinessType.SELECT)
    @GetMapping( "/add")
    public AjaxResult add(){
        WzchSourceAddResponse wzchSourceAddResponse = new WzchSourceAddResponse();
        wzchSourceAddResponse.setId(IdWorker.createId());
        wzchSourceAddResponse.setCreateUserName(SecurityUtils.getSysUser().getNickName());
        wzchSourceAddResponse.setCreateTime(DateUtils.getNowDate());
        return AjaxResult.success(wzchSourceAddResponse);
    }

    /**
     * 变更情况提醒
     */
//    @CustomLogger(title = "来源策划-物资总需项目变更信息",businessType = CustomBusinessType.SELECT)
    @PostMapping( "/projectOfChangeInfo")
    public AjaxResult projectOfChangeInfo(@Validated(ValidationGroups.Select.class) @RequestBody ProjectOfChangeInfoRequest request){
        try{
            List<WzchSourceTotalDemandDetailVO> wzchSourceTotalDemandDetailVOS = wzchSourceService.projectOfChangeInfo(request);
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
