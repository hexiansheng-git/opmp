package com.hhwy.sd.groupManage.domain.vo;

import com.hhwy.sd.groupManage.domain.KcsjGroupManageContract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-12-13 15:27:15
 * @remark kcsj_group_manage_main
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KcsjGroupManageMainVo {

    /**
     * 字段描述：分包类型（1：总体分包，2：勘察分包，3：测绘分包，4：设计分包）
     */
    private String subpackageType;
    /**
     * 字段描述：经营模式（字典项：business_model）
     */
    private String businessModel;
    /**
     * 字段描述：附件组id
     */
    private String fileGroupId;

    private List<KcsjGroupManageContract> contractList;
}
