package com.hhwy.sp.buildSchemeManage.review.domain.vo;

import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeStaffOpinion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:59
 * @remark 评审人员节点页面数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildSchemeStaffOpinionVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：评审人员id
     */
    private String reviewStaffId;

    /**
     * 字段描述：流程节点标识
     */
    private String flowNodeMark;

    /**
     * 字段描述：评分
     */
    private Double score;

    /**
     * 字段描述：意见详情
     */
    private List<SgjsBuildSchemeStaffOpinion> staffOpinionList;
}
