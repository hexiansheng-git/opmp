package com.hhwy.pm.qyzs.safe.qyzsSafeOrganPost.service.impl;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeOrganPost.domain.SafeOrganPostQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeOrganPost.service.IQyzsSafeOrganPostService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * @author cjh
 * @date 2023-11-17 16:26:00
 * @remark
 */
@Service
public class QyzsSafeOrganPostServiceImpl implements IQyzsSafeOrganPostService {

    @Value("${gm.back-url}")
    private String gmUrl;


    public AjaxResult getQyzsSafeOrganPostList(SafeOrganPostQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsSafeOrganPost/list?postName={postName}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getPostName());
    }
}
