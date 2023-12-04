package com.hhwy.pm.qyzs.quality.qyzsQualityRisk.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.quality.qyzsQualityRisk.domain.QyzsQualityRisk;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cjh
 * @date 2023-11-22 11:52:20
 * @remark 质量知识库-质量风险库
 *
 * 9.3.1 质量风险清单
 * 9.3.2 质量风险管控措施
 */
@Validated
@RestController
@RequestMapping("/qyzsQualityRisk")
public class QyzsQualityRiskController extends BaseController {

    @Value("${gm.back-url}")
    private String gmUrl;

//    @PreAuthorize(hasPermi = "qyzsQualityRisk:list")
    @GetMapping("/list")
    public AjaxResult getQyzsQualityRiskList(@Validated(ValidationGroups.Select.class) QyzsQualityRisk param) {
        String url = gmUrl + "/gm/qyzsQualityRisk/list?riskContent={riskContent}&consequence={consequence}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, param.getRiskContent(), param.getConsequence());
    }

}
