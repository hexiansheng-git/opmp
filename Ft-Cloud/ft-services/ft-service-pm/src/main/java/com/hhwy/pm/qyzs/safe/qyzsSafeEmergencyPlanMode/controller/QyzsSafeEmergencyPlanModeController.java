package com.hhwy.pm.qyzs.safe.qyzsSafeEmergencyPlanMode.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeEmergencyPlanMode.domain.QyzsSafeEmergencyPlanMode;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-20 15:21:18
 * @remark 安全知识库-应急预案模板库
 *
 * 8.10.1应急预案管控
 */
@Validated
@RestController
@RequestMapping("/qyzsSafeEmergencyPlanMode")
public class QyzsSafeEmergencyPlanModeController extends BaseController {

    @Value("${gm.back-url}")
    private String gmUrl;

//    @PreAuthorize(hasPermi = "qyzsSafeEmergencyPlanMode:list")
    @GetMapping("/list")
    public AjaxResult getQyzsSafeEmergencyPlanModeList(@Validated(ValidationGroups.Select.class) QyzsSafeEmergencyPlanMode param) {
        String url = gmUrl + "/gm/qyzsSafeEmergencyPlanMode/list?riskType={riskType}&modeName={modeName}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, param.getRiskType(), param.getModeName());
    }


}
