package com.hhwy.sd.achievementReview.domain.vo;

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
public class AchievementQueryVo {
    private static final long serialVersionUID = 1L;


    /**
     * 字段描述：成果状态（未评审、评审中、已评审）
     */
    private String achievementStatus;
    /**
     * 字段描述：勘察设计成果名称
     */
    private String achievementName;
    /**
     * 字段描述：评审级别
     */
    private String reviewGrade;
    /**
     * 字段描述：计划评审日期
     */
    private String planReviewDateStart;
    private String planReviewDateEnd;
    /**
     * 字段描述：实际评审日期
     */
    private String actualReviewDateStart;
    private String actualReviewDateEnd;

    private List<Long> ids;
}
