package com.hhwy.pm.qqch.sgch.qqchconst.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyParam;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.vo.QqchSurveyDesignTeamsVo;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstFacilityPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstFacilityPlanService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 功能：施工部署 - 设备策划
 * 作者: fushudong
 * 时间: 2023/09/27
 */
@RestController
@RequestMapping("/qqchConstFacilityPlan")
public class QqchConstFacilityPlanController {

    @Autowired
    private IQqchConstFacilityPlanService constFacilityPlanService;

    /**
     * 获取设备策划
     * @return
     */
    @GetMapping("/getDevicePlanList")
    public AjaxResult getDevicePlanList(QqchConst qqchConst) {
        if (qqchConst.getVersion() == null){
            BigDecimal version = VersionUtil.getVersion("qqch_const",null);
            qqchConst.setVersion(version);
        }
        List<QqchConstFacilityPlan> list = constFacilityPlanService.list(CompileEntity.dealListDto(qqchConst.getVersion(), new QqchConstFacilityPlan()));
        return AjaxResult.success(list);
    }

    /**
     * 获取设备策划和人员策划 2.1.3同步1.3的数据
     * @return
     */
    @PostMapping("/dataSync")
    public AjaxResult dataSync(@RequestBody QqchSurveyDesignTeamsVo qqchSurveyDesignTeamsVo ) {
        QqchSurveyDesignTeamsVo vo = constFacilityPlanService.dataSync(qqchSurveyDesignTeamsVo);
        return AjaxResult.success(vo);
    }

}