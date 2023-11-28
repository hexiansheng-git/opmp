package com.hhwy.pm.qyzs.survey.qyzsSystemManageMethod.service.impl;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.survey.qyzsSystemManageMethod.domain.SystemManageMethodQueryVo;
import com.hhwy.pm.qyzs.survey.qyzsSystemManageMethod.service.IQyzsSystemManageMethodService;
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
public class QyzsSystemManageMethodServiceImpl implements IQyzsSystemManageMethodService {

    @Value("${gm.back-url}")
    private String gmUrl;


    @Override
    public AjaxResult getQyzsSystemManageMethodList(SystemManageMethodQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsSystemManageMethod/list?level={level}&methodName={methodName}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getLevel(), queryVo.getMethodName());
    }
}
