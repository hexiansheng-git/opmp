package com.hhwy.pm.qqch.preparation.technique.manage.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-11 15:23:27
 * @remark 3.3.2岗位设置
 */
@Data
public class QqchPostSettingVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目技术管理部门及岗位设置集合
     */
    private List<QqchPostSetting> techDeptTreeList;

    /**
     * 工区技术岗位设置集合
     */
    private List<QqchPostSetting> WorkAreaTreeList;
}
