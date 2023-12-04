package com.hhwy.pm.qyzs.quality.qyzsQualitySpecialInspection.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.quality.qyzsQualitySpecialInspection.domain.QyzsQualitySpecialInspection;
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
 * @date 2023-11-22 11:52:26
 * @remark 质量知识库-质量专项检查表
 *
 * 9.4.3 重难点工程检查项
 */
@Validated
@RestController
@RequestMapping("/qyzsQualitySpecialInspection")
public class QyzsQualitySpecialInspectionController extends BaseController {

    @Value("${gm.back-url}")
    private String gmUrl;

//    @PreAuthorize(hasPermi = "qyzsQualitySpecialInspection:list")
    @GetMapping("/list")
    public AjaxResult getQyzsQualitySpecialInspectionList(@Validated(ValidationGroups.Select.class) QyzsQualitySpecialInspection param) {
        String url = gmUrl + "/gm/qyzsQualitySpecialInspection/list?inspectionName={inspectionName}&inspectionProject={inspectionProject}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, param.getInspectionName(), param.getInspectionProject());
    }

}
