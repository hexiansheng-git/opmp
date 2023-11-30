package com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.service.impl;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.SafeRiskBigProjQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.service.IQyzsSafeRiskBigProjService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * @author cjh
 * @date 2023-11-20 15:21:23
 * @remark
 */
@Service
public class QyzsSafeRiskBigProjServiceImpl implements IQyzsSafeRiskBigProjService {

    @Value("${gm.back-url}")
    private String gmUrl;


    public AjaxResult getQyzsSafeRiskBigProjList(SafeRiskBigProjQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsSafeRiskBigProj/getList?riskProjType={riskProjType}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getRiskProjType());
    }
}
