package com.hhwy.sp.techManagement.sgjsPaperPublish.mapper;

import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.SgjsPaperPublish;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.vo.PaperPublishQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:37
 * @remark 
 */
@Repository
public interface SgjsPaperPublishMapper {

    SgjsPaperPublish getSgjsPaperPublishById(@Param("id") Long id);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
    SgjsPaperPublish getSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish);

    List<SgjsPaperPublish> getSgjsPaperPublishList(PaperPublishQueryVo queryVo);

    int insertSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish);

    int insertSgjsPaperPublishList(@Param("sgjsPaperPublishList") List<SgjsPaperPublish> sgjsPaperPublishList);

    int updateSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish);

    int updateSgjsPaperPublishList(@Param("sgjsPaperPublishList") List<SgjsPaperPublish> sgjsPaperPublishList);
    
    int deleteSgjsPaperPublish(SgjsPaperPublish sgjsPaperPublish);

    int deleteSgjsPaperPublishByPks(@Param("sgjsPaperPublishPkList") List<Long> sgjsPaperPublishPkList);

    void deleteSgjsPaperPublishById(@Param("id") Long id);
}
