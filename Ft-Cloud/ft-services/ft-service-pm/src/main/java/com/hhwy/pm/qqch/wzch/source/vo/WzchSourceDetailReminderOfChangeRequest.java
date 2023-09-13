package com.hhwy.pm.qqch.wzch.source.vo;

import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 来源策划详情总需变更提醒请求对象
 * @author HCT
 */
@Data
public class WzchSourceDetailReminderOfChangeRequest implements Serializable {

    /**
     * 项目ID
     */
    @NotNull(message = "项目ID不能为空",groups = {ValidationGroups.Select.class})
    private Long projectId;
    /**
     * 来源策划ID
     */
    @NotNull(message = "来源策划ID不能为空",groups = {ValidationGroups.Select.class})
    private Long sourceId;

}
