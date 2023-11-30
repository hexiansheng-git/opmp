package com.hhwy.pm.qyzs.finance.qyzsFinanceBankStatus.service.impl;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceBankStatus.domain.FinanceBankStatusQueryVo;
import com.hhwy.pm.qyzs.finance.qyzsFinanceBankStatus.service.IQyzsFinanceBankStatusService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * @author cjh
 * @date 2023-11-27 16:41:22
 * @remark
 */
@Service
public class QyzsFinanceBankStatusServiceImpl implements IQyzsFinanceBankStatusService {

    @Value("${gm.back-url}")
    private String gmUrl;


    public AjaxResult getQyzsFinanceBankStatusList(FinanceBankStatusQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsFinanceBankStatus/list?countryCode={countryCode}&nature={nature}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getCountryCode(), queryVo.getNature());
    }
}
