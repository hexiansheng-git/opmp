package com.hhwy.pm.qqch.preparation.technique.manage.service;

import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.vo.QqchPostSettingVo;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-11 15:23:27
 * @remark 3.3.2岗位设置
 */
public interface IQqchPostSettingService {

    QqchPostSettingVo getTreeList(BigDecimal version);

    void batchSave(QqchPostSettingVo qqchPostSettingVo);

    /**
     * 获取项目技术管理部门及岗位设置表
     * @return
     */
    List<QqchPostSetting> getTechDeptList();

}
