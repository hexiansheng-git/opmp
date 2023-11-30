package com.hhwy.pm.qyzs.construction.qyzsConstructionManageMethod.domain;

import lombok.Data;

/**
 * @author cjh
 * @date 2023-11-14 16:41:14
 * @remark qyzs_system_manage_method
 */
@Data
public class ConstructionManageMethodQueryVo {
    /**
     * 字段描述：层级
     */
    private String level;
    /**
     * 字段描述：制度及管理方法名称
     */
    private String methodName;
}
