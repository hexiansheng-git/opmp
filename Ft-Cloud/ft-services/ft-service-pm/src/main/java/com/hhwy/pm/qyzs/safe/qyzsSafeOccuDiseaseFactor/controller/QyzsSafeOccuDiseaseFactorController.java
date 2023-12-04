package com.hhwy.pm.qyzs.safe.qyzsSafeOccuDiseaseFactor.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeOccuDiseaseFactor.domain.QyzsSafeOccuDiseaseFactor;
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

/**
 * @author cjh
 * @date 2023-11-21 10:47:05
 * @remark 安全知识库-职业病危害因素库
 *
 * 8.7.1 弹窗
 */
@Validated
@RestController
@RequestMapping("/qyzsSafeOccuDiseaseFactor")
public class QyzsSafeOccuDiseaseFactorController extends BaseController {

    @Value("${gm.back-url}")
    private String gmUrl;

    @GetMapping("/list")
    public AjaxResult getQyzsSafeOccuDiseaseFactorList(@Validated(ValidationGroups.Select.class) QyzsSafeOccuDiseaseFactor param) {
        String url = gmUrl + "/gm/qyzsSafeOccuDiseaseFactor/list?occuDiseaseName={occuDiseaseName}&etiology={etiology}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, param.getOccuDiseaseName(), param.getEtiology());
    }

}
