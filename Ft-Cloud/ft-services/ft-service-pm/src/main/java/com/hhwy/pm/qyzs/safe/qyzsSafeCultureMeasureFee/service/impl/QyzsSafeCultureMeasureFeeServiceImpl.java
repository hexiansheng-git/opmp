package com.hhwy.pm.qyzs.safe.qyzsSafeCultureMeasureFee.service.impl;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeCultureMeasureFee.domain.SafeCultureMeasureFeeQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeCultureMeasureFee.service.IQyzsSafeCultureMeasureFeeService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * @author cjh
 * @date 2023-11-21 10:46:55
 * @remark
 */
@Service
public class QyzsSafeCultureMeasureFeeServiceImpl implements IQyzsSafeCultureMeasureFeeService {

    @Value("${gm.back-url}")
    private String gmUrl;


    public AjaxResult getQyzsSafeCultureMeasureFeeList(SafeCultureMeasureFeeQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsSafeCultureMeasureFee/list?feeType={feeType}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getFeeType());
    }
}
