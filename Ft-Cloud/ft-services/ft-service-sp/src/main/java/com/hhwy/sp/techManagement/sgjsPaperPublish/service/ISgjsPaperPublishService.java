package com.hhwy.sp.techManagement.sgjsPaperPublish.service;

import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.SgjsPaperPublish;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.vo.PaperPublishExportVo;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.vo.PaperPublishQueryVo;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:37
 * @remark
 */
public interface ISgjsPaperPublishService {

    SgjsPaperPublish getSgjsPaperPublishById(Long id, String type);

    SgjsPaperPublish getSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish);

    List<SgjsPaperPublish> getSgjsPaperPublishList(PaperPublishQueryVo queryVo);

    int insertSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish);

    int insertSgjsPaperPublishList(List<SgjsPaperPublish> sgjsPaperPublishList);

    int updateSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish);

    int updateSgjsPaperPublishList(List<SgjsPaperPublish> sgjsPaperPublishList);

    int deleteSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish);

    int deleteSgjsPaperPublishByPks(List<Long> sgjsPaperPublishPkList);

    Long save(SgjsPaperPublish paperPublish);

    void deleteSgjsPaperPublishById(Long id);

    List<SgjsPaperPublish> getListByIds(List<Long> ids);

    List<PaperPublishExportVo> getExportVoList(List<SgjsPaperPublish> sgjsPaperPublishList);

    void updatePaperPublishProcess(Long id, String pass);

    void submitPaperPublishProcess(Long id);
}
