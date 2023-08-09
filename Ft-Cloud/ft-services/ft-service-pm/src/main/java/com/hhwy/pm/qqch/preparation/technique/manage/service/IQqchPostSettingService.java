package com.hhwy.pm.qqch.preparation.technique.manage.service;

import com.hhwy.pm.qqch.preparation.technique.manage.domain.vo.QqchPostSettingVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-07-11 15:23:27
 * @remark 3.3.2岗位设置
 */
public interface IQqchPostSettingService {

    QqchPostSettingVo getTreeList(BigDecimal version);

    void batchSave(QqchPostSettingVo qqchPostSettingVo);
}
