package com.hhwy.sp.buildSchemeManage.review.domain.vo;

import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:39
 * @remark 区域总工/海外事业部总工节点页面数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildSchemeReviewOpinionVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：方案总得分
     */
    private Double score;
    /**
     * 字段描述：区域总工意见（1：通过  2：修改后通过  3：不通过）
     */
    private String regionChiefOpinion;
    /**
     * 字段描述：区域总工详细意见
     */
    private String regionChiefDetailOpinion;
    /**
     * 字段描述：海外事业部总工意见（1：通过  2：修改后通过  3：不通过）
     */
    private String overseasChiefOpinion;
    /**
     * 字段描述：海外事业部总工详细意见
     */
    private String overseasChiefDetailOpinion;
    /**
     * 字段描述：流程节点标识
     */
    private String flowNodeMark;

    private List<BuildSchemeStaffOpinionGatherVo> gatherVoList;
}
