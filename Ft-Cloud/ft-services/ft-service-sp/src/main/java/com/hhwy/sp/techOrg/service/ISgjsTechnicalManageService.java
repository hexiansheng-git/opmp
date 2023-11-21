package com.hhwy.sp.techOrg.service;

import java.util.List;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManage;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageVo;

/**
 * @author lcf
 * @date 2023-11-17 11:29:23
 * @remark 
 */
public interface ISgjsTechnicalManageService {
                                                                                                                                                                                                                                                                                                                                                                
    SgjsTechnicalManage getSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage);

    List<SgjsTechnicalManage> getSgjsTechnicalManageList(SgjsTechnicalManage sgjsTechnicalManage);

    /**
     * 列表查询
     *
     * @param sgjsTechnicalManage
     * @return
     */
    SgjsTechnicalManageVo list(SgjsTechnicalManage sgjsTechnicalManage);

    int insertSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage);

    int insertSgjsTechnicalManageList(List<SgjsTechnicalManage> sgjsTechnicalManageList);

    int updateSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage);

    int updateSgjsTechnicalManageList(List<SgjsTechnicalManage> sgjsTechnicalManageList);
    
    int deleteSgjsTechnicalManage(SgjsTechnicalManage sgjsTechnicalManage);

    int deleteSgjsTechnicalManageByPks(List<Long> sgjsTechnicalManagePkList);

    /**
     * 批量保持
     *
     * @param sgjsTechnicalManageVo
     * @return
     */
    int batchAdd(SgjsTechnicalManageVo sgjsTechnicalManageVo);

    /**
     * 同步前期策划
     *
     * @return
     */
    AjaxResult sync();
}
