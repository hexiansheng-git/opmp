package com.hhwy.pm.qyzs.finance.qyzsFinanceAccountPolicy.service.impl;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceAccountPolicy.domain.FinanceAccountPolicyQueryVo;
import com.hhwy.pm.qyzs.finance.qyzsFinanceAccountPolicy.service.IQyzsFinanceAccountPolicyService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * @author cj
 * @date 2023-11-27 16:01:11
 * @remark
 */
@Service
public class QyzsFinanceAccountPolicyServiceImpl implements IQyzsFinanceAccountPolicyService {

    @Value("${gm.back-url}")
    private String gmUrl;


    public AjaxResult getQyzsFinanceAccountPolicyList(FinanceAccountPolicyQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsFinanceAccountPolicy/list?countryCode={countryCode}&accountingPolicy={accountingPolicy}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getCountryCode(), queryVo.getAccountingPolicy());
    }
}
