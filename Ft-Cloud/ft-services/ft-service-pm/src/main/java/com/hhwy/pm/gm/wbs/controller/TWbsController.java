package com.hhwy.pm.gm.wbs.controller;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.gm.wbs.domain.TWbs;
import com.hhwy.pm.gm.wbs.service.ITWbsService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
        String parentCode = ObjectUtils.nvlString(map.get("parentCode"));
        Integer num = ObjectUtils.nvl(map.get("num"));
        Integer rootNum = ObjectUtils.nvl(map.get("rootNum"));
        Integer level = ObjectUtils.nvl(map.get("level"),1);
        Map<String, List<TWbs>> resuMap = tWbsService.copyChildList(parentCode,level,rootNum,num,Convert.toLongArray(ids));
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

    @PostMapping("/exportData")
    public void export(@RequestBody Map map, HttpServletResponse response) throws IOException {
        String engineeringType = ObjectUtils.nvlString(map.get("engineeringType"));
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            Long mainId = tWbsService.getEffectMainIdByType(engineeringType);
            if(mainId == null){
                new FtExcelUtil<>(TWbs.class).exportExcel(response, new ArrayList<>(2), DateUtils.getDate());
                return ;
            }
            List list = tWbsService.getTWbsListByMainId(mainId);
            list = TreeUtil.exportListFormat(list, (Class)String.class);
            List result = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                TWbs temp = (TWbs) list.get(i);
                if(temp.getLevel() != 1 ){ //第一层级编号默认为工程类型编码
                    temp.setCode(temp.getAncestorsName());
                }
                result.add(temp);
            }
            FtExcelUtil<TWbs> util = new FtExcelUtil<>(TWbs.class);
            util.exportExcel(response, result, DateUtils.getDate());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }


}
