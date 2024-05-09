package com.hhwy.sp.experiment.sgjsExperProgressManage.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.experiment.sgjsExperProgressManage.domain.SgjsExperProgressManage;
import com.hhwy.sp.experiment.sgjsExperProgressManage.domain.SgjsExperProgressManageVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wll
 * @date 2023-12-09 11:06:27
 * @remark
 */
public interface ISgjsExperProgressManageService {

    SgjsExperProgressManage getSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);


    /**
     * 列表查询
     *
     * @param sgjsTechnicalManage
     * @return
     */
    SgjsExperProgressManageVo list(SgjsExperProgressManage sgjsTechnicalManage);

    List<SgjsExperProgressManage> getSgjsExperProgressManageList(SgjsExperProgressManage sgjsExperProgressManage);

    int insertSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

    int insertSgjsExperProgressManageList(List<SgjsExperProgressManage> sgjsExperProgressManageList);

    int updateSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

    int updateSgjsExperProgressManageList(List<SgjsExperProgressManage> sgjsExperProgressManageList);

    int deleteSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

    int deleteSgjsExperProgressManageByPks(List<Long> sgjsExperProgressManagePkList);

    /**
     * 同步前期策划的数据
     *
     * @return
     */
    SgjsExperProgressManageVo sync();

    /**
     * 批量新增
     *
     * @param sgjsExperProgressManageVo
     * @return
     */
    AjaxResult batchAdd(SgjsExperProgressManageVo sgjsExperProgressManageVo);


    List<SgjsExperProgressManage> getIds(List<String> ids);

    /**
     * 导入文件
     * @param file
     * @return
     */
    AjaxResult importData(MultipartFile file);

}
