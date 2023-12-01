package com.hhwy.pm.qyzs.speciallistOrg.qyzsEnquiryOrgLibrary.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.speciallistOrg.qyzsEnquiryOrgLibrary.domain.EnquiryOrgLibraryQueryVo;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fsd
 * @date 2023-11-30 17:32:56
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qyzsEnquiryOrgLibrary")
public class QyzsEnquiryOrgLibraryController extends BaseController {


    @Value("${gm.back-url}")
    private String gmUrl;


    @GetMapping("/list")
    public AjaxResult getQyzsEnquiryOrgLibraryList(EnquiryOrgLibraryQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsEnquiryOrgLibrary/list?orgName={orgName}&orgType={orgType}&mainBusiness={mainBusiness}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getOrgName(),queryVo.getOrgType(),queryVo.getMainBusiness());
    }
}
