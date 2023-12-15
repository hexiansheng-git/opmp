package com.hhwy.pm.qyzs.quality.qyzsQualityCommonProblem.service.impl;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.gm.wbs.service.ITWbsService;
import com.hhwy.pm.qyzs.quality.qyzsQualityCommonProblem.domain.QualityCommonProblemQueryVo;
import com.hhwy.pm.qyzs.quality.qyzsQualityCommonProblem.service.IQyzsQualityCommonProblemService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * @author cjh
 * @date 2023-11-22 11:52:14
 * @remark
 */
@Service
public class QyzsQualityCommonProblemServiceImpl implements IQyzsQualityCommonProblemService {

    @Autowired
    private ITWbsService wbsService;

    @Value("${gm.back-url}")
    private String gmUrl;


    public AjaxResult getQyzsQualityCommonProblemList(QualityCommonProblemQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsQualityCommonProblem/getList?projectType={projectType}&wbsCode={wbsCode}&problemName={problemName}&measure={measure}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        String projectType = wbsService.getDefaultEngineeringType();
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, projectType, queryVo.getWbsCode(), queryVo.getProblemName(), queryVo.getMeasure());
    }
}
