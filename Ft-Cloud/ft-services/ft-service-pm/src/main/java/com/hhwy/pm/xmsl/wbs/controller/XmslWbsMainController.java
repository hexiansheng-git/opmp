package com.hhwy.pm.xmsl.wbs.controller;

import cn.hutool.core.lang.Assert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.push.WbsPushP6;
import com.hhwy.pm.xmsl.wbs.push.WorkPushP6;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVo;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author wk
 * @date 2023-07-13 18:02:49
 * @remark
 */
@Validated
@RestController
@RequestMapping("/xmslWbsMain")
public class XmslWbsMainController extends BaseController {

    @Autowired
    private IXmslWbsMainService xmslWbsMainService;
    @Autowired
    private IXmslWbsService wbsService;
    @Autowired
    private WbsPushP6 wbsPushP6;
    @Autowired
    private WorkPushP6 workPushP6;
    @Value("${spring.profiles.active}")
    private String profileActive; 

    @CustomLogger(title = "项目设立", name = "项目WBS管理" ,businessType = CustomBusinessType.SELECT)
    @PreAuthorize(hasPermi = "xmslWbsMain:list")
    @GetMapping
    public AjaxResult getXmslWbsMain(@Validated(ValidationGroups.Get.class) @RequestBody XmslWbsMain xmslWbsMainParam) {
        XmslWbsMain xmslWbsMain = xmslWbsMainService.getXmslWbsMain(xmslWbsMainParam);
        return AjaxResult.success(xmslWbsMain);
    }

    @CustomLogger(title = "项目设立", name = "项目WBS管理" ,businessType = CustomBusinessType.SELECT)
    @PreAuthorize(hasPermi = "xmslWbsMain:list")
    @GetMapping("/list")
    public AjaxResult getXmslWbsMainList(@Validated(ValidationGroups.Select.class) XmslWbsMain xmslWbsMainParam) {
        startPage();
        List<XmslWbsMain> xmslWbsMainList = xmslWbsMainService.getXmslWbsMainList(xmslWbsMainParam);
        FlowInfoSearchUtil.getFlowInfo(xmslWbsMainList,FlowEnum.XMSL_WBS);
        return getDataTableAjaxResult(xmslWbsMainList);
    }

    @CustomLogger(title = "项目设立", name = "项目WBS管理" ,businessType = CustomBusinessType.SELECT)
    @PreAuthorize(hasPermi = "xmslWbsMain:detail")
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody XmslWbsMain xmslWbsMainParam) {
        XmslWbsMain wbsMain = null;
        if(xmslWbsMainParam.getId() == null){
            wbsMain = xmslWbsMainService.getLast();
        }else{
            wbsMain = xmslWbsMainService.getById(xmslWbsMainParam.getId());
        }
        //是否有调整记录
        Long count = xmslWbsMainService.getXmslWbsMainCount(new XmslWbsMain());
        if(wbsMain != null)
            wbsMain.setParams(ObjectUtils.toMap(Constant.HISTORY_NOTE_FIELD_NAME,count>0?1:0));
        XmslWbsMain temp = wbsMain==null?new XmslWbsMain():wbsMain;
        if(StringUtils.equals(profileActive,"pro") || StringUtils.equals(profileActive,"dev")){
            temp.setP6ExistPrj(wbsPushP6.isPrjExist()?"1":"0");
        }else{ //测试环境和p6环境不通
            temp.setP6ExistPrj("1");
        }
        temp.setVersion(ObjectUtils.nvl(temp.getVersion(),1));
        FlowInfoSearchUtil.getFlowInfo(temp,FlowEnum.XMSL_WBS);
        return AjaxResult.success(temp);
    }

    /**
     * 获取当前调整数据
     * @return
     */
    @CustomLogger(title = "项目设立", name = "项目WBS管理" ,businessType = CustomBusinessType.SELECT)
    @PreAuthorize(hasPermi = "xmslWbsMain:adjust")
    @GetMapping("/adjustInfo")
    public AjaxResult adjustInfo() {
        XmslWbsMain wbsMain = this.xmslWbsMainService.getAdjustInfo();
        if(wbsMain == null){
            Long id = xmslWbsMainService.initAdjust();
            if(id != null){
                wbsMain = this.xmslWbsMainService.getById(id);
                FlowInfoSearchUtil.getFlowInfo(wbsMain,FlowEnum.XMSL_WBS);
            }
            return AjaxResult.success("",wbsMain);
        }
        //是否有调整记录
        Long count = xmslWbsMainService.getXmslWbsMainCount(new XmslWbsMain());
        wbsMain.setParams(ObjectUtils.toMap(Constant.HISTORY_NOTE_FIELD_NAME,count>0?1:0));
        FlowInfoSearchUtil.getFlowInfo(wbsMain,FlowEnum.XMSL_WBS);
        return AjaxResult.success(wbsMain);
    }

    @PreAuthorize(hasPermi = "xmslWbsMain:remove")
    @CustomLogger(title = "项目设立", name = "项目WBS管理" ,businessType = CustomBusinessType.DELETE)
    @PostMapping("/delete")
    public AjaxResult deleteXmslWbsMain(@Validated(ValidationGroups.Delete.class) @RequestBody XmslWbsMain xmslWbsMainParam) {
        xmslWbsMainService.deleteXmslWbsMain(xmslWbsMainParam);
        return AjaxResult.success();
    }

    @GetMapping("/export")
    @CustomLogger(title = "项目设立", name = "项目WBS管理" ,businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response, XmslWbsMain xmslWbsMainParam) throws IOException {
        List<XmslWbsMain> xmslWbsMainList = xmslWbsMainService.getXmslWbsMainList(xmslWbsMainParam);
        ExcelUtils<XmslWbsMain> util = new ExcelUtils<>(XmslWbsMain.class);
        util.exportExcel(response, xmslWbsMainList, DateUtils.getDate());
    }

    /**
     * 审批监听器
     * @param businessId
     * @return
     */
    @PostMapping("/listener")
    public AjaxResult listener(@RequestParam("id") Long businessId){
        xmslWbsMainService.finishFlow(businessId);
        return AjaxResult.success();
    }

    @PostMapping("/asyncHandler")
    public AjaxResult asyncHandler(Long id) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        XmslWbsMain main = xmslWbsMainService.getById(id);
        Assert.notNull(main,"获取数据失败");
        if(main.getValid() == Constant.YES_INT)
            return AjaxResult.error("数据已生效");
        XmslWbsMain effect = xmslWbsMainService.getEffect();
        xmslWbsMainService.asyncHandler(SecurityUtils.getTenantKey(),main,effect);
        return AjaxResult.success();
    }

    /**
     * 更新p6编码接口
     * 无需登录
     * @param vo
     * @return
     */
    @PostMapping("/updateP6Code")
    public AjaxResult updateP6Code(@RequestBody WbsInfoVo vo) {
        long begin = System.currentTimeMillis();
        try{
            xmslWbsMainService.updateP6Code(vo);
        }finally{
            long usemills = System.currentTimeMillis()-begin;
            logger.debug("更新WBS对应P6编号，耗时:{}毫秒",usemills);
        }
        return AjaxResult.success("");
    }

    @PostMapping("/repushP6")
    public AjaxResult repushP6(@RequestBody Map map) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        Long mainId = ObjectUtils.toLong(map.get("mainId"));
        xmslWbsMainService.repushP6(mainId);
        return AjaxResult.success();
    }
}
