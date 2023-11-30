package com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.SafeRiskBigProjQueryVo;

/**
 * @author cjh
 * @date 2023-11-20 15:21:23
 * @remark
 */
public interface IQyzsSafeRiskBigProjService {

    AjaxResult getQyzsSafeRiskBigProjList(SafeRiskBigProjQueryVo queryVo);
}
