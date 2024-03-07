package com.hhwy.sd.achievementReview.domain.vo;

import com.hhwy.sd.achievementReview.domain.KcsjAchievement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2024-02-05 09:04:08
 * @remark kcsj_achievement
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementPushVo {

    private List<Long> delIdList;

    private List<KcsjAchievement> addList;

    private List<KcsjAchievement> updateList;
}
