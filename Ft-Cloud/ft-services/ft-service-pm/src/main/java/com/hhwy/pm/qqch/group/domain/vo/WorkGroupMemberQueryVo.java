package com.hhwy.pm.qqch.group.domain.vo;

import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author han
 * @date 2023-07-06 15:23:45
 * @remark 前期策划工作小组成员
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkGroupMemberQueryVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主表id
     */
    private Long masterId;
    /**
     * 字段描述：曾任小组职务
     */
    private String temporaryGroupDuty;
    /**
     * 字段描述：负责人姓名
     */
    private String director;
    /**
     * 字段描述：页面所有负责人id
     */
    @NotBlank(message = "负责人Id不能为空",groups = ValidationGroups.Select.class)
    private String directorIds;
    /**
     * 字段描述：页面所有负责人数据
     */
    private List<QqchWorkGroupMember> memberList;

}
