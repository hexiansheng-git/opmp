package com.hhwy.pm.qyzs.manage.qyzsManageContCondition.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.manage.qyzsManageContCondition.domain.QyzsManageContCondition;
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
 * @date 2023-11-15 11:20:12
 * @remark 经营知识库-合同通用条件
 */
@Validated
@RestController
@RequestMapping("/qyzsManageContCondition")
public class QyzsManageContConditionController extends BaseController {

    @Value("${gm.back-url}")
    private String gmUrl;

    @GetMapping("/list")
    public AjaxResult getQyzsManageContConditionList(@Validated(ValidationGroups.Select.class) QyzsManageContCondition param) {
        String url = gmUrl + "/gm/qyzsManageContCondition/list?" +
                "contConditionNo={contConditionNo}&" +
                "chineseConditonName={chineseConditonName}&" +
                "&foreignConditonName={foreignConditonName}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, param.getContConditionNo(), param.getChineseConditonName(), param.getForeignConditonName());
    }

}
