package com.hhwy.pm.qyzs.safe.qyzsSafeDiseaseBank.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeDiseaseBank.domain.QyzsSafeDiseaseBank;
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
 * @date 2023-11-21 10:47:01
 * @remark 安全知识库-疾病库
 *
 * 8.7.2弹窗
 */
@Validated
@RestController
@RequestMapping("/qyzsSafeDiseaseBank")
public class QyzsSafeDiseaseBankController extends BaseController {

    @Value("${gm.back-url}")
    private String gmUrl;

    @GetMapping("/list")
    public AjaxResult getQyzsSafeDiseaseBankList(@Validated(ValidationGroups.Select.class) QyzsSafeDiseaseBank param) {
        String url = gmUrl + "/gm/qyzsSafeDiseaseBank/list?riskFactor={riskFactor}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, param.getRiskFactor());
    }


}
