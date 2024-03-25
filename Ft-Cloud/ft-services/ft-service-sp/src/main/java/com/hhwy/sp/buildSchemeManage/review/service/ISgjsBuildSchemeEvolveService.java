package com.hhwy.sp.buildSchemeManage.review.service;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeEvolve;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeEvolveQueryVo;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark
 */
public interface ISgjsBuildSchemeEvolveService {


    List<SgjsBuildSchemeEvolve> getListByQueryVo(BuildSchemeEvolveQueryVo queryVo);

    List<SgjsBuildSchemeEvolve> getListByIds(List<Long> ids);
}
