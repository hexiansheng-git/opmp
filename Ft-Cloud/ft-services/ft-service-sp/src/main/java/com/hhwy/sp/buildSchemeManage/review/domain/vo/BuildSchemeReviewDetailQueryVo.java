package com.hhwy.sp.buildSchemeManage.review.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark 施工方案评审详情查询参数实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildSchemeReviewDetailQueryVo {
    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 字段描述：详情类型  1：编辑  2：发起审批   3：查看  4：处理
     */
    private String type;
    /**
     * 字段描述：流程节点标识
     */
    private String flowNodeMark;


    /*当前登录人用户名（测试使用）*/
    private String userName;
}
