package com.hhwy.pm.qyzs.safe.qyzsSafePublicSafeStandard.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafePublicSafeStandard.domain.QyzsSafePublicSafeStandard;
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
 * @date 2023-11-21 10:47:10
 * @remark 安全知识库-公共安全标准
 *
 * 8.6.1 弹窗
 */
@Validated
@RestController
@RequestMapping("/qyzsSafePublicSafeStandard")
public class QyzsSafePublicSafeStandardController extends BaseController {

    @Value("${gm.back-url}")
    private String gmUrl;

    @GetMapping("/list")
    public AjaxResult getQyzsSafePublicSafeStandardList(@Validated(ValidationGroups.Select.class) QyzsSafePublicSafeStandard param) {
        String url = gmUrl + "/gm/qyzsSafePublicSafeStandard/list?type={type}&setProject={setProject}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, param.getType(), param.getSetProject());
    }

}
