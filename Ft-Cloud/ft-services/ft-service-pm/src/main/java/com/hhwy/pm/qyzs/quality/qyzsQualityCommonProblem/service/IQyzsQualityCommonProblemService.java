package com.hhwy.pm.qyzs.quality.qyzsQualityCommonProblem.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.quality.qyzsQualityCommonProblem.domain.QualityCommonProblemQueryVo;

/**
 * @author cjh
 * @date 2023-11-22 11:52:14
 * @remark
 */
public interface IQyzsQualityCommonProblemService {

    AjaxResult getQyzsQualityCommonProblemList(QualityCommonProblemQueryVo queryVo);
}
