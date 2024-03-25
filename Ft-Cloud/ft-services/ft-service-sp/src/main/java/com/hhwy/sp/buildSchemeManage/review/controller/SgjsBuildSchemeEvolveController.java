package com.hhwy.sp.buildSchemeManage.review.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeEvolve;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeEvolveQueryVo;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeEvolveService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsBuildScheme;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.service.ISgjsBuildSchemeService;
import com.hhwy.utils.excel.FtExcelUtil;
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
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark 施工方案进展
 */
@Validated
@RestController
@RequestMapping("/sgjsBuildSchemeEvolve")
public class SgjsBuildSchemeEvolveController extends BaseController {

    @Autowired
    private ISgjsBuildSchemeEvolveService sgjsBuildSchemeEvolveService;

    @Autowired
    private ISgjsBuildSchemeService sgjsBuildSchemeService;


    /**
     * 台账
     * @param queryVo
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsBuildSchemeEvolve:list")
    @GetMapping("/list")
    public AjaxResult getListByQueryVo(@Validated(ValidationGroups.Select.class) BuildSchemeEvolveQueryVo queryVo) {
        SgjsBuildScheme sgjsBuildScheme = new SgjsBuildScheme();
        sgjsBuildScheme.setValid("1");
        SgjsBuildScheme lastValidScheme = sgjsBuildSchemeService.getSgjsBuildScheme(sgjsBuildScheme);

        startPage();
        List<SgjsBuildSchemeEvolve> evolveList;
        if(lastValidScheme == null){
            evolveList = new ArrayList<>();
        }else {
            queryVo.setForeignId(lastValidScheme.getId());
            evolveList = sgjsBuildSchemeEvolveService.getListByQueryVo(queryVo);
            for (SgjsBuildSchemeEvolve evolve : evolveList) {
                evolve.setInventoryApprovalTime(lastValidScheme.getUpdateTime());
            }
        }
        return getDataTableAjaxResult(evolveList);
    }

    /**
     * 导出
     * @param response
     * @param queryVo
     * @throws IOException
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody BuildSchemeEvolveQueryVo queryVo) throws IOException {
        SgjsBuildScheme sgjsBuildScheme = new SgjsBuildScheme();
        sgjsBuildScheme.setValid("1");
        SgjsBuildScheme lastValidScheme = sgjsBuildSchemeService.getSgjsBuildScheme(sgjsBuildScheme);

        List<Long> ids = queryVo.getIds();
        List<SgjsBuildSchemeEvolve> evolveList;
        if(CollectionUtils.isEmpty(ids)){
            queryVo.setForeignId(lastValidScheme.getId());
            evolveList = sgjsBuildSchemeEvolveService.getListByQueryVo(queryVo);
        }else {
            evolveList = sgjsBuildSchemeEvolveService.getListByIds(ids);
        }
        if(CollectionUtils.isNotEmpty(evolveList)){
            for (SgjsBuildSchemeEvolve evolve : evolveList) {
                evolve.setInventoryApprovalTime(lastValidScheme.getUpdateTime());
            }
        }
        FtExcelUtil<SgjsBuildSchemeEvolve> util = new FtExcelUtil<>(SgjsBuildSchemeEvolve.class);
        util.exportExcel(response, evolveList, DateUtils.getDate());
    }
}
