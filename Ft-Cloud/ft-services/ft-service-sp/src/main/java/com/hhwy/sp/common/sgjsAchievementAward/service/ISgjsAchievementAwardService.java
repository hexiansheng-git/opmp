package com.hhwy.sp.common.sgjsAchievementAward.service;

import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;

import java.util.List;
import java.util.Map;

/**
 * @author han
 * @date 2024-01-25 09:45:38
 * @remark
 */
public interface ISgjsAchievementAwardService {

    SgjsAchievementAward getSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward);

    List<SgjsAchievementAward> getSgjsAchievementAwardList(SgjsAchievementAward sgjsAchievementAward);

    /**
     * 根据外键获取成果数据集
     * @param foreignId
     * @return
     */
    List<SgjsAchievementAward> getListByForeignId(Long foreignId);

    /**
     * 根据所属业务获取成果数据集
     * @param belongBusiness
     * @return
     */
    Map<Long,List<SgjsAchievementAward>> getMapByBelongBusiness(String belongBusiness);

    /**
     * 保存成果数据集
     * @param foreignId 外键id
     * @param belongBusiness 所属功能
     * @param awardList 成果数据集
     */
    void saveAchievementAward(Long foreignId,String belongBusiness,List<SgjsAchievementAward> awardList);

    int insertSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward);

    int insertSgjsAchievementAwardList(List<SgjsAchievementAward> sgjsAchievementAwardList);

    int updateSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward);

    int updateSgjsAchievementAwardList(List<SgjsAchievementAward> sgjsAchievementAwardList);

    int deleteSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward);

    int deleteSgjsAchievementAwardByPks(List<Long> sgjsAchievementAwardPkList);
}
