package com.hhwy.pm.gm.wbs.controller;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.gm.wbs.domain.TWbs;
import com.hhwy.pm.gm.wbs.service.ITWbsService;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * wbs标准(总部)
 * @author wk
 * @date 2023-08-01 11:26:43
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/tWbs")
public class TWbsController extends BaseController{
    @Autowired
    private ITWbsService tWbsService;


    /**
     * 根据工程类型获取wbs(懒加载)
     * @param map  {engineeringType,parentId,name,nodeType}
     * @return
     */
    @PostMapping("/effectLazyList")
    public AjaxResult effectLazyList(@RequestBody Map map) {
        String engineeringType = ObjectUtils.nvlString(map.get("engineeringType"));
        Long parentId = ObjectUtils.nvlLong(map.get("parentId"),-1L);
        List<TWbs> list = tWbsService.wbsListByType(engineeringType,ObjectUtils.nvlString(map.get("name")),ObjectUtils.nvlString(map.get("nodeType")),parentId);
        return AjaxResult.success(list);
    }

    /**
     * 获取生效版本的wbs（全量）
     * @param map
     * @return
     */
    @PostMapping("/effectTreeList")
    public AjaxResult effectTreeList(@RequestBody Map map) {
        List<TWbs> list = tWbsService.wbsTreeList(map);
        return AjaxResult.success(list);
    }

    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody TWbs wbs) {
        TWbs query = new TWbs();
        query.setId(wbs.getId());
        TWbs result = tWbsService.getTWbs(query);
        return AjaxResult.success(result==null?new TWbs():result);
    }



    @PostMapping("/getChildList")
    public AjaxResult copyChildList(@RequestBody Map map) {
        String ids = ObjectUtils.nvlString(map.get("ids"));
        if(org.apache.commons.lang3.StringUtils.isBlank(ids))
            return AjaxResult.error("参数缺失");
        Map<String, List<TWbs>> resuMap = tWbsService.copyChildList(Convert.toLongArray(ids));
        return AjaxResult.success(resuMap);
    }

    @PostMapping("/getDefaultEngineeringType")
    public AjaxResult getDefaultEngineeringType() {
        String enType = tWbsService.getDefaultEngineeringType();
        return AjaxResult.success("",enType);
    }

    @PostMapping("/getTWbsByPrjWbsCode")
    public AjaxResult getTWbsByPrjWbsCode(String wbsCodes) {
        if(StringUtils.isBlank(wbsCodes))
            return AjaxResult.success();
        Map<String,TWbs> map = tWbsService.getTWbsByPrjWbsCode(SetUtils.hashSet(wbsCodes.split(",")));
        return AjaxResult.success("",map);
    }


}
