package com.hhwy.pm.qyzs.speciallistOrg.qyzsSpeciallistLibrary.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.speciallistOrg.qyzsSpeciallistLibrary.domain.SpeciallistLibraryQueryVo;
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
 * @date 2023-11-30 17:34:54
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qyzsSpeciallistLibrary")
public class QyzsSpeciallistLibraryController extends BaseController {


    @Value("${gm.back-url}")
    private String gmUrl;

    @GetMapping("/list")
    public AjaxResult getQyzsSpeciallistLibraryList(SpeciallistLibraryQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsSpeciallistLibrary/list?speciallistName={speciallistName}&department={department}&businessAreas={businessAreas}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getSpeciallistName(),queryVo.getDepartment(),queryVo.getBusinessAreas());
    }
}
