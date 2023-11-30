package com.hhwy.pm.qyzs.construction.qyzsConstructionManageMethod.service.impl;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.construction.qyzsConstructionManageMethod.domain.ConstructionManageMethodQueryVo;
import com.hhwy.pm.qyzs.construction.qyzsConstructionManageMethod.service.IQyzsConstructionManageMethodService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * @author cjh
 * @date 2023-11-14 16:41:14
 * @remark
 */
@Service
public class QyzsConstructionManageMethodServiceImpl implements IQyzsConstructionManageMethodService {

    @Value("${gm.back-url}")
    private String gmUrl;


    public AjaxResult getQyzsConstructionManageMethodList(ConstructionManageMethodQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsConstructionManageMethod/list?level={level}&methodName={methodName}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getLevel(), queryVo.getMethodName());
    }
}
