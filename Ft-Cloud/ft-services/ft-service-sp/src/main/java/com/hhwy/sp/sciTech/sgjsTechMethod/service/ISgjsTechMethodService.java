package com.hhwy.sp.sciTech.sgjsTechMethod.service;

import java.util.List;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.sciTech.sgjsTechMethod.domain.SgjsTechMethod;

/**
 * @author cjh
 * @date 2024-01-25 10:11:14
 * @remark
 */
public interface ISgjsTechMethodService {

    SgjsTechMethod getSgjsTechMethod(SgjsTechMethod sgjsTechMethod);

    List<SgjsTechMethod> getSgjsTechMethodList(SgjsTechMethod sgjsTechMethod);

    int insertSgjsTechMethod(SgjsTechMethod sgjsTechMethod);

    int insertSgjsTechMethodList(List<SgjsTechMethod> sgjsTechMethodList);

    SgjsTechMethod updateSgjsTechMethod(SgjsTechMethod sgjsTechMethod);

    int updateSgjsTechMethodList(List<SgjsTechMethod> sgjsTechMethodList);

    int deleteSgjsTechMethod(SgjsTechMethod sgjsTechMethod);

    int deleteSgjsTechMethodByPks(List<Long> sgjsTechMethodPkList);

    void updateTaskStatus(Long id, String isPass);

    AjaxResult messagePublic();
}
