package com.hhwy.sp.buildSchemeManage.review.domain.vo;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:55
 * @remark 区域中心/海外事业部节点页面数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildSchemeReviewStaffVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：流程节点标识
     */
    private String flowNodeMark;

    /**
     * 字段描述：流程节点数据
     */
    private List<SgjsBuildSchemeReviewStaff> reviewStaffList;
}
